package com.violinmd.nlpdp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.violinmd.nlpdp.MainActivity;
import com.violinmd.nlpdp.NLPDP;
import com.violinmd.nlpdp.R;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        splashScreen.setKeepOnScreenCondition(() -> true );

        Thread network = new Thread() {
            public void run() {
                if(NLPDP.loadOptions()){
                    runOnUiThread(() -> {
                        Intent homeIntent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                        finish();
                    });
                }
            }
        };
        network.start();




    }
}

