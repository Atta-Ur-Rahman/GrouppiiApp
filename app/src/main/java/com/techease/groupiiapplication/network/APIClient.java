package com.techease.groupiiapplication.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    APIClient context = this;
    public static final String BASE_URL = "http://grouppii.com:4000/api/v1/";
    public static final String HOTEL_BASE_URL="https://test.api.amadeus.com/v2/shopping/";
    private static Retrofit retrofit = null;
    private static Retrofit retrofitHotel= null;


    public static Retrofit getClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Customize the request

                Request request = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Content-Type", "form-data")
                        .method(original.method(), original.body())
                        .build();

                Response response = chain.proceed(request);

                // Customize or return the response
                return response;
            }
        });

        OkHttpClient OkHttpClient = httpClient.build();


        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpClient)
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClient(final String token) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        HttpLoggingInterceptor interceptor1 = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(interceptor1)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        // Customize the request

                        Request request = original.newBuilder()
                                .header("Accept", "application/json")
                                .header("Authorization", "Bearer " + token)
                                .method(original.method(), original.body())
                                .build();

                        Response response = chain.proceed(request);

                        // Customize or return the response
                        return response;
                    }
                });

        OkHttpClient OkHttpClient = httpClient.build();


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient)
                .build();
        return retrofit;
    }


    public static Retrofit getClientForHotel(String token) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        HttpLoggingInterceptor interceptor1 = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(interceptor1)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        // Customize the request
                        Request request = original.newBuilder()
                                .header("Accept", "application/json")
                                .header("Authorization", "Bearer " + token)
                                .method(original.method(), original.body())
                                .build();

                        Response response = chain.proceed(request);

                        // Customize or return the response
                        return response;
                    }
                });

        OkHttpClient OkHttpClient = httpClient.build();


        retrofitHotel = new Retrofit.Builder()
                .baseUrl(HOTEL_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient)
                .build();
        return retrofitHotel;
    }

    public static Retrofit getClientForRegisteration(final String apiKey) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        HttpLoggingInterceptor interceptor1 = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(interceptor1)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        // Customize the request

                        Request request = original.newBuilder()
                                .header("Accept", "application/json")
                                .header("apiKey", apiKey)
                                .method(original.method(), original.body())
                                .build();

                        Response response = chain.proceed(request);

                        // Customize or return the response
                        return response;
                    }
                });

        OkHttpClient OkHttpClient = httpClient.build();


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient)
                .build();
        return retrofit;
    }



}