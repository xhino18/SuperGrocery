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
import com.example.supergrocery.models.DiscountedProductsData;
import com.example.supergrocery.interfaces.ItemClickInterface;
import com.example.supergrocery.other.Links;
import com.example.supergrocery.databinding.MoreProductsModelBinding;

import java.util.List;

public class AdapterMoreDiscountedProducts extends ListAdapter<DiscountedProductsData, AdapterMoreDiscountedProducts.ViewHolder> {
    Context context;

    public AdapterMoreDiscountedProducts(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        MoreProductsModelBinding binding=MoreProductsModelBinding.inflate(inflater,parent,false);
        return new AdapterMoreDiscountedProducts.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        DiscountedProductsData item = getItem(position);
        Glide.with(context).load(Links.categories_images+item.getImage()).into(holder.binding.imageviewMoreProducts);
        holder.binding.cardviewMoreProducts.setOnClickListener(view -> { ((ItemClickInterface)context).dicountedProductsClicked(item);
        });
        holder.bind(item);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        MoreProductsModelBinding binding;

        public ViewHolder(MoreProductsModelBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
        public void bind(DiscountedProductsData item){
            binding.setMoreDiscountedProductsModel(item);
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
