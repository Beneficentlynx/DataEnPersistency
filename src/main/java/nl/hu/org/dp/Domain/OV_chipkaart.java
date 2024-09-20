package nl.hu.org.dp.Domain;

import java.sql.Date;

public class OV_chipkaart {
    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private float saldo;
    private int reiziger_id;

    public OV_chipkaart(int kaart_nummer, Date geldig_tot, int klasse, float saldo, int reiziger_id){
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger_id = reiziger_id;
    }

    public String toString(){
        return "#" + kaart_nummer + " Gelidg tot: " + geldig_tot + " Klasse: " + klasse + " Saldo: " + saldo + " Reiziger_ID: " + reiziger_id;
    }

    public int getKaart_nummer() {
        return kaart_nummer;
    }

    public java.sql.Date getGeldig_tot() {
        return geldig_tot;
    }

    public int getKlasse() {
        return klasse;
    }

    public float getSaldo() {
        return saldo;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setSaldo(float saldo){
        this.saldo = saldo;
    }
}
