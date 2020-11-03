package com.example.supergrocery.Fragments.Profile;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.Models.ModelMainToken;
import com.example.supergrocery.Models.UserRegisterData;
import com.example.supergrocery.Other.SaveData;
import com.example.supergrocery.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileDialog {
    Gson gson;
    Dialog dialog;
    EditText tv_profile_name,tv_profile_email,tv_profile_nuis,tv_profile_phone;
    SaveData saveData;
    String name,email,nuis;

    public void showDialog(Activity activity) {


        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.edit_profile_model);
        saveData=new SaveData(dialog.getContext());

        gson=new GsonBuilder().create();
        tv_profile_name=dialog.findViewById(R.id.tv_profile_name);
        tv_profile_email=dialog.findViewById(R.id.tv_profile_email);
        tv_profile_nuis=dialog.findViewById(R.id.tv_profile_nuis);
        tv_profile_phone=dialog.findViewById(R.id.tv_profile_phone);

        tv_profile_name.setText(saveData.get_name());
        tv_profile_email.setText(saveData.get_email());
        tv_profile_nuis.setText(saveData.get_nuis());

        name=tv_profile_name.getText().toString();
        email=tv_profile_email.getText().toString();
        nuis=tv_profile_nuis.getText().toString();

        Button button_edit_profile_cancel = (Button) dialog.findViewById(R.id.button_edit_profile_cancel);
        button_edit_profile_cancel.setOnClickListener(v -> dialog.dismiss());
        Button button_edit_profile_confirm = (Button) dialog.findViewById(R.id.button_edit_profile_confirm);
        button_edit_profile_confirm.setOnClickListener(v -> {
            editProfileInfo(saveData.getToken());
            dialog.dismiss();
            Toast.makeText(dialog.getContext(), "Profile updated!", Toast.LENGTH_SHORT).show();

        });

        dialog.show();

    }
    public void editProfileInfo(String token){
        API apiClient = ClientAPI.createAPI_With_Token(token);
        retrofit2.Call<ModelMainToken<UserRegisterData>> call = apiClient.editProfile(name,email,nuis);
        call.enqueue(new Callback<ModelMainToken<UserRegisterData>>() {
            @Override
            public void onResponse(retrofit2.Call<ModelMainToken<UserRegisterData>> call, Response<ModelMainToken<UserRegisterData>> response) {
                System.out.println("respoonseeee "+ gson.toJson(response.body()));
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")){
                    if (!response.body().getError()){
                        saveData.save_user_info(
                                tv_profile_name.getText().toString(),
                                tv_profile_email.getText().toString(),
                                tv_profile_nuis.getText().toString(),
                                saveData.get_phone_number());

                    } else {
                        Toast.makeText(dialog.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(dialog.getContext(), "oppssss", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(dialog.getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ModelMainToken<UserRegisterData>> call, Throwable t) {
                Log.e("test", t.toString());

            }
        });
    }
}