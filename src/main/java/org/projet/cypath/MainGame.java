package org.projet.cypath;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.projet.cypath.jfx.EndScene;
import org.projet.cypath.jfx.GameScene;
import org.projet.cypath.jfx.SaveLoadScene;
import org.projet.cypath.jfx.StartScene;

public class MainGame extends Application {
    private Game game; // Votre classe de logique de jeu
    private GameScene gameScene; // Votre scèn;e de jeu
    private StartScene startScene; // Votre scène de démarrage
    private EndScene endScene; // Votre scène de fin de jeu
    private SaveLoadScene saveLoadScene; // Votre scène de sauvegarde/chargement

    @Override
    public void start(Stage stage) {
        // Instanciez votre jeu et vos scènes
        game = new Game();
        gameScene = new GameScene(game);
        startScene = new StartScene(game);
        endScene = new EndScene(game);
        saveLoadScene = new SaveLoadScene(game);

        // Commencez par la scène de démarrage
        Scene scene = new Scene(startScene, 800, 600);
        stage.setScene(scene);
        stage.show();

        // Ajoutez des écouteurs d'événements pour passer à différentes scènes en fonction des actions de l'utilisateur
        startScene.getStartButton().setOnAction(e -> {
            stage.setScene(new Scene(gameScene, 800, 600));
        });

        gameScene.getEndButton().setOnAction(e -> {
            stage.setScene(new Scene(endScene, 800, 600));
        });

        gameScene.getSaveButton().setOnAction(e -> {
            stage.setScene(new Scene(saveLoadScene, 800, 600));
        });

        saveLoadScene.getLoadButton().setOnAction(e -> {
            stage.setScene(new Scene(gameScene, 800, 600));
        });

        // Et ainsi de suite pour les autres boutons/scènes...
    }

    public static void main(String[] args) {
        launch();
    }
}
