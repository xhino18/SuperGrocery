package com.example.supergrocery.GetModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelShopProductsData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("stock")
    @Expose
    private Integer stock;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("sizes")
    @Expose
    private String sizes;
    @SerializedName("minimum_order")
    @Expose
    private Integer minimumOrder;
    @SerializedName("maximum_order")
    @Expose
    private Integer maximumOrder;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("discounted_price")
    @Expose
    private Integer discountedPrice;
    @SerializedName("prices")
    @Expose
    private List<Object> prices = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public Integer getMinimumOrder() {
        return minimumOrder;
    }

    public void setMinimumOrder(Integer minimumOrder) {
        this.minimumOrder = minimumOrder;
    }

    public Integer getMaximumOrder() {
        return maximumOrder;
    }

    public void setMaximumOrder(Integer maximumOrder) {
        this.maximumOrder = maximumOrder;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(Integer discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public List<Object> getPrices() {
        return prices;
    }

    public void setPrices(List<Object> prices) {
        this.prices = prices;
    }
}
