package org.projet.cypath;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class StartSceneController {
    private MainGameTest mainGame;

    public void setMainGame(MainGameTest mainGame) {
        this.mainGame = mainGame;
    }

    @FXML
    private void handleStartButtonAction(ActionEvent event) throws IOException {
        mainGame.showGameScene();
    }
}
