package com.vuduc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vuduc.models.Node;
import com.vuduc.tluiot.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by VuDuc on 9/5/2017.
 */

public class AreaNodeAdapter extends RecyclerView.Adapter<AreaNodeAdapter.ViewHolder>{

    private List<Node.ResultBean> node;
    private Context mContext;

    public AreaNodeAdapter(List<Node.ResultBean> node, Context context) {
        this.node = node;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_device_area, parent, false);
        AreaNodeAdapter.ViewHolder holder = new AreaNodeAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Node.ResultBean myNode = node.get(position);
        holder.txt_device_name.setText(myNode.getName());
    }

    @Override
    public int getItemCount() {
        return node.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        @BindView(R.id.txt_device_name)
        TextView txt_device_name;
        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
