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
import com.example.supergrocery.interfaces.ItemClickInterface;
import com.example.supergrocery.models.CategoriesData;
import com.example.supergrocery.other.Links;
import com.example.supergrocery.databinding.MainSearchModelBinding;

import java.util.List;

public class AdapterSearchedProduct extends ListAdapter<CategoriesData,AdapterSearchedProduct.ViewHolder> {
    Context context;

    public AdapterSearchedProduct(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
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
        CategoriesData item= getItem(position);
        holder.binding.ivSearchedItem.setOnClickListener(v -> ((ItemClickInterface)context).categoryClicked(item));
        holder.binding.tvSearchedName.setOnClickListener(v -> ((ItemClickInterface)context).categoryClicked(item));
        holder.binding.tvSearchedDescription.setOnClickListener(v -> ((ItemClickInterface)context).categoryClicked(item));
        holder.binding.tvEmpty.setOnClickListener(v -> ((ItemClickInterface)context).categoryClicked(item));
        holder.bind(item);

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        MainSearchModelBinding binding;

        public ViewHolder(MainSearchModelBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
        private void bind(CategoriesData item){
            binding.setSearchedProductModel(item);
            binding.executePendingBindings();
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
