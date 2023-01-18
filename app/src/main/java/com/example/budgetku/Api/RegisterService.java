package com.example.budgetku.Api;

import com.example.budgetku.model.api.LoginRequest;
import com.example.budgetku.model.api.LoginResponse;
import com.example.budgetku.model.api.RegisterRequest;
import com.example.budgetku.model.api.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterService {
    @POST("register")
    Call<RegisterResponse> userRegister(@Body RegisterRequest registerRequest);
}
