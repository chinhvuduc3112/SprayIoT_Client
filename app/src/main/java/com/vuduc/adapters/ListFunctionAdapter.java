package com.vuduc.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vuduc.tluiot.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by VuDuc on 11/15/2017.
 */

public class ListFunctionAdapter extends RecyclerView.Adapter<ListFunctionAdapter.MyViewHolder> {

    public static final String TAG = ListFunctionAdapter.class.getSimpleName();


    @Override
    public ListFunctionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actuator_realtime, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListFunctionAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
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
