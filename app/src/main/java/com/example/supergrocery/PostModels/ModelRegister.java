package com.example.supergrocery.PostModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelRegister {

        @SerializedName("error")
        @Expose
        private Boolean error;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("data")
        @Expose
        private ModelRegisterData data;

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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public ModelRegisterData getData() {
            return data;
        }

        public void setData(ModelRegisterData data) {
            this.data = data;
        }
}
