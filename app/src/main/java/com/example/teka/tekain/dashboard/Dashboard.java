package com.example.teka.tekain.dashboard;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teka.tekain.API.APIInterface;
import com.example.teka.tekain.siswa.Data_Siswa;
import com.example.teka.tekain.nilai.Hasil_Belajar;
import com.example.teka.tekain.Login;
import com.example.teka.tekain.Pembayaran;
import com.example.teka.tekain.Pengumuman;
import com.example.teka.tekain.Profile;
import com.example.teka.tekain.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dashboard extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<Notification> notificationList = new ArrayList<>();
    private BottomNavigationView bottomNavView;

    // Other variables (notification icon, profile image, etc.)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_);

        // Inisialisasi BottomNavigationView
         bottomNavView = findViewById(R.id.bottomNavView);

        View daftarsiswa = findViewById(R.id.Data_siswa);
        View daftarguru = findViewById(R.id.GuruStaf);
        View hasilbelajar = findViewById(R.id.HasilBelajar);
        View pembayaran = findViewById(R.id.Pembayaran);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotificationAdapter(this, notificationList);
        recyclerView.setAdapter(adapter);

        // Call the API to fetch notifications
        fetchNotifications();

        //tombol
        daftarsiswa.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, Data_Siswa.class);
            startActivity(intent);
            finish();
        });

        daftarguru.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, Login.class); // LOGIN diganti DataGuru_danStaf
            startActivity(intent);
            finish();
        });

        hasilbelajar.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, Hasil_Belajar.class);
            startActivity(intent);
            finish();
        });

        pembayaran.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, Pembayaran.class);
            startActivity(intent);
            finish();
        });

        // BottomNavigationView setup
        bottomNavView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                return true; // Tetap di dashboard
            } else if (id == R.id.nav_pengumuman) {
                startActivity(new Intent(Dashboard.this, Pengumuman.class));
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(Dashboard.this, Profile.class));
                return true;
            }
            return false;
        });
        // Panggil fungsi untuk mendapatkan notifikasi dari API
        if (isConnected()) {
            fetchNotifications();
        } else {
            Toast.makeText(this, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchNotifications() {
        // Set up Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.5/Teka.in/fetch_data.php") // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create the APIInterface
        APIInterface apiInterface = retrofit.create(APIInterface.class);

        // Make the API call
        apiInterface.getNotifications().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Add the notifications to the list and notify the adapter
                    notificationList.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(Dashboard.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Mengatur visibilitas jika terjadi kegagalan
                if (bottomNavView != null) { // Periksa null untuk mencegah crash
                    bottomNavView.setVisibility(View.GONE);
                }
                Log.e("API_ERROR", t.getMessage());
                Toast.makeText(Dashboard.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}