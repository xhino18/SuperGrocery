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
import com.example.supergrocery.Interfaces.ItemClickInterface;
import com.example.supergrocery.Other.Links;
import com.example.supergrocery.R;

import java.util.List;

public class AdapterMoreFreeDeliveryProducts extends RecyclerView.Adapter<AdapterMoreFreeDeliveryProducts.ViewHolder> {
    Context context;
    List<FreeDeliveryProductsData> freeDeliveryProductsData;

    public AdapterMoreFreeDeliveryProducts(Context context, List<FreeDeliveryProductsData> freeDeliveryProductsData) {
        this.context = context;
        this.freeDeliveryProductsData = freeDeliveryProductsData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewholder = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_products_model, parent, false);
        return new AdapterMoreFreeDeliveryProducts.ViewHolder(viewholder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(Links.categories_images+freeDeliveryProductsData.get(position).getImage()).into(holder.imageview_more_products);
        holder.imageview_more_products.setOnClickListener(v -> {
            ((ItemClickInterface)context).freeDeliveryClicked(freeDeliveryProductsData.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return freeDeliveryProductsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageview_more_products;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageview_more_products=itemView.findViewById(R.id.imageview_more_products);
        }
    }
}
