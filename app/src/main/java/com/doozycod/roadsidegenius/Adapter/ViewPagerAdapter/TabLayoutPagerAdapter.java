package com.doozycod.roadsidegenius.Adapter.ViewPagerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.doozycod.roadsidegenius.Tabs.DriverNavigation.AllTaskFragment;
import com.doozycod.roadsidegenius.Tabs.DriverNavigation.UnassignedFragment;

public class TabLayoutPagerAdapter extends FragmentStatePagerAdapter {

    public TabLayoutPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
//            case 0:
//                fragment = new TodayFragment();
//                break;
            case 0:
                fragment = new AllTaskFragment();
                break;
            case 1:
                fragment = new UnassignedFragment();
                break;
            default:
                fragment = new AllTaskFragment();
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
                title = "Assigned jobs";
                break;
            case 1:
                title = "Completed jobs";
                break;
//            case 2:
//                title = "Unassigned";
//                break;
            default:
                title = "Today";
        }
        return title;
    }
}
