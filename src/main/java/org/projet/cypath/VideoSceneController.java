package org.projet.cypath;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.Objects;

public class VideoSceneController {

    private MainGame mainGame;
    /**
     * Sets the MainGame instance for the start scene.
     * @param mainGame The MainGame instance to set.
     */
    public void setMainGame(MainGame mainGame) {
        this.mainGame = mainGame;
    }
    /**
     * Creates and returns the start scene.
     * @return The created Scene object.
     */
    public Scene start() {
        StackPane root = new StackPane();

        String videoPath = Objects.requireNonNull(getClass().getResource("/org/projet/cypath/cypath.mp4")).toExternalForm();
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        mediaView.setFitWidth(1422);
        mediaView.setFitHeight(800);
        mediaView.setPreserveRatio(true);

        root.getChildren().add(mediaView);

        mediaPlayer.setAutoPlay(true);

        mediaPlayer.setOnError(() -> System.out.println("MediaPlayer Error: " + mediaPlayer.getError()));

        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.stop();

            Platform.runLater(() -> {
                try {
                    mainGame.switchScene(mainGame.showStartScene());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

        Scene scene = new Scene(root, 1422, 800);
        return scene;
    }
}
