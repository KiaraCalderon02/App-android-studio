package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

import com.example.myapplication.adapters.blogAdapter;   // ← cambia el paquete según tu proyecto
import com.example.myapplication.model.blogItem;     // ← cambia el paquete según tu proyecto

public class blog extends AppCompatActivity {

    RecyclerView recyclerBlog;
    blogAdapter adapter;
    ImageView barperfil, barinicio, backpage;
    ArrayList<blogItem> listaBlogs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog);

        barperfil = findViewById(R.id.barperfil);

        barperfil.setOnClickListener(v -> {
            Intent intent = new Intent(blog.this, perfil.class);
            startActivity(intent);
        });

        barinicio = findViewById(R.id.barinicio);

        barinicio.setOnClickListener(v -> {
            Intent intent = new Intent(blog.this, principal.class);
            startActivity(intent);
        });

        backpage = findViewById(R.id.backpage);

        backpage.setOnClickListener(v -> {
            Intent intent = new Intent(blog.this, principal.class);
            startActivity(intent);
        });


        recyclerBlog = findViewById(R.id.recyclerBlog);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(blog.this, 2);
        recyclerBlog.setLayoutManager(gridLayoutManager);

        adapter = new blogAdapter(blog.this, listaBlogs);
        recyclerBlog.setAdapter(adapter);

        cargarBlogsDesdeAPI();
    }

    private void cargarBlogsDesdeAPI() {
        String url = "http://10.0.2.2:3000/api/blogs";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        listaBlogs.clear();

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);

                            blogItem item = new blogItem(
                                    obj.getInt("id"),
                                    obj.getString("titulo"),
                                    obj.getString("imagen_name"),
                                    obj.getInt("autor_id"),
                                    obj.getString("fecha_publicacion"),
                                    obj.getString("contenido")
                            );

                            listaBlogs.add(item);
                        }

                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.e("API_ERROR", "Error en la llamada a la API: " + error.toString());
                }
        );

        queue.add(request);
    }
}