package com.example.supergrocery.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supergrocery.ModelsGet.DiscountedProductsData;
import com.example.supergrocery.Interfaces.ItemClickInterface;
import com.example.supergrocery.Other.Links;
import com.example.supergrocery.R;

import java.util.List;

public class AdapterMoreDiscountedProducts extends RecyclerView.Adapter<AdapterMoreDiscountedProducts.ViewHolder> {
    Context context;
    List<DiscountedProductsData> discountedProductsData;

    public AdapterMoreDiscountedProducts(Context context, List<DiscountedProductsData> discountedProductsData) {
        this.context = context;
        this.discountedProductsData = discountedProductsData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewholder = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_products_model, parent, false);
        return new AdapterMoreDiscountedProducts.ViewHolder(viewholder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(Links.categories_images + discountedProductsData.get(position).getImage()).into(holder.imageview_more_products);
        holder.imageview_more_products.setOnClickListener(v -> {

            ((ItemClickInterface)context).dicountedProductsClicked(discountedProductsData.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return discountedProductsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview_more_products;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview_more_products = itemView.findViewById(R.id.imageview_more_products);

        }
    }
}

