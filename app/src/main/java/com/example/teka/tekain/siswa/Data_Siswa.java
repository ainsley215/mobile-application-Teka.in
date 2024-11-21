package com.example.teka.tekain.siswa;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teka.tekain.R;
import com.example.teka.tekain.API.APIInterface;
import com.example.teka.tekain.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Data_Siswa extends AppCompatActivity {

    private static final String TAG = "Data_Siswa"; // Add TAG for logging

    private RecyclerView recyclerView;
    private SiswaAdapter siswaAdapter;
    private List<Siswa> siswaList;
    private SearchView searchView;
    private Spinner spinnerGender, spinnerClass;
    private Button filterAll, filterNewest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data__siswa_);

        APIInterface apiService = RetrofitClient.getRetrofitInstance().create(APIInterface.class);

        // Initialize components
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerClass = findViewById(R.id.spinnerClass);
        filterAll = findViewById(R.id.filterAll);
        filterNewest = findViewById(R.id.filterNewest);
        ImageView btnBack = findViewById(R.id.buttonDashboard);

        // Initialize siswaList
        siswaList = new ArrayList<>();
        fetchSiswaData(apiService);

        // Set up RecyclerView
        siswaAdapter = new SiswaAdapter(siswaList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(siswaAdapter);

        // Back button functionality
        btnBack.setOnClickListener(v -> finish());

        // Search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                siswaAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                siswaAdapter.getFilter().filter(newText);
                return false;
            }
        });

        // Spinner Gender Filter
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedGender = parent.getItemAtPosition(position).toString();
                if (!selectedGender.equals("All")) {
                    filterByGender(selectedGender);
                } else {
                    siswaAdapter.updateList(siswaList); // Show all if "All" is selected
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Spinner Class Filter
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedClass = parent.getItemAtPosition(position).toString();
                if (!selectedClass.equals("All")) {
                    filterByClass(selectedClass);
                } else {
                    siswaAdapter.updateList(siswaList); // Show all if "All" is selected
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Filter All Button
        filterAll.setOnClickListener(v -> siswaAdapter.updateList(siswaList)); // Show all students

        // Filter Newest Button
        filterNewest.setOnClickListener(v -> {
            List<Siswa> sortedList = new ArrayList<>(siswaList);
            Collections.sort(sortedList, (s1, s2) -> s2.getTimestamp().compareTo(s1.getTimestamp()));
            siswaAdapter.updateList(sortedList);
        });

    }

    private void filterByGender(String gender) {
        List<Siswa> filteredList = new ArrayList<>();
        for (Siswa siswa : siswaList) {
            if (siswa.getGender().equalsIgnoreCase(gender)) {
                filteredList.add(siswa);
            }
        }
        siswaAdapter.updateList(filteredList);
    }

    private void filterByClass(String className) {
        List<Siswa> filteredList = new ArrayList<>();
        for (Siswa siswa : siswaList) {
            if (siswa.getStudentClass().equalsIgnoreCase(className)) {
                filteredList.add(siswa);
            }
        }
        siswaAdapter.updateList(filteredList);
    }



    private void fetchSiswaData(APIInterface apiService) {
        Call<List<Siswa>> call = apiService.getSiswa(); // Fetch list of students
        call.enqueue(new Callback<List<Siswa>>() {
            @Override
            public void onResponse(@NonNull Call<List<Siswa>> call, @NonNull Response<List<Siswa>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    siswaList.clear();
                    siswaList.addAll(response.body());
                    siswaAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "Response not successful or body is null");
                    Toast.makeText(Data_Siswa.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Siswa>> call, @NonNull Throwable t) {
                Log.e(TAG, "Error fetching data: " + t.getMessage());
                Toast.makeText(Data_Siswa.this, "Error fetching data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}