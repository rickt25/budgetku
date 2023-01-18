package com.example.budgetku.Api;

import com.example.budgetku.model.api.WalletRequest;
import com.example.budgetku.model.api.WalletResponse;
import com.example.budgetku.model.object.Response;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface WalletService {
    @GET("wallet")
    Call<WalletResponse> getWalletById(@Header("Authorization") String token);

    @DELETE("wallet/{wallet_id}")
    Call<Response> deleteWalletById(@Header("Authorization") String token, @Path("wallet_id") String wallet_id);

    @POST("wallet")
    Call<Response> addWallet(@Header("Authorization") String token, @Body WalletRequest walletRequest);
}
