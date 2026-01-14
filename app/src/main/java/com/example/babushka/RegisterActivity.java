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

        if (usuario.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (clave.length() < 6 || clave.length() > 20){
            Toast.makeText(this, "La contraseña tiene que estar entre 6 y 20 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        RetrofitClient.getApi()
                .register(new UserDto(usuario, clave))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 201) {
                            Toast.makeText(RegisterActivity.this, "Registro completado", Toast.LENGTH_SHORT).show();
                            volverALogin();
                        } else {
                            etUsuario.setText("");
                            etClave.setText("");
                            Toast.makeText(RegisterActivity.this, "Error: usuario ya existente o inválido", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        etUsuario.setText("");
                        etClave.setText("");
                        Toast.makeText(RegisterActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void volverALogin() { //Usamos esta función para volver al Login desde una Activity (Register)
        startActivity(new Intent(this, LoginActivity.class));
        finish(); // cerramos esta pantalla
    }
}
