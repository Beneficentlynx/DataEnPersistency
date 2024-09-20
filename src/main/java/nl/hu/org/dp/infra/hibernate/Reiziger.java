package nl.hu.org.dp.infra.hibernate;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reiziger")
public class Reiziger {
    private int reiziger_id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;


    public Reiziger(int reiziger_id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum){
        this.reiziger_id = reiziger_id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public Reiziger() {

    }

    public String toString(){
        return "#" + reiziger_id + " " + voorletters + "."
                +  tussenvoegsel + " " + achternaam + " (" +
                geboortedatum + ")";
    }
    @Column(name = "geboortedatum")
    public Date getGeboortedatum() {
        return geboortedatum;
    }

    @Column(name = "achternaam")
    public String getAchternaam() {
        return achternaam;
    }

    @Column(name = "tussenvoegsel")
    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    @Column(name = "voorletters")
    public String getVoorletters() {
        return voorletters;
    }

    @Id
    @Column(name = "reiziger_id")
    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public void setAchternaam(String achternaam){
        this.achternaam = achternaam;
    }

}
