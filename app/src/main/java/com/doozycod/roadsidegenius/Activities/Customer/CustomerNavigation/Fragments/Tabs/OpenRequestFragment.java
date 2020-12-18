package com.doozycod.roadsidegenius.Activities.Customer.CustomerNavigation.Fragments.Tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.doozycod.roadsidegenius.Activities.Admin.Adapter.UnassignAdapter;
import com.doozycod.roadsidegenius.Activities.Customer.CustomerNavigation.Adapter.UnassignCustomerAdapter;
import com.doozycod.roadsidegenius.Model.JobList.Job;
import com.doozycod.roadsidegenius.Model.JobList.JobsListModel;
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


public class OpenRequestFragment extends Fragment {

    RecyclerView recyclerView;
    SharedPreferenceMethod sharedPreferenceMethod;
    CustomProgressBar customProgressBar;
    ApiService apiService;
    TextView textView;
    List<Job> jobList = new ArrayList<>();

    public OpenRequestFragment() {
        // Required empty public constructor
    }

    void initUI(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        textView = view.findViewById(R.id.textView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_open, container, false);
        sharedPreferenceMethod = new SharedPreferenceMethod(getActivity());
        customProgressBar = new CustomProgressBar(getActivity());
        apiService = ApiUtils.getAPIService();
        initUI(view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getJobsList();

        return view;
    }

    void getJobsList() {
        customProgressBar.showProgress();
        apiService.getJobsList(sharedPreferenceMethod.getJWTToken()).enqueue(new Callback<JobsListModel>() {
            @Override
            public void onResponse(Call<JobsListModel> call, Response<JobsListModel> response) {
                customProgressBar.hideProgress();
                Log.e("TAG", "onResponse: " + response.body().getResponse().getStatus());

                if (response.body().getResponse().getStatus().equals("Success")) {
                    jobList = response.body().getResponse().getJobs();
                    if (jobList.size() > 0) {
                        recyclerView.setAdapter(new UnassignCustomerAdapter(getContext(), response.body().getResponse().getJobs()));
                        textView.setVisibility(View.GONE);
                    } else {
                        textView.setVisibility(View.VISIBLE);
//                        Toast.makeText(getActivity(), "No Jobs Found!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JobsListModel> call, Throwable t) {
                customProgressBar.hideProgress();
            }
        });
    }

}