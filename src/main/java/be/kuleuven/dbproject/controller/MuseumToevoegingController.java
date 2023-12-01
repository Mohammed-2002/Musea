package be.kuleuven.dbproject.controller;
import be.kuleuven.dbproject.ProjectMain;
import be.kuleuven.dbproject.model.Medewerker;
import be.kuleuven.dbproject.model.Museum;
import be.kuleuven.dbproject.repository.MedewerkerRepository;
import be.kuleuven.dbproject.repository.MuseumRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.persistence.Persistence;

public class MuseumToevoegingController {

    @FXML
    private TextField txtNaam;

    @FXML
    private TextField txtAdres;

    @FXML
    private Button btnVoegToe;

    @FXML
    private void initialize() {
        // Voeg een actie toe aan de knop "Voeg Toe" bij initialisatie
        btnVoegToe.setOnAction(event -> voegMuseumToe());
    }

    private void voegMuseumToe() {
        String naam = txtNaam.getText();
        String adres = txtAdres.getText();
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.dbproject");
        var entityManager = sessionFactory.createEntityManager();
        MuseumRepository museumRepository = new MuseumRepository(entityManager);
        if(!naam.isEmpty() && !adres.isEmpty()) {
            Museum museum = new Museum(naam, adres);
            museumRepository.save(museum);
            showWerkingsscherm();
        }
    }

    private void showWerkingsscherm(){
        try {

            Stage oldStage = (Stage) btnVoegToe.getScene().getWindow();
            oldStage.close();

            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("beheerscherm2.fxml"));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("welcome");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm2 niet vinden", e);
        }
    }
}
