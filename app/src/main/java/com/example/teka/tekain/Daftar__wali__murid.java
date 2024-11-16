package com.example.teka.tekain;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Daftar__wali__murid extends AppCompatActivity {
    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;
    private GoogleSignInClient mGoogleSignInClient; // Tambahkan GoogleSignInClient

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar__wali__murid_);

        // Inisialisasi Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail() // Minta email pengguna
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso); // Buat GoogleSignInClient

        // UI Components
        EditText nameEditText = findViewById(R.id.masukan_nama);
        EditText addressEditText = findViewById(R.id.masukan_alamat);
        EditText emailEditText = findViewById(R.id.masukan_alamat_email);
        EditText passwordEditText = findViewById(R.id.masukan_password);
        EditText confirmPasswordEditText = findViewById(R.id.ulangi_password_anda);

        // Daftar button handling
        findViewById(R.id._rectangle_349).setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String address = addressEditText.getText().toString();
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
            registerUser (name, address, email, password);
        });

        // Tambahkan tombol untuk login dengan Google
        findViewById(R.id.group_google).setOnClickListener(v -> signInWithGoogle());

        TextView MenuDaftar = findViewById(R.id.masuk_text); // Pastikan ID benar di layout XML
        MenuDaftar.setOnClickListener(v -> {
            // Navigate to login page
        });

        // Password visibility toggle
        setupPasswordVisibilityToggle(passwordEditText, true);
        setupPasswordVisibilityToggle(confirmPasswordEditText, false);
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent(); // Dapatkan intent untuk sign-in
        startActivityForResult(signInIntent, 101); // Mulai activity untuk sign-in
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Hasil dari Google Sign-In
        if (requestCode == 101) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task); // Tangani hasil sign-in
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Login berhasil, dapatkan informasi pengguna
            String name = account.getDisplayName();
            String email = account.getEmail();
            // Kirim data ke backend untuk pendaftaran atau login
            registerUser (name, "", email,  ""); // Anda bisa mengatur password sesuai kebutuhan atau menggunakan token

        } catch (ApiException e) {
            // Login gagal
            Toast.makeText(this, "Google Sign-In failed: " + e.getStatusCode(), Toast.LENGTH_SHORT).show();
        }
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

        return errors; // Kembalikan daftar kesalahan
    }

    private void registerUser (String name, String address, String email, String password) {
        // Send data to PHP script via HTTP POST request
        new Thread(() -> {
            try {
                String urlString = "http://localhost/Teka.in/register.php"; // Replace with your PHP URL
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.getOutputStream().write(
                        ("name=" + name + "&address=" + address + "&email=" + email + "&password=" + password).getBytes()
                );

                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // Handle the response here
                    runOnUiThread(() -> {
                        Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show();
                        // Redirect to Login or other activity
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
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