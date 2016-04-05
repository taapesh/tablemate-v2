package com.aryamohades.tablemate.service;

import com.aryamohades.tablemate.model.User;

import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

public interface AuthService {
    String ENDPOINT = "http://10.0.3.2:8000/auth/";

    @FormUrlEncoded
    @POST("login/")
    Observable<User> login(@Field("email") String email, @Field("password") String password);

    @POST("logout/")
    Observable<Response<Void>> logout(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("register/")
    Observable<User> register(
            @Field("first_name") String firstName,
            @Field("last_name") String lastName,
            @Field("email") String email,
            @Field("password") String password);

    @POST("clear/")
    Observable<Response<Void>> clearDatabase();
}
