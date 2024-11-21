package com.example.teka.tekain.teacher_staff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.teka.tekain.R;

import java.util.ArrayList;
import java.util.List;

public class TeacherStaffAdapter extends RecyclerView.Adapter<TeacherStaffAdapter.ViewHolder> {

    private Context context;
    private List<TeacherStaff> teacherStaffList;
    private OnItemClickListener onItemClickListener;  // Ganti ke OnItemClickListener yang sesuai

    // Perbaikan konstruktor untuk menerima OnItemClickListener
    public TeacherStaffAdapter(Context context, List<TeacherStaff> teacherStaffList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.teacherStaffList = teacherStaffList;
        this.onItemClickListener = onItemClickListener;
    }

    // Method to update the list
    public void updateList(List<TeacherStaff> newList) {
        teacherStaffList = new ArrayList<>();
        teacherStaffList.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeacherStaff teacherStaff = teacherStaffList.get(position);
        holder.textName.setText(teacherStaff.getName());
        holder.textPosition.setText(teacherStaff.getPosition());

        // Memuat gambar menggunakan Glide
        Glide.with(context)
                .load(teacherStaff.getPhotoUrl())  // Memuat gambar dari URL
                .into(holder.imageProfile);         // Menetapkan gambar ke ImageView

        // Menangani klik pada item
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(teacherStaff);  // Panggil listener ketika item diklik
            }
        });

        // Menangani klik tombol Selengkapnya
        holder.buttonSelengkapnya.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onDetailButtonClick(teacherStaff);
            }
        });
    }

    @Override
    public int getItemCount() {
        return teacherStaffList.size();
    }

    // Interface untuk menangani klik item
    public interface OnItemClickListener {
        void onItemClick(TeacherStaff teacherStaff);
        void onDetailButtonClick(TeacherStaff teacherStaff); // Untuk klik tombol Selengkapnya
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProfile;
        TextView textName, textPosition;
        Button buttonSelengkapnya;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProfile = itemView.findViewById(R.id.imageProfile);
            textName = itemView.findViewById(R.id.textName);
            textPosition = itemView.findViewById(R.id.textPosition);
            buttonSelengkapnya = itemView.findViewById(R.id.buttonSelengkapnya);
        }
    }
}