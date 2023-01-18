package com.example.budgetku.Api;

import com.example.budgetku.model.api.CategoryRequest;
import com.example.budgetku.model.api.CategoryResponse;
import com.example.budgetku.model.api.WalletResponse;
import com.example.budgetku.model.object.Response;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CategoryService {

    @GET("category")
    Call<CategoryResponse> getCategory(@Header("Authorization") String token);

    @POST("category")
    Call<Response> addCategory(@Header("Authorization") String token, @Body CategoryRequest categoryRequest);
}
