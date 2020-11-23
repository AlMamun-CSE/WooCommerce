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

public class SignInFragment extends Fragment {
    private static final String TAG = "SignInFragment";

    //Firebase Var
    private FirebaseAuth mAuth;

    //privet weigets var
    private TextView doNotHaveAnAccount,forGetPassword;
    private EditText signInEmail,signInPassword;
    private ImageButton signInCloseBtn;
    private Button signInBtn;
    private ProgressBar signInProgressBar;
    private FrameLayout frameLayout;

    //Another Var
    private String mValidEmailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";




    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started.");
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_in, container, false);


        //define----------------------------------------------------------------------
        doNotHaveAnAccount = view.findViewById(R.id.signInRegisterHereId);
        frameLayout = getActivity().findViewById(R.id.registerFrameLayoutId);
        forGetPassword = view.findViewById(R.id.signInForgetPasswordId);
        signInEmail = view.findViewById(R.id.signInEmailId);
        signInPassword = view.findViewById(R.id.signInPasswordId);
        signInCloseBtn = view.findViewById(R.id.signInCloseBtnId);
        signInProgressBar = view.findViewById(R.id.signInProgressBarId);
        signInBtn = view.findViewById(R.id.signInBtnId);


        //Initialize firebase Object
        mAuth = FirebaseAuth.getInstance();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Set Up Listener
        doNotHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignUpFragment());
            }
        });
        signInEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //calling methods
                CheckInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signInPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //calling methods
                CheckInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling methods
                CheckEmailAndPassword();
            }
        });
        signInCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to Main Activity
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        forGetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set UP ForgetFragment
                setFragment(new ResetPasswordFragment());
            }
        });

    }



    /**
     * Set Up Any Fragment
     * @param fragment
     */
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_to_right,R.anim.salidout_to_left);
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();

    }

    /**
     * Check User Input Empty And Not Empty
     */
    private void CheckInputs() {
        if (!TextUtils.isEmpty(signInEmail.getText().toString().trim())){
            if (!TextUtils.isEmpty(signInPassword.getText().toString().trim())){

                //enable button and set text color
                signInBtn.setEnabled(true);
                signInBtn.setTextColor(Color.rgb(255,255,255));

            }else {
                signInBtn.setEnabled(false);
                signInBtn.setTextColor(Color.argb(50,255,255,255));
            }
        }else {
            signInBtn.setEnabled(false);
            signInBtn.setTextColor(Color.argb(50,255,255,255));
        }
    }

    /**
     * Check Email And Password Valid and Invalid
     */
    private void CheckEmailAndPassword() {
        if (signInEmail.getText().toString().trim().matches(mValidEmailPattern)){
            if (signInPassword.length() >= 8){

                signInProgressBar.setVisibility(View.VISIBLE);
                signInBtn.setEnabled(false);
                signInBtn.setTextColor(Color.argb(50,255,255,255));

                mAuth.signInWithEmailAndPassword(signInEmail.getText().toString().trim(),signInPassword.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    // Go to Main Activity
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }else {
                                    //enable button and set text color
                                    signInBtn.setEnabled(true);
                                    signInBtn.setTextColor(Color.rgb(255,255,255));
                                    signInProgressBar.setVisibility(View.INVISIBLE);
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }else {
                Toast.makeText(getActivity(),"Incorrect Email And Password!",Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(getActivity(),"Incorrect Email And Password!",Toast.LENGTH_LONG).show();
        }
    }
}
