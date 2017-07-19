package com.vuduc.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by VuDuc on 7/17/2017.
 */

public class WeatherResponse {

    /**
     * coord : {"lon":105.85,"lat":21.03}
     * weather : [{"id":500,"main":"Rain","description":"light rain","icon":"10n"},{"id":211,"main":"Thunderstorm","description":"thunderstorm","icon":"11n"}]
     * base : stations
     * main : {"temp":299.15,"pressure":1007,"humidity":100,"temp_min":299.15,"temp_max":299.15}
     * visibility : 10000
     * wind : {"speed":2.6,"deg":80}
     * clouds : {"all":40}
     * dt : 1500303600
     * sys : {"type":1,"id":7980,"message":0.0032,"country":"VN","sunrise":1500243888,"sunset":1500291639}
     * id : 1561096
     * name : Xóm Pho
     * cod : 200
     */

    @SerializedName("coord")
    private Coord coord;
    @SerializedName("base")
    private String base;
    @SerializedName("main")
    private Main main;
    @SerializedName("visibility")
    private int visibility;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("clouds")
    private Clouds clouds;
    @SerializedName("dt")
    private int dt;
    @SerializedName("sys")
    private Sys sys;
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("cod")
    private int cod;
    @SerializedName("weather")
    private List<Weather> weather;

    public static WeatherResponse objectFromData(String str) {

        return new Gson().fromJson(str, WeatherResponse.class);
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public static class Coord {
        /**
         * lon : 105.85
         * lat : 21.03
         */

        @SerializedName("lon")
        private double lon;
        @SerializedName("lat")
        private double lat;

        public static Coord objectFromData(String str) {

            return new Gson().fromJson(str, Coord.class);
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }

    public static class Main {
        /**
         * temp : 299.15
         * pressure : 1007
         * humidity : 100
         * temp_min : 299.15
         * temp_max : 299.15
         */

        @SerializedName("temp")
        private double temp;
        @SerializedName("pressure")
        private int pressure;
        @SerializedName("humidity")
        private int humidity;
        @SerializedName("temp_min")
        private double tempMin;
        @SerializedName("temp_max")
        private double tempMax;

        public static Main objectFromData(String str) {

            return new Gson().fromJson(str, Main.class);
        }

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public int getPressure() {
            return pressure;
        }

        public void setPressure(int pressure) {
            this.pressure = pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public double getTempMin() {
            return tempMin;
        }

        public void setTempMin(double tempMin) {
            this.tempMin = tempMin;
        }

        public double getTempMax() {
            return tempMax;
        }

        public void setTempMax(double tempMax) {
            this.tempMax = tempMax;
        }
    }

    public static class Wind {
        /**
         * speed : 2.6
         * deg : 80
         */

        @SerializedName("speed")
        private double speed;
        @SerializedName("deg")
        private int deg;

        public static Wind objectFromData(String str) {

            return new Gson().fromJson(str, Wind.class);
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public int getDeg() {
            return deg;
        }

        public void setDeg(int deg) {
            this.deg = deg;
        }
    }

    public static class Clouds {
        /**
         * all : 40
         */

        @SerializedName("all")
        private int all;

        public static Clouds objectFromData(String str) {

            return new Gson().fromJson(str, Clouds.class);
        }

        public int getAll() {
            return all;
        }

        public void setAll(int all) {
            this.all = all;
        }
    }

    public static class Sys {
        /**
         * type : 1
         * id : 7980
         * message : 0.0032
         * country : VN
         * sunrise : 1500243888
         * sunset : 1500291639
         */

        @SerializedName("type")
        private int type;
        @SerializedName("id")
        private int id;
        @SerializedName("message")
        private double message;
        @SerializedName("country")
        private String country;
        @SerializedName("sunrise")
        private int sunrise;
        @SerializedName("sunset")
        private int sunset;

        public static Sys objectFromData(String str) {

            return new Gson().fromJson(str, Sys.class);
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getMessage() {
            return message;
        }

        public void setMessage(double message) {
            this.message = message;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getSunrise() {
            return sunrise;
        }

        public void setSunrise(int sunrise) {
            this.sunrise = sunrise;
        }

        public int getSunset() {
            return sunset;
        }

        public void setSunset(int sunset) {
            this.sunset = sunset;
        }
    }

    public static class Weather {
        /**
         * id : 500
         * main : Rain
         * description : light rain
         * icon : 10n
         */

        @SerializedName("id")
        private int id;
        @SerializedName("main")
        private String main;
        @SerializedName("description")
        private String description;
        @SerializedName("icon")
        private String icon;

        public static Weather objectFromData(String str) {

            return new Gson().fromJson(str, Weather.class);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
