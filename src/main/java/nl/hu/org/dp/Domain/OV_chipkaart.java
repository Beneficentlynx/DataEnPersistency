package nl.hu.org.dp.Domain;

import java.sql.Date;
import java.util.ArrayList;

public class OV_chipkaart {
    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private float saldo;
    private Reiziger reiziger;
    private int reiziger_id;
    private ArrayList<Product> producten = new ArrayList<>();

    public OV_chipkaart(int kaart_nummer, Date geldig_tot, int klasse, float saldo, Reiziger reiziger){
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
        this.reiziger_id = reiziger.getReiziger_id();
        this.producten = new ArrayList<>();
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

    public void setGeldig_tot(Date geldig_tot){
        this.geldig_tot = geldig_tot;
    }

    public void setKlasse(int klasse){
        this.klasse = klasse;
    }

    public void setReiziger(Reiziger reiziger){
        this.reiziger = reiziger;
    }

    public void setReiziger_id(int reiziger_id){
        this.reiziger_id = reiziger_id;
    }

    public void setKaart_nummer(int kaart_nummer){
        this.kaart_nummer = kaart_nummer;
    }

    public Reiziger getReiziger(){
        return reiziger;
    }

    public void setReiziger_id(Reiziger reiziger){
        this.reiziger = reiziger;
    }

    public ArrayList<Product> getProducten() {
        return producten;
    }

    public void addProduct(Product product){
        producten.add(product);
    }

    public void removeProduct(Product product){
        producten.remove(product);
    }

}
