package org.projet.cypath;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.projet.cypath.exceptions.InvalidSaveException;
import org.projet.cypath.exceptions.InvalidSceneException;
import org.projet.cypath.exceptions.OutOfBoardException;
import org.projet.cypath.tools.Game;

import java.io.IOException;

/**
 * MainGame is the main class of the application
 */
public class MainGame extends Application {
    /**
     * Primary stage of the application
     */
    private Stage primaryStage;
    /**
     * Start the game on the StartScene
     * @param stage stage of the scene
     * @throws InvalidSceneException if the scene is invalid
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
        primaryStage.setMinWidth(1420);
        primaryStage.setMinHeight(720);
        primaryStage.setWidth(1420);
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
     * @throws IOException if an I/O error occurs while loading the scene
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
     * Displays the game scene.
     * @return the Scene object representing the game scene
     * @throws InvalidSceneException if the scene is invalid
     * @throws OutOfBoardException if the scene contains objects placed outside the game board
     */
    public Scene showGameScene() throws InvalidSceneException, OutOfBoardException {
        GameSceneController gameController = new GameSceneController(numPlayers);
        gameController.setMainGame(this);
        primaryStage.setTitle("Game");
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(850);
        primaryStage.setWidth(1200);
        primaryStage.setHeight(850);
        gameController.load_walls();
        Scene scene = gameController.start();
        if (scene == null) {
            throw new InvalidSceneException("No scene defined");
        }
        switchScene(scene);
        return scene;
    }
    /**
     * Displays the game scene based on the provided file path.
     * @param filepath the file path of the game scene
     * @return the Scene object representing the game scene
     * @throws InvalidSceneException if the scene is invalid
     * @throws IOException if an I/O error occurs while loading the scene
     * @throws OutOfBoardException if the scene contains objects placed outside the game board
     */
    public Scene showGameScene(String filepath) throws InvalidSceneException, IOException, OutOfBoardException {

        GameSceneController gameController = new GameSceneController(numPlayers);
        gameController.setMainGame(this);

        primaryStage.setTitle("Game");
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(850);
        primaryStage.setWidth(1200);
        primaryStage.setHeight(850);
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
     * @return EndScene whene there only one player in listOnGoing
     * @throws IOException if an I/O error occurs while loading the scene
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
     * @throws InvalidSaveException If trying to save from a menu.
     */
    public Scene showSaveLoadScene() throws InvalidSaveException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("save_load_scene.fxml"));
        Parent root = loader.load();
        SaveLoadSceneController controller = loader.getController();
        controller.setMainGame(this);
        try{
            controller.setThisGame(this.game);
        }
        catch (Exception e){
            throw new InvalidSaveException();
        }
        controller.setPreviousScene(primaryStage.getScene());
        primaryStage.setTitle("Save and Load");
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(850);
        primaryStage.setWidth(1200);
        primaryStage.setHeight(850);
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
     * @param ThisGame the game to be bind
     */
    public void setThisGame(Game ThisGame){
        this.game = ThisGame;
    }
    /**
     * Start the game.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}

