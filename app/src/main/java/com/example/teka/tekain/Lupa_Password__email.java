package com.example.teka.tekain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Lupa_Password__email extends AppCompatActivity {

    private EditText emailEditText;
    private View sendOtpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lupa_password__email_);

        emailEditText = findViewById(R.id.masukan_email);
        sendOtpButton = findViewById(R.id.Button_Send);

        sendOtpButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            // Kirim permintaan ke server untuk mengirim OTP
            sendOtpToEmail(email);
        });
    }

    private void sendOtpToEmail(String email) {
        // Implementasikan logika untuk mengirim OTP ke email
        // Setelah berhasil, alihkan ke VerifyOtpActivity
        Intent intent = new Intent(Lupa_Password__email.this, Lupa_Password__kode.class);
        startActivity(intent);
        finish();
    }
}
