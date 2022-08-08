package org.twitter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.twitter.Exceptions.NoSuchFileException;
import org.twitter.Exceptions.NoUserLoggedInException;
import org.twitter.Exceptions.PostContentNullException;
import org.twitter.ObjectClasses.BusinessUser;
import org.twitter.ObjectClasses.PersonalUser;
import org.twitter.ObjectClasses.Post;
import org.twitter.ObjectClasses.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreatePostController implements Initializable {
    Stage thisStage = null;
    Scene thisScene = null;

    @FXML
    Label textNeed;
    @FXML
    TextArea textOfPost;
    @FXML
    Label fileLbl;
    @FXML
    Label textLbl;
    @FXML
    TextField filePathTxt;
    @FXML
    Label dotLbl;
    @FXML
    Button createPost;
    @FXML
    Button clearAll;


    boolean isFileAdded;
    File file;
    FileChooser fc = new FileChooser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                thisScene = createPost.getScene();
                thisStage = (Stage) thisScene.getWindow();

                thisStage.setResizable(false);

                if (App.theme.getValue() == 0) {
                    thisScene.getStylesheets().clear();
                    thisScene.getStylesheets().add(this.getClass().getResource("LightModeSettings.css").toExternalForm());
                }
                else {
                    thisScene.getStylesheets().clear();
                    thisScene.getStylesheets().add(this.getClass().getResource("DarkModeSettings.css").toExternalForm());
                }

                App.theme.addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        if ((int) t1 == 0) {
                            thisScene.getStylesheets().clear();
                            thisScene.getStylesheets().add(this.getClass().getResource("LightModeSettings.css").toExternalForm());
                        }
                        else {
                            thisScene.getStylesheets().clear();
                            thisScene.getStylesheets().add(this.getClass().getResource("DarkModeSettings.css").toExternalForm());}
                        }
                });
            }
        });
    }

    public void createPostProgress(){
        if(!Objects.equals(textOfPost.getText(), "")) {
            if (isFileAdded) {
                try {
                    Post.publishPost(User.getLoggedInUsername(), textOfPost.getText());
                } catch (PostContentNullException e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files (*.png)", "*.png"));
                    fc.setTitle("Choose your picture");
                    file = fc.showOpenDialog(new Stage());
                    if (file != null) {
                        isFileAdded = true;
                        Post.publishPost(User.getLoggedInUsername(), textOfPost.getText(), file.getName());
                        filePathTxt.setText(file.getName());
                    }
                } catch (PostContentNullException | NoSuchFileException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            textNeed.setText("Text is needed for creating post");
        }
    }
    public void clearAllProgress(){
        textOfPost.clear();
        filePathTxt.clear();
    }
}
