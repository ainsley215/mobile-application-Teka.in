package com.example.teka.tekain;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Pembayaran extends AppCompatActivity {

    private TableLayout tablePayment;
    private Spinner spinnerYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pembayaran_);

        TableLayout VtablePayment = findViewById(R.id.tablePayment);
        spinnerYear = findViewById(R.id.spinnerYear);

        // Listener untuk Spinner
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedYear = parent.getItemAtPosition(position).toString();
                loadPaymentData(selectedYear);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void loadPaymentData(String year) {
        // Panggil API PHP untuk mengambil data pembayaran
        String url = "http://192.168.1.x/your_api_folder/get_payment.php?year=" + year;

        // Gunakan library seperti Volley atau Retrofit untuk memproses permintaan HTTP.
        // Contoh menggunakan Volley:
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    tablePayment.removeAllViews(); // Hapus data sebelumnya

                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject payment = response.getJSONObject(i);
                            String month = payment.getString("month");
                            String date = payment.getString("date");
                            String status = payment.getString("status");

                            TableRow row = new TableRow(this);
                            row.addView(createTextView(String.valueOf(i + 1)));
                            row.addView(createTextView(month));
                            row.addView(createTextView(date));
                            row.addView(createTextView(status));

                            tablePayment.addView(row);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace()
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(8, 8, 8, 8);
        return textView;
    }
}