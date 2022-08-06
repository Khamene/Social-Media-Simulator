package org.twitter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchPageController implements Initializable {
    @FXML
    TextField searchText;
    @FXML
    HBox firstResult;
    @FXML
    HBox secondResult;
    @FXML
    HBox thirdResult;
    @FXML
    HBox fourthResult;
    @FXML
    HBox fifthResult;
    @FXML
    AnchorPane searchUserName;
    Stage myScene = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchText.textProperty().addListener(observable -> searchProgress());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                myScene = (Stage) searchUserName.getScene().getWindow();
                myScene.widthProperty().addListener(new ChangeListener<Number>() {

                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

                    }
                });
                myScene.heightProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

                    }
                });
            }
        });
    }
    public void searchProgress(){
        String userName=searchText.getText();
        //
        //searching for userName
        //
    }
    public void sendToFirstProfileProgress(){

    }
    public void sendToSecondProfileProgress(){

    }
    public void sendToThirdProfileProgress(){

    }
    public void sendToFourthProfileProgress(){

    }
    public void sendToFifthProfileProgress(){

    }
    public void showFirstSearch(){

    }
    public void showSecondSearch(){

    }
    public void showThirdSearch(){

    }
    public void showFourthSearch(){

    }
    public void showFifthSearch(){

    }
    public void changeFirstHBoxColor(){
        firstResult.setBackground(new Background(new BackgroundFill(Color.DARKGRAY,null,null)));
    }
    public void changeSecondHBoxColor(){
        secondResult.setBackground(new Background(new BackgroundFill(Color.DARKGRAY,null,null)));
    }
    public void changeThirdHBoxColor(){
        thirdResult.setBackground(new Background(new BackgroundFill(Color.DARKGRAY,null,null)));
    }
    public void changeFourthHBoxColor(){
        fourthResult.setBackground(new Background(new BackgroundFill(Color.DARKGRAY,null,null)));
    }
    public void changeFifthHBoxColor(){
        fifthResult.setBackground(new Background(new BackgroundFill(Color.DARKGRAY,null,null)));
    }
    public void setDimensions(){
        searchText.setLayoutX(myScene.getWidth()/2-154);
        searchText.setLayoutY(myScene.getHeight()/2-211);
        firstResult.setLayoutX(myScene.getWidth()/2-154);
        firstResult.setLayoutY(myScene.getWidth()/2-171);
        secondResult.setLayoutX(myScene.getWidth()/2-154);
        secondResult.setLayoutY(myScene.getWidth()/2-122);
        thirdResult.setLayoutX(myScene.getWidth()/2-154);
        thirdResult.setLayoutY(myScene.getWidth()/2-38);
        fourthResult.setLayoutX(myScene.getWidth()/2-154);
        fourthResult.setLayoutY(myScene.getWidth()/2+66);
        fifthResult.setLayoutX(myScene.getWidth()/2-154);
        fifthResult.setLayoutY(myScene.getWidth()/2+145);
    }
}
