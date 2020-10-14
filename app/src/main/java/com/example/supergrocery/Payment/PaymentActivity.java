package com.example.supergrocery.Payment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.supergrocery.databinding.ActivityPaymentBinding;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ActivityPaymentBinding activityPaymentBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPaymentBinding = ActivityPaymentBinding.inflate(getLayoutInflater());
        final View view = activityPaymentBinding.getRoot();
        setContentView(view);

        activityPaymentBinding.spinnerCity.setOnItemSelectedListener(this);
//        activityPaymentBinding.spinnerCountry.setOnItemSelectedListener(this);


        String total = getIntent().getStringExtra("getTotal");
        activityPaymentBinding.tvFinalTotal.setText(total);

        // Spinner Drop down elements
        List<String> cities = new ArrayList<String>();
        cities.add("Tirana");
        cities.add("Durrës");
        cities.add("Elbasan");
        cities.add("Education");
        cities.add("Vlorë");
        cities.add("Fier");

//        List<String> country = new ArrayList<String>();
//        country.add("Albania");

        // Creating adapter for spinner
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities);
//        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, country);

        // Drop down layout style - list view with radio button
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        activityPaymentBinding.spinnerCity.setAdapter(cityAdapter);
//        activityPaymentBinding.spinnerCity.setAdapter(countryAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }



}