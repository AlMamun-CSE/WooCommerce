package com.alhumdulillah.woocommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.alhumdulillah.woocommerce.Fragment.SignInFragment;
import com.alhumdulillah.woocommerce.Fragment.SignUpFragment;

import java.security.Key;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    // Static Var
    public static boolean forgetPasswordFragment = true;

    //private Var
    private FrameLayout frameLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d(TAG, "onCreate: started.");


        //Find All Android weigets
        frameLayout = findViewById(R.id.registerFrameLayoutId);
        setDefaultFragment(new SignInFragment());
    }


    //Back Stack for Code

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //if click phone back button
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if(forgetPasswordFragment){
                //Then abr forgetPassword = false
                forgetPasswordFragment = false;
                setFragment(new SignInFragment());
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    private void setDefaultFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();

    }

    /**
     * Set Up Any Fragment
     * @param fragment
     */
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_to_left,R.anim.salidout_to_right);
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}