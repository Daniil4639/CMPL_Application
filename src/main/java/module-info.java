module app.uapl_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens app.cmpl_app to javafx.fxml;
    exports app.cmpl_app;
}