package com.doozycod.roadsidegenius.PushNotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.doozycod.roadsidegenius.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomerDetailsActivity extends AppCompatActivity {
    TextView etaET, siteET, vehicleMakeEt, vehicleModelEt, vehicleColor, total_job_time, total_milesET, invoiceTotal,
            truckET, descriptionET, dispatchedTime, dispatchDateTxt, driverName;
    TextView fullNameET, customerEmailET, amount_quoted, notesET, contactNumberTxt;
    TextView getLocationET, getDropOffLocation;
    Toolbar toolbar;

    private void initUI() {
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        notesET = findViewById(R.id.notesET);
        amount_quoted = findViewById(R.id.amount_quoted);
        customerEmailET = findViewById(R.id.customerEmailET);
        contactNumberTxt = findViewById(R.id.contactNumberTxt);
        fullNameET = findViewById(R.id.fullNameET);
        getLocationET = findViewById(R.id.getPickupLocationET);
        getDropOffLocation = findViewById(R.id.getDropOffLocationET);
        vehicleModelEt = findViewById(R.id.vehicleModelEt);
        descriptionET = findViewById(R.id.descriptionET);
        truckET = findViewById(R.id.truckET);
        invoiceTotal = findViewById(R.id.invoiceTotal);
        total_milesET = findViewById(R.id.total_milesET);
        total_job_time = findViewById(R.id.total_job_time);
        vehicleColor = findViewById(R.id.vehicleColor);
        vehicleMakeEt = findViewById(R.id.vehicleMakeEt);
        siteET = findViewById(R.id.siteET);
        etaET = findViewById(R.id.etaET);
        dispatchedTime = findViewById(R.id.dispatchedTime);
        dispatchDateTxt = findViewById(R.id.dispatchDateTxt);
        driverName = findViewById(R.id.driverSpinner);
    }

    String jsonString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        initUI();
        jsonString = getIntent().getStringExtra("jsonObject");
        Log.e("TAG", "onCreate: " + jsonString + " //1");
        if (getIntent().hasExtra("jsonObject")) {
            if (getIntent() != null) {

                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
//            amount_quoted.setText(jsonObject.getString("amount_quoted"));
                    etaET.setText(jsonObject.getString("eta"));
                    fullNameET.setText(jsonObject.getString("customer_name"));
                    dispatchDateTxt.setText(jsonObject.getString("dispatch_date"));
                    customerEmailET.setText(jsonObject.getString("customer_email"));
                    vehicleMakeEt.setText(jsonObject.getString("vehicle_make"));
                    notesET.setText(jsonObject.getString("comments"));
                    total_milesET.setText(jsonObject.getString("total_miles"));
                    getDropOffLocation.setText(jsonObject.getString("customer_dropoff"));
                    siteET.setText(jsonObject.getString("site"));
                    truckET.setText(jsonObject.getString("truck"));
                    invoiceTotal.setText(jsonObject.getString("invoice_total"));
                    driverName.setText(jsonObject.getString("driver_name"));
                    contactNumberTxt.setText("+" + jsonObject.getLong("customer_number"));
                    getLocationET.setText(jsonObject.getString("customer_pickup"));
                    vehicleColor.setText(jsonObject.getString("vehicle_color"));
                    vehicleModelEt.setText(jsonObject.getString("vehicle_model"));
                    total_job_time.setText(jsonObject.getString("total_job_time"));
                    dispatchedTime.setText(jsonObject.getString("dipatch"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
        if (getIntent().hasExtra("customer_name")) {

            etaET.setText(getIntent().getStringExtra("eta"));
            fullNameET.setText(getIntent().getStringExtra("customer_name"));
            dispatchDateTxt.setText(getIntent().getStringExtra("dispatch_date"));
            customerEmailET.setText(getIntent().getStringExtra("customer_email"));
            vehicleMakeEt.setText(getIntent().getStringExtra("vehicle_make"));
            notesET.setText(getIntent().getStringExtra("comments"));
            total_milesET.setText(getIntent().getStringExtra("total_miles"));
            getDropOffLocation.setText(getIntent().getStringExtra("customer_dropoff"));
            siteET.setText(getIntent().getStringExtra("site"));
            truckET.setText(getIntent().getStringExtra("truck"));
            invoiceTotal.setText(getIntent().getStringExtra("invoice_total"));
            driverName.setText(getIntent().getStringExtra("driver_name"));
            contactNumberTxt.setText("+" + getIntent().getStringExtra("customer_number"));
            getLocationET.setText(getIntent().getStringExtra("customer_pickup"));
            vehicleColor.setText(getIntent().getStringExtra("vehicle_color"));
            vehicleModelEt.setText(getIntent().getStringExtra("vehicle_model"));
            total_job_time.setText(getIntent().getStringExtra("total_job_time"));
            dispatchedTime.setText(getIntent().getStringExtra("dipatch"));


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