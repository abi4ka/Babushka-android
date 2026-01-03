package com.example.babushka.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.babushka.R;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etClave;
    private Button btnEntrar;
    private Button btnRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Enlazamos vistas
        etUsuario = findViewById(R.id.etUsuario);
        etClave = findViewById(R.id.etClave);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);

        // Botón Entrar
        btnEntrar.setOnClickListener(v -> login());

        // Botón Registrarse
        btnRegistrarse.setOnClickListener(v -> irARegistro());
    }

    private void login() {

        String usuario = etUsuario.getText().toString().trim();
        String clave = etClave.getText().toString().trim();

        // Validación básica
        if (usuario.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Rellena usuario y clave", Toast.LENGTH_SHORT).show();
            return;
        }

        // Llamamos al API (por hacer)

        /*
        1. Enviar usuario y clave al servidor
        2. Esperar respuesta
        3. Si es correcto: ir a la siguiente pantalla
        4. Si falla: mostrar error
        */

        Toast.makeText(this, "Login correcto", Toast.LENGTH_SHORT).show();
    }

    private void irARegistro() {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}

