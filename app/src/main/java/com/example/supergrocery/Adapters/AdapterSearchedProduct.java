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
import com.example.supergrocery.ModelsGet.CategoriesData;
import com.example.supergrocery.Interfaces.ItemClickInterface;
import com.example.supergrocery.Other.Links;
import com.example.supergrocery.R;
import com.example.supergrocery.databinding.MainSearchModelBinding;
import com.example.supergrocery.databinding.MoreProductsModelBinding;

import java.util.List;

public class AdapterSearchedProduct extends RecyclerView.Adapter<AdapterSearchedProduct.ViewHolder> {
    Context context;
    List<CategoriesData> categoriesData;

    public AdapterSearchedProduct(Context context, List<CategoriesData> categoriesData) {
        this.context = context;
        this.categoriesData = categoriesData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        MainSearchModelBinding binding=MainSearchModelBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvSearchedName.setText(categoriesData.get(position).getName());
        Glide.with(context).load(Links.categories_images+categoriesData.get(position).getImage()).into(holder.binding.ivSearchedItem);
        holder.binding.ivSearchedItem.setOnClickListener(v -> ((ItemClickInterface)context).categoryClicked(categoriesData.get(position)));
        holder.binding.tvSearchedName.setOnClickListener(v -> ((ItemClickInterface)context).categoryClicked(categoriesData.get(position)));
        holder.binding.tvSearchedDescription.setOnClickListener(v -> ((ItemClickInterface)context).categoryClicked(categoriesData.get(position)));
        holder.binding.tvEmpty.setOnClickListener(v -> ((ItemClickInterface)context).categoryClicked(categoriesData.get(position)));

    }

    @Override
    public int getItemCount() {
        return categoriesData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        MainSearchModelBinding binding;

        public ViewHolder(MainSearchModelBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
    }
}
