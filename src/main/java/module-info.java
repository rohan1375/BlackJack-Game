module com.example.myplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.example.myplayer to javafx.fxml;
    exports com.example.myplayer;
}