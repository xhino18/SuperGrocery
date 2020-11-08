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
import com.example.supergrocery.models.DiscountedProductsData;
import com.example.supergrocery.other.Links;
import com.example.supergrocery.databinding.FragmentCategoriesModelBinding;

import java.util.List;

public class AdapterFragmentCategories extends ListAdapter<CategoriesData, AdapterFragmentCategories.ViewHolder> {
    Context context;

    public AdapterFragmentCategories(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentCategoriesModelBinding binding = FragmentCategoriesModelBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        CategoriesData item = getItem(position);
        Glide.with(context).load(Links.categories_images + item.getImage()).into(holder.binding.ivFragmentCategories);
        holder.binding.ivFragmentCategories.setOnClickListener(v -> ((ItemClickInterface) context).categoryClicked(item));
        holder.bind(item);

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        FragmentCategoriesModelBinding binding;

        public ViewHolder(FragmentCategoriesModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(CategoriesData item) {
            binding.setFragmentCategoriesModel(item);
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
