package com.gp2017.Entity;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Object that represents a lesson.
 */
public class Les {
    private String vakNaam;
    private String vakCode;
    private String gebouw;
    private String lokaal;

    private Time starttijd;
    private Time eindtijd;

    private String klas;
    private Docent docent;
    private ArrayList<Absentie> absenties;

    /**
     * @param vakNaam course name
     * @param vakCode course code
     * @param gebouw building
     * @param lokaal room number
     * @param starttijd start time
     * @param eindtijd end time
     * @param klas grade
     * @param docent teacher
     * @param absenties ArrayList of absence objects
     */
    public Les(String vakNaam, String vakCode, String gebouw, String lokaal, Time starttijd, Time eindtijd, String klas,
               Docent docent)
    {
        this.vakNaam = vakNaam;
        this.vakCode = vakCode;
        this.gebouw = gebouw;
        this.lokaal = lokaal;
        this.starttijd = starttijd;
        this.eindtijd = eindtijd;
        this.klas = klas;
        this.docent = docent;
        this.absenties = new ArrayList<Absentie>();
    }
    
    public void voegAbsentieToe(Absentie abs) {
    	this.absenties.add(abs);
    }

    public String getVakNaam() {
        return vakNaam;
    }

    public String getVakCode() {
        return vakCode;
    }

    public String getGebouw() {
        return gebouw;
    }

    public String getLokaal() {
        return lokaal;
    }

    public Time getStarttijd() {
        return starttijd;
    }

    public Time getEindtijd() {
        return eindtijd;
    }

    public String getKlas() {
        return klas;
    }

    public Docent getDocent() {
        return docent;
    }

    public ArrayList<Absentie> getAbsenties() {
        return absenties;
    }
}


