package com.vuduc.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vuduc.adapters.DeviceNodeAdapter;
import com.vuduc.models.AreaByIdResponse;
import com.vuduc.models.AreaResponse;
import com.vuduc.models.DeviceNodeResponse;
import com.vuduc.models.Node;
import com.vuduc.models.NodeByIdResponse;
import com.vuduc.network.ApiUtils;
import com.vuduc.network.SprayIoTApiInterface;
import com.vuduc.tluiot.R;
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

public class NodeInfoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = NodeInfoFragment.class.getSimpleName();
    @BindView(R.id.img_options)
    ImageView imgOption;
    @BindView(R.id.srlLayout)
    SwipeRefreshLayout srlLayout;
    @BindView(R.id.spinner_list_node)
    MaterialSpinner spinListNode;
    @BindView(R.id.edit_node_name)
    EditText editNodeName;
    @BindView(R.id.edit_node_describe)
    EditText editNodeDescribe;
    @BindView(R.id.edit_node_area)
    TextView editNodeArea;
    @BindView(R.id.edit_node_note)
    EditText editNodeNote;
    @BindView(R.id.btn_edit_area)
    ImageView btnEditArea;
    @BindView(R.id.spinner_list_area)
    MaterialSpinner spinListArea;
    @BindView(R.id.rv_sensor)
    RecyclerView rvSensor;

    List<Node.ResultBean> listNodes;
    List<DeviceNodeResponse.Result> mListDeviceNode;
    List<AreaResponse.Result> listAreas = null;
    List<String> arrNodeName;
    List<String> arrAreaName;

    private DeviceNodeAdapter mDeviceNodeAdapter;
    private String mAreaName;
    private String mAreaId, mNodeId;
    private Context mContext;

    public NodeInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_node_info, container, false);
        ButterKnife.bind(this, v);

        mContext = getActivity().getApplicationContext();

        arrNodeName = new ArrayList<>();
        arrAreaName = new ArrayList<>();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getNodes();
            }
        }).start();
        addControls();
        addEvents();
    }

    private void getNodes() {
        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<Node> callNodes = apiService.getAllNode();
        callNodes.enqueue(new Callback<Node>() {
            @Override
            public void onResponse(Call<Node> call, Response<Node> response) {
                if (response.isSuccessful()) {
                    initNodes(response.body());
                }
            }

            @Override
            public void onFailure(Call<Node> call, Throwable t) {

            }
        });
    }

    private void addControls() {
        //Spinner List Node name
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrNodeName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinListNode.setAdapter(adapter);

        //GONE Spinner List Area name
        ArrayAdapter<String> adapterAreas = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrAreaName);
        adapterAreas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinListArea.setAdapter(adapterAreas);

        //RV device node
        mListDeviceNode = new ArrayList<>();
        mDeviceNodeAdapter = new DeviceNodeAdapter(getActivity(), mListDeviceNode); //AlertDialog can getActivity()
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvSensor.setLayoutManager(layoutManager);
        rvSensor.setItemAnimator(new DefaultItemAnimator());
        rvSensor.setNestedScrollingEnabled(false);
        rvSensor.setAdapter(mDeviceNodeAdapter);

        srlLayout.setOnRefreshListener(this);
    }

    private void addEvents() {
        imgOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(imgOption);
            }
        });

        spinListNode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != -1) {
                    mNodeId = listNodes.get(i).getId();
                    mAreaId = listNodes.get(i).getIdArea();
                    if (mAreaId == null)
                        getNodeInfos(mNodeId, getResources().getString(R.string.nulls));
                    getAreaById(mAreaId);
                    getDeviceNodeByNode(mNodeId);
                    mDeviceNodeAdapter.setNodeId(mNodeId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                resetTextView();
            }
        });

        btnEditArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAreas();
                spinListArea.setVisibility(View.VISIBLE);
                spinListArea.performClick();

                Logger.d(TAG, " .. areaname");
                spinListArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != -1) {
                            editNodeArea.setText(arrAreaName.get(i));
                        }
                        spinListArea.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }
        });
    }

    private void getDeviceNodeByNode(String nodeId) {
        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        apiService.getDeviceNodeByNode(nodeId).enqueue(new Callback<DeviceNodeResponse>() {
            @Override
            public void onResponse(Call<DeviceNodeResponse> call, Response<DeviceNodeResponse> response) {
                if (response.isSuccessful()) {
                    initDeviceNode(response.body());
                }
            }

            @Override
            public void onFailure(Call<DeviceNodeResponse> call, Throwable t) {

            }
        });
    }

    private void initDeviceNode(DeviceNodeResponse data) {
        if (data != null) {
            List<DeviceNodeResponse.Result> deviceNodeData = data.getResult();
            mListDeviceNode.clear();
            for (DeviceNodeResponse.Result a : deviceNodeData) {
                Logger.d(TAG, a.getName() + "");
                mListDeviceNode.add(a);
            }
            mDeviceNodeAdapter.notifyDataSetChanged();
        }
    }

    private void getAreas() {
        ProgressDialogLoader.progressdialog_creation(getActivity(), "Loading...");

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
        listAreas = new ArrayList<>();
        if (data != null) {
            listAreas = data.getResult();
            arrAreaName.clear();
            for (AreaResponse.Result a : listAreas) {
                arrAreaName.add(a.getName());
            }
        }
    }

    private String getAreaIdByName() {
        String areaId = null;
        String areaName = editNodeArea.getText().toString();
        if (!areaName.equals("")) {
            if (listAreas != null) {
                for (AreaResponse.Result a : listAreas) {
                    if (a.getName().equals(areaName)) {
                        areaId = a.getId();
                        return areaId;
                    }
                }
            }
        }
        return areaId;
    }

    private void getAreaById(String mAreaId) {
        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<AreaByIdResponse> callAreas = apiService.getAreaById(mAreaId);
        callAreas.enqueue(new Callback<AreaByIdResponse>() {
            @Override
            public void onResponse(Call<AreaByIdResponse> call, Response<AreaByIdResponse> response) {
                if (response.isSuccessful()) {
                    initAreaById(response.body());
                }
            }

            @Override
            public void onFailure(Call<AreaByIdResponse> call, Throwable t) {

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

    private void initAreaById(AreaByIdResponse data) {
        if (data.getResult() != null) {
            mAreaName = data.getResult().getName();
        } else {
            mAreaName = getResources().getString(R.string.nulls);
        }
        getNodeInfos(mNodeId, mAreaName);
    }

    private void getNodeInfos(String nodeId, String areaName) {
        for (Node.ResultBean a : listNodes) {
            if (a.getId().equals(nodeId)) {
                editNodeArea.setText(areaName);
                editNodeDescribe.setText(a.getDescription());
                editNodeName.setText(a.getName());
                editNodeNote.setText(a.getNote());
            }
        }
    }

    private void resetTextView() {
        editNodeArea.setText(getResources().getString(R.string.nulls));
        editNodeDescribe.setText(getResources().getString(R.string.nulls));
        editNodeName.setText(getResources().getString(R.string.nulls));
        editNodeNote.setText(getResources().getString(R.string.nulls));
    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_info_node, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //handler
                getNodes();
                //resetTextView();
                srlLayout.setRefreshing(false);
            }
        }, 1500);
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        String name, description, note;

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
//                case R.id.action_add_node:
//                    FragmentManager fm = getFragmentManager();
//                    FragmentTransaction ft_add = fm.beginTransaction();
//                    ft_add.addToBackStack(null);
//                    ft_add.add(R.id.frame_node_info,new AddNodeFragment());
//                    ft_add.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                    ft_add.commit();
//                    return true;
                case R.id.action_update_info:
                    getTextBox();
                    if (!TextUtils.isEmpty(mNodeId))
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Logger.d(TAG, "step 1: ");
                                requestUpdateNode(name, description, note);
                            }
                        }).start();
                    return true;
                default:
            }
            return false;
        }

        private void getTextBox() {
            name = String.valueOf(editNodeName.getText());
            description = String.valueOf(editNodeDescribe.getText());
            note = String.valueOf(editNodeNote.getText());
        }
    }

    private void requestUpdateNode(String name, String description, String note) {
        String areaId = getAreaIdByName();

        Logger.d(TAG, "step 2: " + areaId);
        ProgressDialogLoader.progressdialog_creation(mContext, "Updating...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<NodeByIdResponse> callNode = apiService.updateNode(mNodeId, name, description, areaId, note, false);
        callNode.enqueue(new Callback<NodeByIdResponse>() {
            @Override
            public void onResponse(Call<NodeByIdResponse> call, Response<NodeByIdResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.action_update_info_succesful, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, R.string.toast_update_area_fail, Toast.LENGTH_SHORT).show();
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<NodeByIdResponse> call, Throwable t) {
                Logger.d(TAG, "Bug: " + t.toString());
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }
}
