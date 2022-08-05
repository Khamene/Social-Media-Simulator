package org.twitter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class ForgotPasswordController {

    @FXML
    public AnchorPane forgotPassword1;

    @FXML
    public AnchorPane forgotPassword2;

    @FXML
    public AnchorPane forgotPassword3;

    @FXML
    public Label forgotPassword2Title;

    @FXML
    public VBox forgotPassword1Vbox;

    @FXML
    public VBox forgotPassword2Vbox;

    @FXML
    public VBox forgotPassword3Vbox;

    public void setForgotPassword1VboxDimensions() {
        forgotPassword1Vbox.setLayoutX(forgotPassword1.getWidth() / 2 - 125);
        forgotPassword1Vbox.setLayoutY(forgotPassword1.getHeight() / 2 - 150);
    }

    public void setForgotPassword2VboxDimensions() {
        forgotPassword2Vbox.setLayoutX(forgotPassword2.getWidth() / 2 - 125);
        forgotPassword2Vbox.setLayoutY(forgotPassword2.getHeight() / 2 - 165);
    }

    public void setForgotPassword2TitleDimensions() {
        forgotPassword2Title.setLayoutX(forgotPassword2.getWidth() / 2 - 203);
        forgotPassword2Title.setLayoutY(forgotPassword2Vbox.getLayoutY() - forgotPassword2Title.getHeight());
    }

    public void setForgotPassword3VboxDimensions() {
        forgotPassword3Vbox.setLayoutX(forgotPassword3.getWidth() / 2 - 125);
        forgotPassword3Vbox.setLayoutY(forgotPassword3.getHeight() / 2 - 165);
    }
}