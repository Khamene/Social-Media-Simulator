package org.twitter;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class CreatPostController implements Initializable {
    @FXML
    private ImageView addImageIcon;

    @FXML
    public Label statusLabel;
    @FXML
    public Label imageLabel;

    @FXML
    public TextArea postTextArea;

    Scene thisScene = null;
    Stage thisStage = null;

    FileChooser fileChooser = new FileChooser();
    File imageFile = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                thisScene = addImageIcon.getScene();
                thisStage = (Stage) thisScene.getWindow();
                thisStage.setResizable(false);
                Image iconImage;
                //theme handle
                if(App.theme.getValue() ==0) {
                    thisScene.getStylesheets().clear();
                    thisScene.getStylesheets().add(this.getClass().getResource("LightModeCreatePost.css").toExternalForm());
                    iconImage = new Image(getClass().getResourceAsStream("LightModeAddImageIcon.png"));

                }
                else {
                    thisScene.getStylesheets().clear();
                    thisScene.getStylesheets().add(this.getClass().getResource("DarkModeCreatePost.css").toExternalForm());
                    iconImage = new Image(getClass().getResourceAsStream("DightModeAddImageIcon.png"));
                }

                addImageIcon.setImage(iconImage);
            }
        });

    }

    public void addImage(ActionEvent event) {
        imageFile = fileChooser.showSaveDialog(thisStage);
        String imageFilePath = imageFile.getAbsolutePath();
        if(imageFile != null)
            imageLabel.setText(imageFilePath + "is selected.");

    }

    public void CreatPost(ActionEvent event) {
        String postText = postTextArea.getText();

        if (postText.equals("") && imageFile == null) {
            statusLabel.setText("You can not create an empty post");
            return;
        }

    }

    public void cancelCreatePost(ActionEvent event) {
        thisStage.close();
    }


}
