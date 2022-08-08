package org.twitter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.twitter.Exceptions.FollowRequestDoesNotExistException;
import org.twitter.Exceptions.NoUserLoggedInException;
import org.twitter.Exceptions.UserDoesNotExistException;
import org.twitter.ObjectClasses.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NotificationsController implements Initializable {
    @FXML
    AnchorPane accessArea;

    Stage thisStage = null;
    Scene thisScene = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                thisScene = accessArea.getScene();
                thisStage = (Stage) thisScene.getWindow();

                thisStage.setResizable(false);

                if (App.theme.getValue() == 0) {
                    thisScene.getStylesheets().clear();
                    thisScene.getStylesheets().add(this.getClass().getResource("LightModeNotifications.css").toExternalForm());
                }
                else
                {
                    thisScene.getStylesheets().clear();
                    thisScene.getStylesheets().add(this.getClass().getResource("DarkModeNotifications.css").toExternalForm());
                }

                loadNotifications();

                App.theme.addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        if ((int)t1 == 0) {
                            thisScene.getStylesheets().clear();
                            thisScene.getStylesheets().add(this.getClass().getResource("LightModeNotifications.css").toExternalForm());
                            loadNotifications();
                        }
                        else {
                            thisScene.getStylesheets().clear();
                            thisScene.getStylesheets().add(this.getClass().getResource("DarkModeNotifications.css").toExternalForm());
                            loadNotifications();
                        }
                    }
                });
            }
        });
    }

    public void loadNotifications() {
        accessArea.getChildren().clear();

        double yPos = 0;

        try {
            ArrayList<User> followUsers = User.getFollowRequests();

            for (User followUser : followUsers) {
                HBox hBox = new HBox();
                hBox.setPrefWidth(accessArea.getPrefWidth());
                hBox.setPrefHeight(thisStage.getHeight() * 4 / 45);
                hBox.setSpacing(10);
                hBox.setStyle("-fx-padding: 0 0 0 15");
                hBox.setAlignment(Pos.CENTER_LEFT);

                ImageView photo = new ImageView();
                if (followUser.getPhotoPath() != null)
                    photo.setImage(new Image(String.valueOf(this.getClass().getResource(followUser.getPhotoPath()))));
                else {
                    if (App.theme.getValue() == 0)
                        photo.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon.png"))));
                    else
                        photo.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon2.png"))));

                }
                photo.setFitWidth(40);
                photo.setFitHeight(40);

                hBox.getChildren().add(photo);

                Label username = new Label();
                username.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
                username.setText(followUser.getUsername());
                username.setId("username");

                hBox.getChildren().add(username);

                Label useless = new Label();
                useless.setPrefWidth(hBox.getPrefWidth() / 3);

                hBox.getChildren().add(useless);

                Button acceptBtn = new Button();
                acceptBtn.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
                acceptBtn.setText("Accept");
                acceptBtn.setId("acceptBtn");

                acceptBtn.setOnAction(ae -> {
                    try {
                        User.acceptFollowRequest(followUser.getUsername());
                        loadNotifications();
                    }
                    catch (NoUserLoggedInException | UserDoesNotExistException | FollowRequestDoesNotExistException e) {
                        e.printStackTrace();
                    }
                });

                hBox.getChildren().add(acceptBtn);

                accessArea.getChildren().add(hBox);

                hBox.setLayoutY(yPos);

                yPos += thisStage.getHeight() * 4 / 45;

            }

            accessArea.setPrefHeight(followUsers.size() * thisStage.getHeight() * 4 / 45 + thisStage.getHeight());
        }
        catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }
    }
}
