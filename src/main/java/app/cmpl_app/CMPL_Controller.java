package app.cmpl_app;

import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Objects;

public class CMPL_Controller {

    private final String borderButtonStyleActive = "-fx-background-color: #fc625d; -fx-border-color: #000000;";
    private final String borderButtonStyleInactive = "-fx-background-color: #505050; -fx-border-color: #000000;";

    private final String upperButtonStyleActive = "-fx-background-color: #666666; -fx-background-radius: 3px;";
    private final String upperButtonStyleInactive = "-fx-background-color: #272727; -fx-background-radius: 3px;";

    private final Image mode1White = findImage("/icons/table.png");
    private final Image mode1Black = findImage("/icons/table_1.png");
    private final Image mode2White = findImage("/icons/engine.png");
    private final Image mode2Black = findImage("/icons/engine_1.png");
    private final Image mode3White = findImage("/icons/modeling.png");
    private final Image mode3Black = findImage("/icons/modeling_1.png");

    @FXML
    private AnchorPane upperPanel;
    @FXML
    private VBox borderPanel;

    @FXML
    private Pane modeButton1;
    @FXML
    private Pane modeButton2;
    @FXML
    private Pane modeButton3;

    @FXML
    private ImageView modeImage1;
    @FXML
    private ImageView modeImage2;
    @FXML
    private ImageView modeImage3;

    @FXML
    private Text modeName1;
    @FXML
    private Text modeName2;
    @FXML
    private Text modeName3;

    @FXML
    private Pane contentPanel1;
    @FXML
    private Pane contentPanel2;
    @FXML
    private Pane contentPanel3;

    @FXML
    private Pane newProjectButton;
    @FXML
    private Pane openProjectButton;
    @FXML
    private Pane instructionButton;

    @FXML
    void switchMode1(MouseEvent event) {
        recolorButton1();

        contentPanel3.toBack();
        contentPanel1.toFront();
        borderPanel.toFront();
        upperPanel.toFront();
    }

    @FXML
    void switchMode2(MouseEvent event) {
        recolorButton2();

        contentPanel3.toBack();
        contentPanel2.toFront();
        borderPanel.toFront();
        upperPanel.toFront();
    }

    @FXML
    void switchMode3(MouseEvent event) {
        recolorButton3();

        contentPanel3.toFront();
        borderPanel.toFront();
        upperPanel.toFront();
    }

    @FXML
    void newProjectButtonEntered(MouseEvent event) {
        newProjectButton.setStyle(upperButtonStyleActive);
        newProjectButton.setEffect(new DropShadow());
    }

    @FXML
    void newProjectButtonExited(MouseEvent event) {
        newProjectButton.setStyle(upperButtonStyleInactive);
        newProjectButton.setEffect(null);
    }

    @FXML
    void openProjectButtonEntered(MouseEvent event) {
        openProjectButton.setStyle(upperButtonStyleActive);
        openProjectButton.setEffect(new DropShadow());
    }

    @FXML
    void openProjectButtonExited(MouseEvent event) {
        openProjectButton.setStyle(upperButtonStyleInactive);
        openProjectButton.setEffect(null);
    }

    @FXML
    void instructionButtonEntered(MouseEvent event) {
        instructionButton.setStyle(upperButtonStyleActive);
        instructionButton.setEffect(new DropShadow());
    }

    @FXML
    void instructionButtonExited(MouseEvent event) {
        instructionButton.setStyle(upperButtonStyleInactive);
        instructionButton.setEffect(null);
    }

    private void recolorButton1() {
        modeButton1.setStyle(borderButtonStyleActive);
        modeButton2.setStyle(borderButtonStyleInactive);
        modeButton3.setStyle(borderButtonStyleInactive);

        modeName1.setStyle("-fx-fill: #000000;");
        modeName2.setStyle("-fx-fill: #FFFFFF;");
        modeName3.setStyle("-fx-fill: #FFFFFF;");

        modeImage1.setImage(mode1Black);
        modeImage2.setImage(mode2White);
        modeImage3.setImage(mode3White);
    }

    private void recolorButton2() {
        modeButton1.setStyle(borderButtonStyleInactive);
        modeButton2.setStyle(borderButtonStyleActive);
        modeButton3.setStyle(borderButtonStyleInactive);

        modeName1.setStyle("-fx-fill: #FFFFFF;");
        modeName2.setStyle("-fx-fill: #000000;");
        modeName3.setStyle("-fx-fill: #FFFFFF;");

        modeImage1.setImage(mode1White);
        modeImage2.setImage(mode2Black);
        modeImage3.setImage(mode3White);
    }

    private void recolorButton3() {
        modeButton1.setStyle(borderButtonStyleInactive);
        modeButton2.setStyle(borderButtonStyleInactive);
        modeButton3.setStyle(borderButtonStyleActive);

        modeName1.setStyle("-fx-fill: #FFFFFF;");
        modeName2.setStyle("-fx-fill: #FFFFFF;");
        modeName3.setStyle("-fx-fill: #000000;");

        modeImage1.setImage(mode1White);
        modeImage2.setImage(mode2White);
        modeImage3.setImage(mode3Black);
    }

    private Image findImage(String url) {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream(url)));
    }
}