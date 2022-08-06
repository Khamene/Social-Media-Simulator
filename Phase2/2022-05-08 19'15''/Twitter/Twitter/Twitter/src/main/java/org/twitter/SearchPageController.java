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
        setDimensions();
        searchText.textProperty().addListener(observable -> searchProgress());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                myScene = (Stage) searchUserName.getScene().getWindow();
                myScene.widthProperty().addListener(new ChangeListener<Number>() {

                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        setDimensions();
                    }
                });
                myScene.heightProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        setDimensions();
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
    public void resetFirstHBoxColor(){
        firstResult.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
    }
    public void resetSecondHBoxColor(){
        secondResult.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
    }
    public void resetThirdHBoxColor(){
        thirdResult.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
    }
    public void resetFourthHBoxColor(){
        fourthResult.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
    }
    public void resetFifthHBoxColor(){
        fifthResult.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
    }
    public void setDimensions(){
        firstResult.setPrefWidth(288*(myScene.getWidth()/800));
        secondResult.setPrefWidth(288*(myScene.getWidth()/800));
        thirdResult.setPrefWidth(288*(myScene.getWidth()/800));
        fourthResult.setPrefWidth(288*(myScene.getWidth()/800));
        fifthResult.setPrefWidth(288*(myScene.getWidth()/800));

        firstResult.setPrefHeight(85*(myScene.getHeight()/800));
        secondResult.setPrefHeight(85*(myScene.getHeight()/800));
        thirdResult.setPrefHeight(85*(myScene.getHeight()/800));
        fourthResult.setPrefHeight(85*(myScene.getHeight()/800));
        fifthResult.setPrefHeight(85*(myScene.getHeight()/450));

        searchText.setLayoutX(myScene.getWidth()/2-searchText.getPrefWidth()/2);
        searchText.setLayoutY(1);
        firstResult.setLayoutX(myScene.getWidth()/2-firstResult.getPrefWidth()/2);
        firstResult.setLayoutY(1+searchText.getPrefHeight());
        secondResult.setLayoutX(myScene.getWidth()/2-firstResult.getPrefWidth()/2);
        secondResult.setLayoutY(1+searchText.getPrefHeight()+firstResult.getPrefHeight());
        thirdResult.setLayoutX(myScene.getWidth()/2-firstResult.getPrefWidth()/2);
        thirdResult.setLayoutY(1+searchText.getPrefHeight()+firstResult.getPrefHeight()*2);
        fourthResult.setLayoutX(myScene.getWidth()/2-firstResult.getPrefWidth()/2);
        fourthResult.setLayoutY(1+searchText.getPrefHeight()+firstResult.getPrefHeight()*3);
        fifthResult.setLayoutX(myScene.getWidth()/2-firstResult.getPrefWidth()/2);
        fifthResult.setLayoutY(1+searchText.getPrefHeight()+firstResult.getPrefHeight()*4);
    }
}
