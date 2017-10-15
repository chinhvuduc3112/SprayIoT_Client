package com.vuduc.network;

import com.vuduc.models.AreaByIdResponse;
import com.vuduc.models.AreaResponse;
import com.vuduc.models.DataByDaysResponse;
import com.vuduc.models.DataByHoursResponse;
import com.vuduc.models.DeviceNodeResponse;
import com.vuduc.models.Node;
import com.vuduc.models.WeatherResponse;

import butterknife.internal.ListenerClass;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @GET("/getAreas")
    Call<AreaResponse> getAreas();

    @GET("/getArea/{id}")
    Call<AreaByIdResponse> getAreaById(@Path("id") String idArea);

    @GET("/getNodeByIdArea/{id}")
    Call<Node> getNodeByIdArea(@Path("id") String areaId);

    @GET("/getChartByHours/{timestamp}?")
    Call<DataByHoursResponse> getChartByHours(@Path("timestamp") Long currentDay,
                                              @Query("deviceNodeId") String deviceNodeId);

    @GET("/getChartByDays?")
    Call<DataByDaysResponse> getChartByDay(@Query("from") Long fromDay, @Query("to") Long toDay,
                                           @Query("deviceNodeId") String deviceNodeId);

    @FormUrlEncoded
    @POST("/addArea")
    Call<AreaResponse> addArea(@Field("name") String areaName, @Field("note") String areaNode,
                               @Field("x") int x, @Field("y") int y);

    @FormUrlEncoded
    @POST("/addNode")
    Call<Node> addNode(@Field("name") String nodeName, @Field("description") String description,
                       @Field("idArea") String idArea, @Field("note") String note);

    @FormUrlEncoded
    @PUT("/updateArea")
    Call<AreaResponse> updateArea(@Field("name") String areaName, @Field("note") String areaNode,
                                  @Field("x") int x, @Field("y") int y,
                                  @Field("trash") Boolean isTrash, @Field("_id") String id);


    @FormUrlEncoded
    @PUT("/updateNode")
    Call<Node> updateNode(@Field("_id") String id, @Field("name") String name,
                          @Field("description") String description, @Field("idArea") String idArea,
                          @Field("note") String note, @Field("trash") Boolean isTrash);
}
