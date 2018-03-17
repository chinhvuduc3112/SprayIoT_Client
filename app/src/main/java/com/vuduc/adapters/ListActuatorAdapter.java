package com.vuduc.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vuduc.models.ActuatorInfosResponse;
import com.vuduc.models.ManualUpdateActuator;
import com.vuduc.network.ApiUtils;
import com.vuduc.network.SprayIoTApiInterface;
import com.vuduc.tluiot.ActuatorUpdateActivity;
import com.vuduc.tluiot.R;
import com.vuduc.until.ProgressDialogLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 11/3/2017.
 */

public class ListActuatorAdapter extends RecyclerView.Adapter<ListActuatorAdapter.MyViewHolder> {

    public static final String TAG = ListActuatorAdapter.class.getSimpleName();

    public static final String ACTUATOR_ID = "ACTUATOR_ID";
    public static final String ACTUATOR_NAME = "ACTUATOR_NAME";
    public static final String ACTUATOR_DESCRIPTION = "ACTUATOR_DESCRIPTION";
    public static final String ACTUATOR_AREA_ID = "ACTUATOR_NODE_ID";
    public static final String ACTUATOR_DEVICETYPE_ID = "ACTUATOR_DEVICETYPE_ID";
    public static final String ACTUATOR_AREA_NAME = "ACTUATOR_AREA_NAME";
    public static final String ACTUATOR_DEVICETYPE_NAME = "ACTUATOR_DEVICETYPE_NAME";

    private String mId, mName, mDescription, mAreaId, mDeviceTypeID, mAreaName, mDeviceTypeName;

    private List<ActuatorInfosResponse.Result> mActuators;
    private Context mContext;

    public ListActuatorAdapter(Context mContext, List<ActuatorInfosResponse.Result> mActuators) {
        this.mActuators = mActuators;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actuator_realtime, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ActuatorInfosResponse.Result actuator = mActuators.get(position);

        holder.txt_actuator_name.setText(actuator.getName());
        holder.txt_actuator_time.setText(actuator.getTime() + "");
        //setCheckedSwitch
        if (actuator.isStatus()) {
            holder.switch_actuator_realtime.setChecked(true);
            startCountDownTimer(holder, actuator);
        } else {
            holder.switch_actuator_realtime.setChecked(false);
        }

        holder.switch_actuator_realtime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //true
                    if (!holder.isRunning || actuator.isStatus() == false) {
                        manualONActuator(holder, actuator);
                    }
                } else {
                    //false
                    if (holder.isRunning & actuator.isStatus() == true) {
                        manualPauseActuator(holder, actuator);
                    }
//                    } else {
//                        pauseCountDownTimer(holder);
//                    }
                }
            }
        });

        holder.txt_actuator_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(actuator);
            }
        });

        holder.img_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActuatorInfo(actuator);
                showPopupMenu(view);
            }
        });
    }

    private void manualONActuator(final MyViewHolder holder, final ActuatorInfosResponse.Result actuator) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Chỉnh thời gian bật");
        builder.setCancelable(false); //click outSide to dismiss dialog
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View alertLayout = inflater.inflate(R.layout.dialog_manual_time, null);
        builder.setView(alertLayout);

        //addControls
        final EditText editTime = alertLayout.findViewById(R.id.edit_time);

        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int time = Integer.parseInt(String.valueOf(editTime.getText()));
                if (editTime.getText() != null) {
                    manualUpdateStatusActuatorResponse(holder, actuator, time, true);
                }
            }
        });

        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                holder.switch_actuator_realtime.setChecked(false);
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void manualPauseActuator(final MyViewHolder holder, final ActuatorInfosResponse.Result actuator) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Spray IoT");
        builder.setMessage("Bạn có chắc muốn TẮT không?");
        builder.setCancelable(false); //click outSide to dismiss dialog=
        builder.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                manualUpdateStatusActuatorResponse(holder, actuator, 0, false);
                holder.isRunning = false;
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                holder.switch_actuator_realtime.setChecked(true);
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void manualUpdateStatusActuatorResponse(final MyViewHolder holder, final ActuatorInfosResponse.Result actuator, int i, final boolean b) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Updating...");

        if (actuator.getId() != null) {
            SprayIoTApiInterface apiInterface = ApiUtils.getSprayIoTApiService();
            retrofit2.Call<ManualUpdateActuator> callActuator = apiInterface.manualUpdateStatusActuator(actuator.getId(), i, b);
            callActuator.enqueue(new Callback<ManualUpdateActuator>() {
                @Override
                public void onResponse(Call<ManualUpdateActuator> call, Response<ManualUpdateActuator> response) {
                    if (response.isSuccessful()) {

                        updateListActuator(response.body());

                        if (b) {
                            Toast.makeText(mContext, "BẬT thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "TẮT thành công", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, "Hành động thất bại", Toast.LENGTH_SHORT).show();
                    }
                    ProgressDialogLoader.progressdialog_dismiss();
                }

                private void updateListActuator(ManualUpdateActuator body) {
                    ManualUpdateActuator.ResultBean actuators = body.getResult();
                    for (ActuatorInfosResponse.Result a : mActuators) {
                        Log.d("Chinh test ", "| actuator2: " + actuators.getId());
                        Log.d("Chinh test ", "| actuator3: " + a.getId());
                        Log.d("Chinh test ", "| actuator4: " + actuators.isStatus());
                        if (a.getId().equals(actuators.getId())) {
                            Log.d("Chinh test ", "| actuator: " + actuators.isStatus());
                            a.setTime(actuators.getTime());
                            a.setStatus(actuators.isStatus());
                        }
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ManualUpdateActuator> call, Throwable t) {
                    ProgressDialogLoader.progressdialog_dismiss();
                }
            });
        }
    }

    private void pauseCountDownTimer(final MyViewHolder holder) {
        holder.txt_actuator_time.setText("Dừng");
        holder.isRunning = false;
        holder.switch_actuator_realtime.setChecked(false);
    }

    private void startCountDownTimer(final MyViewHolder holder, ActuatorInfosResponse.Result actuator) {
        Log.d("CHINH", "startCountDownTimer: " + actuator.getName());

        holder.mStartTime = actuator.getTime() * 60000;

        holder.mCountDownTimer = new CountDownTimer(holder.mStartTime, 60000) {
            @Override
            public void onTick(long l) {
                if (holder.isRunning) {
                    holder.txt_actuator_time.setText(l / 60000 + " min");
                    holder.mStopTime = (int) (l / 60000);
                    holder.isRunning = true;
                }
            }

            @Override
            public void onFinish() {
                holder.txt_actuator_time.setText("Dừng");
                holder.isRunning = false;
                holder.switch_actuator_realtime.setChecked(false);
            }
        }.start();
    }

    private void getActuatorInfo(ActuatorInfosResponse.Result actuator) {
        if (actuator.getId() != null) {
            mId = actuator.getId();
            mName = actuator.getName();
            mDescription = actuator.getDescription();
            if (actuator.getArea() != null) {
                mAreaId = actuator.getArea().getId();
            } else {
                mAreaId = null;
            }
            if (actuator.getArea() != null) {
                mAreaName = actuator.getArea().getName();
            } else {
                mAreaName = "null";
            }
            mDeviceTypeID = actuator.getDeviceType().getId();
            mDeviceTypeName = actuator.getDeviceType().getName();
        }
    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_actuator_detail, popup.getMenu());
        popup.setOnMenuItemClickListener(new ListActuatorAdapter.MyMenuItemClickListener());
        popup.show();
    }

    private void showAlertDialog(ActuatorInfosResponse.Result actuator) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Thông tin thiết bị");
        builder.setCancelable(false); //click outSide to dismiss dialog
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View alertLayout = inflater.inflate(R.layout.dialog_actuator, null);
        builder.setView(alertLayout);

        //addControls
        TextView editDeviceName = alertLayout.findViewById(R.id.edit_device_name);
        TextView editDeviceDescribe = alertLayout.findViewById(R.id.edit_device_describe);
        TextView editTypeDevice = alertLayout.findViewById(R.id.edit_type_device);
        TextView editAreaName = alertLayout.findViewById(R.id.edit_area_name);

        //addEvents
        editDeviceName.setText(actuator.getName());
        editDeviceDescribe.setText(actuator.getDescription());
        editTypeDevice.setText(actuator.getDeviceType().getName());
        if (actuator.getArea() != null) {
            editAreaName.setText(actuator.getArea().getName());
        } else {
            editAreaName.setText("null");
        }

        builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        if (mActuators != null)
            return mActuators.size();
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_actuator_name)
        TextView txt_actuator_name;
        @BindView(R.id.switch_actuator_realtime)
        SwitchCompat switch_actuator_realtime;
        @BindView(R.id.txt_actuator_time)
        TextView txt_actuator_time;
        @BindView(R.id.img_options)
        ImageView img_options;

        View itemView;
        CountDownTimer mCountDownTimer;
        long mStartTime = 0, mStopTime = 0;
        boolean isRunning = true;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.setIsRecyclable(false);

            ButterKnife.bind(this, itemView);
        }
    }

    public class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_actuator_info:
                    Intent intent = new Intent(mContext, ActuatorUpdateActivity.class);
                    Bundle actuatorBundle = new Bundle();
                    actuatorBundle.putString(ACTUATOR_ID, mId);
                    actuatorBundle.putString(ACTUATOR_NAME, mName);
                    actuatorBundle.putString(ACTUATOR_DESCRIPTION, mDescription);
                    actuatorBundle.putString(ACTUATOR_AREA_ID, mAreaId);
                    actuatorBundle.putString(ACTUATOR_AREA_NAME, mAreaName);
                    actuatorBundle.putString(ACTUATOR_DEVICETYPE_ID, mDeviceTypeID);
                    actuatorBundle.putString(ACTUATOR_DEVICETYPE_NAME, mDeviceTypeName);
                    intent.putExtra(TAG, actuatorBundle);
                    mContext.startActivity(intent);
                    return true;
            }
            return false;
        }
    }
}
