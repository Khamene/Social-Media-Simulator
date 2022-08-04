module org.twitter {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.twitter to javafx.fxml;
    exports org.twitter;
}