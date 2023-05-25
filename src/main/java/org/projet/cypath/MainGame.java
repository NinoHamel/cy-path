package org.projet.cypath;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.projet.cypath.exceptions.InvalidSceneException;

import java.io.IOException;
import java.util.Objects;

public class MainGame extends Application {
    private Stage primaryStage;

    /**
     * Start the game on the StartScene
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        showStartScene();
    }

    /**
     * Get the fxml, launch the controller, and set properties to the stage (name / size)
     * @throws IOException
     */
    public void showStartScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start_scene.fxml"));
        Parent root = loader.load();
        StartSceneController controller = loader.getController();
        controller.setMainGame(this);
        primaryStage.setTitle("Game Start");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(720);
        primaryStage.setWidth(1720);
        primaryStage.setHeight(720);
        primaryStage.show();
    }

    /**
     * Get the fxml, and launch the GameScene, with its controller
     * @throws IOException
     */
    public void showGameScene() throws InvalidSceneException {
        GameSceneController gameController = new GameSceneController(numPlayers);
        gameController.setMainGame(this);
        Scene scene = gameController.start();
        if (scene != null) {
            primaryStage.setTitle("Game");
            primaryStage.setMinWidth(1200);
            primaryStage.setMinHeight(800);
            primaryStage.setWidth(1200);
            primaryStage.setHeight(800);
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            throw new InvalidSceneException("No scene defined");
        }
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

    public void showSaveLoadScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("save_load_scene.fxml"));
        Parent root = loader.load();
        SaveLoadSceneController controller = loader.getController();
        controller.setMainGame(this);
        primaryStage.setTitle("Save & Load");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
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

