package nl.hu.org.dp.Domain;

public class OV_chipkaart_product {
    private int kaart_nummer;
    private int product_nummer;
    private String reisproduct_status;
    private String last_update;

    public OV_chipkaart_product(int kaart_nummer, int product_nummer, String reisproduct_status, String last_update) {
        this.kaart_nummer = kaart_nummer;
        this.product_nummer = product_nummer;
        this.reisproduct_status = reisproduct_status;
        this.last_update = last_update;
    }

    public String toString(){
        return "#" + kaart_nummer + " " + product_nummer + " " + reisproduct_status + " " + last_update;
    }

    public int getKaart_nummer() {
        return kaart_nummer;
    }

    public void setKaart_nummer(int kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public String getReisproduct_status() {
        return reisproduct_status;
    }

    public void setReisproduct_status(String reisproduct_status) {
        this.reisproduct_status = reisproduct_status;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }
}
