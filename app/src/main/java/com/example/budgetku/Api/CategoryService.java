package com.example.budgetku.Api;

import com.example.budgetku.model.api.CategoryResponse;
import com.example.budgetku.model.api.WalletResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CategoryService {

    @GET("category")
    Call<CategoryResponse> getCategory(@Header("Authorization") String token);
}
