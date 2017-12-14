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
import android.view.Menu;
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
import com.vuduc.adapters.ExecuConditionAdapter;
import com.vuduc.models.DeviceNodeResponse;
import com.vuduc.models.ExecuConditionByGroupResponse;
import com.vuduc.models.FunctionsResponse;
import com.vuduc.models.GroupExecuResponse;
import com.vuduc.models.Node;
import com.vuduc.network.ApiUtils;
import com.vuduc.network.SprayIoTApiInterface;
import com.vuduc.until.Logger;
import com.vuduc.until.ProgressDialogLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupExecuDetailActivity extends AppCompatActivity {

    private static final String TAG = GroupExecuDetailActivity.class.getSimpleName();
    @BindView(R.id.edit_device_name)
    EditText editGroupExecuName;
    @BindView(R.id.edit_node_describe)
    EditText editGroupExecuDescription;
    @BindView(R.id.edit_function)
    TextView editFunction;
    @BindView(R.id.edit_group_autoTime)
    EditText editGroupAutoTime;
    @BindView(R.id.btn_function)
    ImageView btnFunction;
    @BindView(R.id.img_options)
    ImageView imgOptions;
    @BindView(R.id.spinner_list_function)
    MaterialSpinner spinnerListFunction;
    @BindView(R.id.rv_execuCondition)
    RecyclerView rvExecuCondition;
    @BindView(R.id.srlLayout)
    SwipeRefreshLayout srlLayout;
    @BindView(R.id.edit_type_group)
    TextView editTypeGroup;
    @BindView(R.id.btn_group_type)
    ImageView btnGroupType;
    @BindView(R.id.spinner_list_group_type)
    MaterialSpinner spinnerListGroupType;
    @BindView(R.id.fab_info_actuator)
    FloatingActionMenu mFabInfoGroupExecu;
    @BindView(R.id.fab_create_actuator)
    FloatingActionButton mFabCreateExecu;
    @BindView(R.id.fab_gone_fab)
    FloatingActionButton mFabGoneFab;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    List<FunctionsResponse.ResultBean> mListFunction;
    List<String> arrFunctionName;
    List<ExecuConditionByGroupResponse.ResultBean> mListExecuCondition;
    String[] arrGroupType = {"Bật", "Tắt"};

    //TODO: Valuable update condition dialog
    EditText editConditionName, editConditionDescribe, editCompareValue;
    TextView editCompare, editNodeName, editDeviceName;
    ImageView btnCompare, btnNode, btnDevice;
    MaterialSpinner spinnerListTypeCompare, spinnerListNode, spinnerListDevice;

    List<Node.ResultBean> listNodes;
    List<String> arrNodeName = new ArrayList<>();
    List<DeviceNodeResponse.Result> listDeviceNodeByID;
    List<String> arrDeviceNode = new ArrayList<>();
    String[] arrCompareType = {"=", "<", ">"};

    int compareType, compareValue;
    String conditionID, deviceNodeId, nodeID;

    ArrayAdapter<String> dialogNodeAdapter;
    ArrayAdapter<String> dialogDeviceNodeAdapter;
    ArrayAdapter<String> dialogCompareTypeAdapter;

    private Context mContext;
    private ArrayAdapter<String> mFunctionAdapter, mGroupTypeAdapter;
    private ExecuConditionAdapter mExecuConditionAdapter;
    private String mGroupExecuID, mFunctionID;
    private Boolean mGroupExecuStatus = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_execu_detail);
        mContext = this;

        arrFunctionName = new ArrayList<>();
        //TODO: get list Function
        new Thread(new Runnable() {
            @Override
            public void run() {
                getFunctions();
            }
        }).start();

        addControls();
        initData();
        addEvents();
    }

    private void getFunctions() {
        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        retrofit2.Call<FunctionsResponse> callFunction = apiService.getFunctions();
        callFunction.enqueue(new Callback<FunctionsResponse>() {
            @Override
            public void onResponse(retrofit2.Call<FunctionsResponse> call, Response<FunctionsResponse> response) {
                if (response.isSuccessful()) {
                    initFunction(response.body());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<FunctionsResponse> call, Throwable t) {

            }
        });
    }

    private void initFunction(FunctionsResponse body) {
        mListFunction = new ArrayList<>();
        if (body.getResult() != null) {
            mListFunction = body.getResult();
            arrFunctionName.clear();
            for (FunctionsResponse.ResultBean a : mListFunction) {
                arrFunctionName.add(a.getName());
            }
            mFunctionAdapter.notifyDataSetChanged();
        }
    }

    private void initData() {
        Intent callerIntent = getIntent();
        Bundle packageFromCaller = callerIntent.getBundleExtra(GroupExecuConditionActivity.TAG);
        mGroupExecuID = packageFromCaller.getString(GroupExecuConditionActivity.GROUPEXECU_ID);
        mFunctionID = packageFromCaller.getString(GroupExecuConditionActivity.FUNCTION_ID);
        mGroupExecuStatus = packageFromCaller.getBoolean(GroupExecuConditionActivity.GROUPEXECU_STATUS);
        String groupExecuName = packageFromCaller.getString(GroupExecuConditionActivity.GROUPEXECU_NAME);
        String groupExecuDescription = packageFromCaller.getString(GroupExecuConditionActivity.GROUPEXECU_DESCRIPTION);
        String functionName = packageFromCaller.getString(GroupExecuConditionActivity.FUNCTION_NAME);
        int groupAutoTime = packageFromCaller.getInt(GroupExecuConditionActivity.GROUPEXECU_AUTOTIME);

        editGroupExecuName.setText(groupExecuName);
        editGroupExecuDescription.setText(groupExecuDescription);
        editFunction.setText(functionName);
        editGroupAutoTime.setText(groupAutoTime + "");

        if (mGroupExecuStatus) {
            editTypeGroup.setText("Bật");
        } else {
            editTypeGroup.setText("Tắt");
        }

        if (mGroupExecuID != null) {
            getExecuConditionByGroup(mGroupExecuID);
        }
    }

    private void getExecuConditionByGroup(String groupExecuID) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Loading...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<ExecuConditionByGroupResponse> callExecu = apiService.getExecutionConditionByGroup(groupExecuID);
        callExecu.enqueue(new Callback<ExecuConditionByGroupResponse>() {
            @Override
            public void onResponse(Call<ExecuConditionByGroupResponse> call, Response<ExecuConditionByGroupResponse> response) {
                if (response.isSuccessful()) {
                    initExecuCondition(response.body());
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<ExecuConditionByGroupResponse> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void initExecuCondition(ExecuConditionByGroupResponse body) {
        if (body.getResult() != null) {
            List<ExecuConditionByGroupResponse.ResultBean> data = body.getResult();
            mListExecuCondition.clear();
            for (ExecuConditionByGroupResponse.ResultBean a : data) {
                mListExecuCondition.add(a);
            }
            mExecuConditionAdapter.notifyDataSetChanged();
        }
    }

    private void addEvents() {
        btnFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Hiển thị danh sách van điện từ", Toast.LENGTH_SHORT).show();

                spinnerListFunction.setVisibility(View.VISIBLE);
                spinnerListFunction.performClick();

                spinnerListFunction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != -1) {
                            editFunction.setText(arrFunctionName.get(i));
                            mFunctionID = mListFunction.get(i).getId();
                        }
                        spinnerListFunction.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });

        btnGroupType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Loại điều kiện", Toast.LENGTH_SHORT).show();

                spinnerListGroupType.setVisibility(View.VISIBLE);
                spinnerListGroupType.performClick();

                spinnerListGroupType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            editTypeGroup.setText("Bật");
                            mGroupExecuStatus = true;
                        } else {
                            editTypeGroup.setText("Tắt");
                            mGroupExecuStatus = false;
                        }
                        spinnerListGroupType.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        spinnerListGroupType.setVisibility(View.GONE);
                    }
                });
            }
        });

        imgOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOptionGroupPopup(view);
            }
        });

        mExecuConditionAdapter.SetOnItemClickListener(new ExecuConditionAdapter.OnItemClickListener() {
            @Override
            public void onItemViewClick(View view, int position) {
                ExecuConditionByGroupResponse.ResultBean execuCondition = mListExecuCondition.get(position);
                showConditionInfoDialog(execuCondition);
            }

            @Override
            public void onImgOptionsClick(View view, int position) {
                showOptionExecuPopup(view, position);
            }
        });

        mFabGoneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabInfoGroupExecu.setVisibility(View.GONE);
            }
        });

        mFabCreateExecu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ConditionAddActivity.class));
            }
        });
    }

    private void showOptionGroupPopup(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_info_node, popup.getMenu());
        popup.setOnMenuItemClickListener(new OptionGroupPopupListener());
        popup.show();
    }

    private void showConditionInfoDialog(ExecuConditionByGroupResponse.ResultBean execuCondition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Thông tin điều kiện thực thi");
        builder.setCancelable(false); //click outSide to dismiss dialog
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View alertLayout = inflater.inflate(R.layout.dialog_actuator, null);
        builder.setView(alertLayout);

        //addControls
        TextView editDeviceName = alertLayout.findViewById(R.id.edit_device_name);
        TextView editDeviceDescribe = alertLayout.findViewById(R.id.edit_device_describe);
        TextView editGroupName = alertLayout.findViewById(R.id.edit_type_device);
        TextView editDeviceNodeName = alertLayout.findViewById(R.id.edit_area_name);
        TextView txtGroupName = alertLayout.findViewById(R.id.txt_type_device);
        TextView txtDeviceNodeName = alertLayout.findViewById(R.id.txt_area_name);

        //addEvents
        editDeviceName.setText(execuCondition.getName());
        editDeviceDescribe.setText(execuCondition.getDescription());
        editGroupName.setText(execuCondition.getGroupExecutionCondition().getName());
        editDeviceNodeName.setText(execuCondition.getDeviceNode().getName());
        txtGroupName.setText("Nhóm điều kiện");
        txtDeviceNodeName.setText("Cảm biến");

        builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showOptionExecuPopup(View view, int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_info_node, popup.getMenu());
        popup.setOnMenuItemClickListener(new OptionExecuPopupListener(position));
        popup.show();
    }

    private void addControls() {
        ButterKnife.bind(this);

        mFabInfoGroupExecu.setVisibility(View.VISIBLE);

        //Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Điều kiện thực thi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Function spinner
        mFunctionAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, arrFunctionName);
        mFunctionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerListFunction.setAdapter(mFunctionAdapter);

        //Group type spinner
        mGroupTypeAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, arrGroupType);
        mGroupTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerListGroupType.setAdapter(mGroupTypeAdapter);

        //Execution Condition RecycleView
        mListExecuCondition = new ArrayList<>();
        mExecuConditionAdapter = new ExecuConditionAdapter(mContext, mListExecuCondition);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvExecuCondition.setLayoutManager(layoutManager);
        rvExecuCondition.setItemAnimator(new DefaultItemAnimator());
        rvExecuCondition.setNestedScrollingEnabled(false);
        rvExecuCondition.setAdapter(mExecuConditionAdapter);

        srlLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mGroupExecuID != null) {
                            getExecuConditionByGroup(mGroupExecuID);
                        }
                        mFabInfoGroupExecu.setVisibility(View.VISIBLE);
                        srlLayout.setRefreshing(false);
                    }
                }, 1500);
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

    private void showUpđateCondionInfo(ExecuConditionByGroupResponse.ResultBean condition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Điều kiện thực thi");
        builder.setCancelable(false); //click outSide to dismiss dialog
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View alertLayout = inflater.inflate(R.layout.dialog_update_condition, null);
        builder.setView(alertLayout);

        //add Controls
        addControlsUpdateCondition(alertLayout);

        //init Info
        initConditionInfoDialog(condition);

        //addEvents
        addEventsUpdateCondition();


        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String a = String.valueOf(editCompareValue.getText());
                compareValue = Integer.parseInt(a);
                String conditionName = String.valueOf(editConditionName.getText());
                String conditionDes = String.valueOf(editConditionDescribe.getText());
                requestUpdateConditionInfo(conditionID, conditionName, conditionDes, compareType, compareValue, deviceNodeId);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void requestUpdateConditionInfo(String conditionID, String conditionName, String conditionDes, int compareType,
                                            int compareValue, String deviceNodeId) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Updating...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<ExecuConditionByGroupResponse> callCondition = apiService.updateInfoCondition(conditionID, conditionName, conditionDes, compareType, compareValue, deviceNodeId);
        callCondition.enqueue(new Callback<ExecuConditionByGroupResponse>() {
            @Override
            public void onResponse(Call<ExecuConditionByGroupResponse> call, Response<ExecuConditionByGroupResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.toast_update_info_successful, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, R.string.toast_update_info_fail, Toast.LENGTH_SHORT).show();
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<ExecuConditionByGroupResponse> call, Throwable t) {
                Logger.d(TAG, t.toString());
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    //TODO: add Events for update Condition info Dialog
    private void addEventsUpdateCondition() {
        btnCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerListTypeCompare.setVisibility(View.VISIBLE);
                spinnerListTypeCompare.performClick();

                spinnerListTypeCompare.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            editCompare.setText("=");
                            compareType = 0;
                        } else if (i == 1) {
                            editCompare.setText("<");
                            compareType = 1;
                        } else if (i == 2) {
                            editCompare.setText(">");
                            compareType = 2;
                        }
                        spinnerListTypeCompare.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        spinnerListTypeCompare.setVisibility(View.GONE);
                    }
                });
            }
        });

        btnNode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerListNode.setVisibility(View.VISIBLE);
                spinnerListNode.performClick();

                spinnerListNode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != -1) {
                            editNodeName.setText(listNodes.get(i).getName());
                            nodeID = listNodes.get(i).getId();

                            editDeviceName.setText("null");
                        }
                        spinnerListNode.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        spinnerListNode.setVisibility(View.GONE);
                    }
                });
            }
        });

        btnDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nodeID != null) {
                    getDeviceNodeByNode(nodeID);
                    spinnerListDevice.setVisibility(View.VISIBLE);
                    spinnerListDevice.performClick();

                    spinnerListDevice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (i != -1) {
                                editDeviceName.setText(listDeviceNodeByID.get(i).getName());
                                deviceNodeId = listDeviceNodeByID.get(i).getId();
                            }
                            spinnerListDevice.setVisibility(View.GONE);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            spinnerListDevice.setVisibility(View.GONE);
                        }
                    });
                } else {
                    Toast.makeText(mContext, "Xin lỗi! bạn chưa chọn NODE", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getDeviceNodeByNode(String nodeID) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Loading...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<DeviceNodeResponse> callDeviceNode = apiService.getDeviceNodeByNode(nodeID);
        callDeviceNode.enqueue(new Callback<DeviceNodeResponse>() {
            @Override
            public void onResponse(Call<DeviceNodeResponse> call, Response<DeviceNodeResponse> response) {
                if (response.isSuccessful()) {
                    initDeviceNodeByNode(response.body());
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<DeviceNodeResponse> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void initDeviceNodeByNode(DeviceNodeResponse body) {
        if (body.getResult() != null) {
            listDeviceNodeByID = body.getResult();
            arrDeviceNode.clear();
            for (DeviceNodeResponse.Result a : listDeviceNodeByID) {
                arrDeviceNode.add(a.getName());
            }
            dialogDeviceNodeAdapter.notifyDataSetChanged();
        }
    }

    //TODO: init view for update Condition info Dialog
    private void initConditionInfoDialog(ExecuConditionByGroupResponse.ResultBean condition) {
        editConditionName.setText(condition.getName());
        editConditionDescribe.setText(condition.getDescription());
        editDeviceName.setText(condition.getDeviceNode().getName());
        editCompareValue.setText(condition.getCompareValue() + "");
        initConditionCompareType(condition);
        initNodeName(condition);

        conditionID = condition.getId();
        compareType = condition.getCompare();
        deviceNodeId = condition.getDeviceNode().getId();
    }

    private void initNodeName(ExecuConditionByGroupResponse.ResultBean condition) {
        nodeID = condition.getDeviceNode().getNodeId();
        getNodesForDialog(nodeID);
    }

    private void getNodesForDialog(final String nodeID) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Loading...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<Node> callNodes = apiService.getAllNode();
        callNodes.enqueue(new Callback<Node>() {
            @Override
            public void onResponse(Call<Node> call, Response<Node> response) {
                if (response.isSuccessful())
                    initNodesForDialog(response.body(), nodeID);
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<Node> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void initNodesForDialog(Node body, String nodeID) {
        if (body.getResult() != null) {
            listNodes = body.getResult();
            arrNodeName.clear();
            for (Node.ResultBean a : listNodes) {
                arrNodeName.add(a.getName());
                if (a.getId().equals(nodeID)) {
                    editNodeName.setText(a.getName());
                }
            }
            dialogNodeAdapter.notifyDataSetChanged();
        }
    }

    private void initConditionCompareType(ExecuConditionByGroupResponse.ResultBean condition) {
        int compareType = condition.getCompare();
        switch (compareType) {
            case 0:
                editCompare.setText("=");
                break;
            case 1:
                editCompare.setText("<");
                break;
            case 2:
                editCompare.setText(">");
                break;
            default:
                editCompare.setText("null");
        }
    }

    //TODO: add Controls for update Condition info Dialog
    private void addControlsUpdateCondition(View alertLayout) {
        editConditionName = alertLayout.findViewById(R.id.edit_condition_name);
        editConditionDescribe = alertLayout.findViewById(R.id.edit_condition_describe);
        editCompare = alertLayout.findViewById(R.id.edit_compare);
        btnCompare = alertLayout.findViewById(R.id.btn_compare);
        spinnerListTypeCompare = alertLayout.findViewById(R.id.spinner_list_type_compare);
        editCompareValue = alertLayout.findViewById(R.id.edit_compare_value);
        editNodeName = alertLayout.findViewById(R.id.edit_node_name);
        btnNode = alertLayout.findViewById(R.id.btn_node);
        spinnerListNode = alertLayout.findViewById(R.id.spinner_list_node);
        editDeviceName = alertLayout.findViewById(R.id.edit_device_name);
        btnDevice = alertLayout.findViewById(R.id.btn_device);
        spinnerListDevice = alertLayout.findViewById(R.id.spinner_list_device);

        //Compare type adpater
        dialogCompareTypeAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, arrCompareType);
        dialogCompareTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerListTypeCompare.setAdapter(dialogCompareTypeAdapter);

        //Node adapter
        dialogNodeAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, arrNodeName);
        dialogNodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerListNode.setAdapter(dialogNodeAdapter);

        //DeviceNode adapter
        dialogDeviceNodeAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, arrDeviceNode);
        dialogDeviceNodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerListDevice.setAdapter(dialogDeviceNodeAdapter);
    }

    private void requestUpdateGroupExecu(String groupExecuID, String groupName, String groupDescription, String functionID,
                                         Boolean groupExecuStatus, int groupAutoTime) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Updating...");

        SprayIoTApiInterface apiInterface = ApiUtils.getSprayIoTApiService();
        Call<GroupExecuResponse> callGroup = apiInterface.updateGroupExecu(groupExecuID, groupName, groupDescription, functionID, groupExecuStatus, groupAutoTime);
        callGroup.enqueue(new Callback<GroupExecuResponse>() {
            @Override
            public void onResponse(Call<GroupExecuResponse> call, Response<GroupExecuResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.toast_update_info_successful, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, R.string.toast_update_info_fail, Toast.LENGTH_SHORT).show();
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<GroupExecuResponse> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private class OptionExecuPopupListener implements PopupMenu.OnMenuItemClickListener {
        int mPostion;

        private OptionExecuPopupListener(int postion) {
            mPostion = postion;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_update_info:
                    ExecuConditionByGroupResponse.ResultBean condition = mListExecuCondition.get(mPostion);
                    showUpđateCondionInfo(condition);
                    return true;
                case R.id.action_delete_info:
                    Toast.makeText(mContext, "onItemViewClick", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    }

    private class OptionGroupPopupListener implements PopupMenu.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_update_info:
                    String groupName = String.valueOf(editGroupExecuName.getText());
                    String groupDescription = String.valueOf(editGroupExecuDescription.getText());
                    int groupAutoTime = Integer.parseInt(String.valueOf(editGroupAutoTime.getText()));

                    requestUpdateGroupExecu(mGroupExecuID, groupName, groupDescription, mFunctionID, mGroupExecuStatus, groupAutoTime);
                    return true;
                case R.id.action_delete_info:
                    Toast.makeText(mContext, "onItemViewClick", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    }
}
