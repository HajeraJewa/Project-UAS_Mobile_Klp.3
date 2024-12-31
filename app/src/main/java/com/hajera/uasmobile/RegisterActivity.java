package com.hajera.uasmobile;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    // Deklarasi komponen UI
    private EditText editNamaLengkap, editAlamatLengkap, editNomorTelepon, editEmail, editPassword, editConfirmPassword;
    private Button buttonRegister;

    // Firebase Authentication dan Firestore
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inisialisasi FirebaseAuth dan Firestore
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        // Hubungkan komponen UI
        editNamaLengkap = findViewById(R.id.editNamaLengkap);
        editAlamatLengkap = findViewById(R.id.editAlamatLengkap);
        editNomorTelepon = findViewById(R.id.editNomorTelepon);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editConfirmPassword = findViewById(R.id.editConfirmPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        // Event ketika tombol Register diklik
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    // Method untuk proses registrasi
    private void registerUser() {
        String namaLengkap = editNamaLengkap.getText().toString().trim();
        String alamatLengkap = editAlamatLengkap.getText().toString().trim();
        String nomorTelepon = editNomorTelepon.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String confirmPassword = editConfirmPassword.getText().toString().trim();

        // Validasi input
        if (TextUtils.isEmpty(namaLengkap)) {
            editNamaLengkap.setError("Nama Lengkap diperlukan");
            return;
        }

        if (TextUtils.isEmpty(alamatLengkap)) {
            editAlamatLengkap.setError("Alamat Lengkap diperlukan");
            return;
        }

        if (TextUtils.isEmpty(nomorTelepon)) {
            editNomorTelepon.setError("Nomor Telepon diperlukan");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editEmail.setError("Email diperlukan");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Format email tidak valid");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editPassword.setError("Password diperlukan");
            return;
        }

        if (password.length() < 6) {
            editPassword.setError("Password minimal 6 karakter");
            return;
        }

        if (!password.equals(confirmPassword)) {
            editConfirmPassword.setError("Password tidak cocok");
            return;
        }

        // Disable button and show loading indicator (optional)
        buttonRegister.setEnabled(false);

        // Proses registrasi ke Firebase Authentication
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Enable button back after the task is complete
                        buttonRegister.setEnabled(true);

                        if (task.isSuccessful()) {
                            // Registrasi berhasil, simpan data ke Firestore
                            String userId = firebaseAuth.getCurrentUser().getUid(); // Ambil UID pengguna yang baru terdaftar

                            // Menyusun data pengguna yang akan disimpan ke Firestore
                            Map<String, Object> user = new HashMap<>();
                            user.put("namaLengkap", namaLengkap);
                            user.put("alamatLengkap", alamatLengkap);
                            user.put("nomorTelepon", nomorTelepon);
                            user.put("email", email);

                            // Menyimpan data ke Firestore di koleksi "users" dengan document menggunakan userId
                            firebaseFirestore.collection("users").document(userId)
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Data berhasil disimpan ke Firestore
                                            Toast.makeText(RegisterActivity.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                                            finish(); // Tutup activity setelah berhasil
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Terjadi kesalahan saat menyimpan data
                                            Toast.makeText(RegisterActivity.this, "Gagal menyimpan data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            // Registrasi gagal, tampilkan error
                            Toast.makeText(RegisterActivity.this, "Registrasi Gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
