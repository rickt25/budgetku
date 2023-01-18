package com.example.budgetku.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetku.Api.ApiClient;
import com.example.budgetku.R;
import com.example.budgetku.model.api.LoginRequest;
import com.example.budgetku.model.api.LoginResponse;
import com.example.budgetku.model.object.User;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private TextView linkRegister;
    public static final String SHARED_PREFS = "default";
    public static final String flag = "flag";
    public static final String user_id = "user_id";
    public static final String name = "name";
    public static final String username = "username";
    public static final String token = "token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(v -> {
            if (validate()){
                login();
            } else {
                Toast.makeText(LoginActivity.this, "Username and password must be filled!", Toast.LENGTH_SHORT).show();
                etPassword.setText("");
            }
        });

        linkRegister = findViewById(R.id.link_register);
        linkRegister.setOnClickListener(v -> {
            goToRegister(v);
        });
    }

    public boolean validate(){
        return !etUsername.getText().toString().matches("") &&
               !etPassword.getText().toString().matches("");
    }

    public void login(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(etUsername.getText().toString());
        loginRequest.setPassword(etPassword.getText().toString());

        Call<LoginResponse> loginResponseCall = ApiClient.loginService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                if (response.isSuccessful()){
                    User data = loginResponse.getUser();
                    storeSF(data, loginResponse.getToken());

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("data", loginResponse.getUser());
                    startActivity(intent);
                    finish();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(LoginActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                Toast.makeText(LoginActivity.this, "throwable" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void storeSF(User data, String apiToken){
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(SHARED_PREFS, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(flag, true);
        editor.putString(user_id, data.getId());
        editor.putString(name, data.getName());
        editor.putString(username, data.getName());
        editor.putString(token, apiToken);
        editor.apply();
    }

    public static void setCredential(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setFlag(String key, Boolean value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static String getCredential(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public static Boolean getFlag(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key, false);
    }

    public void goToRegister(View v){
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
    }


}