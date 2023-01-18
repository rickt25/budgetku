package com.example.budgetku.Api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit getRetrofit(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

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

    public static WalletService walletService()
    {
        WalletService walletService = getRetrofit().create(WalletService.class);
        return walletService;
    }

    public static CategoryService categoryService()
    {
        CategoryService categoryService = getRetrofit().create(CategoryService.class);
        return categoryService;
    }

    public static ActivityService activityService()
    {
        ActivityService activityService = getRetrofit().create(ActivityService.class);
        return activityService;
    }

    public static HomeService homeService()
    {
        HomeService homeService = getRetrofit().create(HomeService.class);
        return homeService;
    }
}
