package com.hajera.uasmobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Menggunakan Handler untuk menunggu 2 detik sebelum berpindah ke LoginActivity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();  // Menutup MainActivity agar tidak kembali ke halaman splash
        }, 2000); // Menunggu 2 detik (2000 ms)
    }
}
