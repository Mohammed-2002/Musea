package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.model.BetaalMethode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class bezoekToevoegingController implements Initializable {

    @FXML
    private ComboBox selecteerBetaalmethode;
    private ComboBox selecteerBoek;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<BetaalMethode> list = FXCollections.observableArrayList(BetaalMethode.values());
        selecteerBetaalmethode.setItems(list);
    }

    public void selectpayment(javafx.event.ActionEvent actionEvent) {
        String selected = selecteerBetaalmethode.getSelectionModel().getSelectedItem().toString();
        BetaalMethode betaalMethode = BetaalMethode.valueOf(selected);
        System.out.println(betaalMethode);
    }

    public void selectbook(javafx.event.ActionEvent actionEvent) {
        String selected = selecteerBetaalmethode.getSelectionModel().getSelectedItem().toString();
        BetaalMethode betaalMethode = BetaalMethode.valueOf(selected);
        System.out.println(betaalMethode);
    }
}