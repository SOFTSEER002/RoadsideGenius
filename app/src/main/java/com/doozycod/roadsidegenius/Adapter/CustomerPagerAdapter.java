package com.doozycod.roadsidegenius.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.doozycod.roadsidegenius.Activities.Admin.Navigation.fragment.TabsFragment.AssignedFragment;
import com.doozycod.roadsidegenius.Activities.Admin.Navigation.fragment.TabsFragment.CompletedFragment;
import com.doozycod.roadsidegenius.Activities.Admin.Navigation.fragment.TabsFragment.NewRequestFragment;
import com.doozycod.roadsidegenius.Activities.Customer.CustomerNavigation.Fragments.Tabs.CompletedRequestFragment;
import com.doozycod.roadsidegenius.Activities.Customer.CustomerNavigation.Fragments.Tabs.OpenRequestFragment;

public class CustomerPagerAdapter extends FragmentStatePagerAdapter {

    public CustomerPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new OpenRequestFragment();
                break;
            case 1:
                fragment = new CompletedRequestFragment();
                break;
            default:
                fragment = new OpenRequestFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title;
        switch (position) {
            case 0:
                title = "Open";
                break;
            case 1:
                title = "Completed";
                break;
            default:
                title = "Open";
        }
        return title;
    }
}
