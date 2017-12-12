package com.vuduc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vuduc.models.ExecuConditionByGroupResponse;
import com.vuduc.tluiot.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by VuDuc on 12/2/2017.
 */

public class ExecuConditionAdapter extends RecyclerView.Adapter<ExecuConditionAdapter.ExecuConditionViewHoler> {

    OnItemClickListener mItemClickListenner;

    private List<ExecuConditionByGroupResponse.ResultBean> mListExecuCondition;
    private Context mContext;

    public ExecuConditionAdapter(Context context, List<ExecuConditionByGroupResponse.ResultBean> listExecuCondition) {
        mListExecuCondition = listExecuCondition;
        mContext = context;
    }

    @Override
    public ExecuConditionViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_execu_condition, parent, false);
        ExecuConditionViewHoler holer = new ExecuConditionViewHoler(view);
        return holer;
    }

    @Override
    public void onBindViewHolder(ExecuConditionViewHoler holder, int position) {
        ExecuConditionByGroupResponse.ResultBean condition = mListExecuCondition.get(position);
        holder.txtConditionName.setText(condition.getName());
        holder.txtDeviceNodeName.setText(condition.getDeviceNode().getName());

        switch (condition.getCompare()) {
            case 0:
                holder.txtConpareType.setText("=");
                break;
            case 1:
                holder.txtConpareType.setText("<");
                break;
            case 2:
                holder.txtConpareType.setText(">");
                break;
            default:
                holder.txtConpareType.setText("null");
        }

        holder.txtCompareValue.setText(condition.getCompareValue() + "");
    }

    @Override
    public int getItemCount() {
        return mListExecuCondition != null ? mListExecuCondition.size() : 0;
    }

    public void SetOnItemClickListener(OnItemClickListener mItemCliclListener) {
        this.mItemClickListenner = mItemCliclListener;
    }

    public interface OnItemClickListener {
        void onItemViewClick(View view, int position);

        void onImgOptionsClick(View view, int position);
    }

    public class ExecuConditionViewHoler extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_condition_name)
        TextView txtConditionName;
        @BindView(R.id.txt_device_node_name)
        TextView txtDeviceNodeName;
        @BindView(R.id.txt_compare_type)
        TextView txtConpareType;
        @BindView(R.id.txt_compare_value)
        TextView txtCompareValue;
        @BindView(R.id.img_options)
        ImageView imgOptions;

        View itemView;

        public ExecuConditionViewHoler(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListenner.onItemViewClick(view, getAdapterPosition());
                }
            });

            imgOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListenner.onImgOptionsClick(view, getAdapterPosition());
                }
            });
        }
    }
}
