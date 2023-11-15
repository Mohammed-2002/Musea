package be.kuleuven.dbproject.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Bijdrage {

    @Id
    @GeneratedValue
    private int bijdrageID;

    @Column(nullable = false)
    private float bedrag;

    @Column(nullable = false)
    private BetaalMethode betaalMethode;



    @ManyToMany(mappedBy = "bijdragesTijdensBezoek", fetch = FetchType.LAZY)
    private List<Bezoek> bezoeken;

    public Bijdrage(){

    }
    public Bijdrage(float bedrag, BetaalMethode betaalMethode) {
        this.bedrag = bedrag;
        this.betaalMethode = betaalMethode;

    }

    public int getBijdrageID() {
        return bijdrageID;
    }

    public float getBedrag() {
        return bedrag;
    }

    public BetaalMethode getBetaalMethode() {
        return betaalMethode;
    }
}
