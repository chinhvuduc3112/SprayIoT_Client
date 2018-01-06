package com.vuduc.tluiot;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vuduc.adapters.WeatherAdapter;
import com.vuduc.models.NextDayWeatherResponse;
import com.vuduc.models.WeatherResponse;
import com.vuduc.network.ApiUtils;
import com.vuduc.network.RetrofitClient;
import com.vuduc.network.WeatherApiInterface;
import com.vuduc.until.ProgressDialogLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {

    private static final String TAG = WeatherActivity.class.getSimpleName();
    private final static String API_KEY = "0ac144176c255747798f398017a1da7d";
    public static final String MYWEATHER_URL = "http://api.openweathermap.org/data/2.5/";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edit_city_name)
    EditText edit_city_name;
    @BindView(R.id.btn_search)
    Button btn_search;
    @BindView(R.id.txt_city)
    TextView txt_city;
    @BindView(R.id.txt_day)
    TextView txt_day;
    @BindView(R.id.img_icon_status)
    ImageView img_icon_status;
    @BindView(R.id.txt_temp_avg)
    TextView txt_temp_avg;
    @BindView(R.id.txt_temp_max)
    TextView txt_temp_max;
    @BindView(R.id.txt_temp_min)
    TextView txt_temp_min;
    @BindView(R.id.txt_status)
    TextView txt_status;
    @BindView(R.id.txt_humidity)
    TextView txt_humidity;
    @BindView(R.id.txt_cloudiness)
    TextView txt_cloudiness;
    @BindView(R.id.rv_next_day)
    RecyclerView rv_next_day;
    private WeatherAdapter weatherAdapter;
    private List<NextDayWeatherResponse.ListBean> listWeather;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        mContext = this;
        addControls();
        addEvents();
    }

    private void addEvents() {
        //RV
        listWeather = new ArrayList<>();
        weatherAdapter = new WeatherAdapter(mContext, listWeather);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        rv_next_day.setLayoutManager(layoutManager);
        rv_next_day.setItemAnimator(new DefaultItemAnimator());
        rv_next_day.setAdapter(weatherAdapter);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityName = edit_city_name.getText().toString();
                String cityName1 = "Ha noi";
                if (cityName.equals("")) {
                    cityName = cityName1;
                }
                getWeatherApi(cityName);
                getNextDayWeatherApi(cityName);
            }
        });

    }

    private void getNextDayWeatherApi(String cityName) {

        WeatherApiInterface apiService = ApiUtils.getWeatherApiService();
        Call<NextDayWeatherResponse> callNextDayWeather = apiService.getNextDayWeather(cityName, "metric", API_KEY);
        callNextDayWeather.enqueue(new Callback<NextDayWeatherResponse>() {
            @Override
            public void onResponse(Call<NextDayWeatherResponse> call, Response<NextDayWeatherResponse> response) {
                if(response.isSuccessful()){
                    Log.d("message", response.toString());
                    initNextDayWeatherApi(response.body());
                }
            }
            @Override
            public void onFailure(Call<NextDayWeatherResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private void initNextDayWeatherApi(NextDayWeatherResponse data) {
        if (!data.getCod().equals("404")) {
            List<NextDayWeatherResponse.ListBean> listBean = data.getList();
            listWeather.clear();
            for (NextDayWeatherResponse.ListBean lb : listBean) {
                listWeather.add(lb);
            }
            weatherAdapter.notifyDataSetChanged();
        }else{
            Toast.makeText(mContext, "City not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void getWeatherApi(String cityName) {
        ProgressDialogLoader.progressdialog_creation(mContext, "Loading...");
        WeatherApiInterface apiService = ApiUtils.getWeatherApiService();
        Call<WeatherResponse> callWeather = apiService.getWeatherPresent(cityName, "metric", API_KEY);
        callWeather.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("message", response.toString());
                    initWeatherApi(response.body());
                }
                ProgressDialogLoader.progressdialog_dismiss();
            }
            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }

    private void initWeatherApi(WeatherResponse data) {
        if (!data.getWeather().equals("")){
            txt_city.setText(data.getName());

            List<WeatherResponse.Weather> weathers = data.getWeather();
            String t = weathers.get(0).getDescription();
            txt_status.setText(t);
            Log.d("trang thai", t);

            long longDay = Long.valueOf(data.getDt());
            Date date = new Date(longDay * 1000L);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
            String Day = simpleDateFormat.format(date);
            txt_day.setText(Day);

            String iconStatus = weathers.get(0).getIcon();
            Picasso.with(WeatherActivity.this).load("http://openweathermap.org/img/w/" + iconStatus + ".png").into(img_icon_status);

            WeatherResponse.Main main = data.getMain();
            Double nhietDoAvg = main.getTemp();
            String NhieuDoAvg = String.valueOf(nhietDoAvg.intValue());
            Double nhietDoMax = main.getTempMax();
            String NhieuDoMax = String.valueOf(nhietDoMax.intValue());
            Double nhietDoMin = main.getTempMin();
            String NhieuDoMin = String.valueOf(nhietDoMin.intValue());
            int doAm = main.getHumidity();
            String DoAm = String.valueOf(doAm);

            txt_temp_avg.setText(NhieuDoAvg + "°C");
            txt_temp_max.setText(NhieuDoMax + "°C");
            txt_temp_min.setText(NhieuDoMin + "°C");
            txt_humidity.setText(DoAm + "%");

            WeatherResponse.Clouds clouds = data.getClouds();
            int cloudinessTemp = clouds.getAll();
            String cloudiness = String.valueOf(cloudinessTemp);
            txt_cloudiness.setText(cloudiness + "%");
        }else{
            Toast.makeText(mContext, "Có gì đó sai sai ở đây!", Toast.LENGTH_SHORT).show();
        }
    }


    private void addControls() {
        ButterKnife.bind(this);

        //Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.nav_item_weather);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
