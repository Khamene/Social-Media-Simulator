package org.twitter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    Label welcomeText;

    @FXML
    Parent firstPageVbox;

    @FXML
    Button signUpBtn;

    @FXML
    AnchorPane firstStage;

    Stage myScene = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                myScene = (Stage) firstStage.getScene().getWindow();

                setFirstPageVboxDimensions();
                setWelcomeTextDimensions();
                myScene.widthProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        setFirstPageVboxDimensions();
                        setWelcomeTextDimensions();
                    }
                });

                myScene.heightProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        setFirstPageVboxDimensions();
                        setWelcomeTextDimensions();
                    }
                });

                myScene.setWidth(myScene.getWidth()+100);
                myScene.setWidth(myScene.getWidth()-100);
            }
        });
    }

    public void setFirstPageVboxDimensions() {
        firstPageVbox.setLayoutX(myScene.getWidth()*2/3);
        firstPageVbox.setLayoutY(myScene.getHeight()/2 -160);
    }

    public void setWelcomeTextDimensions() {
        welcomeText.setLayoutX(myScene.getWidth()/3 - welcomeText.getWidth()/2);
        welcomeText.setLayoutY(myScene.getHeight()/2 - welcomeText.getHeight()/2);
    }

    public void signUpButtonClicked(ActionEvent event) throws IOException {
        App.setRoot("createAccount");
    }
}