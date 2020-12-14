package com.doozycod.roadsidegenius.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.doozycod.roadsidegenius.Activities.Admin.AdminLoginActivity;
import com.doozycod.roadsidegenius.Activities.Customer.CustomerLoginActivity;
import com.doozycod.roadsidegenius.Activities.Driver.DriverLoginActvity;
import com.doozycod.roadsidegenius.R;

public class LoginTypeActvvity extends AppCompatActivity {
    Button driverButton, customerButton, adminButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type_actvvity);
        adminButton = findViewById(R.id.adminButton);
        driverButton = findViewById(R.id.driverButton);
        customerButton = findViewById(R.id.customerButton);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginTypeActvvity.this, AdminLoginActivity.class));
            }
        });
        customerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginTypeActvvity.this, CustomerLoginActivity.class));
            }
        });
        driverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginTypeActvvity.this, DriverLoginActvity.class));
            }
        });
    }
}