package com.example.budgetku.Api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit getRetrofit(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://laravel-production-9acb.up.railway.app/api/")
                .client(okHttpClient)
                .build();

        return retrofit;
    }

    public static LoginService loginService(){
        LoginService loginService = getRetrofit().create(LoginService.class);
        return loginService;
    }

    public static RegisterService registerService(){
        RegisterService registerService = getRetrofit().create(RegisterService.class);
        return registerService;
    }
}
