package be.kuleuven.dbproject.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Medewerker {
    @Id
    @GeneratedValue
    private int medewerkerID;
    @Column(nullable = false)
    private String naam;
    @Column(nullable = false)
    private String emailAdres;
    @Column(nullable = false)
    private String wachtwoord;
    @Column(nullable = false)
    private LocalDate geboortedatum;

    @ManyToMany(mappedBy = "medewerkers", fetch = FetchType.LAZY)
    private List<Museum> musea;

    public Medewerker(){

    }
    public Medewerker(String naam, String emailAdres, String wachtwoord, LocalDate geboortedatum, List<Museum> musea) {
        this.naam = naam;
        this.emailAdres = emailAdres;
        this.wachtwoord = wachtwoord;
        this.geboortedatum = geboortedatum;
        this.musea = musea;
    }
    public Medewerker(String naam, String emailAdres, String wachtwoord, LocalDate geboortedatum) {
        this.naam = naam;
        this.emailAdres = emailAdres;
        this.wachtwoord = wachtwoord;
        this.geboortedatum = geboortedatum;
        this.musea = new ArrayList<>();
    }

    public int getMedewerkerID() {
        return medewerkerID;
    }

    public String getNaam() {
        return naam;
    }

    public String getEmailAdres() {
        return emailAdres;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }

    public List<Museum> getMusea() {
        return musea;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setEmailAdres(String emailAdres) {
        this.emailAdres = emailAdres;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public void setMusea(List<Museum> musea) {
        this.musea = musea;
    }
    public void voegMuseumToe(Museum museum){
        musea.add(museum);
        museum.getMedewerkers().add(this);
    }
}
