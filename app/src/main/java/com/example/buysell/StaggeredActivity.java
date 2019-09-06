package com.example.buysell;

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

import java.util.ArrayList;
import java.util.List;

public class StaggeredActivity extends Fragment {
    private RecyclerView rv;
    View view;
    private List<Product> lstProduct = new ArrayList<>();

    public StaggeredActivity() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_staggered, container, false);
        rv = view.findViewById(R.id.recyclerview);
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
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstProduct = new ArrayList<>();
        lstProduct.add(new Product("Rishi", "100", "Book", "Hello man", " ", R.drawable.background_1));
        lstProduct.add(new Product("Rishi", "100", "Book", "What is that", " ", R.drawable.background_2));
        lstProduct.add(new Product("Rishi", "100", "Book", "What!!", " ", R.drawable.background_1));
        lstProduct.add(new Product("Rishi", "100", "Book", "Why?!", " ", R.drawable.background_2));
        lstProduct.add(new Product("Rishi", "100", "Book", "But but...", " ", R.drawable.background_1));
        lstProduct.add(new Product("Rishi", "100", "Book", "Huh..", " ", R.drawable.background_1));
        lstProduct.add(new Product("Rishi", "100", "Book", "Okay", " ", R.drawable.background_1));
        lstProduct.add(new Product("Rishi", "100", "Book", "Hmmm", " ", R.drawable.background_2));
        lstProduct.add(new Product("Rishi", "100", "Book", "Ya", " ", R.drawable.background_2));
        lstProduct.add(new Product("Rishi", "100", "Book", "Nice", " ", R.drawable.background_1));
    }
}
