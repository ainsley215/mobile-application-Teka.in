package com.example.teka.tekain;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.teka.tekain.dashboard.Dashboard;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private boolean isPasswordVisible = false;
    private static final int RC_SIGN_IN = 123;
    private View googleSignInButton;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private View loginButton;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_);  // Ensure the layout file name is correct

        // Initialize GoogleSignInOptions and GoogleSignInClient
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);  // Initialize GoogleSignInClient

        // Find the views
        usernameEditText  = findViewById(R.id.masukan_AlamatEmail);  // Ensure the ID is correct
        passwordEditText = findViewById(R.id.masukkan_password); // Ensure the ID is correct
        googleSignInButton = findViewById(R.id.group_google); // Ensure the ID is correct

        // Password visibility toggle
        passwordEditText.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    togglePasswordVisibility(passwordEditText);
                }
            }
            return false;
        });

        // Forgot password link
        TextView forgotPasswordButton = findViewById(R.id.lupa_password); // Ensure the ID is correct
        forgotPasswordButton.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Lupa_Password__email.class);
            startActivity(intent);
        });

        // Register link
        TextView MenuDaftar = findViewById(R.id.daftar_text); // Ensure the ID is correct
        MenuDaftar.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Daftar__wali__murid.class);
            startActivity(intent);
        });

        // Google Sign-In button
        googleSignInButton.setOnClickListener(view -> signIn());

        // Login button (Email and Password login)
        loginButton = findViewById(R.id.LoginButton); // Ensure this ID exists in your layout
        loginButton.setOnClickListener(v -> {
            String username  = usernameEditText .getText().toString();
            String password = passwordEditText.getText().toString();
            if (username .isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(username , password);
            }
        });
    }

    private void togglePasswordVisibility(EditText passwordEditText) {
        if (isPasswordVisible) {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hide, 0);
            isPasswordVisible = false;
        } else {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye, 0);
            isPasswordVisible = true;
        }
        passwordEditText.setSelection(passwordEditText.length());
    }

    // Method for Google Sign-In
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Successfully signed in
            String name = account.getDisplayName();
            String email = account.getEmail();
            String id = account.getId();

            // You can save this information to your backend (MySQL) here
            Log.d("Login", "User signed in: " + name + ", " + email);
        } catch (ApiException e) {
            Log.w("Login", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to handle traditional email/password login
    private void loginUser (String username, String password) {
        String url = "http://localhost/Teka.in/login.php";  // Ganti dengan URL backend Anda

        // Menggunakan Volley untuk mengirim permintaan POST untuk login
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if ("success".equals(response)) {
                            // Jika login berhasil, pindah ke layar berikutnya
                            Intent intent = new Intent(Login.this, Dashboard.class);
                            startActivity(intent);
                        } else {
                            // Tampilkan pesan kesalahan jika login gagal
                            Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Kirim username dan password sebagai parameter POST
                Map<String, String> params = new HashMap<>();
                params.put("username", username); // Kirim username
                params.put("password", password);  // Kirim password
                return params;
            }
        };

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}