package com.vuduc.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.vuduc.tluiot.MainActivity;
import com.vuduc.tluiot.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ganfra.materialspinner.MaterialSpinner;


public class NodeStatisticsFragment extends Fragment {

    @BindView(R.id.spin_list_node)
    MaterialSpinner spin_list_node;
    @BindView(R.id.spin_type_statistics)
    MaterialSpinner spin_type_statistics;
    @BindView(R.id.edit_date_form)
    EditText edit_date_form;
    @BindView(R.id.edit_date_to)
    EditText edit_date_to;

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");

    public NodeStatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_node_statistics, container, false);
        ButterKnife.bind(this, v);
        addEvents();
        return v;
    }

    private void addEvents() {
        edit_date_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDatePicker(edit_date_form);
            }
        });

        edit_date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDatePicker(edit_date_to);
            }
        });
    }

    private void setDatePicker(final EditText edit_date_picker) {
        DatePickerDialog.OnDateSetListener callBack=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);
                edit_date_picker.setText(sdf.format(calendar.getTime()));
            }
        };
        DatePickerDialog dialog=new DatePickerDialog(
                getContext(),
                callBack,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
}
