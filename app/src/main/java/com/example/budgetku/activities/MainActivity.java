package com.example.budgetku.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.budgetku.R;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View content = findViewById(android.R.id.content);
        content.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        // Check if the initial data is ready.
                        if (checkIsLoggedIn()) {
                            // The content is ready; start drawing.
                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(i);
                        } else {
                            // The content is not ready; suspend.
                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);
                        }

                        content.getViewTreeObserver().removeOnPreDrawListener(this);

                        finish();
                        return true;
                    }

                    private boolean checkIsLoggedIn() {
                        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(SHARED_PREFS, 0);
                        return sharedPreferences.getBoolean("flag", false);
                    }
                });
    }
}