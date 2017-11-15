package com.vuduc.tluiot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vuduc.adapters.DeviceNodeAdapter;
import com.vuduc.fragments.NodeInfoFragment;
import com.vuduc.models.DeviceNodeResponse;
import com.vuduc.models.DeviceTypeResponse;
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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceNodeUpdateActivity extends AppCompatActivity {
    public static final String TAG = DeviceNodeUpdateActivity.class.getSimpleName();
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.btn_edit_area)
    ImageView btnEditNode;
    @BindView(R.id.spinner_list_area)
    MaterialSpinner spinListNode;
    @BindView(R.id.edit_node_area)
    TextView editNodeName;
    @BindView(R.id.edit_device_name)
    EditText editDeviceName;
    @BindView(R.id.edit_node_describe)
    EditText editNodeDescribe;
    @BindView(R.id.edit_device_type)
    TextView editDeviceType;
    @BindView(R.id.btn_edit_device_type)
    ImageView btnEditDeviceType;
    @BindView(R.id.spinner_list_device_type)
    MaterialSpinner spinListDeviceType;
    @BindView(R.id.edit_node_note)
    EditText editNodeNote;
    @BindView(R.id.img_options)
    ImageView imgOptions;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    List<Node.ResultBean> listNodes = null;
    List<String> arrNodeName;
    List<DeviceTypeResponse.ResultBean> listDeviceType = null;
    List<String> arrDeviceTypeName;

    private String mDeviceNode_ID;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_node_update);
        mContext = this;
        addControls();
        initTextView();
        addEvents();
    }

    private void addControls() {
        ButterKnife.bind(this);

        //Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.sensor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        arrNodeName = new ArrayList<>();
        arrDeviceTypeName = new ArrayList<>();

        //GONE Spinner List Area name
        ArrayAdapter<String> adapterAreas = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, arrNodeName);
        adapterAreas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinListNode.setAdapter(adapterAreas);

        //GONE Spinner List Device type name
        ArrayAdapter<String> adapterDeviceType = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, arrDeviceTypeName);
        adapterDeviceType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinListDeviceType.setAdapter(adapterDeviceType);
    }

    private void initTextView() {
        Intent callerIntent = getIntent();
        Bundle packageFromCaller = callerIntent.getBundleExtra(DeviceNodeAdapter.TAG);
        mDeviceNode_ID = packageFromCaller.getString(DeviceNodeAdapter.DEVICENODE_ID);
        String deviceName = packageFromCaller.getString(DeviceNodeAdapter.DEVICENODE_NAME);
        String deviceDescription = packageFromCaller.getString(DeviceNodeAdapter.DEVICENODE_DESCRIPTION);
        String nodeID = packageFromCaller.getString(DeviceNodeAdapter.NODE_ID);
        String deviceTypeID = packageFromCaller.getString(DeviceNodeAdapter.DEVICE_TYPE_ID);
        String deviceNode = packageFromCaller.getString(DeviceNodeAdapter.DEVICENODE_NOTE);

        setTextNodeName(nodeID);
        setTextDeviceTypeName(deviceTypeID);
        editDeviceName.setText(deviceName);
        editNodeDescribe.setText(deviceDescription);
        editNodeNote.setText(deviceNode);
    }

    private void setTextDeviceTypeName(final String deviceTypeID) {
        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<DeviceTypeResponse> callDeviceType = apiService.getAllDeviceTypes();
        callDeviceType.enqueue(new Callback<DeviceTypeResponse>() {
            @Override
            public void onResponse(Call<DeviceTypeResponse> call, Response<DeviceTypeResponse> response) {
                if (response.isSuccessful()) {
                    initDeviceTypeName(response.body(), deviceTypeID);
                }
            }

            @Override
            public void onFailure(Call<DeviceTypeResponse> call, Throwable t) {
            }
        });
    }

    private void initDeviceTypeName(DeviceTypeResponse body, String deviceTypeID) {
        List<DeviceTypeResponse.ResultBean> deviceType;
        if (body.getResult() != null) {
            deviceType = body.getResult();
            for (DeviceTypeResponse.ResultBean a : deviceType) {
                if (a.getId().equals(deviceTypeID)) {
                    editDeviceType.setText(a.getName());
                }
            }
        } else {
            editDeviceType.setText("");
        }
    }

    private void setTextNodeName(final String nodeID) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Loading...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<Node> callListNode = apiService.getAllNode();
        callListNode.enqueue(new Callback<Node>() {
            @Override
            public void onResponse(Call<Node> call, Response<Node> response) {
                if (response.isSuccessful()) {
                    initNodeName(response.body(), nodeID);
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<Node> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void initNodeName(Node data, String nodeID) {
        List<Node.ResultBean> node;
        if (data.getResult() != null) {
            node = data.getResult();
            for (Node.ResultBean a : node) {
                if (a.getId().equals(nodeID)) {
                    editNodeName.setText(a.getName());
                }
            }
        } else {
            editNodeName.setText("");
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
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnEditNode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNodes();

                spinListNode.setVisibility(View.VISIBLE);
                spinListNode.performClick();

                spinListNode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != -1) {
                            editNodeName.setText(arrNodeName.get(i));
                        }
                        spinListNode.setVisibility(View.GONE);
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
                String description = String.valueOf(editNodeDescribe.getText());
                String note = String.valueOf(editNodeNote.getText());
                String nodeId = getNodeIdByName();
                String deviceTypeId = getDeviceTypeIdByName();

                requestUpdateDeviceNode(mDeviceNode_ID, name, description, note, nodeId, deviceTypeId);
            }
        });
        imgOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(imgOptions);
            }
        });
    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_delete_info, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.action_delete_info:
                    showSubmidDialog();
                    return true;
                default:
            }
            return false;
        }
    }

    private void showSubmidDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("SprayIoT");
        builder.setMessage("Xin bạn xác nhận để xóa?");
        builder.setCancelable(false);
        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestDeleteDeviceNode(mDeviceNode_ID);
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

    private void requestDeleteDeviceNode(String deviceNode_id) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Delete...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<ResponseBody> callDeviceNode = apiService.deleteDeviceNode(deviceNode_id);
        callDeviceNode.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.toast_delete_device_node_successful, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(mContext, R.string.toast_delete_device_node_fail, Toast.LENGTH_SHORT).show();
                    finish();
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Logger.d(TAG, t.toString());
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void requestUpdateDeviceNode(String deviceNode_id, String name, String description, String note, String nodeId, String deviceTypeId) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Updating...");
        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<DeviceNodeResponse> callDeviceNode = apiService.updateDeviceNode(deviceNode_id, name, description, note,
                deviceTypeId, nodeId, false);
        callDeviceNode.enqueue(new Callback<DeviceNodeResponse>() {
            @Override
            public void onResponse(Call<DeviceNodeResponse> call, Response<DeviceNodeResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.toast_update_device_node_successful, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, R.string.toast_update_device_node_fail, Toast.LENGTH_SHORT).show();
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<DeviceNodeResponse> call, Throwable t) {
                Logger.d(TAG, t.toString());
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
        listDeviceType = new ArrayList<>();
        if (data != null) {
            listDeviceType = data.getResult();
            arrDeviceTypeName.clear();
            for (DeviceTypeResponse.ResultBean a : listDeviceType) {
                arrDeviceTypeName.add(a.getName());
            }
        }
    }

    private String getDeviceTypeIdByName() {
        String deviceTypeId = null;
        String deviceTypeName = editDeviceType.getText().toString();
        if (!deviceTypeName.equals("")) {
            if (listDeviceType != null) {
                for (DeviceTypeResponse.ResultBean a : listDeviceType) {
                    if (a.getName().equals(deviceTypeName)) {
                        deviceTypeId = a.getId();
                        return deviceTypeId;
                    }
                }
            }
        }
        return deviceTypeId;
    }

    private void getNodes() {
        ProgressDialogLoader.progressdialog_creation(mContext, "Loading...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<Node> callNodes = apiService.getAllNode();
        callNodes.enqueue(new Callback<Node>() {
            @Override
            public void onResponse(Call<Node> call, Response<Node> response) {
                if (response.isSuccessful())
                    initNodes(response.body());
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<Node> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void initNodes(Node data) {
        listNodes = new ArrayList<>();
        if (data != null) {
            listNodes = data.getResult();
            arrNodeName.clear();
            for (Node.ResultBean a : listNodes) {
                arrNodeName.add(a.getName());
            }
        }
    }

    private String getNodeIdByName() {
        String nodeId = null;
        String nodeName = editNodeName.getText().toString();
        if (!nodeName.equals("")) {
            if (listNodes != null) {
                for (Node.ResultBean a : listNodes) {
                    if (a.getName().equals(nodeName)) {
                        nodeId = a.getId();
                        return nodeId;
                    }
                }
            }
        }
        return nodeId;
    }

}
