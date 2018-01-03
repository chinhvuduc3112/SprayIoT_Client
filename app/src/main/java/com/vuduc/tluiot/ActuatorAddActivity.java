package com.vuduc.tluiot;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vuduc.models.ActuatorsResponse;
import com.vuduc.models.AreaResponse;
import com.vuduc.models.DeviceTypeResponse;
import com.vuduc.network.ApiUtils;
import com.vuduc.network.SprayIoTApiInterface;
import com.vuduc.until.ProgressDialogLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActuatorAddActivity extends AppCompatActivity {
    private static final String TAG = ActuatorAddActivity.class.getSimpleName();
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.edit_device_name)
    EditText editDeviceName;
    @BindView(R.id.edit_node_describe)
    EditText editDescription;
    @BindView(R.id.spinner_list_area)
    MaterialSpinner spinListArea;
    @BindView(R.id.spinner_list_device_type)
    MaterialSpinner spinListDeviceType;
    @BindView(R.id.edit_node_area)
    TextView editAreaName;
    @BindView(R.id.edit_device_type)
    TextView editDeviceType;
    @BindView(R.id.btn_edit_area)
    ImageView btnEditArea;
    @BindView(R.id.btn_edit_device_type)
    ImageView btnEditDeviceType;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    List<AreaResponse.Result> mListArea;
    List<String> arrAreaName;
    List<DeviceTypeResponse.ResultBean> mListDeviceType;
    List<String> arrDeviceTypeName;

    private Context mContext;
    private String mAreaId, mDeviceTypeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actuator_add);
        mContext = this;
        addControls();
        addEvents();
    }

    private void addControls() {
        ButterKnife.bind(this);

        //Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Thiết bị Thực thi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        arrAreaName = new ArrayList<>();
        arrDeviceTypeName = new ArrayList<>();

        //GONE Spinner List Area name
        ArrayAdapter<String> adapterAreas = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, arrAreaName);
        adapterAreas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinListArea.setAdapter(adapterAreas);

        //GONE Spinner List Device type name
        ArrayAdapter<String> adapterDeviceType = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, arrDeviceTypeName);
        adapterDeviceType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinListDeviceType.setAdapter(adapterDeviceType);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    private void addEvents() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnEditArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAreas();

                spinListArea.setVisibility(View.VISIBLE);
                spinListArea.performClick();

                spinListArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != -1) {
                            editAreaName.setText(arrAreaName.get(i));
                            mAreaId = mListArea.get(i).getId();
                        }
                        spinListArea.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });

        btnEditDeviceType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceType();
                spinListDeviceType.setVisibility(View.VISIBLE);
                spinListDeviceType.performClick();

                spinListDeviceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != -1) {
                            editDeviceType.setText(arrDeviceTypeName.get(i));
                            mDeviceTypeId = mListDeviceType.get(i).getId();
                        }
                        spinListDeviceType.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = String.valueOf(editDeviceName.getText());
                String description = String.valueOf(editDescription.getText());

                if(mDeviceTypeId!=null){
                    requestAddActuator(name, description);
                }else{
                    Toast.makeText(mContext, "Mời bạn nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void requestAddActuator(String name, String description) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Adding...");
        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<ActuatorsResponse> callActuator = apiService.addActuator(name, description, mDeviceTypeId, mAreaId);
        callActuator.enqueue(new Callback<ActuatorsResponse>() {
            @Override
            public void onResponse(Call<ActuatorsResponse> call, Response<ActuatorsResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.toast_add_device_successful, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, R.string.toast_add_device_fail, Toast.LENGTH_SHORT).show();
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<ActuatorsResponse> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void getDeviceType() {
        ProgressDialogLoader.progressdialog_creation(mContext, "Loading...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<DeviceTypeResponse> callDeviceType = apiService.getAllDeviceTypes();
        callDeviceType.enqueue(new Callback<DeviceTypeResponse>() {
            @Override
            public void onResponse(Call<DeviceTypeResponse> call, Response<DeviceTypeResponse> response) {
                if (response.isSuccessful())
                    initDeviceTypes(response.body());
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<DeviceTypeResponse> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void initDeviceTypes(DeviceTypeResponse data) {
        mListDeviceType = new ArrayList<>();
        if (data.getResult() != null) {
            mListDeviceType = data.getResult();
            arrDeviceTypeName.clear();
            for (DeviceTypeResponse.ResultBean a : mListDeviceType) {
                arrDeviceTypeName.add(a.getName());
            }
        }
    }

    private void getAreas() {
        ProgressDialogLoader.progressdialog_creation(mContext, "Loading...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<AreaResponse> callAreas = apiService.getAreas();
        callAreas.enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                if (response.isSuccessful()) {
                    initAreas(response.body());
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void initAreas(AreaResponse data) {
        mListArea = new ArrayList<>();
        if (data.getResult() != null) {
            mListArea = data.getResult();
            arrAreaName.clear();
            for (AreaResponse.Result a : mListArea) {
                arrAreaName.add(a.getName());
            }
        }
    }
}
