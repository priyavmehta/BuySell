package com.example.buysell;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {

    //private TextInputEditText email;
    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;
    private ProgressBar loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mAuth = FirebaseAuth.getInstance();

        /*if(mAuth.getCurrentUser() != null) {
            startActivity(new Intent(loginActivity.this, HomePageAcytivity.class));
            finish();
        }*/

        setContentView(R.layout.activity_login);
        Button loginButton = findViewById(R.id.login_btn);
        Button registerButton = findViewById(R.id.signup);
        email = findViewById(R.id.email_login);
        loadingbar = findViewById(R.id.progressBar_login);
        password = findViewById(R.id.password_login);
        mAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(loginActivity.this,SignupActivity.class);
                        startActivity(intent);
                    }
                }
        );
        loginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        signIn();
                    }
                }
        );
    }

    public void signIn(){
        String Email = email.getText().toString();
        String pass=password.getText().toString();
        if(TextUtils.isEmpty(Email)){
            Toast.makeText(loginActivity.this, "Email is not entered", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pass)){
            Toast.makeText(loginActivity.this, "Password is not entered", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingbar.setVisibility(View.VISIBLE);
            loginToAccount(Email, pass);
        }
    }

    private void loginToAccount(final String Email, final String pass) {
        mAuth.signInWithEmailAndPassword(Email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loadingbar.setVisibility(View.GONE);
                        if(!task.isSuccessful()) {
                            Toast.makeText(loginActivity.this, "Sign in failed..", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            startActivity(new Intent(loginActivity.this, HomePageAcytivity.class));
                            finish();
                        }
                    }
                });
    }
}
