package com.doozycod.roadsidegenius.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.doozycod.roadsidegenius.MainActivity;
import com.doozycod.roadsidegenius.R;

import java.util.ArrayList;
import java.util.List;

public class DriverLoginActvity extends AppCompatActivity {
    Spinner spinner;
    List<String> vendors = new ArrayList<>();
    Button signinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login_actvity);
        signinButton = findViewById(R.id.signinButton);
        spinner = findViewById(R.id.vendorIdSpinner);

        vendors.add("No Vendors Found!");

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DriverLoginActvity.this, MainActivity.class));
            }
        });
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, vendors);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}