package com.example.ripetizionapp;

import java.util.ArrayList;

public class Insegnante {

    String nome, cognome, località;
    int tel;
    ArrayList<String> materie;

    public Insegnante(){}

    public Insegnante(String nome, String cognome, String località, int tel, ArrayList<String> materie){
        this.nome=nome;
        this.cognome=cognome;
        this.località=località;
        this.tel=tel;
        this.materie=materie;
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
    public int getTel(){
        return tel;
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
    public void setLocalità(String s){
        località=s;
    }
    public void setTel(int i){
        tel=i;
    }
    public void setMaterie(ArrayList<String> m){
        materie=m;
    }
}


