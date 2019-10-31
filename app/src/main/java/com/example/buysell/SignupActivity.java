package com.example.buysell;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputLayout email;
    private TextInputLayout password;
    private TextInputLayout confirm_password;
    private TextInputLayout name;
    private TextInputLayout phone;
    private Button register_button;
    private Button alreadySignedIn;
    ProgressDialog progressDialog;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private android.support.constraint.ConstraintLayout constraintLayout;
    private AnimationDrawable animationDrawable;
    String registerEmail;
    String registerPassword;
    String confirmPassword;
    public String register_name;
    public static String ContactNo;
    public static UploadRegistrationDetails uploadRegistrationDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.email_register);
        password=findViewById(R.id.password_register);
        confirm_password=findViewById(R.id.confirmPassword);
        name=findViewById(R.id.fullName);
        phone=findViewById(R.id.contactNo);
        register_button=findViewById(R.id.register_button);
        alreadySignedIn=findViewById(R.id.already_signed_in);
        database=FirebaseDatabase.getInstance();
        constraintLayout = findViewById(R.id.cl1);
        animationDrawable = (AnimationDrawable)constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
        register_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        register();
                    }
                }
        );
        alreadySignedIn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(SignupActivity.this,loginActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    public void register() {
        registerEmail=email.getEditText().getText().toString();
        registerPassword=password.getEditText().getText().toString();
        confirmPassword=confirm_password.getEditText().getText().toString();
        ContactNo=phone.getEditText().getText().toString();
        register_name=name.getEditText().getText().toString();
        if (TextUtils.isEmpty(registerEmail)){
            email.setError("Email cannot be empty");
        }
        if (TextUtils.isEmpty(registerPassword)){
            password.setError("password cannot be empty");
        }
        if (TextUtils.isEmpty(confirmPassword)){
            confirm_password.setError("confirm password cannot be empty");
        }
        if (TextUtils.isEmpty(ContactNo)){
            phone.setError("This field cannot be empty");
        }
        if (TextUtils.isEmpty(register_name)){
            name.setError("This field cannot be empty");
        }
        if(!TextUtils.isEmpty(registerEmail) && !TextUtils.isEmpty(registerPassword) && !TextUtils.isEmpty(confirmPassword)
                && !TextUtils.isEmpty(ContactNo) && !TextUtils.isEmpty(register_name)){
            if(registerPassword.equals(confirmPassword)){
                progressDialog=new ProgressDialog(this);
                progressDialog.setMessage("Registering");
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(registerEmail,registerPassword).addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                FirebaseUser registeredUser = FirebaseAuth.getInstance().getCurrentUser();
                                String userUid = registeredUser.getUid();
                                uploadRegistrationDetails=new UploadRegistrationDetails(ContactNo,registerPassword,registerEmail,register_name);
                                //String CurrentTime= DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                                myRef=database.getReference("Users");
                                myRef.child(userUid).setValue(uploadRegistrationDetails);
                                Toast.makeText(SignupActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();

                                FirebaseUser user=mAuth.getCurrentUser();
                                UserProfileChangeRequest userProfileChangeRequest=new UserProfileChangeRequest.Builder()
                                        .setDisplayName(register_name).build();

                                user.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignupActivity.this, "Profile updated", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                                Intent intent=new Intent(SignupActivity.this,HomePageAcytivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                ).addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignupActivity.this,e.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                );
            }
            else {
                confirm_password.setError("Passwords do not match");
            }
        }
    }
}
