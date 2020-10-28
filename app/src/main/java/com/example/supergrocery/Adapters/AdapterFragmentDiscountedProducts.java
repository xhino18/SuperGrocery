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
import com.example.supergrocery.Other.Links;
import com.example.supergrocery.R;

import java.util.List;

public class AdapterFragmentDiscountedProducts extends RecyclerView.Adapter<AdapterFragmentDiscountedProducts.ViewHolder> {
    Context context;
    List<DiscountedProductsData> discountedProductsDataList;

    public AdapterFragmentDiscountedProducts(Context context, List<DiscountedProductsData> discountedProductsDataList) {
        this.context = context;
        this.discountedProductsDataList = discountedProductsDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewholder = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_discounted_products_model, parent, false);
        return new AdapterFragmentDiscountedProducts.ViewHolder(viewholder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(Links.categories_images+discountedProductsDataList.get(position).getImage()).into(holder.imageview_discount_product);
    }

    @Override
    public int getItemCount() {
        return discountedProductsDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview_discount_product;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview_discount_product = itemView.findViewById(R.id.imageview_discount_product);


        }
    }
}