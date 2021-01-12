package com.doozycod.roadsidegenius.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.doozycod.roadsidegenius.Activities.Admin.AssignJobActivity;
import com.doozycod.roadsidegenius.Adapter.DriversListAdapter;
import com.doozycod.roadsidegenius.Model.AdminRegisterModel;
import com.doozycod.roadsidegenius.Model.DriverList.Driver;
import com.doozycod.roadsidegenius.Model.DriverList.DriversListModel;
import com.doozycod.roadsidegenius.Model.ServiceList.Service;
import com.doozycod.roadsidegenius.Model.ServiceList.ServiceListModel;
import com.doozycod.roadsidegenius.R;
import com.doozycod.roadsidegenius.Service.ApiService;
import com.doozycod.roadsidegenius.Service.ApiUtils;
import com.doozycod.roadsidegenius.Utils.CustomProgressBar;
import com.doozycod.roadsidegenius.Utils.SharedPreferenceMethod;
import com.hbb20.CountryCodePicker;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminCreateJobFragment extends Fragment {

    public AdminCreateJobFragment() {
        // Required empty public constructor
    }

    EditText siteET, vehicleMakeEt, vehicleModelEt, vehicleColor, total_job_time, total_milesET, invoiceTotal,
            truckET, descriptionET;
    TextView etaET, dispatchedTime, dispatchDateTxt;
    EditText fullNameET, customerEmailET, notesET, contactNumberTxt;
    EditText getLocationET, getDropOffLocation;
    Spinner serviceSpinner, driverSpinner;
    List<String> driverIdList = new ArrayList<>();
    List<String> driverNameList = new ArrayList<>();
    List<String> serviceIdList = new ArrayList<>();
    List<String> serviceNameList = new ArrayList<>();
    List<Driver> driverList = new ArrayList<>();
    List<Service> serviceList = new ArrayList<>();

    ApiService apiService;
    SharedPreferenceMethod sharedPreferenceMethod;

    DatePickerDialog datePickerDialog;
    ImageView datePickerDialogButton;
    final Calendar myCalendar = Calendar.getInstance();
    String myFormat = "MM-dd-yyyy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    TimePickerDialog timePickerDialog, timePickerDialog2;
    RadioGroup radioGroup;
    RadioButton asapRadio, scheduledRadio;
    Button createButton;

    void initUI(View view) {
        countryCodePicker = view.findViewById(R.id.countryCodeLogin);
        datePickerDialogButton = view.findViewById(R.id.datePickerDialogButton);
        createButton = view.findViewById(R.id.createButton);
        scheduledRadio = view.findViewById(R.id.scheduledRadio);
        asapRadio = view.findViewById(R.id.asapRadio);
        radioGroup = view.findViewById(R.id.radioGroup);
        serviceSpinner = view.findViewById(R.id.serviceSpinner);
        notesET = view.findViewById(R.id.descriptionET);
        customerEmailET = view.findViewById(R.id.customerEmailET);
        contactNumberTxt = view.findViewById(R.id.contactNumberTxt);
        fullNameET = view.findViewById(R.id.fullNameET);
        getLocationET = view.findViewById(R.id.getPickupLocationET);
        getDropOffLocation = view.findViewById(R.id.getDropOffLocationET);
        vehicleModelEt = view.findViewById(R.id.vehicleModelEt);
//        descriptionET = view.findViewById(R.id.descriptionET);
        truckET = view.findViewById(R.id.truckET);
        invoiceTotal = view.findViewById(R.id.invoiceTotal);
        total_milesET = view.findViewById(R.id.total_milesET);
        total_job_time = view.findViewById(R.id.total_job_time);
        vehicleColor = view.findViewById(R.id.vehicleColor);
        vehicleMakeEt = view.findViewById(R.id.vehicleMakeEt);
        siteET = view.findViewById(R.id.siteET);
        etaET = view.findViewById(R.id.etaET);
        dispatchedTime = view.findViewById(R.id.dispatchedTime);
        dispatchDateTxt = view.findViewById(R.id.dispatchDateTxt);
        driverSpinner = view.findViewById(R.id.driverSpinner);
    }

    CustomProgressBar customProgressBar;
    CountryCodePicker countryCodePicker;
    String countryCode = "";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_create_job, container, false);
        initUI(view);
        apiService = ApiUtils.getAPIService();
        sharedPreferenceMethod = new SharedPreferenceMethod(getActivity());
        customProgressBar = new CustomProgressBar(getActivity());

        datePickerDialog = new DatePickerDialog(getActivity());

//        radio button for select current time or scheduled a time
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (asapRadio.isChecked()) {
                    dispatchedTime.setEnabled(false);

                    dispatchDateTxt.setText(sdf.format(new Date()));
                    int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                    int minute = myCalendar.get(Calendar.MINUTE);
                    dispatchedTime.setText(hour + ":" + minute);
                }
                if (scheduledRadio.isChecked()) {
                    dispatchedTime.setEnabled(true);
                }
            }
        });
        etaET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                int minute = myCalendar.get(Calendar.MINUTE);
                timePickerDialog2 = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        etaET.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                timePickerDialog2.show();
            }
        });
        datePickerDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        dispatchDateTxt.setText(sdf.format(new Date()));
        int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = myCalendar.get(Calendar.MINUTE);
        dispatchedTime.setText(hour + ":" + minute);

//  country Code
        countryCode = countryCodePicker.getDefaultCountryCode();
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode = countryCodePicker.getSelectedCountryCode();
            }
        });

        dispatchedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        dispatchedTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });
//          date picker for dispatch date
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        });


        getDrivers();
        getServiceList();

//        api call
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fullNameET.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter customer name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (customerEmailET.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidEmail(customerEmailET.getText().toString())) {
                    Toast.makeText(getActivity(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (contactNumberTxt.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (getLocationET.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter pickup location", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (getDropOffLocation.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter drop off location", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (driverSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(getActivity(), "Please select driver", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (serviceSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(getActivity(), "Please select service", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (etaET.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please select eta", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (siteET.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter site", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (dispatchDateTxt.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please select site", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (vehicleMakeEt.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter make", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (vehicleModelEt.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter model", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (vehicleColor.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter color", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!asapRadio.isChecked() && !scheduledRadio.isChecked()) {
                    Toast.makeText(getActivity(), "Please select at least one service time asap or schedules", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (dispatchedTime.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please select dispatch", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (total_milesET.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter total Miles", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (invoiceTotal.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please invoice total", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (truckET.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter truck details", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    createAssignJobAPI(dispatchDateTxt.getText().toString(), siteET.getText().toString(), etaET.getText().toString(),
                            fullNameET.getText().toString(), countryCode + contactNumberTxt.getText().toString(),
                            getLocationET.getText().toString(), getDropOffLocation.getText().toString(), customerEmailET.getText().toString(),
                            vehicleMakeEt.getText().toString(), vehicleModelEt.getText().toString(), vehicleColor.getText().toString(),
                            dispatchedTime.getText().toString(), total_job_time.getText().toString(), total_milesET.getText().toString()
                            , invoiceTotal.getText().toString(), notesET.getText().toString(), truckET.getText().toString());
                }
            }
        });
        return view;
    }

    void createAssignJobAPI(String dispatchDate, String site, String eta, String customerName,
                            String contactNumber, String pickup, String dropoff, String email,
                            String vehicleMake, String vehicleModel, String color, String dispatched, String totalJobTime,
                            String totalMiles, String invoiceTotal, String comments, String truck) {

        customProgressBar.showProgress();
        apiService.createAssignJob(sharedPreferenceMethod.getJWTToken(),
                driverIdList.get(driverSpinner.getSelectedItemPosition()),
                dispatchDate, site, eta, customerName, contactNumber, pickup, dropoff, email,
                serviceIdList.get(serviceSpinner.getSelectedItemPosition()), "Assigned", vehicleMake,
                vehicleModel, color, dispatched, totalJobTime, totalMiles, invoiceTotal, comments, truck).enqueue(new Callback<AdminRegisterModel>() {
            @Override
            public void onResponse(Call<AdminRegisterModel> call, Response<AdminRegisterModel> response) {
                customProgressBar.hideProgress();

                if (response.body().getResponse().getStatus().equals("Success")) {
                    Toast.makeText(getActivity(), response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AdminRegisterModel> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage() );
                customProgressBar.hideProgress();

            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM-dd-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dispatchDateTxt.setText(sdf.format(myCalendar.getTime()));
    }

    void getServiceList() {
//        customProgressBar.showProgress();
        apiService.serviceList(sharedPreferenceMethod.getJWTToken()).enqueue(new Callback<ServiceListModel>() {
            @Override
            public void onResponse(Call<ServiceListModel> call, Response<ServiceListModel> response) {
//                customProgressBar.hideProgress();
                if (response.body().getResponse().getStatus().equals("Success")) {
                    serviceIdList.add("Select");
                    serviceNameList.add("Select Service");

                    serviceList = response.body().getResponse().getServices();
                    for (int i = 0; i < response.body().getResponse().getServices().size(); i++) {
                        serviceIdList.add(response.body().getResponse().getServices().get(i).getId());
                        serviceNameList.add(response.body().getResponse().getServices().get(i).getServiceType());
                    }

                    ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, serviceNameList);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    serviceSpinner.setAdapter(aa);
                } else {

                }
            }

            @Override
            public void onFailure(Call<ServiceListModel> call, Throwable t) {
//                customProgressBar.hideProgress();
                Log.e("TAG", "onFailure: " + t.getMessage());

            }
        });
    }

    void getDrivers() {
//        customProgressBar.showProgress();
        apiService.getDriverList(sharedPreferenceMethod.getJWTToken()).enqueue(new Callback<DriversListModel>() {
            @Override
            public void onResponse(Call<DriversListModel> call, Response<DriversListModel> response) {

                if (response.body().getResponse().getStatus().equals("Success")) {
                    driverIdList.add("Select");
                    driverNameList.add("Select Driver");
                    if (response.body().getResponse().getDrivers().size() > 0) {
                        driverList = response.body().getResponse().getDrivers();
                        for (int i = 0; i < driverList.size(); i++) {
                            driverIdList.add(driverList.get(i).getId());
                            driverNameList.add(driverList.get(i).getDriverName());
                        }
                        ArrayAdapter aa = new ArrayAdapter(getActivity(),
                                android.R.layout.simple_spinner_item, driverNameList);
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        driverSpinner.setAdapter(aa);

                    }
                }
            }

            @Override
            public void onFailure(Call<DriversListModel> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}