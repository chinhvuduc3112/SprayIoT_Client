package com.vuduc.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vuduc.models.DeviceTypeResponse;
import com.vuduc.tluiot.DeviceTypeUpdateActivity;
import com.vuduc.tluiot.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by VuDuc on 10/29/2017.
 */

public class DeviceTypeAdapter extends RecyclerView.Adapter<DeviceTypeAdapter.MyViewHolder> {

    public static final String TAG = DeviceTypeAdapter.class.getSimpleName();
    public static final String DEVICE_TYPE_ID = "DEVICE_TYPE_ID";
    public static final String DEVICE_TYPE_NAME = "DEVICE_TYPE_NAME";
    public static final String DEVICE_TYPE_NOTE = "DEVICE_TYPE_NOTE";

    private List<DeviceTypeResponse.ResultBean> mDeviceTypes;
    private Context mContext;

    public DeviceTypeAdapter(Context context, List<DeviceTypeResponse.ResultBean> deviceTypes) {
        mDeviceTypes = deviceTypes;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_node, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final DeviceTypeResponse.ResultBean deviceType = mDeviceTypes.get(position);

        holder.tvDeviceName.setText(deviceType.getName());
        if (deviceType.getNote().length() > 30) {
            String typeNote = deviceType.getNote().substring(0,30);
            holder.tvDeviceType.setText(typeNote);
        } else {
            holder.tvDeviceType.setText(deviceType.getNote());
        }

        holder.imgOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntents = new Intent(view.getContext(), DeviceTypeUpdateActivity.class);
                Bundle myBundle = new Bundle();
                myBundle.putString(DEVICE_TYPE_ID, deviceType.getId());
                myBundle.putString(DEVICE_TYPE_NAME, deviceType.getName());
                myBundle.putString(DEVICE_TYPE_NOTE, deviceType.getNote());
                myIntents.putExtra(TAG, myBundle);
                view.getContext().startActivity(myIntents);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(deviceType);
            }
        });

        holder.ivDeviceImage.setImageResource(R.drawable.icon_rapberypi);
    }

    private void showAlertDialog(DeviceTypeResponse.ResultBean deviceType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Thông tin cảm biến");
        builder.setCancelable(false); //click outSide to dismiss dialog
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View alertLayout = inflater.inflate(R.layout.dialog_device_type, null);
        builder.setView(alertLayout);

        //addControls
        TextView txtDeviceTypeName = alertLayout.findViewById(R.id.edit_type_name);
        TextView txtDeviceTypeNote = alertLayout.findViewById(R.id.edit_device_type_note);

        //addEvents
        txtDeviceTypeName.setText(deviceType.getName());
        txtDeviceTypeNote.setText(deviceType.getNote());

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
        if(mDeviceTypes!=null)
            return mDeviceTypes.size();
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_device_image)
        ImageView ivDeviceImage;
        @BindView(R.id.tv_device_name)
        TextView tvDeviceName;
        @BindView(R.id.tv_device_description)
        TextView tvDeviceType;
        @BindView(R.id.img_options)
        ImageView imgOptions;

        View itemView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
