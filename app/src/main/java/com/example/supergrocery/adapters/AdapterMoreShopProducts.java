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
import com.example.supergrocery.models.ShopProductsData;
import com.example.supergrocery.interfaces.ProductClickedInterface;
import com.example.supergrocery.other.Links;
import com.example.supergrocery.databinding.MoreProductsModelBinding;

import java.util.List;

public class AdapterMoreShopProducts extends ListAdapter<ShopProductsData,AdapterMoreShopProducts.ViewHolder> {
    Context context;

    public AdapterMoreShopProducts(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
 
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        MoreProductsModelBinding binding=MoreProductsModelBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ShopProductsData item = getItem(position);
        Glide.with(context).load(Links.categories_images+item.getImage()).into(holder.binding.imageviewMoreProducts);
        holder.binding.cardviewMoreProducts.setOnClickListener(view -> { ((ProductClickedInterface)context).productClicked(item);
        });
        holder.bind(item);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        MoreProductsModelBinding binding;

        public ViewHolder(MoreProductsModelBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
        public void bind(ShopProductsData item){
            binding.setMoreShopProductsModel(item);
            binding.executePendingBindings();
            
        }
    }public static final DiffUtil.ItemCallback<ShopProductsData> DIFF_CALLBACK = new DiffUtil.ItemCallback<ShopProductsData>() {
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
