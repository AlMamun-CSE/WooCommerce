package com.alhumdulillah.woocommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = "SplashScreenActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Log.d(TAG, "onCreate: started.");

        SystemClock.sleep(5000);
        Intent loginIntent = new Intent(SplashScreenActivity.this,RegisterActivity.class);
        startActivity(loginIntent);
        finish();
    }
}