package com.vuduc.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.vuduc.tluiot.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ganfra.materialspinner.MaterialSpinner;


public class SensorRealtimeFragment extends Fragment {

    @BindView(R.id.spinner)
    MaterialSpinner spinner;

    String[] listNode = {"Node 1", "Node 2", "Node 3", "Node 4", "Node 5", "Node 6"};

    public SensorRealtimeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_sensor_realtime, container, false);
        ButterKnife.bind(this, v);
        addEvents();
        return v;
    }

    private void addEvents() {
        //Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listNode);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
