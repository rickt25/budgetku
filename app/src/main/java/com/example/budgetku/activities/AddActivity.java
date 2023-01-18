package com.example.budgetku.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.budgetku.Api.ApiClient;
import com.example.budgetku.R;
import com.example.budgetku.adapters.CategoryAutoCompleteAdapter;
import com.example.budgetku.adapters.WalletAutoCompleteAdapter;
import com.example.budgetku.model.api.CategoryResponse;
import com.example.budgetku.model.api.WalletRequest;
import com.example.budgetku.model.api.WalletResponse;
import com.example.budgetku.model.object.Category;
import com.example.budgetku.model.object.Response;
import com.example.budgetku.model.object.Wallet;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class AddActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "default";
    private String token;

    private AutoCompleteTextView choose_wallet;
    private AutoCompleteTextView choose_category;
    private EditText amount;
    private RadioGroup radioGroup;
    private RadioButton radioIncome;
    private RadioButton radioExpense;

    List<Wallet> wallets;
    List<Category> categories;

    private Wallet choosed_wallet;
    private Category choosed_category;
    private Integer input_amount;
    private Integer choosed_type;



    private AlertDialog.Builder dialog;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initializeDialog();

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SharedPreferences shared = getApplication().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        this.token = "Bearer " + shared.getString(LoginActivity.token,"");

        choose_wallet = findViewById(R.id.choose_wallet);
        choose_category = findViewById(R.id.choose_category);

        loadWallets();
        loadCategory();

        choose_wallet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                choosed_wallet = (Wallet) adapterView.getItemAtPosition(i);
            }
        });

        choose_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                choosed_category = (Category) adapterView.getItemAtPosition(i);
            }
        });

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioIncome = (RadioButton) findViewById(R.id.radio_income);
        radioExpense = (RadioButton) findViewById(R.id.radio_expense);





        ImageButton saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addActivity();
            }
        });
    }

    private void addActivity()
    {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton selected = (RadioButton) findViewById(selectedId);
        if(selected.equals(radioIncome))
        {

        }
    }

    private void loadCategory()
    {
        Call<CategoryResponse> categoryResponseCall = ApiClient.categoryService().getCategory(token);

        categoryResponseCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, retrofit2.Response<CategoryResponse> response) {
                CategoryResponse categoryResponse = response.body();

                if(response.isSuccessful())
                {
                    categories = categoryResponse.getData();
                    CategoryAutoCompleteAdapter adapter = new CategoryAutoCompleteAdapter(getApplicationContext(), categories);

                    choose_category.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

            }
        });
    }

    private void loadWallets()
    {
        Call<WalletResponse> walletResponseCall = ApiClient.walletService().getWalletById(token);

        walletResponseCall.enqueue(new Callback<WalletResponse>() {
            @Override
            public void onResponse(Call<WalletResponse> call, retrofit2.Response<WalletResponse> response) {
                WalletResponse walletResponse = response.body();

                if(response.isSuccessful())
                {
                    if(!walletResponse.getData().isEmpty())
                    {
                        wallets = walletResponse.getData();
                        WalletAutoCompleteAdapter adapter = new WalletAutoCompleteAdapter(getApplicationContext(), wallets);

                        choose_wallet.setAdapter(adapter);
                    }
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<WalletResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "throwable" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeDialog()
    {
        dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Wallet Added");
        dialog.setTitle("Success!");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int id) {
                finish();
            }
        });
        alertDialog = dialog.create();
    }

    private void addWallet(String wallet_name, Integer balance)
    {
        this.token = new String();
        WalletRequest walletRequest = new WalletRequest();
        walletRequest.setWallet_name(wallet_name);
        walletRequest.setBalance(balance);

        SharedPreferences shared = getApplication().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        this.token = "Bearer " + shared.getString(LoginActivity.token, "");

        Log.d("Token: ", this.token);
        Call<Response> walletResponseCall = ApiClient.walletService().addWallet(this.token, walletRequest);

        walletResponseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

    }
}