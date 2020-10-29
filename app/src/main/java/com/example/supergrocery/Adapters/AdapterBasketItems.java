package com.example.supergrocery.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supergrocery.Fragments.BasketFragment;
import com.example.supergrocery.Interfaces.AddOrRemoveBasketItem;
import com.example.supergrocery.Other.Links;
import com.example.supergrocery.R;
import com.example.supergrocery.ROOM.OrderItem;
import com.example.supergrocery.databinding.BasketItemModelBinding;

import java.util.List;

public class AdapterBasketItems extends RecyclerView.Adapter<AdapterBasketItems.ViewHolder> {
    BasketFragment basketFragment;
    List<OrderItem> orderItemsModels;
    LifecycleOwner owner;

    public AdapterBasketItems(BasketFragment basketFragment, List<OrderItem> orderItemsModels, LifecycleOwner owner) {
        this.basketFragment = basketFragment;
        this.orderItemsModels = orderItemsModels;
        this.owner = owner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        BasketItemModelBinding binding=BasketItemModelBinding.inflate(inflater,parent,false);
        return new AdapterBasketItems.ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.binding.tvBasketItemName.setText(orderItemsModels.get(position).getName());
        holder.binding.tvBasketItemPrice.setText(orderItemsModels.get(position).getPrice()+" ALL");
        holder.binding.tvBasketItemQuantity.setText(Integer.toString(orderItemsModels.get(position).getQuantity()));
        Glide.with(basketFragment).load(Links.categories_images+orderItemsModels.get(position).getUrlImage()).into(holder.binding.ivBasketItem);
        holder.binding.ivAddQuantity.setOnClickListener(v -> ((AddOrRemoveBasketItem)basketFragment).addClicked(orderItemsModels.get(position),position));
        holder.binding.ivRemoveQuantity.setOnClickListener(v -> ((AddOrRemoveBasketItem)basketFragment).removeClicked(orderItemsModels.get(position),position));

    }

    @Override
    public int getItemCount() {
        return orderItemsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        BasketItemModelBinding binding;

        public ViewHolder(BasketItemModelBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
    }
}
