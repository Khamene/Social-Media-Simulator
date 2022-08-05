package org.twitter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateAccountController implements Initializable {
    @FXML
    Label createAccountTitle;
    @FXML
    Label alreadyHaveAccount;

    @FXML
    VBox createAccountVbox;

    @FXML
    Hyperlink createAccountLoginLink;

    @FXML
    Button nextBtn;

    @FXML
    AnchorPane sgnUpStage;

    Stage myScene = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                myScene = (Stage) sgnUpStage.getScene().getWindow();

                setCreateAccountVboxDimensions();
                setCreateAccountTitleDimensions();
                setCreateAccountAlreadyHaveAccountDimensions();
                setCreateAccountLoginLinkDimensions();

                myScene.widthProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        setCreateAccountVboxDimensions();
                        setCreateAccountTitleDimensions();
                        setCreateAccountAlreadyHaveAccountDimensions();
                        setCreateAccountLoginLinkDimensions();
                    }
                });

                myScene.heightProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        setCreateAccountVboxDimensions();
                        setCreateAccountTitleDimensions();
                        setCreateAccountAlreadyHaveAccountDimensions();
                        setCreateAccountLoginLinkDimensions();
                    }
                });

                myScene.setWidth(myScene.getWidth()+100);
                myScene.setWidth(myScene.getWidth()-100);
            }
        });
    }

    public void setCreateAccountVboxDimensions() {
        createAccountVbox.setLayoutX(myScene.getWidth()/2 - createAccountVbox.getWidth()/2);
        createAccountVbox.setLayoutY(myScene.getHeight()/2 - createAccountVbox.getHeight()/2);
    }

    public void setCreateAccountTitleDimensions() {
        createAccountTitle.setLayoutX(myScene.getWidth()/2 - createAccountTitle.getWidth()/2);
        createAccountTitle.setLayoutY(createAccountVbox.getLayoutY() - createAccountTitle.getHeight() - 10);
    }

    public void setCreateAccountAlreadyHaveAccountDimensions() {
        alreadyHaveAccount.setLayoutX(myScene.getWidth()/2 - (alreadyHaveAccount.getWidth() + createAccountLoginLink.getWidth() + 20)/2);
        alreadyHaveAccount.setLayoutY(createAccountVbox.getLayoutY() + createAccountVbox.getHeight());
    }

    public void setCreateAccountLoginLinkDimensions() {
        createAccountLoginLink.setLayoutX(myScene.getWidth()/2 - (alreadyHaveAccount.getWidth() + createAccountLoginLink.getWidth() + 20)/2 +alreadyHaveAccount.getWidth() + 20);
        createAccountLoginLink.setLayoutY(createAccountVbox.getLayoutY() + createAccountVbox.getHeight());
    }

    public void backToFirstPage(ActionEvent event) throws IOException {
        App.setRoot("firstPage");
    }

    public void createAccountPageNext(ActionEvent event) throws IOException {
        App.setRoot("createAccount2");
    }
}
