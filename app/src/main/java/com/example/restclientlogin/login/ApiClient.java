package com.example.restclientlogin.login;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.Interceptor;
import okhttp3.Response;

public class ApiClient {

    private static Retrofit getRetrofit(String authToken){

        Interceptor interceptor = new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {

                return chain.proceed(chain
                        .request()
                        .newBuilder()
                        .header("Authorization", "Bearer"+authToken)
                        .build());
            }
        };

        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(logger)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://192.168.0.102:8080")
                .client(client)
                .build();

        return retrofit;

    }

    public static UserService getUserService(String authToken){
        UserService userService = getRetrofit(authToken).create(UserService.class);

        return userService;
    }

}
