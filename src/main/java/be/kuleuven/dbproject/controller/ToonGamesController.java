package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.model.BetaalMethode;
import be.kuleuven.dbproject.model.Boek;
import be.kuleuven.dbproject.model.Game;
import be.kuleuven.dbproject.model.Museum;
import be.kuleuven.dbproject.repository.BoekRepository;
import be.kuleuven.dbproject.repository.GameRepository;
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

public class ToonGamesController{

    @FXML
    public TableView gameTableView;


    @FXML
    private TextField zoekGameTextBar;

    SharedData sharedData = SharedData.getInstance();

    public void initialize() {

        initTable(sharedData.getMuseum().getGames());

        GameRepository gameRepository = new GameRepository(sharedData.getEntityManager());
        zoekGameTextBar.textProperty().addListener((observable, oldValue, newValue) ->{
            if (newValue.isEmpty() || newValue.isBlank() || newValue == null ){
                initTable(sharedData.getMuseum().getGames());
            }
            else{
                initTable(gameRepository.findByPartialNameInMuseum(newValue, sharedData.getMuseum()));
            }
        });
    }

    private void initTable(List<Game> games) {

        gameTableView.getItems().clear();
        gameTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        gameTableView.getColumns().clear();

        int colIndex = 0;
        for(var colName : new String[]{"ID", "Naam", "Developer", "Jaar", "Console", "Uitgeleend"}) {
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(colName);
            final int finalColIndex = colIndex;
            col.setCellValueFactory(f -> new ReadOnlyObjectWrapper<>(f.getValue().get(finalColIndex)));
            gameTableView.getColumns().add(col);
            colIndex++;
        }


        for(int i = 0; i <= games.size()-1; i++) {
            gameTableView.getItems().add(FXCollections.observableArrayList(String.valueOf(games.get(i).getGameID()), games.get(i).getNaam(),
                    games.get(i).getDeveloper(), String.valueOf(games.get(i).getPublicatiejaar()), games.get(i).getConsole(),
                    String.valueOf(games.get(i).isUitgeleend())));
        }
    }

}

