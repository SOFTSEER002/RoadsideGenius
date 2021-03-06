package com.doozycod.roadsidegenius.Activities.Admin.Navigation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.doozycod.roadsidegenius.Adapter.AdminPagerAdapter;
import com.doozycod.roadsidegenius.Adapter.TabLayoutPagerAdapter;
import com.doozycod.roadsidegenius.R;
import com.doozycod.roadsidegenius.Utils.CustomViewPager;
import com.google.android.material.tabs.TabLayout;


/**
 * Created by yarolegovich on 25.03.2017.
 */

public class CenteredTextFragment extends Fragment {

    private static final String EXTRA_TEXT = "text";

    public static CenteredTextFragment createFor(String text) {
        CenteredTextFragment fragment = new CenteredTextFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }


    AdminPagerAdapter pagerAdapter;
    CustomViewPager viewPager;
    TabLayout tabLayout;

    boolean allowRefresh = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        pagerAdapter = new AdminPagerAdapter(getFragmentManager());
        viewPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tab_layout_main);

        viewPager.setSwipeable(true);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(pagerAdapter);
        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        //Initialize();
//        if (allowRefresh) {
//            allowRefresh = false;
//            tabLayout.setupWithViewPager(viewPager);
//            viewPager.setAdapter(pagerAdapter);
//
//
//            //call your initialization code here
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (!allowRefresh)
//            allowRefresh = true;
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        final String text = args != null ? args.getString(EXTRA_TEXT) : "";

    }
}
