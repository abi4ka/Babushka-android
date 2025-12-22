package com.example.babushka;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etClave;
    private Button btnRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // tu XML

        // Conectamos con botones del xml
        etUsuario = findViewById(R.id.etUsuario);
        etClave = findViewById(R.id.etClave);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);

        // Acción del botón
        btnRegistrarse.setOnClickListener(v -> registrarUsuario());
    }

    private void registrarUsuario() {

        String usuario = etUsuario.getText().toString().trim();
        String clave = etClave.getText().toString().trim();

        // Validaciones
        if (usuario.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Aquí llamaremos al API


        /*
        1. Enviar usuario y clave al servidor
        2. Esperar respuesta
        3. Si es correcto → volver al login
        4. Si hay error → mostrar mensaje
        */

        // Simulación de registro correcto
        Toast.makeText(this, "Registro completado", Toast.LENGTH_SHORT).show();

        volverALogin();
    }

    private void volverALogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish(); // cerramos esta pantalla
    }
}
