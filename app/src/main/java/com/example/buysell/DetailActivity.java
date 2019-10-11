package com.example.buysell;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends AppCompatActivity {
    TextView productDescription,productPrice,productTitle;
    ImageView productImage;
    private TextView sell;
    private TextView phone;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    private String contact;
    private String seller;
    private Button calling;
    private Button addToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        productDescription = findViewById(R.id.txtDescription);
        productImage = findViewById(R.id.ivImage);
        productPrice=findViewById(R.id.detailPrice);
        productTitle=findViewById(R.id.detailTitle);
        sell=findViewById(R.id.username);
        phone=findViewById(R.id.call);
        mAuth=FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        calling=findViewById(R.id.calling_button);
        addToCart = findViewById(R.id.add_to_cart);

        final Bundle bundle;
        bundle = getIntent().getExtras();
        final CartItem cartItem = new CartItem(bundle.getString("Title"), bundle.getString("Price"));

        if(bundle != null) {
            String name;
            String des;
            String price;
            name = "Product Name : "+bundle.getString("Title");
            des = "Product Description : "+bundle.getString("Description");
            price = "Price : "+bundle.getString("Price");


            productDescription.setText(des);
            productTitle.setText(name);
            productPrice.setText(price);
            Glide.with(this).load(bundle.getString("Image")).into(productImage);
        }


        final String a=bundle.getString("Title");

        databaseReference= FirebaseDatabase.getInstance().getReference("Products");
        valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot productSnapshot: dataSnapshot.getChildren()){
                    {
                        String name3=productSnapshot.child("productTitle").getValue(String.class);
                        if(name3.equals(a)){
                            seller=productSnapshot.child("sellerName").getValue(String.class);
                            sell.setText("Seller's Name : "+(seller).toUpperCase());
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference=FirebaseDatabase.getInstance().getReference("Users");
        valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot nameSnapshot: dataSnapshot.getChildren()) {
                    String name1 = nameSnapshot.child("name").getValue(String.class);
                    if (name1.equals(seller)) {
                        contact=nameSnapshot.child("contactNo").getValue(String.class);
                        phone.setText(contact);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        calling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCall=new Intent(Intent.ACTION_CALL);
                intentCall.setData(Uri.parse("tel:"+phone.getText().toString()));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(DetailActivity.this, "Please Grant Permissions", Toast.LENGTH_SHORT).show();
                    request();
                }
                else {
                    startActivity(intentCall);
                }
            }
        });
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData(cartItem);
            }
        });
    }
    public void got(String ba){
        Toast.makeText(this, ba, Toast.LENGTH_SHORT).show();
    }

    public void request(){
        ActivityCompat.requestPermissions(DetailActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
    }
    private void uploadData(CartItem cartItem) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userKey = currentUser.getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users").child(userKey).child("cart");
        db.child(cartItem.getTitle()).setValue(cartItem).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    Toast.makeText(DetailActivity.this, "Done", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DetailActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

