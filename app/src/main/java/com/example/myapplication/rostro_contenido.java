package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class rostro_contenido extends AppCompatActivity {
    ImageView backpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rostro_contenido);

        backpage = findViewById(R.id.backpage);

        backpage.setOnClickListener(v -> {
            Intent intent = new Intent(rostro_contenido.this, principal.class);
            startActivity(intent);
        });
    }
}