package com.doozycod.roadsidegenius.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.doozycod.roadsidegenius.Adapter.AutoCompleteAdapter;
import com.doozycod.roadsidegenius.Model.AdminRegisterModel;
import com.doozycod.roadsidegenius.Model.JobRequestModel.JobRequestModel;
import com.doozycod.roadsidegenius.Model.ServiceList.Service;
import com.doozycod.roadsidegenius.Model.ServiceList.ServiceListModel;
import com.doozycod.roadsidegenius.R;
import com.doozycod.roadsidegenius.Service.ApiService;
import com.doozycod.roadsidegenius.Service.ApiUtils;
import com.doozycod.roadsidegenius.Utils.CustomProgressBar;
import com.doozycod.roadsidegenius.Utils.SharedPreferenceMethod;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDriverCreateJobFragment extends Fragment {

    TextView customerName,  customerLocation;
    EditText fullNameET,contactNumberTxt, customerEmailET, amount_quoted, notesET;
    AutoCompleteTextView getLocationET, getDropOffLocation;
    AutoCompleteAdapter adapter;
    PlacesClient placesClient;
    Spinner serviceTypeSpinner, vendorIDSpinner;
    ApiService apiService;
    SharedPreferenceMethod sharedPreferenceMethod;
    CustomProgressBar customProgressBar;
    Button requestButton;
    List<Service> serviceList = new ArrayList<>();
    List<String> serviceIdList = new ArrayList<>();
    List<String> serviceTypeList = new ArrayList<>();

    ArrayAdapter aa;
    ImageView contactDialogButton;
    String countryCode = "";
    String otpString = "";
    boolean isSent = false;
    String number = "";
    Toolbar toolbar;
    CountryCodePicker countryCodePicker;

    public AdminDriverCreateJobFragment() {
        // Required empty public constructor
    }

    private void initAutoCompleteTextView(View view) {

        getLocationET = view.findViewById(R.id.getPickupLocationET);
        getLocationET = view.findViewById(R.id.getPickupLocationET);
        getDropOffLocation = view.findViewById(R.id.getDropOffLocationET);
        getLocationET.setThreshold(1);
        getLocationET.setOnItemClickListener(autocompleteClickListener);
        adapter = new AutoCompleteAdapter(getActivity(), placesClient);
        getLocationET.setAdapter(adapter);

        getDropOffLocation.setThreshold(1);
        getDropOffLocation.setOnItemClickListener(autocompleteClickListener);
        adapter = new AutoCompleteAdapter(getActivity(), placesClient);
        getDropOffLocation.setAdapter(adapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void initUI(View view) {
        contactNumberTxt = view.findViewById(R.id.customerNumberET);
        countryCodePicker = view.findViewById(R.id.countryCodeLogin);
        notesET = view.findViewById(R.id.notesET);
        amount_quoted = view.findViewById(R.id.amount_quoted);
        requestButton = view.findViewById(R.id.requestButton);
        contactDialogButton = view.findViewById(R.id.contactDialogButton);
        customerEmailET = view.findViewById(R.id.customerEmailET);
        vendorIDSpinner = view.findViewById(R.id.vendorIDSpinner);
        serviceTypeSpinner = view.findViewById(R.id.serviceTypeSpinner);
//        contactNumberTxt = view.findViewById(R.id.contactNumberTxt);
        fullNameET = view.findViewById(R.id.fullNameET);
        String apiKey = getString(R.string.google_maps_key);
        if (apiKey.isEmpty()) {
            Toast.makeText(getActivity(), "Api key is invalid!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(getActivity(), apiKey);
        }
        placesClient = Places.createClient(getActivity());
        initAutoCompleteTextView(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_driver_create_job, container, false);
        initUI(view);
        contactNumberTxt = view.findViewById(R.id.customerNumberET);
        apiService = ApiUtils.getAPIService();
        sharedPreferenceMethod = new SharedPreferenceMethod(getActivity());
        customProgressBar = new CustomProgressBar(getActivity());

        serviceTypeList.add("Select Service");
        getServiceList();


//        if (!sharedPreferenceMethod.getCustomerContact().equals("")) {
//            contactNumberTxt.setText("+" + sharedPreferenceMethod.getCustomerContact());
//        }
        number = sharedPreferenceMethod.getCustomerContact();
        serviceTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    amount_quoted.setText(serviceList.get((i - 1)).getServiceCost());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        countryCode = countryCodePicker.getDefaultCountryCode();
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode = countryCodePicker.getSelectedCountryCode();
            }
        });

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fullNameET.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (customerEmailET.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidEmail(customerEmailET.getText().toString())) {
                    Toast.makeText(getActivity(), "Please enter valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (contactNumberTxt.getText().toString().equals("")) {
//                    Toast.makeText(getActivity(), "Please enter contact number", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if (getLocationET.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter contact number", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (serviceTypeSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(getActivity(), "Please select a service", Toast.LENGTH_SHORT).show();
                    return;

                } else {

                    createRequest(fullNameET.getText().toString(), customerEmailET.getText().toString(),
                            countryCode + contactNumberTxt.getText().toString(),
                            getLocationET.getText().toString(), getDropOffLocation.getText().toString());

                }
            }
        });
        return view;
    }

    void createRequest(String fullName, String email, String contactNumber, String customerPickup,
                       String customerDropOff) {
        customProgressBar.showProgress();
        apiService.createJob(sharedPreferenceMethod.getJWTToken(),
                fullName, contactNumber, customerPickup, customerDropOff, email,
                serviceList.get(serviceTypeSpinner.getSelectedItemPosition() - 1).getId(),
                notesET.getText().toString(),
                amount_quoted.getText().toString()).enqueue(new Callback<JobRequestModel>() {
            @Override
            public void onResponse(Call<JobRequestModel> call, Response<JobRequestModel> response) {
                customProgressBar.hideProgress();

                if (response.body().getResponse().getStatus().equals("Success")) {
                    Toast.makeText(getActivity(), response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JobRequestModel> call, Throwable t) {
                customProgressBar.hideProgress();

            }
        });
    }

    void getServiceList() {
        customProgressBar.showProgress();
        apiService.serviceList(sharedPreferenceMethod.getJWTToken()).enqueue(new Callback<ServiceListModel>() {
            @Override
            public void onResponse(Call<ServiceListModel> call, Response<ServiceListModel> response) {
                customProgressBar.hideProgress();
                if (response.body().getResponse().getStatus().equals("Success")) {
                    serviceList = response.body().getResponse().getServices();
                    for (int i = 0; i < response.body().getResponse().getServices().size(); i++) {
                        serviceIdList.add(response.body().getResponse().getServices().get(i).getId());
                        serviceTypeList.add(response.body().getResponse().getServices().get(i).getServiceType());
                    }

                    aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, serviceTypeList);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    serviceTypeSpinner.setAdapter(aa);
                } else {

                }
            }

            @Override
            public void onFailure(Call<ServiceListModel> call, Throwable t) {
                customProgressBar.hideProgress();
            }
        });
    }

    private AdapterView.OnItemClickListener autocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            try {
                final AutocompletePrediction item = adapter.getItem(i);
                String placeID = null;
                if (item != null) {
                    placeID = item.getPlaceId();
                }

//                To specify which data types to return, pass an array of Place.Fields in your FetchPlaceRequest
//                Use only those fields which are required.

                List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS
                        , Place.Field.LAT_LNG);

                FetchPlaceRequest request = null;
                if (placeID != null) {
                    request = FetchPlaceRequest.builder(placeID, placeFields)
                            .build();
                }

                if (request != null) {
                    placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onSuccess(FetchPlaceResponse task) {
                            getLocationET.setText(task.getPlace().getName() + "\n" + task.getPlace().getAddress());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                            getLocationET.setText(e.getMessage());
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}