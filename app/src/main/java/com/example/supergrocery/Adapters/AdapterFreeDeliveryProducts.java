package com.example.supergrocery.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supergrocery.GetModels.AllProductsData;
import com.example.supergrocery.GetModels.FreeDeliveryProductsData;
import com.example.supergrocery.Interfaces.ItemClickInterface;
import com.example.supergrocery.Other.Links;
import com.example.supergrocery.R;

import java.util.List;

public class AdapterFreeDeliveryProducts extends RecyclerView.Adapter<AdapterFreeDeliveryProducts.ViewHolder> {
    Context context;
    List<AllProductsData> allProductsData;

    public AdapterFreeDeliveryProducts(Context context, List<AllProductsData> allProductsData) {
        this.context = context;
        this.allProductsData = allProductsData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewholder = LayoutInflater.from(parent.getContext()).inflate(R.layout.free_delivery_products_model, parent, false);
        return new AdapterFreeDeliveryProducts.ViewHolder(viewholder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(Links.categories_images+allProductsData.get(position).getImage()).into(holder.imageview_free_delivery_product);
        holder.imageview_free_delivery_product.setOnClickListener(v -> {

            ((ItemClickInterface)context).freeDeliveryClicked(allProductsData.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return allProductsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageview_free_delivery_product;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageview_free_delivery_product=itemView.findViewById(R.id.imageview_free_delivery_product);
        }
    }
}
