package org.twitter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.twitter.Exceptions.*;
import org.twitter.ObjectClasses.Comment;
import org.twitter.ObjectClasses.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CommentsController implements Initializable {
    @FXML
    AnchorPane accessArea;
    @FXML
    TextField typeField;

    Stage thisStage = null;
    Scene thisScene = null;

    static String postID;

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
                    thisScene.getStylesheets().add(this.getClass().getResource("LightModeComments.css").toExternalForm());
                }
                else {
                    thisScene.getStylesheets().clear();
                    thisScene.getStylesheets().add(this.getClass().getResource("DarkModeComments.css").toExternalForm());
                }

                loadComments();

                typeField.setOnKeyPressed(kp -> {
                    typeField.setStyle("-fx-text-fill: #000000");
                    if (kp.getCode().equals(KeyCode.ENTER)) {
                        if (!typeField.getText().equals("")) {
                            try {
                                User.commentOnTweet(postID, typeField.getText());
                                loadComments();
                            }
                            catch (NoUserLoggedInException | UserDoesNotExistException | PostDoesNotExistException | UnauthorisedEditException e) {
                                e.printStackTrace();
                            } catch (PostAlreadyCommentedException e) {
                                typeField.setText("You have already commented on this post!");
                                typeField.setStyle("-fx-text-fill: #ff0000");
                            }
                        }
                    }
                });

                App.theme.addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        if ((int) t1 == 0) {
                            thisScene.getStylesheets().clear();
                            thisScene.getStylesheets().add(this.getClass().getResource("LightModeComments.css").toExternalForm());
                        }
                        else {
                            thisScene.getStylesheets().clear();
                            thisScene.getStylesheets().add(this.getClass().getResource("DarkModeComments.css").toExternalForm());
                        }
                    }
                });
            }
        });
    }

    public void loadComments() {
        accessArea.getChildren().clear();

        double ypos = 0;

        try {
            ArrayList<Comment> comments = Comment.getComments2(postID);

            for (Comment comment : comments) {
                HBox hBox = new HBox();
                hBox.setPrefWidth(accessArea.getPrefWidth());
                hBox.setPrefHeight(thisStage.getHeight() * 4 / 45);
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setSpacing(5);

                Label username = new Label();
                username.setFont(Font.font("Arial Rounded MT Bold", FontWeight.MEDIUM, 14));
                username.setText(comment.getCommentorID());

                hBox.getChildren().add(username);

                TextArea content = new TextArea();
                content.setFont(Font.font("Arial Rounded MT Bold", FontWeight.MEDIUM, 14));
                content.setText(comment.getContent());
                content.setPrefWidth(hBox.getPrefWidth() * 8 / 10);
                content.setPrefHeight(hBox.getPrefHeight());
                content.setStyle("-fx-background-color: #bbbbbb;");
                content.setEditable(false);

                hBox.getChildren().add(content);

                accessArea.getChildren().add(hBox);
                hBox.setLayoutY(ypos);

                ypos += thisStage.getHeight() * 4 / 45;
            }

            accessArea.setPrefHeight(thisStage.getHeight() * comments.size() * 4 / 45 + thisStage.getHeight());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
