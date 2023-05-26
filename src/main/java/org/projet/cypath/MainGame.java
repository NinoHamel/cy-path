package org.projet.cypath;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.projet.cypath.exceptions.InvalidSceneException;

import java.io.IOException;

public class MainGame extends Application {
    private Stage primaryStage;

    /**
     * Start the game on the StartScene
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws InvalidSceneException {
        this.primaryStage = stage;
        switchScene(showVideoScene());
    }


    public Scene showVideoScene() throws InvalidSceneException {
        VideoSceneController videoController = new VideoSceneController();
        videoController.setMainGame(this);
        primaryStage.setTitle("Intro");
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(720);
        primaryStage.setWidth(1720);
        primaryStage.setHeight(720);
        Scene scene = videoController.start();
        if (scene == null) {
            throw new InvalidSceneException("No scene defined");
        }
        return scene;
    }

    /**
     * Get the fxml, launch the controller, and set properties to the stage (name / size)
     *
     * @return
     * @throws IOException
     */
    public Scene showStartScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start_scene.fxml"));
        Parent root = loader.load();
        StartSceneController controller = loader.getController();
        controller.setMainGame(this);
        primaryStage.setTitle("Game Start");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        return scene;
    }

    /**
     * Get the fxml, and launch the GameScene, with its controller
     * @throws IOException
     */
    public Scene showGameScene() throws InvalidSceneException {
        GameSceneController gameController = new GameSceneController(numPlayers);
        gameController.setMainGame(this);
        primaryStage.setTitle("Game");
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(800);
        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        Scene scene = gameController.start();
        if (scene == null) {
            throw new InvalidSceneException("No scene defined");
        }
        return scene;
    }


    /**
     * Get the fxml, and launch the EndScene, with its controller
     * @throws IOException
     */
    public void showEndScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("end_scene.fxml"));
        Parent root = loader.load();
        EndSceneController controller = loader.getController();
        controller.setMainGame(this);
        primaryStage.setTitle("Game Over");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public Scene showSaveLoadScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("save_load_scene.fxml"));
        Parent root = loader.load();
        SaveLoadSceneController controller = loader.getController();
        controller.setMainGame(this);
        primaryStage.setTitle("Save & Load");
        Scene scene = new Scene(root);
        return scene;
    }

    public void switchScene(Scene newScene) {
        if (primaryStage.getScene() == null) {
            primaryStage.setScene(newScene);

            FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(1), newScene.getRoot());
            fadeInTransition.setFromValue(0.0);
            fadeInTransition.setToValue(1.0);
            fadeInTransition.play();
        } else {
            FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(1), primaryStage.getScene().getRoot());
            fadeOutTransition.setFromValue(1.0);
            fadeOutTransition.setToValue(0.0);

            fadeOutTransition.setOnFinished((ActionEvent event) -> {
                primaryStage.setScene(newScene);

                FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(1), newScene.getRoot());
                fadeInTransition.setFromValue(0.0);
                fadeInTransition.setToValue(1.0);
                fadeInTransition.play();
            });

            fadeOutTransition.play();
        }
    }

    private int numPlayers;
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    /**
     * Start the game.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}

