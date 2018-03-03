package com.vuduc.tluiot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.vuduc.adapters.ViewPagerAdapter;
import com.vuduc.fragments.ActuatorRealtimeFragment;
import com.vuduc.fragments.SensorRealtimeFragment;
import com.vuduc.models.DeviceNodeResponse;
import com.vuduc.models.WeatherResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.navigation_view)
    NavigationView mNavigation_view;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addControls() {
        ButterKnife.bind(this);
    }

    private void addEvents() {
        //Khoi tao actionbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigation_view.setNavigationItemSelectedListener(this);

        //tabLayout
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_item_account_management) {

        } else if (id == R.id.nav_item_actuator) {
            startActivity(new Intent(MainActivity.this, ActuatorActivity.class));
        } else if (id == R.id.nav_item_area_management) {
            startActivity(new Intent(MainActivity.this, AreaActivity.class));
        } else if (id == R.id.nav_item_node_sensor) {
            startActivity(new Intent(MainActivity.this, NodeActivity.class));
        } else if (id == R.id.nav_item_weather) {
            startActivity(new Intent(MainActivity.this, WeatherActivity.class));
        } else if (id==R.id.nav_item_device_type){
            startActivity(new Intent(MainActivity.this, DeviceTypeActivity.class));
        } else if (id==R.id.nav_item_group_condition){
            startActivity(new Intent(MainActivity.this, GroupExecuConditionActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SensorRealtimeFragment(), "Cảm biến");
//        adapter.addFragment(new ActuatorRealtimeFragment(), "Thực thi");
        viewPager.setAdapter(adapter);
    }
}
