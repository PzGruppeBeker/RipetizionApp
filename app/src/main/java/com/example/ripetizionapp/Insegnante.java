package com.example.ripetizionapp;

import java.util.ArrayList;

public class Insegnante {

    String email,nome, cognome, località;
    int tel;
    ArrayList<String> materie;

    public Insegnante(){}

    public Insegnante(String email, String nome, String cognome, String località, int tel, ArrayList<String> materie){
        this.email=email;
        this.nome=nome;
        this.cognome=cognome;
        this.località=località;
        this.tel=tel;
        this.materie=materie;
    }
    public String getEmail(){
        return email;
    }
    public String getNome(){
        return nome;
    }
    public String getCognome(){
        return cognome;
    }
    public String getLocalità() {
        return località;
    }
    public ArrayList<String> getMaterie() {
        return materie;
    }
    public void setNome(String s){
        nome=s;
    }
    public void setCognome(String s){
        cognome=s;
    }
    public void setMaterie(ArrayList<String> m){
        materie=m;
    }
}
