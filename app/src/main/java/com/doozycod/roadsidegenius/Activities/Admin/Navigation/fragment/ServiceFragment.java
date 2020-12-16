package com.doozycod.roadsidegenius.Activities.Admin.Navigation.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.doozycod.roadsidegenius.Adapter.ServiceListAdapter;
import com.doozycod.roadsidegenius.Model.ServiceList.ServiceListModel;
import com.doozycod.roadsidegenius.R;
import com.doozycod.roadsidegenius.Service.ApiService;
import com.doozycod.roadsidegenius.Service.ApiUtils;
import com.doozycod.roadsidegenius.Utils.CustomProgressBar;
import com.doozycod.roadsidegenius.Utils.SharedPreferenceMethod;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServiceFragment extends Fragment {
    private static final String EXTRA_TEXT = "text";


    public ServiceFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentChangeListener {
        void onServiceListListener(int size);
    }

    OnFragmentChangeListener onFragmentChangeListener;

    public void setListener(OnFragmentChangeListener listener) {
        onFragmentChangeListener = listener;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onFragmentChangeListener = (OnFragmentChangeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }

    public static ServiceFragment createFor(String text) {
        ServiceFragment fragment = new ServiceFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    RecyclerView recyclerView;
    Button addServiceButton;
    ApiService apiService;
    SharedPreferenceMethod sharedPreferenceMethod;
    ServiceListAdapter serviceListAdapter;
    CustomProgressBar customProgressBar;
    boolean allowRefresh = false;

    void initUI(View view) {
        recyclerView = view.findViewById(R.id.serviceListRecycler);
        addServiceButton = view.findViewById(R.id.addServiceButton);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        initUI(view);
        apiService = ApiUtils.getAPIService();
        customProgressBar = new CustomProgressBar(getActivity());
        sharedPreferenceMethod = new SharedPreferenceMethod(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getServiceList();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Initialize();
        if (allowRefresh) {
            allowRefresh = false;
            getServiceList();

            //call your initialization code here
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!allowRefresh)
            allowRefresh = true;
    }

    void getServiceList() {
        customProgressBar.showProgress();
        apiService.serviceList(sharedPreferenceMethod.getJWTToken()).enqueue(new Callback<ServiceListModel>() {
            @Override
            public void onResponse(Call<ServiceListModel> call, Response<ServiceListModel> response) {
                customProgressBar.hideProgress();
                if (response.body().getResponse().getStatus().equals("Success")) {
                    onFragmentChangeListener.onServiceListListener(response.body().getResponse().getServices().size());
                    if (response.body().getResponse().getServices().size() > 0) {

                        serviceListAdapter = new ServiceListAdapter(getActivity(), response.body().getResponse().getServices(),
                                apiService);
                        recyclerView.setAdapter(serviceListAdapter);
                        addServiceButton.setVisibility(View.GONE);

                    } else {
                        addServiceButton.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServiceListModel> call, Throwable t) {
                customProgressBar.hideProgress();
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });
    }
}