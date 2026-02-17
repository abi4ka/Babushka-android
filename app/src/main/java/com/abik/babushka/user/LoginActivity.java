package com.abik.babushka.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.abik.babushka.MainActivity;
import com.abik.babushka.R;
import com.abik.babushka.network.dto.LoginResponseDto;
import com.abik.babushka.network.RetrofitClient;
import com.abik.babushka.network.dto.UserDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity for user login.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if a session token is already saved
        String token = getSharedPreferences("session", MODE_PRIVATE)
                .getString("sessionToken", null);
        if (token != null) {
            goToMain();
            return;
        }

        setContentView(R.layout.login_activity);

        // Bind views
        usernameInput = findViewById(R.id.etUsuario);
        passwordInput = findViewById(R.id.etClave);
        loginButton = findViewById(R.id.btnEntrar);
        registerButton = findViewById(R.id.btnRegistrarse);

        // Login button click
        loginButton.setOnClickListener(v -> login());

        // Register button click
        registerButton.setOnClickListener(v -> goToRegister());
    }

    /**
     * Handle login action.
     */
    private void login() {
        String username = this.usernameInput.getText().toString().trim();
        String password = this.passwordInput.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Send login request
        RetrofitClient.getApi()
                .login(new UserDto(username, password))
                .enqueue(new Callback<LoginResponseDto>() {
                    @Override
                    public void onResponse(Call<LoginResponseDto> call, Response<LoginResponseDto> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            saveSessionToken(response.body().sessionToken);
                            goToMain();
                            return;
                        }

                        if (response.code() == 403) {
                            Toast.makeText(LoginActivity.this, "Your account is deactivated.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (response.code() == 401) {
                            Toast.makeText(LoginActivity.this, "Incorrect username or password", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Toast.makeText(LoginActivity.this, "Unexpected error", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<LoginResponseDto> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Connection error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Save session token in SharedPreferences.
     */
    private void saveSessionToken(String token) {
        getSharedPreferences("session", MODE_PRIVATE)
                .edit()
                .putString("sessionToken", token)
                .apply();
    }

    /**
     * Navigate to main activity.
     */
    private void goToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish(); // Prevent returning to login
    }

    /**
     * Navigate to register activity.
     */
    private void goToRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
