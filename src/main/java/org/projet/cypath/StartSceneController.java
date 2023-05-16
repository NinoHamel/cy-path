package org.projet.cypath;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class StartSceneController {
    private MainGame mainGame;
    private List<Color> selectedColors = new ArrayList<>();
    @FXML
    private ComboBox<Integer> numPlayersComboBox;
    @FXML
    private Pane logoView;
    @FXML
    private StackPane playerSelectionView;
    @FXML
    private ComboBox<Color> player1ColorComboBox;
    @FXML
    private ComboBox<Color> player2ColorComboBox;
    @FXML
    private ComboBox<Color> player3ColorComboBox;
    @FXML
    private ComboBox<Color> player4ColorComboBox;
    @FXML
    private VBox player1ColorBox;
    @FXML
    private VBox player2ColorBox;
    @FXML
    private VBox player3ColorBox;
    @FXML
    private VBox player4ColorBox;
    @FXML
    private StackPane titleScreen;
    @FXML
    private ImageView logoImage;
    @FXML
    private ImageView newGameButton;
    @FXML
    private ImageView loadGameButton;
    @FXML
    private ImageView settingsButton;
    @FXML
    private ImageView quitButton;
    @FXML
    private ImageView newgameImageView;
    @FXML
    private ImageView startImageView;
    @FXML
    private ImageView loadImageView;
    @FXML
    private ImageView settingsImageView;
    @FXML
    private ImageView quitImageView;
    @FXML
    private Text logoText;

    public void setMainGame(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    @FXML
    private void handleStartButtonAction(ActionEvent event) throws IOException {
        mainGame.showGameScene();
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
    private void handleNewGameButtonAction(MouseEvent event) {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), titleScreen);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), playerSelectionView);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        fadeOut.setOnFinished(e -> {
            titleScreen.setVisible(false);
            playerSelectionView.setVisible(true);
            fadeIn.play();
        });

        fadeOut.play();
    }

    @FXML
    private void handleLoadButtonAction(MouseEvent event) {     //Open saves menu
        System.out.println("Load button clicked");
    }

    @FXML
    private void handleSettingsButtonAction(MouseEvent event) { //Open settings
        System.out.println("Settings button clicked");
    }

    @FXML
    private void handleQuitButtonAction(MouseEvent event) {     //Quit the game
        System.out.println("Quit button clicked");
        Platform.exit();
    }


    public void initialize() {
        //numPlayersComboBox.setItems(FXCollections.observableArrayList(2,3, 4));

        File file = new File("src/main/resources/org/projet/cypath/cypath.mp4");
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        mediaView.setFitWidth(logoView.getPrefWidth());
        mediaView.setFitHeight(logoView.getPrefHeight());
        mediaView.setPreserveRatio(true);

        logoView.getChildren().add(mediaView);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), logoView);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), logoView);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        mediaPlayer.setOnEndOfMedia(fadeOut::play);

        fadeOut.setOnFinished(event -> {
            logoView.setVisible(false);
            titleScreen.setVisible(true);
        });

        mediaPlayer.play();

        newgameImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/projet/cypath/newgame.png")))); //load the image
        loadImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/projet/cypath/load.png"))));
        settingsImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/projet/cypath/settings.png"))));
        quitImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/projet/cypath/quit.png"))));
        newgameImageView.setFitHeight(100);           //set the height to 100 for all
        loadImageView.setFitHeight(100);
        settingsImageView.setFitHeight(100);
        quitImageView.setFitHeight(100);
        newgameImageView.setPreserveRatio(true);      //conserve aspect ratio
        loadImageView.setPreserveRatio(true);
        settingsImageView.setPreserveRatio(true);
        quitImageView.setPreserveRatio(true);

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



        /*
        numPlayersComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            player1ColorBox.setVisible(newValue >= 1);
            player2ColorBox.setVisible(newValue >= 2);
            player3ColorBox.setVisible(newValue >= 3);
            player4ColorBox.setVisible(newValue >= 4);
        });


        List<Color> availableColors = Arrays.asList(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        List<ComboBox<Color>> allColorComboBoxes = Arrays.asList(player1ColorComboBox, player2ColorComboBox, player3ColorComboBox, player4ColorComboBox);
        initializeColorComboBox(player1ColorComboBox, allColorComboBoxes, availableColors);
        initializeColorComboBox(player2ColorComboBox, allColorComboBoxes, availableColors);
        initializeColorComboBox(player3ColorComboBox, allColorComboBoxes, availableColors);
        initializeColorComboBox(player4ColorComboBox, allColorComboBoxes, availableColors);
        */
    }

    /*
    private void initializeColorComboBox(ComboBox<Color> colorComboBox, List<ComboBox<Color>> allColorComboBoxes, List<Color> availableColors) {
        colorComboBox.getItems().addAll(availableColors);
        colorComboBox.setCellFactory(new Callback<ListView<Color>, ListCell<Color>>() {
            @Override
            public ListCell<Color> call(ListView<Color> param) {
                return new ListCell<Color>() {
                    @Override
                    protected void updateItem(Color item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            Rectangle colorRectangle = new Rectangle(20, 20, item);
                            setGraphic(colorRectangle);
                            setText(getColorName(item));
                        }
                    }
                };
            }
        });
        colorComboBox.setButtonCell(new ListCell<Color>() {
            @Override
            protected void updateItem(Color item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    Rectangle colorRectangle = new Rectangle(20, 20, item);
                    setGraphic(colorRectangle);
                    setText(getColorName(item));
                }
            }
        });
        colorComboBox.setValue(Color.RED);

        colorComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedColors.add(newValue);
                for (ComboBox<Color> otherComboBox : allColorComboBoxes) {
                    if (otherComboBox != colorComboBox) {
                        otherComboBox.getItems().remove(newValue);
                        if (oldValue != null) {
                            if (!selectedColors.contains(oldValue)) {
                                otherComboBox.getItems().add(oldValue);
                            }
                        }
                    }
                }
                if (oldValue != null) {
                    selectedColors.remove(oldValue);
                }
            }
        });
    }

    private String getColorName(Color color) {
        if (Color.RED.equals(color)) {
            return "Red";
        } else if (Color.GREEN.equals(color)) {
            return "Green";
        } else if (Color.BLUE.equals(color)) {
            return "Blue";
        } else if (Color.YELLOW.equals(color)) {
            return "Yellow";
        } else {
            return "Unknown";
        }
    }
    */

}