package org.twitter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.twitter.Exceptions.NoUserLoggedInException;
import org.twitter.Exceptions.UserDoesNotExistException;
import org.twitter.ObjectClasses.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SettingController implements Initializable {
    Stage myScene=null;
    @FXML
    SplitPane settingPage;
    @FXML
    Circle userNameCir;
    @FXML
    Circle passCir;
    @FXML
    Circle picCir;
    @FXML
    Label wrongPassword;
    @FXML
    TextField securityPassword;
    @FXML
    TextField newUserName;
    @FXML
    TextField newPassword;
    @FXML
    TextField newPicture;
    @FXML
    Label statusLblUser;
    @FXML
    Label statusLblPass;
    @FXML
    Label statusLblPic;
    @FXML
    Label picLbl;
    @FXML
    Label userNameLbl;
    @FXML
    Label passLbl;
    @FXML
    Label reportLbl;
    FileChooser fc=new FileChooser();
    boolean accessebility=false;
    File file;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                myScene = (Stage) settingPage.getScene().getWindow();
                myScene.setResizable(false);
            }
        });
    }
    public void checkPassword(){
        securityPassword.textProperty().addListener(observable -> {
            ///
            //check if password is correct
            //if correct: accessebility= true; reportLbl.setText("")
            ///
            //if wrong set text of wrongPassword Label to "Wrong password"
        });
    }
    public void changeUserName(){
        if(accessebility) {
            String usernameTxt = newUserName.getText();
            Pattern usernamePattern = Pattern.compile("[a-zA-Z]*\\d+[a-zA-Z]*");

            if (usernameTxt.length() < 7 || !usernamePattern.matcher(usernameTxt).matches()) {
                statusLblUser.setText("Username must contain a digit and consist of at least 7 characters!");
                //setStatusLblDimensions();
                newUserName.clear();
                newUserName.requestFocus();
                return;
            }
        }
        else{
            reportLbl.setText("No Access");
        }
    }
    public void changePassword(){
        if(accessebility) {
            String passwordTxt = newPassword.getText();

            if (passwordTxt.length() < 7) {
                statusLblPass.setText("Password must consist of at least 7 characters!");
                //setStatusLblDimensions();
                newPassword.clear();
                newPassword.requestFocus();
                return;
            }
        }
        else{
            reportLbl.setText("No Access");
        }

    }
    public void changeProfilePicture(){
        if(accessebility) {
            try {
                User.changeProfile(file.getName());
            } catch (NoUserLoggedInException e) {
                e.printStackTrace();
            }
        }
        else{
            reportLbl.setText("No Access");
        }
    }
    public void addFile() throws IOException {
        fc.setTitle("Profile Picture");
        file=fc.showOpenDialog(null);
        newPicture.clear();
        if(file!=null) {
            if(file.getName().endsWith(".png")) {
                newPicture.setText(file.getName());
                statusLblPic.setText("");
            }
            else{
                statusLblPic.setText("need to be png format");
            }
        }
    }

}