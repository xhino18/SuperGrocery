package com.example.supergrocery.other;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.supergrocery.R;

public class Utils_Binding {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    @BindingAdapter(value = {"price", "price_prefix", "price_bold", "unit", "free"}, requireAll = false)
    public static void setPrice(TextView view, Integer price, String prefix, boolean isBold, String unit, String free) {
        if (price != null && price >= 0) {
            view.setText((new SpannableStringBuilder())
                    .append(prefix != null ? prefix : "")
                    .append(price == 0 && free != null ? free :
                                    price + view.getResources().getString(R.string.suffix_currency),
                            new StyleSpan(isBold ? Typeface.BOLD : Typeface.NORMAL), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    .append(unit != null ? " / " + unit : ""));
        }
    }

    @BindingAdapter("layout_width")
    public static void setLayoutWidth(View view, float width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) width;
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter(value = {"image", "error"}, requireAll = false)
    public static void loadImage(ImageView view, String url, Drawable error) {
        Glide.with(view.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(error)
                .into(view);
    }
}
