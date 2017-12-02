package com.vuduc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vuduc.models.FunctionByAcResponse;
import com.vuduc.tluiot.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by VuDuc on 11/15/2017.
 */

public class ListFunctionAdapter extends RecyclerView.Adapter<ListFunctionAdapter.MyViewHolder> {

    public static final String TAG = ListFunctionAdapter.class.getSimpleName();

    OnItemClickListener mItemClickListenner;

    private List<FunctionByAcResponse.Result> mListFunction;
    private Context mContext;

    public ListFunctionAdapter(Context context, List<FunctionByAcResponse.Result> listFunction) {
        mListFunction = listFunction;
        mContext = context;
    }

    @Override
    public ListFunctionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actuator_realtime, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListFunctionAdapter.MyViewHolder holder, int position) {
        final FunctionByAcResponse.Result function = mListFunction.get(position);

        holder.txt_actuator_name.setText(function.getName());
        holder.txt_actuator_time.setText(function.getActivityDuration() + "");
        if (function.isStatus()) {
            holder.switch_actuator_realtime.setChecked(true);
        } else {
            holder.switch_actuator_realtime.setChecked(false);
        }
    }


    @Override
    public int getItemCount() {
        if (mListFunction != null) {
            return mListFunction.size();
        }
        return 0;
    }

    public void SetOnItemClickListener(OnItemClickListener mItemCliclListener) {
        this.mItemClickListenner = mItemCliclListener;
    }

    public interface OnItemClickListener {
        void onImgOptionsClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

            img_options.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_options:
                    mItemClickListenner.onImgOptionsClick(view, getAdapterPosition());
                    break;
            }
        }
    }
}
