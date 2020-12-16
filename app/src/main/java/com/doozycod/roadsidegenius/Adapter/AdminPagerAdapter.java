package com.doozycod.roadsidegenius.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.doozycod.roadsidegenius.Activities.Admin.Navigation.fragment.TabsFragment.AssignedFragment;
import com.doozycod.roadsidegenius.Activities.Admin.Navigation.fragment.TabsFragment.CompletedFragment;
import com.doozycod.roadsidegenius.Activities.Admin.Navigation.fragment.TabsFragment.NewRequestFragment;
import com.doozycod.roadsidegenius.Activities.Driver.Fragments.TabFragments.AllTaskFragment;
import com.doozycod.roadsidegenius.Activities.Driver.Fragments.TabFragments.TodayFragment;
import com.doozycod.roadsidegenius.Activities.Driver.Fragments.TabFragments.UnassignedFragment;

public class AdminPagerAdapter extends FragmentStatePagerAdapter {

    public AdminPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new NewRequestFragment();
                break;
            case 1:
                fragment = new AssignedFragment();
                break;
            case 2:
                fragment = new CompletedFragment();
                break;
            default:
                fragment = new NewRequestFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title;
        switch (position) {
            case 0:
                title = "New Request";
                break;
            case 1:
                title = "Assigned";
                break;
            case 2:
                title = "Completed";
                break;
            default:
                title = "New Request";
        }
        return title;
    }
}
