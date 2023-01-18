package com.example.budgetku.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.budgetku.Api.ApiClient;
import com.example.budgetku.R;
import com.example.budgetku.model.api.LoginResponse;
import com.example.budgetku.model.api.WalletRequest;
import com.example.budgetku.model.api.WalletResponse;
import com.example.budgetku.model.object.Response;

import retrofit2.Call;
import retrofit2.Callback;

public class AddWalletActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "default";
    private String token;

    private AlertDialog.Builder dialog;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallet);

        dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Wallet Added");
        dialog.setTitle("Success!");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int id) {
                finish();
            }
        });
        alertDialog = dialog.create();

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        EditText wallet_name = findViewById(R.id.wallet_name);
        EditText initial_balance = findViewById(R.id.initial_balance);

        ImageButton saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWallet(wallet_name.getText().toString(), Integer.parseInt(initial_balance.getText().toString()));
            }
        });
    }

    private void addWallet(String wallet_name, Integer balance)
    {
        this.token = new String();
        WalletRequest walletRequest = new WalletRequest();
        walletRequest.setWallet_name(wallet_name);
        walletRequest.setBalance(balance);

        SharedPreferences shared =  getApplication().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        this.token = "Bearer " + shared.getString(LoginActivity.token,"");

        Log.d("Token: ", this.token);
        Call<Response> walletResponseCall = ApiClient.walletService().addWallet(this.token, walletRequest);

        walletResponseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.isSuccessful())
                {
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

    }
}