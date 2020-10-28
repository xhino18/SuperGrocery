package com.example.supergrocery.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supergrocery.ModelsGet.ShopProductsData;
import com.example.supergrocery.Interfaces.ProductClickedInterface;
import com.example.supergrocery.Other.Links;
import com.example.supergrocery.R;

import java.util.List;

public class AdapterMoreShopProducts extends RecyclerView.Adapter<AdapterMoreShopProducts.ViewHolder> {
    Context context;
    List<ShopProductsData> modelShopProductsData;

    public AdapterMoreShopProducts(Context context, List<ShopProductsData> modelShopProductsData) {
        this.context = context;
        this.modelShopProductsData = modelShopProductsData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewholder = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_products_model, parent, false);
        return new ViewHolder(viewholder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(context).load(Links.categories_images+modelShopProductsData.get(position).getImage()).into(holder.imageview_more_products);
        holder.imageview_more_products.setOnClickListener(view -> {
            ((ProductClickedInterface)context).productClicked(modelShopProductsData.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return modelShopProductsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview_more_products;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview_more_products=itemView.findViewById(R.id.imageview_more_products);

        }
    }
}
