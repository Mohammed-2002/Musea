package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.ProjectMain;
import be.kuleuven.dbproject.model.Museum;
import be.kuleuven.dbproject.repository.MuseumRepository;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.persistence.Persistence;
import java.util.List;

public class BeheerScherm2Controller {

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

    public void initialize() {
        initTable();
        btnAdd.setOnAction(e -> addNewRow());
        btnModify.setOnAction(e -> {
            verifyOneRowSelected();
            modifyCurrentRow();
        });
        btnDelete.setOnAction(e -> {
            verifyOneRowSelected();
            deleteCurrentRow();
        });
        
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }

    private void initTable() {
        tblConfigs.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigs.getColumns().clear();


        int colIndex = 0;
        for(var colName : new String[]{"museumID", "Naam", "Adres"}) {
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(colName);
            final int finalColIndex = colIndex;
            col.setCellValueFactory(f -> new ReadOnlyObjectWrapper<>(f.getValue().get(finalColIndex)));
            tblConfigs.getColumns().add(col);
            colIndex++;
        }
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.dbproject");
        var entityManager = sessionFactory.createEntityManager();
        MuseumRepository museumRepository = new MuseumRepository(entityManager);
        List<Museum> musea = museumRepository.findAll();
        for(int i = 0; i <= musea.size()-1; i++) {
            tblConfigs.getItems().add(FXCollections.observableArrayList(String.valueOf(musea.get(i).getMuseumID()), musea.get(i).getNaam(), musea.get(i).getAdres() ));
        }
    }

    private void addNewRow() {
        try {
            Stage currentStage = (Stage) btnAdd.getScene().getWindow();
            currentStage.close();
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("museumToevoeging.fxml"));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Toevoeging van een museum");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException("Kan museumToevoeging.fxml niet vinden", e);
        }
    }

    private void deleteCurrentRow() {
        ObservableList<ObservableList<String>> selectedRows = tblConfigs.getSelectionModel().getSelectedItems();

        ButtonType nietVerwijderenButton = new ButtonType("Niet verwijderen", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Vraag om bevestiging met twee knoppen
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bevestig verwijdering");
        alert.setHeaderText("Ben je zeker dat je de geselecteerde musea wilt verwijderen?");
        alert.setContentText("Deze actie kan niet ongedaan worden gemaakt.");
        alert.getButtonTypes().setAll(ButtonType.OK, nietVerwijderenButton);

        // Haal het resultaat op (OK of Niet verwijderen)
        var result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            for (ObservableList<String> row : selectedRows) {
                int museumID = Integer.parseInt(row.get(0));
                var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.dbproject");
                var entityManager = sessionFactory.createEntityManager();
                MuseumRepository museumRepository = new MuseumRepository(entityManager);
                museumRepository.deleteByID(museumID);
            }
            refreshCurrentStage();
        }
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

    private void verifyOneRowSelected() {
        if(tblConfigs.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Eerst een record selecteren hé.");
        }
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
}
