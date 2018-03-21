package com.vuduc.tluiot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vuduc.models.FunctionsResponse;
import com.vuduc.network.ApiUtils;
import com.vuduc.network.SprayIoTApiInterface;
import com.vuduc.until.ProgressDialogLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;

public class FunctionAddActivity extends AppCompatActivity {

    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.edit_device_name)
    EditText mEditDeviceName;
    @BindView(R.id.edit_device_describe)
    EditText mEditDeviceDescribe;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private String mActuatorID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_add);

        initData();

        initView();
        registerViewEvents();
    }

    private void initData() {
        Intent callerIntent = getIntent();
        Bundle packageFormCaller = callerIntent.getBundleExtra(ActuatorUpdateActivity.TAG);
        mActuatorID = packageFormCaller.getString("ACTUATOR_ID");
    }

    private void registerViewEvents() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = String.valueOf(mEditDeviceName.getText());
                String description = String.valueOf(mEditDeviceDescribe.getText());

                if(mActuatorID!=null){
                    requestAddFunction(name, description);
                } else{
                    Toast.makeText(FunctionAddActivity.this, "Somethings wrong here", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void requestAddFunction(String name, String description) {
        ProgressDialogLoader.progressdialog_creation(FunctionAddActivity.this, "Adding...");
        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        retrofit2.Call<FunctionsResponse> callFunction = apiService.addFunction(name, description, mActuatorID);
        callFunction.enqueue(new Callback<FunctionsResponse>() {
            @Override
            public void onResponse(retrofit2.Call<FunctionsResponse> call, Response<FunctionsResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FunctionAddActivity.this, R.string.toast_add_device_successful, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FunctionAddActivity.this, R.string.toast_add_device_fail, Toast.LENGTH_SHORT).show();
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(retrofit2.Call<FunctionsResponse> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void initView() {
        ButterKnife.bind(this);
        //Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("van điện từ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
