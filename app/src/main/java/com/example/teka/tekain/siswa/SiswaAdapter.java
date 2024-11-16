package com.example.teka.tekain.siswa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teka.tekain.R;

import java.util.ArrayList;
import java.util.List;

public class SiswaAdapter extends RecyclerView.Adapter<SiswaAdapter.ViewHolder> implements Filterable {

    private List<Siswa> siswaList;
    private List<Siswa> siswaListFull; // List penuh untuk pencarian

    public SiswaAdapter(List<Siswa> siswaList) {
        this.siswaList = siswaList;
        siswaListFull = new ArrayList<>(siswaList); // Salin data siswa ke siswaListFull
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_siswa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Siswa siswa = siswaList.get(position);
        holder.textName.setText(siswa.getName());
        holder.textNisn.setText(siswa.getNisn());
        holder.textGender.setText(siswa.getGender());
        holder.textClass.setText(siswa.getStudentClass());
    }

    @Override
    public int getItemCount() {
        return siswaList.size();
    }

    @Override
    public Filter getFilter() {
        return siswaFilter;
    }

    // Filter untuk pencarian
    private final Filter siswaFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Siswa> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(siswaListFull); // Tampilkan semua data jika tidak ada query
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Siswa item : siswaListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern) ||
                            item.getNisn().contains(filterPattern)) { // Sesuaikan dengan atribut yang ingin dicari
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            siswaList.clear();
            siswaList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textNisn, textGender, textClass;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textNisn = itemView.findViewById(R.id.textNisn);
            textGender = itemView.findViewById(R.id.textGender);
            textClass = itemView.findViewById(R.id.textClass);
        }
    }
}