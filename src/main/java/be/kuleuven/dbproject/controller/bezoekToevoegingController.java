package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.model.BetaalMethode;
import be.kuleuven.dbproject.model.Boek;
import be.kuleuven.dbproject.model.Museum;
import be.kuleuven.dbproject.repository.BoekRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class bezoekToevoegingController implements Initializable {

    @FXML
    public TableView boekTableView;
    @FXML
    public TableColumn idBookTableColumn;
    @FXML
    public TableColumn naamBookTableColumn;
    @FXML
    public TableColumn auteurBookTableColumn;
    @FXML
    public TableColumn jaarBookTableColumn;
    @FXML
    public TableColumn uitgeverBookTableColumn;
    @FXML
    public TableColumn uitgeleendBookTableColumn;
    @FXML
    private ComboBox selecteerBetaalmethode;
    @FXML
    private ComboBox selecteerBoek;

    ObservableList<Boek> boeklijsttable = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<BetaalMethode> list = FXCollections.observableArrayList(BetaalMethode.values());
        selecteerBetaalmethode.setItems(list);

        BoekRepository boekRepository = new BoekRepository(SharedData.getInstance().getEntityManager());
        ObservableList<Boek> boeklijst = FXCollections.observableArrayList(boekRepository.findAll());

        selecteerBoek.setItems(boeklijst);

        for (int i = 0; i < boeklijst.size(); i++) {
            Integer id = boeklijst.get(i).getBoekID();
            String naam = boeklijst.get(i).getNaam();
            String auteur = boeklijst.get(i).getAuteur();
            Integer jaar = boeklijst.get(i).getPublicatiejaar();
            String uitgever = boeklijst.get(i).getUitgever();
            Boolean uitgeleend = boeklijst.get(i).isUitgeleend();

            boeklijsttable.add(new Boek(naam, auteur, jaar, uitgever, null, 0.0));

        }
    }


    public void selecteerBetaalmethode(ActionEvent actionEvent) {
        String selected = selecteerBetaalmethode.getSelectionModel().getSelectedItem().toString();
        BetaalMethode betaalMethode = BetaalMethode.valueOf(selected);
        System.out.println(betaalMethode);
    }

    public void selectbook(ActionEvent actionEvent) {
    }
}
/**

    public void selectbook(javafx.event.ActionEvent actionEvent) {
        //String selected = selecteerBoek.getSelectionModel().getSelectedItem().toString();
        //System.out.println(selected);
       // BoekRepository boekRepository = new BoekRepository(SharedData.getInstance().getEntityManager());
       // List<Boek> boeklijst = boekRepository.findAll();
      //  System.out.println(boeklijst);
    }
 **/
