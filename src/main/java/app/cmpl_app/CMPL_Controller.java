package app.cmpl_app;

import app.cmpl_app.datas.*;
import app.cmpl_app.datas.Properties;
import app.cmpl_app.datas.encoding.LogicSignalEncoding;
import app.cmpl_app.datas.encoding.SignalEncoding;
import app.cmpl_app.exceptions.IncorrectDataContent;
import app.cmpl_app.exceptions.IncorrectFormatException;
import app.cmpl_app.exceptions.NoDataException;
import app.cmpl_app.exceptions.UnknownCodeException;
import app.cmpl_app.packages.DataPackage;
import app.cmpl_app.packages.TablePackage;
import app.cmpl_app.utilities.CorrectDataCheckUtils;
import app.cmpl_app.utilities.MachineCalcUtils;
import app.cmpl_app.utilities.SlideUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Pair;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.util.*;

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

    private final FileChooser fileChooser = new FileChooser();

    private DataPackage data;
    private TablePackage tables;

    @FXML
    private AnchorPane generalPanel;

    //------Slide_1--------

    @FXML
    private TableView<MachineTableRow> machineTable;

    //------Slide_1--------

    //------Slide_2--------

    @FXML
    private TableView<Properties> formatTable;
    @FXML
    private TableView<SignalEncoding> aTable;
    @FXML
    private TableView<SignalEncoding> xTable;
    @FXML
    private HBox yBox;

    //------Slide_2--------

    //------Slide_3--------

    @FXML
    private TableView<ResultTableRow> modelingResultsTable;
    @FXML
    private TableView<LogicSignalEncoding> logicCyclesTable;

    @FXML
    private Spinner<Integer> simulationNumericField;
    @FXML
    private TextField entryStageField;

    //------Slide_3--------

    //------System--------

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
    private Pane saveProjectButton;

    //------System--------

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {;
        simulationNumericField.setEditable(false);
        simulationNumericField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        simulationNumericField.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            SlideUtils.fillLogicCycleTable(tables.logicCyclesTable, data, simulationNumericField.getValue());
            SlideUtils.fillResultsTable(tables.modelingResultsTable, data.results, simulationNumericField.getValue());
        });

        yBox.setFillHeight(false);
        data = new DataPackage();

        tablePackage();
        tables.yTables = new ArrayList<>();

        machineTable.setEditable(true);
        formatTable.setEditable(true);
        aTable.setEditable(true);
        xTable.setEditable(true);
        logicCyclesTable.setEditable(true);
        modelingResultsTable.setEditable(false);

        addFirstYTable();

        SlideUtils.fillFormatTable(tables, data);
        SlideUtils.fillCodeTable(tables, data.logicEncoding, aTable, data.aCode, data.props.getAddressSize(), CodeTableMode.AMode);
        SlideUtils.fillCodeTable(tables, data.logicEncoding, xTable, data.xCode, data.props.getLogicSize(), CodeTableMode.XMode);
        SlideUtils.fillMachineTable(tables, data);
        SlideUtils.fillLogicCycleTable(tables.logicCyclesTable, data, 0);
        SlideUtils.fillResultsTable(tables.modelingResultsTable, data.results, 0);
    }

    @FXML
    void switchMode1(MouseEvent event) {
        recolorButton1();

        contentPanel3.toBack();
        contentPanel1.toFront();
        borderPanel.toFront();
        upperPanel.toFront();

        SlideUtils.fillMachineTable(tables, data);
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
        try {
            CorrectDataCheckUtils.checkMachineTable(machineTable, data.machineRows, data.props);
            CorrectDataCheckUtils.checkCodeTables(data);

            recolorButton3();

            contentPanel3.toFront();
            borderPanel.toFront();
            upperPanel.toFront();

            SlideUtils.fillLogicCycleTable(tables.logicCyclesTable, data, simulationNumericField.getValue());
        } catch (NoDataException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("NoDataException");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();

        } catch (IncorrectFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("IncorrectFormatException");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();

        } catch (IncorrectDataContent ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("IncorrectDataContent");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
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
    void saveProjectButtonEntered(MouseEvent event) {
        saveProjectButton.setStyle(upperButtonStyleActive);
        saveProjectButton.setEffect(new DropShadow());
    }

    @FXML
    void saveProjectButtonExited(MouseEvent event) {
        saveProjectButton.setStyle(upperButtonStyleInactive);
        saveProjectButton.setEffect(null);
    }

    @FXML
    void formatMinusClicked(MouseEvent event) {
        if (data.props.getOperationsCount() > 0) {
            data.props.getOperationsSizes().remove(data.props.getOperationsCount() - 1);
            data.props.setOperationsCount(data.props.getOperationsCount() - 1);

            tables.yTables.remove(data.props.getOperationsCount());
            yBox.getChildren().removeLast();
            yBox.getChildren().removeLast();
            data.yCodes.remove(data.props.getOperationsCount());

            yBox.setPrefWidth(95 * yBox.getChildren().size());
        }

        SlideUtils.fillFormatTable(tables, data);
    }

    @FXML
    void formatPlusClicked(MouseEvent event) {
        data.props.setOperationsCount(data.props.getOperationsCount() + 1);
        data.props.getOperationsSizes().add(1);

        TableView<SignalEncoding> newYTable = new TableView<>();
        newYTable.setPrefWidth(180);
        newYTable.setEditable(true);
        tables.yTables.add(newYTable);
        yBox.getChildren().add(newYTable);

        Pane isolator = new Pane();
        isolator.setPrefWidth(10);
        yBox.getChildren().add(isolator);

        data.yCodes.add(new ArrayList<>());

        yBox.setPrefWidth(95 * yBox.getChildren().size());

        SlideUtils.fillCodeTable(tables, data.logicEncoding, newYTable, data.yCodes.getLast(),
                data.props.getOperationsSizes().getLast(), CodeTableMode.YMode);

        newYTable.getColumns().get(1).setText("Y" + data.props.getOperationsCount());

        SlideUtils.fillFormatTable(tables, data);
    }

    @FXML
    void runButtonClicked(MouseEvent event) {
        try {
            while (MachineCalcUtils.makeStep(tables, data, entryStageField.getText(), simulationNumericField.getValue())) {}
        } catch (UnknownCodeException ex) {
            if (!ex.getMessage().equals("With the receipt of the micro command \"yk\" at the last stage, "
                    + "the program completed its execution!")) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("UnknownCodeException");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    void clearButtonClicked(MouseEvent event) {
        ResultTableRow.clear(data.results.getKey());
        ResultTableRow.clear(data.results.getValue());

        SlideUtils.fillResultsTable(tables.modelingResultsTable, data.results, simulationNumericField.getValue());
    }

    @FXML
    void saveResultButtonClicked(MouseEvent event) {
        fileChooser.setTitle("Save results");
        fileChooser.setInitialFileName("My_results");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text file", "*.txt"));

        try {
            File file = fileChooser.showSaveDialog(generalPanel.getScene().getWindow());
            fileChooser.setInitialDirectory(file.getParentFile());

            Pair<String, String> resultStrings = getResultStrings();

            PrintWriter printWriter = new PrintWriter(file);
            printWriter.println(resultStrings.getKey());
            printWriter.println("=".repeat(resultStrings.getKey().length()));
            printWriter.println(resultStrings.getValue());
            printWriter.close();
        } catch (Exception ignored) {}
    }

    @FXML
    void stepButtonClicked(MouseEvent event) {
        try {
            MachineCalcUtils.makeStep(tables, data, entryStageField.getText(), simulationNumericField.getValue());
        } catch (UnknownCodeException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("UnknownCodeException");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void newProjectButtonClicked(MouseEvent event) {
        switchMode1(null);

        while (yBox.getChildren().size() > 1) {
            yBox.getChildren().removeLast();
            yBox.getChildren().removeLast();
        }

        data = new DataPackage();

        simulationNumericField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        entryStageField.setText("");

        tables.yTables.clear();

        addFirstYTable();

        SlideUtils.fillFormatTable(tables, data);
        SlideUtils.fillCodeTable(tables, data.logicEncoding, aTable, data.aCode, data.props.getAddressSize(), CodeTableMode.AMode);
        SlideUtils.fillCodeTable(tables, data.logicEncoding, xTable, data.xCode, data.props.getLogicSize(), CodeTableMode.XMode);
        SlideUtils.fillMachineTable(tables, data);
        SlideUtils.fillLogicCycleTable(tables.logicCyclesTable, data, 0);
        SlideUtils.fillResultsTable(tables.modelingResultsTable, data.results, 0);
    }

    @FXML
    void openProjectButtonClicked(MouseEvent event) {

    }

    @FXML
    void saveProjectButtonClicked(MouseEvent event) {

    }

    private void addFirstYTable() {
        TableView<SignalEncoding> newYTable = new TableView<>();
        newYTable.setPrefWidth(180);
        newYTable.setEditable(true);
        tables.yTables.add(newYTable);
        tables.yBox.getChildren().add(newYTable);

        Pane isolator = new Pane();
        isolator.setPrefWidth(10);
        yBox.getChildren().add(isolator);

        data.yCodes.add(new ArrayList<>());

        SlideUtils.fillCodeTable(tables, data.logicEncoding, newYTable, data.yCodes.getLast(),
                data.props.getOperationsSizes().getLast(), CodeTableMode.YMode);

        newYTable.getColumns().get(1).setText("Y" + data.props.getOperationsCount());
    }

    private Pair<String, String> getResultStrings() {
        int maxLen = 0;
        for (String str: tables.modelingResultsTable.getItems().getFirst().getResults()) {
            if (str.length() > maxLen) {
                maxLen = str.length();
            }
        }
        for (String str: tables.modelingResultsTable.getItems().getLast().getResults()) {
            if (str.length() > maxLen) {
                maxLen = str.length();
            }
        }

        maxLen += 4;
        StringBuilder resStage = new StringBuilder("||");
        StringBuilder resY = new StringBuilder("||");

        for (int i = 0; i < tables.modelingResultsTable.getItems().getFirst().getResults().size(); i++) {
            String str1 = tables.modelingResultsTable.getItems().getFirst().getResults().get(i);
            String str2 = tables.modelingResultsTable.getItems().getLast().getResults().get(i);

            if (str1.isEmpty()) {
                continue;
            }

            int currLen1 = maxLen - str1.length();
            resStage.append(" ".repeat(currLen1 / 2)).append(str1);
            currLen1 -= currLen1 / 2;
            resStage.append(" ".repeat(currLen1)).append("||");

            int currLen2 = maxLen - str2.length();
            resY.append(" ".repeat(currLen2 / 2)).append(str2);
            currLen2 -= currLen2 / 2;
            resY.append(" ".repeat(currLen2)).append("||");
        }

        return new Pair<>(resStage.toString(), resY.toString());
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

    private void tablePackage() {
        tables = new TablePackage(machineTable, formatTable, aTable, xTable, yBox, null, modelingResultsTable,
                logicCyclesTable);
    }
}