package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class inicio_sesion extends AppCompatActivity {

    TextView iniciarsesion;
    TextView registrar;
    EditText txtcorreo, txtcontraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.inicio_sesion);

        iniciarsesion = findViewById(R.id.iniciarsesion);
        iniciarsesion.setOnClickListener(v -> hacerLogin());

        registrar = findViewById(R.id.registrar);

        registrar.setOnClickListener(v -> {
            Intent intent = new Intent(inicio_sesion.this, registrarse.class);
            startActivity(intent);
        });
    }

    private void hacerLogin() {
        txtcorreo = findViewById(R.id.txtcorreo);
        txtcontraseña = findViewById(R.id.txtcontraseña);

        String email = txtcorreo.getText().toString().trim();
        String password = txtcontraseña.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://10.0.2.2:3000/api/login";  // tu endpoint real

        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject datos = new JSONObject();
        try {
            datos.put("email", email);
            datos.put("password", password);
        } catch (Exception e) {}

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                datos,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");

                        if (success) {

                            JSONObject usuario = response.getJSONObject("usuario");
                            int userId = usuario.getInt("id");

                            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                            // Guardar sesión (opcional)
                            SharedPreferences prefs = getSharedPreferences("SESSION", MODE_PRIVATE);
                            prefs.edit().putInt("user_id", userId).apply();

                            // Redirigir al home o dashboard
                            Intent intent = new Intent(inicio_sesion.this, principal.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(this, "Error en la conexión", Toast.LENGTH_SHORT).show();
                    Log.e("API_LOGIN", error.toString());
                }
        );

        queue.add(request);
    }

}