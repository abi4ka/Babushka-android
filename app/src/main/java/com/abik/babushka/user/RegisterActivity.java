package com.abik.babushka.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.abik.babushka.R;
import com.abik.babushka.network.RetrofitClient;
import com.abik.babushka.network.dto.UserDto;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity for user registration.
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        // Bind views
        etUsername = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etClave);
        btnRegister = findViewById(R.id.btnRegistrarse);

        // Register button click
        btnRegister.setOnClickListener(v -> registerUser());
    }

    /**
     * Handle user registration.
     */
    private void registerUser() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validate fields
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6 || password.length() > 20) {
            Toast.makeText(this, "Password must be between 6 and 20 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        // Send registration request
        RetrofitClient.getApi()
                .register(new UserDto(username, password))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 201) {
                            // Registration successful
                            Toast.makeText(RegisterActivity.this, "Registration completed", Toast.LENGTH_SHORT).show();
                            goToLogin();
                        } else {
                            // Error: user already exists or invalid input
                            etUsername.setText("");
                            etPassword.setText("");
                            Toast.makeText(RegisterActivity.this, "Error: username already exists or invalid", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        etUsername.setText("");
                        etPassword.setText("");
                        Toast.makeText(RegisterActivity.this, "Connection error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Navigate back to login activity.
     */
    private void goToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish(); // Close this activity
    }
}
