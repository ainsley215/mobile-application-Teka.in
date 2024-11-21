package com.example.teka.tekain.API;

import com.example.teka.tekain.dashboard.ApiResponse;
import com.example.teka.tekain.siswa.Siswa;
import com.example.teka.tekain.teacher_staff.TeacherStaff;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface APIInterface {
    @GET("get_teachers.php")
    Call<List<TeacherStaff>> getTeachersStaff();

    @GET("get_siswa.php")
    Call<List<Siswa>> getSiswa(); // Added method name for fetching students

    @GET("notifications/fetch_data.php")
    Call<ApiResponse> getNotifications();

    @GET("getStudentScores.php")
    Call<List<StudentScore>> getStudentScores(@Query("nisn") String nisn);
}