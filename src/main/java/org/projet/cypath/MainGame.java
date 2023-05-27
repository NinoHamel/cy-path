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
     * Displays the Save & Load scene in the application.
     * @return The scene object for the Save & Load scene.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    public Scene showSaveLoadScene() throws IOException {
        // Load the FXML file for the Save & Load scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("save_load_scene.fxml"));
        Parent root = loader.load();

        // Retrieve the controller instance from the loader
        SaveLoadSceneController controller = loader.getController();

        // Set the MainGame instance for the Save & Load scene controller
        controller.setMainGame(this);

        // Set the previous scene as the current scene of the primary stage
        controller.setPreviousScene(primaryStage.getScene());

        // Set the title for the primary stage
        primaryStage.setTitle("Save & Load");

        // Create a new scene with the loaded FXML root
        Scene scene = new Scene(root);

        return scene;
    }

    /**
     * Start the game on the StartScene
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws InvalidSceneException, IOException {
        this.primaryStage = stage;
        showVideoScene();
    }
    /**
     * Displays the video scene in the application.
     * @throws InvalidSceneException If no scene is defined.
     */
    public void showVideoScene() throws InvalidSceneException {
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
        primaryStage.setScene(scene);
        primaryStage.show();
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
     * @throws InvalidSceneException
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
        switchScene(scene);
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



    /**
     * Switches the current scene of the application to the specified new scene.
     * @param newScene The new scene to switch to.
     */
    public void switchScene(Scene newScene) {
        if (primaryStage.getScene() == null) {
            // If there is no current scene, set the new scene directly and apply a fade-in transition
            primaryStage.setScene(newScene);

            FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(1), newScene.getRoot());
            fadeInTransition.setFromValue(0.0);
            fadeInTransition.setToValue(1.0);
            fadeInTransition.play();
        } else {
            // If there is a current scene, apply a fade-out transition first, then set the new scene and apply a fade-in transition
            FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0), primaryStage.getScene().getRoot());
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


    /**
     * number of players
     */
    private int numPlayers;

    /**
     * setter or the number of players
     * @param numPlayers int corresponding to the number of players
     */
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

