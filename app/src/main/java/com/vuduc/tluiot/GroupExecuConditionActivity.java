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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.vuduc.adapters.GroupExecuAdapter;
import com.vuduc.models.ActuatorsResponse;
import com.vuduc.models.FunctionByAcResponse;
import com.vuduc.models.FunctionsResponse;
import com.vuduc.models.GroupExecuResponse;
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

public class GroupExecuConditionActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = GroupExecuConditionActivity.class.getSimpleName();
    public static final String GROUPEXECU_ID = "GROUPEXECU_ID";
    public static final String GROUPEXECU_NAME = "GROUPEXECU_NAME";
    public static final String GROUPEXECU_DESCRIPTION = "GROUPEXECU_DESCRIPTION";
    public static final String GROUPEXECU_AUTOTIME = "GROUPEXECU_AUTOTIME";
    public static final String GROUPEXECU_STATUS = "GROUPEXECU_STATUS";
    public static final String FUNCTION_ID = "FUNCTION_ID";
    public static final String FUNCTION_NAME = "FUNCTION_NAME";

    @BindView(R.id.srlLayout)
    SwipeRefreshLayout srlLayout;
    @BindView(R.id.spinner_list_functions)
    MaterialSpinner spinnerListFunctions;
    @BindView(R.id.spinner_list_actuator)
    MaterialSpinner spinnerListActuator;
    @BindView(R.id.rv_group_condition)
    RecyclerView rvGroupCondition;
    @BindView(R.id.fab_info_actuator)
    FloatingActionMenu mFabInfoGroupExecu;
    @BindView(R.id.fab_create_actuator)
    FloatingActionButton mFabCreateGroupExecu;
    @BindView(R.id.fab_gone_fab)
    FloatingActionButton mFabGoneFab;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    //Data for spinner actuator
    List<ActuatorsResponse.ResultBean> listActuators;
    List<String> arrActuatorName;

    //Data for spinner Function
    List<FunctionByAcResponse.Result> listFunctions;
    List<String> arrFunctionName;

    List<GroupExecuResponse.ResultBean> mListGroupExecu;

    private Context mContext;
    private ArrayAdapter<String> functionAdapter, actuatorAdapter;
    private GroupExecuAdapter mGroupExecuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_execu_condition);
        mContext = this;

//        //TODO: get list Function
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                getFunctions();
//            }
//        }).start();

        //TODO: get List Actuator
        new Thread(new Runnable() {
            @Override
            public void run() {
                getActuators();
            }
        }).start();

        addControls();
        addEvents();
    }

    private void addControls() {
        ButterKnife.bind(this);

        arrFunctionName = new ArrayList<>();
        arrActuatorName = new ArrayList<>();

        //FAB
        mFabInfoGroupExecu.setVisibility(View.VISIBLE);

        //Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Điều kiện thực thi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Function Spinner
        functionAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, arrFunctionName);
        functionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerListFunctions.setAdapter(functionAdapter);

        //Actuator Spinner
        actuatorAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, arrActuatorName);
        actuatorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerListActuator.setAdapter(actuatorAdapter);

        //Group ExecutionCondition recyccleView
        mListGroupExecu = new ArrayList<>();
        mGroupExecuAdapter = new GroupExecuAdapter(mContext, mListGroupExecu);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvGroupCondition.setLayoutManager(layoutManager);
        rvGroupCondition.setItemAnimator(new DefaultItemAnimator());
        rvGroupCondition.setNestedScrollingEnabled(false);
        rvGroupCondition.setAdapter(mGroupExecuAdapter);

        //Refresh layout
        srlLayout.setOnRefreshListener(this);
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
            case android.R.id.home:
                finish();
                break;
            case R.id.action_notification:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addEvents() {
        spinnerListFunctions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != -1) {
                    //TODO: get list Group Execu
                    String functionID = listFunctions.get(i).getId();
                    if (functionID != null) {
                        getListGroupExecution(functionID);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinnerListActuator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != -1) {
                    //TODO: Reset list recycleView GroupExecuAdapter
                    mListGroupExecu.clear();
                    mGroupExecuAdapter.notifyDataSetChanged();

                    //TODO: get list function by actuatorID
                    String actuatorID = listActuators.get(i).getId();
                    if (actuatorID != null) {
                        getFunctionsByActuatorID(actuatorID);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mGroupExecuAdapter.SetOnItemClickListener(new GroupExecuAdapter.OnItemClickListener() {
            @Override
            public void onItemViewClick(View view, int position) {
                GroupExecuResponse.ResultBean groupExecu = mListGroupExecu.get(position);
                showGroupexecuInfoDialog(groupExecu);
            }

            @Override
            public void onImgOptionsClick(View view, int position) {
                GroupExecuResponse.ResultBean groupExecu = mListGroupExecu.get(position);

                String groupID = groupExecu.getId();
                String groupName = groupExecu.getName();
                String groupDescrip = groupExecu.getDescription();
                Boolean groupType = groupExecu.isStatus();
                int groupAutoTime = groupExecu.getAutoTime();
                String functionID = groupExecu.getFunction().getId();
                String functionName = groupExecu.getFunction().getName();

                intentDetailActivity(groupID, groupName, groupDescrip, functionID, functionName, groupType, groupAutoTime);
            }
        });

        mFabCreateGroupExecu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, GroupExecuAddActivity.class));
            }
        });
        mFabGoneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabInfoGroupExecu.setVisibility(View.GONE);
            }
        });
    }

    private void intentDetailActivity(String groupID, String groupName, String groupDescrip, String functionID,
                                      String functionName, Boolean groupType, int groupAutoTime) {
        Intent intent = new Intent(mContext, GroupExecuDetailActivity.class);
        Bundle groupBundle = new Bundle();
        groupBundle.putString(GROUPEXECU_ID, groupID);
        groupBundle.putString(GROUPEXECU_NAME, groupName);
        groupBundle.putString(GROUPEXECU_DESCRIPTION, groupDescrip);
        groupBundle.putString(FUNCTION_ID, functionID);
        groupBundle.putString(FUNCTION_NAME, functionName);
        groupBundle.putBoolean(GROUPEXECU_STATUS, groupType);
        groupBundle.putInt(GROUPEXECU_AUTOTIME, groupAutoTime);
        intent.putExtra(TAG, groupBundle);
        mContext.startActivity(intent);
    }

    private void showGroupexecuInfoDialog(GroupExecuResponse.ResultBean groupExecu) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Thông tin điều kiện thực thi");
        builder.setCancelable(false); //click outSide to dismiss dialog
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View alertLayout = inflater.inflate(R.layout.dialog_group_condition, null);
        builder.setView(alertLayout);

        //addControls
        TextView editDeviceName = alertLayout.findViewById(R.id.edit_device_name);
        TextView editDeviceDescribe = alertLayout.findViewById(R.id.edit_device_describe);
        TextView editFunctionName = alertLayout.findViewById(R.id.edit_type_device);
        TextView editTime = alertLayout.findViewById(R.id.edit_area_name);
        TextView editGroupType = alertLayout.findViewById(R.id.edit_type_group);
        TextView txtFunctionName = alertLayout.findViewById(R.id.txt_type_device);
        TextView txtTime = alertLayout.findViewById(R.id.txt_area_name);

        //addEvents
        editDeviceName.setText(groupExecu.getName());
        editDeviceDescribe.setText(groupExecu.getDescription());
        editFunctionName.setText(groupExecu.getFunction().getName());
        editTime.setText(String.valueOf(groupExecu.getAutoTime()));

        if(groupExecu.isStatus()){
            editGroupType.setText("Bật");
        }else{
            editGroupType.setText("Tắt");
        }
        txtFunctionName.setText("Tên van điện từ");
        txtTime.setText("Thời gian hoạt động");

        builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getListGroupExecution(String functionID) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Loading...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<GroupExecuResponse> callGroup = apiService.getGroupExecuByFunction(functionID);
        callGroup.enqueue(new Callback<GroupExecuResponse>() {
            @Override
            public void onResponse(Call<GroupExecuResponse> call, Response<GroupExecuResponse> response) {
                if (response.isSuccessful()) {
                    initGroupExecution(response.body());
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<GroupExecuResponse> call, Throwable t) {
                Toast.makeText(mContext, "Get Info Failed " + t.toString(), Toast.LENGTH_SHORT).show();
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void initGroupExecution(GroupExecuResponse body) {
        if (body.getResult() != null) {
            List<GroupExecuResponse.ResultBean> listGroup = body.getResult();
            mListGroupExecu.clear();
            for (GroupExecuResponse.ResultBean a : listGroup) {
                mListGroupExecu.add(a);
            }
            mGroupExecuAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getActuators();
                mFabInfoGroupExecu.setVisibility(View.VISIBLE);
                srlLayout.setRefreshing(false);
            }
        }, 2500);
    }

    private void getActuators() {
        ProgressDialogLoader.progressdialog_creation(mContext, "Loading...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        retrofit2.Call<ActuatorsResponse> callActuator = apiService.getActuators();
        callActuator.enqueue(new Callback<ActuatorsResponse>() {
            @Override
            public void onResponse(Call<ActuatorsResponse> call, Response<ActuatorsResponse> response) {
                if (response.isSuccessful()) {
                    initActuator(response.body());
                }
                ProgressDialogLoader.progressdialog_dismiss();

            }

            @Override
            public void onFailure(Call<ActuatorsResponse> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void initActuator(ActuatorsResponse body) {
        listActuators = new ArrayList<>();
        if (body.getResult() != null) {
            listActuators = body.getResult();
            arrActuatorName.clear();
            for (ActuatorsResponse.ResultBean a : listActuators) {
                arrActuatorName.add(a.getName());
            }
            actuatorAdapter.notifyDataSetChanged();
        }
    }

    private void getFunctionsByActuatorID(String actuatorID) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Loading...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<FunctionByAcResponse> callService = apiService.getFunctionByActuatorId(actuatorID);
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
        listFunctions = new ArrayList<>();
        if (body.getResult() != null) {
            listFunctions = body.getResult();
            arrFunctionName.clear();
            for (FunctionByAcResponse.Result a : listFunctions) {
                arrFunctionName.add(a.getName());
            }
            functionAdapter.notifyDataSetChanged();
        }
    }
}
