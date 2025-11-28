package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class principal extends AppCompatActivity {

    ImageView rostro, barblog, barperfil;
    TextView input, user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        rostro = findViewById(R.id.rostro);

        rostro.setOnClickListener(v -> {
            Intent intent = new Intent(principal.this, rostro_contenido.class);
            startActivity(intent);
        });

        barblog = findViewById(R.id.barblog);

        barblog.setOnClickListener(v -> {
            Intent intent = new Intent(principal.this, blog.class);
            startActivity(intent);
        });

        barperfil = findViewById(R.id.barperfil);

        barperfil.setOnClickListener(v -> {
            Intent intent = new Intent(principal.this, perfil.class);
            startActivity(intent);
        });

        input = findViewById(R.id.input);

        input.setOnClickListener(v -> {
            Intent intent = new Intent(principal.this, buscar.class);
            startActivity(intent);
        });

        user = findViewById(R.id.user);
        cargarDatosUsuario();
    }

    private void cargarDatosUsuario() {

        SharedPreferences prefs = getSharedPreferences("SESSION", MODE_PRIVATE);
        int userId = prefs.getInt("user_id", -1);

        if (userId == -1) {
            Toast.makeText(this, "Sesión no encontrada", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://10.0.2.2:3000/api/usuarios/" + userId;

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");

                        if (!success) {
                            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        JSONObject usuario = response.getJSONObject("usuario");

                        String nombre = usuario.getString("nombre_usuario");

                        // Usar los datos
                        user.setText(nombre);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.e("API_USER", "Error: " + error.toString());
                }
        );

        queue.add(request);
    }
}