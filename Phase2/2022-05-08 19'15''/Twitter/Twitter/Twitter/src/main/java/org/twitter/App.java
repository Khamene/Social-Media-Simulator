package org.twitter;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.twitter.Functionality.SQLManager;

import java.io.IOException;

public class App extends Application {
    private static Scene scene;

    static SimpleIntegerProperty theme = new SimpleIntegerProperty();

    @Override
    public void start(Stage stage) throws IOException {
        SQLManager.initialize();

        theme.set(0);
        scene = new Scene(loadFXML("setting"));
        stage.setScene(scene);
        stage.setMinHeight(450);
        stage.setMinWidth(650);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    static Scene getScene() {
        return scene;
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
