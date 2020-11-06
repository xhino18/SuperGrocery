package com.example.supergrocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supergrocery.models.CategoriesData;
import com.example.supergrocery.interfaces.ItemClickInterface;
import com.example.supergrocery.other.Links;
import com.example.supergrocery.databinding.FragmentCategoriesModelBinding;

import java.util.List;

public class AdapterFragmentCategories extends RecyclerView.Adapter<AdapterFragmentCategories.ViewHolder> {
    Context context;
    List<CategoriesData>categoriesDataList;

    public AdapterFragmentCategories(Context context, List<CategoriesData> categoriesDataList) {
        this.context = context;
        this.categoriesDataList = categoriesDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        FragmentCategoriesModelBinding binding=FragmentCategoriesModelBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.binding.tvFragmentCategoriesProduct.setText(categoriesDataList.get(position).getName());
        Glide.with(context).load(Links.categories_images+categoriesDataList.get(position).getImage()).into(holder.binding.ivFragmentCategories);
        holder.binding.ivFragmentCategories.setOnClickListener(v -> ((ItemClickInterface)context).categoryClicked(categoriesDataList.get(position)));

    }

    @Override
    public int getItemCount() {
        return categoriesDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        FragmentCategoriesModelBinding binding;

        public ViewHolder(FragmentCategoriesModelBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
    }
}