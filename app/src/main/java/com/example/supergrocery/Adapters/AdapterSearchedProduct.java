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
import com.example.supergrocery.ModelsGet.CategoriesData;
import com.example.supergrocery.Interfaces.ItemClickInterface;
import com.example.supergrocery.Other.Links;
import com.example.supergrocery.R;

import java.util.List;

public class AdapterSearchedProduct extends RecyclerView.Adapter<AdapterSearchedProduct.ViewHolder> {
    Context context;
    List<CategoriesData> categoriesData;

    public AdapterSearchedProduct(Context context, List<CategoriesData> categoriesData) {
        this.context = context;
        this.categoriesData = categoriesData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewholder = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_search_model, parent, false);
        return new AdapterSearchedProduct.ViewHolder(viewholder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_searched_name.setText(categoriesData.get(position).getName());
        Glide.with(context).load(Links.categories_images+categoriesData.get(position).getImage()).into(holder.iv_searched_item);
        holder.iv_searched_item.setOnClickListener(v -> ((ItemClickInterface)context).categoryClicked(categoriesData.get(position)));
        holder.tv_searched_name.setOnClickListener(v -> ((ItemClickInterface)context).categoryClicked(categoriesData.get(position)));
        holder.tv_searched_description.setOnClickListener(v -> ((ItemClickInterface)context).categoryClicked(categoriesData.get(position)));
        holder.tv_empty.setOnClickListener(v -> ((ItemClickInterface)context).categoryClicked(categoriesData.get(position)));


    }

    @Override
    public int getItemCount() {
        return categoriesData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_searched_item;
        TextView tv_searched_name,tv_searched_description,tv_empty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_searched_item=itemView.findViewById(R.id.iv_searched_item);
            tv_searched_name=itemView.findViewById(R.id.tv_searched_name);
            tv_searched_description=itemView.findViewById(R.id.tv_searched_description);
            tv_empty=itemView.findViewById(R.id.tv_empty);

        }
    }
}
