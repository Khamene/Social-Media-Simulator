package org.twitter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.twitter.Exceptions.NoUserLoggedInException;
import org.twitter.Exceptions.PasswordIncorrectException;
import org.twitter.Exceptions.UserDoesNotExistException;
import org.twitter.ObjectClasses.User;
import org.w3c.dom.traversal.TreeWalker;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class LoginController implements Initializable {
    @FXML
    Label welcomeText;

    @FXML
    Parent firstPageVbox;

    @FXML
    Button signUpBtn;

    @FXML
    AnchorPane firstStage;

    @FXML
    Button loginBtn;

    @FXML
    Label usernameInvalid;
    @FXML
    Label passwordInvalid;

    @FXML
    TextField username;
    @FXML
    PasswordField password;

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
        welcomeText.setLayoutX(myScene.getWidth()/3 - 608/2);
        welcomeText.setLayoutY(myScene.getHeight()/2 - 106/2);
    }

    public void signUpButtonClicked(ActionEvent event) throws IOException {
        App.setRoot("createAccount");
    }

    public void login() {
        String usernameTxt = username.getText();

        Pattern usernamePattern = Pattern.compile("[a-zA-Z]*\\d+[a-zA-Z]*");

        if (usernameTxt.length() < 7 || !usernamePattern.matcher(usernameTxt).matches()) {
            usernameInvalid.setText("Username must contain a digit and consist of at least 7 characters!");
            username.clear();
            username.requestFocus();
            return;
        }

        String passwordTxt = password.getText();

        if (passwordTxt.length() < 7) {
            passwordInvalid.setText("Password must consist of at least 7 characters!");
            password.clear();
            password.requestFocus();
            return;
        }

        try {
            User.login(usernameTxt, passwordTxt);
        }
        catch (UserDoesNotExistException e) {
            usernameInvalid.setText(e.getMessage());
        }
        catch (PasswordIncorrectException e) {
            passwordInvalid.setText(e.getMessage());
        }
    }
}