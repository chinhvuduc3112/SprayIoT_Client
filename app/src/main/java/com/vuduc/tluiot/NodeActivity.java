package com.vuduc.tluiot;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.vuduc.adapters.ViewPagerAdapter;
import com.vuduc.fragments.ActuatorRealtimeFragment;
import com.vuduc.fragments.AddNodeFragment;
import com.vuduc.fragments.NodeInfoFragment;
import com.vuduc.fragments.NodeStatisticsFragment;
import com.vuduc.fragments.SensorRealtimeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NodeActivity extends AppCompatActivity {

    @BindView(R.id.fab_info_node)
    FloatingActionMenu fab_info_node;
    @BindView(R.id.fab_create_node)
    FloatingActionButton fabCreateNode;
    @BindView(R.id.fab_create_sensor)
    FloatingActionButton fabCreateSensor;
    @BindView(R.id.fab_gone_fab)
    FloatingActionButton fab_gone_fab;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);
        mContext = this;
        addControls();
        addEvents();
    }

    private void addControls() {
        ButterKnife.bind(this);

        //Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.sensor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //tabLayout
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void addEvents() {
        fabCreateNode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AddNodeFragment addNodeFragment = new AddNodeFragment();
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.frame_node_info, addNodeFragment)
//                        .addToBackStack(null)
//                        .commit();
                startActivity(new Intent(NodeActivity.this, NodeAddActivity.class));
            }
        });
        fabCreateSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NodeActivity.this, DeviceNodeAddActivity.class));
            }
        });
        fab_gone_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab_info_node.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NodeStatisticsFragment(), "Thống kê");
        adapter.addFragment(new NodeInfoFragment(), "Thông tin");
        viewPager.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {
                    fab_info_node.setVisibility(View.VISIBLE);
                } else {
                    fab_info_node.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}
