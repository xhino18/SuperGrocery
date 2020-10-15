package com.example.supergrocery.PostModels;

import com.google.gson.annotations.Expose;

public class Model_VerifyCode extends Model_Response{

    @Expose
    private UserRegisterData data;

    @Expose
    private String token;

    private Model_VerifyCode(String message) {
        this.error = true;
        this.message = message;
        this.data = null;
        this.token = null;
    }

    public static Model_VerifyCode error(Throwable error) {
        return new Model_VerifyCode(error.toString());
    }
}
