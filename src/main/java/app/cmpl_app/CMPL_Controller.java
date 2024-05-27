package app.cmpl_app;

import app.cmpl_app.datas.MachineTableRow;
import app.cmpl_app.datas.Properties;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CMPL_Controller implements Initializable {

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

    private Properties props;
    private List<TableColumn<MachineTableRow, String>> columns;

    @FXML
    private TableView<MachineTableRow> machineTable;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        props = Properties.getDefaultProps();
        columns = new ArrayList<>();
        machineTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        machineTable.setEditable(true);

        fillMachineTable();
    }

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

    private void fillMachineTable() {
        machineTable.getItems().clear();
        machineTable.getColumns().clear();

        // инициализация столбца A
        TableColumn<MachineTableRow, String> aCol = new TableColumn<>("A");
        aCol.setCellValueFactory(new PropertyValueFactory<>("stage"));
        aCol.setOnEditCommit(event -> {
            MachineTableRow row = event.getRowValue();
            row.setStage(event.getNewValue());
        });
        columns.add(aCol);

        // инициализация столбцов Y
        for (int iter = 1; iter <= props.operationsCount; iter++) {

            TableColumn<MachineTableRow, String> yCol = new TableColumn<>("Y" + iter);
            int finalIter = iter - 1;
            yCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValues().get(finalIter)));

            yCol.setOnEditCommit(event -> {
                MachineTableRow row = event.getRowValue();
                row.getValues().set(finalIter, event.getNewValue());
            });

            columns.add(yCol);
        }

        // инициализация столбца X
        TableColumn<MachineTableRow, String> xCol = new TableColumn<>("X");
        xCol.setCellValueFactory(new PropertyValueFactory<>("logic"));
        xCol.setOnEditCommit(event -> {
            MachineTableRow row = event.getRowValue();
            row.setLogic(event.getNewValue());
        });
        columns.add(xCol);

        // инициализация столбца i
        TableColumn<MachineTableRow, String> iCol = new TableColumn<>("i");
        iCol.setCellValueFactory(new PropertyValueFactory<>("i"));
        iCol.setOnEditCommit(event -> {
            MachineTableRow row = event.getRowValue();
            row.setI(event.getNewValue());
        });
        columns.add(iCol);

        // инициализация столбца A1
        TableColumn<MachineTableRow, String> a1Col = new TableColumn<>("A1");
        a1Col.setCellValueFactory(new PropertyValueFactory<>("address"));
        a1Col.setOnEditCommit(event -> {
            MachineTableRow row = event.getRowValue();
            row.setAddress(event.getNewValue());
        });
        columns.add(a1Col);

        machineTable.setPrefWidth(75 * columns.size());
        machineTable.getColumns().addAll(columns);
    }
}