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
import com.example.supergrocery.ModelsGet.GetProductByIDData;
import com.example.supergrocery.Other.Links;
import com.example.supergrocery.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class AdapterGetProductByID extends RecyclerView.Adapter<AdapterGetProductByID.ViewHolder> {
List<GetProductByIDData> getProductByIDData;
Context context;

    public AdapterGetProductByID( Context context,List<GetProductByIDData> getProductByIDData) {
        this.getProductByIDData = getProductByIDData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewholder = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_product_model, parent, false);
        return new AdapterGetProductByID.ViewHolder(viewholder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.products_item_name.setText(getProductByIDData.get(position).getName());
        holder.products_item_price.setText(Integer.toString(getProductByIDData.get(position).getPrice()));
        holder.products_item_description.setText(getProductByIDData.get(position).getDescription());
        Glide.with(context).load(Links.categories_images+getProductByIDData.get(position).getImage()).into(holder.imageview_products);
//        holder.button_add_to_cart.setOnClickListener(view -> {
//
//            ((AddSelectedProductInBasket)context).addSelectedProductInBasket(getProductByIDData.get(position));
//        });



    }

    @Override
    public int getItemCount() {
        return getProductByIDData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView products_item_name,products_item_price,products_item_description;
        MaterialButton button_add_to_cart,button_buy_now;
        ImageView imageview_products;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            products_item_name=itemView.findViewById(R.id.products_item_name);
            products_item_price=itemView.findViewById(R.id.products_item_price);
            products_item_description=itemView.findViewById(R.id.products_item_description);
            button_add_to_cart=itemView.findViewById(R.id.button_add_to_cart);
            imageview_products=itemView.findViewById(R.id.imageview_products);
            button_buy_now=itemView.findViewById(R.id.button_buy_now);
        }
    }
}
