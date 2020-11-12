package com.example.supergrocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supergrocery.models.CategoriesData;
import com.example.supergrocery.models.DiscountedProductsData;
import com.example.supergrocery.interfaces.ItemClickInterface;
import com.example.supergrocery.other.Links;
import com.example.supergrocery.databinding.DiscountProductsModelBinding;

import java.util.List;

public class AdapterDiscountedProducts extends ListAdapter<DiscountedProductsData, AdapterDiscountedProducts.ViewHolder> {
    Context context;

    public AdapterDiscountedProducts(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DiscountProductsModelBinding binding = DiscountProductsModelBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiscountedProductsData item = getItem(position);
        holder.binding.imageviewDiscountProduct.setOnClickListener(v -> ((ItemClickInterface) context).dicountedProductsClicked(item));
        holder.bind(item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        DiscountProductsModelBinding binding;

        public ViewHolder(DiscountProductsModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(DiscountedProductsData item) {
            binding.setDiscountedProducts(item);
            binding.executePendingBindings();
        }
    }

    public static final DiffUtil.ItemCallback<DiscountedProductsData> DIFF_CALLBACK = new DiffUtil.ItemCallback<DiscountedProductsData>() {
        @Override
        public boolean areItemsTheSame(@NonNull DiscountedProductsData oldItem, @NonNull DiscountedProductsData newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull DiscountedProductsData oldItem, @NonNull DiscountedProductsData newItem) {
            return areItemsTheSame(oldItem, newItem);
        }
    };
}

