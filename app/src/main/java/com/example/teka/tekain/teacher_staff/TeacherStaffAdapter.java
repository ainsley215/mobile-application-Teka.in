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

import com.example.teka.tekain.R;

import java.util.List;

public class TeacherStaffAdapter extends RecyclerView.Adapter<TeacherStaffAdapter.ViewHolder> {

    private Context context;
    private List<TeacherStaff> teacherStaffList;

    public TeacherStaffAdapter(Context context, List<TeacherStaff> teacherStaffList) {
        this.context = context;
        this.teacherStaffList = teacherStaffList;
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

        holder.buttonSelengkapnya.setOnClickListener(v -> {
            // Handle button click
        });
    }

    @Override
    public int getItemCount() {
        return teacherStaffList.size();
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