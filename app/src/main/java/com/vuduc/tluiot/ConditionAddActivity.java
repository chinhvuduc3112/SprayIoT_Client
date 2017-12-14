package com.vuduc.tluiot;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vuduc.models.ActuatorsResponse;
import com.vuduc.models.DeviceNodeResponse;
import com.vuduc.models.ExecuConditionByGroupResponse;
import com.vuduc.models.FunctionByAcResponse;
import com.vuduc.models.GroupExecuResponse;
import com.vuduc.models.Node;
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

public class ConditionAddActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.edit_condition_name)
    EditText editConditionName;
    @BindView(R.id.edit_condition_describe)
    EditText editConditionDescribe;

    @BindView(R.id.edit_compare)
    TextView editCompare;
    @BindView(R.id.btn_compare)
    ImageView btnCompare;
    @BindView(R.id.spinner_list_type_compare)
    MaterialSpinner spinnerListTypeCompare;
    @BindView(R.id.edit_compare_value)
    EditText editCompareValue;

    @BindView(R.id.edit_node_name)
    TextView editNodeName;
    @BindView(R.id.btn_node)
    ImageView btnNode;
    @BindView(R.id.spinner_list_node)
    MaterialSpinner spinnerListNode;

    @BindView(R.id.edit_device_name)
    TextView editDeviceName;
    @BindView(R.id.btn_device)
    ImageView btnDevice;
    @BindView(R.id.spinner_list_device)
    MaterialSpinner spinnerListDevice;

    @BindView(R.id.edit_actuator_name)
    TextView editActuatorName;
    @BindView(R.id.btn_actuator)
    ImageView btnActuator;
    @BindView(R.id.spinner_list_actuator)
    MaterialSpinner spinnerListActuator;

    @BindView(R.id.edit_function_name)
    TextView editFunctionName;
    @BindView(R.id.btn_function)
    ImageView btnFunction;
    @BindView(R.id.spinner_list_function)
    MaterialSpinner spinnerListFunction;

    @BindView(R.id.edit_group_name)
    TextView editGroupName;
    @BindView(R.id.btn_group)
    ImageView btnGroup;
    @BindView(R.id.spinner_list_group)
    MaterialSpinner spinnerListGroup;

    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_submit)
    Button btnSubmit;


    String[] arrCompareType = {"=", "<", ">"};
    List<Node.ResultBean> listNodes;
    List<String> arrNodeName = new ArrayList<>();
    List<DeviceNodeResponse.Result> listDeviceNodeByID;
    List<String> arrDeviceNode = new ArrayList<>();
    List<ActuatorsResponse.ResultBean> listActuators;
    List<String> arrActuatorName = new ArrayList<>();
    List<FunctionByAcResponse.Result> listFunctions;
    List<String> arrFunctionName = new ArrayList<>();
    List<GroupExecuResponse.ResultBean> listGroups;
    List<String> arrGroupName = new ArrayList<>();

    ArrayAdapter<String> mCompareTypeAdapter, mNodeAdapter, mDeviceNodeAdapter, mActuatorAdapter, mFunctionAdapter, mGroupConditionAdapter;

    private Context mContext;
    private String mNodeID, mDeviceNodeID, mActuatorID, mFunctionID, mGroupExecuID;
    private int mCompareType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition_add);
        mContext = this;
        addControls();
        addEvents();
    }

    private void addControls() {

        ButterKnife.bind(this);

        //Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Thêm mới điều kiện");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Compare type adapter
        mCompareTypeAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, arrCompareType);
        mCompareTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerListTypeCompare.setAdapter(mCompareTypeAdapter);

        //Node adapter
        mNodeAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, arrNodeName);
        mNodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerListNode.setAdapter(mNodeAdapter);

        //DeviceNode adapter
        mDeviceNodeAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, arrDeviceNode);
        mDeviceNodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerListDevice.setAdapter(mDeviceNodeAdapter);

        //Actuator adapter
        mActuatorAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, arrActuatorName);
        mActuatorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerListActuator.setAdapter(mActuatorAdapter);

        //Function by actuator adapter
        mFunctionAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, arrFunctionName);
        mFunctionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerListFunction.setAdapter(mFunctionAdapter);

        //Group execu condition adapter
        mGroupConditionAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, arrGroupName);
        mGroupConditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerListGroup.setAdapter(mGroupConditionAdapter);
    }

    private void addEvents() {
        btnCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Chọn loại so sánh", Toast.LENGTH_SHORT).show();

                spinnerListTypeCompare.setVisibility(View.VISIBLE);
                spinnerListTypeCompare.performClick();

                spinnerListTypeCompare.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            editCompare.setText(arrCompareType[0]);
                            mCompareType = 0;
                        } else if (i == 1) {
                            editCompare.setText(arrCompareType[1]);
                            mCompareType = 1;
                        } else if (i == 2) {
                            editCompare.setText(arrCompareType[2]);
                            mCompareType = 2;
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
                Toast.makeText(mContext, "Chọn Node cảm biến", Toast.LENGTH_SHORT).show();

                getNodes();

                spinnerListNode.setVisibility(View.VISIBLE);
                spinnerListNode.performClick();

                spinnerListNode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != -1) {
                            editNodeName.setText(listNodes.get(i).getName());
                            mNodeID = listNodes.get(i).getId();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });

        btnDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mNodeID != null) {
                    Toast.makeText(mContext, "Chọn cảm biến", Toast.LENGTH_SHORT).show();

                    getDeviceNodeByNode(mNodeID);

                    spinnerListDevice.setVisibility(View.VISIBLE);
                    spinnerListDevice.performClick();

                    spinnerListDevice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (i != -1) {
                                editDeviceName.setText(listDeviceNodeByID.get(i).getName());
                                mDeviceNodeID = listDeviceNodeByID.get(i).getId();
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

        btnActuator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Chọn actuator", Toast.LENGTH_SHORT).show();

                getActuator();

                spinnerListActuator.setVisibility(View.VISIBLE);
                spinnerListActuator.performClick();
                spinnerListActuator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != -1) {
                            editActuatorName.setText(listActuators.get(i).getName());
                            mActuatorID = listActuators.get(i).getId();
                        }
                        spinnerListActuator.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        spinnerListActuator.setVisibility(View.GONE);
                    }
                });
            }
        });

        btnFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActuatorID != null) {
                    Toast.makeText(mContext, "Chọn function", Toast.LENGTH_SHORT).show();

                    getFunctionByActuator(mActuatorID);

                    spinnerListFunction.setVisibility(View.VISIBLE);
                    spinnerListFunction.performClick();

                    spinnerListFunction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (i != -1) {
                                editFunctionName.setText(listFunctions.get(i).getName());
                                mFunctionID = listFunctions.get(i).getId();
                            }
                            spinnerListFunction.setVisibility(View.GONE);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            spinnerListFunction.setVisibility(View.GONE);
                        }
                    });
                } else {
                    Toast.makeText(mContext, "Xin lỗi! bạn chưa chọn Actuator", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFunctionID != null) {
                    Toast.makeText(mContext, "Chọn nhóm điều kiện", Toast.LENGTH_SHORT).show();

                    getGroupByFunction(mFunctionID);

                    spinnerListGroup.setVisibility(View.VISIBLE);
                    spinnerListGroup.performClick();

                    spinnerListGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (i != -1) {
                                editGroupName.setText(listGroups.get(i).getName());
                                mGroupExecuID = listGroups.get(i).getId();
                            }
                            spinnerListGroup.setVisibility(View.GONE);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            spinnerListGroup.setVisibility(View.GONE);
                        }
                    });
                } else {
                    Toast.makeText(mContext, "Xin lỗi! bạn chưa chọn Function", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = String.valueOf(editConditionName.getText());
                String description = String.valueOf(editConditionDescribe.getText());
                int compareValue = Integer.parseInt(String.valueOf(editCompareValue.getText()));

                if (mDeviceNodeID != null & mGroupExecuID != null) {
                    requestAddCondition(name, description, mCompareType, compareValue, mDeviceNodeID, mGroupExecuID);
                } else {
                    Toast.makeText(mContext, "Xin lỗi! Bạn chưa chọn đủ thông tin yêu cầu!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void requestAddCondition(String name, String description, int compareType, int compareValue, String deviceNodeID, String groupExecuID) {
        SprayIoTApiInterface apiInterface = ApiUtils.getSprayIoTApiService();
        Call<ExecuConditionByGroupResponse> callCondition = apiInterface.addCondition(name, description, groupExecuID, deviceNodeID, compareType, compareValue);
        callCondition.enqueue(new Callback<ExecuConditionByGroupResponse>() {
            @Override
            public void onResponse(Call<ExecuConditionByGroupResponse> call, Response<ExecuConditionByGroupResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.toast_add_info_successful, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, R.string.toast_add_info_fail, Toast.LENGTH_SHORT).show();
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<ExecuConditionByGroupResponse> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void getGroupByFunction(String functionID) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Loading...");

        SprayIoTApiInterface apiInterface = ApiUtils.getSprayIoTApiService();
        Call<GroupExecuResponse> callGroup = apiInterface.getGroupExecuByFunction(functionID);
        callGroup.enqueue(new Callback<GroupExecuResponse>() {
            @Override
            public void onResponse(Call<GroupExecuResponse> call, Response<GroupExecuResponse> response) {
                if (response.isSuccessful()) {
                    initGroup(response.body());
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            private void initGroup(GroupExecuResponse body) {
                if (body.getResult() != null) {
                    listGroups = body.getResult();
                    arrGroupName.clear();
                    for (GroupExecuResponse.ResultBean a : listGroups) {
                        arrGroupName.add(a.getName());
                    }
                    mGroupConditionAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GroupExecuResponse> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void getFunctionByActuator(String actuatorID) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Loading...");

        SprayIoTApiInterface apiInterface = ApiUtils.getSprayIoTApiService();
        Call<FunctionByAcResponse> callFunction = apiInterface.getFunctionByActuatorId(actuatorID);
        callFunction.enqueue(new Callback<FunctionByAcResponse>() {
            @Override
            public void onResponse(Call<FunctionByAcResponse> call, Response<FunctionByAcResponse> response) {
                if (response.isSuccessful()) {
                    initFunctions(response.body());
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            private void initFunctions(FunctionByAcResponse body) {
                if (body.getResult() != null) {
                    listFunctions = body.getResult();
                    arrFunctionName.clear();
                    for (FunctionByAcResponse.Result a : listFunctions) {
                        arrFunctionName.add(a.getName());
                    }
                    mFunctionAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<FunctionByAcResponse> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void getActuator() {
        ProgressDialogLoader.progressdialog_creation(mContext, "Loading...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<ActuatorsResponse> callActuator = apiService.getActuators();
        callActuator.enqueue(new Callback<ActuatorsResponse>() {
            @Override
            public void onResponse(Call<ActuatorsResponse> call, Response<ActuatorsResponse> response) {
                if (response.isSuccessful()) {
                    initActuator(response.body());
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            private void initActuator(ActuatorsResponse body) {
                if (body.getResult() != null) {
                    listActuators = body.getResult();
                    arrActuatorName.clear();
                    for (ActuatorsResponse.ResultBean a : listActuators) {
                        arrActuatorName.add(a.getName());
                    }
                    mActuatorAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ActuatorsResponse> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
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

            private void initDeviceNodeByNode(DeviceNodeResponse body) {
                if (body.getResult() != null) {
                    listDeviceNodeByID = body.getResult();
                    arrDeviceNode.clear();
                    for (DeviceNodeResponse.Result a : listDeviceNodeByID) {
                        arrDeviceNode.add(a.getName());
                    }
                    mDeviceNodeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<DeviceNodeResponse> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void getNodes() {
        ProgressDialogLoader.progressdialog_creation(mContext, "Loading...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<Node> callNodes = apiService.getAllNode();
        callNodes.enqueue(new Callback<Node>() {
            @Override
            public void onResponse(Call<Node> call, Response<Node> response) {
                if (response.isSuccessful())
                    initNode(response.body());
                ProgressDialogLoader.progressdialog_dismiss();
            }

            private void initNode(Node body) {
                if (body.getResult() != null) {
                    listNodes = body.getResult();
                    arrNodeName.clear();
                    for (Node.ResultBean a : listNodes) {
                        arrNodeName.add(a.getName());

                    }
                    mNodeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Node> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }
}
