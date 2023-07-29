module com.nam.lifegame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.nam.lifegame to javafx.fxml;
    exports com.nam.lifegame;
    exports com.nam.lifegame.model;
}