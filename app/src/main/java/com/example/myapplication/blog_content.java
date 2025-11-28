package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class blog_content extends AppCompatActivity {

    ImageView imgBlog, backpage;
    TextView txtTitulo, txtAutor, txtContenido, txtFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.blog_content);

        // Vinculamos el TextView del XML
        backpage = findViewById(R.id.backpage);

        backpage.setOnClickListener(v -> {
            Intent intent = new Intent(blog_content.this, blog.class);
            startActivity(intent);
        });

        imgBlog = findViewById(R.id.content_img);
        txtTitulo = findViewById(R.id.titulo);
        txtAutor = findViewById(R.id.autor);
        txtContenido = findViewById(R.id.content);

        Intent intent = getIntent();

        String titulo = intent.getStringExtra("titulo");
        String imagenName = intent.getStringExtra("imagen_name");
        int autorId = intent.getIntExtra("autor_id", 0);
        String contenido = intent.getStringExtra("contenido");

        txtTitulo.setText(titulo);
        txtContenido.setText(contenido);
        txtAutor.setText("Autor #" + autorId);

        int imgRes = getResources().getIdentifier(imagenName, "mipmap", getPackageName());
        imgBlog.setImageResource(imgRes);
    }
}