package com.example.teka.tekain.siswa;

public class Siswa {
    private String name;
    private String nisn;
    private String gender;
    private String studentClass;
    private String timestamp;

    public Siswa(String name, String nisn, String gender, String studentClass) {
        this.name = name;
        this.nisn = nisn;
        this.gender = gender;
        this.studentClass = studentClass;
    }

    public String getName() {
        return name;
    }

    public String getNisn() {
        return nisn;
    }

    public String getGender() {
        return gender;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public String getTimestamp() {
        return timestamp; }
}