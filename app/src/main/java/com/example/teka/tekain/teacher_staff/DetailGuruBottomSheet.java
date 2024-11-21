package com.example.teka.tekain.teacher_staff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.example.teka.tekain.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DetailGuruBottomSheet extends BottomSheetDialogFragment {

    private TextView tvName, tvAboutDescription;
    private ImageView profileImage;

    // Pass the details as arguments
    public static DetailGuruBottomSheet newInstance(String name, String about, String profileImageUrl) {
        DetailGuruBottomSheet fragment = new DetailGuruBottomSheet();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("about", about);
        args.putString("profileImageUrl", profileImageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for the bottom sheet
        View view = inflater.inflate(R.layout.bottom_sheet_dialog_fragment_, container, false);

        // Initialize views
        tvName = view.findViewById(R.id.name);
        tvAboutDescription = view.findViewById(R.id.textViewAboutTeacher);
        profileImage = view.findViewById(R.id.profilepic);

        // Retrieve the data from arguments
        if (getArguments() != null) {
            String name = getArguments().getString("name");
            String about = getArguments().getString("about");
            String profileImageUrl = getArguments().getString("profileImageUrl");

            tvName.setText(name);
            tvAboutDescription.setText(about);

            // Load profileImageUrl into profileImage using Glide
            Glide.with(this)
                    .load(profileImageUrl)
                    .placeholder(R.drawable.ic_profile_placeholder) // Tambahkan placeholder jika diperlukan
                    .error(R.drawable.ic_profile_placeholder) // Tambahkan error image jika URL gagal di-load
                    .into(profileImage);
        }

        return view;
    }
}