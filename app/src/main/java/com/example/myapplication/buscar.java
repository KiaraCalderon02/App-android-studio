package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class buscar extends AppCompatActivity {

    ImageView backpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar);

        backpage = findViewById(R.id.backpage);

        backpage.setOnClickListener(v -> {
            Intent intent = new Intent(buscar.this, principal.class);
            startActivity(intent);
        });
    }
}