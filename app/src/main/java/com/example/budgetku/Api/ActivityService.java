package com.example.budgetku.Api;

import com.example.budgetku.model.api.ActivityRequest;
import com.example.budgetku.model.api.ActivityResponse;
import com.example.budgetku.model.api.TodayActivityResponse;
import com.example.budgetku.model.object.Response;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ActivityService {

    @POST("activity")
    Call<Response> addActivity(@Header("Authorization") String token, @Body ActivityRequest activityRequest);

    @GET("activity")
    Call<ActivityResponse> getAllActivities(@Header("Authorization") String token);

    @GET("today-activity")
    Call<TodayActivityResponse> getTodayActivities(@Header("Authorization") String token);
}
