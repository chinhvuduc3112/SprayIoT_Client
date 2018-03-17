package com.vuduc.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vuduc.models.ActuatorRealtime;
import com.vuduc.tluiot.R;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 7/8/2017.
 */

public class ActuatorRealtimeAdapter extends RecyclerView.Adapter<ActuatorRealtimeAdapter.ViewHolder> {

    private Context mContext;
    private List<ActuatorRealtime> mData;

    OnItemClickListener mItemClickListener;

    public ActuatorRealtimeAdapter(Context mContext, List<ActuatorRealtime> mData) {
        this.mContext = mContext;
        this.mData = mData;
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
        @BindView(R.id.txt_actuator_time)
        TextView txt_actuator_time;
        @BindView(R.id.img_options)
        ImageView img_options;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // Tạo viewholder hiển thị 1 item
    @Override
    public ActuatorRealtimeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actuator_realtime, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ActuatorRealtimeAdapter.ViewHolder holder, int position) {
        final ActuatorRealtime actuatorRealtime = mData.get(position);
        holder.txt_actuator_name.setText(actuatorRealtime.getActuatorName());
        //Chuyển đổi thời gian
        long milis = actuatorRealtime.getTime();
        String time = String.format("%1$d:%2$d:%3$d", TimeUnit.MILLISECONDS.toHours(milis),
                TimeUnit.MILLISECONDS.toMinutes(milis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milis)),
                TimeUnit.MILLISECONDS.toSeconds(milis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milis)));
        holder.txt_actuator_time.setText(time);

        if (actuatorRealtime.getStatus()) {
            holder.switch_actuator_realtime.setChecked(true);
        } else {
            holder.switch_actuator_realtime.setChecked(false);
        }
        holder.switch_actuator_realtime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    actuatorRealtime.setStatus(true);
                } else {
                    actuatorRealtime.setStatus(false);
                }
            }
        });

        holder.img_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(holder.img_options);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_actuator_realtime, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_actuator_set_time:
                    Toast.makeText(mContext, R.string.action_actuator_set_time, Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_actuator_set_conditions:
                    Toast.makeText(mContext, R.string.action_actuator_set_conditions, Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position, String id);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}