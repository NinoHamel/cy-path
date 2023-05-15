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

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        showStartScene();
    }

    public void showStartScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start_scene.fxml"));
        Parent root = loader.load();
        StartSceneController controller = loader.getController();
        controller.setMainGame(this);
        primaryStage.setTitle("Game Start");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public void showGameScene() throws IOException {
        FXMLLoader loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game_scene.fxml")));
        Parent root = loader.load();
        StartSceneController controller = loader.getController();
        controller.setMainGame(this);
        primaryStage.setTitle("Game");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void showEndScene() throws IOException {
        FXMLLoader loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("end_scene.fxml")));
        Parent root = loader.load();
        StartSceneController controller = loader.getController();
        controller.setMainGame(this);
        primaryStage.setTitle("Game Over");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void showSaveLoadScene() throws IOException {
        FXMLLoader loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("save_load_scene.fxml")));
        Parent root = loader.load();
        StartSceneController controller = loader.getController();
        controller.setMainGame(this);
        primaryStage.setTitle("Save & Load");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
