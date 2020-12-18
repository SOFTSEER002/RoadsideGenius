package com.doozycod.roadsidegenius.Activities.Customer.CustomerNavigation.Fragments.Tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doozycod.roadsidegenius.R;

public class CompletedRequestFragment extends Fragment {

    public CompletedRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_completed_request, container, false);
        return view;
    }
}