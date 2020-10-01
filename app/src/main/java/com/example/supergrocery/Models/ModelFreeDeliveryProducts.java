package com.example.supergrocery.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelFreeDeliveryProducts {


    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<ModelFreeDeliveryProductsData> data = null;

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

    public List<ModelFreeDeliveryProductsData> getData() {
        return data;
    }

    public void setData(List<ModelFreeDeliveryProductsData> data) {
        this.data = data;
    }
}
