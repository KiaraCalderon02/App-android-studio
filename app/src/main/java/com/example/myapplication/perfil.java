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

public class perfil extends AppCompatActivity {

    ImageView barinicio, barblog;
    TextView barnombre, barcorreo, barnumtelf, barpassword, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.perfil);

        barblog = findViewById(R.id.barblog);

        barblog.setOnClickListener(v -> {
            Intent intent = new Intent(perfil.this, blog.class);
            startActivity(intent);
        });

        barinicio = findViewById(R.id.barinicio);

        barinicio.setOnClickListener(v -> {
            Intent intent = new Intent(perfil.this, principal.class);
            startActivity(intent);
        });

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(v -> {
            cerrarSesion();
        });

        barnombre = findViewById(R.id.barnombre);
        barpassword = findViewById(R.id.barpassword);
        barcorreo = findViewById(R.id.barcorreo);
        barnumtelf = findViewById(R.id.barnumtelf);

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
                        String email = usuario.getString("email");
                        String numtelf = usuario.getString("numtelf");
                        String password= usuario.getString("password");

                        // Usar los datos
                        barnombre.setText(nombre);
                        barcorreo.setText(email);
                        barnumtelf.setText(numtelf);
                        barpassword.setText(password);

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

    private void cerrarSesion() {
        SharedPreferences prefs = getSharedPreferences("SESSION", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.clear();     // Borra todos los datos de sesión
        editor.apply();

        // Redirige al login
        Intent intent = new Intent(this, inicio_sesion.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        finish(); // Cierra esta activity para que no pueda volver con el botón atrás
    }
}