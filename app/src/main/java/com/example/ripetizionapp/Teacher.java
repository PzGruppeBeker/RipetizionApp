package com.example.ripetizionapp;

import java.util.ArrayList;

public class Teacher {

    String nome, cognome, località, provincia;
    int tel;
    ArrayList<String> materie, recensioni;

    public Teacher(){}

    public Teacher(String nome, String cognome, String provincia, int tel, ArrayList<String> materie){
        this.nome=nome;
        this.cognome=cognome;
        this.provincia=provincia;
        this.tel=tel;
        this.materie=materie;
    }
    public String getNome(){
        return nome;
    }
    public String getCognome(){
        return cognome;
    }
    public String getProvincia() {
        return provincia;
    }
    public int getTel(){
        return tel;
    }
    public String getLocalità() {return località;}
    public ArrayList<String> getMaterie() {
        return materie;
    }
    public ArrayList<String> getRecensioni(){
        return recensioni;
    }
    public void setNome(String s){
        nome=s;
    }
    public void setCognome(String s){
        cognome=s;
    }
    public void setProvincia(String s){
        provincia=s;
    }
    public void setTel(int i){
        tel=i;
    }
    public void setMaterie(ArrayList<String> m){
        materie=m;
    }
    public void setLocalità(String s) {
        località=s;
    }
    public void setRecensioni(ArrayList<String> r){
        recensioni=r;
    }
    private void addRecensione(String s){
        recensioni.add(s);
    }
    private void deleteRecensione(int i){
        recensioni.remove(i);
    }
}


