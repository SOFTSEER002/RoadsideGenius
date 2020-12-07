package com.doozycod.roadsidegenius;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;

import com.doozycod.roadsidegenius.Activities.DriverLoginActvity;

public class LoginTypeActvvity extends AppCompatActivity {
    Button driverButton,customerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type_actvvity);
        driverButton = findViewById(R.id.driverButton);
        customerButton = findViewById(R.id.customerButton);
        customerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginTypeActvvity.this, RequestActivity.class));
            }
        });  driverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginTypeActvvity.this, DriverLoginActvity.class));
            }
        });
    }
}