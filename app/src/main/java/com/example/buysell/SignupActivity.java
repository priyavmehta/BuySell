package com.example.buysell;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    private TextInputEditText name;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText confirmpassword;
    private ProgressBar loadingBar;
    private FirebaseAuth auth;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name = findViewById(R.id.name_register);
        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);
        loadingBar = findViewById(R.id.progressBar);
        confirmpassword = findViewById(R.id.confirm_password_register);
        signUp = findViewById(R.id.signup);
        auth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
    }

    public void addUser(){
        String Name = name.getText().toString();
        String e = email.getText().toString();
        String pass = password.getText().toString();
        String confirmPass = confirmpassword.getText().toString();
        if(TextUtils.isEmpty(e)){
            Toast.makeText(SignupActivity.this, "Enter email address!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pass)){
            Toast.makeText(SignupActivity.this, "Enter password!", Toast.LENGTH_SHORT).show();
        }
        else if(!pass.equals(confirmPass)){
            Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setVisibility(View.VISIBLE);
            createUser(Name, e, pass);
        }
    }

    private void createUser(final String Name, final String e, final String pass) {
        auth.createUserWithEmailAndPassword(e, pass)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        loadingBar.setVisibility(View.GONE);
                        if(!task.isSuccessful())
                            Toast.makeText(SignupActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                        else {
                            startActivity(new Intent(SignupActivity.this, loginActivity.class));
                            finish();
                        }
                    }
                });

    }
}
