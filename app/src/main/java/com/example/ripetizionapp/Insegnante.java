package com.example.ripetizionapp;

public class Insegnante {

    String nome, cognome;

    public Insegnante(){}

    public Insegnante(String nome, String cognome){
        this.nome=nome;
        this.cognome=cognome;
    }
    public String getNome(){
        return nome;
    }
    public String getCognomeme(){
        return cognome;
    }
    public void setNome(String s){
        nome=s;
    }
    public void setCognome(String s){
        cognome=s;
    }
}
