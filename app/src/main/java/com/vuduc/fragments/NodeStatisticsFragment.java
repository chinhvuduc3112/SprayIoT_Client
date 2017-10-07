package com.vuduc.fragments;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.vuduc.models.DeviceNodeResponse;
import com.vuduc.models.Node;
import com.vuduc.network.ApiUtils;
import com.vuduc.network.SprayIoTApiInterface;
import com.vuduc.tluiot.R;
import com.vuduc.until.FixData;
import com.vuduc.until.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NodeStatisticsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = NodeStatisticsFragment.class.getSimpleName();
    @BindView(R.id.spin_list_node)
    MaterialSpinner spin_list_node;
    @BindView(R.id.spin_type_statistics)
    MaterialSpinner spin_type_statistics;
    @BindView(R.id.spin_time_statistics)
    MaterialSpinner spin_time_statistics;
    @BindView(R.id.txt_date_form)
    TextView txt_date_form;
    @BindView(R.id.txt_date_to)
    TextView txt_date_to;
    @BindView(R.id.srlLayout)
    SwipeRefreshLayout srlLayout;
    @BindView(R.id.chart)
    LineChart mChart;

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String mDeviceNodeId;

    List<Node.ResultBean> listNode;
    List<DeviceNodeResponse.Result> listDeviceNode;
    List<String> arrNodeName;
    List<String> arrTypeData;
    List<String> arrTypeDate;
    List<Entry> entriesData;
    List<String> mChartLables;

    public NodeStatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_node_statistics, container, false);
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

    private void addControls() {
        //Spinner node names
        ArrayAdapter<String> adapterNodeNames = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrNodeName);
        adapterNodeNames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_list_node.setAdapter(adapterNodeNames);

        //Spinner type data
        arrTypeData = FixData.addTypeData();
        ArrayAdapter<String> adapterTypeData = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrTypeData);
        adapterTypeData.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_type_statistics.setAdapter(adapterTypeData);

        //Spinner type date
        arrTypeDate = FixData.addTypeDate();
        ArrayAdapter<String> adapterTypeDate = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrTypeDate);
        adapterTypeDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_time_statistics.setAdapter(adapterTypeDate);

//        //LineChart
//        LineDataSet dataSet = new LineDataSet(entriesData, "Times");
//        mChartLables = FixData.labelsLineChartDay();
//        LineData chartData = new LineData(dataSet);
//        chartData.setValueTextColor(ColorTemplate.COLORFUL_COLORS);

        //Refresh
        srlLayout.setOnRefreshListener(this);
    }

    private void addEvents() {
        txt_date_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDatePicker(txt_date_form);
            }
        });

        txt_date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDatePicker(txt_date_to);
            }
        });

        spin_list_node.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != -1) {
                    String idNode = listNode.get(i).getId();
                    getDeviceNodeByNode(idNode);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spin_type_statistics.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String idDeviceNode = getIdDeviceNode(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spin_time_statistics.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        txt_date_to.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        txt_date_to.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                txt_date_to.setVisibility(View.VISIBLE);
            }
        });
    }

    private String getIdDeviceNode(int i) {
        String mIdDeviceNode = null;
        switch (i) {
            case 0:
                mIdDeviceNode = getDeviceNodeByType("tempSensor");
                break;
            case 1:
                mIdDeviceNode = getDeviceNodeByType("airHumiSensor");
                break;
            case 2:
                mIdDeviceNode = getDeviceNodeByType("humiSensor");
                break;
            case 3:
                mIdDeviceNode = getDeviceNodeByType("lightSensor");
                break;
        }
        return mIdDeviceNode;
    }

    private String getDeviceNodeByType(String type) {
        String mIdDeviceNode = null;
        for (DeviceNodeResponse.Result a : listDeviceNode) {
            if (a.getDeviceType().getName().equals(type)) {
                mIdDeviceNode = a.getId();
            }
        }
        return mIdDeviceNode;
    }

    private void setDatePicker(final TextView txt_date_picker) {
        DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);
                txt_date_picker.setText(sdf.format(calendar.getTime()));

                Date date = (Date) calendar.getTime();
                long output = date.getTime();
                Logger.d(TAG, output + ".....2");
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(
                getContext(),
                callBack,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void getListNode() {
        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<Node> callListNode = apiService.getAllNode();
        callListNode.enqueue(new Callback<Node>() {
            @Override
            public void onResponse(Call<Node> call, Response<Node> response) {
                if (response.isSuccessful()) {
                    initListNode(response.body());
                }
            }

            @Override
            public void onFailure(Call<Node> call, Throwable t) {

            }
        });
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
        listDeviceNode = new ArrayList<>();
        listDeviceNode = data.getResult();
    }

    private void initListNode(Node data) {
        listNode = new ArrayList<>();
        listNode = data.getResult();
        arrNodeName.clear();
        for (Node.ResultBean a : listNode) {
            arrNodeName.add(a.getName());
        }
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
