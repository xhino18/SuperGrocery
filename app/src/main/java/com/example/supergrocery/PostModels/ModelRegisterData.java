package com.example.supergrocery.PostModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelRegisterData {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("nuis")
    @Expose
    private String nuis;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("verified")
    @Expose
    private Boolean verified;
    @SerializedName("minimum_order")
    @Expose
    private Object minimumOrder;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNuis() {
        return nuis;
    }

    public void setNuis(String nuis) {
        this.nuis = nuis;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Object getMinimumOrder() {
        return minimumOrder;
    }

    public void setMinimumOrder(Object minimumOrder) {
        this.minimumOrder = minimumOrder;
    }
}
