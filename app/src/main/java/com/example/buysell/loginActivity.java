package com.example.buysell;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {

    private TextInputEditText email;
    private TextInputEditText password;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginButton = findViewById(R.id.login_btn);
        Button registerButton = findViewById(R.id.register);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        mAuth=FirebaseAuth.getInstance();
        mAuthlistener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    Toast.makeText(loginActivity.this," Enter email and password ",Toast.LENGTH_LONG).show();
                }
            }
        };

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
                        signin();
                    }
                }
        );
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthlistener);
    }
    public void signin(){
        String e=email.getText().toString();
        String pass=password.getText().toString();
        if(TextUtils.isEmpty(e)){
            Toast.makeText(loginActivity.this, "Email is not entered", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pass)){
            Toast.makeText(loginActivity.this, "Password is not entered", Toast.LENGTH_SHORT).show();
        }
        else {


            mAuth.signInWithEmailAndPassword(e, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(loginActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(loginActivity.this,HomePageAcytivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(loginActivity.this, "Sign in problem", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        }
    }
}
