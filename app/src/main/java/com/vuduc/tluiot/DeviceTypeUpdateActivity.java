package com.vuduc.tluiot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.vuduc.adapters.DeviceTypeAdapter;
import com.vuduc.models.DeviceTypeResponse;
import com.vuduc.network.ApiUtils;
import com.vuduc.network.SprayIoTApiInterface;
import com.vuduc.until.ProgressDialogLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceTypeUpdateActivity extends AppCompatActivity {
    public static final String TAG = DeviceTypeUpdateActivity.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.img_options)
    ImageView imgOptions;
    @BindView(R.id.edit_device_name)
    EditText editDeviceTypeName;
    @BindView(R.id.edit_node_describe)
    EditText editDeviceTypeNote;

    private Context mContext;
    private String mDeviceTypeID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_type_update);
        mContext = this;
        addControls();
        initTextView();
        addEvents();
    }

    private void initTextView() {
        Intent callerIntent = getIntent();
        Bundle packageFromCaller = callerIntent.getBundleExtra(DeviceTypeAdapter.TAG);
        mDeviceTypeID = packageFromCaller.getString(DeviceTypeAdapter.DEVICE_TYPE_ID);
        String deviceTypeName = packageFromCaller.getString(DeviceTypeAdapter.DEVICE_TYPE_NAME);
        String deviceTypeNote = packageFromCaller.getString(DeviceTypeAdapter.DEVICE_TYPE_NOTE);

        editDeviceTypeName.setText(deviceTypeName);
        editDeviceTypeNote.setText(deviceTypeNote);
    }

    private void addControls() {
        ButterKnife.bind(this);

        //Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.device_type);
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
            public void onClick(View view) {
                String name = String.valueOf(editDeviceTypeName.getText());
                String note = String.valueOf(editDeviceTypeNote.getText());
                
                requestUpdateDeviceType(mDeviceTypeID, name, note);
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
        popup.setOnMenuItemClickListener(new DeviceTypeUpdateActivity.MyMenuItemClickListener());
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
                requestDeleteDeviceNode(mDeviceTypeID);
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

    private void requestDeleteDeviceNode(String deviceTypeID) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Delete...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<ResponseBody> callDeviceType = apiService.deleteDeviceType(deviceTypeID);
        callDeviceType.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.toast_delete_device_type_successful, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(mContext, R.string.toast_delete_device_type_fail, Toast.LENGTH_SHORT).show();
                    finish();
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }


    private void requestUpdateDeviceType(String deviceTypeID, String name, String note) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Updating...");
        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<DeviceTypeResponse> callDeviceType =apiService.updateDeviceType(deviceTypeID, name, note, false);
        callDeviceType.enqueue(new Callback<DeviceTypeResponse>() {
            @Override
            public void onResponse(Call<DeviceTypeResponse> call, Response<DeviceTypeResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.toast_update_device_type_successful, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, R.string.toast_update_device_type_fail, Toast.LENGTH_SHORT).show();
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<DeviceTypeResponse> call, Throwable t) {
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
