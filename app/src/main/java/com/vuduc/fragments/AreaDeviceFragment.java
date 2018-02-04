package com.vuduc.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vuduc.adapters.AreaNodeAdapter;
import com.vuduc.models.Node;
import com.vuduc.network.ApiUtils;
import com.vuduc.network.SprayIoTApiInterface;
import com.vuduc.tluiot.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AreaDeviceFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public final String TAG = AreaDeviceFragment.class.getSimpleName();

    @BindView(R.id.rv_list_node)
    RecyclerView rv_list_node;
    @BindView(R.id.rv_list_actuator)
    RecyclerView rv_list_actuator;
    @BindView(R.id.srlLayout)
    SwipeRefreshLayout srlLayout;
    private Context mContext;
    private List<Node.ResultBean> listNode;
    private AreaNodeAdapter mAreaNodeAdapter;
    private String mAreaID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_area_device, container, false);
        ButterKnife.bind(this, v);
        mContext = getActivity().getApplicationContext();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls();
    }

    private void addControls() {
        srlLayout.setOnRefreshListener(this);

        listNode = new ArrayList<>();
        mAreaNodeAdapter = new AreaNodeAdapter(listNode, getActivity().getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv_list_node.setLayoutManager(mLayoutManager);
        rv_list_node.setItemAnimator(new DefaultItemAnimator());
        rv_list_node.setAdapter(mAreaNodeAdapter);
    }

    private void getNodeByIdArea(String areaID) {
        if (areaID != null) {
            SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
            Call<Node> callNodes = apiService.getNodeByIdArea(areaID);
            callNodes.enqueue(new Callback<Node>() {
                @Override
                public void onResponse(Call<Node> call, Response<Node> response) {
                    if (response.isSuccessful()) {
                        initListNode(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Node> call, Throwable t) {

                }
            });
        }
    }

    private void initListNode(Node data) {
        List<Node.ResultBean> listBean = data.getResult();
        listNode.clear();
        Log.d(TAG, "initListNode: "+listBean.size());
        for (Node.ResultBean lb : listBean) {
            listNode.add(lb);
        }
        mAreaNodeAdapter.notifyDataSetChanged();
    }

    public void setAreaID(String areaID) {
        mAreaID = areaID;
        getNodeByIdArea(areaID);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //handler
                getNodeByIdArea(mAreaID);
                srlLayout.setRefreshing(false);
            }
        }, 1500);
    }
}
