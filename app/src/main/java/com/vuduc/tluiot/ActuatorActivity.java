package com.vuduc.tluiot;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.vuduc.adapters.ViewPagerAdapter;
import com.vuduc.fragments.ActuatorInfoFragment;
import com.vuduc.fragments.ActuatorRealtimeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActuatorActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actuator);
        addControls();
        addEvents();
    }

    private void addEvents() {
    }

    private void addControls() {
        ButterKnife.bind(this);

        //Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.actuator);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //tabLayout
        setupViewPager(mViewpager);
        mTabLayout.setupWithViewPager(mViewpager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ActuatorInfoFragment(), "Thông tin");
        adapter.addFragment(new ActuatorRealtimeFragment(), "Ds Thiết bị");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish(); // close this activity and return to preview activity (if there is any)
                break;
            case R.id.action_notification:
                Toast.makeText(getApplicationContext(), "thong bao", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
