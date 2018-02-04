package com.vuduc.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vuduc.models.FunctionByAcResponse;
import com.vuduc.models.ManualUpdateFunction;
import com.vuduc.network.ApiUtils;
import com.vuduc.network.SprayIoTApiInterface;
import com.vuduc.tluiot.R;
import com.vuduc.until.ProgressDialogLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void onBindViewHolder(final ListFunctionAdapter.MyViewHolder holder, int position) {
        final FunctionByAcResponse.Result function = mListFunction.get(position);

        holder.txt_actuator_name.setText(function.getName());
        if (function.getActivityDuration() < 0) {
            holder.txt_actuator_time.setText("0");
        } else {
            holder.txt_actuator_time.setText(function.getActivityDuration() + "");
        }

        if (function.isStatus()) {
            holder.switch_actuator_realtime.setChecked(true);
            startCountDownTimer(holder, function);
        } else {
            holder.switch_actuator_realtime.setChecked(false);
        }

        holder.switch_actuator_realtime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //true
                    if (!holder.isRunning || function.isStatus() == false) {
                        manualONFunction(holder, function);
                    }
                } else {
                    //false
                    if (holder.isRunning & function.isStatus() == true) {
                        manualPauseFunction(holder, function);
                    }
                }
            }
        });
    }

    private void manualONFunction(final MyViewHolder holder, final FunctionByAcResponse.Result function) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Chỉnh thời gian bật");
        builder.setCancelable(false); //click outSide to dismiss dialog
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View alertLayout = inflater.inflate(R.layout.dialog_manual_time, null);
        builder.setView(alertLayout);

        //addControls
        final EditText editTime = alertLayout.findViewById(R.id.edit_time);

        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int time = Integer.parseInt(String.valueOf(editTime.getText()));
                manualUpdateStatusFunctionResponse(holder, function, time, true);
            }
        });

        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                holder.switch_actuator_realtime.setChecked(false);
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void manualPauseFunction(final MyViewHolder holder, final FunctionByAcResponse.Result function) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Spray IoT");
        builder.setMessage("Bạn có chắc muốn TẮT không?");
        builder.setCancelable(false); //click outSide to dismiss dialog=
        builder.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                manualUpdateStatusFunctionResponse(holder, function, 0, false);
                holder.isRunning = false;
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                holder.switch_actuator_realtime.setChecked(true);
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void manualUpdateStatusFunctionResponse(MyViewHolder holder, FunctionByAcResponse.Result function, int i, final boolean b) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Updating...");

        if (function.getId() != null) {
            SprayIoTApiInterface apiInterface = ApiUtils.getSprayIoTApiService();
            retrofit2.Call<ManualUpdateFunction> callFunction = apiInterface.manualUpdateFunctionStatus(function.getId(), i, b);
            callFunction.enqueue(new Callback<ManualUpdateFunction>() {
                @Override
                public void onResponse(Call<ManualUpdateFunction> call, Response<ManualUpdateFunction> response) {
                    if (response.isSuccessful()) {

                        updateListFunction(response.body());

                        if (b) {
                            Toast.makeText(mContext, "BẬT thành công \n Relaod trang để tải lại thông tin", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "TẮT thành công \n Relaod trang để tải lại thông tin", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, "Hành động thất bại", Toast.LENGTH_SHORT).show();
                    }
                    ProgressDialogLoader.progressdialog_dismiss();
                }

                private void updateListFunction(ManualUpdateFunction body) {
                    List<ManualUpdateFunction.ResultBean.FunctionsBean> functions = body.getResult().getFunctions();
                    for (FunctionByAcResponse.Result a : mListFunction) {
                        for (ManualUpdateFunction.ResultBean.FunctionsBean b : functions) {
                            if (a.getId().equals(b.getId())) {
                                a.setActivityDuration(b.getActivityDuration());
                                a.setStatus(b.isStatus());
                            }
                        }
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ManualUpdateFunction> call, Throwable t) {
                    ProgressDialogLoader.progressdialog_dismiss();
                }
            });
        }
    }

    private void startCountDownTimer(final MyViewHolder holder, FunctionByAcResponse.Result function) {
        long startTime = function.getActivityDuration() * 60000;

        holder.mCountDownTimer = new CountDownTimer(startTime, 60000) {
            @Override
            public void onTick(long l) {
                if (holder.isRunning) {
                    holder.txt_actuator_time.setText(l / 60000 + " min");
                    holder.isRunning = true;
                }
            }

            @Override
            public void onFinish() {
                holder.txt_actuator_time.setText("Dừng");
                holder.isRunning = false;
                holder.switch_actuator_realtime.setChecked(false);
            }
        }.start();
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
        CountDownTimer mCountDownTimer;
        boolean isRunning = true;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.setIsRecyclable(false);
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
