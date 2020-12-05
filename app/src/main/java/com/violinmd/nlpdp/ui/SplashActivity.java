package com.violinmd.nlpdp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.violinmd.nlpdp.MainActivity;
import com.violinmd.nlpdp.NLPDP;
import com.violinmd.nlpdp.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        setContentView(R.layout.activity_splash);
        Thread network = new Thread() {
            public void run() {
                if(NLPDP.loadOptions()){
                    runOnUiThread(() -> {
                        new Handler().postDelayed(() -> {
                            Intent homeIntent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(homeIntent);
                            finish();
                        },SPLASH_TIME_OUT);
                    });
                }
            }
        };
        network.start();




    }
}

