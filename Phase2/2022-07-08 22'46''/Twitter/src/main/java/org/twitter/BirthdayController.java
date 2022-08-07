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
import javafx.scene.control.ToggleButton;
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

    @FXML
    ToggleButton lightMode;
    @FXML
    ToggleButton darkMode;

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
                myScene = (Stage) birthday.getScene().getWindow();

                setAddBirthdayVboxDimensions();
                setAddBirthdayPrivacyMessageDimensions();
                setAddBirthdayTitleDimensions();
                setAddBirthdayImageDimensions();

                if (App.theme.getValue() == 0) {
                    lightMode.selectedProperty().set(true);
                    darkMode.selectedProperty().set(false);
                    App.getScene().getStylesheets().clear();
                    App.getScene().getStylesheets().add(this.getClass().getResource("LightModeBirthday.css").toExternalForm());
                }
                else if (App.theme.getValue() == 1) {
                    lightMode.selectedProperty().set(false);
                    darkMode.selectedProperty().set(true);
                    App.getScene().getStylesheets().clear();
                    App.getScene().getStylesheets().add(this.getClass().getResource("DarkModeBirthday.css").toExternalForm());
                }

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

                App.theme.addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        if ((int) t1 == 0) {
                            App.getScene().getStylesheets().clear();
                            App.getScene().getStylesheets().add(this.getClass().getResource("LightModeBirthday.css").toExternalForm());
                        }
                        else if ((int) t1 == 1) {
                            App.getScene().getStylesheets().clear();
                            App.getScene().getStylesheets().add(this.getClass().getResource("DarkModeBirthday.css").toExternalForm());
                        }
                    }
                });
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
