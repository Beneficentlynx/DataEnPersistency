package nl.hu.org.dp.infra.hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "adres")
public class Adres {
    private int adres_id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private int reiziger_id;

    public Adres(int adres_id, String postcode, String huisnummer, String straat, String woonplaats, int reiziger_id){
        this.adres_id = adres_id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger_id = reiziger_id;
    }

    public Adres() {

    }

    public String toString(){
        return "#" + adres_id + " " + postcode + " " + huisnummer + " " + straat + " " + woonplaats;
    }

    @Column(name = "postcode")
    public String getPostcode() {
        return postcode;
    }

    @Column(name = "huisnummer")
    public String getHuisnummer() {
        return huisnummer;
    }

    @Column(name = "straat")
    public String getStraat() {
        return straat;
    }

    @Column(name = "woonplaats")
    public String getWoonplaats() {
        return woonplaats;
    }

    @Id
    @Column(name = "adres_id")
    public int getAdres_id() {
        return adres_id;
    }

    @Column(name = "reiziger_id")
    public int getReiziger_id() {
        return reiziger_id;
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

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }
}
