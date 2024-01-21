package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.model.BetaalMethode;
import be.kuleuven.dbproject.model.Boek;
import be.kuleuven.dbproject.model.Museum;
import be.kuleuven.dbproject.repository.BoekRepository;
import be.kuleuven.dbproject.repository.MuseumRepository;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class bezoekToevoegingController{

    @FXML
    public TableView boekTableView;
    @FXML
    public TableColumn idBookTableColumn;
    @FXML
    public TableColumn naamBookTableColumn;
    @FXML
    public TableColumn auteurBookTableColumn;
    @FXML
    public TableColumn publicatiejaarBookTableColumn;
    @FXML
    public TableColumn uitgeverBookTableColumn;
    @FXML
    public TableColumn uitgeleendBookTableColumn;
    @FXML
    private ComboBox selecteerBetaalmethode;

    @FXML
    private TextField zoekBoekTextBar;

    SharedData sharedData = SharedData.getInstance();

    ObservableList<Boek> boeklijsttable = FXCollections.observableArrayList();


    public void initialize() {
        ObservableList<BetaalMethode> list = FXCollections.observableArrayList(BetaalMethode.values());

        selecteerBetaalmethode.setItems(list);

        initTable(sharedData.getMuseum().getBoeken());

        BoekRepository boekRepository = new BoekRepository(sharedData.getEntityManager());
        zoekBoekTextBar.textProperty().addListener((observable, oldValue, newValue) ->{
            if (newValue.isEmpty() || newValue.isBlank() || newValue == null ){
                initTable(sharedData.getMuseum().getBoeken());
            }
            else{
                initTable(boekRepository.findByPartialNameInMuseum(newValue, sharedData.getMuseum()));
            }
        });
    }


    public void selecteerBetaalmethode(ActionEvent actionEvent) {
        String selected = selecteerBetaalmethode.getSelectionModel().getSelectedItem().toString();
        BetaalMethode betaalMethode = BetaalMethode.valueOf(selected);
        System.out.println(betaalMethode);
    }

    private void initTable(List<Boek> boeken) {

        boekTableView.getItems().clear();
        boekTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        boekTableView.getColumns().clear();

        int colIndex = 0;
        for(var colName : new String[]{"ID", "Naam", "Auteur", "Jaar", "Uitgever", "Uitgeleend"}) {
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(colName);
            final int finalColIndex = colIndex;
            col.setCellValueFactory(f -> new ReadOnlyObjectWrapper<>(f.getValue().get(finalColIndex)));
            boekTableView.getColumns().add(col);
            colIndex++;
        }


        for(int i = 0; i <= boeken.size()-1; i++) {
            boekTableView.getItems().add(FXCollections.observableArrayList(String.valueOf(boeken.get(i).getBoekID()), boeken.get(i).getNaam(),
                    boeken.get(i).getAuteur(), String.valueOf(boeken.get(i).getPublicatiejaar()), boeken.get(i).getUitgever(),
                    String.valueOf(boeken.get(i).isUitgeleend())));
        }
    }

}

