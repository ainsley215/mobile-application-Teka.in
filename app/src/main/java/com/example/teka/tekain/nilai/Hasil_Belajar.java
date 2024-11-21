package com.example.teka.tekain.nilai;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.teka.tekain.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Hasil_Belajar extends AppCompatActivity {

    private TextView tvName, tvNisn, tvGender, tvGroup, tvClassTeacher, tvAddress, tvGrades;
    private String studentId = "12345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.hasil_belajar_);

        // Inisialisasi TextView
        tvName = findViewById(R.id.tvName);
        tvNisn = findViewById(R.id.tvNisn);
        tvGender = findViewById(R.id.tvGender);
        tvGroup = findViewById(R.id.tvGroup);
        tvClassTeacher = findViewById(R.id.tvClassTeacher);
        tvAddress = findViewById(R.id.tvAddress);
        tvGrades = findViewById(R.id.tvGrades);

        // Panggil fungsi untuk mengambil data
        fetchStudentData(studentId);
    }

    private void fetchStudentData(String studentId) {
        String url = "http://your-server-address/get_student_data.php?id=" + studentId;

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("error")) {
                                Toast.makeText(StudentDetailActivity.this, response.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // Parsing data siswa
                            JSONObject student = response.getJSONObject("student");
                            tvName.setText(student.getString("name"));
                            tvNisn.setText(student.getString("nisn"));
                            tvGender.setText(student.getString("gender"));
                            tvGroup.setText(student.getString("group"));
                            tvClassTeacher.setText(student.getString("class_teacher"));
                            tvAddress.setText(student.getString("address"));

                            // Parsing nilai
                            JSONArray grades = response.getJSONArray("grades");
                            StringBuilder gradesText = new StringBuilder();
                            for (int i = 0; i < grades.length(); i++) {
                                JSONObject grade = grades.getJSONObject(i);
                                gradesText.append(grade.getString("subject"))
                                        .append(": ")
                                        .append(grade.getString("grade"))
                                        .append("\nComment: ")
                                        .append(grade.getString("comment"))
                                        .append("\n\n");
                            }
                            tvGrades.setText(gradesText.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(StudentDetailActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StudentDetailActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }
}