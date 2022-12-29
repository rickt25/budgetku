package com.example.budgetku.Api;

import com.example.budgetku.model.api.LoginRequest;
import com.example.budgetku.model.api.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);
}
