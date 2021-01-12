package com.doozycod.roadsidegenius.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doozycod.roadsidegenius.Adapter.ViewPagerAdapter.CustomerPagerAdapter;
import com.doozycod.roadsidegenius.R;
import com.doozycod.roadsidegenius.Utils.CustomViewPager;
import com.google.android.material.tabs.TabLayout;

public class CustomerRequestTabsFragment extends Fragment {

    CustomViewPager viewPager;
    TabLayout tabLayout;
    CustomerPagerAdapter pagerAdapter;

    public CustomerRequestTabsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_request_tabs, container, false);
        pagerAdapter = new CustomerPagerAdapter(getFragmentManager());
        viewPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tab_layout_main);

        viewPager.setSwipeable(true);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(pagerAdapter);
        return view;
    }
}