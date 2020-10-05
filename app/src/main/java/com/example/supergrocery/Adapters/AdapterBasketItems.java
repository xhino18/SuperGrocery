package com.example.supergrocery.Adapters;

import android.content.Context;
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
import com.example.supergrocery.Links;
import com.example.supergrocery.R;
import com.example.supergrocery.ROOM.OrderItemsModel;

import java.util.List;

public class AdapterBasketItems extends RecyclerView.Adapter<AdapterBasketItems.ViewHolder> {
    BasketFragment basketFragment;
    List<OrderItemsModel> orderItemsModels;
    LifecycleOwner owner;

    public AdapterBasketItems(BasketFragment basketFragment, List<OrderItemsModel> orderItemsModels, LifecycleOwner owner) {
        this.basketFragment = basketFragment;
        this.orderItemsModels = orderItemsModels;
        this.owner = owner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.basket_item_model, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_basket_item_name.setText(orderItemsModels.get(position).getName());
        holder.tv_basket_item_price.setText(orderItemsModels.get(position).getPrice().toString()+" ALL");
        holder.tv_basket_item_quantity.setText(orderItemsModels.get(position).getQuantity().toString());
        Glide.with(basketFragment).load(Links.categories_images+orderItemsModels.get(position).getUrlImage()).into(holder.iv_basket_item);
        holder.iv_add_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AddOrRemoveBasketItem)basketFragment).addClicked(orderItemsModels.get(position),position);
            }
        });
        holder.iv_remove_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AddOrRemoveBasketItem)basketFragment).removeClicked(orderItemsModels.get(position),position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderItemsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_basket_item,iv_add_quantity,iv_remove_quantity;
        TextView tv_basket_item_name,tv_basket_item_price,tv_basket_item_quantity;
        Button button_confirm;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_basket_item=itemView.findViewById(R.id.iv_basket_item);
            iv_add_quantity=itemView.findViewById(R.id.iv_add_quantity);
            iv_remove_quantity=itemView.findViewById(R.id.iv_remove_quantity);
            tv_basket_item_name=itemView.findViewById(R.id.tv_basket_item_name);
            tv_basket_item_price=itemView.findViewById(R.id.tv_basket_item_price);
            tv_basket_item_quantity=itemView.findViewById(R.id.tv_basket_item_quantity);
            button_confirm=itemView.findViewById(R.id.button_confirm);
        }
    }
}
