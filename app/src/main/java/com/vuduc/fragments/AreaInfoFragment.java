package com.vuduc.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.vuduc.models.AreaResponse;
import com.vuduc.network.ApiUtils;
import com.vuduc.network.SprayIoTApiInterface;
import com.vuduc.tluiot.AreaActivity;
import com.vuduc.tluiot.AreaAddActivity;
import com.vuduc.tluiot.R;
import com.vuduc.until.Logger;
import com.vuduc.until.ProgressDialogLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AreaInfoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public final String TAG = AreaInfoFragment.class.getSimpleName();
    @BindView(R.id.spinner_list_area)
    MaterialSpinner spinner_list_area;
    @BindView(R.id.edit_area_name)
    EditText edit_area_name;
    @BindView(R.id.edit_area_describe)
    EditText edit_area_describe;
    @BindView(R.id.edit_area_x)
    EditText edit_area_x;
    @BindView(R.id.edit_area_y)
    EditText edit_area_y;
    @BindView(R.id.srlLayout)
    SwipeRefreshLayout srlLayout;
    @BindView(R.id.img_options)
    ImageView img_options;

    List<AreaResponse.Result> listArea;
    List<String> arrAreaName;
    private Context mContext;
    private String mAreaId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_area_info, container, false);
        ButterKnife.bind(this, v);
        arrAreaName = new ArrayList<>();
        mContext = getActivity().getApplicationContext();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getAreas();
            }
        }).start();
        addControls();
        addEvents();
    }

    private void addEvents() {

        spinner_list_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != -1) {
                    String areaName = listArea.get(i).getName();
                    String areaNote = listArea.get(i).getNote();
                    int x = listArea.get(i).getX();
                    int y = listArea.get(i).getY();

                    edit_area_name.setText(areaName);
                    edit_area_describe.setText(areaNote);
                    edit_area_x.setText(x + "");
                    edit_area_y.setText(y + "");

                    mAreaId = listArea.get(i).getId();
                    Logger.d(TAG, mAreaId);
                    //mSharedPref.setAreaId(mAreaId);
                    ((AreaActivity) getActivity()).transferData(mAreaId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        img_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(img_options);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(getActivity(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_area_info, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    private void addControls() {
        //Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrAreaName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_list_area.setAdapter(adapter);

        srlLayout.setOnRefreshListener(this);
    }

    private void getAreas() {
        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<AreaResponse> callAreas = apiService.getAreas();
        callAreas.enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                if (response.isSuccessful()) {
                    initAreas(response.body());
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {

            }
        });
    }

    private void initAreas(AreaResponse data) {
        listArea = new ArrayList<>();
        if (data != null) {
            listArea = data.getResult();
            arrAreaName.clear();
            for (AreaResponse.Result a : listArea) {
                arrAreaName.add(a.getName());
            }
        }
    }

    private void requestUpdateArea(String name, String note, int areaX, int areaY) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Updating...");

        SprayIoTApiInterface apiService = ApiUtils.getSprayIoTApiService();
        Call<AreaResponse> callAreas = apiService.updateArea(name, note, areaX, areaY, false, mAreaId);
        callAreas.enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.action_update_info_succesful, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, R.string.toast_update_area_fail, Toast.LENGTH_SHORT).show();
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {
                Logger.d(TAG, t.toString());
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getAreas();
                srlLayout.setRefreshing(false);
            }
        }, 2500);
    }


    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        int areaX = 0;
        int areaY = 0;
        String name, note;

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_update_info:
                    getTextBox();
                    if (!TextUtils.isEmpty(mAreaId))
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                requestUpdateArea(name, note, areaX, areaY);
                            }
                        }).start();
                    return true;
                case R.id.action_create_area:
                    startActivity(new Intent(getActivity(), AreaAddActivity.class));
                    return true;
                default:
            }
            return false;
        }

        private void getTextBox() {
            name = String.valueOf(edit_area_name.getText());
            note = String.valueOf(edit_area_describe.getText());

            if (!edit_area_x.getText().toString().equals("")) {
                areaX = Integer.parseInt(edit_area_x.getText().toString());
            }
            if (!edit_area_y.getText().toString().equals("")) {
                areaY = Integer.parseInt(edit_area_y.getText().toString());
            }
        }
    }
}
