package org.twitter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.twitter.App;
import org.twitter.ObjectClasses.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


public class SecurityQuestionsController implements Initializable {

    @FXML
    public AnchorPane securityQuestions;

    @FXML
    public VBox securityQuestionsVbox;

    @FXML
    public ChoiceBox<String> firstQuestionBox;

    @FXML
    public ChoiceBox<String> secondQuestionBox;

    @FXML
    public Button finishButton;

    @FXML
    public HBox securityQuestionsHbox;

    @FXML
    ToggleButton lightMode;
    @FXML
    ToggleButton darkMode;

    @FXML
    Label sameQuestions;
    @FXML
    Label ans1Invalid;
    @FXML
    Label ans2Invalid;

    @FXML
    TextField answer1;
    @FXML
    TextField answer2;

    Stage myScene = null;

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

        ArrayList<String> qustions = new ArrayList<>();
        qustions.add("1- What was the name of your childhood pet?");
        qustions.add("2- Where did your parents meet the first time?");
        qustions.add("3- What is the name of your first elementary teacher?");
        qustions.add("4- What is your favorite sports club?");
        qustions.add("5- What was your childhood dream job?");

        firstQuestionBox.getItems().addAll(qustions);
        secondQuestionBox.getItems().addAll(qustions);

        firstQuestionBox.setValue(qustions.get(0));
        secondQuestionBox.setValue(qustions.get(1));

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                myScene = (Stage) securityQuestions.getScene().getWindow();

                setSecurityQuestionsVboxDimensions();
                setFinishButtonDimensions();
                setSecurityQuestionsHboxDimensions();

                if (App.theme.getValue() == 0) {
                    lightMode.selectedProperty().set(true);
                    darkMode.selectedProperty().set(false);
                    App.getScene().getStylesheets().clear();
                    App.getScene().getStylesheets().add(this.getClass().getResource("LightModeSecurityQuestions.css").toExternalForm());
                }
                else if (App.theme.getValue() == 1) {
                    lightMode.selectedProperty().set(false);
                    darkMode.selectedProperty().set(true);
                    App.getScene().getStylesheets().clear();
                    App.getScene().getStylesheets().add(this.getClass().getResource("DarkModeSecurityQuestions.css").toExternalForm());
                }

                myScene.widthProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        setSecurityQuestionsVboxDimensions();
                        setFinishButtonDimensions();
                        setSecurityQuestionsHboxDimensions();
                    }
                });

                myScene.heightProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        setSecurityQuestionsVboxDimensions();
                        setFinishButtonDimensions();
                        setSecurityQuestionsHboxDimensions();
                    }
                });

                App.theme.addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        if ((int) t1 == 0) {
                            App.getScene().getStylesheets().clear();
                            App.getScene().getStylesheets().add(this.getClass().getResource("LightModeSecurityQuestions.css").toExternalForm());
                        }
                        else if ((int) t1 == 1) {
                            App.getScene().getStylesheets().clear();
                            App.getScene().getStylesheets().add(this.getClass().getResource("DarkModeSecurityQuestions.css").toExternalForm());
                        }
                    }
                });
            }
        });
    }

    public void setSecurityQuestionsVboxDimensions() {
        securityQuestionsVbox.setLayoutX(myScene.getWidth()/2 - 165);
        securityQuestionsVbox.setLayoutY(myScene.getHeight()/2 - 210);
    }

    public void setFinishButtonDimensions() {
        finishButton.setLayoutX(myScene.getWidth()/2 - 125);
        finishButton.setLayoutY(securityQuestionsVbox.getLayoutY() + securityQuestionsVbox.getHeight());
    }

    public void setSecurityQuestionsHboxDimensions() {
        securityQuestionsHbox.setLayoutX(myScene.getWidth()/2 - 125);
        securityQuestionsHbox.setLayoutY(finishButton.getLayoutY() + finishButton.getHeight());
    }
    public void backToFirstPage(ActionEvent actionEvent) throws IOException {
        User.deleteUser();
        User.abortMidway();
        App.setRoot("firstPage");
    }

    public void finishClicked(MouseEvent mouseEvent) throws IOException {
        if (firstQuestionBox.getSelectionModel().getSelectedIndex() == secondQuestionBox.getSelectionModel().getSelectedIndex()) {
            sameQuestions.setText("You can not answer the same question twice!");
            return;
        }

        String answer1Txt = answer1.getText();

        Pattern answerCheck = Pattern.compile("\\s*");

        if (answerCheck.matcher(answer1Txt).matches()) {
            ans1Invalid.setText("Answer to selected question can not be null!");
            answer1.requestFocus();
            return;
        }

        String answer2Txt = answer2.getText();

        if (answerCheck.matcher(answer2Txt).matches()) {
            ans2Invalid.setText("Answer to selected question can not be null!");
            answer2.requestFocus();
            return;
        }

        User.updateSecurityQuestions(firstQuestionBox.getSelectionModel().getSelectedIndex()+1, (secondQuestionBox.getSelectionModel().getSelectedIndex()+1),
                answer1Txt, answer2Txt);
        ans2Invalid.setText("Security questions updated successfully");
        User.abortMidway();

        App.setRoot("firstPage");
    }
}
