package org.projet.cypath;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.IOException;

public class VideoSceneController {

    private MainGame mainGame;

    public void setMainGame(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    public Scene start() {
        StackPane root = new StackPane();

        // Utilisez getResource pour obtenir le chemin correct du fichier.
        String videoPath = getClass().getResource("/org/projet/cypath/cypath.mp4").toExternalForm();
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Définir la taille du MediaView
        mediaView.setFitWidth(1720);
        mediaView.setFitHeight(720);
        mediaView.setPreserveRatio(true);

        root.getChildren().add(mediaView);

        mediaPlayer.setAutoPlay(true);

        mediaPlayer.setOnError(() -> System.out.println("MediaPlayer Error: " + mediaPlayer.getError()));

        // Passez à la scène de départ lorsque la vidéo est terminée.
        mediaPlayer.setOnEndOfMedia(() -> Platform.runLater(() -> {
            try {
                mainGame.switchScene(mainGame.showStartScene());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        return new Scene(root, 1720, 720);
    }

}

