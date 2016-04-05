package com.aryamohades.tablemate.service;

import com.aryamohades.tablemate.model.Order;
import com.aryamohades.tablemate.model.Receipt;
import com.aryamohades.tablemate.model.Restaurant;
import com.aryamohades.tablemate.model.Table;
import com.aryamohades.tablemate.model.User;

import java.util.ArrayList;

import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface UserService {
    String ENDPOINT = "http://10.0.3.2:8000/user/";

    @GET("")
    Observable<User> getUser(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("table/create_or_join/")
    Observable<Table> createOrJoinTable(
            @Header("Authorization") String token,
            @Field("restaurant_id") String id,
            @Field("table_number") String table_number);

    @GET("table/")
    Observable<Response<Table>> getTable(@Header("Authorization") String token);

    @POST("table/request/")
    Observable<Response<Void>> makeRequest(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("table/add_to_order/")
    Observable<Order> addToOrder(
            @Header("Authorization") String token,
            @Field("item_id") String item_id);

    @POST("table/place_order/")
    Observable<Response<Void>> placeOrder(@Header("Authorization") String token);

    @GET("receipts/")
    Observable<ArrayList<Receipt>> getReceipts(@Header("Authorization") String token);

    @GET("receipts/{id}/")
    Observable<Receipt> getReceipt(
            @Header("Authorization") String token,
            @Path("id") String id);

    @GET("pending_orders/")
    Observable<ArrayList<Order>> getPendingOrders(@Header("Authorization") String token);

    @GET("active_orders/")
    Observable<ArrayList<Order>> getActiveOrders(@Header("Authorization") String token);

    @GET("orders/{id}")
    Observable<Order> getOrder(
            @Header("Authorization") String token,
            @Path("id") String id);

    @POST("table/pay/")
    Observable<Receipt> pay(@Header("Authorization") String token);

    @GET("nearby_restaurants/")
    Observable<ArrayList<Restaurant>> getNearbyRestaurants(
            @Header("Authorization") String token);
}
