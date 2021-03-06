package com.gp2017.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Object to represent a person
 */
public class Persoon {
    @JsonProperty("persoonId")
    private int id;
    private String naam;
    private String email;
    private String pswd;
    private String rol;


    /**
     * @param naam persons name
     * @param email persons email
     * @param pswd persons password
     */
    public Persoon(int id, String naam, String email, String pswd, String rol) {   //, ArrayList<Les> lessen) {
        this.id = id;
        this.naam = naam;
        this.email = email;
        this.pswd = pswd;
        this.rol = rol;

        //this.lessen = lessen;
    }

    public int getId() {
        return id;
    }

    /**
     * Get's persons name
     * @return name
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Get's persons email
     * @return email
     */
    public String getEmail() {
        return email;
    }



    /**
     * Checks password of the user
     * @param pswd
     * @return boolean
     */
    public boolean checkPswd(String pswd){
        return this.pswd.equals(pswd);
    }



    /**
     * check if an object is equal to another.
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Persoon){
            Persoon otherPersoon = (Persoon) obj;
            if (this.naam.equals(otherPersoon.getNaam()) &&
                    this.email.equals(otherPersoon.getEmail())){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Persoon{" +
                "naam='" + naam + '\'' +
                ", email='" + email + '\'' +
                ", pswd='" + pswd + '\'' +
                '}';
    }

    public String getRol() {
        return rol;
    }
}
