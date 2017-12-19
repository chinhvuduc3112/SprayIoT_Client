package com.vuduc.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.vuduc.adapters.ListActuatorAdapter;
import com.vuduc.models.ActuatorInfosResponse;
import com.vuduc.network.ApiUtils;
import com.vuduc.network.SprayIoTApiInterface;
import com.vuduc.tluiot.ActuatorAddActivity;
import com.vuduc.tluiot.R;
import com.vuduc.until.ProgressDialogLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActuatorInfoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private final static String TAG = ActuatorInfoFragment.class.getSimpleName();

    @BindView(R.id.rv_list_actuator)
    RecyclerView rvListActuator;
    @BindView(R.id.srlLayout)
    SwipeRefreshLayout srlLayout;
    @BindView(R.id.fab_info_actuator)
    FloatingActionMenu fabInfoActuator;
    @BindView(R.id.fab_create_actuator)
    FloatingActionButton fabCreateActuator;
    @BindView(R.id.fab_gone_fab)
    FloatingActionButton fabGoneFab;

    List<ActuatorInfosResponse.Result> mListActuator = new ArrayList<>();

    private ListActuatorAdapter mListActuatorAdapter;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_actuator_info, container, false);
        ButterKnife.bind(this, view);
        mContext = getActivity().getApplicationContext();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getActuators();
            }
        }).start();
        addControls();
        addEvents();
    }

    private void addControls() {
        //RV actuator
        mListActuatorAdapter = new ListActuatorAdapter(getActivity(), mListActuator); //AlertDialog can getActivity()
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false);
        rvListActuator.setLayoutManager(layoutManager);
        rvListActuator.setItemAnimator(new DefaultItemAnimator());
        rvListActuator.setNestedScrollingEnabled(false);
        rvListActuator.setAdapter(mListActuatorAdapter);

        srlLayout.setOnRefreshListener(this);
    }

    private void addEvents() {
        fabCreateActuator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ActuatorAddActivity.class));
            }
        });
        fabGoneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabInfoActuator.setVisibility(View.GONE);
            }
        });
    }

    private void getActuators() {
        ProgressDialogLoader.progressdialog_creation(getActivity(), "Loading...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<ActuatorInfosResponse> callService = apiService.getActuatorInfos();
        callService.enqueue(new Callback<ActuatorInfosResponse>() {
            @Override
            public void onResponse(Call<ActuatorInfosResponse> call, Response<ActuatorInfosResponse> response) {
                if (response.isSuccessful())
                    initActuators(response.body());
                ProgressDialogLoader.progressdialog_dismiss();

            }

            @Override
            public void onFailure(Call<ActuatorInfosResponse> call, Throwable t) {
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void initActuators(ActuatorInfosResponse body) {
        if (body.getResult() != null) {
            List<ActuatorInfosResponse.Result> actuatorData = body.getResult();
            mListActuator.clear();
            for (ActuatorInfosResponse.Result a : actuatorData) {
                mListActuator.add(a);
            }
            mListActuatorAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getActuators();
                fabInfoActuator.setVisibility(View.VISIBLE);
                srlLayout.setRefreshing(false);
            }
        }, 1500);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
