package com.vuduc.tluiot;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.vuduc.adapters.DeviceTypeAdapter;
import com.vuduc.models.DeviceTypeResponse;
import com.vuduc.network.ApiUtils;
import com.vuduc.network.SprayIoTApiInterface;
import com.vuduc.until.Logger;
import com.vuduc.until.ProgressDialogLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceTypeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = DeviceTypeActivity.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_device_type)
    RecyclerView mRvDeviceType;
    @BindView(R.id.srlLayout)
    SwipeRefreshLayout srlLayout;

    List<DeviceTypeResponse.ResultBean> mListDeviceType = new ArrayList<>();

    private DeviceTypeAdapter mDeviceTypeAdapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_type);
        mContext = this;
        addControls();
        addEvents();
    }

    private void addControls() {
        ButterKnife.bind(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getDeviceType();
            }
        }).start();

        //Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.device_type);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //RV device node
        mDeviceTypeAdapter = new DeviceTypeAdapter(mContext, mListDeviceType); //AlertDialog can getActivity()
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        mRvDeviceType.setLayoutManager(layoutManager);
        mRvDeviceType.setItemAnimator(new DefaultItemAnimator());
        mRvDeviceType.setNestedScrollingEnabled(false);
        mRvDeviceType.setAdapter(mDeviceTypeAdapter);

        srlLayout.setOnRefreshListener(this);
    }

    private void getDeviceType() {
        ProgressDialogLoader.progressdialog_creation(mContext, "Loading...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        retrofit2.Call<DeviceTypeResponse> callDeviceType = apiService.getAllDeviceTypes();
        callDeviceType.enqueue(new Callback<DeviceTypeResponse>() {
            @Override
            public void onResponse(retrofit2.Call<DeviceTypeResponse> call, Response<DeviceTypeResponse> response) {
                if (response.isSuccessful())
                    initDeviceTypes(response.body());
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(retrofit2.Call<DeviceTypeResponse> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void initDeviceTypes(DeviceTypeResponse data) {
        if (data.getResult() != null) {
            List<DeviceTypeResponse.ResultBean> deviceTypeData = data.getResult();
            mListDeviceType.clear();
            for(DeviceTypeResponse.ResultBean a:deviceTypeData){
                Logger.d(TAG, a.getName() + "");
                mListDeviceType.add(a);
            }
            mDeviceTypeAdapter.notifyDataSetChanged();
        }
    }

    private void addEvents() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        switch (item.getItemId()) {
            case R.id.action_notification:
                Toast.makeText(mContext, "Notification", Toast.LENGTH_SHORT).show();
                break;
            case R.id.home:
                finish(); // close this activity and return to preview activity (if there is any)
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getDeviceType();
                srlLayout.setRefreshing(false);
            }
        }, 1500);
    }
}
