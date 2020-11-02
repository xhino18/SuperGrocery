package com.example.supergrocery.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supergrocery.Models.BannerData;
import com.example.supergrocery.Other.Links;
import com.example.supergrocery.databinding.BannerModelBinding;

import java.util.List;

public class AdapterBanner extends RecyclerView.Adapter<AdapterBanner.ViewHolder> {

    Context context;
    List<BannerData> bannerData;

    public AdapterBanner(Context context, List<BannerData> bannerData) {
        this.context = context;
        this.bannerData = bannerData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        BannerModelBinding binding=BannerModelBinding.inflate(inflater,parent,false);
        return new AdapterBanner.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(Links.categories_images+bannerData.get(position).getImage()).into(holder.binding.imageviewDiscountProduct);

    }

    @Override
    public int getItemCount() {
        return bannerData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        BannerModelBinding binding;


        public ViewHolder(BannerModelBinding binding) {
            super(binding.getRoot());
            this.binding=binding;


        }
    }
}
