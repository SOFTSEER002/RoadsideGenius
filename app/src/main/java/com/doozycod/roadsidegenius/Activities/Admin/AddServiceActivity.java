package com.doozycod.roadsidegenius.Activities.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.doozycod.roadsidegenius.Model.AdminRegisterModel;
import com.doozycod.roadsidegenius.R;
import com.doozycod.roadsidegenius.Service.ApiService;
import com.doozycod.roadsidegenius.Service.ApiUtils;
import com.doozycod.roadsidegenius.Utils.CustomProgressBar;
import com.doozycod.roadsidegenius.Utils.SharedPreferenceMethod;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddServiceActivity extends AppCompatActivity {
    EditText serviceTypeET, costET, descriptionET;
    Button addServiceButton;

    private SharedPreferenceMethod sharedPreferenceMethod;
    private ApiService apiService;
    private CustomProgressBar customProgressBar;
    Toolbar toolbar;

    void initUI() {
        addServiceButton = findViewById(R.id.addServiceButton);
        costET = findViewById(R.id.costET);
        serviceTypeET = findViewById(R.id.serviceTypeET);
        descriptionET = findViewById(R.id.descriptionET);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        sharedPreferenceMethod = new SharedPreferenceMethod(this);
        customProgressBar = new CustomProgressBar(this);
        apiService = ApiUtils.getAPIService();

        initUI();

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        addServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (serviceTypeET.getText().toString().equals("")) {
                    Toast.makeText(AddServiceActivity.this, "Please Enter Service Type", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (costET.getText().toString().equals("")) {
                    Toast.makeText(AddServiceActivity.this, "Please enter service cost", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    addServiceAPI(serviceTypeET.getText().toString(), costET.getText().toString(), descriptionET.getText().toString());
                }
            }
        });
    }

    void addServiceAPI(String type, String cost, String description) {
        customProgressBar.showProgress();
        apiService.addService(sharedPreferenceMethod.getJWTToken(), type, cost, description).enqueue(new Callback<AdminRegisterModel>() {
            @Override
            public void onResponse(Call<AdminRegisterModel> call, Response<AdminRegisterModel> response) {
                customProgressBar.hideProgress();
                if (response.body().getResponse().getStatus().equals("Success")) {
                    Toast.makeText(AddServiceActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddServiceActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<AdminRegisterModel> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}