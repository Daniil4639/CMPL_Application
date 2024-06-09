package app.cmpl_app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class CMPL_Application extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CMPL_Application.class.getResource("cmpl_form.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1000, 660);
        scene.getStylesheets().add(CMPL_Application.class.getResource("style.css").toExternalForm());

        stage.setTitle("CMPL");
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/emblem.png")));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}