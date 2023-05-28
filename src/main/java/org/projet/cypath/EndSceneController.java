package org.projet.cypath;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.projet.cypath.players.Player;
import org.projet.cypath.tools.Game;

import java.io.InputStream;
import java.util.List;

public class EndSceneController {
    private MainGame mainGame;
    private Game game;

    @FXML
    private Label titleLabel;

    @FXML
    private Label winnerLabel;

    @FXML
    private Rectangle winnerColorBox;

    @FXML
    private HBox otherPlayersContainer;

    @FXML
    private AnchorPane endScenePane;


    public void setMainGame(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    public void setGame(Game game) {
        this.game = game;
        populateEndScene();
    }

    private void populateEndScene() {
        InputStream is = getClass().getResourceAsStream("/org/projet/cypath/start_background_transparent.png");
        assert is != null;
        Image image = new Image(is);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        endScenePane.setBackground(background);

        List<Player> listWinners = game.getListWinners();

        // Set the winner's color box
        winnerColorBox.setFill(Color.valueOf(listWinners.get(0).getColor()));

        // For the rest of the players, create a color box and add it to the container
        for (int i = 1; i < listWinners.size(); i++) {
            Rectangle colorBox = new Rectangle(20, 20);
            colorBox.setFill(Color.valueOf(listWinners.get(i).getColor()));
            otherPlayersContainer.getChildren().add(colorBox);
        }
    }
}
