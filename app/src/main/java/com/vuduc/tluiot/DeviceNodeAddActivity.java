package com.vuduc.tluiot;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceNodeAddActivity extends AppCompatActivity {
    private static final String TAG = DeviceNodeAddActivity.class.getSimpleName();
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

    List<Node.ResultBean> listNodes;
    List<String> arrNodeName;
    List<DeviceTypeResponse.ResultBean> listDeviceType;
    List<String> arrDeviceTypeName;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_node_add);
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

                requestAddDeviceNode(name, description, note, nodeId, deviceTypeId);
            }
        });
    }

    private void requestAddDeviceNode(String name, String description, String note, String nodeId, String deviceTypeId) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Adding...");
        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<DeviceNodeResponse> callDeviceNode = apiService.addDeviceNode(name, description, note, deviceTypeId, nodeId);
        callDeviceNode.enqueue(new Callback<DeviceNodeResponse>() {
            @Override
            public void onResponse(Call<DeviceNodeResponse> call, Response<DeviceNodeResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.toast_add_device_node_successful, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, R.string.toast_add_device_node_fail, Toast.LENGTH_SHORT).show();
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
        if (data.getResult() != null) {
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
