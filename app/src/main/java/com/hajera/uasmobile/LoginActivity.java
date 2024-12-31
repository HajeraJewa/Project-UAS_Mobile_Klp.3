package com.hajera.uasmobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView registerRedirect;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Menginisialisasi komponen UI
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerRedirect = findViewById(R.id.button_register);

        // Inisialisasi FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Set aksi untuk tombol login
        loginButton.setOnClickListener(v -> loginUser());

        // Set aksi untuk redirect ke halaman register
        registerRedirect.setOnClickListener(v -> {
            // Pindah ke RegisterActivity saat tombol Create a new Account ditekan
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validasi email
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Masukkan email yang valid");
            emailEditText.requestFocus();
            return;
        }

        // Validasi password
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            passwordEditText.setError("Password minimal 6 karakter");
            passwordEditText.requestFocus();
            return;
        }

        // Melakukan login menggunakan FirebaseAuth
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                        // Pindah ke MainActivity jika login berhasil
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
