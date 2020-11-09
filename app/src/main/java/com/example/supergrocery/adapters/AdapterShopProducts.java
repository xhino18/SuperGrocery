package com.example.supergrocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supergrocery.models.ShopProductsData;
import com.example.supergrocery.interfaces.AddItemInBasket;
import com.example.supergrocery.interfaces.ProductClickedInterface;
import com.example.supergrocery.other.Links;
import com.example.supergrocery.databinding.ShopProductsModelBinding;

import java.util.List;

public class AdapterShopProducts extends ListAdapter<ShopProductsData, AdapterShopProducts.ViewHolder> {
    Context context;

    public AdapterShopProducts(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ShopProductsModelBinding binding = ShopProductsModelBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ShopProductsData item = getItem(position);
        holder.binding.ivAddProduct.setOnClickListener(view -> ((AddItemInBasket) context).addtoBasket(item));
        holder.binding.ivProductsModel.setOnClickListener(view -> {
            ((ProductClickedInterface) context).productClicked(item);
        });
        holder.bind(item);

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ShopProductsModelBinding binding;

        public ViewHolder(ShopProductsModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        private void bind(ShopProductsData item) {
            binding.setShopProductsModel(item);
            binding.executePendingBindings();

        }

    }

    public static final DiffUtil.ItemCallback<ShopProductsData> DIFF_CALLBACK = new DiffUtil.ItemCallback<ShopProductsData>() {
        @Override
        public boolean areItemsTheSame(@NonNull ShopProductsData oldItem, @NonNull ShopProductsData newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ShopProductsData oldItem, @NonNull ShopProductsData newItem) {
            return areItemsTheSame(oldItem, newItem);
        }
    };
}
