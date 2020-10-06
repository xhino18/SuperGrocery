package com.example.supergrocery.Other;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supergrocery.Fragments.ProfileFragment;
import com.example.supergrocery.R;

public class EditProfileDialog {
    Dialog dialog;
    Context context;


    public void showDialog(Activity activity, String msg) {


        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.edit_profile_model);

        Button button_edit_profile_cancel = (Button) dialog.findViewById(R.id.button_edit_profile_cancel);
        button_edit_profile_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button button_edit_profile_confirm = (Button) dialog.findViewById(R.id.button_edit_profile_confirm);
        button_edit_profile_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        dialog.show();

    }
}
