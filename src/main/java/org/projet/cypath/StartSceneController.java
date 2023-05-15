package org.projet.cypath;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class StartSceneController {
    private MainGame mainGame;

    public void setMainGame(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    @FXML
    private void handleStartButtonAction(ActionEvent event) throws IOException {
        mainGame.showGameScene();
    }
}
