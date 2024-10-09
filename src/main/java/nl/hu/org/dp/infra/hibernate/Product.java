package nl.hu.org.dp.infra.hibernate;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "product_nummer")
    private int product_nummer;
    @Column(name = "naam")
    private String naam;
    @Column(name = "beschrijving")
    private String beschrijving;
    @Column(name = "prijs")
    private float prijs;
    @ManyToMany(
            cascade = CascadeType.MERGE
    )
    @JoinTable(
            name = "ov_chipkaart_product",
            joinColumns = {@JoinColumn(name = "kaart_nummer")},
            inverseJoinColumns = {@JoinColumn(name = "product_nummer")}
    )
    private List<OV_chipkaart> ovChipkaarten = new ArrayList<>();

    public Product(int product_nummer, String naam, String beschrijving, float prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public Product() {

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
}
