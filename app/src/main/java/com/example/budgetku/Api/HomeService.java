package com.example.budgetku.Api;

import com.example.budgetku.model.api.CategoryResponse;
import com.example.budgetku.model.api.OverviewResponse;
import com.example.budgetku.model.object.Overview;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface HomeService {

    @GET("overview")
    Call<OverviewResponse> getOverview(@Header("Authorization") String token);
}
