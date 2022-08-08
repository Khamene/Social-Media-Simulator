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
import org.twitter.Exceptions.UserDoesNotExistException;
import org.twitter.Exceptions.WrongSecurityAnswerException;
import org.twitter.ObjectClasses.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ForgotPassword2Controller implements Initializable {
    @FXML
    public AnchorPane forgotPassword2;

    @FXML
    public Label forgotPassword2Title;

    @FXML
    public VBox forgotPassword2Vbox;

    @FXML
    ToggleButton lightMode;
    @FXML
    ToggleButton darkMode;

    @FXML
    Label question1;
    @FXML
    Label question2;
    @FXML
    Label status;

    @FXML
    TextField answer1;
    @FXML
    TextField answer2;

    Stage myStage = null;

    static String username = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int q1 = 1, q2 = 1;

        try {
            String[] secQs = User.getSecurityQuestions(username).split("");

            q1 = Integer.parseInt(secQs[0]);
            q2 = Integer.parseInt(secQs[1]);
        }
        catch (UserDoesNotExistException e) {
            status.setText("User with such username does not exist!");
        }

        switch (q1) {
            case 1:
                question1.setText("1- What was the name of your childhood pet?");
                break;
            case 2:
                question1.setText("2- Where did your parents meet the first time?");
                break;
            case 3:
                question1.setText("3- What is the name of your first elementary teacher?");
                break;
            case 4:
                question1.setText("4- What is your favorite sports club?");
                break;
            case 5:
                question1.setText("5- What was your childhood dream job?");
                break;
        }

        switch (q2) {
            case 1:
                question2.setText("1- What was the name of your childhood pet?");
                break;
            case 2:
                question2.setText("2- Where did your parents meet the first time?");
                break;
            case 3:
                question2.setText("3- What is the name of your first elementary teacher?");
                break;
            case 4:
                question2.setText("4- What is your favorite sports club?");
                break;
            case 5:
                question2.setText("5- What was your childhood dream job?");
                break;
        }

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
                myStage = (Stage) forgotPassword2.getScene().getWindow();

                setForgotPassword2VboxDimensions();
                setForgotPassword2TitleDimensions();

                if (App.theme.getValue() == 0) {
                    lightMode.selectedProperty().set(true);
                    darkMode.selectedProperty().set(false);
                    App.getScene().getStylesheets().clear();
                    App.getScene().getStylesheets().add(this.getClass().getResource("LightModeForgotPassword2.css").toExternalForm());
                }
                else if (App.theme.getValue() == 1) {
                    lightMode.selectedProperty().set(false);
                    darkMode.selectedProperty().set(true);
                    App.getScene().getStylesheets().clear();
                    App.getScene().getStylesheets().add(this.getClass().getResource("DarkModeForgotPassword2.css").toExternalForm());
                }

                myStage.widthProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        setForgotPassword2VboxDimensions();
                        setForgotPassword2TitleDimensions();
                    }
                });

                myStage.heightProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        setForgotPassword2VboxDimensions();
                        setForgotPassword2TitleDimensions();
                    }
                });

                App.theme.addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        if ((int) t1 == 0) {
                            App.getScene().getStylesheets().clear();
                            App.getScene().getStylesheets().add(this.getClass().getResource("LightModeForgotPassword2.css").toExternalForm());
                        }
                        else if ((int) t1 == 1) {
                            App.getScene().getStylesheets().clear();
                            App.getScene().getStylesheets().add(this.getClass().getResource("DarkModeForgotPassword2.css").toExternalForm());
                        }
                    }
                });
            }
        });

    }

    public void setForgotPassword2VboxDimensions() {
        forgotPassword2.setPrefWidth(myStage.getWidth());
        forgotPassword2.setPrefHeight(myStage.getHeight());

        forgotPassword2Vbox.setLayoutX(forgotPassword2.getPrefWidth() / 2 - 125);
        forgotPassword2Vbox.setLayoutY(forgotPassword2.getPrefHeight() / 2 - 165);
    }

    public void setForgotPassword2TitleDimensions() {
        forgotPassword2Title.setLayoutX(forgotPassword2.getPrefWidth() / 2 - 203);
        forgotPassword2Title.setLayoutY(forgotPassword2Vbox.getLayoutY() - forgotPassword2Title.getPrefHeight());
    }

    public void backTo1() throws IOException {
        username = null;
        App.setRoot("forgotPassword1");
    }

    public void nextTo3() throws IOException {
        String answer1Txt = answer1.getText();

        Pattern answerCheck = Pattern.compile("\\s*");

        if (answerCheck.matcher(answer1Txt).matches()) {
            status.setText("Security answer may not be null!");
            answer1.clear();
            answer1.requestFocus();
            return;
        }

        String answer2Txt = answer2.getText();

        if (answerCheck.matcher(answer2Txt).matches()) {
            status.setText("Security answer may not be null!");
            answer2.clear();
            answer2.requestFocus();
            return;
        }

        try {
            User.forgotPassword(username, answer1Txt, answer2Txt);
        }
        catch (UserDoesNotExistException e) {
            status.setText("User with such username does not exist!");
            return;
        } catch (WrongSecurityAnswerException e) {
            status.setText("Incorrect answers entered!");
            return;
        }

        App.setRoot("forgotPassword3");
    }
}
