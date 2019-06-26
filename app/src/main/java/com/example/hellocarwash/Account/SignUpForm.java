package com.example.hellocarwash.Account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hellocarwash.MainActivity;
import com.example.hellocarwash.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpForm extends AppCompatActivity {
    private TextInputLayout userFullName, userEmail, phoneNumber, password, confirmPassword;
    private Button registryButton, haveAccountButton;
    private FirebaseAuth mFirebaseAuth ;
    private ProgressDialog mProgressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_form);
        getSupportActionBar().setTitle(getString(R.string.signUpActivityTitle));
        mFirebaseAuth = FirebaseAuth.getInstance();
        mProgressDialog = new  ProgressDialog(this);
        setupUIViews();
        registryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    // upload data to the database
                    String user_email = userEmail.getEditText().getText().toString().trim();
                    String user_password = password.getEditText().getText().toString().trim();
                    mProgressDialog.setMessage(getString(R.string.signUpDialog));
                    mProgressDialog.show();
                    mFirebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mProgressDialog.dismiss();
                            if(task.isSuccessful()){
                                sendEmailVer();
                           }else{
                              Toast.makeText(SignUpForm.this,getString(R.string.signUpFailed),Toast.LENGTH_SHORT).show();
                           }
                        }
                    });

                    
                }
            }
        });
        haveAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SignUpForm.this, SignInForm.class));
            }
        });
    }
    // a method to setup all the views in organized  way
    private void setupUIViews () {
        userFullName = (TextInputLayout)findViewById(R.id.ti_FullName);
        userEmail = (TextInputLayout)findViewById(R.id.ti_Email);
        phoneNumber = (TextInputLayout)findViewById(R.id.ti_PhoneNumber);
        password = (TextInputLayout)findViewById(R.id.ti_Password);
        confirmPassword = (TextInputLayout)findViewById(R.id.ti_ConfirmPassword);
        registryButton = (Button) findViewById(R.id.registry_Button);
        haveAccountButton = (Button) findViewById(R.id.haveAccount_Button);
    }
    // a method to checkout that the user fill out all the required details
    private boolean validate(){
        boolean checking = false ;
        String fullName = userFullName.getEditText().getText().toString();
        String email = userEmail.getEditText().getText().toString();
        String phone = phoneNumber.getEditText().getText().toString();
        String uPassword = password.getEditText().getText().toString();
        String conPassword = confirmPassword.getEditText().getText().toString();
        if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || uPassword.isEmpty() || conPassword.isEmpty()){
            Toast.makeText(this,getString(R.string.signUpMissedReq),Toast.LENGTH_SHORT).show();
        }else if(phone.length()<10){
            Toast.makeText(this,getString(R.string.signUpPhoneLen),Toast.LENGTH_SHORT).show();
        }else if(uPassword.length()<8){
            Toast.makeText(this,getString(R.string.signUpPasswordLen) , Toast.LENGTH_SHORT).show();
        }else if(!uPassword.equals(conPassword)){
            Toast.makeText(this,getString(R.string.signUpConPassword),Toast.LENGTH_SHORT).show();
        }else {
            checking = true;
        }
        return checking ;
    }
    // a method to send the verification email to the user to make sure that he use his own email
    private void sendEmailVer(){
        FirebaseUser mfirebaseUser = mFirebaseAuth.getCurrentUser();
        if(mfirebaseUser!=null){
            mfirebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignUpForm.this,getString(R.string.signUpSuccessfully),Toast.LENGTH_SHORT).show();
                        mFirebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(SignUpForm.this,SignInForm.class));
                    }else{
                        Toast.makeText(SignUpForm.this,getString(R.string.signUpFailed),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
