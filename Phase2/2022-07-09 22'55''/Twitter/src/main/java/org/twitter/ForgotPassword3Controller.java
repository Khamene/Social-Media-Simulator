package org.twitter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.twitter.Exceptions.UserDoesNotExistException;
import org.twitter.Exceptions.WrongSecurityAnswerException;
import org.twitter.ObjectClasses.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForgotPassword3Controller implements Initializable {
    @FXML
    public AnchorPane forgotPassword3;

    @FXML
    public VBox forgotPassword3Vbox;

    @FXML
    ToggleButton lightMode;
    @FXML
    ToggleButton darkMode;

    @FXML
    Label formatInvalid;
    @FXML
    Label misMatch;

    @FXML
    PasswordField password1;
    @FXML
    PasswordField password2;

    Stage myStage = null;

    ChangeListener<Number> listener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
            setForgotPassword3VboxDimensions();
        }
    };

    ChangeListener<Number> listener2 = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
            if ((int) t1 == 0) {
                App.getScene().getStylesheets().clear();
                App.getScene().getStylesheets().add(this.getClass().getResource("LightModeForgotPassword3.css").toExternalForm());
            }
            else if ((int) t1 == 1) {
                App.getScene().getStylesheets().clear();
                App.getScene().getStylesheets().add(this.getClass().getResource("DarkModeForgotPassword3.css").toExternalForm());
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
                myStage = (Stage) forgotPassword3.getScene().getWindow();

                myStage.focusedProperty().addListener(listener3);

                setForgotPassword3VboxDimensions();

                if (App.theme.getValue() == 0) {
                    lightMode.selectedProperty().set(true);
                    darkMode.selectedProperty().set(false);
                    App.getScene().getStylesheets().clear();
                    App.getScene().getStylesheets().add(this.getClass().getResource("LightModeForgotPassword3.css").toExternalForm());
                }
                else if (App.theme.getValue() == 1) {
                    lightMode.selectedProperty().set(false);
                    darkMode.selectedProperty().set(true);
                    App.getScene().getStylesheets().clear();
                    App.getScene().getStylesheets().add(this.getClass().getResource("DarkModeForgotPassword3.css").toExternalForm());
                }

                myStage.widthProperty().addListener(listener);

                myStage.heightProperty().addListener(listener);

                App.theme.addListener(listener2);
            }
        });

    }

    public void setForgotPassword3VboxDimensions() {
        forgotPassword3.setPrefWidth(myStage.getWidth());
        forgotPassword3.setPrefHeight(myStage.getHeight());

        forgotPassword3Vbox.setLayoutX(forgotPassword3.getPrefWidth() / 2 - 125);
        forgotPassword3Vbox.setLayoutY(forgotPassword3.getPrefHeight() / 2 - 165);
    }

    public void finish() throws IOException {
        String password1Txt = password1.getText();

        if (password1Txt.length() < 7) {
            formatInvalid.setText("Password must be at least 7 characters long!");
            password1.clear();
            password1.requestFocus();
            return;
        }

        String password2Txt = password2.getText();

        if (!password1Txt.equals(password2Txt)) {
            misMatch.setText("Entered passwords do not match!");
            password2.clear();
            password2.requestFocus();
            return;
        }

        try {
            User.changePasswordAfterForgotPassword(ForgotPassword2Controller.username, password1Txt);
        } catch (WrongSecurityAnswerException e) {
            e.printStackTrace();
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }

        ForgotPassword2Controller.username = null;

        myStage.heightProperty().removeListener(listener);
        myStage.widthProperty().removeListener(listener);
        App.theme.removeListener(listener2);
        myStage = null;
        App.setRoot("firstPage");
    }

    public void backTo2() throws IOException {
        myStage.heightProperty().removeListener(listener);
        myStage.widthProperty().removeListener(listener);
        App.theme.removeListener(listener2);
        myStage = null;
        App.setRoot("forgotPassword2");
    }
}
