package com.hajera.uasmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AccountFragment extends Fragment {

    private ImageView profileImageView;
    private TextView userNameTextView;
    private TextView userHandleTextView;
    private TextView userDescriptionTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate layout untuk fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        // Inisialisasi view dari layout
        profileImageView = view.findViewById(R.id.profileImageView);
        userNameTextView = view.findViewById(R.id.userNameTextView);
        userHandleTextView = view.findViewById(R.id.userHandleTextView);
        userDescriptionTextView = view.findViewById(R.id.userDescriptionTextView);

        // Data yang ada pada fragment_account.xml
        // Misalnya menggunakan drawable dan teks yang sudah ada pada layout

        // Menampilkan gambar profil
        profileImageView.setImageResource(R.drawable.mobile); // Ganti dengan ID gambar sesuai kebutuhan

        // Menampilkan teks pada TextViews
        userNameTextView.setText("Hajera Jewa");
        userHandleTextView.setText("hajerajewa4757");
        userDescriptionTextView.setText("Selengkapnya tentang anda... selengkapnya");

        return view;
    }
}
