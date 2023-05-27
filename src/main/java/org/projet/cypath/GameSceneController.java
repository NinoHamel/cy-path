package org.projet.cypath;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.projet.cypath.exceptions.OutOfBoardException;
import org.projet.cypath.players.Player;
import org.projet.cypath.tools.Board;
import org.projet.cypath.tools.Box;
import org.projet.cypath.tools.Game;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


public class GameSceneController {
    private static final int checkerboard_SIZE = 9;
    private MainGame mainGame;
    private Game game;
    private final int numPlayers;
    private Text wall_remaining_hbox_text;

    public GameSceneController(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public void setMainGame(MainGame mainGame) {
        this.mainGame = mainGame;
    }
    /**
     Starts the game and returns the JavaFX Scene using {@link #createCheckerboard(VBox)},
     @return The JavaFX Scene representing the game.
     */
    public Scene start() {
        try {
            game = new Game(numPlayers);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        HBox double_Vbox = new HBox(); //create hbox
        VBox first_Vbox = new VBox(); //create vbox
        VBox second_Vbox = new VBox(); //create vbox
        HBox player_turn_hbox = new HBox();
        HBox wall_remaining_hbox = new HBox();

        GridPane checkerboard = createCheckerboard(second_Vbox); //create gridpane
        first_Vbox.getChildren().addAll(player_turn_hbox,checkerboard,wall_remaining_hbox); //first vbox filling
        double_Vbox.getChildren().addAll(first_Vbox,second_Vbox); //add gridpane and vbox to hbox

        //settings vbox and hbox
        double_Vbox.setSpacing(100);
        double_Vbox.setAlignment(Pos.CENTER);
        second_Vbox.setSpacing(50);
        second_Vbox.setPadding(new Insets(50, 0, 0, 0));
        second_Vbox.setAlignment(Pos.TOP_CENTER);
        first_Vbox.setSpacing(50);
        first_Vbox.setPadding(new Insets(50, 0, 0, 0));
        first_Vbox.setAlignment(Pos.TOP_CENTER);
        wall_remaining_hbox.setAlignment(Pos.CENTER);
        wall_remaining_hbox.setSpacing(20);
        player_turn_hbox.setAlignment(Pos.CENTER);
        player_turn_hbox.setSpacing(20);

        initialize_player_turn_hbox(player_turn_hbox);
        initialize_wall_remaining_hbox(wall_remaining_hbox);

        StackPane rootPane = new StackPane();
        rootPane.getChildren().add(double_Vbox);

        return new Scene(rootPane, 1200, 800);
    }

    private void initialize_player_turn_hbox(HBox player_hbox){
        ImageView player_turnImageView = new ImageView(Objects.requireNonNull(getClass().getResource("/org/projet/cypath/player.png")).toExternalForm());
        player_turnImageView.setFitHeight(80);
        player_turnImageView.setPreserveRatio(true);
        player_hbox.getChildren().add(player_turnImageView);

        Text player_turn_text = new Text();
        player_turn_text.setText(": ONE");
        player_turn_text.setStyle("-fx-font-size: 50");
        player_turn_text.setTextAlignment(TextAlignment.CENTER);
        player_hbox.getChildren().add(player_turn_text);
    }
    private void initialize_wall_remaining_hbox(HBox wall_remaining_hbox){
        ImageView wall_remainingImageView = new ImageView(Objects.requireNonNull(getClass().getResource("/org/projet/cypath/wall_remaining.png")).toExternalForm());
        wall_remainingImageView.setFitHeight(80);
        wall_remainingImageView.setPreserveRatio(true);
        wall_remaining_hbox.getChildren().add(wall_remainingImageView);

        wall_remaining_hbox_text = new Text();
        wall_remaining_hbox_text.setText(": "+ CounterRemainingWalls());
        wall_remaining_hbox_text.setStyle("-fx-font-size: 90");
        wall_remaining_hbox_text.setTextAlignment(TextAlignment.CENTER);
        wall_remaining_hbox.getChildren().add(wall_remaining_hbox_text);
    }

    /**
     Creates a GridPane representing the game board using:
     {@link #initializeGridPane()},
     {@link #setCellPaneClickHandler(StackPane, int, int, AtomicReference, AtomicReference, AtomicReference, AtomicReference, GridPane, List, List, AtomicInteger)},
     {@link #setCellPaneMouseEnterHandler(StackPane, int, int, AtomicReference, AtomicReference, AtomicReference, AtomicReference, AtomicReference, AtomicReference, GridPane)},
     {@link #setCellPaneMouseExitHandler(StackPane, int, int, AtomicReference, AtomicReference, AtomicReference, AtomicReference, AtomicReference, GridPane)},
     {@link #initializePlayers(GridPane, List)},
     {@link #initializeButtons(VBox, GridPane, AtomicReference, AtomicReference, AtomicReference, AtomicReference, List, AtomicInteger)},
     @return The created GridPane.
     */
    private GridPane createCheckerboard(VBox vbox_buttons) {
        GridPane gridPane = initializeGridPane();
        AtomicReference<Boolean> actionMove = new AtomicReference<>(false);
        AtomicReference<Boolean> actionWall = new AtomicReference<>(false);
        AtomicReference<Boolean> horizontalWall = new AtomicReference<>(false);
        AtomicReference<Boolean> verticalWall = new AtomicReference<>(false);
        AtomicInteger index = new AtomicInteger();
        List<Player> listOnGoing=game.getListOnGoing();
        List<Player> listWinners=game.getListWinners();
        for (int row = 0; row < checkerboard_SIZE; row++) {
            for (int col = 0; col < checkerboard_SIZE; col++) {
                // Créer des bordures blanches
                //Création de bordures avec des tailles nuls car si l'on fait uniquement avec des bordures bottom et right, si les 2 ont la même taille et la même couleur, les 2 parties des murs forment un carré
                BorderStroke whiteRightBorderStroke = new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 4, 0, 0));
                BorderStroke whiteTopBorderStroke = new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 0, 0));
                BorderStroke whiteLeftBorderStroke = new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 0, 0));
                BorderStroke whiteBottomBorderStroke = new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 4, 0));
                Border whiteBorder = new Border(
                        whiteTopBorderStroke,
                        whiteRightBorderStroke,
                        whiteBottomBorderStroke,
                        whiteLeftBorderStroke
                );
                StackPane cellPane = createCellPane(row, col);
                gridPane.add(cellPane, col, row);

                // Appliquer la bordure à la cellule
                cellPane.setBorder(whiteBorder);

                AtomicReference<Border> savedBorder = new AtomicReference<>(cellPane.getBorder());
                AtomicReference<Border> savedBottomCellBorder = new AtomicReference<>();
                AtomicReference<Border> savedRightCellBorder = new AtomicReference<>();


                setCellPaneClickHandler(cellPane, row, col,
                        actionMove, actionWall, horizontalWall, verticalWall,
                        gridPane,listOnGoing,listWinners,index);
                setCellPaneMouseEnterHandler(cellPane, row, col,
                        actionWall, horizontalWall, verticalWall, savedBorder,
                        savedBottomCellBorder, savedRightCellBorder,gridPane);
                setCellPaneMouseExitHandler(cellPane, row, col,
                        horizontalWall, verticalWall, savedBorder,
                        savedBottomCellBorder, savedRightCellBorder,gridPane);
            }
        }
        initializePlayerTurn(gridPane,listOnGoing,index);
        initializePlayers(gridPane, listOnGoing);
        initializeButtons(vbox_buttons, gridPane, actionMove, actionWall, horizontalWall, verticalWall,listOnGoing,index);

        return gridPane;
    }
    /**
     Initializes a GridPane for the game board.
     @return The initialized GridPane.
     */
    private GridPane initializeGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        return gridPane;
    }
    /**
     Creates a StackPane representing a cell on the game board.
     @param row The row index of the cell.
     @param col The column index of the cell.
     @return The created StackPane representing the cell.
     */
    private StackPane createCellPane(int row, int col) {
        StackPane cellPane = new StackPane();
        cellPane.setPrefSize(50, 50);
        Rectangle rect = new Rectangle(46, 46);
        rect.setFill((row + col) % 2 == 0 ? Color.WHITE : Color.GREY);
        cellPane.getChildren().add(rect);
        return cellPane;
    }
    /**
     Initializes the visual representation of players on the game board.
     @param gridPane The GridPane representing the game board.
     @param listOnGoing The list of Player objects to be initialized.
     */
    private void initializePlayers(GridPane gridPane, List<Player> listOnGoing) {
        for (Player player : listOnGoing) {
            int row = player.getCurrentBox().getRow();
            int column = player.getCurrentBox().getColumn();
            String color = player.getColor();
            StackPane cellPane = getCellPane(gridPane, row, column);
            Rectangle rect = (Rectangle) cellPane.getChildren().get(0);
            rect.setFill(Color.web(color));
        }
    }
    /**
     *Retrieves the StackPane representing a cell in the GridPane.
     *@param gridPane The GridPane containing the cells and creating the board of the game.
     *@param row The row index of the desired cell.
     *@param column The column index of the desired cell.
     *@return The StackPane representing the specified cell.
     */
    private StackPane getCellPane(GridPane gridPane, int row, int column) {
        return (StackPane) gridPane.getChildren().get(row * checkerboard_SIZE + column);
    }

    private void settingsButtonAction() throws IOException {
        System.out.println("button click");
        mainGame.switchScene(mainGame.showSaveLoadScene());
    }

    private void createImageView(Button name, String path ){
        name.setStyle("-fx-background-color: transparent");

        ImageView tempImageView = new ImageView(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
        tempImageView.setFitHeight(80);
        tempImageView.setPreserveRatio(true);
        name.setGraphic(tempImageView);
    }
    /**
     * Initializes the player's turn by displaying the current player's color on the grid pane.
     * @param gridPane     The GridPane on which the player's color will be displayed.
     * @param listOnGoing  The list of players in the current game.
     * @param index        The index of the current player in the list.
     */
    private void initializePlayerTurn(GridPane gridPane,
                                      List<Player> listOnGoing,
                                      AtomicInteger index) {
        Player currentPlayer = listOnGoing.get(index.get());
        String color = currentPlayer.getColor();
        StackPane cellPaneColorPlayer = new StackPane();
        cellPaneColorPlayer.setPrefSize(50, 50);
        Rectangle rect = new Rectangle(46, 46);
        rect.setFill(Paint.valueOf(color));
        cellPaneColorPlayer.getChildren().add(rect);
        gridPane.add(cellPaneColorPlayer, 10, 0);
    }

    /**
     *Initializes the buttons for the game interface.
     *A few buttons are created:
     *horizontalWallButton using {@link #createhorizontalWallButton(AtomicReference, AtomicReference)}
     *verticalWallButton using {@link #createverticalWallButton(AtomicReference, AtomicReference)}
     *moveButton using {@link #createMoveButton(VBox, GridPane, AtomicReference, AtomicReference, AtomicReference, AtomicReference, Button, Button, List, AtomicInteger)}
     *placeWallButton using {@link #createplaceWallButton(VBox, GridPane, AtomicReference, AtomicReference, AtomicReference, AtomicReference, Button, Button, List, AtomicInteger)}
     *@param gridPane The GridPane representing the game board.
     *@param actionMove An AtomicReference<Boolean> representing the actionMove flag.
     *@param actionWall An AtomicReference<Boolean> representing the actionWall flag.
     *@param horizontalWall An AtomicReference<Boolean> representing the horizontalWall flag. Created using {@link #createhorizontalWallButton(AtomicReference, AtomicReference)}
     *@param verticalWall An AtomicReference<Boolean> representing the verticalWall flag. Created usind {@link #createverticalWallButton(AtomicReference, AtomicReference)}
     *@param listOnGoing The list of players who are still playing.
     *@param index The index of the current player in the listOnGoing.
     */
    private void initializeButtons(VBox vbox_buttons, GridPane gridPane, AtomicReference<Boolean> actionMove,
                                   AtomicReference<Boolean> actionWall, AtomicReference<Boolean> horizontalWall,
                                   AtomicReference<Boolean> verticalWall,List<Player> listOnGoing,
                                   AtomicInteger index) {
        Button horizontalWallButton=createhorizontalWallButton(horizontalWall,verticalWall);
        Button verticalWallButton=createverticalWallButton(horizontalWall,verticalWall);
        Button moveButton = createMoveButton(vbox_buttons, gridPane, actionMove, actionWall, horizontalWall, verticalWall,
                horizontalWallButton,verticalWallButton,listOnGoing, index);
        Button placeWallButton = createplaceWallButton(vbox_buttons, gridPane, actionMove, actionWall, horizontalWall, verticalWall,
                horizontalWallButton,verticalWallButton,listOnGoing,index);

        Button settingsButton = new Button();

        settingsButton.setOnAction(event -> {
            try {
                settingsButtonAction();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        //Creating a graphic (image)
        createImageView(moveButton,"/org/projet/cypath/move.png");
        createImageView(placeWallButton,"/org/projet/cypath/wall.png");
        createImageView(settingsButton,"/org/projet/cypath/settings.png");

        vbox_buttons.getChildren().add(settingsButton);
        vbox_buttons.getChildren().add(moveButton);
        vbox_buttons.getChildren().add(placeWallButton);
    }

    private Button createMoveButton(VBox vbox_buttons, GridPane gridPane, AtomicReference<Boolean> actionMove,
                                    AtomicReference<Boolean> actionWall, AtomicReference<Boolean> horizontalWall,
                                    AtomicReference<Boolean> verticalWall, Button horizontalWallButton, Button verticalWallButton,
                                    List<Player> listOnGoing, AtomicInteger index){

    Button moveButton = new Button();
        moveButton.setOnAction(event -> {
            actionMove.set(true);
            actionWall.set(false);
            vbox_buttons.getChildren().remove(horizontalWallButton);
            vbox_buttons.getChildren().remove(verticalWallButton);
            horizontalWall.set(false);
            verticalWall.set(false);
            try {
                Player currentPlayer = listOnGoing.get(index.get());
                List<Box> possibleMove = currentPlayer.possibleMove(game.getBoard());
                for (Box box : possibleMove) {
                    int moveRow = box.getRow();
                    int moveCol = box.getColumn();
                    StackPane moveCellPane=getCellPane(gridPane,moveRow,moveCol);
                    Rectangle moveRect = (Rectangle) moveCellPane.getChildren().get(0);
                    moveRect.setFill(Color.PURPLE);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return moveButton;
    }

    /**
     *Creates a button to initiate the wall placement mode.
     *@param gridPane The GridPane representing the game board.
     *@param actionMove An AtomicReference<Boolean> representing the actionMove flag (set to false).
     *@param actionWall An AtomicReference<Boolean> representing the actionWall flag (set to true).
     *@param horizontalWall An AtomicReference<Boolean> representing the horizontalWall flag (set to false).
     *@param verticalWall An AtomicReference<Boolean> representing the verticalWall flag (set to false).
     *@param horizontalWallButton The Button for selecting horizontal wall placement.
     *@param verticalWallButton The Button for selecting vertical wall placement.
     *@param listOnGoing The list of players who are still playing.
     *@param index The index of the current player in the listOnGoing.
     *@return The created Button for initiating wall placement.
     */
    private Button createplaceWallButton(VBox vbox_buttons, GridPane gridPane, AtomicReference<Boolean> actionMove, AtomicReference<Boolean> actionWall,
                                         AtomicReference<Boolean> horizontalWall, AtomicReference<Boolean> verticalWall,
                                         Button horizontalWallButton, Button verticalWallButton,
                                         List<Player> listOnGoing, AtomicInteger index
    ) {
        Button placeWallButton = new Button();
        placeWallButton.setOnAction(event -> {
            if(!actionWall.get()) {
                vbox_buttons.getChildren().add(verticalWallButton);
                vbox_buttons.getChildren().add(horizontalWallButton);
            }
            actionMove.set(false);
            actionWall.set(true);
            horizontalWall.set(false);
            verticalWall.set(false);
            try {
                Player currentPlayer = listOnGoing.get(index.get());
                List<Box> possibleMove = currentPlayer.possibleMove(game.getBoard());
                // Enlever les couleurs des cases de mouvement possible de l'ancien joueur
                for (Box box : possibleMove) {
                    int moveRow = box.getRow();
                    int moveCol = box.getColumn();
                    StackPane moveCellPane=getCellPane(gridPane,moveRow,moveCol);
                    Rectangle moveRect = (Rectangle) moveCellPane.getChildren().get(0);
                    moveRect.setFill((moveRow + moveCol) % 2 == 0 ? Color.WHITE : Color.GREY);
                }
            } catch (OutOfBoardException e) {
                throw new RuntimeException(e);
            }
        });
        return placeWallButton;
    }
    /**
     *Creates a button to select the horizontal wall option.
     *@param horizontalWall A boolean value indicating whether the wall should be horizontal (set to true).
     *@param verticalWall A boolean value indicating whether the wall should be vertical (set to false).
     *@return The created Button.
     */
    private Button createhorizontalWallButton(AtomicReference<Boolean> horizontalWall, AtomicReference<Boolean> verticalWall) {
        Button horizontalWallButton = new Button();
        createImageView(horizontalWallButton,"/org/projet/cypath/rightKey.png");
        horizontalWallButton.setOnAction(event -> {
            horizontalWall.set(true);
            verticalWall.set(false);
        });
        return horizontalWallButton;
    }
    /**
    *Creates a button to select the vertical wall option.
    *@param horizontalWall A boolean value indicating whether the wall should be horizontal (set to false).
    *@param verticalWall A boolean value indicating whether the wall should be vertical (set to true).
    *@return The created Button.
    */
    private Button createverticalWallButton(AtomicReference<Boolean> horizontalWall, AtomicReference<Boolean> verticalWall) {
        Button verticalWallButton = new Button();
        createImageView(verticalWallButton,"/org/projet/cypath/upKey.png");
        verticalWallButton.setOnAction(event -> {
            horizontalWall.set(false);
            verticalWall.set(true);
        });
        return verticalWallButton;
    }
    /**
     * Proceeds to the next turn by updating the game state and indicating the current player.
     * This method is used in
     * {@link #setCellPaneClickHandler(StackPane, int, int, AtomicReference, AtomicReference, AtomicReference, AtomicReference, GridPane, List, List, AtomicInteger)},
     * {@link #initializeGridPane()},
     * @param gridPane     The GridPane representing the game board.
     * @param listOnGoing  The list of players currently in the game.
     * @param index        The current player index.
     */
    private void nextTurn(GridPane gridPane,List<Player> listOnGoing,
                          AtomicInteger index){
        // Incrémenter l'index et revenir à 0 si on atteint la taille de listOnGoing
        index.getAndIncrement();
        if (index.get() >= listOnGoing.size()) {
            index.set(0);
        }
        //Modifier la couleur de la case montrant à quelle joueur c'est le tour
        Player currentPlayer = listOnGoing.get(index.get());
        Color colorCurrentPlayer= Color.valueOf(currentPlayer.getColor());
        StackPane moveCellPane = (StackPane) gridPane.getChildren().get(81);
        Rectangle moveRect = (Rectangle) moveCellPane.getChildren().get(0);
        moveRect.setFill(colorCurrentPlayer);
    }

    /**
     *This method handles the player's interaction with a cell on the board. There are two possible scenarios:
     *When the boolean variable actionMove is true, indicating that the player wants to move. The method will move the player to the corresponding cell if possible.
     *When the boolean variable actionWall is true, indicating that the player wants to place a wall. The method will place a wall on the corresponding cell based on the following conditions:
     *If horizontalWall is true, a horizontal wall will be placed on the board.
     *If verticalWall is true, a vertical wall will be placed on the board.
     *@param cellPane The cell that has been clicked.
     *@param currentRow The row of the clicked cell.
     *@param currentCol The column of the clicked cell.
     *@param actionMove Boolean indicating if the player wants to move.
     *@param actionWall Boolean indicating if the player wants to place a wall.
     *@param horizontalWall Boolean indicating if a horizontal wall should be placed.
     *@param verticalWall Boolean indicating if a vertical wall should be placed.
     *@param gridPane The GridPane representing the game board.
     *@param listOnGoing List of players who are still playing.
     *@param listWinners List of players who have won.
     *@param index The index of the current player in the listOnGoing.
     */
    private void setCellPaneClickHandler(StackPane cellPane, int currentRow, int currentCol,
                                         AtomicReference<Boolean> actionMove, AtomicReference<Boolean> actionWall,
                                         AtomicReference<Boolean> horizontalWall, AtomicReference<Boolean> verticalWall,
                                         GridPane gridPane,List<Player> listOnGoing,List<Player> listWinners,
                                         AtomicInteger index
                                         ) {
        cellPane.setOnMouseClicked(event -> {
            if (actionMove.get()) {
                    Player currentPlayer = listOnGoing.get(index.get());
                    int fromRow = currentPlayer.getCurrentBox().getRow();
                    int fromCol = currentPlayer.getCurrentBox().getColumn();
                    List<Box> victoryBox = currentPlayer.getVictoryBoxes();
                    String color = currentPlayer.getColor();
                    try {
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
                            // Enlever les couleurs des cases de mouvement possible de l'ancienne position
                            for (Box box : possibleMove) {
                                int moveRow = box.getRow();
                                int moveCol = box.getColumn();
                                StackPane moveCellPane = getCellPane(gridPane,moveRow,moveCol);
                                Rectangle moveRect = (Rectangle) moveCellPane.getChildren().get(0);
                                moveRect.setFill((moveRow + moveCol) % 2 == 0 ? Color.WHITE : Color.GREY);
                            }
                            // Modifier la couleur de la case d'origine de l'ancienne position
                            StackPane fromCellPane = getCellPane(gridPane,fromRow,fromCol);
                            Rectangle fromRect = (Rectangle) fromCellPane.getChildren().get(0);
                            fromRect.setFill((fromRow + fromCol) % 2 == 0 ? Color.WHITE : Color.GREY);
                            currentPlayer.moveTo(game.getBoard().getBox(currentRow, currentCol));
                            int toRow = currentPlayer.getCurrentBox().getRow();
                            int toCol = currentPlayer.getCurrentBox().getColumn();
                            // Modifier la couleur de la nouvelle case de la nouvelle position
                            StackPane toCellPane = getCellPane(gridPane,toRow,toCol);
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
                                    index.getAndDecrement();
                                    if (index.get() < 0) {
                                        index.set(listOnGoing.size() - 1);
                                    }
                                    //On fait disparaitre le joueur du plateau si il a gagné
                                    Box currentBox=game.getBoard().getBox(currentRow,currentCol);
                                    currentBox.setHasPlayer(false);
                                    toRect.setFill((toRow + toCol) % 2 == 0 ? Color.WHITE : Color.GREY);
                                    //Clignottement du joueur quand il gagne
                                    Timeline blinkAnimation = new Timeline(
                                            new KeyFrame(Duration.seconds(0.5), new KeyValue(toRect.fillProperty(), Color.web(color))),
                                            new KeyFrame(Duration.seconds(1.0), new KeyValue(toRect.fillProperty(), (toRow + toCol) % 2 == 0 ? Color.WHITE : Color.GREY))
                                    );
                                    blinkAnimation.setCycleCount(2); // Effectuer le clignotement deux fois
                                    blinkAnimation.play();
                                }
                            }
                            //Modification de la couleur de la case et incrémentation
                            nextTurn(gridPane,listOnGoing,index);
                            // Afficher les nouveaux déplacements possible
                            currentPlayer = listOnGoing.get(index.get());
                            possibleMove = currentPlayer.possibleMove(game.getBoard());
                            for (Box box : possibleMove) {
                                int moveRow = box.getRow();
                                int moveCol = box.getColumn();
                                StackPane moveCellPane = getCellPane(gridPane,moveRow,moveCol);
                                Rectangle moveRect = (Rectangle) moveCellPane.getChildren().get(0);
                                moveRect.setFill(Color.PURPLE);
                            }

                        }
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }
            }
            if (actionWall.get()) {
                try {
                    Box currentBox = game.getBoard().getBox(currentRow, currentCol);
                    if (horizontalWall.get() && game.getBoard().canSetWall(currentBox, 0)) {
                        if (game.hasPath(currentRow, currentCol, 0)) {
                            game.getBoard().setBottomWall(currentRow, currentCol);
                            //Modification de la couleur de la case et incrémentation
                            nextTurn(gridPane,listOnGoing,index);
                        }
                    }
                    if (verticalWall.get() && game.getBoard().canSetWall(currentBox, 1)) {
                        if (game.hasPath(currentRow, currentCol, 1)){
                            game.getBoard().setRightWall(currentRow, currentCol);
                            //Modification de la couleur de la case et incrémentation
                            nextTurn(gridPane,listOnGoing,index);
                        }
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
    }
    /**
     *Sets the mouse enter event handler for a cell pane.
     *If action wall is true, it displays the wall according to 2 different scenarios
     *If horizontalWall is true, a horizontal wall will be displayed on the board.
     *If verticalWall is true, a vertical wall will be displayed on the board.
     *@param cellPane The StackPane representing the cell pane.
     *@param currentRow The current row index of the cell pane.
     *@param currentCol The current column index of the cell pane.
     *@param actionWall AtomicReference<Boolean> flag indicating the wall action.
     *@param horizontalWall AtomicReference<Boolean> flag indicating the horizontal wall option.
     *@param verticalWall AtomicReference<Boolean> flag indicating the vertical wall option.
     *@param savedBorder AtomicReference<Border> reference to store the original border of the cell pane.
     *@param savedBottomCellBorder AtomicReference<Border> reference to store the original border of the bottom adjacent cell pane.
     *@param savedRightCellBorder AtomicReference<Border> reference to store the original border of the right adjacent cell pane.
     *@param gridPane The GridPane representing the game board.
     */
    private void setCellPaneMouseEnterHandler(StackPane cellPane, int currentRow, int currentCol,
                                              AtomicReference<Boolean> actionWall, AtomicReference<Boolean> horizontalWall,
                                              AtomicReference<Boolean> verticalWall, AtomicReference<Border> savedBorder,
                                              AtomicReference<Border> savedBottomCellBorder, AtomicReference<Border> savedRightCellBorder,
                                              GridPane gridPane
    ) {
        cellPane.setOnMouseEntered(event -> {
            try {
                Box currentBox = game.getBoard().getBox(currentRow, currentCol);
                if (actionWall.get()) {
                    if (horizontalWall.get() && game.getBoard().canSetWall(currentBox, 0)) {
                        StackPane rightCell=getCellPane(gridPane,currentRow,currentCol+1);
                        savedBorder.set(cellPane.getBorder());
                        savedRightCellBorder.set(rightCell.getBorder());
                        Border currentBorder = cellPane.getBorder();
                        Border rightCellBorder = rightCell.getBorder();
                        Border newBorder = addBottomBorder(currentBorder);
                        cellPane.setBorder(newBorder);
                        Border newRightBorder = addBottomBorder(rightCellBorder);
                        rightCell.setBorder(newRightBorder);
                    }
                    if (verticalWall.get() && game.getBoard().canSetWall(currentBox, 1)) {
                        StackPane bottomCell=getCellPane(gridPane,currentRow+1,currentCol);
                        savedBorder.set(cellPane.getBorder());
                        savedBottomCellBorder.set(bottomCell.getBorder());

                        Border currentBorder = cellPane.getBorder();
                        Border bottomCellBorder = bottomCell.getBorder();

                        Border newBorder = addRightBorder(currentBorder);
                        cellPane.setBorder(newBorder);
                        Border newBottomBorder = addRightBorder(bottomCellBorder);
                        bottomCell.setBorder(newBottomBorder);
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        });
    }
    /**
     *Sets the mouse exit event handler for a cell pane. If a wall display was present, it will be removed.
     *@param cellPane The StackPane representing the cell pane.
     *@param currentRow The current row index of the cell pane.
     *@param currentCol The current column index of the cell pane.
     *@param horizontalWall AtomicReference<Boolean> flag indicating the horizontal wall option.
     *@param verticalWall AtomicReference<Boolean> flag indicating the vertical wall option.
     *@param savedBorder AtomicReference<Border> reference to the saved original border of the cell pane. This is used to restore the original appearance of the current cell.
     *@param savedBottomCellBorder AtomicReference<Border> reference to the saved original border of the bottom adjacent cell pane. This is used to restore the original appearance of the bottom cell if a wall was displayed.
     *@param savedRightCellBorder AtomicReference<Border> reference to the saved original border of the right adjacent cell pane. This is used to restore the original appearance of the right cell if a wall was displayed.
     *@param gridPane The GridPane representing the game board.
     */
    private void setCellPaneMouseExitHandler(StackPane cellPane, int currentRow, int currentCol,
                                             AtomicReference<Boolean> horizontalWall, AtomicReference<Boolean> verticalWall,
                                             AtomicReference<Border> savedBorder, AtomicReference<Border> savedBottomCellBorder,
                                             AtomicReference<Border> savedRightCellBorder,
                                             GridPane gridPane
                                             ) {
        cellPane.setOnMouseExited(event -> {
            try {
                Box currentBox = game.getBoard().getBox(currentRow, currentCol);
                // Vérifiez si une bordure à droite peut être supprimée
                if (horizontalWall.get() && game.getBoard().canSetWall(currentBox, 0)) {
                    StackPane rightCell=getCellPane(gridPane,currentRow,currentCol+1);
                    cellPane.setBorder(savedBorder.get());
                    rightCell.setBorder(savedRightCellBorder.get());
                }
                if (verticalWall.get() && game.getBoard().canSetWall(currentBox, 1)) {
                    StackPane bottomCell=getCellPane(gridPane,currentRow+1,currentCol);
                    cellPane.setBorder(savedBorder.get());
                    bottomCell.setBorder(savedBottomCellBorder.get());
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        });
    }

    /**
     *Adds a bottom border to the given border. This simulates the appearance of a bottom wall.
     *@param border The original border to which the bottom border will be added.
     *@return The new border with the added bottom border.
     */
    private Border addBottomBorder(Border border) {
        // Récupérer la bordure existante
        BorderStroke borderStrokeTop = border.getStrokes().get(0);
        BorderStroke borderStrokeRight = border.getStrokes().get(1);
        BorderStroke borderStrokeBottom = border.getStrokes().get(2);
        BorderStroke borderStrokeLeft = border.getStrokes().get(3);
        // Récupérer les propriétés de la bordure existante
        BorderStrokeStyle borderLineStyle = BorderStrokeStyle.SOLID;
        //Bottom
        BorderStroke newBorderStrokeBottom = new BorderStroke(
                Color.BLACK,
                borderLineStyle,
                borderStrokeBottom.getRadii(),
                new BorderWidths(0, 0, 4, 0)
        );
        // Mettre à jour le compteur
        updateWallCounter();
        // Créer une nouvelle bordure en combinant les bordures individuelles
        return new Border(
                borderStrokeTop,
                borderStrokeRight,
                newBorderStrokeBottom,
                borderStrokeLeft
        );
    }
    /**
     Adds a right border to the given border. This simulates the appearance of a right wall.
     @param border The original border to which the right border will be added.
     @return The new border with the added right border.
     */
    private Border addRightBorder(Border border) {
        // Récupérer la bordure existante
        BorderStroke borderStrokeTop = border.getStrokes().get(0);
        BorderStroke borderStrokeRight = border.getStrokes().get(1);
        BorderStroke borderStrokeBottom = border.getStrokes().get(2);
        BorderStroke borderStrokeLeft = border.getStrokes().get(3);
        // Récupérer les propriétés de la bordure existante
        BorderStrokeStyle borderLineStyle = BorderStrokeStyle.SOLID;
        //Right
        BorderStroke newBorderStrokeRight = new BorderStroke(
                Color.BLACK,
                borderLineStyle,
                borderStrokeBottom.getRadii(),
                new BorderWidths(0, 4, 0, 0)
        );
        // Mettre à jour le compteur
        updateWallCounter();
        // Créer une nouvelle bordure en combinant les bordures individuelles
        return new Border(
                borderStrokeTop,
                newBorderStrokeRight,
                borderStrokeBottom,
                borderStrokeLeft
        );
    }

    /**
     * Get the number of remaining walls.
     * @return the number of remaining walls.
     */
    public int CounterRemainingWalls(){
        Board board = this.game.getBoard();
        return board.getRemainingWalls();
    }

    /**
     * Update the text of the wall counter.
     */
    public void updateWallCounter(){
        wall_remaining_hbox_text.setText(": "+ CounterRemainingWalls());
    }
}

