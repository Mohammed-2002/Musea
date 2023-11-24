package be.kuleuven.dbproject.model;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Bezoek {

    @Id
    @GeneratedValue
    private int bezoekID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bezoekerID")
    private Bezoeker bezoeker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "museumID")
    private Museum museum;

    @Column(nullable = false)
    private LocalDate datum;

    @ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinTable(
            name = "GeleendeBoeken",
            joinColumns = @JoinColumn(name = "bezoekID"),
            inverseJoinColumns = @JoinColumn(name = "boekID")
    )
    private List<Boek> geleendeBoeken;
    @ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinTable(
            name = "GeleendeGames",
            joinColumns = @JoinColumn(name = "bezoekID"),
            inverseJoinColumns = @JoinColumn(name = "gameID")
    )
    private List<Boek> geleendeGames;
    @ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinTable(
            name = "BijdragesTijdensBezoek",
            joinColumns = @JoinColumn(name = "bezoekID"),
            inverseJoinColumns = @JoinColumn(name = "bijdrageID")
    )
    private List<Bijdrage> bijdragesTijdensBezoek;

    public Bezoek(){

    }
    public Bezoek(Bezoeker bezoeker, Museum museum, LocalDate datum, List<Boek> geleendeBoeken, List<Boek> geleendeGames, List<Bijdrage> bijdragesTijdensBezoek) {
        this.bezoeker = bezoeker;
        this.museum = museum;
        this.datum = datum;
        this.geleendeBoeken = geleendeBoeken;
        this.geleendeGames = geleendeGames;
        this.bijdragesTijdensBezoek = bijdragesTijdensBezoek;
    }


    public int getBezoekID() {
        return bezoekID;
    }

    public Bezoeker getBezoeker() {
        return bezoeker;
    }

    public Museum getMuseum() {
        return museum;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public List<Boek> getGeleendeBoeken() {
        return geleendeBoeken;
    }

    public List<Boek> getGeleendeGames() {
        return geleendeGames;
    }

    public List<Bijdrage> getBijdragesTijdensBezoek() {
        return bijdragesTijdensBezoek;
    }

    public void setMuseum(Museum museum) {
        this.museum = museum;
    }

    public float getTotaleBedrag(){
        float totaalBedrag = 0.0f;

        for (Bijdrage bijdrage : bijdragesTijdensBezoek) {
            totaalBedrag += bijdrage.getBedrag();
        }

        return totaalBedrag;
    }
}
