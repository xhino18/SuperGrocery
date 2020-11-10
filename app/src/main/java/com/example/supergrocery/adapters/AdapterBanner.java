package com.example.supergrocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supergrocery.models.BannerData;
import com.example.supergrocery.other.Links;
import com.example.supergrocery.databinding.BannerModelBinding;

public class AdapterBanner extends ListAdapter<BannerData, AdapterBanner.BannerViewHolder> {
    Context context;

    public AdapterBanner(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        BannerModelBinding binding = BannerModelBinding.inflate(inflater, parent, false);
        return new BannerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        BannerData item = getItem(position);
        Glide.with(context).load(Links.categories_images + item.getImage()).into(holder.binding.imageviewDiscountProduct);
        holder.bind(item);

    }

    public static class BannerViewHolder extends RecyclerView.ViewHolder {
        BannerModelBinding binding;

        public BannerViewHolder(BannerModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(BannerData item) {
            binding.setBanner(item);
        }
    }

    public static final DiffUtil.ItemCallback<BannerData> DIFF_CALLBACK = new DiffUtil.ItemCallback<BannerData>() {
        @Override
        public boolean areItemsTheSame(@NonNull BannerData oldItem, @NonNull BannerData newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull BannerData oldItem, @NonNull BannerData newItem) {
            return areItemsTheSame(oldItem, newItem);
        }
    };
}
