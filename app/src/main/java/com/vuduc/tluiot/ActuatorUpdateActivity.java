package com.vuduc.tluiot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.vuduc.adapters.ListActuatorAdapter;
import com.vuduc.adapters.ListFunctionAdapter;
import com.vuduc.models.ActuatorsResponse;
import com.vuduc.models.AreaResponse;
import com.vuduc.models.DeviceTypeResponse;
import com.vuduc.models.FunctionByAcResponse;
import com.vuduc.models.FunctionsResponse;
import com.vuduc.network.ApiUtils;
import com.vuduc.network.SprayIoTApiInterface;
import com.vuduc.until.Logger;
import com.vuduc.until.ProgressDialogLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ganfra.materialspinner.MaterialSpinner;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActuatorUpdateActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = ActuatorUpdateActivity.class.getSimpleName();
    @BindView(R.id.img_options)
    ImageView imgOptions;
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
    @BindView(R.id.rv_functions)
    RecyclerView mRVFunction;
    @BindView(R.id.srlLayout)
    SwipeRefreshLayout srlLayout;
    @BindView(R.id.fab_info_actuator)
    FloatingActionMenu fabFunction;
    @BindView(R.id.fab_create_function)
    FloatingActionButton fabCreateFunction;
    @BindView(R.id.fab_gone_fab)
    FloatingActionButton fabGoneFab;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    List<AreaResponse.Result> mListArea;
    List<String> arrAreaName;
    List<DeviceTypeResponse.ResultBean> mListDeviceType;
    List<String> arrDeviceTypeName;
    List<FunctionByAcResponse.Result> mListFunction;
    ArrayAdapter<String> actuatorAdapter;
    List<ActuatorsResponse.ResultBean> mListActuator;
    List<String> arrActuatorName = new ArrayList<>();
    private Context mContext;
    private ListFunctionAdapter mListFunctionAdapter;
    private String mId, mAreaId, mDeviceTypeId, actuatorID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actuator_update);
        mContext = this;
        addControls();
        initData();
        addEvents();
    }

    private void initData() {
        Intent callerIntent = getIntent();
        Bundle packageFormCaller = callerIntent.getBundleExtra(ListActuatorAdapter.TAG);
        mId = packageFormCaller.getString(ListActuatorAdapter.ACTUATOR_ID);
        mAreaId = packageFormCaller.getString(ListActuatorAdapter.ACTUATOR_AREA_ID);
        mDeviceTypeId = packageFormCaller.getString(ListActuatorAdapter.ACTUATOR_DEVICETYPE_ID);
        String actuatorName = packageFormCaller.getString(ListActuatorAdapter.ACTUATOR_NAME);
        String actuatorDescrip = packageFormCaller.getString(ListActuatorAdapter.ACTUATOR_DESCRIPTION);
        String areaName = packageFormCaller.getString(ListActuatorAdapter.ACTUATOR_AREA_NAME);
        String deviceTypeName = packageFormCaller.getString(ListActuatorAdapter.ACTUATOR_DEVICETYPE_NAME);

        editDeviceName.setText(actuatorName);
        editAreaName.setText(areaName);
        editDeviceType.setText(deviceTypeName);
        editDescription.setText(actuatorDescrip);

        //TODO: get list of function
        if (mId != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getFunctions();
                }
            }).start();
        } else {
            Toast.makeText(mContext, "Có gì đó sai sai ở đây! " + mId, Toast.LENGTH_SHORT).show();
        }
    }

    private void addControls() {
        ButterKnife.bind(this);

        //Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Thực thi");
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

        //RV list function
        mListFunction = new ArrayList<>();
        mListFunctionAdapter = new ListFunctionAdapter(mContext, mListFunction);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRVFunction.setLayoutManager(layoutManager);
        mRVFunction.setItemAnimator(new DefaultItemAnimator());
        mRVFunction.setNestedScrollingEnabled(false);
        mRVFunction.setAdapter(mListFunctionAdapter);

        srlLayout.setOnRefreshListener(this);
    }

    private void getFunctions() {
        ProgressDialogLoader.progressdialog_creation(mContext, "Loading...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<FunctionByAcResponse> callService = apiService.getFunctionByActuatorId(mId);
        callService.enqueue(new Callback<FunctionByAcResponse>() {
            @Override
            public void onResponse(Call<FunctionByAcResponse> call, Response<FunctionByAcResponse> response) {
                if (response.isSuccessful())
                    initFunctions(response.body());
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<FunctionByAcResponse> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void initFunctions(FunctionByAcResponse body) {
        if (body.getResult() != null) {
            List<FunctionByAcResponse.Result> functionData = body.getResult();
            mListFunction.clear();
            for (FunctionByAcResponse.Result a : functionData) {
                mListFunction.add(a);
            }
            mListFunctionAdapter.notifyDataSetChanged();
        }
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

        imgOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });

        mListFunctionAdapter.SetOnItemClickListener(new ListFunctionAdapter.OnItemClickListener() {
            @Override
            public void onImgOptionsClick(View view, int position) {
                showFunctionPopupMenu(view, position);
            }
        });

        fabCreateFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fabGoneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabFunction.setVisibility(View.GONE);
            }
        });
    }

    private void showFunctionPopupMenu(View view, int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_info_node, popup.getMenu());
        popup.setOnMenuItemClickListener(new FunctionMenuItemClickListener(position));
        popup.show();
    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_info_node, popup.getMenu());
        popup.setOnMenuItemClickListener(new ActuatorMenuItemClickListener());
        popup.show();
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

    private void showSubmitUpdateDialog() {
        final String name = String.valueOf(editDeviceName.getText());
        final String description = String.valueOf(editDescription.getText());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("SprayIoT");
        builder.setMessage("Xin bạn xác nhận để cập nhật thông tin");
        builder.setCancelable(false);
        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestUpdateActuatorInfo(mId, name, description, mAreaId, mDeviceTypeId);
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void requestUpdateActuatorInfo(String id, String name, String description, String areaId, String deviceTypeId) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Updating...");
        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<ActuatorsResponse> callActuator = apiService.updateInfoActuator(id, name, description, areaId, deviceTypeId);
        callActuator.enqueue(new Callback<ActuatorsResponse>() {
            @Override
            public void onResponse(Call<ActuatorsResponse> call, Response<ActuatorsResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.toast_update_device_successful, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, R.string.toast_update_device_fail, Toast.LENGTH_SHORT).show();
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<ActuatorsResponse> call, Throwable t) {
                Logger.d(TAG, t.toString());
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getFunctions();
                fabFunction.setVisibility(View.VISIBLE);
                srlLayout.setRefreshing(false);
            }
        }, 1500);
    }

    private void showUpdateFunctionInfoDialog(final FunctionByAcResponse.Result itemFunction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Thông tin van điện từ");
        builder.setCancelable(false); //click outSide to dismiss dialog
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View alertLayout = inflater.inflate(R.layout.dialog_update_function_info, null);
        builder.setView(alertLayout);

        //addControls
        final EditText editFunctionName = alertLayout.findViewById(R.id.edit_function_name);
        final EditText editFunctionDescribe = alertLayout.findViewById(R.id.edit_function_describe);
        final TextView editActuator = alertLayout.findViewById(R.id.edit_actuator);
        ImageView btnActuator = alertLayout.findViewById(R.id.btn_actuator);
        final MaterialSpinner spinnerListActuator = alertLayout.findViewById(R.id.spinner_list_actuator);

        //Function spinner
        actuatorAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, arrActuatorName);
        actuatorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerListActuator.setAdapter(actuatorAdapter);

        //initInfo
        editFunctionName.setText(itemFunction.getName());
        editFunctionDescribe.setText(itemFunction.getDescription());
        editActuator.setText(itemFunction.getActuator().getName());
        actuatorID = itemFunction.getActuator().getId();
        //addEvents
        btnActuator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActuators();
                spinnerListActuator.setVisibility(View.VISIBLE);
                spinnerListActuator.performClick();

                spinnerListActuator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != -1) {
                            editActuator.setText(arrActuatorName.get(i));
                            actuatorID = mListActuator.get(i).getId();
                        }
                        spinnerListActuator.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });


        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String functionId = itemFunction.getId();
                String functionName = String.valueOf(editFunctionName.getText());
                String functionDescription = String.valueOf(editFunctionDescribe.getText());

                if (functionId != null) {
                    requestUpdateFunctionInfo(functionId, functionName, functionDescription, actuatorID);
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void requestUpdateFunctionInfo(String functionId, String functionName, String functionDescription, String actuatorID) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Updating...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<FunctionsResponse> callFunction = apiService.updateInfoFunction(functionId, functionName, functionDescription, actuatorID);
        callFunction.enqueue(new Callback<FunctionsResponse>() {
            @Override
            public void onResponse(Call<FunctionsResponse> call, Response<FunctionsResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.toast_update_area_successful, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, R.string.toast_update_area_fail, Toast.LENGTH_SHORT).show();
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<FunctionsResponse> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void getActuators() {
        ProgressDialogLoader.progressdialog_creation(mContext, "Loading...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<ActuatorsResponse> callActuator = apiService.getActuators();
        callActuator.enqueue(new Callback<ActuatorsResponse>() {
            @Override
            public void onResponse(Call<ActuatorsResponse> call, Response<ActuatorsResponse> response) {
                if (response.isSuccessful()) {
                    initListActuator(response.body());
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<ActuatorsResponse> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void initListActuator(ActuatorsResponse body) {
        if (body.getResult() != null) {
            mListActuator = body.getResult();
            arrActuatorName.clear();
            for (ActuatorsResponse.ResultBean a : mListActuator) {
                arrActuatorName.add(a.getName());
            }
            actuatorAdapter.notifyDataSetChanged();
        }
    }

    private void requestDeleteFunction(String functionId) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Deleting...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<ResponseBody> callFunction = apiService.deleteFunction(functionId);
        callFunction.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.toast_delete_device_successful, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(mContext, R.string.toast_delete_device_fail, Toast.LENGTH_SHORT).show();
                    finish();
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private class ActuatorMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_update_info:
                    showSubmitUpdateDialog();
                    return true;
                case R.id.action_delete_info:
                    return true;

            }
            return false;
        }
    }

    private class FunctionMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        int mPosition;

        private FunctionMenuItemClickListener(int position) {
            mPosition = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_update_info:
                    FunctionByAcResponse.Result itemFunction = mListFunction.get(mPosition);
                    showUpdateFunctionInfoDialog(itemFunction);
                    return true;
                case R.id.action_delete_info:
                    Toast.makeText(mContext, "action_delete_info", Toast.LENGTH_SHORT).show();
                    String functionId = mListFunction.get(mPosition).getId();
                    if (functionId != null) {
                        requestDeleteFunction(functionId);
                    }
                    return true;

            }
            return false;
        }
    }
}
