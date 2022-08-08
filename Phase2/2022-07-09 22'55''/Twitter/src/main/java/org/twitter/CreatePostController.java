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
import org.twitter.Exceptions.UserDoesNotExistException;
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
    File file = null;
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
                    thisScene.getStylesheets().add(this.getClass().getResource("LightModeCreatePost.css").toExternalForm());
                }
                else {
                    thisScene.getStylesheets().clear();
                    thisScene.getStylesheets().add(this.getClass().getResource("DarkModeCreatePost.css").toExternalForm());
                }

                App.theme.addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        if ((int) t1 == 0) {
                            thisScene.getStylesheets().clear();
                            thisScene.getStylesheets().add(this.getClass().getResource("LightModeCreatePost.css").toExternalForm());
                        }
                        else {
                            thisScene.getStylesheets().clear();
                            thisScene.getStylesheets().add(this.getClass().getResource("DarkModeCreatePost.css").toExternalForm());}
                        }
                });
            }
        });
    }

    public void addFile() {
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files (*.png)", "*.png"));
        fc.setTitle("Choose your picture");
        System.out.println(System.getProperty("user.dir") + "..\\src\\main\\resources\\ord\\twitter");
        fc.initialDirectoryProperty().set(new File(System.getProperty("user.dir") + "\\src\\main\\resources\\org\\twitter"));
        file = fc.showOpenDialog(new Stage());
    }

    public void createPostProgress(){
        if(!Objects.equals(textOfPost.getText(), "")) {
            if (file == null) {
                try {
                    Post.publishPost(User.getUserID(User.getLoggedInUsername()), textOfPost.getText());
                }
                catch (PostContentNullException | UserDoesNotExistException e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    Post.publishPost(User.getUserID(User.getLoggedInUsername()), textOfPost.getText(), file.getName());
                    filePathTxt.setText(file.getName());
                }
                catch (PostContentNullException | NoSuchFileException | UserDoesNotExistException e) {
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
