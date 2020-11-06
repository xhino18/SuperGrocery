package com.example.supergrocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supergrocery.models.ShopProductsData;
import com.example.supergrocery.interfaces.AddItemInBasket;
import com.example.supergrocery.interfaces.ProductClickedInterface;
import com.example.supergrocery.other.Links;
import com.example.supergrocery.databinding.ShopProductsModelBinding;

import java.util.List;

public class AdapterShopProducts extends RecyclerView.Adapter<AdapterShopProducts.ViewHolder> {
    Context context;
    List<ShopProductsData> modelShopProductsData;

    public AdapterShopProducts(Context context, List<ShopProductsData> modelShopProductsData) {
        this.context = context;
        this.modelShopProductsData = modelShopProductsData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ShopProductsModelBinding binding=ShopProductsModelBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.binding.tvProductName.setText(modelShopProductsData.get(position).getName());
        holder.binding.tvProductPrice.setText(modelShopProductsData.get(position).getPrice()+" ALL");
        Glide.with(context).load(Links.categories_images+modelShopProductsData.get(position).getImage()).into(holder.binding.ivProductsModel);
        holder.binding.ivAddProduct.setOnClickListener(view -> ((AddItemInBasket)context).addtoBasket(modelShopProductsData.get(position)));
        holder.binding.ivProductsModel.setOnClickListener(view -> { ((ProductClickedInterface)context).productClicked(modelShopProductsData.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return modelShopProductsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ShopProductsModelBinding binding;

        public ViewHolder(ShopProductsModelBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
    }
}
