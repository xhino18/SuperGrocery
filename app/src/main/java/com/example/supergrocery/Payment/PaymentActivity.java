package com.example.supergrocery.Payment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
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
    ActivityPaymentBinding binding;
    Bundle bundle;
    Boolean doubleBackToExitPressedOnce=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);

        init();
    }

    private void init() {
        bundle=getIntent().getExtras();
        binding.spinnerCity.setOnItemSelectedListener(this);
        binding.spinnerCountry.setOnItemSelectedListener(this);

        if(bundle!=null) {
            if (bundle.getString("payment_type").equalsIgnoreCase("buy_now")) {
                int total = getIntent().getIntExtra("item_price",-1);
                binding.tvFinalTotal.setText(total+" ALL");
            } else {
                String total = getIntent().getStringExtra("getTotal");
                binding.tvFinalTotal.setText(total);
            }
        }

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
        binding.spinnerCity.setAdapter(cityAdapter);
        binding.spinnerCountry.setAdapter(countryAdapter);
        binding.buttonCancel.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        });
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
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit Payment", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}