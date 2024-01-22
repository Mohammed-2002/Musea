package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.model.BetaalMethode;
import be.kuleuven.dbproject.model.Boek;
import be.kuleuven.dbproject.model.Game;
import be.kuleuven.dbproject.repository.BoekRepository;
import be.kuleuven.dbproject.repository.GameRepository;
import be.kuleuven.dbproject.repository.MuseumRepository;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class bezoekToevoegingController{

    @FXML
    public TableView boekTableView;
    @FXML
    public TableView gameTableView;

    @FXML
    public TableView winkelwagenTableView;

    @FXML
    private ComboBox selecteerBetaalmethode;

    @FXML
    private TextField zoekBoekTextBar;

    @FXML
    private TextField zoekGameTextBar;
    @FXML
    private TextField teBetalenBedrag;

    @FXML
    private Button btnSlaBezoekOp;

    @FXML
    private Button btnVoegBoek;
    @FXML
    private Button btnVoegGame;





    SharedData sharedData = SharedData.getInstance();




    public void initialize() {
        ObservableList<BetaalMethode> list = FXCollections.observableArrayList(BetaalMethode.values());

        selecteerBetaalmethode.setItems(list);

        initTableBoeken(sharedData.getMuseum().getBoeken());
        initTableGames(sharedData.getMuseum().getGames());

        GameRepository gameRepository = new GameRepository(sharedData.getEntityManager());
        zoekGameTextBar.textProperty().addListener((observable, oldValue, newValue) ->{
            if (newValue.isEmpty() || newValue.isBlank() || newValue == null ){
                initTableGames(sharedData.getMuseum().getGames());
            }
            else{
                initTableGames(gameRepository.findByPartialNameInMuseum(newValue, sharedData.getMuseum()));
            }
        });

        BoekRepository boekRepository = new BoekRepository(sharedData.getEntityManager());
        zoekBoekTextBar.textProperty().addListener((observable, oldValue, newValue) ->{
            if (newValue.isEmpty() || newValue.isBlank() || newValue == null ){
                initTableBoeken(sharedData.getMuseum().getBoeken());
            }
            else{
                initTableBoeken(boekRepository.findByPartialNameInMuseum(newValue, sharedData.getMuseum()));
            }
        });

        btnVoegGame.setOnAction(e -> {
            if(isOneGameSelected()){
                VoegGameToeAanWinkelwagen();
            }
        });

        btnVoegBoek.setOnAction(e -> {
            if(isOneBoekSelected()){
                VoegBoekToeAanWinkelwagen();
            }
        });

    }

    private void VoegBoekToeAanWinkelwagen() {
        ObservableList<String> selectedRow = (ObservableList<String>) boekTableView.getSelectionModel().getSelectedItem();


            int boekID = Integer.parseInt(selectedRow.get(0));
            BoekRepository boekRepository =new BoekRepository(sharedData.getEntityManager());
            Boek boek = boekRepository.findById(boekID);
            if(sharedData.getBezoek().getGeleendeBoeken().contains(boek)){
                showAlert("boek is al in uw winkelwagen", "het geselecteerde boek ia al in uw winkelwagen");
            }
            else if(boek.isUitgeleend()){
                showAlert("boek is uitgeleend", "het geselecteerde boek ia al uitgeleend");
            }else {
                sharedData.getBezoek().kenBoekenToe(List.of(boek));
                initTableWinkelwagen();
            }

    }

    private void VoegGameToeAanWinkelwagen() {

        ObservableList<String> selectedRow = (ObservableList<String>) gameTableView.getSelectionModel().getSelectedItem();


        int gameID = Integer.parseInt(selectedRow.get(0));
        GameRepository gameRepository =new GameRepository(sharedData.getEntityManager());
        Game game = gameRepository.findById(gameID);
        if(sharedData.getBezoek().getGeleendeBoeken().contains(game)){
            showAlert("game is al in uw winkelwagen", "het geselecteerde game ia al in uw winkelwagen");
        }
        else if(game.isUitgeleend()){
            showAlert("game is uitgeleend", "het geselecteerde game ia al uitgeleend");
        }else {
            sharedData.getBezoek().kenGamesToe(List.of(game));
            initTableWinkelwagen();
        }
    }


    public void selecteerBetaalmethode(ActionEvent actionEvent) {
        String selected = selecteerBetaalmethode.getSelectionModel().getSelectedItem().toString();
        BetaalMethode betaalMethode = BetaalMethode.valueOf(selected);
    }

    private void initTableBoeken(List<Boek> boeken) {

        boekTableView.getItems().clear();
        boekTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
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

    private void initTableGames(List<Game> games) {

        gameTableView.getItems().clear();
        gameTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        gameTableView.getColumns().clear();

        int colIndex = 0;
        for (var colName : new String[]{"ID", "Naam", "Developer", "Jaar", "Console", "Uitgeleend"}) {
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(colName);
            final int finalColIndex = colIndex;
            col.setCellValueFactory(f -> new ReadOnlyObjectWrapper<>(f.getValue().get(finalColIndex)));
            gameTableView.getColumns().add(col);
            colIndex++;
        }

        for (int i = 0; i <= games.size() - 1; i++) {
            gameTableView.getItems().add(FXCollections.observableArrayList(String.valueOf(games.get(i).getGameID()), games.get(i).getNaam(),
                    games.get(i).getDeveloper(), String.valueOf(games.get(i).getPublicatiejaar()), String.valueOf(games.get(i).getConsole()),
                    String.valueOf(games.get(i).isUitgeleend())));
        }
    }

    private void initTableWinkelwagen(){


        int colIndex = 0;
        for (var colName : new String[]{"ID", "Naam", "Type"}) {
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(colName);
            final int finalColIndex = colIndex;
            col.setCellValueFactory(f -> new ReadOnlyObjectWrapper<>(f.getValue().get(finalColIndex)));
            winkelwagenTableView.getColumns().add(col);
            colIndex++;
        }
        List<Game> games= sharedData.getBezoek().getGeleendeGames();
        List<Boek> boeken = sharedData.getBezoek().getGeleendeBoeken();
        int aantalGames  = sharedData.getBezoek().getGeleendeGames().size();
        int aantalBoeken = sharedData.getBezoek().getGeleendeBoeken().size();

        for (int i = 0; i <= aantalGames - 1; i++) {
            winkelwagenTableView.getItems().add(FXCollections.observableArrayList(String.valueOf(games.get(i).getGameID()), games.get(i).getNaam(), "Game"));
        }
        for (int i = 0; i <= aantalBoeken - 1; i++) {
            winkelwagenTableView.getItems().add(FXCollections.observableArrayList(String.valueOf(boeken.get(i).getBoekID()), boeken.get(i).getNaam(), "Boek"));
        }
    }

    private boolean isOneGameSelected() {
        if(gameTableView.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Fout!", "Selecteer een game");
        }
        return !(gameTableView.getSelectionModel().getSelectedCells().size() == 0);
    }
    private boolean isOneBoekSelected() {
        if(boekTableView.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Fout!", "Selecteer een boek");
        }
        return !(boekTableView.getSelectionModel().getSelectedCells().size() == 0);
    }

    public void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


}

