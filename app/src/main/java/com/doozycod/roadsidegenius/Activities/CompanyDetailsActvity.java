package com.doozycod.roadsidegenius.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.doozycod.roadsidegenius.Model.Company.Company;
import com.doozycod.roadsidegenius.R;

import java.util.ArrayList;
import java.util.List;

public class CompanyDetailsActvity extends AppCompatActivity {

    EditText nameET, emailET, numberET, vendorIdET, driverAddressET, companyCityET, stateET, zipcodeET,
            primaryServiceET;
    //    Spinner w9FormSpinner, l9formSpinner;
    List<String> list = new ArrayList<>();
    TextView selectfile, selectfile2, selectfile3, primaryNumberET, secondaryNumberET;
    Company company;
    Toolbar toolbar;

    void initUI() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        secondaryNumberET = findViewById(R.id.secondaryNumberET);
        primaryNumberET = findViewById(R.id.primaryNumberET);
        primaryServiceET = findViewById(R.id.primaryServiceET);
        zipcodeET = findViewById(R.id.zipcodeET);
        stateET = findViewById(R.id.stateET);
        companyCityET = findViewById(R.id.companyCityET);
        driverAddressET = findViewById(R.id.driverAddressET);
        selectfile3 = findViewById(R.id.selectfile3);
        selectfile = findViewById(R.id.selectfile);
        selectfile2 = findViewById(R.id.selectfile2);
        nameET = findViewById(R.id.nameET);
        emailET = findViewById(R.id.emailET);
        numberET = findViewById(R.id.numberET);
        vendorIdET = findViewById(R.id.vendorET);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details_screen);
        initUI();

        if (getIntent() != null) {
            company = (Company) getIntent().getExtras().getSerializable("company");
            selectfile3.setText(company.getCoi());
            selectfile2.setText(company.getW9Forms());
            selectfile.setText(company.getI9Forms());
            secondaryNumberET.setText("+" + company.getSecondaryPhone());
            primaryNumberET.setText("+" + company.getPrimaryPhone());
            primaryServiceET.setText(company.getPrimaryServiceArea());
            zipcodeET.setText(company.getCompanyZipcode());
            companyCityET.setText(company.getCompanyCity());
            stateET.setText(company.getCompanyState());
            driverAddressET.setText(company.getCompanyAddress());
            nameET.setText(company.getCompanyName());
            numberET.setText(company.getCompanyNumber());
            emailET.setText(company.getCompanyEmail());

        }
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