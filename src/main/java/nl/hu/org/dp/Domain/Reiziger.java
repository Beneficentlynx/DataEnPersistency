package nl.hu.org.dp.Domain;

import java.util.Date;

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

    public String toString(){
        return "#" + reiziger_id + " " + voorletters + "."
                +  tussenvoegsel + " " + achternaam + " (" +
                geboortedatum + ")";
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setAchternaam(String achternaam){
        this.achternaam = achternaam;
    }

}
