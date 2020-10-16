package com.example.supergrocery.Other;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.Fragments.ProfileFragment;
import com.example.supergrocery.Login_RegisterAcitivity.LoginCodeActivity;
import com.example.supergrocery.Login_RegisterAcitivity.RegisterActivity;
import com.example.supergrocery.MainActivity2;
import com.example.supergrocery.PostModels.ModelRegister;
import com.example.supergrocery.PostModels.UserRegister;
import com.example.supergrocery.R;
import com.example.supergrocery.databinding.EditProfileModelBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileDialog {
    Gson gson;
    Dialog dialog;
    Context context;
    EditText tv_profile_name,tv_profile_email,tv_profile_nuis,tv_profile_phone;
    SaveData saveData;
    String name,email,nuis;

    public void showDialog(Activity activity, String msg) {


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

        tv_profile_name.setText(saveData.get_Name());
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
//            dialog.getContext().startActivity(intent);


        });

        dialog.show();

    }
    public void editProfileInfo(String token){
        API apiClient = ClientAPI.createAPI_With_Token(token);
        retrofit2.Call<UserRegister> call = apiClient.editProfile(name,email,nuis);
        call.enqueue(new Callback<UserRegister>() {
            @Override
            public void onResponse(retrofit2.Call<UserRegister> call, Response<UserRegister> response) {
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
            public void onFailure(retrofit2.Call<UserRegister> call, Throwable t) {
                Log.e("test", t.toString());

            }
        });
    }
}
