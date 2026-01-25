package com.example.babushka.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.babushka.MainActivity;
import com.example.babushka.R;
import com.example.babushka.network.LoginResponseDto;
import com.example.babushka.network.RetrofitClient;
import com.example.babushka.network.UserDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    // Declaramos los EditText y los botones
    private EditText etUsuario;
    private EditText etClave;
    private Button btnEntrar;
    private Button btnRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Comprobar si ya hay token guardado
        String token = getSharedPreferences("session", MODE_PRIVATE)
                .getString("sessionToken", null);
        if (token != null) {
            irAInicio();
            return;
        }

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

        //Recuperamos lo que puso el usuario en los editText de username y contraseña, lo convertimos a STring (toString)
        //y eliminamos los espacios en blanco del final (trim)
        String usuario = etUsuario.getText().toString().trim();
        String clave = etClave.getText().toString().trim();

        //Si el usuario no introduce el username y/o la contraseña, salta error
        if (usuario.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Rellena usuario y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        RetrofitClient.getApi()
                .login(new UserDto(usuario, clave))
                .enqueue(new Callback<LoginResponseDto>() {
                    @Override
                    public void onResponse(Call<LoginResponseDto> call, Response<LoginResponseDto> response) {

                        if (response.isSuccessful()) {
                            saveSessionToken(response.body().sessionToken);
                            irAInicio();
                            return;
                        }

                        //En el backend de Django, asignamos el error 403 a si el usuario estaba inactivo
                        if (response.code() == 403) {
                            Toast.makeText(LoginActivity.this, "Tu cuenta está desactivada.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        // Si el error es 401 es que hubo algún problema con el usuario/contraseña, tal y como definimos en el back
                        if (response.code() == 401) {
                            Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
                            return;
                        }
                        //Cualquier otro error no catcheado
                        Toast.makeText(LoginActivity.this, "Error inesperado", Toast.LENGTH_LONG).show();
                    }

                    //Si no ha sido capaz de conectarse al servidor:
                    @Override
                    public void onFailure(Call<LoginResponseDto> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Guardar token de sesión
    private void saveSessionToken(String token) {
        getSharedPreferences("session", MODE_PRIVATE)
                .edit()
                .putString("sessionToken", token)
                .apply();
    }

    //Método para ir a la pantalla de inicio
    private void irAInicio() {
        startActivity(new Intent(this, MainActivity.class));
        finish(); // Para que no vuelva atrás al login
    }

    //Método para ir a la pantalla de registro
    private void irARegistro() {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
