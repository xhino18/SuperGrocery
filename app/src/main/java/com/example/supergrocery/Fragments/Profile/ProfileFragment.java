package com.example.supergrocery.Fragments.Profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.supergrocery.Login_RegisterAcitivity.LoginActivity;
import com.example.supergrocery.Other.SaveData;
import com.example.supergrocery.R;
import com.example.supergrocery.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    SaveData saveData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(inflater,container,false);
        View view=binding.getRoot();

        final EditProfileDialog alertDialog = new EditProfileDialog();
        saveData=new SaveData(getActivity());
        binding.buttonEditProfile.setOnClickListener(v -> alertDialog.showDialog(getActivity(),"Title"));
        binding.tvProfileName.setText(saveData.get_name());
        binding.tvProfileEmail.setText(saveData.get_email());
        binding.tvProfilePhone.setText(saveData.get_phone_number());
        binding.tvProfileNuis.setText(saveData.get_nuis());
        binding.ivLogout.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle(R.string.logout)
                    .setMessage(R.string.confirmation_logout)
                    .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> logout())
                    .setNegativeButton(android.R.string.no, null).show();



        });

    return view;
    }
    private void logout(){

        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        saveData.saveUserToken("");
        getActivity().finish();
    }


}