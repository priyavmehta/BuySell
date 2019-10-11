package com.example.buysell;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    private List<CartItem> products;
    private LayoutInflater inflater;
    private Context context;

    public CartAdapter(Context context, List<CartItem> products) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.products = products;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = inflater.inflate(R.layout.cart_items_layout, viewGroup, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i) {
        cartViewHolder.productName.setText(products.get(i).getTitle());
        //Toast.makeText(context, products.get(i).getTitle(), Toast.LENGTH_LONG).show();
        cartViewHolder.productPrice.setText(products.get(i).getPrice());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}

class CartViewHolder extends RecyclerView.ViewHolder{

    TextView productName;
    TextView productPrice;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        productName = itemView.findViewById(R.id.cart_product_name);
        productPrice = itemView.findViewById(R.id.cart_product_price);
    }
}

