package com.example.supergrocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supergrocery.models.DiscountedProductsData;
import com.example.supergrocery.interfaces.ItemClickInterface;
import com.example.supergrocery.other.Links;
import com.example.supergrocery.databinding.MoreProductsModelBinding;

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
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        MoreProductsModelBinding binding=MoreProductsModelBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(Links.categories_images + discountedProductsData.get(position).getImage()).into(holder.binding.imageviewMoreProducts);
        holder.binding.cardviewMoreProducts.setOnClickListener(v -> {
            ((ItemClickInterface)context).dicountedProductsClicked(discountedProductsData.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return discountedProductsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        MoreProductsModelBinding binding;

        public ViewHolder(MoreProductsModelBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
    }
}

