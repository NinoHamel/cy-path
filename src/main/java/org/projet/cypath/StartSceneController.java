package org.projet.cypath;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class StartSceneController {
    private MainGame mainGame;

    @FXML
    private ComboBox<Integer> numPlayersComboBox;

    public void setMainGame(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    @FXML
    private void handleStartButtonAction(ActionEvent event) throws IOException {
        mainGame.showGameScene();
    }

    public void initialize() {
        numPlayersComboBox.setItems(FXCollections.observableArrayList(2,3, 4));
    }
}