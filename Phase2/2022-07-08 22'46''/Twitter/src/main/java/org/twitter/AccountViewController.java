package org.twitter;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.twitter.Exceptions.*;
import org.twitter.Functionality.SQLManager;
import org.twitter.ObjectClasses.BusinessUser;
import org.twitter.ObjectClasses.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AccountViewController implements Initializable {
    @FXML
    ScrollPane postViewScrollField;
    @FXML
    ToggleButton followCheck;
    @FXML
    Button directMessage;
    @FXML
    Label profileUserName;
    @FXML
    AnchorPane postViewField;
    @FXML
    Button viewNumberButton;
    @FXML
    Button followNumberButton;
    @FXML
    HBox followViewHBox;
    @FXML
    ImageView profileImage;
    @FXML
    ToggleButton blockUser;
    @FXML

    AnchorPane profileViewPage;
    Stage myScene=null;
    String profileImagePath="";
    static String gotViewedUserName="Mehrshad522";
    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        postViewScrollField.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        setProfileImage();
        setProfileUserName();
        try {
            checkFollowFormat();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        checkBlockFormat();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                myScene = (Stage) profileViewPage.getScene().getWindow();

            }
        });

    }

    public void showFollowNumber(ActionEvent event){
        //follow number
    }

    public void showViewNumber(ActionEvent event){
        //viewNumber only for business account and logged in user = gotViewedUserName
    }

    public void followProcess(){
        try {
            if(followed()){
                try {
                    User.unfollow(gotViewedUserName);
                } catch (UserDoesNotExistException e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    User.sendFollowRequest(gotViewedUserName);
                } catch (UserAlreadyFollowedException | UserDoesNotExistException | UnauthorisedEditException | NoUserLoggedInException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void goToDirectMessage(ActionEvent event) {
        //
        // send to direct message
        //
    }

    public void setProfileImage(){
        try {
            profileImagePath = User.getUserProfile(gotViewedUserName);
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }

        if(!profileImagePath.equals("")) {
            profileImage.setImage(new Image(String.valueOf(this.getClass().getResource(profileImagePath))));
        }
        else{
            profileImage.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon.png"))));
        }
    }

    public void setProfileUserName(){
        profileUserName.setText(gotViewedUserName);
    }

    public void blockProgress() {
        try {
            if(blocked()){
                User.unblockUser(gotViewedUserName);
                checkBlockFormat();
            }
            else{
                try {
                    User.blockUser(gotViewedUserName);
                    checkBlockFormat();
                } catch (UserAlreadyBlockedException e) {
                    e.printStackTrace();
                }
            }
        } catch (UserDoesNotExistException | NoUserLoggedInException | UserNotBlockedException e) {
            e.printStackTrace();
        }
    }

    public void checkBlockFormat(){
        try {
            if(blocked()){
                blockUser.setText("unblock");
            }
            else{
                blockUser.setText("block");
            }
        } catch (UserDoesNotExistException | NoUserLoggedInException e) {
            e.printStackTrace();
        }
    }

    public void checkFollowFormat() throws SQLException {
        if(followed()){
            followCheck.setText("unfollow");
        }
        else {
            followCheck.setText("follow");
        }
    }

    public boolean followed() throws SQLException {
        return SQLManager.checkFollowedAccount(User.getLoggedInUsername(), gotViewedUserName) != -1;
    }

    public boolean blocked() throws UserDoesNotExistException, NoUserLoggedInException {
        return User.getBlocked(gotViewedUserName);
    }

    public void setDimension(){

    }
}
