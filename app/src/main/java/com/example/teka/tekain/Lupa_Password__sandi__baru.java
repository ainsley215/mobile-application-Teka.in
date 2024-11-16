package com.example.teka.tekain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Lupa_Password__sandi__baru extends AppCompatActivity {

    private EditText newPasswordEditText, confirmPasswordEditText;
    private View resetPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lupa_password_sandi_baru_);

        newPasswordEditText = findViewById(R.id.sandi_baru_txt);
        confirmPasswordEditText = findViewById(R.id.kofir_sandi_txt);
        resetPasswordButton = findViewById(R.id.Button_Send);

        resetPasswordButton.setOnClickListener(v -> {
            String newPassword = newPasswordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();
            if (newPassword.equals(confirmPassword)) {
                // Kirim permintaan ke server untuk mengganti password
                resetPassword(newPassword);
            } else {
                Toast.makeText(this, "Password tidak cocok", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetPassword(String newPassword) {
        // Implementasikan logika untuk mengganti password
        // Setelah berhasil, alihkan ke halaman login
        Intent intent = new Intent(Lupa_Password__sandi__baru.this, Login.class);
        startActivity(intent);
        finish();
    }
}