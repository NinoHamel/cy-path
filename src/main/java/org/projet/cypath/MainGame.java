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
import org.projet.cypath.exceptions.OutOfBoardException;
import org.projet.cypath.tools.Game;

import java.io.IOException;

public class MainGame extends Application {
    private Stage primaryStage;

    /**
     * Start the game on the StartScene
     * @param stage the input stage
     * @throws InvalidSceneException throw exception if the scene is invalid
     */
    @Override
    public void start(Stage stage) throws InvalidSceneException {
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
        primaryStage.setMinWidth(1720);
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
     * @return the created scene
     * @throws IOException input or output exception
     */
    public Scene showStartScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start_scene.fxml"));
        Parent root = loader.load();
        StartSceneController controller = loader.getController();
        controller.setMainGame(this);
        primaryStage.setTitle("Game Start");
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(850);
        primaryStage.setWidth(1200);
        primaryStage.setHeight(850);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        return scene;
    }

    /**
     * Get the fxml, and launch the GameScene, with its controller
     * @throws InvalidSceneException If no scene is defined.
     * @throws OutOfBoardException the exception when a position is out of the board
     */
    public Scene showGameScene() throws InvalidSceneException, OutOfBoardException {
        GameSceneController gameController = new GameSceneController(numPlayers);
        gameController.setMainGame(this);
        primaryStage.setTitle("Game");
        gameController.load_walls();
        Scene scene = gameController.start();
        if (scene == null) {
            throw new InvalidSceneException("No scene defined");
        }
        switchScene(scene);
        return scene;
    }

    /**
     * Get the fxml, launch the controller, and set properties to the stage (name / size)
     *
     * @return the created scene
     * @throws IOException input or output exception
     */
    public Scene showGameScene(String filepath) throws InvalidSceneException, IOException, OutOfBoardException {

        GameSceneController gameController = new GameSceneController(numPlayers);
        gameController.setMainGame(this);

        primaryStage.setTitle("Game");
        Scene scene = gameController.load(filepath);
        gameController.load_walls();
        if (scene == null) {
            throw new InvalidSceneException("No scene defined");
        }
        switchScene(scene);
        return scene;
    }


    /**
     * Get the fxml, and launch the EndScene, with its controller
     *
     * @return the created scene
     * @throws IOException input or output exception
     */
    public Scene showEndScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("end_scene.fxml"));
        Parent root = loader.load();
        EndSceneController controller = loader.getController();
        controller.setMainGame(this);
        controller.setGame(this.game);
        primaryStage.setTitle("Game Over");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        return scene;
    }

    /**
     * Displays the Save and Load scene in the application.
     * @return The scene object for the Save and Load scene.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    public Scene showSaveLoadScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("save_load_scene.fxml"));
        Parent root = loader.load();
        SaveLoadSceneController controller = loader.getController();
        controller.setMainGame(this);
        controller.setPreviousScene(primaryStage.getScene());
        primaryStage.setTitle("Save and Load");
        return new Scene(root);
    }

    /**
     * Switches the current scene of the application to the specified new scene.
     * @param newScene The new scene to switch to.
     */
    public void switchScene(Scene newScene) {
        if (primaryStage.getScene() == null) {
            primaryStage.setScene(newScene);

            FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(1), newScene.getRoot());
            fadeInTransition.setFromValue(0.0);
            fadeInTransition.setToValue(1.0);
            fadeInTransition.play();
        } else {
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
     * Create a game instance in the MainGame
     */
    private Game game;

    /**
     * Allow to bind a game instance from a controller to the MainGame
     * @param ThisGame a game instance
     */
    public void setThisGame(Game ThisGame){
        this.game = ThisGame;
    }

    /**
     * Start the game.
     * @param args arguments of main method
     */
    public static void main(String[] args) {
        launch(args);
    }
}

