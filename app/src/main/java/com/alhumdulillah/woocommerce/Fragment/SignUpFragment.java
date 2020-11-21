package com.alhumdulillah.woocommerce.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alhumdulillah.woocommerce.MainActivity;
import com.alhumdulillah.woocommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpFragment extends Fragment {
    private static final String TAG = "SignUpFragment";

    //Firebase Var
    private FirebaseAuth mAuth;


    //Another Var
    private String mValidEmailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    //private Var
    private TextView alReadyHaveAnAccount;
    private FrameLayout parentFrameLayout;
    private EditText signUpUserEmail,signUpUserName,signUpUserPassword,signUpUserConfirmPassword;
    private ImageButton signUpCrossBtn;
    private Button signUpBtn;
    private ProgressBar signUpProgressBar;



    public SignUpFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started.");

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_up, container, false);


        //define----------------------------------------------------------------------
        alReadyHaveAnAccount = view.findViewById(R.id.alReadyHaveAnAccountId);
        parentFrameLayout = getActivity().findViewById(R.id.registerFrameLayoutId);
        signUpUserEmail = view.findViewById(R.id.signUpEmailId);
        signUpUserName = view.findViewById(R.id.signUpNameId);
        signUpUserPassword = view.findViewById(R.id.signUpPasswordId);
        signUpUserConfirmPassword = view.findViewById(R.id.signUpConfirmPasswordId);
        signUpCrossBtn = view.findViewById(R.id.signUpCloseBtnId);
        signUpBtn = view.findViewById(R.id.signUpBtnId);
        signUpProgressBar = view.findViewById(R.id.signUpProgressBarId);


        mAuth = FirebaseAuth.getInstance();


        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Set All Weiget Action
        alReadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set Up Fragment
                setFragment(new SignInFragment());
            }
        });

        //Set Up Listener
        signUpUserEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Calling Method
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signUpUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Calling Method
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signUpUserPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Calling Method
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signUpUserConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Calling Method
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calling Method
                CheckEmailAndPassword();
            }
        });

    }


    /**
     * Set Up Any Fragment
     * @param fragment
     */
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_to_left,R.anim.salidout_to_right);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }


    /**
     * Check User Input Empty And Not Empty
     */
    private void checkInputs() {
        if (!TextUtils.isEmpty(signUpUserEmail.getText())){
            if (!TextUtils.isEmpty(signUpUserName.getText())){
                if (!TextUtils.isEmpty(signUpUserPassword.getText()) && signUpUserPassword.length() >=8){
                    if (!TextUtils.isEmpty(signUpUserConfirmPassword.getText())){
                        signUpBtn.setEnabled(true);
                        signUpBtn.setTextColor(Color.rgb(255,255,255));
                    }else {
                        signUpBtn.setEnabled(false);
                        signUpBtn.setTextColor(Color.argb(50,255,255,255));
                    }
                }else {
                    signUpBtn.setEnabled(false);
                    signUpBtn.setTextColor(Color.argb(50,255,255,255));
                }
            }else {
                signUpBtn.setEnabled(false);
                signUpBtn.setTextColor(Color.argb(50,255,255,255));
            }
        }else {
            signUpBtn.setEnabled(false);
            signUpBtn.setTextColor(Color.argb(50,255,255,255));
        }
    }



    /**
     * Check Email And Password Valid and Invalid
     */
    private void CheckEmailAndPassword() {
        if (signUpUserEmail.getText().toString().matches(mValidEmailPattern)){
            if (signUpUserPassword.getText().toString().equals(signUpUserConfirmPassword.getText().toString())){

                //Visible progress
                signUpProgressBar.setVisibility(View.VISIBLE);
                //Invisible sign up button
                signUpBtn.setEnabled(false);
                signUpBtn.setTextColor(Color.argb(50,255,255,255));

                //create firebase user account with email and password
                mAuth.createUserWithEmailAndPassword(signUpUserEmail.getText().toString(),signUpUserPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    //Go To Main Activity
                                    Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(mainIntent);
                                    getActivity().finish();

                                }else {
                                    //visible sign up button
                                    signUpBtn.setEnabled(true);
                                    signUpBtn.setTextColor(Color.rgb(255,255,255));
                                    //Invisible progress bar
                                    signUpProgressBar.setVisibility(View.INVISIBLE);
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
            else {
                signUpUserPassword.setError("Password doesn't matched!");
            }
        }else {
            signUpUserEmail.setError("Invalid Email!");
        }

    }
}