package com.example.teka.tekain.daftar;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teka.tekain.API.APIInterface;
import com.example.teka.tekain.R;
import com.example.teka.tekain.Retrofit.RetrofitClient;
import com.example.teka.tekain.dashboard.Dashboard;
import com.example.teka.tekain.masuk.Login;
import com.example.teka.tekain.siswa.Data_Siswa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Daftar__wali__murid extends AppCompatActivity {
    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar__wali__murid_);

        // UI Components
        EditText nameEditText = findViewById(R.id.nama_siswa);
        EditText nisEditText = findViewById(R.id.nis);
        EditText emailEditText = findViewById(R.id.email);
        EditText passwordEditText = findViewById(R.id.password);
        EditText confirmPasswordEditText = findViewById(R.id.ulangi_password_anda);
        TextView masuk = findViewById(R.id.masuk_text);

        //tombol
        masuk.setOnClickListener(v -> {
            Intent intent = new Intent(Daftar__wali__murid.this, Login.class);
            startActivity(intent);
            finish();
        });

        // Daftar button handling
        findViewById(R.id._rectangle_349).setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String nis = nisEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            // Check if password matches
            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Password tidak cocok", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate password
            List<String> validationErrors = validatePassword(password);
            if (!validationErrors.isEmpty()) {
                for (String error : validationErrors) {
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // Proceed with user registration
            registerUser(name, nis, email, password);
        });

        // Menu login navigation
        TextView menuDaftar = findViewById(R.id.masuk_text); // Pastikan ID benar di layout XML
        menuDaftar.setOnClickListener(v -> {
            // Navigate to login page
        });

        // Password visibility toggle
        setupPasswordVisibilityToggle(passwordEditText, true);
        setupPasswordVisibilityToggle(confirmPasswordEditText, false);
    }

    private List<String> validatePassword(String password) {
        List<String> errors = new ArrayList<>();

        // Aturan validasi
        if (password.length() < 8) {
            errors.add("Password harus memiliki setidaknya 8 karakter.");
        }
        if (!password.matches(".*[A-Z].*")) {
            errors.add("Password harus mengandung setidaknya satu huruf besar.");
        }
        if (!password.matches(".*[a-z].*")) {
            errors.add("Password harus mengandung setidaknya satu huruf kecil.");
        }
        if (!password.matches(".*[0-9].*")) {
            errors.add("Password harus mengandung setidaknya satu angka.");
        }
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            errors.add("Password harus mengandung setidaknya satu karakter khusus.");
        }

        return errors;
    }

    private void registerUser(String name, String nis, String email, String password) {
        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);

        Map<String, String> params = new HashMap<>();
        params.put("nama_siswa", name);
        //params.put("nis", nis);
        params.put("nis", String.valueOf(Integer.parseInt(nis)));
        params.put("email", email);
        params.put("password", password);

        Call<Void> call = apiInterface.registerUser(params);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Daftar__wali__murid.this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show();
                    // Redirect ke halaman lain login
                    Intent intent = new Intent(Daftar__wali__murid.this, Login.class); // LOGIN diganti DataGuru_danStaf
                    startActivity(intent);
                } else {
                    Toast.makeText(Daftar__wali__murid.this, "Registrasi gagal: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Daftar__wali__murid.this, "Kesalahan jaringan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupPasswordVisibilityToggle(EditText passwordEditText, boolean isPrimaryPassword) {
        passwordEditText.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    togglePasswordVisibility(passwordEditText, isPrimaryPassword);
                    return true;
                }
            }
            return false;
        });
    }

    private void togglePasswordVisibility(EditText passwordEditText, boolean isPrimaryPassword) {
        if (isPrimaryPassword) {
            if (isPasswordVisible) {
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hide, 0);
                isPasswordVisible = false;
            } else {
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye, 0);
                isPasswordVisible = true;
            }
        } else {
            if (isConfirmPasswordVisible) {
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hide, 0);
                isConfirmPasswordVisible = false;
            } else {
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye, 0);
                isConfirmPasswordVisible = true;
            }
        }
        // Move cursor to the end
        passwordEditText.setSelection(passwordEditText.length());
    }
}