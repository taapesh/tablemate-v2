package com.aryamohades.tablemate.service;

import com.aryamohades.tablemate.model.HttpResponse;
import com.aryamohades.tablemate.model.Table;

import java.util.ArrayList;

import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

public interface TableService {
    String API_BASE = "https://mysterious-journey-88182.herokuapp.com/";

    @GET("server/tables/")
    Observable<ArrayList<Table>> getServerTables(@Header("Authorization") String token);

    @GET("user/table/")
    Observable<Table> getActiveTable(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("tables/")
    Observable<Table> createOrJoinTable(
        @Header("Authorization") String token,
        @Field("restaurant_name") String name,
        @Field("restaurant_addr") String address,
        @Field("table_number") String number
    );

    @POST("table/request_service/")
    Observable<Response<HttpResponse>> requestService(@Header("Authorization") String token);

}
