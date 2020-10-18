package com.example.supergrocery.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.supergrocery.Adapters.AdapterBasketItems;
import com.example.supergrocery.Interfaces.AddOrRemoveBasketItem;
import com.example.supergrocery.MainActivity2;
import com.example.supergrocery.Payment.PaymentActivity;
import com.example.supergrocery.R;
import com.example.supergrocery.ROOM.ItemsDB;
import com.example.supergrocery.ROOM.OrderItem;
import com.example.supergrocery.databinding.FragmentBasketBinding;

import java.util.ArrayList;
import java.util.List;




public class BasketFragment extends Fragment implements AddOrRemoveBasketItem {
    FragmentBasketBinding fragmentBasketBinding;
    
    List<OrderItem> orderItemsModels = new ArrayList<>();
    List<OrderItem> list = new ArrayList<>();
    AdapterBasketItems adapterBasketItems;
    LifecycleOwner owner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentBasketBinding=FragmentBasketBinding.inflate(inflater,container,false);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(fragmentBasketBinding.recycleviewBasketItems);
        View view=fragmentBasketBinding.getRoot();

        fragmentBasketBinding.recycleviewBasketItems.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        fragmentBasketBinding.buttonCheckout.setOnClickListener(v -> {
            Intent intent=new Intent(getActivity(), PaymentActivity.class);
            String total=fragmentBasketBinding.tvFinalTotal.getText().toString();
            intent.putExtra("getTotal",total);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        getTotalQuantity();
        showBasketItems();
        updateTotal();

        return view;
    }

    private void getTotalQuantity() {
        ((MainActivity2)requireActivity()).getTotalQuantity();
    }


    public void showBasketItems() {
        orderItemsModels = ItemsDB.getInstance(getActivity()).orderItemDao().getAllItems();
        adapterBasketItems = new AdapterBasketItems(BasketFragment.this, orderItemsModels, owner);
       fragmentBasketBinding.recycleviewBasketItems.setAdapter(adapterBasketItems);
    }
    @Override
    public void addClicked(OrderItem orderItemsModel, int position) {
        updateOrderItem(orderItemsModels.get(position), 1);
        adapterBasketItems.notifyDataSetChanged();
       fragmentBasketBinding.recycleviewBasketItems.setAdapter(adapterBasketItems);
        updateTotal();
        getTotalQuantity();

    }
    @Override
    public void removeClicked(OrderItem orderItemsModel, int position) {
        if (orderItemsModels.get(position).getQuantity() == 1) {
            if (orderItemsModels.size() == 0) {
                OrderItem orderItem = orderItemsModels.get(position);
                ItemsDB.getInstance(getActivity()).orderItemDao().delete(orderItem);
                adapterBasketItems.notifyDataSetChanged();
               fragmentBasketBinding.recycleviewBasketItems.setAdapter(adapterBasketItems);
                updateTotal();
                getTotalQuantity();
            } else {
                OrderItem orderItem = orderItemsModels.get(position);
                ItemsDB.getInstance(getActivity()).orderItemDao().delete(orderItem);
                orderItemsModels.remove(position);
                adapterBasketItems.notifyDataSetChanged();
               fragmentBasketBinding.recycleviewBasketItems.setAdapter(adapterBasketItems);
                updateTotal();
                getTotalQuantity();
            }
        } else {
            updateOrderItem(orderItemsModels.get(position), -1);
            adapterBasketItems.notifyDataSetChanged();
           fragmentBasketBinding.recycleviewBasketItems.setAdapter(adapterBasketItems);
            updateTotal();
            getTotalQuantity();
        }
    }
    public void updateOrderItem(OrderItem orderItem, int value) {
        int basketPosition = orderItemExistsOnBasket(orderItem);
        orderItemsModels.get(basketPosition).setQuantity(orderItemsModels.get(basketPosition).getQuantity() + value);
        orderItem.setQuantity(orderItemsModels.get(basketPosition).getQuantity());
        ItemsDB.getInstance(getActivity()).orderItemDao().update(orderItem);
        adapterBasketItems = new AdapterBasketItems(BasketFragment.this, orderItemsModels, owner);
       fragmentBasketBinding.recycleviewBasketItems.setAdapter(adapterBasketItems);

    }
    public int orderItemExistsOnBasket(OrderItem productData) {
        for (int i = 0; i < orderItemsModels.size(); i++) {
            if (orderItemsModels.get(i).getId()==(productData.getId())) {
                return i;
            }
        }
        return -1;
    }
    public void updateTotal() {
        double total = 0;
        for (int i = 0; i < orderItemsModels.size(); i++) {
            total = total + orderItemsModels.get(i).getPrice() * orderItemsModels.get(i).getQuantity();
        }
        fragmentBasketBinding.tvFinalTotal.setText(total + " ALL");
    }
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback= new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            OrderItem orderItem = orderItemsModels.get(viewHolder.getAdapterPosition());
            orderItemsModels.remove(viewHolder.getAdapterPosition());
            Toast.makeText(getActivity(), "Items removed!", Toast.LENGTH_SHORT).show();
            ItemsDB.getInstance(getActivity()).orderItemDao().delete(orderItem);
            adapterBasketItems.notifyDataSetChanged();
            fragmentBasketBinding.recycleviewBasketItems.setAdapter(adapterBasketItems);
            updateTotal();
            getTotalQuantity();


        }
    };

}