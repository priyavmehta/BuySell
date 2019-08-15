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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText confirmpassword;
    private FirebaseAuth mAuth;
    private Button registerbutton;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    FirebaseDatabase database;
    DatabaseReference myref;
    String name;
    String e;
    String pass;
    String confirmPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        database=FirebaseDatabase.getInstance();
        myref=database.getReference();//.child("Welcome to BUYSELL");

        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password);
        registerbutton=findViewById(R.id.register);
        confirmpassword = findViewById(R.id.confirm_password);

        //myref=database.getReference().child("Welcome to BUYSELL");
        mAuth=FirebaseAuth.getInstance();
        mAuthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Toast.makeText(SignupActivity.this, " Enter email and password ", Toast.LENGTH_LONG).show();
                }
            }
        };
        registerbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adduser();
                    }
                }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthlistener);
    }

    public void adduser(){
        e = email.getText().toString();
        pass = password.getText().toString();
        confirmPass = confirmpassword.getText().toString();
        if(TextUtils.isEmpty(e)){

            Toast.makeText(SignupActivity.this, "Email is  not entered", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pass)){
            Toast.makeText(SignupActivity.this, "Password is not entered", Toast.LENGTH_SHORT).show();
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
                                myref=database.getReference();
                                myref.child("Priyav");
                                myref.child("Priyav").child("Email").setValue(e);
                                myref.child("Priyav").child("Password").setValue(pass);
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

    //public void registerNow(View view) {
        //adduser();
    //}
}
