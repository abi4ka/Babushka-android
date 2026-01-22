package com.example.babushka.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.babushka.R;
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

        //Recuperamos lo que puso el usuario en los editText de username y contraseña, lo convertimos a STring (toString)
        //y eliminamos los espacios en blanco del final (trim)
        String usuario = etUsuario.getText().toString().trim();
        String clave = etClave.getText().toString().trim();

        //Si el usuario no rellena el campo de username y/o contraseña, va a mostrar un mensaje de error.
        if (usuario.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        //Controlamos que la contraseña no sea demasiado larga ni demasiado corta
        if (clave.length() < 6 || clave.length() > 20){
            Toast.makeText(this, "La contraseña tiene que estar entre 6 y 20 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }
        // Llamamos al singleton de Retrofit que está definido en la clase RetrofitClient
        RetrofitClient.getApi()
                //Llamamos al metodo registrar y le pasamos el username y la contraseña que escribió nuestro usuario en el front
                .register(new UserDto(usuario, clave))
                //Enqueue hace que la petición sea asíncrona, es decir, se ejecuta en segundo plano sin bloquear nada
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    //Respuesta del servidor:
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //Si el código de respuesta el 201, es que el usuario se ha creado sin problemas y volvemos a la pantalla
                        //de login para poder entrar con el usuario recién creado
                        if (response.code() == 201) {
                            Toast.makeText(RegisterActivity.this, "Registro completado", Toast.LENGTH_SHORT).show();
                            volverALogin();
                            //Si hay algún error, se vacían los campos de username y contraseña y se manda un mensaje de error
                        } else {
                            etUsuario.setText("");
                            etClave.setText("");
                            Toast.makeText(RegisterActivity.this, "Error: usuario ya existente o inválido", Toast.LENGTH_SHORT).show();
                        }
                    }
                    //Si ocurre algún error de conexión, se ejecuta onFailure. Ponemos en blanco los campos de username y contraseña y mandamos
                    //un mensaje de error
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
