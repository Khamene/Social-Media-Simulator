package org.twitter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class SecurityQuestionsController {

    @FXML
    public AnchorPane securityQuestions;

    @FXML
    public VBox securityQuestionsVbox;

    @FXML
    public ChoiceBox firstQuestionBox;

    @FXML
    public ChoiceBox secondQuestionBox;

    @FXML
    public Button finishButton;

    @FXML
    public HBox securityQuestionsHbox;


    public void setSecurityQuestionsVboxDimensions() {
        securityQuestionsVbox.setLayoutX(securityQuestions.getWidth()/2 - 165);
        securityQuestionsVbox.setLayoutY(securityQuestions.getHeight()/2 - 210);
    }

    public void setFinishButtonDimensions() {
        finishButton.setLayoutX(securityQuestions.getWidth()/2 - 125);
        finishButton.setLayoutY(securityQuestionsVbox.getLayoutY() + securityQuestionsVbox.getHeight());
    }

    public void setSecurityQuestionsHboxDimensions() {
        securityQuestionsHbox.setLayoutX(securityQuestions.getWidth()/2 - 125);
        securityQuestionsHbox.setLayoutY(finishButton.getLayoutY() + finishButton.getHeight());
    }
    public void backToFirstPage(ActionEvent actionEvent) throws IOException {
        App.setRoot("firstPage");
    }

    public void finishClicked(MouseEvent mouseEvent) throws IOException {
        /*
        SQL code for saving data
         */
        App.setRoot("firstPage");
    }
}
