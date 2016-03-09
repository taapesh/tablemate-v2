package com.aryamohades.tablemate.service;

import retrofit2.Retrofit;
import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceFactory {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    /**
     * Create a retrofit service from an arbitrary class
     * @param clazz Java interface of the retrofit service
     * @param endPoint REST endpoint url
     * @return retrofit service with defined endpoint
     */
    public static <T> T createService(final Class<T> clazz, final String endPoint) {
        final Retrofit.Builder builder =
            new Retrofit.Builder()
                .baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(clazz);
    }
}
