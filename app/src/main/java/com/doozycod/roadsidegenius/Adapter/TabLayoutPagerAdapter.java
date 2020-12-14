package com.doozycod.roadsidegenius.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.doozycod.roadsidegenius.Activities.Driver.Fragments.TabFragments.AllTaskFragment;
import com.doozycod.roadsidegenius.Activities.Driver.Fragments.TabFragments.TodayFragment;
import com.doozycod.roadsidegenius.Activities.Driver.Fragments.TabFragments.UnassignedFragment;

public class TabLayoutPagerAdapter extends FragmentStatePagerAdapter {

    public TabLayoutPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new TodayFragment();
                break;
            case 1:
                fragment = new AllTaskFragment();
                break;
            case 2:
                fragment = new UnassignedFragment();
                break;
            default:
                fragment = new TodayFragment();
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
                title = "Today";
                break;
            case 1:
                title = "All";
                break;
            case 2:
                title = "Unassigned";
                break;
            default:
                title = "Today";
        }
        return title;
    }
}
