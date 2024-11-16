package com.example.teka.tekain.API;

import com.example.teka.tekain.siswa.Siswa;
import com.example.teka.tekain.teacher_staff.TeacherStaff;
import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface APIInterface {
    @GET("get_teachers.php")
    Call<List<TeacherStaff>> getTeachers();

    @GET("get_siswa.php")
    Call<List<Siswa>> getSiswa(); // Added method name for fetching students
}