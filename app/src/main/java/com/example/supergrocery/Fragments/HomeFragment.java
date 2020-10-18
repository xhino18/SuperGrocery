package com.example.supergrocery.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.supergrocery.MainActivity;
import com.example.supergrocery.R;


public class HomeFragment extends Fragment {
    private void go(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        go();
        final View view =  inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }



}