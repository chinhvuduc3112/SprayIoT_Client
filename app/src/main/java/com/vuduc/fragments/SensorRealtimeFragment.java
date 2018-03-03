package com.vuduc.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vuduc.models.DeviceNodeResponse;
import com.vuduc.models.Node;
import com.vuduc.network.ApiUtils;
import com.vuduc.network.SprayIoTApiInterface;
import com.vuduc.tluiot.R;
import com.vuduc.until.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SensorRealtimeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.spinner)
    MaterialSpinner spinner;
    @BindView(R.id.txt_value_temp)
    TextView txt_value_temp;
    @BindView(R.id.txt_value_light)
    TextView txt_value_light;
    @BindView(R.id.txt_value_humi)
    TextView txt_value_humi;
    @BindView(R.id.txt_value_humiAir)
    TextView txt_value_humiAir;
    @BindView(R.id.srlLayout)
    SwipeRefreshLayout srlLayout;

    List<Node.ResultBean> listNode;
    List<DeviceNodeResponse.Result> listDeviceNode;
    List<String> arrNodeName;

    public SensorRealtimeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        getListNode();
        View v = inflater.inflate(R.layout.fragment_sensor_realtime, container, false);
        ButterKnife.bind(this, v);
        arrNodeName = new ArrayList<>();
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getListNode();
            }
        }).start();

        addControls();
        addEvents();
    }

    private void addEvents() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Logger.d("tag: ", Integer.toString(i));
                resetTextView();
                if (i != -1) {
                    String idNode = listNode.get(i).getId();
                    getDeviceNodeByNode(idNode);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                resetTextView();
            }
        });
    }

    private void resetTextView() {
        txt_value_light.setText(R.string.nulls);
        txt_value_temp.setText(R.string.nulls);
        txt_value_humi.setText(R.string.nulls);
        txt_value_humiAir.setText(R.string.nulls);
    }


    private void getDeviceNodeByNode(String idNode) {
        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        apiService.getDeviceNodeByNode(idNode).enqueue(new Callback<DeviceNodeResponse>() {
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
        if (data.getStatus() != 0) {
            listDeviceNode = new ArrayList<>();
            listDeviceNode = data.getResult();
            for (DeviceNodeResponse.Result a : listDeviceNode) {
                switch (a.getDeviceType().getName()) {
                    case "lightSensor":
                        txt_value_light.setText(a.getData() + "");
                        break;
                    case "tempSensor":
                        txt_value_temp.setText(a.getData() + "");
                        break;
                    case "humiSensor":
                        txt_value_humiAir.setText(a.getData() + "");
                        break;
                    case "airHumiSensor":
                        txt_value_humi.setText(a.getData() + "");
                        break;
                }
            }
        }
    }

    private void getListNode() {
        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<Node> callListNode = apiService.getAllNode();
        callListNode.enqueue(new Callback<Node>() {
            @Override
            public void onResponse(Call<Node> call, Response<Node> response) {
//                Log.d("listNode", response.toString());
                if (response.isSuccessful()) {
                    initListNode(response.body());
                }
            }

            @Override
            public void onFailure(Call<Node> call, Throwable t) {

            }
        });
    }

    private void initListNode(Node data) {
        listNode = new ArrayList<>();
        if (data.getResult() != null) {
            listNode = data.getResult();
            arrNodeName.clear();
            for (Node.ResultBean a : listNode) {
                arrNodeName.add(a.getName());
            }
        }
    }

    private void addControls() {
        //Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrNodeName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        srlLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getListNode();
                srlLayout.setRefreshing(false);
            }
        }, 2500);
    }
}
