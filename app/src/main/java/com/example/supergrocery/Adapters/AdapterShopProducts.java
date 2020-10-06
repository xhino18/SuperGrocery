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
import com.example.supergrocery.Interfaces.AddItemInBasket;
import com.example.supergrocery.Other.Links;
import com.example.supergrocery.GetModels.ModelShopProductsData;
import com.example.supergrocery.R;

import java.util.List;

public class AdapterShopProducts extends RecyclerView.Adapter<AdapterShopProducts.ViewHolder> {
    Context context;
    List<ModelShopProductsData> modelShopProductsData;

    public AdapterShopProducts(Context context, List<ModelShopProductsData> modelShopProductsData) {
        this.context = context;
        this.modelShopProductsData = modelShopProductsData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewholder = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_products_model, parent, false);
        return new ViewHolder(viewholder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_product_name.setText(modelShopProductsData.get(position).getName());
        holder.tv_product_price.setText(modelShopProductsData.get(position).getPrice().toString()+" ALL");
        Glide.with(context).load(Links.categories_images+modelShopProductsData.get(position).getImage()).into(holder.iv_products_model);
        holder.iv_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AddItemInBasket)context).addtoBasket(modelShopProductsData.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelShopProductsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_products_model,iv_add_product;
        TextView tv_product_price,tv_product_name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_add_product=itemView.findViewById(R.id.iv_add_product);
            iv_products_model=itemView.findViewById(R.id.iv_products_model);
            tv_product_price=itemView.findViewById(R.id.tv_product_price);
            tv_product_name=itemView.findViewById(R.id.tv_product_name);
        }
    }
}
