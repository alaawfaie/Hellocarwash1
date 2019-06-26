package com.example.hellocarwash.Account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hellocarwash.MainActivity;
import com.example.hellocarwash.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInForm extends AppCompatActivity {
    private ImageView companyLogo ;
    private TextInputLayout userEmail, userPassword ;
    private Button signIn_button, pricesAndServices_button, noAccount_button, forgetPass_button ;
    private FirebaseAuth mFirebaseAuth ;
    private ProgressDialog mProgressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_form);
        getSupportActionBar().setTitle(getString(R.string.signInActivityTitle));
        mFirebaseAuth = FirebaseAuth.getInstance();
        mProgressDialog = new  ProgressDialog(this);
        setupUIView();
        // check if a user is signed in and direct him to the next activity
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if(user != null ){
            finish();
            startActivity(new Intent(SignInForm.this, MainActivity.class));
        }
        //  sign in button on click listener
        signIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(userEmail.getEditText().getText().toString(),userPassword.getEditText().getText().toString());
            }
        });
        // prices button on click listener
        pricesAndServices_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInForm.this,PricesForm.class));
                finish();
            }
        });
        //  no account button on click listener
        noAccount_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInForm.this,SignUpForm.class));
                finish();
            }
        });
        //  forget password button on click listener
        forgetPass_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInForm.this,ResetPasswordForm.class));
                finish();
            }
        });
    }
    // a method to setup all the views in organized  way
    private void setupUIView(){
        companyLogo = (ImageView) findViewById(R.id.companyLogo);
        userEmail = (TextInputLayout) findViewById(R.id.signInEmail);
        userPassword = (TextInputLayout) findViewById(R.id.signInPass);
        signIn_button = (Button) findViewById(R.id.signIn_Button);
        pricesAndServices_button = (Button) findViewById(R.id.services_Button);
        noAccount_button = (Button) findViewById(R.id.noAccount_Button);
        forgetPass_button = (Button) findViewById(R.id.forgetPass_button);
    }
    // a method to checkout that the user fill out all the required details and to sign him in
    private void  validate(String user_Email , String user_pass ){
        String email = userEmail.getEditText().getText().toString();
        String uPassword = userPassword.getEditText().getText().toString();
        if ( email.isEmpty()|| uPassword.isEmpty()){
            Toast.makeText(this,getString(R.string.signInEmptyFields),Toast.LENGTH_SHORT).show();
        }else if (email.isEmpty() ){
            Toast.makeText(this,getString(R.string.signInEmptyEmail),Toast.LENGTH_SHORT).show();
        }else if (uPassword.isEmpty()){
            Toast.makeText(this,getString(R.string.signInEmptyPass),Toast.LENGTH_SHORT).show();
        }else {
            mProgressDialog.setMessage(getString(R.string.signInDialog));
            mProgressDialog.show();
            mFirebaseAuth.signInWithEmailAndPassword(user_Email,user_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                  mProgressDialog.dismiss();
                  if(task.isSuccessful()){
                      checkEmailVer();
                  }else{
                      Toast.makeText(SignInForm.this, getString(R.string.signInError),Toast.LENGTH_SHORT).show();
                  }
                }
            });
        }

    }
    // a method to make sure that the user has verify his email
    private void checkEmailVer(){
        FirebaseUser mfirebaseUser = mFirebaseAuth.getInstance().getCurrentUser();
        boolean checkedVer = mfirebaseUser.isEmailVerified();
        if(checkedVer){
            startActivity(new Intent(SignInForm.this, MainActivity.class));
        }else{
            Toast.makeText(SignInForm.this,getString(R.string.signInVer),Toast.LENGTH_SHORT).show();
            mFirebaseAuth.signOut();
        }
    }

}
