package com.example.supergrocery.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supergrocery.Models.AllProductsData;
import com.example.supergrocery.Interfaces.ItemClickInterface;
import com.example.supergrocery.Other.Links;
import com.example.supergrocery.databinding.MoreProductsModelBinding;

import java.util.List;

public class AdapterMoreFreeDeliveryProducts extends RecyclerView.Adapter<AdapterMoreFreeDeliveryProducts.ViewHolder> {
    Context context;
    List<AllProductsData> allProductsData;

    public AdapterMoreFreeDeliveryProducts(Context context, List<AllProductsData> allProductsData) {
        this.context = context;
        this.allProductsData = allProductsData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        MoreProductsModelBinding binding=MoreProductsModelBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(Links.categories_images+allProductsData.get(position).getImage()).into(holder.binding.imageviewMoreProducts);
        holder.binding.cardviewMoreProducts.setOnClickListener(v -> { ((ItemClickInterface)context).freeDeliveryClicked(allProductsData.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return allProductsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        MoreProductsModelBinding binding;

        public ViewHolder(MoreProductsModelBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
    }
}
