package org.twitter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.twitter.Exceptions.UserDoesNotExistException;
import org.twitter.ObjectClasses.Comment;
import org.twitter.ObjectClasses.Like;
import org.twitter.ObjectClasses.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Insights2Controller implements Initializable {
    @FXML
    private AnchorPane accessArea1;
    @FXML
    private AnchorPane accessArea2;

    Stage thisStage;
    Scene thisScene;

    static String username = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                thisScene = accessArea1.getScene();
                thisStage = (Stage) thisScene.getWindow();

                thisStage.setResizable(false);

                loadInsights();

                if (App.theme.getValue() == 0) {
                    thisScene.getStylesheets().clear();
                    thisScene.getStylesheets().add(this.getClass().getResource("LightModeInsights.css").toExternalForm());
                }
                else
                {
                    thisScene.getStylesheets().clear();
                    thisScene.getStylesheets().add(this.getClass().getResource("DarkModeInsights.css").toExternalForm());
                }

                App.theme.addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        if ((int)t1 == 0) {
                            thisScene.getStylesheets().clear();
                            thisScene.getStylesheets().add(this.getClass().getResource("LightModeInsights.css").toExternalForm());
                            loadInsights();
                        }
                        else {
                            thisScene.getStylesheets().clear();
                            thisScene.getStylesheets().add(this.getClass().getResource("DarkModeInsights.css").toExternalForm());
                            loadInsights();
                        }
                    }
                });
            }
        });
    }

    public void loadInsights() {
        accessArea1.getChildren().clear();

        double ypos = 0;

        try {
            ArrayList<Like> likes = Like.getLikes(username);

            for (Like like : likes) {
                HBox hBox = new HBox();
                hBox.setPrefWidth(accessArea1.getPrefWidth());
                hBox.setPrefHeight(thisStage.getHeight() * 4 / 45);
                hBox.setSpacing(10);
                hBox.setStyle("-fx-padding: 0 0 0 15");
                hBox.setAlignment(Pos.CENTER_LEFT);

                Label likeDate = new Label();
                likeDate.setFont(Font.font("Arial Rounded MT Bold", FontWeight.EXTRA_BOLD, 14));
                likeDate.setText(like.getDateLiked().toString());
                likeDate.setId("likeDate");

                hBox.getChildren().add(likeDate);

                Label likeNum = new Label();
                likeNum.setFont(Font.font("Arial Rounded MT Bold", FontWeight.EXTRA_BOLD, 14));
                likeNum.setText(Integer.toString(like.getLikeNum()));
                likeNum.setId("likeNum");

                hBox.getChildren().add(likeNum);

                accessArea1.getChildren().add(hBox);

                Separator sep = new Separator();

                hBox.setLayoutY(ypos);

                ypos += thisStage.getHeight() * 4 / 45;

                sep.setLayoutY(ypos);
                sep.setPrefWidth(accessArea1.getPrefWidth() - 100);
                sep.setLayoutX(accessArea1.getPrefWidth()/2 - sep.getPrefWidth()/2);

                accessArea1.getChildren().add(sep);
            }

            accessArea1.setPrefHeight(likes.size() * thisStage.getHeight() * 4 / 45 + thisStage.getHeight());
        }
        catch (UserDoesNotExistException | SQLException e) {
            e.printStackTrace();
        }

        ypos = 0;

        try {
            ArrayList<Comment> comments = Comment.getComments(username);

            for (Comment comment : comments) {
                HBox hBox = new HBox();
                hBox.setPrefWidth(accessArea2.getPrefWidth());
                hBox.setPrefHeight(thisStage.getHeight() * 4 / 45);
                hBox.setSpacing(10);
                hBox.setStyle("-fx-padding: 0 0 0 15");
                hBox.setAlignment(Pos.CENTER_LEFT);

                Label commentDate = new Label();
                commentDate.setFont(Font.font("Arial Rounded MT Bold", FontWeight.EXTRA_BOLD, 14));
                commentDate.setText(comment.getDateCommented().toString());
                commentDate.setId("commentDate");

                hBox.getChildren().add(commentDate);

                Label commentNum = new Label();
                commentNum.setFont(Font.font("Arial Rounded MT Bold", FontWeight.EXTRA_BOLD, 14));
                commentNum.setText(Integer.toString(comment.getCommentNum()));
                commentNum.setId("commentNum");

                hBox.getChildren().add(commentNum);

                accessArea2.getChildren().add(hBox);

                Separator sep = new Separator();

                hBox.setLayoutY(ypos);

                ypos += thisStage.getHeight() * 4 / 45;

                sep.setLayoutY(ypos);
                sep.setPrefWidth(accessArea2.getPrefWidth() - 100);
                sep.setLayoutX(accessArea2.getPrefWidth()/2 - sep.getPrefWidth()/2);

                accessArea2.getChildren().add(sep);
            }

            accessArea2.setPrefHeight(thisStage.getHeight() * comments.size() * 4 / 45 + thisStage.getHeight());
        }
        catch (UserDoesNotExistException | SQLException e) {
            e.printStackTrace();
        }
    }
}
