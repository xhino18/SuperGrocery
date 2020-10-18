package com.example.supergrocery.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supergrocery.GetModels.FreeDeliveryProductsData;
import com.example.supergrocery.Other.Links;
import com.example.supergrocery.R;

import java.util.List;

public class AdapterFreeDeliveryProducts extends RecyclerView.Adapter<AdapterFreeDeliveryProducts.ViewHolder> {
    Context context;
    List<FreeDeliveryProductsData> freeDeliveryProductsData;

    public AdapterFreeDeliveryProducts(Context context, List<FreeDeliveryProductsData> freeDeliveryProductsData) {
        this.context = context;
        this.freeDeliveryProductsData = freeDeliveryProductsData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewholder = LayoutInflater.from(parent.getContext()).inflate(R.layout.free_delivery_products_model, parent, false);
        return new AdapterFreeDeliveryProducts.ViewHolder(viewholder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(Links.categories_images+freeDeliveryProductsData.get(position).getImage()).into(holder.imageview_free_delivery_product);

    }

    @Override
    public int getItemCount() {
        return freeDeliveryProductsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageview_free_delivery_product;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageview_free_delivery_product=itemView.findViewById(R.id.imageview_free_delivery_product);
        }
    }
}
