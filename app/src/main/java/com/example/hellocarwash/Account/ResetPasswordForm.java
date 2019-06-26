package com.example.hellocarwash.Account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hellocarwash.R;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordForm extends AppCompatActivity {
    private TextInputLayout emailForgetPass ;
    private Button resetPass;
    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog mProgressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_form);
        getSupportActionBar().setTitle(getString(R.string.resetPassActivityTitle));
        mFirebaseAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);
        emailForgetPass = (TextInputLayout) findViewById(R.id.forgetPassEmail);
        resetPass = (Button) findViewById(R.id.forgetPassButton);
        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }
    private void validate(){
       String theEmail = emailForgetPass.getEditText().getText().toString().trim();
       if(theEmail.isEmpty()){
           Toast.makeText(this,getString(R.string.resetPassEmptyFields),Toast.LENGTH_SHORT).show();
       }else{
            mProgressDialog.setMessage(getString(R.string.resetPassDialog));
            mProgressDialog.show();
            mFirebaseAuth.sendPasswordResetEmail(theEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    mProgressDialog.dismiss();
                    if(task.isSuccessful()){
                        Toast.makeText(ResetPasswordForm.this,getString(R.string.resetPassSendSuc),Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(ResetPasswordForm.this,SignInForm.class));
                    }else {
                        Toast.makeText(ResetPasswordForm.this,getString(R.string.resetPassSendFailed),Toast.LENGTH_LONG).show();
                    }
                }
            });
       }

    }
}
