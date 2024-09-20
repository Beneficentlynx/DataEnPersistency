package nl.hu.org.dp.infra.hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;

@Entity
@Table(name = "ov_chipkaart")
public class OV_chipkaart {

    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private double saldo;
    private int reiziger_id;

    public OV_chipkaart(int kaart_nummer, Date geldig_tot, int klasse, double saldo, int reiziger_id) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger_id = reiziger_id;
    }

    public OV_chipkaart() {
    }

    public String toString() {
        return "#" + kaart_nummer + " Geldig tot: " + geldig_tot + " klasse: " + klasse + " saldo: " + saldo + " reiziger_ID: " + reiziger_id;
    }
    @Id
    @Column(name = "kaart_nummer")
    public int getKaart_nummer() {
        return kaart_nummer;
    }

    @Column(name = "geldig_tot")
    public Date getGeldig_tot() {
        return geldig_tot;
    }

    public void setGeldig_tot(Date geldig_tot) {
        this.geldig_tot = geldig_tot;
    }

    @Column(name = "klasse")
    public int getKlasse() {
        return klasse;
    }

    @Column(name = "saldo")
    public double getSaldo() {
        return saldo;
    }

    @Column(name = "reiziger_id")
    public int getReiziger_id() {
        return reiziger_id;
    }


    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public void setKaart_nummer(int kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
    }

}
