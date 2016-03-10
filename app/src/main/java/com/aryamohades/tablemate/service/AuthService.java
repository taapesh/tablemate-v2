package com.aryamohades.tablemate.service;

import com.aryamohades.tablemate.model.User;

import org.json.JSONObject;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

public interface AuthService {
    String API_BASE = "https://mysterious-journey-88182.herokuapp.com/auth/";

    @FormUrlEncoded
    @POST("login")
    Observable<User> login(@Field("email") String email, @Field("password") String password);

    @POST("logout")
    Observable<JSONObject> logout(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("register")
    Observable<User> register(
            @Field("first_name") String firstName,
            @Field("last_name") String lastName,
            @Field("email") String email,
            @Field("password") String password
    );
}
