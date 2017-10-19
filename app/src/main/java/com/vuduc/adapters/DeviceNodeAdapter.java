package com.vuduc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vuduc.models.DeviceNodeResponse;
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
        DeviceNodeResponse.Result deviceNode = mDeviceNodes.get(position);

        holder.tvDeviceName.setText(deviceNode.getName());
        String deviceNodeType = deviceNode.getDeviceType().getNote();
        holder.tvDeviceType.setText(deviceNodeType);
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

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
