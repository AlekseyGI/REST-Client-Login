package com.example.restclientlogin.login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {

    @POST("/login")
    @Headers("Content-Type: application/json")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

}
