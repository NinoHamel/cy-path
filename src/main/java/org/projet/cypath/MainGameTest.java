package org.projet.cypath;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainGameTest extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        showStartScene();
    }

    public void showStartScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("start_scene.fxml"));
        primaryStage.setTitle("Game Start");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void showGameScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("game_scene.fxml"));
        primaryStage.setTitle("Game");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void showEndScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("end_scene.fxml"));
        primaryStage.setTitle("Game Over");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void showSaveLoadScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("save_load_scene.fxml"));
        primaryStage.setTitle("Save & Load");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
