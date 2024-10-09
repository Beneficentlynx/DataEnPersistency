package nl.hu.org.dp.infra.hibernate;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reiziger")
public class Reiziger {
    @Id
    @Column(name = "reiziger_id")
    private int reiziger_id;
    @Column(name = "voorletters")
    private String voorletters;
    @Column(name = "tussenvoegsel")
    private String tussenvoegsel;
    @Column(name = "achternaam")
    private String achternaam;
    @Column(name = "geboortedatum")
    private Date geboortedatum;
    @OneToOne (
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "reiziger_id")
    private Adres adres;
    @OneToMany(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "reiziger_id")
    private List<OV_chipkaart> ovChipkaarten = new ArrayList<>();



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

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setOvChipkaarten(List<OV_chipkaart> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }

    public List<OV_chipkaart> getOvChipkaarten() {
        return ovChipkaarten;
    }

    public void addOvChipkaart(OV_chipkaart ovChipkaart) {
        ovChipkaarten.add(ovChipkaart);
    }

    public void removeOvChipkaart(OV_chipkaart ovChipkaart) {
        ovChipkaarten.remove(ovChipkaart);
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
