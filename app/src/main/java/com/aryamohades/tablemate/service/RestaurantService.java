package com.aryamohades.tablemate.service;

import com.aryamohades.tablemate.model.MenuCategory;
import com.aryamohades.tablemate.model.MenuItem;
import com.aryamohades.tablemate.model.Restaurant;
import com.aryamohades.tablemate.model.Server;

import java.util.ArrayList;

import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface RestaurantService {
    String ENDPOINT = "http://10.0.3.2:8000/restaurant/";

    @FormUrlEncoded
    @POST("register/")
    Observable<Restaurant> registerRestaurant(
            @Field("name") String name,
            @Field("address") String address);

    @GET("{id}/")
    Observable<Restaurant> getRestaurant(@Path("id") String id);

    @POST("{id}/register_server/")
    Observable<Server> registerServer(
            @Path("id") String id,
            @Field("email") String email);

    @POST("{id}/create_menu_category/")
    Observable<Restaurant> createMenuCategory(@Path("id") String id, @Field("name") String name);

    @GET("{id}/menu_categories/")
    Observable<ArrayList<MenuCategory>> getMenuCategories(@Path("id") String id);

    @POST("{id}/create_menu_item/")
    Observable<MenuItem> createMenuItem(
            @Field("name") String name,
            @Field("price") Double price,
            @Field("category") String category,
            @Field("description") String description);

    @GET("{id}/menu/")
    Observable<ArrayList<MenuItem>> getMenu(@Path("id") String id);

    @GET("{restaurant_id}/menu/{item_id}")
    Observable<MenuItem> getMenuItem(
            @Path("restaurant_id") String restaurant_id,
            @Path("item_id") String item_id);
}
