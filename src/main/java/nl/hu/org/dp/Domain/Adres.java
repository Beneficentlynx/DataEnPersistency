package nl.hu.org.dp.Domain;

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

    public int getReiziger() {
        return reiziger_id;
    }
}
