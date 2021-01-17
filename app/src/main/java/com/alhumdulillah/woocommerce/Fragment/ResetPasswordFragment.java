package com.alhumdulillah.woocommerce.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.TransitionManager;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alhumdulillah.woocommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordFragment extends Fragment {
    //Final Var
    private static final String TAG = "ResetPasswordFragment";

    //Firebase Var
    private FirebaseAuth mAuth;


    //private Weigets Var
    private EditText forgetEmail;
    private Button forgetBtn;
    private TextView forgetGoBack;
    private FrameLayout mainFrameLayout;
    private ProgressBar forgetProgressBar;
    private ImageView emailIcon;
    private TextView emailIconText;
    private ViewGroup emailIconContainer;


    //Another Var
    private String mValidEmailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";




    public ResetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started.");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R
                .layout.fragment_reset_password, container, false);


        //define----------------------------------------------------------------------
        mainFrameLayout = getActivity().findViewById(R.id.registerFrameLayoutId);
        forgetEmail = view.findViewById(R.id.forgetEmailVerificationId);
        forgetBtn = view.findViewById(R.id.forgetPasswordButtonId);
        forgetGoBack = view.findViewById(R.id.forgetGoBackId);
        forgetProgressBar = view.findViewById(R.id.forgetEmailPasswordProgressBarId);
        emailIconContainer = view.findViewById(R.id.forgotPasswordContainerId);
        emailIcon = view.findViewById(R.id.forgotEmailIconId);
        emailIconText = view.findViewById(R.id.forgetEmailTextId);



        //Initialize firebase Object
        mAuth = FirebaseAuth.getInstance();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: started.");

        //Set Up Listener
        forgetEmail.addTextChangedListener(new TextWatcher() {
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
        //button click
        forgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CheckValidEmailInput();
                TransitionManager.beginDelayedTransition(emailIconContainer);
                forgetProgressBar.setVisibility(View.GONE);


                TransitionManager.beginDelayedTransition(emailIconContainer);
                emailIcon.setVisibility(View.VISIBLE);
                forgetProgressBar.setVisibility(View.VISIBLE);

                forgetBtn.setEnabled(false);
                forgetBtn.setTextColor(Color.argb(50,255,255,255));

                mAuth.sendPasswordResetEmail(forgetEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    ScaleAnimation scaleAnimation = new ScaleAnimation(1,0,1,0,emailIcon.getWidth()/2,emailIcon.getHeight()/2);
                                    scaleAnimation.setDuration(100);
                                    scaleAnimation.setInterpolator(new AccelerateInterpolator());
                                    scaleAnimation.setRepeatMode(Animation.REVERSE);
                                    scaleAnimation.setRepeatCount(1);

                                    scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            emailIconText.setText("Recovery email sent successfully ! check your inbox");
                                            emailIconText.setTextColor(getResources().getColor(R.color.succesGreen));

                                            TransitionManager.beginDelayedTransition(emailIconContainer);
                                            emailIconText.setVisibility(View.VISIBLE);

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {
                                            emailIcon.setImageResource(R.drawable.ic_green_mail);
                                        }
                                    });
                                    emailIconText.startAnimation(scaleAnimation);
                                }else {
                                    String error=task.getException().getMessage();

                                    forgetBtn.setEnabled(true);
                                    forgetBtn.setTextColor(Color.rgb(255,255,255));

                                    emailIconText.setText(error);
                                    emailIconText.setTextColor(getResources().getColor(R.color.purple_500));
                                    TransitionManager.beginDelayedTransition(emailIconContainer);
                                    emailIconText.setVisibility(View.VISIBLE);
                                }
                                forgetProgressBar.setVisibility(View.GONE);

                            }
                        });
            }
        });

        forgetGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set Up SignIn Fragment
                setFragment(new SignInFragment());
            }
        });

    }


    /**
     * Check Email Empty And Not Empty
     */
    private void CheckInputs() {
        if (TextUtils.isEmpty(forgetEmail.getText().toString().trim())){
            forgetBtn.setEnabled(false);
            forgetBtn.setTextColor(Color.argb(50,255,255,255));
        }else {
            forgetBtn.setEnabled(true);
            forgetBtn.setTextColor(Color.rgb(255,255,255));
        }

    }



    /**
     * Check Email And Password Valid and Invalid
     */

    private void CheckValidEmailInput() {
        if (forgetEmail.getText().toString().trim().matches(mValidEmailPattern)){
            Toast.makeText(getActivity(),"Wow matches valid pattern",Toast.LENGTH_LONG).show();

            TransitionManager.beginDelayedTransition(emailIconContainer);
            emailIconText.setVisibility(View.GONE);

            //Then, ProgressBar Visible And ForgetBtn InVisible
            TransitionManager.beginDelayedTransition(emailIconContainer);
            emailIcon.setVisibility(View.VISIBLE);
            emailIconText.setVisibility(View.VISIBLE);
            forgetProgressBar.setVisibility(View.VISIBLE);
            forgetBtn.setEnabled(false);
            forgetBtn.setTextColor(Color.argb(50,255,255,255));

            mAuth.sendPasswordResetEmail(forgetEmail.getText().toString().trim())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){

                            }else {
                                String error = task.getException().getMessage();

                                forgetBtn.setEnabled(true);
                                forgetBtn.setTextColor(Color.argb(50,255,255,255));

                                emailIconText.setText(error);
                                emailIconText.setTextColor(getResources().getColor(R.color.btnRed));
                                TransitionManager.beginDelayedTransition(emailIconContainer);
                                emailIconText.setVisibility(View.VISIBLE);
                            }

                            forgetProgressBar.setVisibility(View.GONE);

                        }
                    });

        }else {
            Toast.makeText(getActivity(),"InValid Email ID!",Toast.LENGTH_LONG).show();
        }
    }



    /**
     * Set Up Any Fragment
     * @param fragment
     */
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_to_right,R.anim.salidout_to_left);
        fragmentTransaction.replace(mainFrameLayout.getId(),fragment);
        fragmentTransaction.commit();

    }
}
