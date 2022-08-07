package org.twitter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.twitter.Exceptions.*;
import org.twitter.ObjectClasses.Group;
import org.twitter.ObjectClasses.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchPage1Controller implements Initializable {
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
            ((Hyperlink)((HBox) child).getChildren().get(3)).setOnAction(null);
        }

        searchText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                for (Node child : mainVBox.getChildren()) {
                    if (mainVBox.getChildren().indexOf(child) == 0)
                        continue;

                    child.setVisible(false);
                    ((Hyperlink)((HBox) child).getChildren().get(3)).setOnAction(null);
                    ((Label)((HBox) child).getChildren().get(1)).setText("");
                    ((ImageView)((HBox) child).getChildren().get(0)).setImage(null);
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
            ArrayList<User> foundUsers = User.searchUser(content);

            for (int i = 0; i < foundUsers.size(); i++) {
                User foundUser = foundUsers.get(i);

                try {
                    Group.checkInGroup(User.getUserID(foundUser.getUsername()), Group.getGroupID(GroupInfoController.myChat.getName()));
                    foundUsers.remove(foundUser);
                    i--;
                }
                catch (UserNotMemberException e) {
//                    e.printStackTrace();
                } catch (GroupDoesNotExistException e) {
                    e.printStackTrace();
                }
            }

            relatedResults = foundUsers.size();

            for (int i = 1; i <= Math.min(relatedResults, 7); i++) {
                HBox currentHBox = (HBox) (mainVBox.getChildren().get(i));
                Label username = (Label) (currentHBox.getChildren().get(1));
                currentHBox.setVisible(true);
                username.setText(foundUsers.get(i-1).getUsername());

                if (foundUsers.get(i-1).getPhotoPath() != null) {
                    ((ImageView) currentHBox.getChildren().get(0)).setImage(new Image(String.valueOf
                            (this.getClass().getResource(foundUsers.get(i-1).getPhotoPath()))));
                }
                else {
                    ((ImageView) currentHBox.getChildren().get(0)).setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon1.png"))));
                }

                Hyperlink addBtn = (Hyperlink)currentHBox.getChildren().get(3);
                addBtn.setDisable(false);
                addBtn.setOnAction(ae -> {
                    try {
                        Group.addMember(User.getUserID(username.getText()), User.getUserID(User.getLoggedInUsername()), GroupInfoController.myChat.getName());
                        GroupInfoController.myGroup = Group.getGroup(GroupInfoController.myChat.getName());
                        GroupInfoController.toggleChange.set(!GroupInfoController.toggleChange.getValue());
                        addBtn.setDisable(true);
                    }
                    catch (UserNotAdminException e) {
                        e.printStackTrace();
                    }
                    catch (GroupDoesNotExistException e) {
                        e.printStackTrace();
                    }
                    catch (UserDoesNotExistException e) {
                        e.printStackTrace();
                    }
                    catch (UnauthorisedEditException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        catch (NoUserLoggedInException | UserDoesNotExistException e) {
            e.printStackTrace();
        }
    }
}
