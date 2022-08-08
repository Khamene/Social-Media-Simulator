package org.twitter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.twitter.Exceptions.NoUserLoggedInException;
import org.twitter.Exceptions.UserDoesNotExistException;
import org.twitter.ObjectClasses.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SettingController implements Initializable {
    @FXML
    Circle userNameCir;
    @FXML
    Circle passCir;
    @FXML
    Circle picCir;
    @FXML
    Label wrongPassword;
    @FXML
    PasswordField securityPassword;
    @FXML
    TextField newUserName;
    @FXML
    PasswordField newPassword;
    @FXML
    TextField newPicture;
    @FXML
    Label statusLblUser;
    @FXML
    Label statusLblPass;
    @FXML
    Label statusLblPic;
    @FXML
    Label picLbl;
    @FXML
    Label userNameLbl;
    @FXML
    Label passLbl;
    @FXML
    Button logoutBtn;
    @FXML
    HBox passHBox;

    FileChooser fc = new FileChooser();
    boolean accessebility = false;
    File file;

    String oldPassword = null;

    Stage thisStage = null;
    Scene thisScene = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkPassword();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                thisScene = logoutBtn.getScene();
                thisStage = (Stage) thisScene.getWindow();

                thisStage.setResizable(false);

                if (App.theme.getValue() == 0) {
                    thisScene.getStylesheets().clear();
                    thisScene.getStylesheets().add(this.getClass().getResource("LightModeSettings.css").toExternalForm());
                    userNameCir.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("TickIcon.png")))));
                    passCir.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("TickIcon.png")))));
                    picCir.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("Ellipsis.png")))));
                }
                else {
                    thisScene.getStylesheets().clear();
                    thisScene.getStylesheets().add(this.getClass().getResource("DarkModeSettings.css").toExternalForm());
                    userNameCir.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("TickIcon1.png")))));
                    passCir.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("TickIcon1.png")))));
                    picCir.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("Ellipsis1.png")))));
                }

                App.theme.addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        if ((int) t1 == 0) {
                            thisScene.getStylesheets().clear();
                            thisScene.getStylesheets().add(this.getClass().getResource("LightModeSettings.css").toExternalForm());
                            userNameCir.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("TickIcon.png")))));
                            passCir.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("TickIcon.png")))));
                            picCir.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("Ellipsis.png")))));
                        }
                        else {
                            thisScene.getStylesheets().clear();
                            thisScene.getStylesheets().add(this.getClass().getResource("DarkModeSettings.css").toExternalForm());
                            userNameCir.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("TickIcon1.png")))));
                            passCir.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("TickIcon1.png")))));
                            picCir.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("Ellipsis1.png")))));
                        }
                    }
                });
            }
        });
    }

    public void checkPassword() {
        securityPassword.setOnKeyPressed(kp -> {
            if (kp.getCode().equals(KeyCode.ENTER)) {
                oldPassword = securityPassword.getText();
                try {
                    if (User.checkPassword(oldPassword)) {
                        accessebility = true;

                        passHBox.setDisable(true);

                        wrongPassword.setText("Access Granted");
                        wrongPassword.setStyle("-fx-text-fill: #00ff00");
                    }
                    else {
                        accessebility = false;

                        wrongPassword.setText("Wrong password entered");
                    }
                }
                catch (NoUserLoggedInException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void changeUserName() {
        if (accessebility) {
            String usernameTxt = newUserName.getText();
            Pattern usernamePattern = Pattern.compile("[a-zA-Z]*\\d+[a-zA-Z]*");

            if (usernameTxt.length() < 7 || !usernamePattern.matcher(usernameTxt).matches()) {
                statusLblUser.setText("Username must contain a digit and consist of at least 7 characters!");
                //setStatusLblDimensions();
                newUserName.clear();
                newUserName.requestFocus();
                return;
            }
            else {

            }
        }
        else {
            statusLblUser.setText("No Access");
            return;
        }
    }

    public void changePassword() {
        if(accessebility) {
            String passwordTxt = newPassword.getText();

            if (passwordTxt.length() < 7) {
                statusLblPass.setText("Password must consist of at least 7 characters!");
                newPassword.clear();
                newPassword.requestFocus();
                return;
            }
            else {

            }
        }
        else {
            statusLblPass.setText("No Access");
        }

    }

    public void changeProfilePicture() {
        if(accessebility) {
            try {
                fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files (*.png)", "*.png"));
                fc.setTitle("Choose your profile Picture");
                file = fc.showOpenDialog(new Stage());
                newPicture.setText(file.getName());
                if (file != null)
                    User.changeProfile(file.getName());
            }
            catch (NoUserLoggedInException e) {
                e.printStackTrace();
            }
        }
        else {
           statusLblPic.setText("No Access");
        }
    }

    public void logout() throws IOException {
        try {
            User.logout();
            App.setRoot("firstPage");
            App.getScene().getWindow().requestFocus();
            thisStage.close();
        }
        catch (NoUserLoggedInException e) {
            e.printStackTrace();
        }
    }
}
