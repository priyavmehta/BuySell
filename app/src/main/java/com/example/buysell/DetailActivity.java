package com.example.buysell;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    TextView productDescription;
    ImageView productImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        productDescription = findViewById(R.id.txtDescription);
        productImage = findViewById(R.id.ivImage);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            productDescription.setText(bundle.getString("Description"));
            productImage.setImageResource(bundle.getInt("Image"));
        }
    }
}
