package com.example.supergrocery.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supergrocery.GetModels.CategoriesData;
import com.example.supergrocery.Interfaces.ItemClickInterface;
import com.example.supergrocery.Other.Links;
import com.example.supergrocery.R;

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
        View viewholder = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_categories_model, parent, false);
        return new AdapterFragmentCategories.ViewHolder(viewholder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_fragment_categories_product.setText(categoriesDataList.get(position).getName());
        Glide.with(context).load(Links.categories_images+categoriesDataList.get(position).getImage()).into(holder.iv_fragment_categories);
        holder.iv_fragment_categories.setOnClickListener(v -> ((ItemClickInterface)context).categoryClicked(categoriesDataList.get(position)));

    }

    @Override
    public int getItemCount() {
        return categoriesDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_fragment_categories_product;
        ImageView iv_fragment_categories;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_fragment_categories=itemView.findViewById(R.id.iv_fragment_categories);
            tv_fragment_categories_product=itemView.findViewById(R.id.tv_fragment_categories_product);
        }
    }
}
