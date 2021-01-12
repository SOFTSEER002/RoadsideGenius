package com.doozycod.roadsidegenius.Tabs.AdminNavigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.doozycod.roadsidegenius.Adapter.AdminAdapter.UnassignAdapter;
import com.doozycod.roadsidegenius.Model.JobList.JobsListModel;
import com.doozycod.roadsidegenius.R;
import com.doozycod.roadsidegenius.Service.ApiService;
import com.doozycod.roadsidegenius.Service.ApiUtils;
import com.doozycod.roadsidegenius.Utils.CustomProgressBar;
import com.doozycod.roadsidegenius.Utils.SharedPreferenceMethod;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewRequestFragment extends Fragment {

    RecyclerView recyclerView;
    SharedPreferenceMethod sharedPreferenceMethod;
    CustomProgressBar customProgressBar;
    ApiService apiService;
    boolean allowRefresh = false;
    TextView text;

    public NewRequestFragment() {
        // Required empty public constructor
    }

    void initUI(View view) {
        text = view.findViewById(R.id.text);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_request, container, false);

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

    @Override
    public void onResume() {
        super.onResume();
        //Initialize();
        if (allowRefresh) {
            allowRefresh = false;
            getJobsList();

            //call your initialization code here
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!allowRefresh)
            allowRefresh = true;
    }

    void getJobsList() {
        customProgressBar.showProgress();
        apiService.getJobsList(sharedPreferenceMethod.getJWTToken()).enqueue(new Callback<JobsListModel>() {
            @Override
            public void onResponse(Call<JobsListModel> call, Response<JobsListModel> response) {
                customProgressBar.hideProgress();

                if (response.body().getResponse().getStatus().equals("Success")) {
                    if (response.body().getResponse().getJobs().size() > 0) {
                        text.setVisibility(View.GONE);
                        recyclerView.setAdapter(new UnassignAdapter(getContext(), response.body().getResponse().getJobs()));
                    } else {
                        text.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "No Jobs Found yet!", Toast.LENGTH_SHORT).show();
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