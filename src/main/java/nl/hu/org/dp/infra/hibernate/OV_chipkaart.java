package nl.hu.org.dp.infra.hibernate;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ov_chipkaart")
public class OV_chipkaart {
    @Id
    @Column(name = "kaart_nummer")
    private int kaart_nummer;
    @Column(name = "geldig_tot")
    private Date geldig_tot;
    @Column(name = "klasse")
    private int klasse;
    @Column(name = "saldo")
    private double saldo;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger;
    @ManyToMany(mappedBy = "ovChipkaarten")
    private List<Product> producten = new ArrayList<>();

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public OV_chipkaart(int kaart_nummer, Date geldig_tot, int klasse, double saldo, Reiziger reiziger) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }

    public OV_chipkaart() {
    }

    public String toString() {
        return "#" + kaart_nummer + " Geldig tot: " + geldig_tot + " klasse: " + klasse + " saldo: " + saldo + " reiziger_ID: " + reiziger.getReiziger_id();
    }

    public int getKaart_nummer() {
        return kaart_nummer;
    }


    public Date getGeldig_tot() {
        return geldig_tot;
    }

    public void setGeldig_tot(Date geldig_tot) {
        this.geldig_tot = geldig_tot;
    }


    public int getKlasse() {
        return klasse;
    }


    public double getSaldo() {
        return saldo;
    }


    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setKaart_nummer(int kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
    }


}
