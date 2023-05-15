package org.projet.cypath;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public void setMainGame(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    @FXML
    private void handleStartButtonAction(ActionEvent event) throws IOException {
        mainGame.showGameScene();
    }

    public void initialize() {
        numPlayersComboBox.setItems(FXCollections.observableArrayList(2,3, 4));

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
            playerSelectionView.setVisible(true);
        });

        mediaPlayer.play();

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
    }

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


}