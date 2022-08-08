package org.twitter;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.twitter.Exceptions.*;
import org.twitter.ObjectClasses.*;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML
    AnchorPane chatNameAnchor;
    @FXML
    HBox chatNameHBox;
    @FXML
    ScrollPane chatNameScroll;
    @FXML
    VBox chatsVBox;
    @FXML
    AnchorPane leftSide;
    @FXML
    AnchorPane mainSide;
    @FXML
    AnchorPane rightSide;
    @FXML
    AnchorPane main;
    @FXML
    ToggleButton lightMode;
    @FXML
    ToggleButton darkMode;
    @FXML
    SplitPane splitPane;
    @FXML
    ToggleButton allChats;
    @FXML
    ToggleButton personChat;
    @FXML
    ToggleButton groupChat;
    @FXML
    ToggleGroup themeChoose;
    @FXML
    ToggleGroup filter;
    @FXML
    HBox themeHBox;
    @FXML
    HBox filterHBox;
    @FXML
    HBox menuHbox1;
    @FXML
    HBox menuHbox2;
    @FXML
    HBox menuHbox3;
    @FXML
    HBox menuHbox4;
    @FXML
    HBox menuHbox5;
    @FXML
    HBox menuHbox6;
    @FXML
    HBox menuHbox7;
    @FXML
    HBox menuHbox8;
    @FXML
    VBox menuVBox;
    @FXML
    ImageView homePhoto;
    @FXML
    ImageView notePhoto;
    @FXML
    ImageView messagePhoto;
    @FXML
    ImageView setPhoto;
    @FXML
    ImageView insightPhoto;
    @FXML
    ImageView searchPhoto;
    @FXML
    Button  homeBtn;
    @FXML
    Button  noteBtn;
    @FXML
    Button  messageBtn;
    @FXML
    Button  setBtn;
    @FXML
    Button  insightBtn;
    @FXML
    Button  searchBtn;
    @FXML
    Button  postBtn;
    @FXML
    Button  groupBtn;
    @FXML
    Label appTitle;
    @FXML
    ScrollPane messageScroll;
    @FXML
    AnchorPane messageArea;
    @FXML
    HBox typeArea;
    @FXML
    TextField typeField;
    @FXML
    ImageView postPhoto;

    Stage myStage = null;

    static int chatFilter = 0;
    static SimpleIntegerProperty selectedChat = new SimpleIntegerProperty();
    static SimpleBooleanProperty toggleChange = new SimpleBooleanProperty();

    static ArrayList<Chat> chats = new ArrayList<>();
    ArrayList<HBox> chatNameHolders = new ArrayList<>();
    String clickedHBoxID = null;

    ChangeListener<Number> listener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
            setDimensions();
            loadChats();
        }
    };

    ChangeListener<Number> listener2 = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
            if ((int) t1 == 0) {
                App.getScene().getStylesheets().clear();
                App.getScene().getStylesheets().add(this.getClass().getResource("LightModeChat.css").toExternalForm());
                setDimensions();
                loadChats();
                homePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeHomeIcon.png"))));
                notePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeNotificationsIcon.png"))));
                messagePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeChatIcon.png"))));
                setPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeSettingsIcon.png"))));
                insightPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeInsightsIcon.png"))));
                searchPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeSearchIcons.png"))));
                postPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("post-icon.png"))));
            }
            else if ((int) t1 == 1) {
                App.getScene().getStylesheets().clear();
                App.getScene().getStylesheets().add(this.getClass().getResource("DarkModeChat.css").toExternalForm());
                setDimensions();
                loadChats();
                homePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeHomeIcons.png"))));
                notePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeNotificationsIcon.png"))));
                messagePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeChatsIcon.png"))));
                setPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeSettingsIcon.png"))));
                insightPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeInsightsIcon.png"))));
                searchPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeSearchIcons.png"))));
                postPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("post-icon1.png"))));
            }
        }
    };

    ChangeListener<Boolean> listener3 = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
            if (t1) {
                myStage.heightProperty().removeListener(listener);
                myStage.widthProperty().removeListener(listener);
                App.theme.removeListener(listener2);

                myStage.widthProperty().addListener(listener);
                myStage.heightProperty().addListener(listener);
                App.theme.addListener(listener2);
            }
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            if (!User.getUserType(User.getLoggedInUsername()))
                insightBtn.setDisable(true);
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }

        selectedChat.set(-1);
        toggleChange.set(false);

        try {
            chats = Chat.getChats();
        } catch (NoUserLoggedInException | UserDoesNotExistException e) {
            e.printStackTrace();
        }

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
                myStage = (Stage) main.getScene().getWindow();

                myStage.focusedProperty().addListener(listener3);

                if (App.theme.getValue() == 0) {
                    lightMode.selectedProperty().set(true);
                    darkMode.selectedProperty().set(false);
                    App.getScene().getStylesheets().clear();
                    App.getScene().getStylesheets().add(this.getClass().getResource("LightModeChat.css").toExternalForm());
                    homePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeHomeIcon.png"))));
                    notePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeNotificationsIcon.png"))));
                    messagePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeChatIcon.png"))));
                    setPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeSettingsIcon.png"))));
                    insightPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeInsightsIcon.png"))));
                    searchPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("lightModeSearchIcons.png"))));
                    postPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("post-icon.png"))));
                }
                else if (App.theme.getValue() == 1) {
                    lightMode.selectedProperty().set(false);
                    darkMode.selectedProperty().set(true);
                    App.getScene().getStylesheets().clear();
                    App.getScene().getStylesheets().add(this.getClass().getResource("DarkModeChat.css").toExternalForm());
                    homePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeHomeIcons.png"))));
                    notePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeNotificationsIcon.png"))));
                    messagePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeChatsIcon.png"))));
                    setPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeSettingsIcon.png"))));
                    insightPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeInsightsIcon.png"))));
                    searchPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("darkModeSearchIcons.png"))));
                    postPhoto.setImage(new Image(String.valueOf(this.getClass().getResource("post-icon1.png"))));
                }

                setDimensions();
                loadChats();

                myStage.widthProperty().addListener(listener);

                myStage.heightProperty().addListener(listener);

                App.theme.addListener(listener2);

                allChats.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                        if (t1) {
                            chatFilter = 0;
                            setDimensions();
                            loadChats();
                            clickedHBoxID = null;
                        }
                    }
                });

                personChat.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                        if (t1) {
                            chatFilter = 1;
                            setDimensions();
                            loadChats();
                            clickedHBoxID = null;
                        }
                    }
                });

                groupChat.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                        if (t1) {
                            chatFilter = 2;
                            setDimensions();
                            loadChats();
                            clickedHBoxID = null;
                        }
                    }
                });

                selectedChat.addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        setDimensions();
                        loadChats();
                    }
                });

                toggleChange.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                        setDimensions();
                        loadChats();
                    }
                });
            }
        });
    }

    public void setDimensions() {
        splitPane.setPrefWidth(myStage.getWidth());
        splitPane.setPrefHeight(myStage.getHeight());

        rightSide.setPrefWidth(myStage.getWidth() / 4);
        leftSide.setPrefWidth(myStage.getWidth() / 4);
        mainSide.setPrefWidth(myStage.getWidth() / 2);
        leftSide.setPrefHeight(myStage.getHeight());
        mainSide.setPrefHeight(myStage.getHeight());
        rightSide.setPrefHeight(myStage.getHeight());
        mainSide.setLayoutX(myStage.getWidth() / 4);
        mainSide.setLayoutY(0);

        chatsVBox.setPrefWidth(mainSide.getPrefWidth());
        chatsVBox.setPrefHeight(myStage.getHeight());
//        chatsVBox.setManaged(false);

        chatNameHBox.setPrefWidth(mainSide.getPrefWidth());
        chatNameHBox.setPrefHeight(mainSide.getHeight() * 2 / 40);
//        chatNameHBox.setLayoutY(0);
//        chatNameAnchor.setLayoutX(mainSide.getLayoutX());

        messageScroll.setPrefWidth(mainSide.getPrefWidth());
        messageScroll.setPrefHeight(mainSide.getHeight() * 32 / 40);
//        messageScroll.setLayoutY(chatNameAnchor.getLayoutY() + chatNameAnchor.getPrefHeight());
//        messageScroll.setLayoutX(mainSide.getLayoutX());

        messageArea.setPrefWidth(mainSide.getPrefWidth());
        messageArea.setPrefHeight(myStage.getHeight() * 3);
//        messageArea.setLayoutY(messageScroll.getLayoutY());
//        messageArea.setLayoutX(messageScroll.getLayoutX());

        typeArea.setPrefWidth(mainSide.getWidth());
        typeArea.setPrefHeight(mainSide.getHeight() * 5 / 80);
//        typeArea.setLayoutX(mainSide.getLayoutX() - typeArea.getPrefWidth()/2);
//        typeArea.setLayoutY(myStage.getHeight() - 2 * typeArea.getPrefHeight());


        typeField.setPrefWidth(typeArea.getPrefWidth());
        typeField.setPrefHeight(typeArea.getPrefHeight());

        menuVBox.setPrefWidth(leftSide.getPrefWidth());
        menuVBox.setPrefHeight(leftSide.getPrefHeight());
        menuVBox.setSpacing(myStage.getHeight() / 80);

        appTitle.setPrefWidth(leftSide.getPrefWidth());
        appTitle.setPrefHeight(myStage.getHeight() / 20);
        appTitle.setLayoutX(leftSide.getPrefWidth()/2 - appTitle.getPrefHeight()/2);

        menuHbox1.setPrefWidth(myStage.getWidth() * 185 / 800);
        menuHbox1.setPrefHeight(myStage.getHeight() * 3 / 45);
        menuHbox1.setLayoutX(myStage.getWidth()/8 - menuHbox1.getPrefWidth()/2);

        menuHbox2.setPrefWidth(myStage.getWidth() * 185 / 800);
        menuHbox2.setPrefHeight(myStage.getHeight() * 3 / 45);
        menuHbox2.setLayoutX(myStage.getWidth()/8 - menuHbox2.getPrefWidth()/2);

        menuHbox3.setPrefWidth(myStage.getWidth() * 185 / 800);
        menuHbox3.setPrefHeight(myStage.getHeight() * 3 / 45);
        menuHbox3.setLayoutX(myStage.getWidth()/8 - menuHbox3.getPrefWidth()/2);

        menuHbox4.setPrefWidth(myStage.getWidth() * 185 / 800);
        menuHbox4.setPrefHeight(myStage.getHeight() * 3 / 45);
        menuHbox4.setLayoutX(myStage.getWidth()/8 - menuHbox4.getPrefWidth()/2);

        menuHbox5.setPrefWidth(myStage.getWidth() * 185 / 800);
        menuHbox5.setPrefHeight(myStage.getHeight() * 3 / 45);
        menuHbox5.setLayoutX(myStage.getWidth()/8 - menuHbox5.getPrefWidth()/2);

        menuHbox6.setPrefWidth(myStage.getWidth() * 185 / 800);
        menuHbox6.setPrefHeight(myStage.getHeight() * 3 / 45);
        menuHbox6.setLayoutX(myStage.getWidth()/8 - menuHbox6.getPrefWidth()/2);

        menuHbox7.setPrefWidth(myStage.getWidth() * 185 / 800);
        menuHbox7.setPrefHeight(myStage.getHeight() * 3 / 45);
        menuHbox7.setLayoutX(myStage.getWidth()/8 - menuHbox7.getPrefWidth()/2);

        menuHbox8.setPrefWidth(myStage.getWidth() * 185 / 800);
        menuHbox8.setPrefHeight(myStage.getHeight() * 3 / 45);
        menuHbox8.setLayoutX(myStage.getWidth()/8 - menuHbox8.getPrefWidth()/2);

        homeBtn.setPrefWidth(menuHbox1.getPrefWidth() * 7.2 / 9);
        noteBtn.setPrefWidth(menuHbox1.getPrefWidth() * 7.2 / 9);
        messageBtn.setPrefWidth(menuHbox1.getPrefWidth() * 7.2 / 9);
        setBtn.setPrefWidth(menuHbox1.getPrefWidth() * 7.2 / 9);
        insightBtn.setPrefWidth(menuHbox1.getPrefWidth() * 7.2 / 9);
        searchBtn.setPrefWidth(menuHbox1.getPrefWidth() * 7.2 / 9);
        postBtn.setPrefWidth(menuHbox1.getPrefWidth() * 7.2 / 9);
        groupBtn.setPrefWidth(menuHbox1.getPrefWidth() * 7.2 / 9);

        chatNameScroll.setPrefWidth(rightSide.getWidth());
        chatNameScroll.setPrefHeight(myStage.getHeight());
        chatNameScroll.setLayoutY(0);

        chatNameAnchor.setPrefWidth(chatNameScroll.getPrefWidth());

        themeHBox.setPrefWidth(leftSide.getPrefWidth() * 8 / 10);
        themeHBox.setPrefHeight(myStage.getHeight() * 4 / 45);
        themeHBox.setLayoutX(leftSide.getLayoutX() + leftSide.getWidth()/2 - themeHBox.getPrefWidth()/2 + 5);

        darkMode.setPrefWidth(themeHBox.getPrefWidth()/2);
        lightMode.setPrefWidth(themeHBox.getPrefWidth()/2);

        filterHBox.setPrefWidth(leftSide.getPrefWidth() * 8 / 10);
        filterHBox.setPrefHeight(myStage.getHeight() * 4 / 45);
        filterHBox.setLayoutX(leftSide.getLayoutX() + leftSide.getWidth()/2 - filterHBox.getPrefWidth()/2 + 5);

        allChats.setPrefWidth(filterHBox.getPrefWidth()/3);
        personChat.setPrefWidth(filterHBox.getPrefWidth()/3);
        groupChat.setPrefWidth(filterHBox.getPrefWidth()/3);

//        ((Separator) chatsVBox.getChildren().get(1)).setPrefWidth(mainSide.getWidth());

        if (selectedChat.getValue() == -1) {
            chatNameHBox.setDisable(true);
            typeArea.setDisable(true);

            typeField.setOnKeyPressed(null);
        }
        else {
            chatNameHBox.setDisable(false);
            typeArea.setDisable(false);

            Chat myChat = chats.get(selectedChat.getValue());
            ((Label)(chatNameHBox.getChildren().get(0))).setText(myChat.getName());

            if (myChat.getGroup()) {
                chatNameHBox.setOnMouseClicked(mc -> {
                    try {
                        showGroupInfo(myChat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            else {
                chatNameHBox.setOnMouseClicked(null);
            }

            showMessages(myChat);

            typeField.setOnKeyPressed(kp -> {
                typeField.setStyle("-fx-text-fill: #000000");
                if (!typeField.getText().equals("")) {
                    if (kp.getCode().equals(KeyCode.ENTER)) {
                        if (myChat.getGroup()) {
                            String content = typeField.getText();
                            try {
                                String groupID = Group.getGroupID(myChat.getName());

                                User.sendGroupMessage(groupID, content);
                                chats = Chat.getChats();
                                selectedChat.set(chats.indexOf(myChat));
                                toggleChange.set(!toggleChange.getValue());
                                typeField.clear();
                            }
                            catch (GroupDoesNotExistException | NoUserLoggedInException | UserDoesNotExistException | MessageContentNullException | UserNotMemberException | UserWasBannedException | UnauthorisedEditException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            String content = typeField.getText();
                            String receiverName = myChat.getName();

                            try {
                                User.sendDirectMessage(receiverName, content);
                                chats = Chat.getChats();
                                selectedChat.set(chats.indexOf(myChat));
                                toggleChange.set(!toggleChange.getValue());
                                typeField.clear();
                            }
                            catch (NoUserLoggedInException | UserDoesNotExistException | MessageContentNullException | UnauthorisedEditException e) {
                                e.printStackTrace();
                            }
                            catch (UserWasBannedException e) {
                                System.out.println(e.getMessage());
                                typeField.setText("You were blocked by the selected user");
                                typeField.setStyle("-fx-text-fill: #ff0000");
                            }
                        }
                    }
                }
            });
        }
    }

    public void showGroupInfo(Chat chat) throws IOException {
        Stage newStage = new Stage();
        GroupInfoController.myChat = chat;
        Scene newScene = new Scene(App.loadFXML("GroupInfo"));
        newStage.setScene(newScene);
        newStage.setWidth(450);
        newStage.setHeight(600);
        newStage.show();
    }

    public void loadChats() {
        double yPos = 0;

        String lastClickedChatName = null;
        for (HBox chatNameHolder : chatNameHolders) {
            if (chatNameHolder.getId().equals(clickedHBoxID))
                lastClickedChatName = ((Label)chatNameHolder.getChildren().get(1)).getText();
        }

        chatNameAnchor.getChildren().clear();
        chatNameHolders.clear();

        if (chatFilter == 0) {
            for (Chat chat : chats) {
                HBox newHBox = new HBox();
                chatNameHolders.add(newHBox);

                newHBox.setPrefWidth(chatNameScroll.getPrefWidth());
                newHBox.setPrefHeight(myStage.getHeight() * 4 / 45);
                newHBox.setAlignment(Pos.CENTER_LEFT);
                newHBox.setId("chatNameContainer" + chats.indexOf(chat));

                ImageView profilePhoto = new ImageView();
                if (chat.getPhotoPath() != null) {
                    Image myImage = new Image(chat.getPhotoPath());
                    profilePhoto.setImage(myImage);
                }
                else {
                    Image myImage = new Image(String.valueOf(this.getClass().getResource("Cool-icon.png")));
                    profilePhoto.setImage(myImage);
                }
                profilePhoto.setFitWidth(myStage.getHeight() * 4 / 45);
                profilePhoto.setFitHeight(myStage.getHeight() * 4 / 45);
                profilePhoto.setId("chatPhoto");

                newHBox.getChildren().add(profilePhoto);

                Label chatName = new Label();
                chatName.setFont(Font.font("Arial", FontWeight.MEDIUM, 14 ));
                chatName.setAlignment(Pos.CENTER_LEFT);
                chatName.setText(chat.getName());
                chatName.setId("chatName");


                newHBox.getChildren().add(chatName);

                Label newMessageCount = new Label();
                newMessageCount.setFont(Font.font("Arial", FontWeight.MEDIUM, 14 ));
                newMessageCount.setAlignment(Pos.CENTER_LEFT);
                if (chat.getNewMessageNum() != -1 && chat.getNewMessageNum() != 0) {
                    newMessageCount.setText(Integer.toString(chat.getNewMessageNum()));
                    newMessageCount.setStyle(String.format("-fx-background-insets: 0 0 0 %f; -fx-background-radius: %f; -fx-padding: %f %f %f %f",
                            (myStage.getWidth() / 70), (myStage.getWidth() / 40), (myStage.getWidth() / 160), (myStage.getWidth() / 160 * 2),
                            (myStage.getWidth() / 160), (myStage.getWidth() / 160 * 4)));
                    newMessageCount.setId("newMessageCount");
                }
                else
                    newMessageCount.setText("");

                newHBox.getChildren().add(newMessageCount);

                newHBox.setSpacing(rightSide.getWidth() / 15);

                newHBox.setLayoutY(yPos);

                yPos += myStage.getHeight() * 4 / 45;

                Line separator = new Line();
                separator.setStartX(chatNameAnchor.getLayoutX());
                separator.setEndX(chatNameAnchor.getLayoutX() + chatNameAnchor.getWidth());
                separator.setStartY(yPos);
                separator.setEndY(yPos);
                separator.setStrokeWidth(1);
                yPos++;

                if (App.theme.getValue() == 0) {
                    if (chat.getName().equals(lastClickedChatName)) {
                        if (chat.getPhotoPath() == null)
                            profilePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon2.png"))));
                        newHBox.setStyle("-fx-background-color: #0571ED;");
                        newHBox.getChildren().get(1).setStyle("-fx-text-fill: #ffffff");
                    }

                    newHBox.setOnMouseEntered(me -> {
                        try {
                            if (!clickedHBoxID.equals(newHBox.getId()))
                                newHBox.setStyle("-fx-background-color: #dddddd;");
                        }
                        catch (NullPointerException e) {
                            newHBox.setStyle("-fx-background-color: #dddddd;");
                        }
                    });
                    newHBox.setOnMouseExited(me -> {
                        try {
                            if (!clickedHBoxID.equals(newHBox.getId()))
                                newHBox.setStyle("-fx-background-color: #ffffff;");
                        }
                        catch (NullPointerException e) {
                            newHBox.setStyle("-fx-background-color: #ffffff;");
                        }
                    });
                    newHBox.setOnMouseClicked(mc -> {
                        for (HBox chatNameHolder : chatNameHolders) {
                            chatNameHolder.setStyle("-fx-background-color: #ffffff;");
                            chatNameHolder.getChildren().get(1).setStyle("-fx-text-fill: #000000");

                            if (chats.get(chatNameHolders.indexOf(chatNameHolder)).getPhotoPath() == null)
                                ((ImageView)chatNameHolder.getChildren().get(0)).setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon.png"))));
                        }

                        if (chat.getPhotoPath() == null)
                            profilePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon2.png"))));
                        newHBox.setStyle("-fx-background-color: #0571ED;");
                        newHBox.getChildren().get(1).setStyle("-fx-text-fill: #ffffff");
                        clickedHBoxID = newHBox.getId();
                        selectedChat.set(chats.indexOf(chat));
                    });
                }
                else if (App.theme.getValue() == 1) {
                    if (chat.getName().equals(lastClickedChatName)) {
                        if (chat.getPhotoPath() == null)
                            profilePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon2.png"))));
                        newHBox.setStyle("-fx-background-color: #1e80e9;");
                        newHBox.getChildren().get(1).setStyle("-fx-text-fill: #ffffff");
                    }

                    if (chat.getPhotoPath() == null)
                        profilePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon1.png"))));

                    chatName.setStyle("-fx-text-fill: #ffffff;");

                    newHBox.setOnMouseEntered(me -> {
                        try {
                            if (!clickedHBoxID.equals(newHBox.getId()))
                                newHBox.setStyle("-fx-background-color: #777777;");
                        }
                        catch (NullPointerException e) {
                            newHBox.setStyle("-fx-background-color: #dddddd;");
                        }
                    });
                    newHBox.setOnMouseExited(me -> {
                        try {
                            if (!clickedHBoxID.equals(newHBox.getId()))
                                newHBox.setStyle("-fx-background-color: #111111;");
                        }
                        catch (NullPointerException e) {
                            newHBox.setStyle("-fx-background-color: #111111;");
                        }
                    });
                    newHBox.setOnMouseClicked(mc -> {
                        for (HBox chatNameHolder : chatNameHolders) {
                            chatNameHolder.setStyle("-fx-background-color: #111111;");

                            if (chats.get(chatNameHolders.indexOf(chatNameHolder)).getPhotoPath() == null)
                                ((ImageView)chatNameHolder.getChildren().get(0)).setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon1.png"))));
                        }

                        if (chat.getPhotoPath() == null)
                            profilePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon2.png"))));
                        newHBox.setStyle("-fx-background-color: #1e80e9;");
                        clickedHBoxID = newHBox.getId();
                        selectedChat.set(chats.indexOf(chat));
                    });
                }

                chatNameAnchor.getChildren().add(newHBox);
                chatNameAnchor.getChildren().add(separator);
            }
        }
        else if (chatFilter == 1) {
            for (Chat chat : chats) {
                if (!chat.getGroup()) {
                    HBox newHBox = new HBox();
                    chatNameHolders.add(newHBox);

                    newHBox.setPrefWidth(chatNameScroll.getPrefWidth());
                    newHBox.setPrefHeight(myStage.getHeight() * 4 / 45);
                    newHBox.setAlignment(Pos.CENTER_LEFT);
                    newHBox.setId("chatNameContainer" + chats.indexOf(chat));

                    ImageView profilePhoto = new ImageView();
                    if (chat.getPhotoPath() != null) {
                        Image myImage = new Image(chat.getPhotoPath());
                        profilePhoto.setImage(myImage);
                    }
                    else {
                        Image myImage = new Image(String.valueOf(this.getClass().getResource("Cool-icon.png")));
                        profilePhoto.setImage(myImage);
                    }
                    profilePhoto.setFitWidth(myStage.getHeight() * 4 / 45);
                    profilePhoto.setFitHeight(myStage.getHeight() * 4 / 45);
                    profilePhoto.setId("chatPhoto");

                    newHBox.getChildren().add(profilePhoto);

                    Label chatName = new Label();
                    chatName.setFont(Font.font("Arial", FontWeight.MEDIUM, 14 ));
                    chatName.setAlignment(Pos.CENTER_LEFT);
                    chatName.setText(chat.getName());
                    chatName.setId("chatName");

                    newHBox.getChildren().add(chatName);
                    newHBox.setSpacing(rightSide.getWidth() / 15);

                    newHBox.setLayoutY(yPos);

                    yPos += myStage.getHeight() * 4 / 45;

                    Line separator = new Line();
                    separator.setStartX(chatNameAnchor.getLayoutX());
                    separator.setEndX(chatNameAnchor.getLayoutX() + chatNameAnchor.getWidth());
                    separator.setStartY(yPos);
                    separator.setEndY(yPos);
                    separator.setStrokeWidth(1);
                    yPos++;

                    if (App.theme.getValue() == 0) {
                        if (chat.getName().equals(lastClickedChatName)) {
                            if (chat.getPhotoPath() == null)
                                profilePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon2.png"))));
                            newHBox.setStyle("-fx-background-color: #0571ED;");
                            newHBox.getChildren().get(1).setStyle("-fx-text-fill: #ffffff");
                        }

                        newHBox.setOnMouseEntered(me -> {
                            try {
                                if (!clickedHBoxID.equals(newHBox.getId()))
                                    newHBox.setStyle("-fx-background-color: #dddddd;");
                            }
                            catch (NullPointerException e) {
                                newHBox.setStyle("-fx-background-color: #dddddd;");
                            }
                        });
                        newHBox.setOnMouseExited(me -> {
                            try {
                                if (!clickedHBoxID.equals(newHBox.getId()))
                                    newHBox.setStyle("-fx-background-color: #ffffff;");
                            }
                            catch (NullPointerException e) {
                                newHBox.setStyle("-fx-background-color: #ffffff;");
                            }
                        });
                        newHBox.setOnMouseClicked(mc -> {
                            for (HBox chatNameHolder : chatNameHolders) {
                                chatNameHolder.setStyle("-fx-background-color: #ffffff;");
                                chatNameHolder.getChildren().get(1).setStyle("-fx-text-fill: #000000");

                                if (chats.get(chatNameHolders.indexOf(chatNameHolder)).getPhotoPath() == null)
                                    ((ImageView)chatNameHolder.getChildren().get(0)).setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon.png"))));
                            }

                            if (chat.getPhotoPath() == null)
                                profilePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon2.png"))));
                            newHBox.setStyle("-fx-background-color: #0571ED;");
                            newHBox.getChildren().get(1).setStyle("-fx-text-fill: #ffffff");
                            clickedHBoxID = newHBox.getId();

                            selectedChat.set(chats.indexOf(chat));
                        });
                    }
                    else if (App.theme.getValue() == 1) {
                        if (chat.getName().equals(lastClickedChatName)) {
                            if (chat.getPhotoPath() == null)
                                profilePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon2.png"))));
                            newHBox.setStyle("-fx-background-color: #1e80e9;");
                            newHBox.getChildren().get(1).setStyle("-fx-text-fill: #ffffff");
                        }

                        if (chat.getPhotoPath() == null)
                            profilePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon1.png"))));

                        chatName.setStyle("-fx-text-fill: #ffffff;");

                        newHBox.setOnMouseEntered(me -> {
                            try {
                                if (!clickedHBoxID.equals(newHBox.getId()))
                                    newHBox.setStyle("-fx-background-color: #777777;");
                            }
                            catch (NullPointerException e) {
                                newHBox.setStyle("-fx-background-color: #dddddd;");
                            }
                        });
                        newHBox.setOnMouseExited(me -> {
                            try {
                                if (!clickedHBoxID.equals(newHBox.getId()))
                                    newHBox.setStyle("-fx-background-color: #111111;");
                            }
                            catch (NullPointerException e) {
                                newHBox.setStyle("-fx-background-color: #111111;");
                            }
                        });
                        newHBox.setOnMouseClicked(mc -> {
                            for (HBox chatNameHolder : chatNameHolders) {
                                chatNameHolder.setStyle("-fx-background-color: #111111;");

                                if (chats.get(chatNameHolders.indexOf(chatNameHolder)).getPhotoPath() == null)
                                    ((ImageView)chatNameHolder.getChildren().get(0)).setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon1.png"))));
                            }

                            if (chat.getPhotoPath() == null)
                                profilePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon2.png"))));
                            newHBox.setStyle("-fx-background-color: #1e80e9;");
                            clickedHBoxID = newHBox.getId();

                            selectedChat.set(chats.indexOf(chat));
                        });
                    }

                    chatNameAnchor.getChildren().add(newHBox);
                    chatNameAnchor.getChildren().add(separator);
                }

                chatNameAnchor.setPrefHeight(chats.size() * myStage.getHeight() * 4 / 45 + myStage.getHeight());
                }
        }
        else if (chatFilter == 2) {
            for (Chat chat : chats) {
                if (chat.getGroup()) {
                    HBox newHBox = new HBox();
                    chatNameHolders.add(newHBox);

                    newHBox.setPrefWidth(chatNameScroll.getPrefWidth());
                    newHBox.setPrefHeight(myStage.getHeight() * 4 / 45);
                    newHBox.setAlignment(Pos.CENTER_LEFT);
                    newHBox.setId("chatNameContainer" + chats.indexOf(chat));

                    ImageView profilePhoto = new ImageView();
                    if (chat.getPhotoPath() != null) {
                        Image myImage = new Image(chat.getPhotoPath());
                        profilePhoto.setImage(myImage);
                    }
                    else {
                        Image myImage = new Image(String.valueOf(this.getClass().getResource("Cool-icon.png")));
                        profilePhoto.setImage(myImage);
                    }
                    profilePhoto.setFitWidth(myStage.getHeight() * 4 / 45);
                    profilePhoto.setFitHeight(myStage.getHeight() * 4 / 45);
                    profilePhoto.setId("chatPhoto");

                    newHBox.getChildren().add(profilePhoto);

                    Label chatName = new Label();
                    chatName.setFont(Font.font("Arial", FontWeight.MEDIUM, 14 ));
                    chatName.setAlignment(Pos.CENTER_LEFT);
                    chatName.setText(chat.getName());
                    chatName.setId("chatName");

                    newHBox.getChildren().add(chatName);
                    newHBox.setSpacing(rightSide.getWidth() / 15);

                    newHBox.setLayoutY(yPos);

                    yPos += myStage.getHeight() * 4 / 45;

                    Line separator = new Line();
                    separator.setStartX(chatNameAnchor.getLayoutX());
                    separator.setEndX(chatNameAnchor.getLayoutX() + chatNameAnchor.getWidth());
                    separator.setStartY(yPos);
                    separator.setEndY(yPos);
                    separator.setStrokeWidth(1);
                    yPos++;

                    if (App.theme.getValue() == 0) {
                        if (chat.getName().equals(lastClickedChatName)) {
                            if (chat.getPhotoPath() == null)
                                profilePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon2.png"))));
                            newHBox.setStyle("-fx-background-color: #0571ED;");
                            newHBox.getChildren().get(1).setStyle("-fx-text-fill: #ffffff");
                        }

                        newHBox.setOnMouseEntered(me -> {
                            try {
                                if (!clickedHBoxID.equals(newHBox.getId()))
                                    newHBox.setStyle("-fx-background-color: #dddddd;");
                            }
                            catch (NullPointerException e) {
                                newHBox.setStyle("-fx-background-color: #dddddd;");
                            }
                        });
                        newHBox.setOnMouseExited(me -> {
                            try {
                                if (!clickedHBoxID.equals(newHBox.getId()))
                                    newHBox.setStyle("-fx-background-color: #ffffff;");
                            }
                            catch (NullPointerException e) {
                                newHBox.setStyle("-fx-background-color: #ffffff;");
                            }
                        });
                        newHBox.setOnMouseClicked(mc -> {
                            for (HBox chatNameHolder : chatNameHolders) {
                                chatNameHolder.setStyle("-fx-background-color: #ffffff;");
                                chatNameHolder.getChildren().get(1).setStyle("-fx-text-fill: #000000");

                                if (chats.get(chatNameHolders.indexOf(chatNameHolder)).getPhotoPath() == null)
                                    ((ImageView)chatNameHolder.getChildren().get(0)).setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon.png"))));
                            }

                            if (chat.getPhotoPath() == null)
                                profilePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon2.png"))));
                            newHBox.setStyle("-fx-background-color: #0571ED;");
                            newHBox.getChildren().get(1).setStyle("-fx-text-fill: #ffffff");
                            clickedHBoxID = newHBox.getId();

                            selectedChat.set(chats.indexOf(chat));
                        });
                    }
                    else if (App.theme.getValue() == 1) {
                        if (chat.getName().equals(lastClickedChatName)) {
                            if (chat.getPhotoPath() == null)
                                profilePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon2.png"))));
                            newHBox.setStyle("-fx-background-color: #1e80e9;");
                            newHBox.getChildren().get(1).setStyle("-fx-text-fill: #ffffff");
                        }

                        if (chat.getPhotoPath() == null)
                            profilePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon1.png"))));

                        chatName.setStyle("-fx-text-fill: #ffffff;");

                        newHBox.setOnMouseEntered(me -> {
                            try {
                                if (!clickedHBoxID.equals(newHBox.getId()))
                                    newHBox.setStyle("-fx-background-color: #777777;");
                            }
                            catch (NullPointerException e) {
                                newHBox.setStyle("-fx-background-color: #dddddd;");
                            }
                        });
                        newHBox.setOnMouseExited(me -> {
                            try {
                                if (!clickedHBoxID.equals(newHBox.getId()))
                                    newHBox.setStyle("-fx-background-color: #111111;");
                            }
                            catch (NullPointerException e) {
                                newHBox.setStyle("-fx-background-color: #111111;");
                            }
                        });
                        newHBox.setOnMouseClicked(mc -> {
                            for (HBox chatNameHolder : chatNameHolders) {
                                chatNameHolder.setStyle("-fx-background-color: #111111;");

                                if (chats.get(chatNameHolders.indexOf(chatNameHolder)).getPhotoPath() == null)
                                    ((ImageView)chatNameHolder.getChildren().get(0)).setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon1.png"))));
                            }

                            if (chat.getPhotoPath() == null)
                                profilePhoto.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon2.png"))));
                            newHBox.setStyle("-fx-background-color: #1e80e9;");
                            clickedHBoxID = newHBox.getId();

                            selectedChat.set(chats.indexOf(chat));
                        });
                    }

                    chatNameAnchor.getChildren().add(newHBox);
                    chatNameAnchor.getChildren().add(separator);
                }

                chatNameAnchor.setPrefHeight(chats.size() * myStage.getHeight() * 4 / 45 + myStage.getHeight());
            }
        }

        chatNameAnchor.setPrefHeight(chats.size() * myStage.getHeight() * 4 / 45 + myStage.getHeight());
    }

    public void showMessages(Chat chat) {
        messageArea.getChildren().clear();

        double ypos = messageArea.getLayoutY();

        if (!chat.getGroup()) {
            try {
                ArrayList<DirectMessage> messages = DirectMessage.getChatMessages(User.getUserID(chat.getName()));

                for (DirectMessage message : messages) {
                    HBox hBox = new HBox();
                    hBox.setPrefWidth(mainSide.getPrefWidth());
                    hBox.setPrefHeight(myStage.getHeight() * 4 / 45);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    hBox.setSpacing(10);
                    hBox.setStyle("-fx-padding: 20;");

                    ImageView profile = new ImageView();
                    profile.setFitWidth(40);
                    profile.setFitHeight(40);
                    profile.setId("messageProfID");

                    Label content = new Label();
                    content.setFont(Font.font("Arial", FontWeight.MEDIUM, 14));
                    content.setId("messageText");

                    hBox.getChildren().add(profile);
                    hBox.getChildren().add(content);

                    content.setText(message.getContent());

                    if (message.getMine()) {
                        hBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

                        content.setStyle("-fx-background-color: green; -fx-background-radius: 40; -fx-padding: 10");

                        if (!User.getUserProfile(User.getLoggedInUsername()).equals(""))
                            profile.setImage(new Image(String.valueOf(this.getClass().getResource(User.getUserProfile(User.getLoggedInUsername())))));
                        else
                            profile.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon1.png"))));
                    }
                    else {
                        hBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

                        content.setStyle("-fx-background-color: #0571ED; -fx-background-radius: 40; -fx-padding: 10");

                        if (chat.getPhotoPath() != null)
                            profile.setImage(new Image(String.valueOf(this.getClass().getResource(chat.getPhotoPath()))));
                        else
                            profile.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon1.png"))));
                    }

                    hBox.setLayoutY(ypos);
                    ypos += myStage.getHeight() * 4 / 45;

                    messageArea.getChildren().add(hBox);
                }

                messageArea.setPrefHeight(messages.size() * myStage.getHeight() * 4 / 45 + myStage.getHeight());
            }
            catch (UserDoesNotExistException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                ArrayList<GroupMessage> messages = GroupMessage.getMessages(chat.getName());

                for (GroupMessage message : messages) {
                    HBox hBox = new HBox();
                    hBox.setPrefWidth(mainSide.getPrefWidth());
                    hBox.setPrefHeight(myStage.getHeight() * 4 / 45);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    hBox.setSpacing(10);
                    hBox.setStyle("-fx-padding: 20;");

                    ImageView profile = new ImageView();
                    profile.setFitWidth(40);
                    profile.setFitHeight(40);
                    profile.setId("messageProfID");

                    Label content = new Label();
                    content.setFont(Font.font("Arial", FontWeight.MEDIUM, 14));
                    content.setId("messageText");

                    hBox.getChildren().add(profile);
                    hBox.getChildren().add(content);

                    content.setText(message.getContent());

                    if (message.getMine()) {
                        hBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

                        content.setStyle("-fx-background-color: green; -fx-background-radius: 40; -fx-padding: 10");

                        if (!User.getUserProfile(User.getLoggedInUsername()).equals(""))
                            profile.setImage(new Image(String.valueOf(this.getClass().getResource(User.getUserProfile(User.getLoggedInUsername())))));
                        else
                            profile.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon1.png"))));
                    }
                    else {
                        hBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

                        content.setStyle("-fx-background-color: #0571ED; -fx-background-radius: 40; -fx-padding: 10");

                        if (chat.getPhotoPath() != null)
                            profile.setImage(new Image(String.valueOf(this.getClass().getResource(message.getPhotoPath()))));
                        else
                            profile.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon1.png"))));
                    }

                    hBox.setLayoutY(ypos);
                    ypos += myStage.getHeight() * 4 / 45;

                    messageArea.getChildren().add(hBox);
                }

                messageArea.setPrefHeight(messages.size() * myStage.getHeight() * 4 / 45 + myStage.getHeight());
            }
            catch (UserDoesNotExistException e) {
                e.printStackTrace();
            }
        }

        try {
            chats = Chat.getChats();
            selectedChat.set(chats.indexOf(chat));

        }
        catch (NoUserLoggedInException e) {
            e.printStackTrace();
        }
        catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }
    }

    public void gotoHome() {

    }

    public void goToNotifications() throws IOException {
        myStage.heightProperty().removeListener(listener);
        myStage.widthProperty().removeListener(listener);
        App.theme.removeListener(listener2);

        Stage stage = new Stage();
        Scene scene = new Scene(App.loadFXML("Notifications"));
        stage.setScene(scene);
        stage.show();
    }

    public void gotoSettings() throws IOException {
        myStage.heightProperty().removeListener(listener);
        myStage.widthProperty().removeListener(listener);
        App.theme.removeListener(listener2);

        Stage stage = new Stage();
        Scene scene = new Scene(App.loadFXML("Setting"));
        stage.setScene(scene);
        stage.show();
    }

    public void gotoInsigts() throws IOException {
        myStage.heightProperty().removeListener(listener);
        myStage.widthProperty().removeListener(listener);
        App.theme.removeListener(listener2);

        Stage stage = new Stage();
        Scene scene = new Scene(App.loadFXML("Insights"));
        stage.setScene(scene);
        stage.show();
    }

    public void gotoSearchUser() throws IOException {
        myStage.heightProperty().removeListener(listener);
        myStage.widthProperty().removeListener(listener);
        App.theme.removeListener(listener2);

        Stage stage = new Stage();
        Scene scene = new Scene(App.loadFXML("SearchUserAccount"));
        stage.setScene(scene);
        stage.show();
    }

    public void createNewPost() {
        myStage.heightProperty().removeListener(listener);
        myStage.widthProperty().removeListener(listener);
        App.theme.removeListener(listener2);

    }

    public void createNewGroup() throws IOException {
        myStage.heightProperty().removeListener(listener);
        myStage.widthProperty().removeListener(listener);
        App.theme.removeListener(listener2);

        Stage stage = new Stage();
        Scene scene = new Scene(App.loadFXML("CreateGroup"));
        stage.setScene(scene);
        stage.show();
    }
}
