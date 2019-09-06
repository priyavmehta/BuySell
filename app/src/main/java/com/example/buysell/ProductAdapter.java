package com.example.buysell;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private List<Product> products;
    private LayoutInflater inflater;
    private Context context;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.e("Adapter", "onCreateViewHolder: " );
        View itemView = inflater.inflate(R.layout.staggeredlayout,viewGroup,false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder productViewHolder, int i) {
        Log.e("Adapter", "onBindViewHolder: " );
        productViewHolder.productName.setText(products.get(i).getProductTitle());
        productViewHolder.productPicture.setImageResource(products.get(i).getImage());

        productViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Image", products.get(productViewHolder.getAdapterPosition()).getImage());
                intent.putExtra("Description", products.get(productViewHolder.getAdapterPosition()).getProductDescription());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
class ProductViewHolder extends RecyclerView.ViewHolder {
    TextView productPrice;
    TextView productName;
    ImageView productPicture;
    CardView cardView;
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        productName = itemView.findViewById(R.id.productName);
        productPrice = itemView.findViewById(R.id.productPrice);
        productPicture = itemView.findViewById(R.id.productImage);
        cardView = itemView.findViewById(R.id.cardview);
    }
}
