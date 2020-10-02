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
import com.example.supergrocery.Interfaces.ItemClickInterface;
import com.example.supergrocery.Links;
import com.example.supergrocery.Models.ModelCategoriesData;
import com.example.supergrocery.R;

import java.util.List;

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.ViewHolder> {
    Context context;
    List<ModelCategoriesData> categoriesDataList;

    public AdapterCategories(Context context, List<ModelCategoriesData> categoriesDataList) {
        this.context = context;
        this.categoriesDataList = categoriesDataList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewholder = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_categories_model, parent, false);
        return new AdapterCategories.ViewHolder(viewholder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_category_product_name.setText(categoriesDataList.get(position).getName());
        Glide.with(context).load(Links.categories_images+categoriesDataList.get(position).getImage()).into(holder.iv_category_product);
        holder.iv_category_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ItemClickInterface)context).categoryClicked(categoriesDataList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoriesDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_category_product;
        TextView tv_category_product_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_category_product=itemView.findViewById(R.id.iv_category_product);
            tv_category_product_name=itemView.findViewById(R.id.tv_category_product_name);

        }
    }
}
