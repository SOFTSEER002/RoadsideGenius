package com.doozycod.roadsidegenius.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.doozycod.roadsidegenius.Activities.Driver.WithdrawDriverActivity;
import com.doozycod.roadsidegenius.R;


public class PaymentsWithdrawFragment extends Fragment {

    Button withdrawButton;

    public PaymentsWithdrawFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payments_withdraw, container, false);

        withdrawButton = view.findViewById(R.id.withdrawButton);

        withdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WithdrawDriverActivity.class));
            }
        });
        return view;
    }
}