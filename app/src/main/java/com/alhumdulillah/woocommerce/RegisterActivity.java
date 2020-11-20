package com.alhumdulillah.woocommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.alhumdulillah.woocommerce.Fragment.SignInFragment;
import com.alhumdulillah.woocommerce.Fragment.SignUpFragment;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    //private Var
    private FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d(TAG, "onCreate: started.");


        //Find All Android weigets
        frameLayout = findViewById(R.id.registerFrameLayoutId);
        setFragment(new SignInFragment());
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();

    }
}