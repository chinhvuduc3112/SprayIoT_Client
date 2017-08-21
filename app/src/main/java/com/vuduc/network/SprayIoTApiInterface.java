package com.vuduc.network;

import com.vuduc.models.DeviceNodeResponse;
import com.vuduc.models.Node;
import com.vuduc.models.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by VuDuc on 8/12/2017.
 */

public interface SprayIoTApiInterface {
    @GET("getAllNodes")
    Call<Node> getAllNode();

    @GET("/getDeviceNodeByNode/{id}")
    Call<DeviceNodeResponse> getDeviceNodeByNode(@Path("id") String idNode);
}
