package com.example.supergrocery.Other;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveData {
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private static final String LOGIN_TOKEN = "Token";

    public SaveData(Context context) {
        this.context = context;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor = this.preferences.edit();
        this.editor.commit();
    }

    public void saveUserToken(String token) {
        this.editor.putString("Token", token);
        this.editor.commit();
    }

    public String getToken() {
        return this.preferences.getString("Token", "");
    }

    public void saveBussinessID(Integer businesId) {
        this.editor.putInt("businessId", businesId);
        this.editor.commit();
    }

    public Integer getBussinesId() {
        return this.preferences.getInt("businessId", -1);
    }

    public void clearAll() {
        this.editor.clear();
        this.editor.commit();
    }
    public void save_user_info(String name, String email, String nuis,String phone_number) {
        editor.putString("user_name", name);
        editor.putString("user_email", email);
        editor.putString("user_nuis", nuis);
        editor.putString("user_phone_number", phone_number);
        editor.commit();
    }

}