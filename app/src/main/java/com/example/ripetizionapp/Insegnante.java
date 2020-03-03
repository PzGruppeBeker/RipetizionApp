package com.example.ripetizionapp;

import java.util.ArrayList;

public class Insegnante {

    String nome, cognome;
    ArrayList<String> materie;

    public Insegnante(){}

    public Insegnante(String nome, String cognome, ArrayList<String> materie){
        this.nome=nome;
        this.cognome=cognome;
        this.materie=materie;
    }
    public String getNome(){
        return nome;
    }
    public String getCognome(){
        return cognome;
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
