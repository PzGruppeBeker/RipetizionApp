package com.example.ripetizionapp;

public class TeacherItem {

    private int teacherProfile;
    private String teacherName;
    private String teacherSubjects;

    //ho usato una sola stringa nome per nome e cognome, se necessario lo modifico
    //idem per le materie, ma essendo un numero variabile devo per forza

    public TeacherItem(int image, String name, String subjects) {
        teacherProfile = image;
        teacherName = name;
        teacherSubjects = subjects;
    }

    public int getTeacherProfile() {
        return teacherProfile;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getTeacherSubjects() {
        return teacherSubjects;
    }
}
