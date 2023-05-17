package org.projet.cypath;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        primaryStage.setMinWidth(1720);
        primaryStage.setMinHeight(720);
        primaryStage.show();
    }

    /**
     * Get the fxml, and launch the GameScene, with its controller
     * @throws IOException
     */
    public void showGameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game_scene.fxml"));
        Parent root = loader.load();
        GameSceneController controller = loader.getController();
        controller.setMainGame(this);
        primaryStage.setTitle("Game");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
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

    /**
     * Start the game.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}

