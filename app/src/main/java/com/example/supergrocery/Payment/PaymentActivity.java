package com.example.supergrocery.Payment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.supergrocery.R;
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);

        activityPaymentBinding.spinnerCity.setOnItemSelectedListener(this);
        activityPaymentBinding.spinnerCountry.setOnItemSelectedListener(this);


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
        cities.add("Tirana1");
        cities.add("Durrës1");
        cities.add("Elbasan1");
        cities.add("Education1");
        cities.add("Vlorë1");
        cities.add("Fier1");
        cities.add("Tirana2");
        cities.add("Durrës2");
        cities.add("Elbasan2");
        cities.add("Education2");
        cities.add("Vlorë2");
        cities.add("Fier2");
        cities.add("Tirana3");
        cities.add("Durrës3");
        cities.add("Elbasan3");
        cities.add("Education3");
        cities.add("Vlorë3");
        cities.add("Fier3");


        List<String> country = new ArrayList<String>();
        country.add("Albania");

        // Creating adapter for spinner
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities);
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, country);

        // Drop down layout style - list view with radio button
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        activityPaymentBinding.spinnerCity.setAdapter(cityAdapter);
        activityPaymentBinding.spinnerCountry.setAdapter(countryAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
//        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }
}