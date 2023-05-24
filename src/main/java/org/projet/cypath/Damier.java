package org.projet.cypath;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.projet.cypath.exceptions.InvalidWallException;
import org.projet.cypath.exceptions.OutOfBoardException;
import org.projet.cypath.players.Player;
import org.projet.cypath.tools.Box;
import org.projet.cypath.tools.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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
        Scene scene = new Scene(gridPane, 600, 600);
        primaryStage.setTitle("Damier");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createDamier() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        AtomicInteger index = new AtomicInteger();
        List<Player> listOnGoing = game.getListOnGoing();
        List<Player> listWinners = game.getListWinners();

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHalignment(HPos.CENTER);


        AtomicReference<Boolean> actionBouger = new AtomicReference<>(true);
        AtomicReference<Boolean> actionMur = new AtomicReference<>(false);
        AtomicReference<Boolean> murHorizontal = new AtomicReference<>(false);
        AtomicReference<Boolean> murVertical = new AtomicReference<>(false);

        //AtomicReference<Boolean> poseMur= new AtomicReference<>(false);

        for (int row = 0; row < DAMIER_SIZE; row++) {
            for (int col = 0; col < DAMIER_SIZE; col++) {
                StackPane cellPane = new StackPane();
                cellPane.setPrefSize(50, 50);

                Rectangle rect = new Rectangle(46, 46);
                rect.setFill((row + col) % 2 == 0 ? Color.WHITE : Color.BLACK);

                cellPane.getChildren().add(rect);
                gridPane.add(cellPane, col, row);
                // Créer des bordures blanches
                BorderStroke whiteRightBorderStroke = new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 2, 0, 0));
                BorderStroke whiteTopBorderStroke = new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 0, 0));
                BorderStroke whiteLeftBorderStroke = new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 0, 0));
                BorderStroke whiteBottomBorderStroke = new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 2, 0));
                Border whiteBorder = new Border(
                        whiteTopBorderStroke,
                        whiteRightBorderStroke,
                        whiteBottomBorderStroke,
                        whiteLeftBorderStroke
                );
                // Appliquer la bordure à la cellule
                cellPane.setBorder(whiteBorder);

                final int currentRow = row;
                final int currentCol = col;

                AtomicReference<Border> savedBorder = new AtomicReference<>(whiteBorder);
                AtomicReference<Border> savedBottomCellBorder = new AtomicReference<>(whiteBorder);
                AtomicReference<Border> savedRightCellBorder = new AtomicReference<>(whiteBorder);


                cellPane.setOnMouseClicked(event -> {
                    if (actionBouger.get() == true) {
                        try {
                            Player currentPlayer = listOnGoing.get(index.get());
                            int fromRow = currentPlayer.getCurrentBox().getRow();
                            int fromCol = currentPlayer.getCurrentBox().getColumn();
                            List<Box> victoryBox = currentPlayer.getVictoryBoxes();
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
                                // Modifier la couleur de la case d'origine de l'ancienne position
                                StackPane fromCellPane = (StackPane) gridPane.getChildren().get(fromRow * DAMIER_SIZE + fromCol);
                                Rectangle fromRect = (Rectangle) fromCellPane.getChildren().get(0);
                                fromRect.setFill((fromRow + fromCol) % 2 == 0 ? Color.WHITE : Color.BLACK);
                                currentPlayer.moveTo(game.getBoard().getBox(currentRow, currentCol));
                                int toRow = currentPlayer.getCurrentBox().getRow();
                                int toCol = currentPlayer.getCurrentBox().getColumn();
                                // Modifier la couleur de la nouvelle case de la nouvelle position
                                StackPane toCellPane = (StackPane) gridPane.getChildren().get(toRow * DAMIER_SIZE + toCol);
                                Rectangle toRect = (Rectangle) toCellPane.getChildren().get(0);
                                toRect.setFill(Color.web(color));
                                //Vérifier si le joueur a gagné
                                for (Box boxVictory : victoryBox) {
                                    int victoryRow = boxVictory.getRow();
                                    int victoryCol = boxVictory.getColumn();
                                    if (victoryCol == toCol && victoryRow == toRow) {
                                        currentPlayer.setVictory(true);
                                        listWinners.add(currentPlayer);
                                        listOnGoing.remove(currentPlayer);
                                        index.set(index.get() - 1);
                                        if (index.get() < 0) {
                                            index.set(listOnGoing.size() - 1);
                                        }
                                    }
                                }
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
                               // System.out.println(game.getBoard().displayBoard());
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    if (actionMur.get() == true) {
                       // System.out.println("actionMur.get()");
                        try {
                            Box currentBox = game.getBoard().getBox(currentRow, currentCol);
                           // System.out.println("try");
                            if (murHorizontal.get() == true && game.getBoard().canSetWall(currentBox, 0)) {
                                //System.out.println("Avant HasPath");
                                if (HasPath(game, currentRow, currentCol, 0)) {
                                    System.out.println("Avant setBottomWall");
                                    game.getBoard().setBottomWall(currentRow, currentCol);
                                    System.out.println("Après setBottomWall Avant Index:"+index);
                                    index.getAndIncrement();
                                    if (index.get() >= listOnGoing.size()) {
                                        index.set(0);
                                    }
                                    System.out.println("Après Index:"+index);
                                }
                            }
                            if (murVertical.get() == true && game.getBoard().canSetWall(currentBox, 1)) {
                                if (HasPath(game, currentRow, currentCol, 1)) {
                                    System.out.println("Avant setRightWall");
                                    game.getBoard().setRightWall(currentRow, currentCol);
                                    System.out.println("Après setRightWall Avant Index:"+index);
                                    index.getAndIncrement();
                                    if (index.get() >= listOnGoing.size()) {
                                        index.set(0);
                                    }
                                    System.out.println("Après Index:"+index);
                                }
                            }
                            System.out.println(game.getBoard().displayBoard());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                      //  System.out.println("index:"+index);
                    }
                });

                cellPane.setOnMouseEntered(event -> {
                    try {
                        //System.out.println("mouseEntered CurrentRow:" + currentRow + ";" + currentCol);
                        Box currentBox = game.getBoard().getBox(currentRow, currentCol);
                        if (actionMur.get() == true) {
                            if (murHorizontal.get() == true && game.getBoard().canSetWall(currentBox, 0)) {
                                StackPane rightCell = (StackPane) gridPane.getChildren().get(DAMIER_SIZE * currentRow + (currentCol + 1));

                                savedBorder.set(cellPane.getBorder());
                                savedRightCellBorder.set(rightCell.getBorder());
                                Border currentBorder = cellPane.getBorder();
                                Border rightBorder = ((StackPane) gridPane.getChildren().get(DAMIER_SIZE * currentRow + (currentCol + 1))).getBorder();
                                //System.out.println("currentBorder:" + currentBorder);
                                Border newBorder = addBottomBorder(currentBorder);
                                cellPane.setBorder(newBorder);
                                //System.out.println("right:" + newBorder);
                                Border newRightBorder = addBottomBorder(rightBorder);
                                rightCell.setBorder(newRightBorder);
                            }
                            if (murVertical.get() == true && game.getBoard().canSetWall(currentBox, 1)) {
                                StackPane bottomCell = (StackPane) gridPane.getChildren().get(DAMIER_SIZE * (currentRow + 1) + (currentCol));

                                savedBorder.set(cellPane.getBorder());
                                savedBottomCellBorder.set(bottomCell.getBorder());

                                Border currentBorder = cellPane.getBorder();
                                Border rightCellBorder = ((StackPane) gridPane.getChildren().get(DAMIER_SIZE * (currentRow + 1) + (currentCol))).getBorder();

                                Border newBorder = addRightBorder(currentBorder);
                                cellPane.setBorder(newBorder);
                                Border newBottomBorder = addRightBorder(rightCellBorder);
                                bottomCell.setBorder(newBottomBorder);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                });
                cellPane.setOnMouseExited(event -> {
                   // System.out.println("mouseExited CurrentRow:" + currentRow + ";" + currentCol);

                    try {
                        Box currentBox = game.getBoard().getBox(currentRow, currentCol);
                        //System.out.println("currentBox:" + currentBox);
                        // Vérifiez si une bordure à droite peut être supprimée
                        if (murHorizontal.get() && game.getBoard().canSetWall(currentBox, 0)) {
                            StackPane rightCell = (StackPane) gridPane.getChildren().get(DAMIER_SIZE * currentRow + (currentCol + 1));
                            cellPane.setBorder(savedBorder.get());
                            rightCell.setBorder(savedRightCellBorder.get());
                        }
                        if (murVertical.get() && game.getBoard().canSetWall(currentBox, 1)) {
                            StackPane bottomCell = (StackPane) gridPane.getChildren().get(DAMIER_SIZE * (currentRow + 1) + (currentCol));
                            cellPane.setBorder(savedBorder.get());
                            bottomCell.setBorder(savedBottomCellBorder.get());
                        }

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                });

            }
        }

        //Initialisation de l'affichage des joueurs
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
        //Initialisation des bouttons
        gridPane.getColumnConstraints().add(columnConstraints);
        Button bougerButton = new Button("Bouger");
        gridPane.add(bougerButton, DAMIER_SIZE, 0);
        Button placeMursButton = new Button("Placer murs");
        gridPane.add(placeMursButton, DAMIER_SIZE, 1);
        Button murHorizontalButton = new Button("murHorizontal");
        Button murVerticalButton = new Button("murVertical");
        placeMursButton.setOnAction(event -> {
            actionBouger.set(false);
            actionMur.set(true);
            gridPane.add(murHorizontalButton, DAMIER_SIZE, 2);
            gridPane.add(murVerticalButton, DAMIER_SIZE, 3);
            murHorizontal.set(false);
            murVertical.set(false);
            if (!actionBouger.get()) {
                try {
                    Player currentPlayer = listOnGoing.get(index.get());
                    List<Box> possibleMove = currentPlayer.possibleMove(game.getBoard());
                    int playerRow = currentPlayer.getCurrentBox().getRow();
                    int playerColumn = currentPlayer.getCurrentBox().getColumn();
                    // Enlever les couleurs des cases de mouvement possible de l'ancien joueur
                    for (Box box : possibleMove) {
                        int moveRow = box.getRow();
                        int moveCol = box.getColumn();
                        StackPane moveCellPane = (StackPane) gridPane.getChildren().get(moveRow * DAMIER_SIZE + moveCol);
                        Rectangle moveRect = (Rectangle) moveCellPane.getChildren().get(0);
                        moveRect.setFill((moveRow + moveCol) % 2 == 0 ? Color.WHITE : Color.BLACK);
                    }
                } catch (OutOfBoardException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        bougerButton.setOnAction(event -> {
            actionBouger.set(true);
            actionMur.set(false);
            gridPane.getChildren().remove(murHorizontalButton);
            gridPane.getChildren().remove(murVerticalButton);
            murHorizontal.set(false);
            murVertical.set(false);
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
            } catch (OutOfBoardException e) {
                throw new RuntimeException(e);
            }
        });
        murHorizontalButton.setOnAction(event -> {
            murHorizontal.set(true);
            murVertical.set(false);
        });
        murVerticalButton.setOnAction(event -> {
            murHorizontal.set(false);
            murVertical.set(true);
        });


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

    private Border addBottomBorder(Border border) {
        //System.out.println(border.getStrokes().size());
        // Récupérer la bordure existante
        BorderStroke borderStrokeTop = border.getStrokes().get(0);
        BorderStroke borderStrokeRight = border.getStrokes().get(1);
        BorderStroke borderStrokeBottom = border.getStrokes().get(2);
        BorderStroke borderStrokeLeft = border.getStrokes().get(3);
        // Récupérer les propriétés de la bordure existante
        BorderStrokeStyle borderLineStyle = BorderStrokeStyle.SOLID;
        //Bottom
        BorderStroke newBorderStrokeBottom = new BorderStroke(
                Color.GRAY,
                borderLineStyle,
                borderStrokeBottom.getRadii(),
                new BorderWidths(0, 0, 4, 0)
        );
        //System.out.println("borderStroke.getLeftStroke():" + "GRAY");
        // Créer une nouvelle bordure en combinant les bordures individuelles
        Border newBorder = new Border(
                borderStrokeTop,
                borderStrokeRight,
                newBorderStrokeBottom,
                borderStrokeLeft
        );
        return newBorder;
    }

    private Border addRightBorder(Border border) {
        //System.out.println(border.getStrokes().size());
        // Récupérer la bordure existante
        BorderStroke borderStrokeTop = border.getStrokes().get(0);
        BorderStroke borderStrokeRight = border.getStrokes().get(1);
        BorderStroke borderStrokeBottom = border.getStrokes().get(2);
        BorderStroke borderStrokeLeft = border.getStrokes().get(3);
        // Récupérer les propriétés de la bordure existante
        BorderStrokeStyle borderLineStyle = BorderStrokeStyle.SOLID;
        //Right
        BorderStroke newBorderStrokeRight = new BorderStroke(
                Color.GRAY,
                borderLineStyle,
                borderStrokeBottom.getRadii(),
                new BorderWidths(0, 4, 0, 0)
        );
        //System.out.println("borderStroke.getLeftStroke():" + "GRAY");
        // Créer une nouvelle bordure en combinant les bordures individuelles
        Border newBorder = new Border(
                borderStrokeTop,
                newBorderStrokeRight,
                borderStrokeBottom,
                borderStrokeLeft
        );
        return newBorder;
    }
    /**
     *
     * @param game
     * @param currentRow
     * @param currentCol
     * @param  orientation is the orientation of the box, 0 for bottom and 1 for right
     * @return
     * @throws InvalidWallException
     * @throws OutOfBoardException
     */
    public Boolean HasPath(Game game,int currentRow,int currentCol,int orientation) throws InvalidWallException, OutOfBoardException {
        List<Player> listOnGoing = game.getListOnGoing();
        if (orientation == 0) {
            game.getBoard().setBottomWall(currentRow,currentCol,true);
            game.getBoard().setBottomWall(currentRow,currentCol,false);
            for (Player player : listOnGoing) {
                if (!game.getBoard().hasPath(player)) {
                    game.getBoard().setBottomWall(currentRow,currentCol,false);
                    return false;
                }
            };
            game.getBoard().setBottomWall(currentRow,currentCol,false);
            return true;
        }
        else {
            game.getBoard().setRightWall(currentRow, currentCol,true);
            System.out.println(game.getBoard());
            for (Player player : listOnGoing) {
                if (!game.getBoard().hasPath(player)) {
                    game.getBoard().setRightWall(currentRow,currentCol,false);
                    return false;
                }
            }
            game.getBoard().setRightWall(currentRow,currentCol,false);
            return true;
        }
    }
}
