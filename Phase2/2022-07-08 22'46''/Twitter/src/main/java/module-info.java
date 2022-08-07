module org.twitter {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.twitter to javafx.fxml;
    exports org.twitter;
}