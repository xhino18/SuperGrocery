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
import com.example.supergrocery.payment.PaymentActivity;
import com.example.supergrocery.R;
import com.example.supergrocery.room.OrderItem;
import com.example.supergrocery.databinding.FragmentBasketBinding;


import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class BasketFragment extends Fragment implements AddOrRemoveBasketItem {
    FragmentBasketBinding binding;

    AdapterBasketItems adapterBasketItems;
    double total;
    private MainViewModel mainViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding = FragmentBasketBinding.inflate(inflater, container, false);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recycleviewBasketItems);
        adapterBasketItems = new AdapterBasketItems(BasketFragment.this);
        binding.recycleviewBasketItems.setAdapter(adapterBasketItems);
        init();
        getItems();
        return binding.getRoot();
    }

    private void getItems() {
        mainViewModel.getOrderItemLiveData().observe(getViewLifecycleOwner(), orderItems -> {
            Timber.e("observe");
            adapterBasketItems.submitList(orderItems);
            if (orderItems.isEmpty()) {
                binding.basketanim.setVisibility(View.VISIBLE);
            } else {
                binding.basketanim.setVisibility(View.GONE);
            }
            total = 0;
            for (int i = 0; i < orderItems.size(); i++) {
                total = total + orderItems.get(i).getPrice() * orderItems.get(i).getQuantity();
            }
            binding.tvFinalTotal.setText(total + " ALL");
        });
    }

    private void init() {
        binding.buttonCheckout.setOnClickListener(v -> {
            if (!mainViewModel.getOrderItemLiveData().getValue().isEmpty()) {
                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                String total = binding.tvFinalTotal.getText().toString();
                intent.putExtra("payment_type", "checkout");
                intent.putExtra("getTotal", total);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else {
                Toast.makeText(getContext(), "Basket empty!", Toast.LENGTH_SHORT).show();
            }

        });
        binding.ivDeleteAll.setOnClickListener(view1 -> {
            if (!mainViewModel.getOrderItemLiveData().getValue().isEmpty()) {
                new AlertDialog.Builder(requireContext())
                        .setTitle(R.string.delete_all)
                        .setMessage(R.string.confirmation_logout)
                        .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> deleteAll())
                        .setNegativeButton(android.R.string.no, null).show();
            } else {
                Toast.makeText(getContext(), "Basket is already empty!", Toast.LENGTH_SHORT).show();
            }
        });
        mainViewModel.getBasketItems();
    }

    private void deleteAll() {
        mainViewModel.deleteAll();
    }

    @Override
    public void addClicked(OrderItem orderItemsModel) {
        Timber.e("increment");
        mainViewModel.incrementQuantity(orderItemsModel);
        startActivity(new Intent(getActivity(),BasketFragment.class));

    }

    @Override
    public void removeClicked(OrderItem orderItemsModel) {
        Timber.e("decrement");
        mainViewModel.decrementQuantity(orderItemsModel);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            mainViewModel.deleteBasketItem(adapterBasketItems.getItem(viewHolder.getAdapterPosition()));
            Toast.makeText(getActivity(), "Items removed!", Toast.LENGTH_SHORT).show();
        }
    };
}