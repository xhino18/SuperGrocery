package com.example.supergrocery.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supergrocery.GetModels.AllProductsData;
import com.example.supergrocery.Other.Links;
import com.example.supergrocery.R;
import com.example.supergrocery.databinding.AllProductsModelBinding;

import java.util.ArrayList;
import java.util.List;

public class AdapterAllProducts extends RecyclerView.Adapter<AdapterAllProducts.ViewHolder> {
    AllProductsModelBinding allProductsModelBinding;
    List<AllProductsData> allProductsData;
    Context context;

    public AdapterAllProducts( Context context,List<AllProductsData> allProductsData) {

        this.context = context;
        this.allProductsData = allProductsData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewholder = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_products_model, parent, false);
        return new AdapterAllProducts.ViewHolder(viewholder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(allProductsData.get(position).getName());
        holder.price.setText(Integer.toString(allProductsData.get(position).getPrice()));
        Glide.with(context).load(Links.categories_images+allProductsData.get(position).getImage()).into(holder.image);




    }

    @Override
    public int getItemCount() {
        return allProductsData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView image,image_add;
    TextView name,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_add=allProductsModelBinding.ivAddQuantity;
            image=allProductsModelBinding.ivAllProducts;
            name=allProductsModelBinding.tvAllProductsName;
            price=allProductsModelBinding.tvAllProductsPrice;
        }
    }
}
