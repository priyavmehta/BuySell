package com.example.buysell;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText confirmpassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirm_password);
        mAuth=FirebaseAuth.getInstance();
        mAuthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Toast.makeText(SignupActivity.this, " Enter email and password ", Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthlistener);
    }

    public void adduser(){
        String e = email.getText().toString();
        String pass = password.getText().toString();
        String confirmPass = confirmpassword.getText().toString();
        if(TextUtils.isEmpty(e) || TextUtils.isEmpty(pass)){
            Toast.makeText(SignupActivity.this, "Email or Password is not entered", Toast.LENGTH_SHORT).show();
        }
        else if(!pass.equals(confirmPass)){
            Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(e, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignupActivity.this, "User logged in", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignupActivity.this, HomePageAcytivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(SignupActivity.this, "Sign in problem", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        }
    }

    public void registerNow(View view) {
        adduser();
    }
}
