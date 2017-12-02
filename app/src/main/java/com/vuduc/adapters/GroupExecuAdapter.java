package com.vuduc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vuduc.models.GroupExecuResponse;
import com.vuduc.tluiot.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by VuDuc on 11/23/2017.
 */

public class GroupExecuAdapter extends RecyclerView.Adapter<GroupExecuAdapter.ExecuViewHoler> {

    OnItemClickListener mItemClickListenner;

    private List<GroupExecuResponse.ResultBean> mListGroupExecu;
    private Context mContext;

    public GroupExecuAdapter(Context context, List<GroupExecuResponse.ResultBean> listGroupExecu) {
        mListGroupExecu = listGroupExecu;
        mContext = context;
    }

    @Override
    public GroupExecuAdapter.ExecuViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_node, parent, false);
        ExecuViewHoler holer = new ExecuViewHoler(view);
        return holer;
    }

    @Override
    public void onBindViewHolder(ExecuViewHoler holder, int position) {
        GroupExecuResponse.ResultBean groupExecu = mListGroupExecu.get(position);
        holder.txtExecuName.setText(groupExecu.getName());
        if(groupExecu.getDescription().length()>30){
            String description = groupExecu.getDescription().substring(0, 30);
            holder.txtDeviceDescription.setText(description);
        } else {
            holder.txtDeviceDescription.setText(groupExecu.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return mListGroupExecu != null ? mListGroupExecu.size() : 0;
    }

    public void SetOnItemClickListener(GroupExecuAdapter.OnItemClickListener mItemCliclListener) {
        this.mItemClickListenner = mItemCliclListener;
    }

    public interface OnItemClickListener {
        void onItemViewClick(View view, int position);

        void onImgOptionsClick(View view, int position);
    }

    public class ExecuViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_device_image)
        ImageView ivExecuImage;
        @BindView(R.id.tv_device_name)
        TextView txtExecuName;
        @BindView(R.id.tv_device_description)
        TextView txtDeviceDescription;
        @BindView(R.id.img_options)
        ImageView imgOption;

        View itemView;

        public ExecuViewHoler(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListenner.onItemViewClick(view, getAdapterPosition());
                }
            });

            imgOption.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_options:
                    mItemClickListenner.onImgOptionsClick(view, getAdapterPosition());
            }
        }
    }
}
