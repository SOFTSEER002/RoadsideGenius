package com.doozycod.roadsidegenius.Activities.Admin.Navigation.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.doozycod.roadsidegenius.Utils.CustomProgressBar;
import com.doozycod.roadsidegenius.Model.AdminRegisterModel;
import com.doozycod.roadsidegenius.R;
import com.doozycod.roadsidegenius.Service.ApiService;
import com.doozycod.roadsidegenius.Service.ApiUtils;
import com.doozycod.roadsidegenius.Utils.SharedPreferenceMethod;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCompanyFragment extends Fragment {
    private static final String EXTRA_TEXT = "text";

    EditText nameET, emailET, numberET, vendorIdET;
    Spinner w9FormSpinner, l9formSpinner;
    List<String> list = new ArrayList<>();
    Button addCompanyButton;
    SharedPreferenceMethod sharedPreferenceMethod;
    CustomProgressBar customProgressBar;
    ApiService apiService;

    public AddCompanyFragment() {
        // Required empty public constructor
    }

    void initUI(View view) {
        list = new ArrayList<>();
        list.add("Select Yes/No");
        list.add("Yes");
        list.add("No");

        nameET = view.findViewById(R.id.nameET);
        emailET = view.findViewById(R.id.emailET);
        numberET = view.findViewById(R.id.numberET);
        vendorIdET = view.findViewById(R.id.vendorET);
        w9FormSpinner = view.findViewById(R.id.w9formsSpinner);
        l9formSpinner = view.findViewById(R.id.l9formsSpinner);
        addCompanyButton = view.findViewById(R.id.addCompanyButton);
    }

    public static AddCompanyFragment createFor(String text) {
        AddCompanyFragment fragment = new AddCompanyFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_company, container, false);

        sharedPreferenceMethod = new SharedPreferenceMethod(getContext());
        customProgressBar = new CustomProgressBar(getContext());
        apiService = ApiUtils.getAPIService();
        initUI(view);


        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, list);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        w9FormSpinner.setAdapter(aa);
        l9formSpinner.setAdapter(aa);
        onClickEvents();
        return view;
    }

    private void onClickEvents() {

        addCompanyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameET.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter Company Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (emailET.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter Company Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidEmail(emailET.getText().toString())) {
                    Toast.makeText(getActivity(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (numberET.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter Company Number", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (vendorIdET.getText().toString().equals("")) {
//                    Toast.makeText(getActivity(), "Please enter Vendor ID", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if (w9FormSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(getActivity(), "Please select yes or no!", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (l9formSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(getActivity(), "Please select yes or no!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    addCompanyAPI(emailET.getText().toString(),
                            numberET.getText().toString(),
                            nameET.getText().toString());
                }
            }
        });

        l9formSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        w9FormSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void addCompanyAPI(String email, String number, String name) {
        customProgressBar.showProgress();
        apiService.registerCompany(sharedPreferenceMethod.getJWTToken(), email, number, name,
                l9formSpinner.getSelectedItem().toString(), w9FormSpinner.getSelectedItem().toString()).enqueue(new Callback<AdminRegisterModel>() {
            @Override
            public void onResponse(Call<AdminRegisterModel> call, Response<AdminRegisterModel> response) {
                customProgressBar.hideProgress();
                if (response.body().getResponse().getStatus().equals("Success")) {
                    Toast.makeText(getActivity(), response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                    emailET.setText("");
                    nameET.setText("");
                    numberET.setText("");
                    vendorIdET.setText("");
                    w9FormSpinner.setSelection(0);
                    l9formSpinner.setSelection(0);
                } else {
                    Toast.makeText(getActivity(), response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AdminRegisterModel> call, Throwable t) {
                customProgressBar.hideProgress();
                Log.e("TAG", "onFailure: " + t.getMessage());
                Toast.makeText(getActivity(), "Something went Wrong!\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
//        final String text = args != null ? args.getString(EXTRA_TEXT) : "";
//        TextView textView = view.findViewById(R.id.text);
//        textView.setText(text);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), text, Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}