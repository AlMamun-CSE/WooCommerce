package com.alhumdulillah.woocommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = "SplashScreenActivity";

    //Firebase Var
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Log.d(TAG, "onCreate: started.");

        //Initialize Firebase Object
        mAuth = FirebaseAuth.getInstance();

        //Create Thread
        SystemClock.sleep(5000);


    }

    /**
     * First Time Check User Log In And Not LogIn
     */
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
            //Go To Register Activity
            Intent loginIntent = new Intent(SplashScreenActivity.this,RegisterActivity.class);
            startActivity(loginIntent);
            finish();
        }else {
            //Go To Main Activity
            Intent loginIntent = new Intent(SplashScreenActivity.this,MainActivity.class);
            startActivity(loginIntent);
            finish();
        }
    }
}