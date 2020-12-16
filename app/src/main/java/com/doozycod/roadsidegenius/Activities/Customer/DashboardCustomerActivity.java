package com.doozycod.roadsidegenius.Activities.Customer;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
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
import android.widget.TextView;

import com.doozycod.roadsidegenius.Activities.Admin.Navigation.menu.DrawerAdapter;
import com.doozycod.roadsidegenius.Activities.Admin.Navigation.menu.DrawerItem;
import com.doozycod.roadsidegenius.Activities.Admin.Navigation.menu.SimpleItem;
import com.doozycod.roadsidegenius.Activities.Admin.Navigation.menu.SpaceItem;
import com.doozycod.roadsidegenius.Activities.Customer.CustomerNavigation.Fragments.RequestServiceFragment;
import com.doozycod.roadsidegenius.Activities.LoginTypeActvvity;
import com.doozycod.roadsidegenius.R;
import com.doozycod.roadsidegenius.Utils.SharedPreferenceMethod;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class DashboardCustomerActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {
    private static final int POS_REQUEST = 0;
    private static final int POS_TASK_HISTORY = 1;
    private static final int POS_LOGOUT = 2;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;
    SharedPreferenceMethod sharedPreferenceMethod;
    Toolbar toolbar;
    TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_customer);


        toolbar = findViewById(R.id.toolbar);
        toolbar_title = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
//        toolbar.setTitle("Dashboard");

        sharedPreferenceMethod = new SharedPreferenceMethod(this);
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();
        toolbar_title.setText("Request a Service");
        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();
        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_REQUEST).setChecked(true),
                new SpaceItem(48),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_REQUEST);
    }

    @Override
    public void onItemSelected(int position) {
        Fragment selectedScreen;
        switch (position) {
            case 2:
                sharedPreferenceMethod.removeLogin();
                startActivity(new Intent(DashboardCustomerActivity.this, LoginTypeActvvity.class));
                finishAffinity();
                break;

            default:
                toolbar_title.setText("Request a service");
                selectedScreen = new RequestServiceFragment();
                showFragment(selectedScreen);
                break;

        }
        slidingRootNav.closeMenu();

    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitlesCustomer);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIconsCustomer);
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

    @SuppressWarnings("rawtypes")
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.teal_200))
                .withSelectedTextTint(color(R.color.teal_200));
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }
}