package com.example.ripetizionapp;

public class RegTeacher {

    String password,provincia;

    RegTeacher(String password, String provincia) {
        this.password=password;
        this.provincia=provincia;
    }

    public String getPassword(){
        return password;
    }
    public String getProvincia(){
        return provincia;
    }
    public void setPassword(String s){
        password=s;
    }
    public void setProvincia(String s){
        provincia=s;
    }
}