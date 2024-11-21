package com.example.teka.tekain.teacher_staff;

public class TeacherStaff {
    private String name;
    private String position;
    private String profileImageUrl; // URL for the teacher's photo
    private String timestamp;  // Atau bisa menggunakan long jika format timestamp adalah Unix Timestamp

    public TeacherStaff(String name, String position, String photoUrl) {
        this.name = name;
        this.position = position;
        this.profileImageUrl = photoUrl;
        this.timestamp = timestamp;  // Misalnya, format string seperti "2024-11-20T10:00:00"
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getPhotoUrl() {
        return profileImageUrl;
    }

    public String getTimestamp() {
        return timestamp;  // Jika menggunakan String atau bisa menggunakan Long
    }
}