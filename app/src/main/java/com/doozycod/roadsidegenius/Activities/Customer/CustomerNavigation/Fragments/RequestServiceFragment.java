package com.doozycod.roadsidegenius.Activities.Customer.CustomerNavigation.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.doozycod.roadsidegenius.Activities.Admin.Navigation.fragment.CenteredTextFragment;
import com.doozycod.roadsidegenius.Adapter.AutoCompleteAdapter;
import com.doozycod.roadsidegenius.Model.AddCustomerNumberModel.VerifyOTPModel;
import com.doozycod.roadsidegenius.Model.AdminRegisterModel;
import com.doozycod.roadsidegenius.Model.Company.Company;
import com.doozycod.roadsidegenius.Model.Company.CompanyModel;
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
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RequestServiceFragment extends Fragment {
    TextView customerName, contactNumberTxt, customerLocation;
    EditText fullNameET, customerEmailET, amount_quoted, notesET;
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

    public RequestServiceFragment() {
        // Required empty public constructor
    }


    private void initAutoCompleteTextView(View view) {


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

        notesET = view.findViewById(R.id.notesET);
        amount_quoted = view.findViewById(R.id.amount_quoted);
        requestButton = view.findViewById(R.id.requestButton);
        contactDialogButton = view.findViewById(R.id.contactDialogButton);
        customerEmailET = view.findViewById(R.id.customerEmailET);
        vendorIDSpinner = view.findViewById(R.id.vendorIDSpinner);
        serviceTypeSpinner = view.findViewById(R.id.serviceTypeSpinner);
        contactNumberTxt = view.findViewById(R.id.contactNumberTxt);
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

        View view = inflater.inflate(R.layout.fragment_request_service, container, false);
        initUI(view);
        apiService = ApiUtils.getAPIService();
        sharedPreferenceMethod = new SharedPreferenceMethod(getActivity());
        customProgressBar = new CustomProgressBar(getActivity());

        serviceTypeList.add("Select Service");
        getServiceList();
        contactDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContactDialog();
            }
        });
        if(!sharedPreferenceMethod.getCustomerContact().equals("")){
            contactNumberTxt.setText("+" + sharedPreferenceMethod.getCustomerContact());
        }
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
                if (contactNumberTxt.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter contact number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (getLocationET.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter contact number", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    createRequest(fullNameET.getText().toString(), customerEmailET.getText().toString(), countryCode + number,
                            getLocationET.getText().toString(), getDropOffLocation.getText().toString());
                }
            }
        });
        return view;
    }

    void createRequest(String fullName, String email, String contactNumber, String customerPickup,
                       String customerDropOff) {
        customProgressBar.showProgress();
        apiService.createJobRequest(sharedPreferenceMethod.getJWTToken(), sharedPreferenceMethod.getCustomerID(),
                fullName, contactNumber, customerPickup, customerDropOff, email,
                serviceList.get(serviceTypeSpinner.getSelectedItemPosition()).getId(),
                notesET.getText().toString(),
                amount_quoted.getText().toString()).enqueue(new Callback<AdminRegisterModel>() {
            @Override
            public void onResponse(Call<AdminRegisterModel> call, Response<AdminRegisterModel> response) {
                customProgressBar.hideProgress();

                if (response.body().getResponse().getStatus().equals("Success")) {
                    Toast.makeText(getActivity(), response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AdminRegisterModel> call, Throwable t) {
                customProgressBar.hideProgress();

            }
        });
    }

    void showContactDialog() {
        final String[] enteredOtp = new String[1];
        Dialog dialog = new Dialog(getActivity());
//        dialog.setCancelable(false);
        dialog.setContentView(R.layout.contact_verify_dialog);
        EditText contactNumber = dialog.findViewById(R.id.customerNumberET);
        Button sendOtpButton = dialog.findViewById(R.id.sendOtpButton);
        CountryCodePicker countryCodePicker = dialog.findViewById(R.id.countryCodePicker);
        OtpView otp_view = dialog.findViewById(R.id.otp_view);
        LinearLayout showOTP = dialog.findViewById(R.id.showOTP);
        otp_view.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                enteredOtp[0] = otp;

            }
        });
        isSent = false;
        countryCode = countryCodePicker.getDefaultCountryCode();
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode = countryCodePicker.getSelectedCountryCode();
            }
        });
        sendOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSent) {
                    getOTP(countryCode + contactNumber.getText().toString(), sendOtpButton);
                    showOTP.setVisibility(View.VISIBLE);
                } else {
                    if (enteredOtp[0].equals(otpString)) {
                        Toast.makeText(getActivity(), "Phone number verified!", Toast.LENGTH_SHORT).show();
                        contactNumberTxt.setText("+" + countryCode + contactNumber.getText().toString());
                        dialog.dismiss();
                    }
                }

            }
        });
        dialog.show();
    }

    void getOTP(String phNumber, Button sendBTN) {
        customProgressBar.showProgress();
        apiService.addCustomerNumber(sharedPreferenceMethod.getJWTToken(), phNumber).enqueue(new Callback<VerifyOTPModel>() {
            @Override
            public void onResponse(Call<VerifyOTPModel> call, Response<VerifyOTPModel> response) {
                customProgressBar.hideProgress();
                if (response.body().getResponse().getStatus().equals("Success")) {
                    isSent = true;
                    sendBTN.setText("Submit");
                    number = phNumber;
                    otpString = String.valueOf(response.body().getResponse().getOtp());
                    Toast.makeText(getActivity(), response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<VerifyOTPModel> call, Throwable t) {
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

}