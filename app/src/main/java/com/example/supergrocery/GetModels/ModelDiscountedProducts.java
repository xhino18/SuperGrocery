package com.example.supergrocery.GetModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelDiscountedProducts {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<ModelDiscountedProductsData> data = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ModelDiscountedProductsData> getData() {
        return data;
    }

    public void setData(List<ModelDiscountedProductsData> data) {
        this.data = data;
    }
}
