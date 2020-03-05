package com.example.ripetizionapp;

public class RegInsegnante {

    String password,località;

    RegInsegnante (String password, String località) {
        this.password=password;
        this.località=località;
    }

    public String getPassword(){
        return password;
    }
    public String getLocalità(){
        return località;
    }
    public void setPassword(String s){
        password=s;
    }
    public void setLocalità(String s){
        località=s;
    }
}
