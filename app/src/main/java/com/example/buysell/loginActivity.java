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


import com.example.buysell.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity {

    //private TextInputEditText email;
    private EditText phone;
    private EditText password;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginButton = findViewById(R.id.login_btn);
        Button registerButton = findViewById(R.id.register);
        phone = findViewById(R.id.phone_login);
        password = findViewById(R.id.password_login);
        /*mAuth=FirebaseAuth.getInstance();
        mAuthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    Toast.makeText(loginActivity.this," Enter email and password ",Toast.LENGTH_LONG).show();
                }
            }
        };*/

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
        String Phone = phone.getText().toString();
        String pass=password.getText().toString();
        if(TextUtils.isEmpty(Phone)){
            Toast.makeText(loginActivity.this, "Email is not entered", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pass)){
            Toast.makeText(loginActivity.this, "Password is not entered", Toast.LENGTH_SHORT).show();
        }
        else {
            loginToAccount(Phone, pass);
        }
    }

    private void loginToAccount(final String Phone, final String pass) {
        final DatabaseReference myref;
        myref = FirebaseDatabase.getInstance().getReference();

        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(Phone).exists()){
                    Users user = dataSnapshot.child("Users").child(Phone).getValue(Users.class);
                    if (user.getPhone().equals(Phone)) {
                        if(user.getPassword().equals(pass)) {
                            Toast.makeText(loginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(loginActivity.this, HomePageAcytivity.class);
                            startActivity(intent);
                        }
                    }
                    else {
                        Toast.makeText(loginActivity.this, "Sign in problem...", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(loginActivity.this, "Account does not exist..", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
