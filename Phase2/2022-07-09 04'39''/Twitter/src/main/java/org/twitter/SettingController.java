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
    Stage myScene=new Stage();
    @FXML
    AnchorPane leftSideAnchor;
    @FXML
    AnchorPane rightSideAnchor;
    @FXML
    ToggleButton darkMode;
    @FXML
    ToggleButton lightMode;
    @FXML
    ToggleButton allChats;
    @FXML
    ToggleButton personChat;
    @FXML
    ToggleButton groupChat;
    @FXML
    Button settingButton;
    @FXML
    Button insightButton;
    @FXML
    Button notificationButton;
    @FXML
    Button messageButton;
    @FXML
    Button homeButton;
    @FXML
    Button searchButton;
    @FXML
    Button postButton;
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
    ImageView homeImage;
    @FXML
    ImageView notificationImage;
    @FXML
    ImageView messImage;
    @FXML
    ImageView settingImage;
    @FXML
    ImageView insightImage;
    @FXML
    ImageView searchImage;
    @FXML
    ImageView postImage;
    @FXML
    Label picLbl;
    @FXML
    Label userNameLbl;
    @FXML
    Label passLbl;
    @FXML
    Label reportLbl;
    @FXML
    Label appTitle;
    FileChooser fc=new FileChooser();
    boolean accessebility=false;
    File file;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        settingButton.setDisable(true);
        if(!insightNeed()){
            insightButton.setDisable(true);
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                myScene = (Stage) settingPage.getScene().getWindow();
                myScene.widthProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        setDimension();
                    }
                });
                myScene.heightProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        setDimension();
                    }
                });
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
    public void clickedOnHome(){
//send to Home
    }
    public void clickedOnNotification(){
//Send to notification
    }
    public void clickedOnSetting(){
//send to setting
    }
    public void clickedOnNewPost(){
//send to post page
    }
    public void clickedOnMessages(){
//send to messages
    }
    public void clickedOnInsight(){
//send to insight
    }
    public void clickedOnSearchUser(){
//send to search user
    }
    public void toLightTheme(){

        //change to theme to light
    }
    public void setDimension(){

        appTitle.setPrefWidth(158*myScene.getWidth()/800);
        appTitle.setPrefHeight(40*myScene.getHeight()/450);
        homeImage.setFitWidth(myScene.getWidth()/20);
        homeImage.setFitHeight(40*myScene.getHeight()/450);
        notificationImage.setFitWidth(myScene.getWidth()/20);
        notificationImage.setFitHeight(40*myScene.getHeight()/450);
        settingImage.setFitWidth(myScene.getWidth()/20);
        settingImage.setFitHeight(40*myScene.getHeight()/450);
        messImage.setFitWidth(myScene.getWidth()/20);
        messImage.setFitHeight(40*myScene.getHeight()/450);
        insightImage.setFitWidth(myScene.getWidth()/20);
        insightImage.setFitHeight(40*myScene.getHeight()/450);
        searchImage.setFitWidth(myScene.getWidth()/20);
        searchImage.setFitHeight(40*myScene.getHeight()/450);
        postImage.setFitWidth(myScene.getWidth()/20);
        postImage.setFitHeight(40*myScene.getHeight()/450);
        homeButton.setPrefWidth(158*myScene.getWidth()/800);
        homeButton.setPrefHeight(40*myScene.getHeight()/400);
        notificationButton.setPrefWidth(158*myScene.getWidth()/800);
        notificationButton.setPrefHeight(40*myScene.getHeight()/400);
        messageButton.setPrefWidth(158*myScene.getWidth()/800);
        messageButton.setPrefHeight(40*myScene.getHeight()/400);
        settingButton.setPrefWidth(158*myScene.getWidth()/800);
        settingButton.setPrefHeight(40*myScene.getHeight()/400);
        insightButton.setPrefWidth(158*myScene.getWidth()/800);
        insightButton.setPrefHeight(40*myScene.getHeight()/400);
        searchButton.setPrefWidth(158*myScene.getWidth()/800);
        searchButton.setPrefHeight(40*myScene.getHeight()/400);
        postButton.setPrefWidth(158*myScene.getWidth()/800);
        postButton.setPrefHeight(40*myScene.getHeight()/400);
        allChats.setPrefWidth(61*myScene.getWidth()/800);
        allChats.setPrefHeight(38*myScene.getHeight()/450);
        personChat.setPrefWidth(61*myScene.getWidth()/800);
        personChat.setPrefHeight(38*myScene.getHeight()/450);
        groupChat.setPrefWidth(61*myScene.getWidth()/800);
        groupChat.setPrefHeight(38*myScene.getHeight()/450);
        lightMode.setPrefWidth(61*myScene.getWidth()/800);
        darkMode.setPrefHeight(38*myScene.getHeight()/450);
        leftSideAnchor.setPrefWidth(myScene.getWidth()/8);
        leftSideAnchor.setPrefHeight(myScene.getHeight()/5);
        rightSideAnchor.setPrefWidth(myScene.getWidth()/8);
        rightSideAnchor.setPrefHeight(myScene.getHeight()/5);
        userNameCir.setRadius(15*Math.sqrt(((myScene.getWidth()*myScene.getWidth()+myScene.getHeight()
                *myScene.getHeight())/800*800+450*450)));
        picCir.setRadius(15*Math.sqrt(((myScene.getWidth()*myScene.getWidth()+myScene.getHeight()
                *myScene.getHeight())/800*800+450*450)));
        passCir.setRadius(15*Math.sqrt(((myScene.getWidth()*myScene.getWidth()+myScene.getHeight()
                *myScene.getHeight())/800*800+450*450)));


    }
    public boolean insightNeed(){
        try {
            return User.getUserType(User.getLoggedInUsername());
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }
        return false;
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
