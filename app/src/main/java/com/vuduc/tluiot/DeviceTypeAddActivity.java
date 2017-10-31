package com.vuduc.tluiot;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.vuduc.models.DeviceTypeResponse;
import com.vuduc.network.ApiUtils;
import com.vuduc.network.SprayIoTApiInterface;
import com.vuduc.until.ProgressDialogLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceTypeAddActivity extends AppCompatActivity {
    public static final String TAG = DeviceTypeAddActivity.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.img_options)
    ImageView imgOptions;
    @BindView(R.id.edit_device_name)
    EditText editDeviceTypeName;
    @BindView(R.id.edit_node_describe)
    EditText editDeviceTypeNote;

    private Context mContext;
    private String mDeviceTypeID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_type_add);
        mContext = this;
        addControls();
        addEvents();
    }

    private void addControls() {
        ButterKnife.bind(this);

        //Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.device_type);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void addEvents() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = String.valueOf(editDeviceTypeName.getText());
                String note = String.valueOf(editDeviceTypeNote.getText());

                requestAddDeviceType(name, note);
            }
        });
    }

    private void requestAddDeviceType(String name, String note) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Add...");
        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<DeviceTypeResponse> callDeviceType = apiService.addDeviceType(name, note);
        callDeviceType.enqueue(new Callback<DeviceTypeResponse>() {
            @Override
            public void onResponse(Call<DeviceTypeResponse> call, Response<DeviceTypeResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.toast_add_device_type_successful, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, R.string.toast_add_device_type_fail, Toast.LENGTH_SHORT).show();
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<DeviceTypeResponse> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
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
}
