package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.ProjectMain;
import be.kuleuven.dbproject.model.Boek;
import be.kuleuven.dbproject.model.Museum;
import be.kuleuven.dbproject.repository.BoekRepository;
import be.kuleuven.dbproject.repository.MuseumRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.persistence.Persistence;

public class BoekToevoegingController {

    @FXML
    private TextField txtNaam;
    @FXML
    public TextField naamField;
    @FXML
    public TextField auteurField;
    @FXML
    public Button btnVoegToe;
    @FXML
    public TextField jaarField;
    @FXML
    public TextField uitgeverField;
    @FXML
    public TextField waardeField;

    @FXML
    private void initialize() {
        // Voeg een actie toe aan de knop "Voeg Toe" bij initialisatie
        btnVoegToe.setOnAction(event -> voegBoekToe());
    }

    SharedData sharedData = SharedData.getInstance();

    private void voegBoekToe() {
        String naam = naamField.getText();
        String auteur = auteurField.getText();
        Integer publicatiejaar = Integer.parseInt(jaarField.getText());
        String uitgever = uitgeverField.getText();
        Double waarde = Double.parseDouble(waardeField.getText());
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.dbproject");
        var entityManager = sessionFactory.createEntityManager();
        MuseumRepository museumRepository = new MuseumRepository(entityManager);
        if (!naam.isEmpty() && !auteur.isEmpty()) {
            Boek boek = new Boek(naam, auteur, publicatiejaar, uitgever, sharedData.getMuseum(), waarde);
            museumRepository.update(sharedData.getMuseum());
            //currentStage.close();


        }
    }

}
