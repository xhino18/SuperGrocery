package com.example.supergrocery.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supergrocery.Models.DiscountedProductsData;
import com.example.supergrocery.Interfaces.ItemClickInterface;
import com.example.supergrocery.Other.Links;
import com.example.supergrocery.databinding.DiscountProductsModelBinding;

import java.util.List;

public class AdapterDiscountedProducts extends RecyclerView.Adapter<AdapterDiscountedProducts.ViewHolder> {
    Context context;
    List<DiscountedProductsData> discountedProductsData;

    public AdapterDiscountedProducts(Context context, List<DiscountedProductsData> discountedProductsData) {
        this.context = context;
        this.discountedProductsData = discountedProductsData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        DiscountProductsModelBinding binding=DiscountProductsModelBinding.inflate(inflater,parent,false);
        return new AdapterDiscountedProducts.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(Links.categories_images + discountedProductsData.get(position).getImage()).into(holder.binding.imageviewDiscountProduct);
        holder.binding.imageviewDiscountProduct.setOnClickListener(v -> { ((ItemClickInterface)context).dicountedProductsClicked(discountedProductsData.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return discountedProductsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        DiscountProductsModelBinding binding;

        public ViewHolder(DiscountProductsModelBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
    }
}

