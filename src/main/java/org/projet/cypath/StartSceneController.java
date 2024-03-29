package org.projet.cypath;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import org.projet.cypath.exceptions.InvalidSaveException;
import org.projet.cypath.exceptions.InvalidSceneException;
import org.projet.cypath.exceptions.OutOfBoardException;
/**
 * The controller class for the main game screen.
 */
public class StartSceneController{
    /**
     * The controller class for the main game screen.
     */
    private MainGame mainGame;
    /**
     * The number of players in the game.
     * Default value is 2.
     */
    private int numPlayers = 2;
    @FXML
    private StackPane playerSelectionView;
    @FXML
    private StackPane settingsView;
    @FXML
    private StackPane titleScreen;
    @FXML
    private ImageView newgameImageView;
    @FXML
    private ImageView loadImageView;
    @FXML
    private ImageView settingsImageView;
    @FXML
    private ImageView quitImageView;
    @FXML
    private Text logoText;
    @FXML
    private ImageView backButtonImageView;
    @FXML
    private ImageView startgameImageView;
    @FXML
    private ImageView minusButtonImageView;
    @FXML
    private ImageView plusButtonImageView;
    @FXML
    private Label numPlayersLabel;
    @FXML
    private ImageView backButtonSettingsImageView;
    @FXML
    private ImageView musicButtonImageView;
    @FXML
    private ImageView previousButtonImageView;
    @FXML
    private ImageView nextButtonImageView;
    @FXML
    private ImageView rulesImageView;
    @FXML
    private Text rulesText;
    @FXML
    private ImageView void1ImageView;
    @FXML
    private ImageView void2ImageView;

    /* IMAGES REGLES
    private Image muteImage = new Image(getClass().getResourceAsStream("/org/projet/cypath/mute.png"));*/

    private final Image muteImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/projet/cypath/mute.png")));
    private final Image soundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/projet/cypath/sound.png")));
    private static boolean musicState = false;
    private static int rulesTextState = 0;

    /**
     * Setter for the MainGame instance.
     * @param mainGame the MainGame instance
     */
    public void setMainGame(MainGame mainGame) {
        this.mainGame = mainGame;
    }
    /**
     * Event handler for the start button action.
     * @throws InvalidSceneException if the scene is invalid
     */
    @FXML
    private void handleStartButtonAction() throws InvalidSceneException, OutOfBoardException {
        mainGame.setNumPlayers(numPlayers);
        mainGame.switchScene(mainGame.showGameScene());
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
     * Event handler for the new game button action.
     */
    @FXML
    private void handleNewGameButtonAction() {
        System.out.println("New Game button clicked");
        fadingPanes(titleScreen,playerSelectionView);

        changeBackground(playerSelectionView);
    }
    /**
     * Event handler for the load button action.
     * @throws IOException if an I/O error occurs
     */
    @FXML
    private void handleLoadButtonAction() throws IOException, InvalidSaveException {     //Open saves menu
        System.out.println("Load button clicked");
        mainGame.switchScene(mainGame.showSaveLoadScene());
    }
    /**
     * Event handler for the settings button action.
     */
    @FXML
    private void handleSettingsButtonAction() { //Open settings
        System.out.println("Settings button clicked");
        fadingPanes(titleScreen,settingsView);
        changeBackground(settingsView);
    }
    /**
     * Event handler for the quit button action.
     */
    @FXML
    private void handleQuitButtonAction() {     //Quit the game
        System.out.println("Quit button clicked");
        Platform.exit();
    }
    /**
     * Handles the button action for navigating back to the previous screen.
     */
    @FXML
    public void handleButtonBack() {
        System.out.println("Back button clicked");

        fadingPanes(playerSelectionView,titleScreen);
    }
    /**
     * Handles the button action for increasing the number of players.
     */
    @FXML
    private void handlePlusButtonAction() {
        if (numPlayers < 4) {
            numPlayers++;
            numPlayersLabel.setText(String.valueOf(numPlayers));
        }
    }
    /**
     * Handles the button action for decreasing the number of players.
     */
    @FXML
    private void handleMinusButtonAction() {
        if (numPlayers > 2) {
            numPlayers--;
            numPlayersLabel.setText(String.valueOf(numPlayers));
        }
    }
    /**
     * Handles the button action for navigating back to the main menu from the settings menu.
     */
    @FXML
    public void handleButtonBackSettings() {
        System.out.println("Back button clicked");

        fadingPanes(settingsView,titleScreen);


    }
    /**
     * Handles the button action for toggling the music state.
     */
    @FXML
    public void handleButtonMusic() {
        System.out.println("Music button clicked");

        setMusicState();
        System.out.println(musicState);

        if(musicState){
            musicButtonImageView.setImage(soundImage);
        }
        else {
            musicButtonImageView.setImage(muteImage);
        }

    }
    /**
     * Handles the click event of the "Previous" button.
     * Updates the rulesTextState variable and calls the setRulesText() method to update the rules text accordingly.
     */
    @FXML
    public void handleButtonPrevious() {
        System.out.println("Previous button clicked");

        if(rulesTextState == 0){
            rulesTextState = 4;
        }
        else {
            rulesTextState -=1;
        }

        setRulesText(rulesText);

    }
    /**
     * Handles the click event of the "Next" button.
     * Updates the rulesTextState variable and calls the setRulesText() method to update the rules text accordingly.
     */
    @FXML
    public void handleButtonNext() {
        System.out.println("Next button clicked");

        if(rulesTextState == 4){
            rulesTextState = 0;
        }
        else {
            rulesTextState +=1;
        }

        setRulesText(rulesText);

    }
    /**
     * Changes the background of a StackPane with the provided image.
     * @param pane The StackPane to change the background of.
     */
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
     * Fades out one StackPane and fades in another StackPane with a transition animation.
     * @param fadeOutPane The StackPane to fade out.
     * @param fadeInPane The StackPane to fade in.
     */
    public void fadingPanes(StackPane fadeOutPane, StackPane fadeInPane){

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), fadeOutPane);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), fadeInPane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        fadeOut.setOnFinished(e -> {
            fadeOutPane.setVisible(false);
            fadeInPane.setVisible(true);
            fadeIn.play();
        });

        fadeOut.play();
    }
    /**
     * Creates an ImageView with the provided image path, height, and aspect ratio settings.
     * @param name The ImageView to create.
     * @param path The path to the image resource.
     * @param height The height of the ImageView.
     * @param aspectRatio Whether to preserve the aspect ratio of the image.
     */
    public void createImageView(ImageView name, String path, int height, boolean aspectRatio){

        name.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))));
        name.setFitHeight(height); //set height
        name.setPreserveRatio(aspectRatio); //conserve aspect ratio

    }
    /**
     * Sets the text of the rulesText Text node based on the current rulesTextState.
     * @param rulesText The Text node to set the text for.
     */
    public static void setRulesText(Text rulesText){
        switch (rulesTextState) {
            case 0 ->
                    rulesText.setText("Ce projet est un jeu de plateau compétitif pour 2 ou 4 joueurs. Le but est de faire traverser le plateau à son pion avant les autres joueurs.\n" +
                            "La zone de jeu est constituée de 81 cases disposées en carré. Chaque case est séparée des autres par des lignes horizontales et verticales (quadrillage). Chaque joueur démarre sur la case milieu d'un des bords du plateau (pour 2 joueurs, les pions doivent être placés sur des bords opposés).");
            case 1 ->
                    rulesText.setText("Chaque joueur joue chacun son tour. A chaque tour de jeu, un joueur peut choisir de déplacer son pion d'une seule case horizontalement ou verticalement, dans n'importe quelle direction.\n" +
                            "Il peut également choisir de positionner une barrière, horizontalement ou verticalement. Une barrière possède une longueur de 2 cases. Une barrière ne peut pas dépasser de la zone de jeu. Une barrière ne pas superposer une autre barrière. Une barrière est forcément placée pour avoir 2 cases adjacentes complètes. Les barrières sont au nombre de 20 au total.");
            case 2 ->
                    rulesText.setText("""
                            Les barrières ne peuvent pas être franchies par les pions des joueurs. Ces derniers devront donc trouver un autre chemin pour progresser en direction
                            du bord opposé.
                            Lorsqu'une barrière est posée, il est impossible de la déplacer pour le reste de la partie. Lorsqu'il n'y a plus de barrières à poser, les joueurs ne peuvent alors que déplacer leurs pions pour terminer la partie.
                            Si il n'est plus possible de poser de nouvelles barrières, alors la seule action possible pour un joueur est de déplacer son pion.
                            Lorsqu'un joueur pose une barrière, il doit s'assurer que le pion du joueur adverse dispose au moins d'un trajet possible. Il est interdit de bloquer définitivement l'adversaire : tout joueur doit avoir au moins 1 trajet possible.""");
            case 3 ->
                    rulesText.setText("Le premier joueur à faire parvenir son pion sur n'importe quelle case qui se trouve sur le bord opposé à son point de départ est déclaré vainqueur. Lorsque 2 pions sont côte à côte, il est possible pour l'un de \"sauter\" par dessus l'autre pour passer de l'autre côté en un seul coup. Ceci n'est possible QUE SI la case d'arrivée n'est pas déjà occupée par un autre joueur (cas d'une partie à 4 joueurs par exemple), ou bien que la case à sauter et la case d'arrivée ne soient pas séparées par une barrière.\n" +
                            "Dans l'une des ces deux situations, et seulement dans ce cas là, il est possible pour le joueur actif de se déplacer en diagonale à côté du joueur adverse.");
            case 4 ->
                    rulesText.setText("Dans ce cas précis, si l'une des cases diagonales est séparée de la case du joueur jaune par une barrière, alors le déplacement diagonale ne pourra pas se faire non plus. Il faut voir ce \"saut\" comme si le joueur bleu avançait sur la case du joueur jaune, puis se déplaçait sur la case d'arrivée. Il effectue donc 2 déplacements consécutifs, si et seulement si ces 2 déplacements sont possibles (non entravés par une barrière, ou un 3ème joueur).");
        }

    }
    /**
     * Toggles the music state (on/off).
     */
    public static void setMusicState(){
        musicState = !musicState;
    }

    /**
     * Initializes the controller by setting up various UI elements and animations.
     */
    public void initialize() {
        //main menu buttons
        createImageView( newgameImageView,"/org/projet/cypath/newgame.png",100,true);
        createImageView(loadImageView,"/org/projet/cypath/load.png",100,true);
        createImageView(settingsImageView,"/org/projet/cypath/settings.png",100,true);
        createImageView(quitImageView,"/org/projet/cypath/quit.png",100,true);

        //settings buttons
        createImageView(backButtonSettingsImageView,"/org/projet/cypath/back.png",70,true);
        createImageView(musicButtonImageView,"/org/projet/cypath/mute.png",70,true);
        createImageView(previousButtonImageView,"/org/projet/cypath/back_settings.png",70,true);
        createImageView(nextButtonImageView,"/org/projet/cypath/next_settings.png",70,true);
        createImageView(rulesImageView, "/org/projet/cypath/rules1.png", 300, true);
        createImageView(void1ImageView,"/org/projet/cypath/void.png",50,true);
        createImageView(void2ImageView,"/org/projet/cypath/void.png",50,true);
        setRulesText(rulesText);



        //player selection buttons
        createImageView(backButtonImageView,"/org/projet/cypath/back.png",70,true);
        createImageView(startgameImageView,"/org/projet/cypath/start.png",80,true);
        createImageView(minusButtonImageView,"/org/projet/cypath/minus.png",50,true);
        createImageView(plusButtonImageView,"/org/projet/cypath/plus.png",50,true);

        InputStream is = getClass().getResourceAsStream("/org/projet/cypath/start_background.png");
        assert is != null;
        Image image = new Image(is);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        titleScreen.setBackground(background);

        logoText.setText("CY-PATH");

        // Create a glow effect
        Glow glow = new Glow();

        // Create an inner shadow effect
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setOffsetY(0f);
        innerShadow.setOffsetX(0f);
        innerShadow.setColor(Color.BLACK);
        innerShadow.setRadius(10);
        innerShadow.setChoke(0.2);

        // Combine the glow and inner shadow effects
        Blend blend = new Blend(BlendMode.MULTIPLY, glow, innerShadow);

        // Apply the combined effect to the text
        logoText.setEffect(blend);

        // set the color of the text to a neon color
        logoText.setFill(Color.web("#feeb42"));

        // Set the font size of the text
        logoText.setFont(Font.font(180));

        // Create an animation that periodically changes the glow and inner shadow level
        KeyValue kv1 = new KeyValue(glow.levelProperty(), 0.0, Interpolator.EASE_BOTH);
        KeyValue kv2 = new KeyValue(glow.levelProperty(), 1.0, Interpolator.EASE_BOTH);
        KeyValue kv3 = new KeyValue(innerShadow.radiusProperty(), 0.0, Interpolator.EASE_BOTH);
        KeyValue kv4 = new KeyValue(innerShadow.radiusProperty(), 5.0, Interpolator.EASE_BOTH);
        KeyFrame kf1 = new KeyFrame(Duration.ZERO, kv1, kv3);
        KeyFrame kf2 = new KeyFrame(Duration.seconds(1), kv2, kv4);
        KeyFrame kf3 = new KeyFrame(Duration.seconds(2), kv1, kv3);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(kf1, kf2, kf3);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}