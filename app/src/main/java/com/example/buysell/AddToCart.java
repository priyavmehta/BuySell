package com.example.buysell;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddToCart extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button nextProcessBtn;
    private TextView totalPrice;
    List<CartItem> myCartList;
    List<CartItem> databseCartList;
    private CartAdapter cartAdapter;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private DatabaseReference db;
    private ValueEventListener eventListener;
    private String TAG = "AddToCart";
    private ProductAdapter myAdapter;
    private int sum = 0;


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
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myCartList.clear();
                for (DataSnapshot cartSnapshot : dataSnapshot.getChildren()) {
                    CartItem cartItem = cartSnapshot.getValue(CartItem.class);
                    Toast.makeText(AddToCart.this, cartItem.getTitle(), Toast.LENGTH_SHORT).show();
                    myCartList.add(cartItem);
                }

                for (int i = 0 ; i < myCartList.size(); i++) {
                    sum += Integer.parseInt(myCartList.get(i).getProductPrice());
                }
                totalPrice.setText("Total Price: Rs. "+sum);
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

        nextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < myCartList.size(); i++) {
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Products").child(myCartList.get(i).getId());
                    db.removeValue();
                }
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid()).child("cart");
                db.removeValue();
                finish();
            }
        });
    }
}

