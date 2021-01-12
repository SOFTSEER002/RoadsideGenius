package com.doozycod.roadsidegenius.Activities.Admin.Navigation;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.doozycod.roadsidegenius.Activities.Admin.AddServiceActivity;
import com.doozycod.roadsidegenius.Activities.Admin.Navigation.fragment.AddCompanyFragment;
import com.doozycod.roadsidegenius.Activities.Admin.Navigation.fragment.AddDriverFragment;
import com.doozycod.roadsidegenius.Activities.Admin.Navigation.fragment.CenteredTextFragment;
import com.doozycod.roadsidegenius.Activities.Admin.Navigation.fragment.CompaniesListFragment;
import com.doozycod.roadsidegenius.Activities.Admin.Navigation.fragment.DriversListFragment;
import com.doozycod.roadsidegenius.Activities.Admin.Navigation.fragment.ServiceFragment;
import com.doozycod.roadsidegenius.Activities.Admin.Navigation.menu.DrawerAdapter;
import com.doozycod.roadsidegenius.Activities.Admin.Navigation.menu.DrawerItem;
import com.doozycod.roadsidegenius.Activities.Admin.Navigation.menu.SimpleItem;
import com.doozycod.roadsidegenius.Activities.Customer.CustomerNavigation.Fragments.RequestServiceFragment;
import com.doozycod.roadsidegenius.Activities.LoginTypeActvvity;
import com.doozycod.roadsidegenius.Model.Customer.CustomerLoginModel;
import com.doozycod.roadsidegenius.R;
import com.doozycod.roadsidegenius.Service.ApiService;
import com.doozycod.roadsidegenius.Service.ApiUtils;
import com.doozycod.roadsidegenius.Utils.CustomProgressBar;
import com.doozycod.roadsidegenius.Utils.SharedPreferenceMethod;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardAdminActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener, ServiceFragment.OnFragmentChangeListener {
    private static final int POS_DASHBOARD = 0;
    private static final int POS_ADD_DRIVER = 1;
    private static final int POS_ADD_COMPANY = 2;
    private static final int POS_COMPANY_LIST = 3;
    private static final int POS_CREATE_JOB = 4;
    private static final int POS_DRIVER_LIST = 5;
    private static final int POS_SERVICES = 6;
    private static final int POS_LOGOUT = 7;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;
    SharedPreferenceMethod sharedPreferenceMethod;
    Toolbar toolbar;
    TextView toolbar_title;
    ImageButton addServiceButton;
    ServiceFragment serviceFragment;
    ApiService apiService;
    CustomProgressBar customProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
        addServiceButton = findViewById(R.id.addServiceButton);
        toolbar = findViewById(R.id.toolbar);
        toolbar_title = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
//        toolbar.setTitle("Dashboard");

        sharedPreferenceMethod = new SharedPreferenceMethod(this);
        customProgressBar = new CustomProgressBar(this);
        apiService = ApiUtils.getAPIService();
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        serviceFragment = new ServiceFragment();
        serviceFragment.setListener(this::onServiceListListener);
        toolbar_title.setText("Dashboard");

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_ADD_DRIVER),
                createItemFor(POS_ADD_COMPANY),
                createItemFor(POS_COMPANY_LIST),
                createItemFor(POS_CREATE_JOB),
                createItemFor(POS_DRIVER_LIST),
                createItemFor(POS_SERVICES),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);
        addServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardAdminActivity.this, AddServiceActivity.class));
            }
        });
    }

    @Override
    public void onItemSelected(int position) {
        if (position == POS_LOGOUT) {
            logoutAPI();
        }
        if (position == POS_ADD_COMPANY) {
            toolbar_title.setText("Add Company");
            Fragment selectedScreen = AddCompanyFragment.createFor(screenTitles[position]);
            showFragment(selectedScreen);
        }
        if (position == POS_ADD_DRIVER) {
            toolbar_title.setText("Add Driver");
            Fragment selectedScreen = AddDriverFragment.createFor(screenTitles[position]);
            showFragment(selectedScreen);
        }
        if (position == POS_DASHBOARD) {
            toolbar_title.setText("Dashboard");
            Fragment selectedScreen = CenteredTextFragment.createFor(screenTitles[position]);
            showFragment(selectedScreen);
        }
        if (position == POS_COMPANY_LIST) {
            toolbar_title.setText("Company List");
            Fragment selectedScreen = new CompaniesListFragment();
            showFragment(selectedScreen);
        }
        if (position == POS_CREATE_JOB) {
            toolbar_title.setText("Create Job");
            Fragment selectedScreen = new RequestServiceFragment();
            showFragment(selectedScreen);
        }
        if (position == POS_DRIVER_LIST) {
            toolbar_title.setText("Drivers List");
            Fragment selectedScreen = DriversListFragment.createFor(screenTitles[position]);
            showFragment(selectedScreen);
        }
        if (position == POS_SERVICES) {
            toolbar_title.setText("Services");
            Fragment selectedScreen = ServiceFragment.createFor(screenTitles[position]);

            showFragment(selectedScreen);
        }
        slidingRootNav.closeMenu();
        if (!toolbar_title.getText().toString().equals("Services")) {
            addServiceButton.setVisibility(View.GONE);
        }
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    void logoutAPI() {
        customProgressBar.showProgress();
        apiService.adminLogout(sharedPreferenceMethod.getJWTToken(), sharedPreferenceMethod.getDeviceId()).enqueue(new Callback<CustomerLoginModel>() {
            @Override
            public void onResponse(Call<CustomerLoginModel> call, Response<CustomerLoginModel> response) {
                customProgressBar.hideProgress();
                if (response.body().getResponse().getStatus().equals("Success")) {
                    sharedPreferenceMethod.removeLogin();
                    startActivity(new Intent(DashboardAdminActivity.this, LoginTypeActvvity.class));
                    finishAffinity();
                    Toast.makeText(DashboardAdminActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(DashboardAdminActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerLoginModel> call, Throwable t) {
                customProgressBar.hideProgress();
            }
        });
    }

    @SuppressWarnings("rawtypes")
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.teal_200))
                .withSelectedTextTint(color(R.color.teal_200));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof ServiceFragment) {
            this.serviceFragment = (ServiceFragment) fragment;
        } /*else if (fragment instanceof AssignNewPropertyFragment) {
            this.assignNewPropertyFragment = (AssignNewPropertyFragment) fragment;

        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment f : getSupportFragmentManager().getFragments()) {
            f.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onServiceListListener(int size) {
        Log.e("TAG", "onServiceListListener: " + size);
        if (toolbar_title.getText().toString().equals("Services")) {
            if (size == 0) {
                addServiceButton.setVisibility(View.GONE);
                return;
            }
            if (size > 0) {
                addServiceButton.setVisibility(View.VISIBLE);
            }
        }
    }
}