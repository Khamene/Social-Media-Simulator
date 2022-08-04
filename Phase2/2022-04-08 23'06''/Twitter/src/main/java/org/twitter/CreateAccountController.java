package org.twitter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.twitter.ObjectClasses.BusinessUser;
import org.twitter.ObjectClasses.PersonalUser;
import org.twitter.ObjectClasses.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CreateAccountController implements Initializable {
    @FXML
    Label createAccountTitle;
    @FXML
    Label alreadyHaveAccount;
    @FXML
    Label statusLbl;

    @FXML
    VBox createAccountVbox;

    @FXML
    Hyperlink createAccountLoginLink;

    @FXML
    Button nextBtn;

    @FXML
    AnchorPane sgnUpStage;

    @FXML
    TextField fullName;
    @FXML
    TextField username;
    @FXML
    TextField email;
    @FXML
    TextField phoneNumber;
    @FXML
    PasswordField password;

    @FXML
    RadioButton personal;
    @FXML
    RadioButton business;
    @FXML
    RadioButton male;
    @FXML
    RadioButton female;

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
                setStatusLblDimensions();

                myScene.widthProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        setCreateAccountVboxDimensions();
                        setCreateAccountTitleDimensions();
                        setCreateAccountAlreadyHaveAccountDimensions();
                        setCreateAccountLoginLinkDimensions();
                        setStatusLblDimensions();
                    }
                });

                myScene.heightProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        setCreateAccountVboxDimensions();
                        setCreateAccountTitleDimensions();
                        setCreateAccountAlreadyHaveAccountDimensions();
                        setCreateAccountLoginLinkDimensions();
                        setStatusLblDimensions();
                    }
                });

                myScene.setWidth(myScene.getWidth()+100);
                myScene.setWidth(myScene.getWidth()-100);
            }
        });
    }

    public void setCreateAccountVboxDimensions() {
        createAccountVbox.setLayoutX(myScene.getWidth()/2 - 250/2);
        createAccountVbox.setLayoutY(myScene.getHeight()/2 - 329/2);
    }

    public void setCreateAccountTitleDimensions() {
        createAccountTitle.setLayoutX(myScene.getWidth()/2 - 72/2);
        createAccountTitle.setLayoutY(createAccountVbox.getLayoutY() - 30 - 10);
    }

    public void setCreateAccountAlreadyHaveAccountDimensions() {
        alreadyHaveAccount.setLayoutX(myScene.getWidth()/2 - (143 + 46 + 20)/2);
        alreadyHaveAccount.setLayoutY(createAccountVbox.getLayoutY() + 329);
    }

    public void setCreateAccountLoginLinkDimensions() {
        createAccountLoginLink.setLayoutX(myScene.getWidth()/2 - (143 + 46 + 20)/2 + 143 + 20);
        createAccountLoginLink.setLayoutY(createAccountVbox.getLayoutY() + 329);
    }

    public void setStatusLblDimensions() {
        statusLbl.setLayoutX(myScene.getWidth()/2 - statusLbl.getWidth()/2);
        statusLbl.setLayoutY(7);
        statusLbl.setFont(Font.font("System", FontWeight.BOLD, 20));
    }

    public void backToFirstPage(ActionEvent event) throws IOException {
        App.setRoot("firstPage");
    }

    public void createAccountPageNext(ActionEvent event) throws IOException {
        String[] fullNameTxt = fullName.getText().split(" ");

        if (fullNameTxt.length != 2) {
            statusLbl.setText("fullName must contain two parts seperated by a white space!");
            setStatusLblDimensions();
            fullName.clear();
            fullName.requestFocus();
            return;
        }

        String firstName = fullNameTxt[0];
        String lastName = fullNameTxt[1];

        String usernameTxt = username.getText();
        Pattern usernamePattern = Pattern.compile("[a-zA-Z]*\\d+[a-zA-Z]*");

        if (usernameTxt.length() < 7 || !usernamePattern.matcher(usernameTxt).matches()) {
            statusLbl.setText("Username must contain a digit and consist of at least 7 characters!");
            setStatusLblDimensions();
            username.clear();
            username.requestFocus();
            return;
        }

        String emailTxt = email.getText();
        Pattern emailPattern = Pattern.compile("\\w+[a-zA-Z0-9.]*@[a-zA-Z]+\\.com");
        if (!emailPattern.matcher(emailTxt).matches()) {
            statusLbl.setText("Email format invalid!");
            setStatusLblDimensions();
            email.clear();
            email.requestFocus();
            return;
        }

        String phoneNumberTxt = phoneNumber.getText();
        Pattern phoneNumberPattern = Pattern.compile("\\d{11}");

        if (!phoneNumberPattern.matcher(phoneNumberTxt).matches()) {
            statusLbl.setText("Phone number must consist of 11 digits!");
            setStatusLblDimensions();
            phoneNumber.clear();
            phoneNumber.requestFocus();
            return;
        }

        String passwordTxt = password.getText();

        if (passwordTxt.length() < 7) {
            statusLbl.setText("Password must consist of at least 7 characters!");
            setStatusLblDimensions();
            password.clear();
            password.requestFocus();
            return;
        }

        boolean gender = false;

        if (female.isSelected())
            gender = true;

        boolean isPrivate = true;

        if (business.isSelected())
            isPrivate = false;

        if (isPrivate) {
            PersonalUser.createNewPersonalAccount(usernameTxt, passwordTxt, firstName, lastName, emailTxt, phoneNumberTxt, gender, true);
        }
        else {
            BusinessUser.createBusinessAccount(usernameTxt, passwordTxt, firstName, lastName, emailTxt, phoneNumberTxt, gender, true);
        }

        statusLbl.setText(String.format("User %s created successfully", usernameTxt));

        User.setLoggedInUsername(usernameTxt);

        App.setRoot("createAccount2");
    }
}
