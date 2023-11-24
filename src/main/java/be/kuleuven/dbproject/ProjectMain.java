package be.kuleuven.dbproject;

import be.kuleuven.dbproject.model.*;
import be.kuleuven.dbproject.repository.MuseumRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DB Taak 2022-2023: De Vrolijke Zweters
 * Zie https://kuleuven-diepenbeek.github.io/db-course/extra/project/ voor opgave details
 *
 * Deze code is slechts een quick-start om je op weg te helpen met de integratie van JavaFX tabellen en data!
 * Zie README.md voor meer informatie.
 */
public class ProjectMain extends Application {

    private static Stage rootStage;

    public static Stage getRootStage() {
        return rootStage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        rootStage = stage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("DAdministratie hoofdscherm TODO pas mij aan");
        stage.setScene(scene);
        stage.show();


        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.dbproject");
        var entityManager = sessionFactory.createEntityManager();
        var museumRepository = new MuseumRepository(entityManager);

        ArrayList<Medewerker> medewerkers = new ArrayList<>();
        ArrayList<Game> games = new ArrayList<>();
        ArrayList<Boek> boeken = new ArrayList<>();
        ArrayList<Bezoek> bezoeken = new ArrayList<>();
        ArrayList<Museum> musea =new ArrayList<>();
        Museum museum= new Museum("Museum van Tongeren","Tongeren", medewerkers,boeken,games,bezoeken);

        museumRepository.save(museum);


        Boek boek = new Boek("De liefde","Mohammed",1998,"UHasselt",museum,12,false,bezoeken);

        museum.voegBoekToe(boek);
        museumRepository.update(museum);



        Game game = new Game(museum,"The fox",1999,13,"FIFA",Console.PS5,bezoeken,false);
        museum.voegGameToe(game);
        museumRepository.update(museum);


        Medewerker medewerker = new Medewerker("Ased","Mail@gmail.com","blablabla", LocalDate.now(),musea);
        museum.voegMedewerkerToe(medewerker);
        museumRepository.update(museum);




        Museum museum2 = museumRepository.findById(1);
        System.out.println(museum2.getBoeken().get(0).getNaam());



    }

    public static void main(String[] args){
        launch();
    }
}
