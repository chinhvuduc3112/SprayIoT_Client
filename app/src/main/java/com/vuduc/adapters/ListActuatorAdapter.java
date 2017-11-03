package com.vuduc.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vuduc.models.ActuatorsResponse;
import com.vuduc.tluiot.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 11/3/2017.
 */

public class ListActuatorAdapter extends RecyclerView.Adapter<ListActuatorAdapter.MyViewHolder> {

    public static final String TAG = ListActuatorAdapter.class.getSimpleName();

    private List<ActuatorsResponse.ResultBean> mActuators;
    private Context mContext;

    public ListActuatorAdapter(Context mContext, List<ActuatorsResponse.ResultBean> mActuators) {
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ActuatorsResponse.ResultBean actuator = mActuators.get(position);

        holder.txt_actuator_name.setText(actuator.getName());

        //setCheckedSwitch
        if (actuator.isStatus()) {
            holder.switch_actuator_realtime.setChecked(true);
        } else {
            holder.switch_actuator_realtime.setChecked(false);
        }

        holder.txt_actuator_time.setText(actuator.getTime()+"");

        holder.txt_actuator_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(actuator);
            }
        });
    }

    private void showAlertDialog(ActuatorsResponse.ResultBean actuator) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Thông tin cảm biến");
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

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
