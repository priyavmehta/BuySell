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
    private Button registerbutton;
    private TextInputEditText fullname;
    private TextInputEditText username;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText confirmpassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setTitle("Signup form");
        mAuth=FirebaseAuth.getInstance();

        registerbutton=(Button)findViewById(R.id.register);
        registerbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adduser();
                        Intent intent =new Intent(SignupActivity.this,HomePageAcytivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
    public void adduser(){
        String e=email.getText().toString();
        String pass=password.getText().toString();
        if(TextUtils.isEmpty(e) || TextUtils.isEmpty(pass)){
            Toast.makeText(SignupActivity.this, "Either email or password is not entered", Toast.LENGTH_SHORT).show();
        }
        else {


            mAuth.createUserWithEmailAndPassword(e, pass).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignupActivity.this, "User logged in ", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(SignupActivity.this,HomePageAcytivity.class);
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
}
