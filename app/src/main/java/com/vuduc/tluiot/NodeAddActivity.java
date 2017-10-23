package com.vuduc.tluiot;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.vuduc.fragments.AddNodeFragment;
import com.vuduc.models.AreaResponse;
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

public class NodeAddActivity extends AppCompatActivity {
    private static final String TAG = NodeAddActivity.class.getSimpleName();
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.btn_edit_area)
    ImageView btnEditArea;
    @BindView(R.id.spinner_list_area)
    MaterialSpinner spinListArea;
    @BindView(R.id.edit_node_area)
    TextView editNodeArea;
    @BindView(R.id.edit_node_name)
    EditText editNodeName;
    @BindView(R.id.edit_node_describe)
    EditText editNodeDescribe;
    @BindView(R.id.edit_node_note)
    EditText editNodeNote;

    List<AreaResponse.Result> listAreas = null;
    List<String> arrAreaName;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_add);
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

        arrAreaName = new ArrayList<>();

        //GONE Spinner List Area name
        ArrayAdapter<String> adapterAreas = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, arrAreaName);
        adapterAreas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinListArea.setAdapter(adapterAreas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

        btnEditArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAreas();
                spinListArea.setVisibility(View.VISIBLE);
                spinListArea.performClick();

                Logger.d(TAG, " ..1");
                spinListArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != -1) {
                            Logger.d(TAG, arrAreaName.get(position)+" ..2");
                            editNodeArea.setText(arrAreaName.get(position));
                        }
                        spinListArea.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(editNodeName.getText());
                String description = String.valueOf(editNodeDescribe.getText());
                String note = String.valueOf(editNodeNote.getText());
                String areaId = getAreaIdByName();

                requestAddNode(name, description, note, areaId);
            }
        });
    }

    private void requestAddNode(String name, String description, String note, String areaId) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Adding...");
        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<Node> callNodes = apiService.addNode(name, description,areaId, note);
        callNodes.enqueue(new Callback<Node>() {
            @Override
            public void onResponse(Call<Node> call, Response<Node> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.toast_add_node_successful, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, R.string.toast_add_node_fail, Toast.LENGTH_SHORT).show();
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<Node> call, Throwable t) {
                Logger.d(TAG, t.toString());
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void getAreas() {
        ProgressDialogLoader.progressdialog_creation(mContext, "Loading...");

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


}
