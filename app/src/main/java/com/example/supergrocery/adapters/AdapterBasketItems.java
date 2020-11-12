package com.example.supergrocery.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supergrocery.databinding.BasketItemModelBinding;
import com.example.supergrocery.fragments.BasketFragment;
import com.example.supergrocery.interfaces.AddOrRemoveBasketItem;
import com.example.supergrocery.room.OrderItem;

public class AdapterBasketItems extends ListAdapter<OrderItem, AdapterBasketItems.BasketViewHolder> {
    BasketFragment basketFragment;


    public AdapterBasketItems(BasketFragment basketFragment) {
        super(DIFF_CALLBACK);
        this.basketFragment = basketFragment;

    }

    @NonNull
    @Override
    public BasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        BasketItemModelBinding binding=BasketItemModelBinding.inflate(inflater,parent,false);
        return new BasketViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull BasketViewHolder holder, final int position) {
        OrderItem item=getItem(position);
        holder.binding.ivAddQuantity.setOnClickListener(v -> ((AddOrRemoveBasketItem)basketFragment).addClicked(item));
        holder.binding.ivRemoveQuantity.setOnClickListener(v -> ((AddOrRemoveBasketItem)basketFragment).removeClicked(item));
        holder.bind(item);

    }

    @Override
    public OrderItem getItem(int position) {
        return super.getItem(position);
    }

    public class BasketViewHolder extends RecyclerView.ViewHolder{
        BasketItemModelBinding binding;

        public BasketViewHolder(BasketItemModelBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
        public void bind(OrderItem item){
            binding.setBasket(item);
            binding.executePendingBindings();

        }
    }
    public static final DiffUtil.ItemCallback<OrderItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<OrderItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull OrderItem oldItem, @NonNull OrderItem newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull OrderItem oldItem, @NonNull OrderItem newItem) {
            return areItemsTheSame(oldItem, newItem);
        }
    };
}
