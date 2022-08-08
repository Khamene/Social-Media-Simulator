package org.twitter;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.twitter.Exceptions.*;
import org.twitter.Functionality.SQLManager;
import org.twitter.ObjectClasses.BusinessUser;
import org.twitter.ObjectClasses.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AccountViewController implements Initializable {
    @FXML
    private Label appTitle;
    @FXML
    private ToggleButton blockBtn;
    @FXML
    private ImageView commentsBtn;
    @FXML
    private ToggleButton darkMode;
    @FXML
    private Button directBtn;
    @FXML
    private VBox feedVBox;
    @FXML
    private ToggleButton followBtn;
    @FXML
    private HBox followViewHBox;
    @FXML
    private Button homeBtn;
    @FXML
    private ImageView homePhoto;
    @FXML
    private SplitPane horizSplit;
    @FXML
    private Button insightBtn;
    @FXML
    private ImageView insightPhoto;
    @FXML
    private AnchorPane leftSide;
    @FXML
    private ToggleButton lightMode;
    @FXML
    private ImageView likesBtn;
    @FXML
    private AnchorPane mainSide;
    @FXML
    private HBox menuHbox1;
    @FXML
    private HBox menuHbox2;
    @FXML
    private HBox menuHbox3;
    @FXML
    private HBox menuHbox4;
    @FXML
    private HBox menuHbox5;
    @FXML
    private HBox menuHbox6;
    @FXML
    private HBox menuHbox7;
    @FXML
    private VBox menuVBox;
    @FXML
    private Button messageBtn;
    @FXML
    private ImageView messagePhoto;
    @FXML
    private Button noteBtn;
    @FXML
    private ImageView notePhoto;
    @FXML
    private Button postBtn;
    @FXML
    private AnchorPane postViewField;
    @FXML
    private ScrollPane postViewScrollField;
    @FXML
    private ImageView profileImage;
    @FXML
    private ImageView postPhoto;
    @FXML
    private Label profileUserName;
    @FXML
    private Label followerCount;
    @FXML
    private Label followingCount;
    @FXML
    private AnchorPane profileViewPage;
    @FXML
    private Button searchBtn;
    @FXML
    private ImageView searchPhoto;
    @FXML
    private Button setBtn;
    @FXML
    private ImageView setPhoto;
    @FXML
    private ToggleGroup themeChoose;
    @FXML
    private HBox themeHBox;
    @FXML
    private SplitPane vertSplit;
    @FXML
    private AnchorPane topSide;
    @FXML
    private AnchorPane bottomSide;
    @FXML
    private VBox generalInfo;
    @FXML
    private HBox accountName;
    @FXML
    private HBox followStat;
    @FXML
    private HBox interaction;
    @FXML
    private Separator sep1;
    @FXML
    private Separator sep2;

    Stage myStage = null;

    String profileImagePath = null;
    static String gotViewedUserName = "Mehrshad522";
    static SimpleBooleanProperty followed = new SimpleBooleanProperty();
    SimpleBooleanProperty blocked = new SimpleBooleanProperty();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setProfileImage();
        setProfileUserName();

        if (User.getLoggedInUsername().equalsIgnoreCase(gotViewedUserName)) {
            followBtn.setDisable(true);
            blockBtn.setDisable(true);
            directBtn.setDisable(true);
        }

        try {
            if (!User.getUserType(gotViewedUserName)) {
                likesBtn.setDisable(true);
                commentsBtn.setDisable(true);
            }
        }
        catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }

        try {
            if (!User.getUserType(User.getLoggedInUsername())) {
                insightBtn.setDisable(true);
            }
        }
        catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }

        try {
            followerCount.setText(followerCount.getText() + User.getFollowerCount(gotViewedUserName));
        }
        catch (UserDoesNotExistException | SQLException e) {
            e.printStackTrace();
        }

        try {
            followingCount.setText(followingCount.getText() + User.getFollowingCount(gotViewedUserName));
        }
        catch (UserDoesNotExistException | SQLException e) {
            e.printStackTrace();
        }

        try {
            User.checkFollowedAccount(User.getUserID(User.getLoggedInUsername()), User.getUserID(gotViewedUserName));
            followBtn.setSelected(true);
            followed.setValue(true);

            if (followed.getValue())
                followBtn.setText("Unfollow");
            else
                followBtn.setText("Requested");
        }
        catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }
        catch (UnauthorisedEditException e) {
            followBtn.setSelected(false);
            followed.setValue(false);
            followBtn.setText("Follow");
        }

        try {
            if (User.getBlocked(gotViewedUserName)) {
                blockBtn.setSelected(true);
                blocked.setValue(true);
                blockBtn.setText("Unblock");
            }
            else {
                blockBtn.setSelected(false);
                blocked.setValue(false);
                blockBtn.setText("Block");
            }
        }
        catch (NoUserLoggedInException | UserDoesNotExistException e) {
            e.printStackTrace();
        }

        followBtn.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    try {
                        User.sendFollowRequest(gotViewedUserName);
                        followBtn.setText("Requested");
                    }
                    catch (UserAlreadyFollowedException | UserDoesNotExistException | NoUserLoggedInException | UnauthorisedEditException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    try {
                        User.unfollow(gotViewedUserName);
                        followBtn.setText("Follow");
                        followed.setValue(false);
                    }
                    catch (UserDoesNotExistException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        blockBtn.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    try {
                        User.blockUser(gotViewedUserName);
                        blockBtn.setText("Unblock");
                    }
                    catch (NoUserLoggedInException | UserDoesNotExistException | UserAlreadyBlockedException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        User.unblockUser(gotViewedUserName);
                        blockBtn.setText("Block");
                    }
                    catch (NoUserLoggedInException | UserDoesNotExistException | UserNotBlockedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

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
                myStage = (Stage) profileViewPage.getScene().getWindow();

                if (App.theme.getValue() == 0) {
                    lightMode.selectedProperty().set(true);
                    darkMode.selectedProperty().set(false);
                    App.getScene().getStylesheets().clear();
                    App.getScene().getStylesheets().add(this.getClass().getResource("LightModeAccount.css").toExternalForm());
                    homePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeHomeIcon.png"))));
                    notePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeNotificationsIcon.png"))));
                    messagePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeChatIcon.png"))));
                    setPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeSettingsIcon.png"))));
                    insightPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeInsightsIcon.png"))));
                    searchPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeSearchIcons.png"))));
                    postPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("Post-Icon.png"))));
                    likesBtn.setImage(new Image(String.valueOf(this.getClass().getResource("LikeIcon.png"))));
                    commentsBtn.setImage(new Image(String.valueOf(this.getClass().getResource("commentIcon.png"))));
                }
                else if (App.theme.getValue() == 1) {
                    lightMode.selectedProperty().set(false);
                    darkMode.selectedProperty().set(true);
                    App.getScene().getStylesheets().clear();
                    App.getScene().getStylesheets().add(this.getClass().getResource("DarkModeAccount.css").toExternalForm());
                    homePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeHomeIcons.png"))));
                    notePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeNotificationsIcon.png"))));
                    messagePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeChatsIcon.png"))));
                    setPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeSettingsIcon.png"))));
                    insightPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeInsightsIcon.png"))));
                    searchPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeSearchIcons.png"))));
                    postPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("Post-Icon1.png"))));
                    likesBtn.setImage(new Image(String.valueOf(this.getClass().getResource("LikeIcon1.png"))));
                    commentsBtn.setImage(new Image(String.valueOf(this.getClass().getResource("commentIcon1.png"))));
                }

                setDimensions();
                loadPosts();

                myStage.widthProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        setDimensions();
                        loadPosts();
                    }
                });

                myStage.heightProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        setDimensions();
                        loadPosts();
                    }
                });

                App.theme.addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        setProfileImage();

                        if ((int) t1 == 0) {
                            App.getScene().getStylesheets().clear();
                            App.getScene().getStylesheets().add(this.getClass().getResource("LightModeAccount.css").toExternalForm());
                            setDimensions();
                            loadPosts();
                            homePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeHomeIcon.png"))));
                            notePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeNotificationsIcon.png"))));
                            messagePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeChatIcon.png"))));
                            setPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeSettingsIcon.png"))));
                            insightPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeInsightsIcon.png"))));
                            searchPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeSearchIcons.png"))));
                            postPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("Post-Icon.png"))));
                            likesBtn.setImage(new Image(String.valueOf(this.getClass().getResource("LikeIcon.png"))));
                            commentsBtn.setImage(new Image(String.valueOf(this.getClass().getResource("commentIcon.png"))));
                        }
                        else if ((int) t1 == 1) {
                            App.getScene().getStylesheets().clear();
                            App.getScene().getStylesheets().add(this.getClass().getResource("DarkModeAccount.css").toExternalForm());
                            setDimensions();
                            loadPosts();
                            homePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeHomeIcons.png"))));
                            notePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeNotificationsIcon.png"))));
                            messagePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeChatsIcon.png"))));
                            setPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeSettingsIcon.png"))));
                            insightPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeInsightsIcon.png"))));
                            searchPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeSearchIcons.png"))));
                            postPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("Post-Icon1.png"))));
                            likesBtn.setImage(new Image(String.valueOf(this.getClass().getResource("LikeIcon1.png"))));
                            commentsBtn.setImage(new Image(String.valueOf(this.getClass().getResource("commentIcon1.png"))));
                        }
                    }
                });
            }
        });

    }

    public void setDimensions() {
        profileViewPage.setPrefWidth(myStage.getWidth());
        profileViewPage.setPrefHeight(myStage.getHeight());

        vertSplit.setPrefWidth(profileViewPage.getPrefWidth());
        vertSplit.setPrefHeight(profileViewPage.getPrefHeight());

        leftSide.setPrefWidth(vertSplit.getPrefWidth() / 4);
        leftSide.setPrefHeight(vertSplit.getPrefHeight());

        menuVBox.setPrefWidth(leftSide.getPrefWidth());
        menuVBox.setPrefHeight(leftSide.getPrefHeight());

        for (int i = 0; i < menuVBox.getChildren().size(); i++) {
            if (i == 0) {
                ((Label)menuVBox.getChildren().get(i)).setPrefWidth(menuVBox.getPrefWidth());
                ((Label)menuVBox.getChildren().get(i)).setPrefHeight(menuVBox.getPrefHeight() * 4 / 45);
            }
            else {
                ((HBox)menuVBox.getChildren().get(i)).setPrefWidth(menuVBox.getPrefWidth());
                ((HBox)menuVBox.getChildren().get(i)).setPrefHeight(menuVBox.getPrefHeight() * 4 / 45);
            }
        }

        homeBtn.setPrefWidth(menuHbox1.getPrefWidth() * 7 / 10);
        homeBtn.setPrefHeight(menuHbox1.getPrefHeight() * 8 / 10);

        noteBtn.setPrefWidth(menuHbox2.getPrefWidth() * 7 / 10);
        noteBtn.setPrefHeight(menuHbox2.getPrefHeight() * 8 / 10);

        messageBtn.setPrefWidth(menuHbox3.getPrefWidth() * 7 / 10);
        messageBtn.setPrefHeight(menuHbox3.getPrefHeight() * 8 / 10);

        setBtn.setPrefWidth(menuHbox4.getPrefWidth() * 7 / 10);
        setBtn.setPrefHeight(menuHbox4.getPrefHeight() * 8 / 10);

        insightBtn.setPrefWidth(menuHbox5.getPrefWidth() * 7 / 10);
        insightBtn.setPrefHeight(menuHbox5.getPrefHeight() * 8 / 10);

        searchBtn.setPrefWidth(menuHbox6.getPrefWidth() * 7 / 10);
        searchBtn.setPrefHeight(menuHbox6.getPrefHeight() * 8 / 10);

        postBtn.setPrefWidth(menuHbox7.getPrefWidth() * 7 / 10);
        postBtn.setPrefHeight(menuHbox7.getPrefHeight() * 8 / 10);

        lightMode.setPrefWidth(themeHBox.getPrefWidth() * 3 / 10);
        lightMode.setPrefHeight(themeHBox.getPrefHeight() * 8 / 10);

        darkMode.setPrefWidth(themeHBox.getPrefWidth() * 3 / 10);
        darkMode.setPrefHeight(themeHBox.getPrefHeight() * 8 / 10);

        mainSide.setPrefWidth(vertSplit.getPrefWidth() * 3 / 4);
        mainSide.setPrefHeight(vertSplit.getPrefHeight());

        horizSplit.setPrefWidth(mainSide.getPrefWidth());
        horizSplit.setPrefHeight(mainSide.getPrefHeight());

        topSide.setPrefWidth(horizSplit.getPrefWidth());
        topSide.setPrefHeight(horizSplit.getPrefHeight() / 5);

        generalInfo.setPrefWidth(topSide.getPrefWidth());
        generalInfo.setPrefHeight(topSide.getPrefHeight());

        accountName.setPrefWidth(generalInfo.getPrefWidth());
        accountName.setPrefHeight(generalInfo.getPrefHeight() / 4);

        profileUserName.setPrefWidth(accountName.getPrefWidth());
        profileUserName.setPrefHeight(accountName.getPrefHeight() * 9 / 10);

        followStat.setPrefWidth(generalInfo.getPrefWidth());
        followStat.setPrefHeight(generalInfo.getPrefHeight() / 4);

        followerCount.setPrefWidth(followStat.getPrefWidth() / 3);
        followerCount.setPrefHeight(followStat.getPrefHeight() * 9 / 10);

        followingCount.setPrefWidth(followStat.getPrefWidth() / 3);
        followingCount.setPrefHeight(followStat.getPrefHeight() * 9 / 10);

        interaction.setPrefWidth(generalInfo.getPrefWidth());
        interaction.setPrefHeight(generalInfo.getPrefHeight() / 4);

        followBtn.setPrefWidth(interaction.getPrefWidth() / 4);
        followBtn.setPrefHeight(interaction.getPrefHeight() * 9 / 10);

        blockBtn.setPrefWidth(interaction.getPrefWidth() / 4);
        blockBtn.setPrefHeight(interaction.getPrefHeight() * 9 / 10);

        directBtn.setPrefWidth(interaction.getPrefWidth() / 4);
        directBtn.setPrefHeight(interaction.getPrefHeight() * 9 / 10);

        sep1.setPrefWidth(generalInfo.getPrefWidth());

        bottomSide.setPrefWidth(horizSplit.getPrefWidth());
        bottomSide.setPrefHeight(horizSplit.getPrefHeight() * 4 / 5);

        feedVBox.setPrefWidth(bottomSide.getPrefWidth());
        feedVBox.setPrefHeight(bottomSide.getPrefHeight());

        postViewScrollField.setPrefWidth(feedVBox.getPrefWidth());
        postViewScrollField.setPrefHeight(feedVBox.getPrefHeight() * 11.3 / 13.5);

        postViewField.setPrefWidth(postViewScrollField.getPrefWidth());
        postViewField.setPrefHeight(postViewScrollField.getPrefHeight());

        followViewHBox.setPrefWidth(feedVBox.getPrefWidth());
        followViewHBox.setPrefHeight(feedVBox.getPrefHeight() - postViewScrollField.getPrefHeight());

        followViewHBox.setSpacing(20);

        likesBtn.setFitWidth(followViewHBox.getPrefHeight() / 3);
        likesBtn.setFitHeight(followViewHBox.getPrefHeight() / 3);

        commentsBtn.setFitWidth(followViewHBox.getPrefHeight() / 3);
        commentsBtn.setFitHeight(followViewHBox.getPrefHeight() / 3);

        sep2.setPrefWidth(feedVBox.getPrefWidth());
        sep2.setPrefHeight(feedVBox.getPrefHeight() * 0.4 / 13.5);
    }

    public void loadPosts() {

    }

    public void showLikes(ActionEvent event){
        //follow number
    }

    public void showComments(ActionEvent event){
        //viewNumber only for business account and logged in user = gotViewedUserName
    }

    public void toggleBlock() {

    }

    public void toggleFollow() {

    }


    public void goToDirectMessage(ActionEvent event) {
        //
        // send to direct message
        //
    }

    public void gotoHome() {

    }

    public void goToNotifications() {

    }

    public void gotoSettings() {

    }

    public void gotoMessages() {}

    public void gotoInsigts() {

    }

    public void gotoSearchUser() throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(App.loadFXML("SearchUserAccount"));
        stage.setScene(scene);
        stage.show();
    }

    public void createNewPost() {

    }


    public void setProfileImage() {
        try {
            profileImagePath = User.getUserProfile(gotViewedUserName);
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }

        if (!profileImagePath.equals(""))
            profileImage.setImage(new Image(String.valueOf(this.getClass().getResource(profileImagePath))));
        else {
            if (App.theme.getValue() == 0)
                profileImage.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon.png"))));
            else
                profileImage.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon1.png"))));
        }
    }

    public void setProfileUserName() {
        profileUserName.setText(gotViewedUserName);
    }
}
