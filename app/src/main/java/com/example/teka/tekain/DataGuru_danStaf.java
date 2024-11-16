package com.example.teka.tekain;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teka.tekain.teacher_staff.TeacherStaff;
import com.example.teka.tekain.teacher_staff.TeacherStaffAdapter;

import java.util.ArrayList;
import java.util.List;

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

        // Initialize dummy data for demonstration
        teacherStaffList = new ArrayList<>();
        teacherStaffList.add(new TeacherStaff("Nama Guru 1", "Posisi Guru 1"));
        teacherStaffList.add(new TeacherStaff("Nama Guru 2", "Posisi Guru 2"));

        // Initialize adapter and set it to the RecyclerView
        teacherAdapter = new TeacherStaffAdapter(this, teacherStaffList);
        recyclerView.setAdapter(teacherAdapter);
    }

    // Method to show the bottom sheet with teacher/staff details
    public void showDetailGuruBottomSheet(String name, String about, String profileImageUrl) {
        DetailGuruBottomSheet detailGuruBottomSheet = DetailGuruBottomSheet.newInstance(name, about, profileImageUrl);
        detailGuruBottomSheet.show(getSupportFragmentManager(), detailGuruBottomSheet.getTag());
    }
}
