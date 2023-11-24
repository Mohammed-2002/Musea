package be.kuleuven.dbproject.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Boek {

    @Id
    @GeneratedValue
    private int boekID;

    @Column(nullable = false)
    private String naam;
    @Column(nullable = false)
    private String auteur;
    @Column(nullable = false)
    private int publicatiejaar;
    @Column(nullable = false)
    private String uitgever;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "museumID")
    private Museum museum;
    @Column(nullable = false)
    private float waarde;
    @Column(nullable = false)
    private boolean uitgeleend;

    @ManyToMany(mappedBy = "geleendeBoeken", fetch = FetchType.LAZY)
    private List<Bezoek> bezoeken;

    public Boek(){

    }
    public Boek(String naam, String auteur, int publicatiejaar, String uitgever, Museum museum, float waarde, boolean uitgeleend, List<Bezoek> bezoeken) {
        this.naam = naam;
        this.auteur = auteur;
        this.publicatiejaar = publicatiejaar;
        this.uitgever = uitgever;
        this.museum = museum;
        this.waarde = waarde;
        this.uitgeleend = uitgeleend;
        this.bezoeken = bezoeken;
    }
    public Boek(String naam, String auteur, int publicatiejaar, String uitgever, Museum museum, float waarde) {
        this.naam = naam;
        this.auteur = auteur;
        this.publicatiejaar = publicatiejaar;
        this.uitgever = uitgever;
        this.museum = museum;
        this.waarde = waarde;
        this.uitgeleend = false;
        this.bezoeken = new ArrayList<>();
    }

    public int getBoekID() {
        return boekID;
    }

    public String getNaam() {
        return naam;
    }

    public String getAuteur() {
        return auteur;
    }

    public int getPublicatiejaar() {
        return publicatiejaar;
    }

    public String getUitgever() {
        return uitgever;
    }

    public Museum getMuseum() {
        return museum;
    }

    public float getWaarde() {
        return waarde;
    }

    public boolean isUitgeleend() {
        return uitgeleend;
    }

    public List<Bezoek> getBezoeken() {
        return bezoeken;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setMuseum(Museum museum) {
        this.museum = museum;
    }

    public void setWaarde(float waarde) {
        this.waarde = waarde;
    }

    public void setUitgeleend(boolean uitgeleend) {
        this.uitgeleend = uitgeleend;
    }

    public void setBezoeken(List<Bezoek> bezoeken) {
        this.bezoeken = bezoeken;
    }
}
