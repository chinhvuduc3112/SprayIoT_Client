package com.vuduc.tluiot;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vuduc.models.AreaResponse;
import com.vuduc.network.ApiUtils;
import com.vuduc.network.SprayIoTApiInterface;
import com.vuduc.until.Logger;
import com.vuduc.until.ProgressDialogLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AreaAddActivity extends AppCompatActivity {
    public static final String TAG = AreaAddActivity.class.getSimpleName();

    @BindView(R.id.edit_area_name)
    EditText edit_area_name;
    @BindView(R.id.edit_area_describe)
    EditText edit_area_describe;
    @BindView(R.id.edit_area_x)
    EditText edit_area_x;
    @BindView(R.id.edit_area_y)
    EditText edit_area_y;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_add);

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
    }

    private void addEvents() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(edit_area_name.getText());
                String note = String.valueOf(edit_area_describe.getText());
                int x = Integer.parseInt(String.valueOf(edit_area_x.getText()));
                int y = Integer.parseInt(String.valueOf(edit_area_y.getText()));

                requestAddArea(name, note, x, y);
            }
        });
    }

    private void requestAddArea(String name, String note, int x, int y) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Adding");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<AreaResponse> callAreas = apiService.addArea(name, note, x, y);
        callAreas.enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.toast_add_area_successful, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, R.string.toast_add_area_fail, Toast.LENGTH_SHORT).show();
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {
                Logger.d(TAG, t.toString());
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
