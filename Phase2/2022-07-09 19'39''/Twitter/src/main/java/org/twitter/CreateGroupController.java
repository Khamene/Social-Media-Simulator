package org.twitter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.twitter.Exceptions.GroupAlreadyExists;
import org.twitter.Exceptions.NoUserLoggedInException;
import org.twitter.Exceptions.UserDoesNotExistException;
import org.twitter.ObjectClasses.Chat;
import org.twitter.ObjectClasses.User;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateGroupController implements Initializable {

    @FXML
    private TextField groupName;

    @FXML
    private Label statusLbl;

    Stage thisStage = null;
    Scene thisScene = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                thisScene = groupName.getScene();
                thisStage = (Stage) thisScene.getWindow();
                thisStage.setResizable(false);

                if (App.theme.getValue() == 0) {
                    thisScene.getStylesheets().clear();
                    thisScene.getStylesheets().add(this.getClass().getResource("LightModeCreateGroup.css").toExternalForm());
                }
                else {
                    thisScene.getStylesheets().clear();
                    thisScene.getStylesheets().add(this.getClass().getResource("DarkModeCreateGroup.css").toExternalForm());
                }

                App.theme.addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        if ((int) t1 == 0) {
                            thisScene.getStylesheets().clear();
                            thisScene.getStylesheets().add(this.getClass().getResource("LightModeCreateGroup.css").toExternalForm());
                        }
                        else {
                            thisScene.getStylesheets().clear();
                            thisScene.getStylesheets().add(this.getClass().getResource("DarkModeCreateGroup.css").toExternalForm());
                        }
                    }
                });
            }
        });
    }

    public void createGroup(ActionEvent event) {
        String groupNameTxt = groupName.getText();

        if (groupNameTxt.equals("")) {
            statusLbl.setText("You can not set the group name to null");
            return;
        }

        try {
            User.createNewGroup(groupNameTxt);
            ChatController.chats = Chat.getChats();
            ChatController.toggleChange.set(!ChatController.toggleChange.getValue());
            thisStage.close();
        }
        catch (NoUserLoggedInException e) {
            e.printStackTrace();
        }
        catch (UserDoesNotExistException e) {
            e.printStackTrace();
        } catch (GroupAlreadyExists e) {
            statusLbl.setText("duplicate group with such name already exists");
            return;
        }

    }
}
