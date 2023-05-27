package org.projet.cypath;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import org.projet.cypath.exceptions.InvalidSceneException;
import org.projet.cypath.exceptions.OutOfBoardException;
import org.projet.cypath.tools.Game;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class SaveLoadSceneController {

    @FXML
    private ImageView saveImageView;
    @FXML
    private ImageView loadImageView;
    @FXML
    private ImageView quitImageView;
    @FXML
    private ImageView backButtonImageView;
    @FXML
    private ImageView startGameImageView;
    @FXML
    private StackPane loadScreen;



    private MainGame mainGame;

    @FXML
    private void changeBackground(StackPane pane) {

        InputStream is = getClass().getResourceAsStream("/org/projet/cypath/start_background_transparent.png");
        assert is != null;
        Image image = new Image(is);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        pane.setBackground(background);
    }

    @FXML
    private void handleButtonHover(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        Glow glow = new Glow();
        glow.setLevel(0.5); // Set between 0.0 and 1.0
        imageView.setEffect(glow);
    }

    @FXML
    private void handleButtonExit(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        imageView.setEffect(null); // remove the glow effect

    }

    @FXML
    public void handleSaveButtonAction(MouseEvent mouseEvent) {
        System.out.println("Save button clicked");
        

    }

    @FXML
    public void handleButtonBack(MouseEvent mouseEvent) throws IOException {
        System.out.println("Back button clicked");

        loadScreen.setVisible(false);
        mainGame.switchScene(mainGame.showStartScene());

        /*fadingPanes(playerSelectionView,titleScreen); */
    }

    @FXML
    private void handleStartButtonAction(MouseEvent event) throws InvalidSceneException  {
        mainGame.showGameScene();
    }

    @FXML
    private void handleLoadButtonAction(MouseEvent event) throws IOException, OutOfBoardException {     //Open saves menu
        System.out.println("Load button clicked");

        Game game = new Game(2);
        //game.getSave();



    }

    @FXML
    private void handleQuitButtonAction(MouseEvent event) {     //Quit the game
        System.out.println("Quit button clicked");
        Platform.exit();
    }



    public void createImageView(ImageView name, String path, int height, boolean aspectRatio){

        name.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))));
        name.setFitHeight(height); //set height
        name.setPreserveRatio(aspectRatio); //conserve aspect ratio

    }

    public void setMainGame(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    public void initialize() {

        createImageView(quitImageView,"/org/projet/cypath/quit.png",100,true);

        createImageView(backButtonImageView,"/org/projet/cypath/back.png",100,true);

        createImageView(startGameImageView,"/org/projet/cypath/start.png",100,true);

        createImageView(loadImageView,"/org/projet/cypath/load.png",100,true);

        createImageView(saveImageView,"/org/projet/cypath/save.png",150,true);

        changeBackground(loadScreen);

        loadScreen.setVisible(true);

    }

}
