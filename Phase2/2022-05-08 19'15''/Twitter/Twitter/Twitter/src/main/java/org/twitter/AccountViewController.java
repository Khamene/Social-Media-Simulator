package org.twitter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        postViewScrollField.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

    }
    public void showFollowNumebr(){

    }
    public void showViewNumebr(){

    }
    public void followProcess(){}
    public void goToDirectMessage(ActionEvent event) {
    }

}
