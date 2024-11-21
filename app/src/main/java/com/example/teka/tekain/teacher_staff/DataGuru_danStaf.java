package com.example.teka.tekain.teacher_staff;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;  // Add this import
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teka.tekain.API.APIInterface;
import com.example.teka.tekain.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataGuru_danStaf extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TeacherStaffAdapter teacherAdapter;
    private List<TeacherStaff> teacherStaffList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_guru_dan_staf_);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the list
        teacherStaffList = new ArrayList<>();

        // Initialize the adapter with item click listener
        teacherAdapter = new TeacherStaffAdapter(this, teacherStaffList, new TeacherStaffAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TeacherStaff teacherStaff) {
                // Handle item click and show the bottom sheet
                showDetailGuruBottomSheet(teacherStaff.getName(), teacherStaff.getPosition(), teacherStaff.getPhotoUrl());
            }

            @Override
            public void onDetailButtonClick(TeacherStaff teacherStaff) {
                // Tangani klik pada tombol Selengkapnya
                showDetailGuruBottomSheet(teacherStaff.getName(), teacherStaff.getPosition(), teacherStaff.getPhotoUrl());
            }

        });
        recyclerView.setAdapter(teacherAdapter);

        // Fetch data from API
        fetchDataFromAPI();

        // Setup Filter All Button
        Button filterAllButton = findViewById(R.id.filterAll);
        filterAllButton.setOnClickListener(v -> showAllTeachers());

        // Setup Filter Newest Button
        Button filterNewestButton = findViewById(R.id.filterNewest);
        filterNewestButton.setOnClickListener(v -> showNewestTeachers());

        // Set up SearchView for filtering
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform search action if needed
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter list based on query text
                filterTeacherStaff(newText);
                return true;
            }
        });
    }

    // Method to filter and show all teachers/staff
    private void showAllTeachers() {
        // Just refresh the list to show all teachers
        teacherAdapter.updateList(teacherStaffList);
    }

    // Method to filter and show the newest teachers/staff
    private void showNewestTeachers() {
        // Sort the list by the newest first (assuming "timestamp" or "id" for now)
        Collections.sort(teacherStaffList, new Comparator<TeacherStaff>() {
            @Override
            public int compare(TeacherStaff t1, TeacherStaff t2) {
                // Assuming TeacherStaff has a "timestamp" field
                return t2.getTimestamp().compareTo(t1.getTimestamp());
            }
        });

        // Notify the adapter of the sorted list
        teacherAdapter.updateList(teacherStaffList);
    }

    // Method to filter teacher staff based on search query
    private void filterTeacherStaff(String query) {
        List<TeacherStaff> filteredList = new ArrayList<>();

        for (TeacherStaff teacherStaff : teacherStaffList) {
            if (teacherStaff.getName().toLowerCase().contains(query.toLowerCase()) ||
                    teacherStaff.getPosition().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(teacherStaff);
            }
        }

        // Update the adapter with the filtered list
        teacherAdapter.updateList(filteredList);
    }

    // Method to show the bottom sheet with teacher/staff details
    public void showDetailGuruBottomSheet(String name, String about, String profileImageUrl) {
        DetailGuruBottomSheet detailGuruBottomSheet = DetailGuruBottomSheet.newInstance(name, about, profileImageUrl);
        detailGuruBottomSheet.show(getSupportFragmentManager(), detailGuruBottomSheet.getTag());
    }

    private void fetchDataFromAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.5/Teka.in/get_teachers.php") // Replace with your actual base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIInterface api = retrofit.create(APIInterface.class);

        api.getTeachersStaff().enqueue(new Callback<List<TeacherStaff>>() {
            @Override
            public void onResponse(Call<List<TeacherStaff>> call, Response<List<TeacherStaff>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Clear the current list
                    teacherStaffList.clear();

                    // Add the data from API
                    teacherStaffList.addAll(response.body());

                    // Notify the adapter of data changes
                    teacherAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<TeacherStaff>> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
            }
        });
    }
}