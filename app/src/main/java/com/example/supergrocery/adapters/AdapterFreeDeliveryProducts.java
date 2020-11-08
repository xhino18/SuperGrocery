package com.example.supergrocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supergrocery.models.AllProductsData;
import com.example.supergrocery.interfaces.ItemClickInterface;
import com.example.supergrocery.models.AllProductsData;
import com.example.supergrocery.other.Links;
import com.example.supergrocery.databinding.FreeDeliveryProductsModelBinding;

import java.util.List;

public class AdapterFreeDeliveryProducts extends ListAdapter<AllProductsData,AdapterFreeDeliveryProducts.ViewHolder> {
    Context context;

    public AdapterFreeDeliveryProducts(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        FreeDeliveryProductsModelBinding binding=FreeDeliveryProductsModelBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllProductsData item=getItem(position);
        Glide.with(context).load(Links.categories_images+item.getImage()).into(holder.binding.imageviewFreeDeliveryProduct);
        holder.binding.cardviewFreeDeliveryProducts.setOnClickListener(v -> { ((ItemClickInterface)context).freeDeliveryClicked(item);
        holder.bind(item);
        });

    }
    
    public class ViewHolder extends RecyclerView.ViewHolder{
        FreeDeliveryProductsModelBinding binding;

        public ViewHolder(FreeDeliveryProductsModelBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }

        public void bind(AllProductsData item) {
            binding.setFreeDeliveryProducts(item);
            binding.executePendingBindings();
        }
    }
    public static final DiffUtil.ItemCallback<AllProductsData> DIFF_CALLBACK = new DiffUtil.ItemCallback<AllProductsData>() {
        @Override
        public boolean areItemsTheSame(@NonNull AllProductsData oldItem, @NonNull AllProductsData newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull AllProductsData oldItem, @NonNull AllProductsData newItem) {
            return areItemsTheSame(oldItem, newItem);
        }
    };
}
