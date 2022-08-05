package org.twitter;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountViewController implements Initializable {
    @FXML
    ScrollPane postViewScrollField;
    @FXML
    CheckBox followCheck;
    @FXML
    Button directMessage;
    @FXML
    Label profileUserName;
    @FXML
    AnchorPane postViewField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        postViewScrollField.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

    }
}
