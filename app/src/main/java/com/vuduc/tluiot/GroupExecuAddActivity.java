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

public class GroupExecuAddActivity extends AppCompatActivity {
    public static final String TAG = GroupExecuAddActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edit_device_name)
    EditText editGroupName;
    @BindView(R.id.edit_node_describe)
    EditText editGroupDescribe;
    @BindView(R.id.edit_function)
    TextView editFunction;
    @BindView(R.id.btn_function)
    ImageView btnFunction;
    @BindView(R.id.spinner_list_function)
    MaterialSpinner spinnerListFunction;
    @BindView(R.id.edit_group_autoTime)
    EditText editGroupActuTime;
    @BindView(R.id.edit_type_group)
    TextView editTypeGroup;
    @BindView(R.id.btn_group_type)
    ImageView btnGroupType;
    @BindView(R.id.spinner_list_group_type)
    MaterialSpinner spinnerListGroupType;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    List<FunctionsResponse.ResultBean> mListFunction;
    List<String> arrFunctionName;
    String[] arrGroupType = {"Bật", "Tắt"};
    ArrayAdapter<String> mFunctionAdapter, mGroupTypeAdapter;
    private Context mContext;
    private String mFunctionID;
    private Boolean mGroupExecuStatus = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_execu_create);
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

    private void addControls() {
        ButterKnife.bind(this);

        //Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Thêm mới nhóm điều kiện");
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

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = String.valueOf(editGroupName.getText());
                String description = String.valueOf(editGroupDescribe.getText());
                int autoTime = Integer.parseInt(String.valueOf(editGroupActuTime.getText()));
                if (mFunctionID != null) {
                    requestAddGroupCondition(name, description, autoTime);
                } else {
                    Toast.makeText(mContext, "Xin lỗi! Bạn chưa chọn van điện từ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void requestAddGroupCondition(String name, String description, int autoTime) {
        SprayIoTApiInterface apiInterface = ApiUtils.getSprayIoTApiService();
        Call<GroupExecuResponse> callGroup = apiInterface.addGroupCondition(name, description, mFunctionID, autoTime, mGroupExecuStatus);
        callGroup.enqueue(new Callback<GroupExecuResponse>() {
            @Override
            public void onResponse(Call<GroupExecuResponse> call, Response<GroupExecuResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.toast_add_info_successful, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, R.string.toast_add_info_fail, Toast.LENGTH_SHORT).show();
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<GroupExecuResponse> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }
}
