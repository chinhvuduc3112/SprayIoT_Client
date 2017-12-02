package com.vuduc.tluiot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vuduc.models.ExecuConditionByGroupResponse;
import com.vuduc.models.FunctionsResponse;
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

public class GroupExecuDetailActivity extends AppCompatActivity {

    @BindView(R.id.edit_device_name)
    EditText editGroupExecuName;
    @BindView(R.id.edit_node_describe)
    EditText editGroupExecuDescription;
    @BindView(R.id.edit_function)
    TextView editFunction;
    @BindView(R.id.btn_function)
    ImageView btnFunction;
    @BindView(R.id.spinner_list_function)
    MaterialSpinner spinnerListFunction;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    List<FunctionsResponse.ResultBean> mListFunction;
    List<String> arrFunctionName;
    List<ExecuConditionByGroupResponse.ResultBean> mListExecuCondition;

    private Context mContext;
    private ArrayAdapter<String> mFunctionAdapter;
    private String mGroupExecuID, mFunctionID;

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
        String groupExecuName = packageFromCaller.getString(GroupExecuConditionActivity.GROUPEXECU_NAME);
        String groupExecuDescription = packageFromCaller.getString(GroupExecuConditionActivity.GROUPEXECU_DESCRIPTION);
        String functionName = packageFromCaller.getString(GroupExecuConditionActivity.FUNCTION_NAME);

        editGroupExecuName.setText(groupExecuName);
        editGroupExecuDescription.setText(groupExecuDescription);
        editFunction.setText(functionName);

        if(mGroupExecuID!=null){
            getExecuConditionByGroup(mGroupExecuID);
        }
    }

    private void getExecuConditionByGroup(String groupExecuID) {
        //ProgressDialogLoader.progressdialog_creation(mContext, "Loading...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<ExecuConditionByGroupResponse> callExecu = apiService.getExecutionConditionByGroup(groupExecuID);
        callExecu.enqueue(new Callback<ExecuConditionByGroupResponse>() {
            @Override
            public void onResponse(Call<ExecuConditionByGroupResponse> call, Response<ExecuConditionByGroupResponse> response) {
                if(response.isSuccessful()){
                    initExecuCondition(response.body());
                }
            }

            @Override
            public void onFailure(Call<ExecuConditionByGroupResponse> call, Throwable t) {

            }
        });
    }

    private void initExecuCondition(ExecuConditionByGroupResponse body) {

    }

    private void addEvents() {
    }

    private void addControls() {
        ButterKnife.bind(this);

        //Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Điều kiện thực thi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Function spinner
        mFunctionAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, arrFunctionName);
        mFunctionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerListFunction.setAdapter(mFunctionAdapter);

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
}
