package com.example.supergrocery.ROOM;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "OrderItems",


        indices = {@Index("id")}
)
public class OrderItemsModel {

    @PrimaryKey(autoGenerate = true)
    private Long unique_id;

    @NonNull
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "url_image")
    private String urlImage;

    @ColumnInfo(name = "price")
    private Integer price;

    @ColumnInfo(name = "quantity")
    private Integer quantity;

    public OrderItemsModel(Long unique_id, @NonNull Integer id, String name, String urlImage, Integer price, Integer quantity) {
        this.unique_id = unique_id;
        this.id = id;
        this.name = name;
        this.urlImage = urlImage;
        this.price = price;
        this.quantity = quantity;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    public void reductionQuantity() {
        this.quantity--;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }


    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public Long getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(Long unique_id) {
        this.unique_id = unique_id;
    }

}
