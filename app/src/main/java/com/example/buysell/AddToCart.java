package com.example.buysell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddToCart extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button nextProcessBtn;
    private TextView totalPrice;
    List<Product> myProductList;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myProductList = new ArrayList<>();
        cartAdapter = new CartAdapter(this, myProductList);
        recyclerView.setAdapter(cartAdapter);

        Intent intent = getIntent();
        /*Bundle bundle = intent.getExtras();
        if(bundle != null) {
            String title = bundle.getString("Title");
            String price = bundle.getString("Price");
            Product product = new Product();
            product.setProductTitle(title);
            product.setProductPrice(price);
            myProductList.add(product);
            cartAdapter.notifyDataSetChanged();
        }*/

        nextProcessBtn = findViewById(R.id.next_process_button);
        totalPrice = findViewById(R.id.total_price);
    }

}
