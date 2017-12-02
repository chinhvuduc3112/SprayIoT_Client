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
import android.widget.ImageView;
import android.widget.TextView;

import com.vuduc.models.ActuatorInfosResponse;
import com.vuduc.tluiot.ActuatorUpdateActivity;
import com.vuduc.tluiot.R;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private long mStartTime = 0, mStopTime = 0, mActiveTime = 0;

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
            startCountDownTimer(holder.txt_actuator_time, holder.mCountDownTimer);
        } else {
            holder.switch_actuator_realtime.setChecked(false);
        }

        holder.switch_actuator_realtime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (holder.switch_actuator_realtime.isChecked()) {
                    holder.switch_actuator_realtime.setChecked(true);
                } else {
                    pauseCountDownTimer(holder.txt_actuator_time, holder.mCountDownTimer);
                    holder.switch_actuator_realtime.setChecked(false);
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

    private void pauseCountDownTimer(TextView txt_actuator_time, CountDownTimer countDownTimer) {
        countDownTimer.cancel();
    }

    public void onPauseFragment() {
        Log.d(TAG, "pauseCountDownTimer: ");
//        if(mCountDownTimer!=null){
//            mCountDownTimer.cancel();
//        }
    }

    private void startCountDownTimer(final TextView txt_actuator_time, CountDownTimer countDownTimer) {
        mStartTime = Long.parseLong(txt_actuator_time.getText().toString()) * 1000;
        countDownTimer = new CountDownTimer(mStartTime, 1000) {
            @Override
            public void onTick(long l) {
                Log.d(TAG, "onTick: " + l);
                txt_actuator_time.setText(l / 1000 + " min");
                mStopTime = (int) (l / 1000);
            }

            @Override
            public void onFinish() {
                txt_actuator_time.setText("Dừng");
            }
        }.start();
    }

    private void getActuatorInfo(ActuatorInfosResponse.Result actuator) {
        mId = actuator.getId();
        mName = actuator.getName();
        mDescription = actuator.getDescription();
        mAreaId = actuator.getArea().getId();
        mAreaName = actuator.getArea().getName();
        mDeviceTypeID = actuator.getDeviceType().getId();
        mDeviceTypeName = actuator.getDeviceType().getName();
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
        editAreaName.setText(actuator.getArea().getName());

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

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
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
