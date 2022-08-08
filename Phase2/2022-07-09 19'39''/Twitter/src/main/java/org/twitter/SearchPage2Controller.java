package org.twitter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.twitter.Exceptions.NoUserLoggedInException;
import org.twitter.ObjectClasses.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchPage2Controller implements Initializable {
    @FXML
    TextField searchText;
    @FXML
    Circle searchIcon;
    @FXML
    HBox firstResult;
    @FXML
    HBox secondResult;
    @FXML
    HBox thirdResult;
    @FXML
    HBox fourthResult;
    @FXML
    HBox fifthResult;
    @FXML
    AnchorPane searchUserName;
    @FXML
    VBox mainVBox;

    Stage myScene = null;
    Scene thisScene = null;

    int relatedResults = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Node child : mainVBox.getChildren()) {
            if (mainVBox.getChildren().indexOf(child) == 0)
                continue;
            child.setVisible(false);
        }

        searchText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                for (Node child : mainVBox.getChildren()) {
                    if (mainVBox.getChildren().indexOf(child) == 0)
                        continue;

                    child.setOnMouseEntered(null);
                    child.setOnMouseExited(null);
                    child.setOnMouseClicked(null);
                    child.setVisible(false);
                    ((ImageView)((HBox) child).getChildren().get(0)).setImage(null);
                    ((Label)((HBox) child).getChildren().get(1)).setText("");
                }

                if (!t1.equals(""))
                    showResults(t1);
            }
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                myScene = (Stage) searchUserName.getScene().getWindow();
                thisScene = searchUserName.getScene();

                myScene.setResizable(false);

                if (App.theme.getValue() == 0) {
                    thisScene.getStylesheets().clear();
                    thisScene.getStylesheets().add(this.getClass().getResource("LightModeSearch1.css").toExternalForm());
                    searchIcon.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("lightModeSearchIcons.png")))));

                }
                else {
                    thisScene.getStylesheets().clear();
                    thisScene.getStylesheets().add(this.getClass().getResource("DarkModeSearch1.css").toExternalForm());
                    searchIcon.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("darkModeSearchIcons.png")))));
                }

                App.theme.addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        if ((int) t1 == 0) {
                            thisScene.getStylesheets().clear();
                            thisScene.getStylesheets().add(this.getClass().getResource("LightModeSearch1.css").toExternalForm());
                            searchIcon.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("lightModeSearchIcons.png")))));

                        }
                        else {
                            thisScene.getStylesheets().clear();
                            thisScene.getStylesheets().add(this.getClass().getResource("DarkModeSearch1.css").toExternalForm());
                            searchIcon.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("darkModeSearchIcons.png")))));
                        }
                    }
                });
            }
        });
    }

    public void showResults(String content) {
        try {
            ArrayList<User> foundUsers = User.searchUserAll(content);

            relatedResults = foundUsers.size();

            for (int i = 1; i <= Math.min(relatedResults, 7); i++) {
                HBox currentHBox = (HBox) (mainVBox.getChildren().get(i));
                Label username = (Label) (currentHBox.getChildren().get(1));
                currentHBox.setVisible(true);
                username.setText(foundUsers.get(i - 1).getUsername());

                if (foundUsers.get(i - 1).getPhotoPath() != null) {
                    ((ImageView) currentHBox.getChildren().get(0)).setImage(new Image(String.valueOf
                            (this.getClass().getResource(foundUsers.get(i - 1).getPhotoPath()))));
                } else {
                    ((ImageView) currentHBox.getChildren().get(0)).setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon1.png"))));
                }

                currentHBox.setOnMouseEntered(me -> {
                    if (App.theme.getValue() == 0)
                        currentHBox.setStyle("-fx-background-color: #cccccc;");
                    else
                        currentHBox.setStyle("-fx-background-color: #aaaaaa");
                });

                currentHBox.setOnMouseExited(me -> {
                    currentHBox.setStyle("-fx-background-color: transparent");
                });

                currentHBox.setOnMouseClicked(me -> {
                    AccountViewController.gotViewedUserName = ((Label)currentHBox.getChildren().get(1)).getText();
                    try {
                        App.setRoot("viewProfile");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    myScene.close();
                });
            }
        }
        catch (NoUserLoggedInException e) {
            e.printStackTrace();
        }
    }
}
