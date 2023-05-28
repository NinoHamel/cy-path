package org.projet.cypath;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.projet.cypath.players.Player;
import org.projet.cypath.tools.Game;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class EndSceneController {
    public HBox winnerContainer;
    public Label winnerLabel;
    public Label titleLabel;
    public VBox mainContainer;
    private MainGame mainGame;
    private Game game;

    @FXML
    private Rectangle winnerColorBox;

    @FXML
    private HBox otherPlayersContainer;

    @FXML
    private AnchorPane endScenePane;

    @FXML
    private ImageView backButtonImageView;

    /**
     * Create an instance of mainGame to call later
     * @param mainGame instance of the MainGame
     */
    public void setMainGame(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    /**
     * Needed to get the previous game winners list
     * @param game Get the previous game instance
     */
    public void setGame(Game game) {
        this.game = game;
        initialize();
    }

    /**
     * Event handler for button hover event.
     * @param event the mouse event
     */
    @FXML
    private void handleButtonHover(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        Glow glow = new Glow();
        glow.setLevel(0.5); // Set between 0.0 and 1.0
        imageView.setEffect(glow);
    }
    /**
     * Event handler for button exit event.
     * @param event the mouse event
     */
    @FXML
    private void handleButtonExit(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        imageView.setEffect(null); // remove the glow effect
    }

    /**
     * A button to go back to the main menu
     */
    @FXML
    public void handleButtonBack() throws IOException {
        System.out.println("Back button clicked");
        mainGame.switchScene(mainGame.showStartScene());
    }

    /**
     * Load ressources for the end screen
     */
    private void initialize() {
        InputStream is = getClass().getResourceAsStream("/org/projet/cypath/start_background_transparent.png");
        assert is != null;
        Image image = new Image(is);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        endScenePane.setBackground(background);

        backButtonImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/projet/cypath/back.png"))));
        backButtonImageView.setFitHeight(80);
        backButtonImageView.setPreserveRatio(true);

        List<Player> listWinners = game.getListWinners();

        // Set the winner's color box
        winnerColorBox.setFill(Color.valueOf(listWinners.get(0).getColor()));

        // For the rest of the players, create a color box and add it to the container
        for (int i = 1; i < listWinners.size(); i++) {
            Rectangle colorBox = new Rectangle(40, 40);
            colorBox.setFill(Color.valueOf(listWinners.get(i).getColor()));
            otherPlayersContainer.getChildren().add(colorBox);
        }
    }
}
