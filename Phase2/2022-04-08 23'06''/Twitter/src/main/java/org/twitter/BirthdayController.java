package org.twitter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.twitter.Exceptions.NoUserLoggedInException;
import org.twitter.ObjectClasses.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BirthdayController implements Initializable {
    @FXML
    Label addBirthdayTitle;
    @FXML
    Label addBirthdayPrivacyMessage;
    @FXML
    Label statusLbl;

    @FXML
    VBox addBirthdayVbox;

    @FXML
    ImageView addBirthdayImage;

    @FXML
    Hyperlink backHyper;

    @FXML
    AnchorPane birthday;

    @FXML
    DatePicker datePicker;

    Stage myScene = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                myScene = (Stage) birthday.getScene().getWindow();

                setAddBirthdayVboxDimensions();
                setAddBirthdayPrivacyMessageDimensions();
                setAddBirthdayTitleDimensions();
                setAddBirthdayImageDimensions();

                myScene.widthProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        setAddBirthdayVboxDimensions();
                        setAddBirthdayPrivacyMessageDimensions();
                        setAddBirthdayTitleDimensions();
                        setAddBirthdayImageDimensions();
                    }
                });

                myScene.heightProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        setAddBirthdayVboxDimensions();
                        setAddBirthdayPrivacyMessageDimensions();
                        setAddBirthdayTitleDimensions();
                        setAddBirthdayImageDimensions();
                    }
                });

                myScene.setWidth(myScene.getWidth()+100);
                myScene.setWidth(myScene.getWidth()-100);
            }
        });
    }

    public void setAddBirthdayVboxDimensions() {
        addBirthdayVbox.setLayoutX(myScene.getWidth()/2 - addBirthdayVbox.getWidth()/2);
        addBirthdayVbox.setLayoutY(myScene.getHeight()/2);
    }

    public void setAddBirthdayPrivacyMessageDimensions() {
        addBirthdayPrivacyMessage.setLayoutX(myScene.getWidth()/2 - addBirthdayPrivacyMessage.getWidth()/2);
        addBirthdayPrivacyMessage.setLayoutY(addBirthdayVbox.getLayoutY() - 45 - addBirthdayPrivacyMessage.getHeight());
    }

    public void setAddBirthdayTitleDimensions() {
        addBirthdayTitle.setLayoutX(myScene.getWidth()/2 - addBirthdayTitle.getWidth()/2);
        addBirthdayTitle.setLayoutY(addBirthdayPrivacyMessage.getLayoutY() - addBirthdayTitle.getHeight() - 5);
    }

    public void setAddBirthdayImageDimensions() {
        addBirthdayImage.setLayoutX(myScene.getWidth()/2 - addBirthdayImage.getFitWidth()/2);
        addBirthdayImage.setLayoutY(addBirthdayTitle.getLayoutY() - addBirthdayImage.getFitHeight() - 20);
    }

    public void create(ActionEvent event) throws IOException {
        if (datePicker.getValue() == null) {
            statusLbl.setText("Birthday can not be null");
            return;
        }

        LocalDate birthdayTxt = datePicker.getValue();
        User.updateBirthday(birthdayTxt);
        statusLbl.setText("Birthday successfully added");

        App.setRoot("securityQuestions");
    }

    public void backToFirstPage(ActionEvent event) throws IOException {
        User.deleteUser();
        User.abortMidway();
        App.setRoot("firstPage");
    }
}
