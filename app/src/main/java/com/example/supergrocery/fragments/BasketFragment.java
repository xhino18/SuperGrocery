package com.example.supergrocery.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.supergrocery.MainViewModel;
import com.example.supergrocery.adapters.AdapterBasketItems;
import com.example.supergrocery.interfaces.AddOrRemoveBasketItem;
import com.example.supergrocery.MainActivity;
import com.example.supergrocery.payment.PaymentActivity;
import com.example.supergrocery.R;
import com.example.supergrocery.room.ItemsDB;
import com.example.supergrocery.room.OrderItem;
import com.example.supergrocery.databinding.FragmentBasketBinding;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BasketFragment extends Fragment implements AddOrRemoveBasketItem {
    FragmentBasketBinding fragmentBasketBinding;

    AdapterBasketItems adapterBasketItems;
    double total;
    private MainViewModel mainViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        fragmentBasketBinding = FragmentBasketBinding.inflate(inflater, container, false);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(fragmentBasketBinding.recycleviewBasketItems);
        adapterBasketItems = new AdapterBasketItems(BasketFragment.this);
        fragmentBasketBinding.recycleviewBasketItems.setAdapter(adapterBasketItems);
        init();

        return fragmentBasketBinding.getRoot();
    }

    private void init() {

        fragmentBasketBinding.buttonCheckout.setOnClickListener(v -> {
            mainViewModel.getOrderItemLiveData().observe(getViewLifecycleOwner(), orderItems -> {
            if (!orderItems.isEmpty()) {
                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                String total = fragmentBasketBinding.tvFinalTotal.getText().toString();
                intent.putExtra("payment_type", "checkout");
                intent.putExtra("getTotal", total);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else {
                Toast.makeText(getContext(), "Basket empty!", Toast.LENGTH_SHORT).show();
            }
            });
        });
        fragmentBasketBinding.ivDeleteAll.setOnClickListener(view1 -> {
            mainViewModel.getOrderItemLiveData().observe(getViewLifecycleOwner(), orderItems -> {
            if (!orderItems.isEmpty()) {
                new AlertDialog.Builder(requireContext())
                        .setTitle(R.string.delete_all)
                        .setMessage(R.string.confirmation_logout)
                        .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> deleteAll())
                        .setNegativeButton(android.R.string.no, null).show();
            } else {
                Toast.makeText(getContext(), "Basket is already empty!", Toast.LENGTH_SHORT).show();
            }
            });
        });
        mainViewModel.getBasketItems();
        getTotalQuantity();
        showBasketItems();
        updateTotal();
    }

    private void deleteAll() {
        mainViewModel.deleteAll();
        adapterBasketItems.notifyDataSetChanged();
        fragmentBasketBinding.recycleviewBasketItems.setAdapter(adapterBasketItems);
        updateTotal();
        getTotalQuantity();

    }

    private void getTotalQuantity() {
        ((MainActivity) requireActivity()).getTotalQuantity();

    }


    public void showBasketItems() {
        mainViewModel.getOrderItemLiveData().observe(getViewLifecycleOwner(), orderItems -> {
            adapterBasketItems.submitList(orderItems);
            if (orderItems.isEmpty()) {
                fragmentBasketBinding.basketanim.setVisibility(View.VISIBLE);
            } else {
                fragmentBasketBinding.basketanim.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void addClicked(OrderItem orderItemsModel) {
        mainViewModel.getOrderItemLiveData().observe(getViewLifecycleOwner(), orderItems -> {
        mainViewModel.incrementQuantity(orderItemsModel);
            adapterBasketItems.notifyDataSetChanged();
            updateTotal();
        getTotalQuantity();
        });
    }

    @Override
    public void removeClicked(OrderItem orderItemsModel) {
        mainViewModel.getOrderItemLiveData().observe(getViewLifecycleOwner(), orderItems -> {
            mainViewModel.decrementQuantity(orderItemsModel);
            adapterBasketItems.notifyDataSetChanged();
            updateTotal();
            getTotalQuantity();
            if (orderItems.isEmpty()) {
                fragmentBasketBinding.basketanim.setVisibility(View.VISIBLE);
            } else {
                fragmentBasketBinding.basketanim.setVisibility(View.GONE);
            }
        });
    }
//
//    public void updateOrderItem(OrderItem orderItem, int value) {
//        mainViewModel.getOrderItemLiveData().observe(getViewLifecycleOwner(), orderItems -> {
//        int basketPosition = orderItemExistsOnBasket(orderItem);
//        orderItems.get(basketPosition).setQuantity(orderItems.get(basketPosition).getQuantity() + value);
////        orderItem.setQuantity(orderItems.get(basketPosition).getQuantity());
//        mainViewModel.incrementQuantity(orderItem);
//        mainViewModel.updateBasket(orderItem);
//        adapterBasketItems = new AdapterBasketItems(BasketFragment.this);
//        fragmentBasketBinding.recycleviewBasketItems.setAdapter(adapterBasketItems);
//        });
//    }
//
//    public int orderItemExistsOnBasket(OrderItem productData) {
//        mainViewModel.getOrderItemLiveData().observe(getViewLifecycleOwner(), orderItems -> {
//
//            for (int i = 0; i < orderItems.size(); i++) {
//                if (orderItems.get(i).getId() == (productData.getId())) {
////                    return i;
//                }
//            }
//        });
//        return -1;
//
//
//    }

    public void updateTotal() {
        mainViewModel.getOrderItemLiveData().observe(getViewLifecycleOwner(), orderItems -> {
        total = 0;
        for (int i = 0; i < orderItems.size(); i++) {
            total = total + orderItems.get(i).getPrice() * orderItems.get(i).getQuantity();
        }
        fragmentBasketBinding.tvFinalTotal.setText(total + " ALL");
        });
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            mainViewModel.getOrderItemLiveData().observe(getViewLifecycleOwner(), orderItems -> {

            OrderItem orderItem = orderItems.get(viewHolder.getAdapterPosition());
            orderItems.remove(viewHolder.getAdapterPosition());
            Toast.makeText(getActivity(), "Items removed!", Toast.LENGTH_SHORT).show();
            mainViewModel.deleteBasketItem(orderItem);
            adapterBasketItems.notifyDataSetChanged();
            fragmentBasketBinding.recycleviewBasketItems.setAdapter(adapterBasketItems);
            updateTotal();
            getTotalQuantity();
            if (orderItems.isEmpty()) {
                fragmentBasketBinding.basketanim.setVisibility(View.VISIBLE);
            } else {
                fragmentBasketBinding.basketanim.setVisibility(View.GONE);
            }
            });

        }

    };

}