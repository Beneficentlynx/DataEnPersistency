package nl.hu.org.dp.infra.hibernate;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "adres")
public class Adres {
    @Id
    @Column(name = "adres_id")
    private int adres_id;
    @Column(name = "postcode")
    private String postcode;
    @Column(name = "huisnummer")
    private String huisnummer;
    @Column(name = "straat")
    private String straat;
    @Column(name = "woonplaats")
    private String woonplaats;
    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger;

    public Adres(int adres_id, String postcode, String huisnummer, String straat, String woonplaats, Reiziger reiziger){
        this.adres_id = adres_id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger = reiziger;
    }

    public Adres() {

    }

    public String toString(){
        return "#" + adres_id + " " + postcode + " " + huisnummer + " " + straat + " " + woonplaats;
    }


    public String getPostcode() {
        return postcode;
    }


    public String getHuisnummer() {
        return huisnummer;
    }


    public String getStraat() {
        return straat;
    }


    public String getWoonplaats() {
        return woonplaats;
    }


    public int getAdres_id() {
        return adres_id;
    }


    public void setPostcode(String postcode){
        this.postcode = postcode;
    }

    public void setHuisnummer(String huisnummer){
        this.huisnummer = huisnummer;
    }

    public void setStraat(String straat){
        this.straat = straat;
    }

    public void setAdres_id(int adres_id) {
        this.adres_id = adres_id;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }


    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }
}
