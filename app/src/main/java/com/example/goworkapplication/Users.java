package com.example.goworkapplication;

public class Users {
    private String idUser;
    private String genre;
    private String nom;
    private String prenom;
    private String email;
    private String etude;
    private String lieux;
    private String phone;
    private String date;
    private String password;

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Users(String genre, String nom, String prenom, String email, String etude, String lieux, String phone, String date, String password) {
        this.genre= genre;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.etude = etude;
        this.lieux = lieux;
        this.phone = phone;
        this.date = date;
        this.password = password;
    }

    public Users(String idUser,String genre, String nom, String prenom, String email, String etude, String lieux, String phone, String date, String password) {
        this.idUser = idUser;
        this.genre= genre;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.etude = etude;
        this.lieux = lieux;
        this.phone = phone;
        this.date = date;
        this.password = password;
    }

    public Users() {
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEtude() {
        return etude;
    }

    public void setEtude(String etude) {
        this.etude = etude;
    }

    public String getLieux() {
        return lieux;
    }

    public void setLieux(String lieux) {
        this.lieux = lieux;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
