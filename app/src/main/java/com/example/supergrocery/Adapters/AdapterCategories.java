package com.example.supergrocery.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supergrocery.Interfaces.ItemClickInterface;
import com.example.supergrocery.Models.CategoriesData;
import com.example.supergrocery.Other.Links;
import com.example.supergrocery.databinding.FoodCategoriesModelBinding;

public class AdapterCategories extends ListAdapter<CategoriesData, AdapterCategories.CategoryViewHolder> {

    Context context;

    public AdapterCategories(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FoodCategoriesModelBinding binding = FoodCategoriesModelBinding.inflate(inflater, parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoriesData item = getItem(position);
        holder.bind(item);
        Glide.with(context).load(Links.categories_images + item.getImage()).into(holder.binding.ivCategoryProduct);
        holder.binding.ivCategoryProduct.setOnClickListener(v -> ((ItemClickInterface) context).categoryClicked(item));
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        FoodCategoriesModelBinding binding;

        public CategoryViewHolder(FoodCategoriesModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CategoriesData item) {
            binding.setCategory(item);
        }
    }

    public static final DiffUtil.ItemCallback<CategoriesData> DIFF_CALLBACK = new DiffUtil.ItemCallback<CategoriesData>() {
        @Override
        public boolean areItemsTheSame(@NonNull CategoriesData oldItem, @NonNull CategoriesData newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CategoriesData oldItem, @NonNull CategoriesData newItem) {
            return areItemsTheSame(oldItem, newItem);
        }
    };
}
