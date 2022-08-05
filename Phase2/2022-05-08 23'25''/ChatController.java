package org.twitter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.twitter.Exceptions.NoUserLoggedInException;
import org.twitter.Exceptions.UserDoesNotExistException;
import org.twitter.ObjectClasses.Chat;
import org.twitter.ObjectClasses.Like;
import org.twitter.ObjectClasses.User;
import org.w3c.dom.events.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML
    AnchorPane chatNameAnchor;

    @FXML
    HBox chatNameHBox;

    @FXML
    ScrollPane chatNameScroll;

    @FXML
    VBox chatsVBox;

    @FXML
    AnchorPane leftSide;

    @FXML
    AnchorPane mainSide;

    @FXML
    AnchorPane rightSide;

    @FXML
    AnchorPane main;

    @FXML
    SplitPane splitPane;

    Stage myStage = null;

    ArrayList<Chat> chats = new ArrayList<>();
    ArrayList<HBox> chatNameHolders = new ArrayList<>();
    String clickedHBoxID = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            chats = Chat.getChats();
        } catch (NoUserLoggedInException | UserDoesNotExistException e) {
            e.printStackTrace();
        }

        System.out.println(chats.size());

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                myStage = (Stage) main.getScene().getWindow();

                setDimensions();

                myStage.widthProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        setDimensions();
                    }
                });

                myStage.heightProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        setDimensions();
                    }
                });
            }
        });
    }

    public void setDimensions() {
        splitPane.setPrefWidth(myStage.getWidth());
        splitPane.setPrefHeight(myStage.getHeight());

        chatNameScroll.setPrefWidth(rightSide.getWidth());
        chatNameScroll.setPrefWidth(chatNameScroll.getPrefWidth() * 95 / 100);
        chatNameScroll.setPrefHeight(myStage.getHeight());

        chatNameAnchor.setPrefWidth(chatNameScroll.getPrefWidth());

        double yPos = 0;

        chatNameAnchor.getChildren().clear();

        for (int i = 1; i < 20; i++) {
            for (Chat chat : chats) {
                HBox newHBox = new HBox();
                chatNameHolders.add(newHBox);

                newHBox.setPrefWidth(chatNameScroll.getPrefWidth());
                newHBox.setPrefHeight(myStage.getHeight() * 4 / 45);
                newHBox.setAlignment(Pos.CENTER_LEFT);
                newHBox.setId("chatNameContainer" + (chats.indexOf(chat) + 1)*i);

                ImageView profilePhoto = new ImageView();
                if (chat.getPhotoPath() != null) {
                    Image myImage = new Image(chat.getPhotoPath());
                    profilePhoto.setImage(myImage);
                }
                else {
                    Image myImage = new Image(String.valueOf(this.getClass().getResource("Cool-icon.png")));
                    profilePhoto.setImage(myImage);
                }
                profilePhoto.setFitWidth(myStage.getHeight() * 4 / 45);
                profilePhoto.setFitHeight(myStage.getHeight() * 4 / 45);
                profilePhoto.setId("chatPhoto");

                newHBox.getChildren().add(profilePhoto);

                Label chatName = new Label();
                chatName.setFont(Font.font("Arial", FontWeight.MEDIUM, 14));
                chatName.setAlignment(Pos.CENTER_LEFT);
                chatName.setText(chat.getName());
                chatName.setId("chatName");

                newHBox.getChildren().add(chatName);
                newHBox.setSpacing(rightSide.getWidth() / 15);

                newHBox.setLayoutY(yPos);

                yPos += myStage.getHeight() * 4 / 45;

                Line separator = new Line();
                separator.setStartX(chatNameAnchor.getLayoutX());
                separator.setEndX(chatNameAnchor.getLayoutX() + chatNameAnchor.getWidth());
                separator.setStartY(yPos);
                separator.setEndY(yPos);
                separator.setStrokeWidth(1);
                yPos++;

                newHBox.setOnMouseEntered(me -> {
                    try {
                        if (!clickedHBoxID.equals(newHBox.getId()))
                            newHBox.setStyle("-fx-background-color: #dddddd;");
                    }
                    catch (NullPointerException e) {
                        newHBox.setStyle("-fx-background-color: #dddddd;");
                    }
                });
                newHBox.setOnMouseExited(me -> {
                    try {
                        if (!clickedHBoxID.equals(newHBox.getId()))
                            newHBox.setStyle("-fx-background-color: #ffffff;");
                    }
                    catch (NullPointerException e) {
                        newHBox.setStyle("-fx-background-color: #ffffff;");
                    }
                });
                newHBox.setOnMouseClicked(mc -> {
                    for (HBox chatNameHolder : chatNameHolders) {
                        chatNameHolder.setStyle("-fx-background-color: #ffffff;");
                        chatNameHolder.getChildren().get(1).setStyle("-fx-text-fill: #000000");
                    }

                    newHBox.setStyle("-fx-background-color: #0571ED;");
                    newHBox.getChildren().get(1).setStyle("-fx-text-fill: #ffffff");
                    clickedHBoxID = newHBox.getId();
                });

                chatNameAnchor.getChildren().add(newHBox);
                chatNameAnchor.getChildren().add(separator);
            }
        }


        chatNameAnchor.setPrefHeight(20 * chats.size() * myStage.getHeight() * 4 / 45 + 100);
    }
}
