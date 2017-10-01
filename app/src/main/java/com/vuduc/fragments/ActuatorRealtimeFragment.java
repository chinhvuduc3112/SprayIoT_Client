package com.vuduc.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vuduc.adapters.ActuatorRealtimeAdapter;
import com.vuduc.models.ActuatorRealtime;
import com.vuduc.tluiot.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActuatorRealtimeFragment extends Fragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private ActuatorRealtimeAdapter adapter;
    private List<ActuatorRealtime> actuatorList;

    public ActuatorRealtimeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_actuator_realtime, container, false);
        ButterKnife.bind(this, v);
        addEvents();
        return v;
    }

    private void addEvents() {
        actuatorList = new ArrayList<>();
        adapter = new ActuatorRealtimeAdapter(getContext(), actuatorList);
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareActuator();
    }

    private void prepareActuator() {
        ActuatorRealtime a = new ActuatorRealtime("Thiết bị 1", 3600000L, true);
        actuatorList.add(a);
        a = new ActuatorRealtime("Thiết bị 2", 3600000L, true);
        actuatorList.add(a);
        a = new ActuatorRealtime("Thiết bị 3", 3600000L, false);
        actuatorList.add(a);
        a = new ActuatorRealtime("Thiết bị 4", 3600000L, true);
        actuatorList.add(a);
        a = new ActuatorRealtime("Thiết bị 5", 3600000L, false);
        actuatorList.add(a);
        a = new ActuatorRealtime("Thiết bị 6", 3600000L, true);
        actuatorList.add(a);
        a = new ActuatorRealtime("Thiết bị 7", 3600000L, false);
        actuatorList.add(a);
        a = new ActuatorRealtime("Thiết bị 8", 3600000L, true);
        actuatorList.add(a);
        a = new ActuatorRealtime("Thiết bị 9", 3600000L, false);
        actuatorList.add(a);
        a = new ActuatorRealtime("Thiết bị 10", 3600000L, true);
        actuatorList.add(a);
        a = new ActuatorRealtime("Thiết bị 11", 3600000L, false);
        actuatorList.add(a);
        a = new ActuatorRealtime("Thiết bị 12", 3600000L, true);
        actuatorList.add(a);
        a = new ActuatorRealtime("Thiết bị 13", 3600000L, false);
        actuatorList.add(a);
        a = new ActuatorRealtime("Thiết bị 14", 3600000L, true);
        actuatorList.add(a);
        adapter.notifyDataSetChanged();
    }
}
