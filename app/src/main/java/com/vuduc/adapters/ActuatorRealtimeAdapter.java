package com.vuduc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vuduc.models.ActuatorRealtime;
import com.vuduc.tluiot.R;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by admin on 7/8/2017.
 */

public class ActuatorRealtimeAdapter extends RecyclerView.Adapter<ActuatorRealtimeAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ActuatorRealtime> mData;
    private LayoutInflater mLayoutInflater;

    public ActuatorRealtimeAdapter(Context mContext, ArrayList<ActuatorRealtime> mData) {
        this.mContext = mContext;
        this.mData=mData;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    // Tạo viewholder hiển thị 1 item
    @Override
    public ActuatorRealtimeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_actuator_realtime, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ActuatorRealtimeAdapter.ViewHolder holder, int position) {
        ActuatorRealtime course = mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_actuator_name)
        TextView txt_actuator_name;
        @BindView(R.id.switch_actuator_realtime)
        SwitchCompat switch_actuator_realtime;
        public ViewHolder(View itemView) {

            super(itemView);
        }
    }
}
