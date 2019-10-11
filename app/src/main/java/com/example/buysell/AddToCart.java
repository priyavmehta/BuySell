package com.example.buysell;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

public class AddToCart extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button nextProcessBtn;
    private TextView totalPrice;
    List<CartItem> myCartList;
    private CartAdapter cartAdapter;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userKey = currentUser.getUid();

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myCartList = new ArrayList<>();
        cartAdapter = new CartAdapter(this, myCartList);
        recyclerView.setAdapter(cartAdapter);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Cart items");


        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid()).child("cart");
        progressDialog.show();
        CartItem mcart = new CartItem("Rishi", "Kaul");
        databaseReference.child("Scooter").setValue(mcart);
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myCartList.clear();
                for (DataSnapshot cartSnapshot : dataSnapshot.getChildren()) {
                    CartItem cartItem = cartSnapshot.getValue(CartItem.class);
                    Toast.makeText(AddToCart.this, cartItem.getTitle(), Toast.LENGTH_SHORT).show();
                    myCartList.add(cartItem);
                }
                cartAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
        nextProcessBtn = findViewById(R.id.next_process_button);
        totalPrice = findViewById(R.id.total_price);
    }
}

