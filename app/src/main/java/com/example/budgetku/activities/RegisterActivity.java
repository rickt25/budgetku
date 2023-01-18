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
import com.example.budgetku.model.api.RegisterRequest;
import com.example.budgetku.model.api.RegisterResponse;
import com.example.budgetku.model.object.User;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPassword, etConfirmPassword;
    public static final String SHARED_PREFS = "default";
    public static final String flag = "flag";
    public static final String user_id = "user_id";
    public static final String name = "name";
    public static final String username = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);

        Button btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(v -> {
            if (validate()){
                register();
            } else {
                Toast.makeText(RegisterActivity.this, "Username and password must be filled!", Toast.LENGTH_SHORT).show();
                etPassword.setText("");
                etConfirmPassword.setText("");
            }
        });

        TextView linkLogin = findViewById(R.id.link_login);
        linkLogin.setOnClickListener(v -> {
            goToLogin(v);
        });
    }

    private boolean validate() {
        if(etEmail.getText().toString().equals("") ||
                etName.getText().toString().equals("") ||
                etConfirmPassword.getText().toString().equals("") ||
                etPassword.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())){
            Toast.makeText(RegisterActivity.this, "Password and Confirm Password is not matching", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void register() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(etEmail.getText().toString());
        registerRequest.setName(etName.getText().toString());
        registerRequest.setPassword(etPassword.getText().toString());

        Call<RegisterResponse> registerResponseCall = ApiClient.registerService().userRegister(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();

                if (response.isSuccessful()) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    //intent.putExtra("data", registerResponse.getUser());
                    startActivity(intent);
                    finish();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(RegisterActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegisterResponse> call, @NonNull Throwable t) {
                Toast.makeText(RegisterActivity.this, "throwable" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void storeSF(User data){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFS, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(flag, true);
        editor.putString(user_id, data.getId());
        editor.putString(name, data.getName());
        editor.putString(username, data.getName());
        editor.apply();
    }

    public void goToLogin(View v){
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }
}