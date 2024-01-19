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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class toonMedewerkersController {


    @FXML
    private TableView tblConfigs;
    SharedData sharedData = SharedData.getInstance();

    public void initialize() {
        initTable();
    }

    private void initTable() {
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


}


