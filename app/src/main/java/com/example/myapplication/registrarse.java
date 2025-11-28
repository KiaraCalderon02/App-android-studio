package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class registrarse extends AppCompatActivity {
    TextView registrar, iniciarsesion;
    EditText barnombre, baremail, barpassword, barnumtelf;
    ImageView backpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrarse);

        iniciarsesion = findViewById(R.id.iniciarsesion);

        iniciarsesion.setOnClickListener(v -> {
            Intent intent = new Intent(registrarse.this, inicio_sesion.class);
            startActivity(intent);
        });

        backpage = findViewById(R.id.backpage);

        backpage.setOnClickListener(v -> {
            Intent intent = new Intent(registrarse.this, inicio_sesion.class);
            startActivity(intent);
        });

        barnombre = findViewById(R.id.barnombre);
        baremail = findViewById(R.id.baremail);
        barpassword = findViewById(R.id.barpassword);
        barnumtelf = findViewById(R.id.barnumtelf);
        registrar = findViewById(R.id.registrar);

        registrar.setOnClickListener(v -> registrarUsuario());
    }

    private void registrarUsuario() {

        registrar.setEnabled(false);

        String nombre = barnombre.getText().toString().trim();
        String email = baremail.getText().toString().trim();
        String password = barpassword.getText().toString().trim();
        String numtelf = barnumtelf.getText().toString().trim();

        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || numtelf.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            registrar.setEnabled(true);
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Correo inválido", Toast.LENGTH_SHORT).show();
            registrar.setEnabled(true);
            return;
        }

        if (!numtelf.matches("\\d{9}")) {
            Toast.makeText(this, "El número debe tener 9 dígitos", Toast.LENGTH_SHORT).show();
            registrar.setEnabled(true);
            return;
        }

        String url = "http://10.0.2.2:3000/api/registrar";

        JSONObject datos = new JSONObject();
        try {
            datos.put("nombre_usuario", nombre);
            datos.put("email", email);
            datos.put("password", password);
            datos.put("numtelf", numtelf);
        } catch (Exception e) {
            e.printStackTrace();
            registrar.setEnabled(true);
        }

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                datos,
                response -> {
                    registrar.setEnabled(true);

                    try {
                        boolean success = response.getBoolean("success");

                        if (success) {

                            int userId = response.getInt("user_id");

                            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                            SharedPreferences prefs = getSharedPreferences("SESSION", MODE_PRIVATE);
                            prefs.edit().putInt("user_id", userId).apply();

                            Intent intent = new Intent(registrarse.this, inicio_sesion.class);
                            startActivity(intent);
                            finish();

                        } else {
                            String mensaje = response.optString("mensaje", "Error desconocido");
                            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error procesando respuesta", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    registrar.setEnabled(true);
                    Toast.makeText(this, "Error en la conexión", Toast.LENGTH_SHORT).show();
                    Log.e("API_REGISTER", error.toString());
                }
        );

        queue.add(request);
    }
}