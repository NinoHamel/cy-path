package org.projet.cypath;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class StartSceneController {
    private MainGame mainGame;

    @FXML
    private ComboBox<Integer> numPlayersComboBox;
    @FXML
    private Pane logoView;

    public void setMainGame(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    @FXML
    private void handleStartButtonAction(ActionEvent event) throws IOException {
        mainGame.showGameScene();
    }

    public void initialize() {
        numPlayersComboBox.setItems(FXCollections.observableArrayList(2,3, 4));

        File file = new File("src/main/resources/org/projet/cypath/cypath.mp4");
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        logoView.getChildren().add(mediaView);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), logoView);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), logoView);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        mediaPlayer.setOnEndOfMedia(fadeOut::play);

        fadeOut.setOnFinished(event -> {
            logoView.setVisible(false);
            // Here you should add the code to show your player selection view.
        });

        mediaPlayer.play();
    }

}