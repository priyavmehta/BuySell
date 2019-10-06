package com.example.buysell;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class SellActivity extends Fragment {
    View view;
    public SellActivity() {
    }

    private EditText name;
    private EditText des;
    private EditText Price;
    private EditText seller_name;
    private Button selectIm;
    private Button upload;
    private ImageView dataImage;
    private static final int PICK_IMAGE = 100;
    Uri uri;
    private StorageReference mStorageRef;
    private String ImageUrl;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    UploadRegistrationDetails uploadRegistrationDetails;
    private ValueEventListener valueEventListener;
    private DatabaseReference databaseReference;
    private String contact;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_sell, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadProducts();
            }
        });

        name = (EditText) view.findViewById(R.id.upload_name);
        des = (EditText) view.findViewById(R.id.upload_Description);
        Price = (EditText) view.findViewById(R.id.upload_price);
        //seller_name=(EditText) view.findViewById(R.id.upload_seller_name);
        //selectIm = (Button) view.findViewById(R.id.selectImage);
        //upload = (Button) view.findViewById(R.id.uploadProduct);
        dataImage = (ImageView) view.findViewById(R.id.uploadImage);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        uploadRegistrationDetails = new UploadRegistrationDetails();
        firebaseUser = mAuth.getCurrentUser();

        dataImage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog();
                    }
                }
        );
        return view;
    }

    public void openDialog(){
        AlertDialog.Builder imageType=new AlertDialog.Builder(getContext());
        imageType.setMessage("Choose Image Type");
        imageType.setPositiveButton("Take Image", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TakeImage();
            }
        })
                 .setNeutralButton("Choose Image", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         ChooseImage();
                     }
                 });
                imageType.show();

    }

    public void TakeImage(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PICK_IMAGE);

    }

    public void ChooseImage(){
        Intent photoPicker=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(photoPicker,PICK_IMAGE);

    }


    public void UploadProducts() {
        uploadImage();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            uri = data.getData();
            dataImage.setImageURI(uri);
        } else Toast.makeText(getActivity(), "Image not selected", Toast.LENGTH_LONG).show();
    }

    public void uploadImage() {
        mStorageRef = FirebaseStorage.getInstance().getReference().child("buySellImage").child(uri.getLastPathSegment());
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Data uploading...");
        progressDialog.show();
        mStorageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri urlImage = uriTask.getResult();
                ImageUrl = urlImage.toString();
                uploadData();
                progressDialog.dismiss();
            }
        }).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                }
        );
    }

    public void uploadData() {
        firebaseUser = mAuth.getCurrentUser();
        String name1 = firebaseUser.getDisplayName();
        Product product = new Product(name.getText().toString(), des.getText().toString(), Price.getText().toString(), ImageUrl, name1);
        String CurrentTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        myRef = database.getReference("Products");
        myRef.child(CurrentTime).setValue(product).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            getContactNo();
                        }
                    }
                }
        ).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                    }
                }
        );
    }

    private void getContactNo() {
        firebaseUser = mAuth.getCurrentUser();
        final String name1 = firebaseUser.getDisplayName();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot nameSnapshot : dataSnapshot.getChildren()) {
                    String name = nameSnapshot.child("name").getValue(String.class);
                    if (name.equals(name1)) {
                        contact = nameSnapshot.child("contactNo").getValue(String.class);

                        hello(contact);
                        // break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void hello(String Contact) {

        Messaging mess = new Messaging(getActivity());
        String message_upload = "Thank you for uploading product at BUYSELL.";
        mess.message(Contact, message_upload);
    }

}
/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/