package com.example.e_voting_system;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class splash_screen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
                Intent intent;
                if (isLoggedIn) {
                    intent = new Intent(splash_screen.this, home_screen.class);
                } else {
                    intent = new Intent(splash_screen.this, login_screen.class);
                }
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
