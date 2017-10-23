package com.vuduc.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vuduc.models.DeviceNodeResponse;
import com.vuduc.tluiot.DeviceNodeAddActivity;
import com.vuduc.tluiot.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 10/19/2017.
 */

public class DeviceNodeAdapter extends RecyclerView.Adapter<DeviceNodeAdapter.MyViewHolder> {

    private List<DeviceNodeResponse.Result> mDeviceNodes;
    private Context mContext;

    public DeviceNodeAdapter(Context mContext, List<DeviceNodeResponse.Result> mDeviceNodes) {
        this.mDeviceNodes = mDeviceNodes;
        this.mContext = mContext;
    }

    @Override
    public DeviceNodeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_node, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DeviceNodeAdapter.MyViewHolder holder, int position) {
        final DeviceNodeResponse.Result deviceNode = mDeviceNodes.get(position);

        holder.tvDeviceName.setText(deviceNode.getName());
        String deviceNodeType = deviceNode.getDeviceType().getNote();
        holder.tvDeviceType.setText(deviceNodeType);

        holder.imgOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(view.getContext(), DeviceNodeAddActivity.class));
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(deviceNode);
            }
        });
    }

    public void showAlertDialog(DeviceNodeResponse.Result deviceNode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Thông tin cảm biến");
        builder.setCancelable(false); //click outSide to dismiss dialog
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View alertLayout = inflater.inflate(R.layout.dialog_device_node, null);
        builder.setView(alertLayout);

        //addControls
        TextView editDeviceNodeName = alertLayout.findViewById(R.id.edit_node_name);
        TextView editDeviceNodeDescribe = alertLayout.findViewById(R.id.edit_node_describe);
        TextView editTypeSensor = alertLayout.findViewById(R.id.edit_type_sensor);
        TextView editDeviceNodeNote = alertLayout.findViewById(R.id.edit_node_note);

        //addEvents
        editDeviceNodeName.setText(deviceNode.getName());
        editDeviceNodeDescribe.setText(deviceNode.getDescription());
        editTypeSensor.setText(deviceNode.getDeviceType().getName());
        editDeviceNodeNote.setText(deviceNode.getNote());

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
        return mDeviceNodes.size();
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
