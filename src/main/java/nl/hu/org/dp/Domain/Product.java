package nl.hu.org.dp.Domain;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private float prijs;
    private List<OV_chipkaart> ovChipkaarten = new ArrayList<>();
    private ArrayList<OV_chipkaart_product> ovChipkaartProducts;

    public Product(int product_nummer, String naam, String beschrijving, float prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public Product(int product_nummer, String naam, String beschrijving, float prijs, ArrayList<OV_chipkaart_product> ovChipkaartProducts) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        this.ovChipkaartProducts = ovChipkaartProducts;
    }

    public String toString(){
        return "#" + product_nummer + " " + naam + " " + beschrijving + " " + prijs;
    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public float getPrijs() {
        return prijs;
    }

    public void setPrijs(float prijs) {
        this.prijs = prijs;
    }

    public void addOVKaart(OV_chipkaart ov_chipkaart){
        ovChipkaarten.add(ov_chipkaart);
    }

    public List<OV_chipkaart> getOvChipkaarten() {
        return ovChipkaarten;
    }

//    public ArrayList<OV_chipkaart_product> getOvChipkaartProducts() {
//        return ovChipkaartProducts;
//    }

}
