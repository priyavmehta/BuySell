package com.example.buysell;

import android.content.Intent;
import android.support.annotation.NonNull;
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

public class SecondActivity extends AppCompatActivity {
    private EditText email1;
    private EditText paasword1;
    private Button login;
    private Button adduser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        email1=(EditText)findViewById(R.id.email_login);
        paasword1=(EditText)findViewById(R.id.paasword);
        login=(Button)findViewById(R.id.login_button);
        adduser=(Button)findViewById(R.id.add_user);
        mAuth=FirebaseAuth.getInstance();
        mAuthlistener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    Toast.makeText(SecondActivity.this," Enter email and paasword ",Toast.LENGTH_LONG).show();
                }
            }
        };


        login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        signin();
                    }
                }
        );
        adduser.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add();
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
        String e=email1.getText().toString();
        String pass=paasword1.getText().toString();
        if(TextUtils.isEmpty(e) || TextUtils.isEmpty(pass)){
            Toast.makeText(SecondActivity.this, "Either email or password is not entered", Toast.LENGTH_SHORT).show();
        }
        else {


            mAuth.signInWithEmailAndPassword(e, pass).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                              //  Toast.makeText(SecondActivity.this, "Sign in is successful", Toast.LENGTH_SHORT).show();
                             //   Intent intent=new Intent(SecondActivity.this,ThirdActivity.class);
                               // startActivity(intent);
                            }
                            else{
                                Toast.makeText(SecondActivity.this, "Sign in problem", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        }
    }
    public void add(){
        String e=email1.getText().toString();
        String pass=paasword1.getText().toString();
        if(TextUtils.isEmpty(e) || TextUtils.isEmpty(pass)){
            Toast.makeText(SecondActivity.this, "Either email or password is not entered", Toast.LENGTH_SHORT).show();
        }
        else {


            mAuth.createUserWithEmailAndPassword(e, pass).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SecondActivity.this, "User logged in ", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(SecondActivity.this,SecondActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(SecondActivity.this, "Sign in problem", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        }
    }
}
