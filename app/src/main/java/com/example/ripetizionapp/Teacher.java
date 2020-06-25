package com.example.ripetizionapp;

import java.util.ArrayList;

/**
 * Questo oggetto contiene tutti i dati dell'insegnante visualizzabili dagli utenti ed è univocamente riferito ad un
 * oggetto "RegTeacher" tramite l'indirizzo e-mai.
 *
 */

public class Teacher {

    String email, nome, cognome, località, provincia, orario, tel;
    ArrayList<String> materie, recensioni;

    public Teacher(){}

    public Teacher(String email, String nome, String cognome, String provincia, String orario,  String tel, ArrayList<String> materie){
        this.email=SupportMethods.mailfromDB(email);
        this.nome=nome;
        this.cognome=cognome;
        this.provincia=provincia;
        this.orario=orario;
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
    public String getProvincia() {
        return provincia;
    }
    public String getOrario() {
        return orario;
    }
    public String getTel(){
        return tel;
    }
    public String getLocalità() {return località;}
    public ArrayList<String> getMaterie() {
        return materie;
    }
    public ArrayList<String> getRecensioni(){
        return recensioni;
    }
    public void setEmail(String s){
        email=s;
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
    public void setOrario(String orario) {
        this.orario = orario;
    }
    public void setTel(String s){
        tel=s;
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
    public void addRecensione(String s){
        recensioni.add(s);
    }
    public void deleteRecensione(int i){
        recensioni.remove(i);
    }
}


