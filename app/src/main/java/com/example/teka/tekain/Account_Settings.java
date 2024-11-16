package com.example.teka.tekain;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



public class Account_Settings extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private ImageView profileImageView;
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText addressEditText;
    private EditText nisnEditText; // Tambahkan EditText untuk NISN
    private Button saveButton;
    private Button changeProfilePictureButton;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings_);
    }}