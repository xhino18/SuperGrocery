package com.example.supergrocery.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supergrocery.Models.CategoriesData;
import com.example.supergrocery.Interfaces.ItemClickInterface;
import com.example.supergrocery.Other.Links;
import com.example.supergrocery.databinding.FoodCategoriesModelBinding;

import java.util.List;

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.ViewHolder> {
    Context context;
    List<CategoriesData> categoriesDataList;

    public AdapterCategories(Context context, List<CategoriesData> categoriesDataList) {
        this.context = context;
        this.categoriesDataList = categoriesDataList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        FoodCategoriesModelBinding binding=FoodCategoriesModelBinding.inflate(inflater,parent,false);
        return new AdapterCategories.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.binding.tvCategoryProductName.setText(categoriesDataList.get(position).getName());
        Glide.with(context).load(Links.categories_images+categoriesDataList.get(position).getImage()).into(holder.binding.ivCategoryProduct);
        holder.binding.ivCategoryProduct.setOnClickListener(v -> ((ItemClickInterface)context).categoryClicked(categoriesDataList.get(position)));

    }

    @Override
    public int getItemCount() {
        return categoriesDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        FoodCategoriesModelBinding binding;

        public ViewHolder(FoodCategoriesModelBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
    }
}
