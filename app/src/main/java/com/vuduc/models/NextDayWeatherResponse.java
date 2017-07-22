package com.vuduc.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by VuDuc on 7/21/2017.
 */

public class NextDayWeatherResponse {

    /**
     * city : {"id":1561096,"name":"Xóm Pho","coord":{"lon":105.85,"lat":21.0333},"country":"VN","population":0}
     * cod : 200
     * message : 1.4162144
     * cnt : 7
     * list : [{"dt":1500613200,"temp":{"day":299.15,"min":297.47,"max":299.15,"night":297.47,"eve":299.15,"morn":299.15},"pressure":1013.77,"humidity":98,"weather":[{"id":501,"main":"Rain","description":"moderate rain","icon":"10d"}],"speed":1.12,"deg":117,"clouds":92,"rain":5.47},{"dt":1500699600,"temp":{"day":302.12,"min":297.36,"max":302.84,"night":297.71,"eve":300.66,"morn":297.36},"pressure":1014.1,"humidity":90,"weather":[{"id":502,"main":"Rain","description":"heavy intensity rain","icon":"10d"}],"speed":2.51,"deg":321,"clouds":56,"rain":20.81},{"dt":1500786000,"temp":{"day":305.01,"min":298.87,"max":305.46,"night":298.87,"eve":302.54,"morn":298.94},"pressure":1010.6,"humidity":83,"weather":[{"id":800,"main":"Clear","description":"sky is clear","icon":"01d"}],"speed":1.95,"deg":314,"clouds":0},{"dt":1500872400,"temp":{"day":306.55,"min":299.12,"max":306.55,"night":299.12,"eve":303.42,"morn":300.25},"pressure":1006.74,"humidity":77,"weather":[{"id":501,"main":"Rain","description":"moderate rain","icon":"10d"}],"speed":2.87,"deg":336,"clouds":12,"rain":5.09},{"dt":1500958800,"temp":{"day":303.81,"min":299.62,"max":303.81,"night":299.62,"eve":301.12,"morn":300.09},"pressure":998.74,"humidity":0,"weather":[{"id":502,"main":"Rain","description":"heavy intensity rain","icon":"10d"}],"speed":5.38,"deg":8,"clouds":20,"rain":25.04},{"dt":1501045200,"temp":{"day":300.84,"min":298.34,"max":300.84,"night":298.34,"eve":299.42,"morn":299.58},"pressure":997.46,"humidity":0,"weather":[{"id":503,"main":"Rain","description":"very heavy rain","icon":"10d"}],"speed":8,"deg":10,"clouds":91,"rain":80.55},{"dt":1501131600,"temp":{"day":298.74,"min":297.95,"max":298.93,"night":298.4,"eve":298.93,"morn":297.95},"pressure":1000.47,"humidity":0,"weather":[{"id":503,"main":"Rain","description":"very heavy rain","icon":"10d"}],"speed":6.71,"deg":110,"clouds":97,"rain":78.51}]
     */

    @SerializedName("city")
    private CityBean city;
    @SerializedName("cod")
    private String cod;
    @SerializedName("message")
    private double message;
    @SerializedName("cnt")
    private int cnt;
    @SerializedName("list")
    private List<ListBean> list;

    public static NextDayWeatherResponse objectFromData(String str) {

        return new Gson().fromJson(str, NextDayWeatherResponse.class);
    }

    public CityBean getCity() {
        return city;
    }

    public void setCity(CityBean city) {
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class CityBean {
        /**
         * id : 1561096
         * name : Xóm Pho
         * coord : {"lon":105.85,"lat":21.0333}
         * country : VN
         * population : 0
         */

        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("coord")
        private CoordBean coord;
        @SerializedName("country")
        private String country;
        @SerializedName("population")
        private int population;

        public static CityBean objectFromData(String str) {

            return new Gson().fromJson(str, CityBean.class);
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

        public CoordBean getCoord() {
            return coord;
        }

        public void setCoord(CoordBean coord) {
            this.coord = coord;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getPopulation() {
            return population;
        }

        public void setPopulation(int population) {
            this.population = population;
        }

        public static class CoordBean {
            /**
             * lon : 105.85
             * lat : 21.0333
             */

            @SerializedName("lon")
            private double lon;
            @SerializedName("lat")
            private double lat;

            public static CoordBean objectFromData(String str) {

                return new Gson().fromJson(str, CoordBean.class);
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
    }

    public static class ListBean {
        /**
         * dt : 1500613200
         * temp : {"day":299.15,"min":297.47,"max":299.15,"night":297.47,"eve":299.15,"morn":299.15}
         * pressure : 1013.77
         * humidity : 98
         * weather : [{"id":501,"main":"Rain","description":"moderate rain","icon":"10d"}]
         * speed : 1.12
         * deg : 117
         * clouds : 92
         * rain : 5.47
         */

        @SerializedName("dt")
        private int dt;
        @SerializedName("temp")
        private TempBean temp;
        @SerializedName("pressure")
        private double pressure;
        @SerializedName("humidity")
        private int humidity;
        @SerializedName("speed")
        private double speed;
        @SerializedName("deg")
        private int deg;
        @SerializedName("clouds")
        private int clouds;
        @SerializedName("rain")
        private double rain;
        @SerializedName("weather")
        private List<WeatherBean> weather;

        public static ListBean objectFromData(String str) {

            return new Gson().fromJson(str, ListBean.class);
        }

        public int getDt() {
            return dt;
        }

        public void setDt(int dt) {
            this.dt = dt;
        }

        public TempBean getTemp() {
            return temp;
        }

        public void setTemp(TempBean temp) {
            this.temp = temp;
        }

        public double getPressure() {
            return pressure;
        }

        public void setPressure(double pressure) {
            this.pressure = pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
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

        public int getClouds() {
            return clouds;
        }

        public void setClouds(int clouds) {
            this.clouds = clouds;
        }

        public double getRain() {
            return rain;
        }

        public void setRain(double rain) {
            this.rain = rain;
        }

        public List<WeatherBean> getWeather() {
            return weather;
        }

        public void setWeather(List<WeatherBean> weather) {
            this.weather = weather;
        }

        public static class TempBean {
            /**
             * day : 299.15
             * min : 297.47
             * max : 299.15
             * night : 297.47
             * eve : 299.15
             * morn : 299.15
             */

            @SerializedName("day")
            private double day;
            @SerializedName("min")
            private double min;
            @SerializedName("max")
            private double max;
            @SerializedName("night")
            private double night;
            @SerializedName("eve")
            private double eve;
            @SerializedName("morn")
            private double morn;

            public static TempBean objectFromData(String str) {

                return new Gson().fromJson(str, TempBean.class);
            }

            public double getDay() {
                return day;
            }

            public void setDay(double day) {
                this.day = day;
            }

            public double getMin() {
                return min;
            }

            public void setMin(double min) {
                this.min = min;
            }

            public double getMax() {
                return max;
            }

            public void setMax(double max) {
                this.max = max;
            }

            public double getNight() {
                return night;
            }

            public void setNight(double night) {
                this.night = night;
            }

            public double getEve() {
                return eve;
            }

            public void setEve(double eve) {
                this.eve = eve;
            }

            public double getMorn() {
                return morn;
            }

            public void setMorn(double morn) {
                this.morn = morn;
            }
        }

        public static class WeatherBean {
            /**
             * id : 501
             * main : Rain
             * description : moderate rain
             * icon : 10d
             */

            @SerializedName("id")
            private int id;
            @SerializedName("main")
            private String main;
            @SerializedName("description")
            private String description;
            @SerializedName("icon")
            private String icon;

            public static WeatherBean objectFromData(String str) {

                return new Gson().fromJson(str, WeatherBean.class);
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
}
