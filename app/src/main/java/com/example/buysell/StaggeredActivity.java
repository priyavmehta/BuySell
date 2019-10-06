package com.example.buysell;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StaggeredActivity extends Fragment {
    /*private RecyclerView rv;
    View view;
    private List<Product> lstProduct = new ArrayList<>();*/
    View view;

    RecyclerView mRecyclerView;
    List<Product> myFoodList;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    ProgressDialog progressDialog;
    private EditText SearchProducts;
    private ProductAdapter myAdapter;

    public StaggeredActivity() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_staggered, container, false);
        mRecyclerView=view.findViewById(R.id.recyclerview);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),1);
        mRecyclerView.setLayoutManager(gridLayoutManager);


        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Products");

        myFoodList=new ArrayList<>();

        myAdapter=new ProductAdapter(getActivity(),myFoodList);
        mRecyclerView.setAdapter(myAdapter);

        databaseReference= FirebaseDatabase.getInstance().getReference("Products");
        progressDialog.show();
        eventListener=databaseReference.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        myFoodList.clear();
                        for (DataSnapshot productSnapshot: dataSnapshot.getChildren()){
                            Product product= productSnapshot.getValue(Product.class);
                            myFoodList.add(product);
                        }
                        myAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                }
        );
        /*rv = view.findViewById(R.id.recyclerview);
        ProductAdapter productAdapter = new ProductAdapter(getContext(), lstProduct);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(productAdapter);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
