package com.doozycod.roadsidegenius.Activities.Admin.Navigation.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.doozycod.roadsidegenius.Activities.Admin.Adapter.CompanyAdapter;
import com.doozycod.roadsidegenius.Adapter.DriversListAdapter;
import com.doozycod.roadsidegenius.Model.Company.Company;
import com.doozycod.roadsidegenius.Model.Company.CompanyModel;
import com.doozycod.roadsidegenius.Model.DriverList.Driver;
import com.doozycod.roadsidegenius.R;
import com.doozycod.roadsidegenius.Service.ApiService;
import com.doozycod.roadsidegenius.Service.ApiUtils;
import com.doozycod.roadsidegenius.Utils.CustomProgressBar;
import com.doozycod.roadsidegenius.Utils.SharedPreferenceMethod;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompaniesListFragment extends Fragment {


    public CompaniesListFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    CompanyAdapter companyAdapter;
    SharedPreferenceMethod sharedPreferenceMethod;
    ApiService apiService;
    CustomProgressBar customProgressBar;
    List<Company> driverList = new ArrayList<>();
    boolean allowRefresh = false;

    @Override
    public void onResume() {
        super.onResume();
        //Initialize();
        if (allowRefresh) {
            allowRefresh = false;
            getCompanies();

            //call your initialization code here
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!allowRefresh)
            allowRefresh = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_companies_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        sharedPreferenceMethod = new SharedPreferenceMethod(getContext());
        customProgressBar = new CustomProgressBar(getContext());
        apiService = ApiUtils.getAPIService();
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        getCompanies();
        return view;
    }

    private void getCompanies() {
        customProgressBar.showProgress();

        apiService.getCompanyLists(sharedPreferenceMethod.getJWTToken()).enqueue(new Callback<CompanyModel>() {
            @Override
            public void onResponse(Call<CompanyModel> call, Response<CompanyModel> response) {
                customProgressBar.hideProgress();
                allowRefresh = true;

                if (response.body().getResponse().getStatus().equals("Success")) {
                    driverList = response.body().getResponse().getCompanies();
                    if (driverList.size() == 0) {
                        Toast.makeText(getActivity(), "No Driver Found!", Toast.LENGTH_SHORT).show();
                    } else
                        companyAdapter = new CompanyAdapter(getContext(), driverList);
                    recyclerView.setAdapter(companyAdapter);
                }
            }

            @Override
            public void onFailure(Call<CompanyModel> call, Throwable t) {
                customProgressBar.hideProgress();

            }
        });
    }
}