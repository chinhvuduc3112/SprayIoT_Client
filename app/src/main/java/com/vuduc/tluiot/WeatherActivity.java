package com.vuduc.tluiot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import com.vuduc.models.WeatherResponse;
import com.vuduc.network.ApiClient;
import com.vuduc.network.ApiInterface;

import java.text.SimpleDateFormat;
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

    WeatherResponse weatherResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityName = edit_city_name.getText().toString();
                String cityName1 = "Hanoi";
                if (cityName.equals("")) {
                    cityName=cityName1;
                }
                getWeatherApi(cityName);
            }
        });
    }

    private void getWeatherApi(String cityName) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<WeatherResponse> callWeather = apiService.getWeatherPresent(cityName, API_KEY);
        callWeather.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                Log.d("message", response.toString());
                if(response.isSuccessful()){
                    init(response.body());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private void init(WeatherResponse data) {
        txt_city.setText(data.getName());

        List<WeatherResponse.Weather> weathers = data.getWeather();
        String t = weathers.get(0).getDescription();
        txt_status.setText(t);

        long longDay = Long.valueOf(data.getDt());
        Date date = new Date(1*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
        String Day = simpleDateFormat.format(date);
        txt_day.setText(Day);

        String iconStatus = weathers.get(0).getIcon();

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
