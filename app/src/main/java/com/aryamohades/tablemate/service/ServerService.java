package com.aryamohades.tablemate.service;

import com.aryamohades.tablemate.model.Order;
import com.aryamohades.tablemate.model.Restaurant;
import com.aryamohades.tablemate.model.Table;

import java.util.ArrayList;

import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface ServerService {
    String ENDPOINT = "http://10.0.3.2:8000/server/";

    @GET("restaurants/{id}/tables/")
    Observable<ArrayList<Table>> getTables(
            @Header("Authorization") String token,
            @Path("id") String id);

    @GET("tables/{id}/")
    Observable<Table> getTable(
            @Header("Authorization") String token,
            @Path("id") String id);

    @POST("tables/{id}/bump/")
    Observable<Response<Void>> bumpTable(
            @Header("Authorization") String token,
            @Path("id") String id);

    @GET("tables/{id}/orders/")
    Observable<ArrayList<Order>> getTableOrders(
            @Header("Authorization") String token,
            @Path("id") String id);

    @GET("tables/{table_id}/orders/{order_id}/")
    Observable<Order> getOrder(
            @Header("Authorization") String token,
            @Path("table_id") String table_id,
            @Path("order_id") String order_id);

    @GET("restaurants/")
    Observable<ArrayList<Restaurant>> getRestaurants(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("start_serving/")
    Observable<Response<Void>> startServing(
            @Header("Authorization") String token,
            @Field("restaurant_id") String restaurantId);
}
