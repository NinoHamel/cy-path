package org.projet.cypath;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.projet.cypath.exceptions.OutOfBoardException;
import org.projet.cypath.players.Player;
import org.projet.cypath.tools.Box;
import org.projet.cypath.tools.Game;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Damier extends Application {
    private static final int DAMIER_SIZE = 9;
    private Game game; // Ajout d'une variable pour stocker l'instance de jeu

    @Override
    public void start(Stage primaryStage) {
        try {
            game = new Game(4);
        } catch (Exception e) {
            System.out.println(e);
            return; // Quitte la méthode si une exception se produit lors de l'initialisation de game
        }

        GridPane gridPane = createDamier();
        Scene scene = new Scene(gridPane, 400, 400);
        primaryStage.setTitle("Damier");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createDamier() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        AtomicInteger index= new AtomicInteger();
        List<Player> listOnGoing=game.getListOnGoing();
        List<Player> listWinners=game.getListWinners();

        for (int row = 0; row < DAMIER_SIZE; row++) {
            for (int col = 0; col < DAMIER_SIZE; col++) {
                StackPane cellPane = new StackPane();
                cellPane.setPrefSize(50, 50);

                Rectangle rect = new Rectangle(50, 50);
                rect.setFill((row + col) % 2 == 0 ? Color.WHITE : Color.BLACK);

                cellPane.getChildren().add(rect);
                gridPane.add(cellPane, col, row);

                final int currentRow = row;
                final int currentCol = col;
                cellPane.setOnMouseClicked(event -> {
                    try {
                        Player currentPlayer = listOnGoing.get(index.get());
                        int fromRow = currentPlayer.getCurrentBox().getRow();
                        int fromCol = currentPlayer.getCurrentBox().getColumn();

                        String color = currentPlayer.getColor();

                        // Mise à jour des mouvements possibles pour le joueur en cours
                        List<Box> possibleMove = currentPlayer.possibleMove(game.getBoard());
                        // Vérifier si la case sélectionnée est une case de mouvement possible
                        boolean isValidMove = false;
                        for (Box box : possibleMove) {
                            int moveRow = box.getRow();
                            int moveCol = box.getColumn();
                            if (currentRow == moveRow && currentCol == moveCol) {
                                isValidMove = true;
                                break;
                            }
                        }
                        if (isValidMove) {
                            System.out.println(currentPlayer.getColor());
                            System.out.println(currentPlayer.getCurrentBox());
                            // Enlever les couleurs des cases de mouvement possible de l'ancien joueur
                            for (Box box : possibleMove) {
                                int moveRow = box.getRow();
                                int moveCol = box.getColumn();
                                StackPane moveCellPane = (StackPane) gridPane.getChildren().get(moveRow * DAMIER_SIZE + moveCol);
                                Rectangle moveRect = (Rectangle) moveCellPane.getChildren().get(0);
                                moveRect.setFill((moveRow + moveCol) % 2 == 0 ? Color.WHITE : Color.BLACK);
                            }
                            // Modifier la couleur de la case d'origine de l'ancien joueur
                            StackPane fromCellPane = (StackPane) gridPane.getChildren().get(fromRow * DAMIER_SIZE + fromCol);
                            Rectangle fromRect = (Rectangle) fromCellPane.getChildren().get(0);
                            fromRect.setFill((fromRow + fromCol) % 2 == 0 ? Color.WHITE : Color.BLACK);
                            currentPlayer.moveTo(game.getBoard().getBox(currentRow, currentCol));
                            int toRow = currentPlayer.getCurrentBox().getRow();
                            int toCol = currentPlayer.getCurrentBox().getColumn();
                            // Modifier la couleur de la nouvelle case du nouveau joueur
                            StackPane toCellPane = (StackPane) gridPane.getChildren().get(toRow * DAMIER_SIZE + toCol);
                            Rectangle toRect = (Rectangle) toCellPane.getChildren().get(0);
                            toRect.setFill(Color.web(color));
                            // Incrémenter l'index et revenir à 0 si on atteint la taille de listOnGoing
                            index.getAndIncrement();
                            if (index.get() >= listOnGoing.size()) {
                                index.set(0);
                            }
                            // Afficher les nouveaux déplacements possible
                            currentPlayer = listOnGoing.get(index.get());
                            possibleMove = currentPlayer.possibleMove(game.getBoard());
                            for (Box box : possibleMove) {
                                int moveRow = box.getRow();
                                int moveCol = box.getColumn();
                                StackPane moveCellPane = (StackPane) gridPane.getChildren().get(moveRow * DAMIER_SIZE + moveCol);
                                Rectangle moveRect = (Rectangle) moveCellPane.getChildren().get(0);
                                moveRect.setFill(Color.PURPLE);
                            }
                            System.out.println(game.getBoard().displayBoard());
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                });
            }
        }
        //Initialisation des déplacements possible
        try {
            Player currentPlayer = listOnGoing.get(index.get());
            List<Box> possibleMove = currentPlayer.possibleMove(game.getBoard());
            int playerRow = currentPlayer.getCurrentBox().getRow();
            int playerColumn = currentPlayer.getCurrentBox().getColumn();
            //if (currentRow == playerRow && currentCol == playerColumn) {
            for (Box box : possibleMove) {
                int moveRow = box.getRow();
                int moveCol = box.getColumn();
                StackPane moveCellPane = (StackPane) gridPane.getChildren().get(moveRow * DAMIER_SIZE + moveCol);
                Rectangle moveRect = (Rectangle) moveCellPane.getChildren().get(0);
                moveRect.setFill(Color.PURPLE);
            }
        }
        catch(OutOfBoardException e){
            throw new RuntimeException(e);
        }
        //Initialisation de l'affichage des joeurs
        for (Player player : listOnGoing) {
            int row = player.getCurrentBox().getRow();
            int column = player.getCurrentBox().getColumn();
            String color = player.getColor();
            // Récupérer la cellule correspondante dans la grille
            StackPane cellPane = (StackPane) gridPane.getChildren().get(row * DAMIER_SIZE + column);
            Rectangle rect = (Rectangle) cellPane.getChildren().get(0);
            // Appliquer la couleur spécifiée
            rect.setFill(Color.web(color));
        }
        return gridPane;
    }
    private void votreFonctionJava(int row, int col) throws OutOfBoardException {
        // Utilisez votre instance de jeu pour gérer les actions de l'utilisateur
        System.out.println(row + ";" + col);
        if (game != null) {
            try {
                System.out.println(game.getBoard().getBox(row, col));
            } catch (OutOfBoardException e) {
                throw e;
            }
        } else {
            System.out.println("Erreur : l'instance de jeu n'a pas été initialisée correctement.");
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
