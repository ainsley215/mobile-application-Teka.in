package com.example.teka.tekain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class Lupa_Password__kode extends AppCompatActivity {

    private EditText otpEditText;
    private View verifyOtpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.lupa_password__kode_);

        otpEditText = findViewById(R.id.masukan_kode);
        verifyOtpButton = findViewById(R.id.Button_Send);

        verifyOtpButton.setOnClickListener(v -> {
            String otp = otpEditText.getText().toString();
            // Kirim permintaan ke server untuk memverifikasi OTP
            verifyOtp(otp);
        });
    }

    private void verifyOtp(String otp) {
        // Implementasikan logika untuk memverifikasi OTP
        // Jika valid, alihkan ke ResetPasswordActivity
        Intent intent = new Intent(Lupa_Password__kode.this, Lupa_Password__sandi__baru.class);
        startActivity(intent);
        finish();
    }
}