package be.kuleuven.dbproject.controller;
import be.kuleuven.dbproject.ProjectMain;
import be.kuleuven.dbproject.model.Medewerker;
import be.kuleuven.dbproject.repository.MedewerkerRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.persistence.Persistence;

public class ProjectMainController{


    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;

    public void initialize() {
        btnLogin.setOnAction(e -> logIn());
    }

    private void logIn(){

        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.dbproject");
        var entityManager = sessionFactory.createEntityManager();
        var medewerkersRepository = new MedewerkerRepository(entityManager);
        Medewerker medewerker= medewerkersRepository.findByEmailAdres(txtEmail.getText());
        if (medewerker == null){
            showAlert("Medewerker niet gevonden", "De opgegeven mailAdres is niet gevonden.");
        } else if (!medewerker.getWachtwoord().equals(txtPassword.getText())) {
            showAlert("Incorrect password", "De opgegeven password is niet correct.");
        }else{
            showWerkingsscherm(medewerker);
        }
    }
    private void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showWerkingsscherm(Medewerker medewerker){
        try {

            Stage oldStage = (Stage) btnLogin.getScene().getWindow();
            oldStage.close();

            var stage = new Stage();
            var root = (TabPane) FXMLLoader.load(getClass().getClassLoader().getResource("museaMedewerker.fxml"));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("welcome" + medewerker.getNaam());
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm2 niet vinden", e);
        }
    }
}
