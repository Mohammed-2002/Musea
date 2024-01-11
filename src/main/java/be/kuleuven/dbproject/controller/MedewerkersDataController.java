package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.ProjectMain;
import be.kuleuven.dbproject.model.Medewerker;
import be.kuleuven.dbproject.model.Museum;
import be.kuleuven.dbproject.repository.MedewerkerRepository;
import be.kuleuven.dbproject.repository.MuseumRepository;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MedewerkersDataController {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;
    @FXML
    private TableView tblConfigs;

    SharedData sharedData = SharedData.getInstance();

    public void init() {
        if (!sharedData.getLoggedInMedewerker().isAdmin()) {
            btnDelete.setDisable(true);
            btnAdd.setDisable(true);
            btnModify.setDisable(true);
        }

        initTable(); //tabel laten zien

        btnAdd.setOnAction(e -> voegEenNieuweMedewerkerToe());

        btnModify.setOnAction(e -> {
            IsOneRowSelected();
            modifyCurrentRow();
        });
        btnDelete.setOnAction(e -> {
            IsOneRowSelected();
            deleteCurrentRow();
        });

        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }

    private void voegEenNieuweMedewerkerToe() {

        MedewerkerRepository medewerkerRepository = new MedewerkerRepository(sharedData.getEntityManager());


        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/medewerkerToevoeging.fxml"));
            Parent root = loader.load();

            MedewerkerToevoegingController medewerkerToevoegingController = loader.getController();


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e){
            throw new RuntimeException("Kan medewerkerToevoeging.fxml niet vinden", e);
        }
    }

    private void initTable() { //tabel maken
        tblConfigs.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigs.getColumns().clear();

        int colIndex = 0;
        for(var colName : new String[]{"medewerkerID", "Naam", "Adres"}) {
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(colName);
            final int finalColIndex = colIndex;
            col.setCellValueFactory(f -> new ReadOnlyObjectWrapper<>(f.getValue().get(finalColIndex)));
            tblConfigs.getColumns().add(col);
            colIndex++;
        }

        MedewerkerRepository medewerkerRepository = new MedewerkerRepository((sharedData.getEntityManager()));
        List<Medewerker> medewerkerLijst = new ArrayList<>();
        medewerkerLijst = medewerkerRepository.findAll();
        for(int i = 0; i <= medewerkerLijst.size()-1; i++) {
            tblConfigs.getItems().add(FXCollections.observableArrayList(String.valueOf(medewerkerLijst.get(i).getMedewerkerID()), medewerkerLijst.get(i).getNaam(), medewerkerLijst.get(i).getEmailAdres() ));
        }
    }

    private void addNewRow() {
        try {
            Stage currentStage = (Stage) btnAdd.getScene().getWindow();
            currentStage.close();
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("medewerkerToevoeging.fxml"));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Toevoeging van een medewerker");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException("Kan medewerkerToevoeging.fxml niet vinden", e);
        }
    }


    private void deleteCurrentRow() {
        ObservableList<String> selectedRow = (ObservableList<String>) tblConfigs.getSelectionModel().getSelectedItem();

        ButtonType nietVerwijderenButton = new ButtonType("Niet verwijderen", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Vraag om bevestiging met twee knoppen
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bevestig verwijdering");
        alert.setHeaderText("Ben je zeker dat je de geselecteerde musea wilt verwijderen?");
        alert.setContentText("Deze actie kan niet ongedaan worden gemaakt.");
        alert.getButtonTypes().setAll(ButtonType.OK, nietVerwijderenButton);

        // Haal het resultaat op (OK of Niet verwijderen)
        var result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) { //haalt rij uit tabel
            int museumID = Integer.parseInt(selectedRow.get(0));
            MuseumRepository museumRepository = new MuseumRepository(sharedData.getEntityManager());
            museumRepository.deleteByID(museumID);
        }
        refreshCurrentStage();

    }
    private void modifyCurrentRow() {
    }

    public void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean IsOneRowSelected() {
        if(tblConfigs.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Eerst een record selecteren hé.");
        }
        return !(tblConfigs.getSelectionModel().getSelectedCells().size() == 0);
    }
    private void refreshCurrentStage() {
        // Haal de huidige stage op en vernieuw deze
        Stage currentStage = (Stage) btnAdd.getScene().getWindow();
        ObservableList<ObservableList<String>> items = FXCollections.observableArrayList();
        tblConfigs.setItems(items);
        // Optioneel: Als je extra configuratie in je TableView hebt, kun je deze ook opnieuw instellen
        tblConfigs.getColumns().clear();
        initTable();
        currentStage.hide();  // Verberg de huidige stage
        currentStage.show();  // Toon de vernieuwde stage
    }
    private boolean wordtEenNieuweMedewerkerToegevoegd() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Keuze medewerker");
        alert.setHeaderText("Voeg een nieuwe of bestaande medewerker toe?");
        alert.setContentText("Kies een optie:");

        // Voeg knoppen toe aan het dialoogvenster
        ButtonType nieuweMedewerkerButton = new ButtonType("Nieuwe medewerker");
        ButtonType bestaandeMedewerkerButton = new ButtonType("Bestaande medewerker");
        ButtonType cancelButton = new ButtonType("Annuleren");

        alert.getButtonTypes().setAll(nieuweMedewerkerButton, bestaandeMedewerkerButton, cancelButton);

        // Toon het dialoogvenster en wacht op de reactie van de gebruiker
        alert.showAndWait();

        // Bepaal de keuze van de gebruiker op basis van de reactie
        return alert.getResult() == nieuweMedewerkerButton;
    }
    private String vraagEmailAdres() {
        // Maak een nieuw dialoogvenster aan met een TextField om het e-mailadres in te voeren
        TextField emailField = new TextField();
        emailField.setPromptText("Voer het e-mailadres in");

        GridPane grid = new GridPane();
        grid.add(emailField, 0, 0);

        Alert emailDialog = new Alert(Alert.AlertType.CONFIRMATION);
        emailDialog.setTitle("Voer e-mailadres in");
        emailDialog.setHeaderText(null);
        emailDialog.getDialogPane().setContent(grid);

        // Voeg OK en Annuleren knoppen toe
        emailDialog.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        // Toon het dialoogvenster en wacht op de reactie van de gebruiker
        emailDialog.showAndWait();

        // Controleer of de gebruiker op OK heeft geklikt en retourneer het ingevoerde e-mailadres
        if (emailDialog.getResult() == ButtonType.OK) {
            return emailField.getText().toLowerCase();
        } else {
            // Gebruiker heeft geannuleerd of venster gesloten, retourneer een lege string
            return "";
        }
    }
}
