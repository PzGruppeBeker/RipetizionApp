package com.example.ripetizionapp;

public class RegTeacher {

    String password,provincia;
    String admin="0";

    public RegTeacher(){}

    public RegTeacher(String password, String provincia) {
        this.password=password;
        this.provincia=provincia;
    }

    public String getPassword(){
        return password;
    }
    public String getProvincia(){
        return provincia;
    }
    public String getAdmin() {
        return admin;
    }
    public void setPassword(String s){
        password=s;
    }
    public void setProvincia(String s){
        provincia=s;
    }
    public void setAdmin(String s) {
        admin=s;
    }
}