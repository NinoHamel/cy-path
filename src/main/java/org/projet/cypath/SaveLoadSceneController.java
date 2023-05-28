package org.projet.cypath;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.projet.cypath.exceptions.InvalidSaveException;
import org.projet.cypath.exceptions.InvalidSceneException;
import org.projet.cypath.exceptions.OutOfBoardException;
import org.projet.cypath.tools.Game;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    /**
     * Represents the main game instance.
     */
    private MainGame mainGame;

    /**
     * Represents the previous Scene.
     */
    private Scene previousScene;
    /**
     * Changes the background of the specified StackPane.
     * @param pane The StackPane to change the background of.
     */

    private Game game;

    public void setThisGame(Game game){
        this.game = game;
    }

    @FXML
    private void changeBackground(StackPane pane) {

        InputStream is = getClass().getResourceAsStream("/org/projet/cypath/start_background_transparent.png");
        assert is != null;
        Image image = new Image(is);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        pane.setBackground(background);
    }
    /**
     * Handles the hover event for buttons.
     * @param event The MouseEvent triggered by the button.
     */
    @FXML
    private void handleButtonHover(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        Glow glow = new Glow();
        glow.setLevel(0.5); // Set between 0.0 and 1.0
        imageView.setEffect(glow);
    }
    /**
     * Handles the exit event for buttons.
     * @param event The MouseEvent triggered by the button.
     */
    @FXML
    private void handleButtonExit(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        imageView.setEffect(null); // remove the glow effect

    }
    /**
     * Handles the action when the save button is clicked.
     * @param mouseEvent The MouseEvent triggered by the button.
     */
    @FXML
    public void handleSaveButtonAction(MouseEvent mouseEvent) throws InvalidSaveException {
        System.out.println("Save button clicked");
        try{
            game.save();
        }
        catch (Exception e){
            throw new InvalidSaveException();
        }


        

    }
    /**
     * Handles the action when the back button is clicked.
     * @param mouseEvent The MouseEvent triggered by the button.
     * @throws IOException In case of an I/O exception during the switch of scenes.
     */
    @FXML
    public void handleButtonBack(MouseEvent mouseEvent) throws IOException {
        System.out.println("Back button clicked");

        mainGame.switchScene(this.previousScene);

    }

    /**
     * Handles the action when the load button is clicked.
     * @param event The MouseEvent triggered by the button.
     * @throws IOException          In case of an I/O exception during the loading process.
     * @throws OutOfBoardException If a board is out of bounds.
     */
    @FXML
    private void handleLoadButtonAction(MouseEvent event) throws IOException, OutOfBoardException, InvalidSceneException, InvalidSaveException {     //Open saves menu
        System.out.println("Load button clicked");


        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src"));
        directoryChooser.setTitle("Load save");
        File selectedDirectory = directoryChooser.showDialog(mainGame.showSaveLoadScene().getWindow());
        String path = selectedDirectory.getPath();
        System.out.println(selectedDirectory);
        System.out.println(path);

        mainGame.switchScene(mainGame.showGameScene(path));
    }
    /**
     * Handles the action when the quit button is clicked.
     * @param event The MouseEvent triggered by the button.
     */
    @FXML
    private void handleQuitButtonAction(MouseEvent event) {     //Quit the game
        System.out.println("Quit button clicked");
        Platform.exit();
    }
    /**
     * Creates an ImageView with the specified properties.
     * @param name        The ImageView instance to configure.
     * @param path        The path to the image resource.
     * @param height      The height of the ImageView.
     * @param aspectRatio Determines whether to preserve the aspect ratio of the image.
     */
    public void createImageView(ImageView name, String path, int height, boolean aspectRatio){

        name.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))));
        name.setFitHeight(height); //set height
        name.setPreserveRatio(aspectRatio); //conserve aspect ratio

    }
    /**
     * Sets the main game instance.
     * @param mainGame The main game instance.
     */
    public void setMainGame(MainGame mainGame) {
        this.mainGame = mainGame;
    }
    /**
     * Sets the previous Scene.
     * @param previousScene The previous Scene.
     */
    public  void setPreviousScene(Scene previousScene){
        this.previousScene = previousScene;
    }
    /**
     * Initializes the controller.
     */
    public void initialize() {

        createImageView(quitImageView,"/org/projet/cypath/quit.png",100,true);

        createImageView(backButtonImageView,"/org/projet/cypath/back.png",100,true);

        createImageView(loadImageView,"/org/projet/cypath/load.png",100,true);

        createImageView(saveImageView,"/org/projet/cypath/save.png",150,true);

        changeBackground(loadScreen);

    }

}
