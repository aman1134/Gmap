package com.example.lattice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import javax.xml.parsers.SAXParser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Context context = SplashActivity.this;
        final SharedPreferences sharedPreferences = context.getSharedPreferences("user" , MODE_PRIVATE);

        View decor = getWindow().getDecorView();
        int ui = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decor.setSystemUiVisibility(ui);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPreferences.getBoolean("logged", false)) {
                    startActivity(new Intent(SplashActivity.this, DisplayUser.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, LogIn.class));
                    finish();
                }
            }
        }, 2000);
    }
}

