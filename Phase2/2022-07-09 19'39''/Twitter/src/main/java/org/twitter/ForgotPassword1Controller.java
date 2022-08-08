package org.twitter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ForgotPassword1Controller implements Initializable {

    @FXML
    public AnchorPane forgotPassword1;

    @FXML
    public VBox forgotPassword1Vbox;

    @FXML
    ToggleButton lightMode;
    @FXML
    ToggleButton darkMode;

    @FXML
    TextField username;

    @FXML
    Label status;

    Stage myStage = null;

    ChangeListener<Number> listener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
            setForgotPassword1VboxDimensions();
        }
    };

    ChangeListener<Number> listener2 = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
            if ((int) t1 == 0) {
                App.getScene().getStylesheets().clear();
                App.getScene().getStylesheets().add(this.getClass().getResource("LightModeForgotPassword1.css").toExternalForm());
            }
            else if ((int) t1 == 1) {
                App.getScene().getStylesheets().clear();
                App.getScene().getStylesheets().add(this.getClass().getResource("DarkModeForgotPassword1.css").toExternalForm());
            }
        }
    };

    ChangeListener<Boolean> listener3 = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
            if (t1) {
                myStage.heightProperty().removeListener(listener);
                myStage.widthProperty().removeListener(listener);
                App.theme.removeListener(listener2);

                myStage.widthProperty().addListener(listener);
                myStage.heightProperty().addListener(listener);
                App.theme.addListener(listener2);
            }
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        darkMode.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    App.theme.set(1);
                }
                else {
                    App.theme.set(0);
                }
            }
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                myStage = (Stage) forgotPassword1.getScene().getWindow();

                myStage.focusedProperty().addListener(listener3);

                setForgotPassword1VboxDimensions();

                if (App.theme.getValue() == 0) {
                    lightMode.selectedProperty().set(true);
                    darkMode.selectedProperty().set(false);
                    App.getScene().getStylesheets().clear();
                    App.getScene().getStylesheets().add(this.getClass().getResource("LightModeForgotPassword1.css").toExternalForm());
                }
                else if (App.theme.getValue() == 1) {
                    lightMode.selectedProperty().set(false);
                    darkMode.selectedProperty().set(true);
                    App.getScene().getStylesheets().clear();
                    App.getScene().getStylesheets().add(this.getClass().getResource("DarkModeForgotPassword1.css").toExternalForm());
                }

                myStage.widthProperty().addListener(listener);

                myStage.heightProperty().addListener(listener);

                App.theme.addListener(listener2);
            }
        });

    }

    public void setForgotPassword1VboxDimensions() {
        forgotPassword1.setPrefWidth(myStage.getWidth());
        forgotPassword1.setPrefHeight(myStage.getHeight());

        forgotPassword1Vbox.setLayoutX(forgotPassword1.getPrefWidth() / 2 - 125);
        forgotPassword1Vbox.setLayoutY(forgotPassword1.getPrefHeight() / 2 - 150);
    }

    public void backToLogin() throws IOException {
        ForgotPassword2Controller.username = null;

        myStage.heightProperty().removeListener(listener);
        myStage.widthProperty().removeListener(listener);
        App.theme.removeListener(listener2);
        myStage = null;
        App.setRoot("firstPage");
    }
    
    public void nextTo2() throws IOException {
        String usernameTxt = username.getText();

        Pattern usernamePattern = Pattern.compile("[a-zA-Z]*\\d+[a-zA-Z]*");

        if (!usernamePattern.matcher(usernameTxt).matches()) {
            status.setText("Username must consist of 7 characters and contain at least one number!");
            username.clear();
            username.requestFocus();
            return;
        }

        ForgotPassword2Controller.username = usernameTxt;

        myStage.heightProperty().removeListener(listener);
        myStage.widthProperty().removeListener(listener);
        App.theme.removeListener(listener2);
        myStage = null;
        App.setRoot("forgotPassword2");
    }

}