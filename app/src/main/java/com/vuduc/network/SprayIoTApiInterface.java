package com.vuduc.network;

import com.vuduc.models.ActuatorInfosResponse;
import com.vuduc.models.ActuatorsResponse;
import com.vuduc.models.AreaByIdResponse;
import com.vuduc.models.AreaResponse;
import com.vuduc.models.AutoableResponse;
import com.vuduc.models.DataByDaysResponse;
import com.vuduc.models.DataByHoursResponse;
import com.vuduc.models.DeviceNodeResponse;
import com.vuduc.models.DeviceTypeResponse;
import com.vuduc.models.ExecuConditionByGroupResponse;
import com.vuduc.models.FunctionByAcResponse;
import com.vuduc.models.FunctionsResponse;
import com.vuduc.models.GroupExecuResponse;
import com.vuduc.models.ManualUpdateActuator;
import com.vuduc.models.ManualUpdateFunction;
import com.vuduc.models.Node;
import com.vuduc.models.NodeByIdResponse;

import java.security.acl.Group;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
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

    @GET("/getAllDeviceTypes")
    Call<DeviceTypeResponse> getAllDeviceTypes();

    @GET("/getChartByHours/{timestamp}?")
    Call<DataByHoursResponse> getChartByHours(@Path("timestamp") Long currentDay,
                                              @Query("deviceNodeId") String deviceNodeId);

    @GET("/getChartByDays?")
    Call<DataByDaysResponse> getChartByDay(@Query("from") Long fromDay, @Query("to") Long toDay,
                                           @Query("deviceNodeId") String deviceNodeId);

    @GET("/getActuators")
    Call<ActuatorsResponse> getActuators();

    @GET("/getInfoActuators")
    Call<ActuatorInfosResponse> getActuatorInfos();

    @GET("/getFunctionByActuatorId/{id}")
    Call<FunctionByAcResponse> getFunctionByActuatorId(@Path("id") String actuatorID);

    @GET("/getFunctions")
    Call<FunctionsResponse> getFunctions();

    @GET("/getGroupExcutionConditionByFunction/{id}")
    Call<GroupExecuResponse> getGroupExecuByFunction(@Path("id") String functionID);

    @GET("/getExecutionConditionByGroup/{id}")
    Call<ExecuConditionByGroupResponse> getExecutionConditionByGroup(@Path("id") String groupID);

    @FormUrlEncoded
    @POST("/addArea")
    Call<AreaResponse> addArea(@Field("name") String areaName, @Field("note") String areaNode,
                               @Field("x") int x, @Field("y") int y);

    @FormUrlEncoded
    @POST("/addNode")
    Call<Node> addNode(@Field("name") String nodeName, @Field("description") String description,
                       @Field("idArea") String idArea, @Field("note") String note);

    @FormUrlEncoded
    @POST("/addDeviceNode")
    Call<DeviceNodeResponse> addDeviceNode(@Field("name") String nodeName, @Field("description") String description,
                                           @Field("note") String note, @Field("deviceTypeId") String deviceTypeId,
                                           @Field("nodeId") String nodeId);

    @FormUrlEncoded
    @POST("/addDeviceType")
    Call<DeviceTypeResponse> addDeviceType(@Field("name") String name, @Field("note") String note);

    @FormUrlEncoded
    @POST("/addActuator")
    Call<ActuatorsResponse> addActuator(@Field("name") String nodeName, @Field("description") String description,
                                        @Field("deviceTypeId") String deviceTypeId, @Field("idArea") String idArea);

    @FormUrlEncoded
    @POST("/addFunction")
    Call<FunctionsResponse> addFunction(@Field("name") String functionName, @Field("description") String description,
                                        @Field("actuatorId") String actuatorId);

    @FormUrlEncoded
    @POST("/addGroupExcutionCondition")
    Call<GroupExecuResponse> addGroupCondition(@Field("name") String nodeName, @Field("description") String description,
                                               @Field("functionId") String functionId, @Field("autoTime") int autoTime, @Field("status") Boolean status);

    @FormUrlEncoded
    @POST("/addExecutionCondition")
    Call<ExecuConditionByGroupResponse> addCondition(@Field("name") String name, @Field("description") String description,
                                                     @Field("groupExecutionConditionId") String groupID, @Field("deviceNodeId") String deviceNodeID,
                                                     @Field("compare") int compare, @Field("compareValue") int compareValue);

    @FormUrlEncoded
    @PUT("/updateArea")
    Call<AreaResponse> updateArea(@Field("name") String areaName, @Field("note") String areaNode,
                                  @Field("x") int x, @Field("y") int y,
                                  @Field("trash") Boolean isTrash, @Field("_id") String id);


    @FormUrlEncoded
    @PUT("/updateNode")
    Call<NodeByIdResponse> updateNode(@Field("_id") String id, @Field("name") String name,
                                      @Field("description") String description, @Field("idArea") String idArea,
                                      @Field("note") String note, @Field("trash") Boolean isTrash);

    @FormUrlEncoded
    @PUT("/updateDeviceNode")
    Call<DeviceNodeResponse>updateDeviceNode(@Field("_id") String id, @Field("name") String nodeName,
                                              @Field("description") String description, @Field("note") String note,
                                              @Field("deviceTypeId") String deviceTypeId,
                                              @Field("nodeId") String nodeId, @Field("trash") Boolean isTrash);

    @FormUrlEncoded
    @PUT("/updateDeviceType")
    Call<DeviceTypeResponse> updateDeviceType(@Field("_id") String id, @Field("name") String name,
                                              @Field("note") String note, @Field("trash") Boolean isTrash);

    @FormUrlEncoded
    @PUT("/updateInfoActuator")
    Call<ActuatorsResponse> updateInfoActuator(@Field("_id") String id, @Field("name") String name, @Field("description") String description,
                                           @Field("idArea") String idArea, @Field("deviceTypeId") String deviceTypeId);

    @FormUrlEncoded
    @PUT("/updateInfoFunction")
    Call<FunctionsResponse> updateInfoFunction(@Field("_id") String id, @Field("name") String name, @Field("description") String description,
                                               @Field("actuatorId") String actuatorId);

    @FormUrlEncoded
    @PUT("/updateInfoExecutionCondition")
    Call<ExecuConditionByGroupResponse> updateInfoCondition(@Field("_id") String id, @Field("name") String name, @Field("description") String description,
                                                            @Field("compare") int compare, @Field("compareValue") int compareValue,
                                                            @Field("deviceNodeId") String deviceNodeID);

    @FormUrlEncoded
    @PUT("/updateGroupExcutionCondition")
    Call<GroupExecuResponse> updateGroupExecu(@Field("_id") String id, @Field("name") String name, @Field("description") String description,
                                              @Field("functionId") String functionId, @Field("status") Boolean groupType, @Field("autoTime") int autoTime);

    @FormUrlEncoded
    @PUT("/manualUpdateStatusActuator")
    Call<ManualUpdateActuator> manualUpdateStatusActuator(@Field("actuatorId") String actuatorId, @Field("time") int time,
                                                          @Field("status") Boolean status);

    @FormUrlEncoded
    @PUT("/manualUpdateFunctionStatusById")
    Call<ManualUpdateFunction> manualUpdateFunctionStatus(@Field("functionId") String functionId, @Field("time") int time,
                                                          @Field("status") Boolean status);

    @DELETE("/deleteDeviceNode/{id}")
    Call<ResponseBody> deleteDeviceNode(@Path("id") String deviceNodeId);

    @DELETE("/deleteDeviceType/{id}")
    Call<ResponseBody> deleteDeviceType(@Path("id") String deviceTypeId);

    @DELETE("/deleteFunction/{id}")
    Call<ResponseBody> deleteFunction(@Path("id") String funcionId);

    @GET("/getAutoable")
    Call<AutoableResponse> getAutoable();

    @FormUrlEncoded
    @POST("/setAutoable")
    Call<AutoableResponse> setAutoable(@Field("able") boolean able);
}
