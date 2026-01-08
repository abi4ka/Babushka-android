package com.example.babushka;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.babushka.network.RetrofitClient;
import com.example.babushka.network.UserDto;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        if (usuario.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Rellena usuario y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        RetrofitClient.getApi()
                .login(new UserDto(usuario, clave))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            // Login correcto
                            // Opcional: gestionar tokens de sesión
                            irAInicio();
                        } else {
                            Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void irAInicio() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Para que no vuelva atrás al login
    }

    private void irARegistro() {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}

