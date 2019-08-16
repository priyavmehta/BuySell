package com.example.buysell;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    private TextInputEditText name;
    private TextInputEditText phone;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText confirmpassword;
    private ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name = findViewById(R.id.name_register);
        phone = findViewById(R.id.phone_register);
        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);
        confirmpassword = findViewById(R.id.confirm_password_register);
    }

    public void addUser(){
        String Name = name.getText().toString();
        String Phone = phone.getText().toString();
        String e = email.getText().toString();
        String pass = password.getText().toString();
        String confirmPass = confirmpassword.getText().toString();
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
            validatePhone(Name, Phone, e, pass);
        }
    }

    private void validatePhone(final String Name, final String Phone, final String e, final String pass) {

        final DatabaseReference myref;
        myref = FirebaseDatabase.getInstance().getReference();

        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(Phone).exists())) {
                    HashMap<String, Object> userData = new HashMap<>();
                    userData.put("Name", Name);
                    userData.put("Phone", Phone);
                    userData.put("Email", e);
                    userData.put("Password", pass);
                    myref.child("Users").child(Phone).updateChildren(userData)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(SignupActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                                        Intent in = new Intent(SignupActivity.this, HomePageAcytivity.class);
                                        startActivity(in);
                                    }
                                    else {
                                        Toast.makeText(SignupActivity.this, "Please try again..", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(SignupActivity.this, "Phone number already exists", Toast.LENGTH_SHORT).show();
                    Toast.makeText(SignupActivity.this, "Try using different phone number", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, loginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void registerNow(View view) {
        addUser();
    }
}
