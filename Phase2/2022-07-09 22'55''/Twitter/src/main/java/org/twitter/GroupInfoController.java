package org.twitter;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.twitter.Exceptions.GroupDoesNotExistException;
import org.twitter.Exceptions.UserDoesNotExistException;
import org.twitter.Exceptions.UserNotAdminException;
import org.twitter.Exceptions.UserNotMemberException;
import org.twitter.ObjectClasses.Chat;
import org.twitter.ObjectClasses.Group;
import org.twitter.ObjectClasses.User;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GroupInfoController implements Initializable {
    @FXML
    private Circle addMember;

    @FXML
    private Label groupInfoTitle;

    @FXML
    private Label groupName;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private Label memberNum;

    @FXML
    private ImageView profile;

    @FXML
    private HBox titleHBox;

    @FXML
    VBox membersVBox;

    static Chat myChat = null;
    static Group myGroup = null;

    ArrayList<HBox> memeberHolder = new ArrayList<>();

    Stage myStage = null;
    Scene thisScene = null;

    static SimpleBooleanProperty toggleChange = new SimpleBooleanProperty();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toggleChange.set(false);

        try {
            System.out.println("SHIT");
            myGroup = Group.getGroup(myChat.getName());
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        groupName.setText(myGroup.getGroupName());
        memberNum.setText(Integer.toString(myGroup.getMemberCount()) + " MEMBERS");

        addMember.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("Circle.png")))));

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                groupInfoTitle.requestFocus();

                myStage = (Stage) profile.getScene().getWindow();
                thisScene = profile.getScene();
                myStage.setResizable(false);

                loadGroupInfo();

                toggleChange.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                        loadGroupInfo();
                        groupInfoTitle.requestFocus();
                    }
                });

                if (App.theme.getValue() == 0) {
                    thisScene.getStylesheets().clear();
                    thisScene.getStylesheets().add(this.getClass().getResource("LightModeGroupInfo.css").toExternalForm());
                    profile.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon.png"))));

                    addMember.setOnMouseEntered(me -> {
                        addMember.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("CircleDark.png")))));
                    });

                    addMember.setOnMouseExited(me -> {
                        addMember.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("Circle.png")))));
                    });
                }
                else {
                    thisScene.getStylesheets().clear();
                    thisScene.getStylesheets().add(this.getClass().getResource("DarkModeGroupInfo.css").toExternalForm());
                    profile.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon2.png"))));

                    addMember.setOnMouseEntered(me -> {
                        addMember.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("CircleLight.png")))));
                    });

                    addMember.setOnMouseExited(me -> {
                        addMember.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("Circle.png")))));
                    });
                }

                App.theme.addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        if ((int) t1 == 0) {
                            thisScene.getStylesheets().clear();
                            thisScene.getStylesheets().add(this.getClass().getResource("LightModeGroupInfo.css").toExternalForm());
                            profile.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon.png"))));

                            loadGroupInfo();

                            addMember.setOnMouseEntered(me -> {
                                addMember.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("CircleDark.png")))));
                            });

                            addMember.setOnMouseExited(me -> {
                                addMember.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("Circle.png")))));
                            });
                        }
                        else if ((int) t1 == 1) {
                            thisScene.getStylesheets().clear();
                            thisScene.getStylesheets().add(this.getClass().getResource("DarkModeGroupInfo.css").toExternalForm());
                            profile.setImage(new Image(String.valueOf(this.getClass().getResource("Cool-icon2.png"))));

                            loadGroupInfo();

                            addMember.setOnMouseEntered(me -> {
                                addMember.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("CircleLight.png")))));
                            });

                            addMember.setOnMouseExited(me -> {
                                addMember.setFill(new ImagePattern(new Image(String.valueOf(this.getClass().getResource("Circle.png")))));
                            });
                        }
                    }
                });
            }
        });
    }

    public void loadGroupInfo() {
        boolean found = false;
        for (String s : myGroup.getAdminsID()) {
            if (s.equalsIgnoreCase(User.getLoggedInUsername())) {
                found = true;
                break;
            }
        }

        if (!found)
            addMember.setDisable(true);

        double yPos = 0;

        membersVBox.getChildren().clear();
        memeberHolder.clear();

        for (int i = 0; i < myGroup.getMemberCount(); i++) {
            String memberID = myGroup.getUsersID().get(i);

            HBox hBox = new HBox();
            memeberHolder.add(hBox);

            hBox.setPrefWidth(membersVBox.getPrefWidth());
            hBox.setPrefHeight(myStage.getHeight() * 4 / 45);
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setSpacing(myStage.getWidth() / 60);
            hBox.setId("memberHolder" + i);

            ImageView profilePhoto = new ImageView();
            if (!myGroup.getProfilePhotos().get(i).equals("")) {
                Image myImage = new Image(String.valueOf(this.getClass().getResource(myGroup.getProfilePhotos().get(i))));
                profilePhoto.setImage(myImage);
            }
            else {
                Image myImage;
                if (App.theme.getValue() == 0)
                    myImage = new Image(String.valueOf(this.getClass().getResource("Cool-icon.png")));
                else
                    myImage = new Image(String.valueOf(this.getClass().getResource("Cool-icon2.png")));

                profilePhoto.setImage(myImage);
            }

            profilePhoto.setFitWidth(myStage.getHeight() * 4 / 45);
            profilePhoto.setFitHeight(myStage.getHeight() * 4 / 45);
            profilePhoto.setId("memberPhoto");

            hBox.getChildren().add(profilePhoto);

            Label memberName = new Label();
            memberName.setFont(Font.font("Arial", FontWeight.MEDIUM, 14 ));
            memberName.setAlignment(Pos.CENTER_LEFT);
            memberName.setText(memberID);
            memberName.setId("memberName");

            hBox.getChildren().add(memberName);

            Label separator = new Label();
            separator.setPrefWidth(myStage.getWidth()/25);
            separator.setPrefHeight(myStage.getHeight() * 4 / 45);
            separator.setMinHeight(Label.USE_PREF_SIZE);
            separator.setMaxWidth(Label.USE_PREF_SIZE);
            separator.setMaxHeight(Label.USE_PREF_SIZE);
            separator.resize(myStage.getWidth()/5,myStage.getHeight() * 4 / 45);

            hBox.getChildren().add(separator);

            Label ownerText = new Label();
            Label adminsText = new Label();
            Hyperlink promote = new Hyperlink();
            Hyperlink ban = new Hyperlink();
            Hyperlink kick = new Hyperlink();
            hBox.getChildren().add(ownerText);
            hBox.getChildren().add(adminsText);
            hBox.getChildren().add(promote);
            hBox.getChildren().add(ban);
            hBox.getChildren().add(kick);

            if (memberID.equalsIgnoreCase(User.getLoggedInUsername()))  {
                promote.setDisable(true);
                ban.setDisable(true);
                kick.setDisable(true);
            }

            promote.setFont(Font.font("Arial", FontWeight.MEDIUM, 14 ));
            promote.setAlignment(Pos.CENTER_LEFT);
            promote.setId("changeAdmin");
            ban.setFont(Font.font("Arial", FontWeight.MEDIUM, 14 ));
            ban.setAlignment(Pos.CENTER_LEFT);
            ban.setId("ban");
            kick.setFont(Font.font("Arial", FontWeight.MEDIUM, 14 ));
            kick.setAlignment(Pos.CENTER_LEFT);
            kick.setId("kick");

            if (myGroup.getBanned().get(i)) {
                ban.setText("UNBAN");

                boolean found1 = false;
                for (String s : myGroup.getAdminsID()) {
                    if (s.equalsIgnoreCase(User.getLoggedInUsername())) {
                        found1 = true;
                        break;
                    }
                }

                if (found1)
                ban.setOnAction(ae -> {
                    try {
                        Group.unbanMember(User.getUserID(memberID), User.getUserID(User.getLoggedInUsername()), myGroup.getGroupName());
                        ban.setText("BAN");
                        myGroup = Group.getGroup(myChat.getName());
                        toggleChange.set(!toggleChange.get());
                    } catch (UserNotAdminException e) {
                        e.printStackTrace();
                    } catch (GroupDoesNotExistException e) {
                        e.printStackTrace();
                    } catch (UserDoesNotExistException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
                else
                    ban.setDisable(true);

            }
            else {
                try {
                    if (!User.getUserID(memberID).equalsIgnoreCase(myGroup.getOwnerID())) {
                        ban.setText("BAN");

                        boolean found2 = false;
                        for (String s : myGroup.getAdminsID()) {
                            if (s.equalsIgnoreCase(User.getLoggedInUsername())) {
                                found2 = true;
                                break;
                            }
                        }

                        if (found2)
                            ban.setOnAction(ae -> {
                                try {
                                    Group.banMember(User.getUserID(memberID), User.getUserID(User.getLoggedInUsername()), myGroup.getGroupName());
                                    ban.setText("UNBAN");
                                    myGroup = Group.getGroup(myChat.getName());
                                    toggleChange.set(!toggleChange.get());
                                } catch (UserNotAdminException e) {
                                    e.printStackTrace();
                                } catch (GroupDoesNotExistException e) {
                                    e.printStackTrace();
                                } catch (UserDoesNotExistException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            });
                        else
                            ban.setDisable(true);
                    }
                } catch (UserDoesNotExistException e) {
                    e.printStackTrace();
                }
            }

            try {
                if (myGroup.getOwnerID().equalsIgnoreCase(User.getUserID(memberID))) {
                    ownerText.setFont(Font.font("Arial", FontWeight.MEDIUM, 14 ));
                    ownerText.setAlignment(Pos.CENTER_LEFT);
                    ownerText.setText("Owner");
                    ownerText.setId("owner");

                    promote.setDisable(true);
                    promote.setVisible(false);
                }
                else if (myGroup.getAdminsID().contains(memberID)) {
                    adminsText.setFont(Font.font("Arial", FontWeight.MEDIUM, 14 ));
                    adminsText.setAlignment(Pos.CENTER_LEFT);
                    adminsText.setText("Admin");
                    adminsText.setId("admin");

                    kick.setText("Kick");

                    if (myGroup.getOwnerID().equalsIgnoreCase(User.getUserID(User.getLoggedInUsername()))) {
                        promote.setOnAction(ae -> {
                            try {
                                Group.removeAdmin(User.getUserID(memberID), User.getUserID(User.getLoggedInUsername()), myGroup.getGroupName());
                                promote.setText("Set admin");
                                myGroup = Group.getGroup(myChat.getName());
                                toggleChange.set(!toggleChange.getValue());
                            }
                            catch (UserNotAdminException e) {
                                e.printStackTrace();
                            }
                            catch (GroupDoesNotExistException e) {
                                e.printStackTrace();
                            }
                            catch (UserDoesNotExistException e) {
                                e.printStackTrace();
                            }
                            catch (UserNotMemberException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    else {
                        promote.setDisable(true);
                    }

                    boolean found3 = false;
                    for (String s : myGroup.getAdminsID()) {
                        if (s.equalsIgnoreCase(User.getLoggedInUsername())) {
                            found3 = true;
                            break;
                        }
                    }

                    if (found3) {
                        kick.setOnAction(ae -> {
                            try {
                                Group.removeMember(User.getUserID(memberID), User.getUserID(User.getLoggedInUsername()), myGroup.getGroupName());
                                myGroup = Group.getGroup(myChat.getName());
                                toggleChange.set(!toggleChange.getValue());
                            }
                            catch (UserNotAdminException e) {
                                e.printStackTrace();
                            }
                            catch (GroupDoesNotExistException e) {
                                e.printStackTrace();
                            }
                            catch (UserDoesNotExistException e) {
                                e.printStackTrace();
                            }
                            catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }

                        });
                    }
                    else
                        kick.setDisable(true);

                    promote.setText("Remove admin");
                }
                else {
                    kick.setText("Kick");

                    if (myGroup.getOwnerID().equalsIgnoreCase(User.getUserID(User.getLoggedInUsername()))) {
                        promote.setOnAction(ae -> {
                            try {
                                Group.addAdmin(User.getUserID(memberID), User.getUserID(User.getLoggedInUsername()), myGroup.getGroupName());
                                promote.setText("Remove admin");
                                myGroup = Group.getGroup(myChat.getName());
                                toggleChange.set(!toggleChange.getValue());
                            }
                            catch (UserNotAdminException e) {
                                e.printStackTrace();
                            }
                            catch (GroupDoesNotExistException e) {
                                e.printStackTrace();
                            }
                            catch (UserDoesNotExistException e) {
                                e.printStackTrace();
                            }
                            catch (UserNotMemberException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    else {
                        promote.setDisable(true);
                    }

                    boolean found4 = false;
                    for (String s : myGroup.getAdminsID()) {
                        if (s.equalsIgnoreCase(User.getLoggedInUsername())) {
                            found4 = true;
                            break;
                        }
                    }

                    if (found4) {
                        kick.setOnAction(ae -> {
                            try {
                                Group.removeMember(User.getUserID(memberID), User.getUserID(User.getLoggedInUsername()), myGroup.getGroupName());
                                myGroup = Group.getGroup(myChat.getName());
                                toggleChange.set(!toggleChange.getValue());
                            }
                            catch (UserNotAdminException e) {
                                e.printStackTrace();
                            }
                            catch (GroupDoesNotExistException e) {
                                e.printStackTrace();
                            }
                            catch (UserDoesNotExistException e) {
                                e.printStackTrace();
                            }
                            catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }

                        });
                    }
                    else
                        kick.setDisable(true);

                    promote.setText("Set admin");
                }
            }
            catch (UserDoesNotExistException e) {
                e.printStackTrace();
            }


            membersVBox.getChildren().add(hBox);
            hBox.setLayoutY(yPos);

            yPos += myStage.getHeight() * 4 / 45;
        }

        membersVBox.setPrefHeight(myGroup.getMemberCount() * 4 / 45 * myStage.getHeight() + myStage.getHeight());
    }

    public void addMember() throws IOException {
        Stage newStage = new Stage();
        Scene newScene = new Scene(App.loadFXML("searchPageAddMember"));
        newStage.setScene(newScene);
        newStage.setWidth(300);
        newStage.setHeight(400);
        newStage.show();
    }
}
