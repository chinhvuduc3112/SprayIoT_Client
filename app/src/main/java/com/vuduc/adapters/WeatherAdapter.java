package com.vuduc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vuduc.models.NextDayWeatherResponse;
import com.vuduc.models.WeatherResponse;
import com.vuduc.tluiot.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by VuDuc on 7/21/2017.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private List<NextDayWeatherResponse.ListBean> weathers;
    private Context context;

    public WeatherAdapter(Context context, List<NextDayWeatherResponse.ListBean> weathers) {
        this.weathers = weathers;
        this.context = context;
    }

    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_next_day, parent, false);
//        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        view.setLayoutParams(lp);
        WeatherAdapter.ViewHolder holder = new WeatherAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(WeatherAdapter.ViewHolder holder, int position) {
        NextDayWeatherResponse.ListBean listBean  = weathers.get(position);
        long longDay = Long.valueOf(listBean.getDt());
        Date date = new Date(longDay*1000L);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EEEE");
        String Day = simpleDateFormat1.format(date);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM-dd");
        String Day2 = simpleDateFormat2.format(date);
        holder.txt_thu.setText(Day);
        holder.txt_ngaythang.setText(Day2);

        List<NextDayWeatherResponse.ListBean.WeatherBean> weatherStatus = listBean.getWeather();
        Picasso.with(context).load("http://openweathermap.org/img/w/"+weatherStatus.get(0).getIcon()+".png").into(holder.img_icon_status);

        Double nhietDoMax =listBean.getTemp().getMax();
        String tempMax = String.valueOf(nhietDoMax.intValue());
        Double nhietDoMin = listBean.getTemp().getMin();
        String tempMin = String.valueOf(nhietDoMin.intValue());
        holder.txt_temp_maxmin.setText(tempMax+"°/"+tempMin+"°");

        int cloudiness = listBean.getClouds();
        holder.txt_cloudiness.setText(String.valueOf(cloudiness));
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_thu)
        TextView txt_thu;
        @BindView(R.id.txt_ngaythang)
        TextView txt_ngaythang;
        @BindView(R.id.img_icon_status)
        ImageView img_icon_status;
        @BindView(R.id.txt_temp_maxmin)
        TextView txt_temp_maxmin;
        @BindView(R.id.txt_cloudiness)
        TextView txt_cloudiness;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
