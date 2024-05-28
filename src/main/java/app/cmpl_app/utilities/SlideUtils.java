package app.cmpl_app.utilities;

import app.cmpl_app.datas.MachineTableRow;
import app.cmpl_app.datas.Properties;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.util.List;

public class SlideUtils {

    public static void fillMachineTable(TableView<MachineTableRow> machineTable,
                                        List<TableColumn<MachineTableRow, String>> columns,
                                        List<MachineTableRow> rows,
                                        Properties props) {

        machineTable.getItems().clear();
        machineTable.getColumns().clear();

        // инициализация столбца A
        TableColumn<MachineTableRow, String> aCol = new TableColumn<>("A");
        aCol.setCellFactory(TextFieldTableCell.forTableColumn());
        aCol.setCellValueFactory(new PropertyValueFactory<>("stage"));
        aCol.setOnEditCommit(event -> {
            MachineTableRow row = event.getRowValue();
            row.setStage(event.getNewValue());
        });
        columns.add(aCol);

        // инициализация столбцов Y
        for (int iter = 1; iter <= props.getOperationsCount(); iter++) {

            TableColumn<MachineTableRow, String> yCol = new TableColumn<>("Y" + iter);
            yCol.setCellFactory(TextFieldTableCell.forTableColumn());
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
        xCol.setCellFactory(TextFieldTableCell.forTableColumn());
        xCol.setCellValueFactory(new PropertyValueFactory<>("logic"));
        xCol.setOnEditCommit(event -> {
            MachineTableRow row = event.getRowValue();
            row.setLogic(event.getNewValue());
        });
        columns.add(xCol);

        // инициализация столбца i
        TableColumn<MachineTableRow, String> iCol = new TableColumn<>("i");
        iCol.setCellFactory(TextFieldTableCell.forTableColumn());
        iCol.setCellValueFactory(new PropertyValueFactory<>("i"));
        iCol.setOnEditCommit(event -> {
            MachineTableRow row = event.getRowValue();
            row.setI(event.getNewValue());
        });
        columns.add(iCol);

        // инициализация столбца A1
        TableColumn<MachineTableRow, String> a1Col = new TableColumn<>("A1");
        a1Col.setCellFactory(TextFieldTableCell.forTableColumn());
        a1Col.setCellValueFactory(new PropertyValueFactory<>("address"));
        a1Col.setOnEditCommit(event -> {
            MachineTableRow row = event.getRowValue();
            row.setAddress(event.getNewValue());
        });
        columns.add(a1Col);

        for (TableColumn<MachineTableRow, String> column : columns) {
            column.setPrefWidth(90);
            column.setResizable(false);
        }

        machineTable.getColumns().addAll(columns);

        machineTable.setPrefWidth(Math.min(790, 90 * columns.size() + 2));
        machineTable.setPrefHeight(Math.min(480, 50 + 26 * rows.size()));
    }

    public static void fillFormatTable(TableView<Properties> formatTable,
                                       Properties props) {

        formatTable.getColumns().clear();
        formatTable.getItems().clear();

        // инициализация столбца A
        TableColumn<Properties, Integer> aCol = new TableColumn<>("A");
        aCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        aCol.setCellValueFactory(new PropertyValueFactory<>("addressSize"));
        aCol.setOnEditCommit(event -> {
            Properties row = event.getRowValue();
            row.setAddressSize(event.getNewValue());

            formatTable.getItems().set(0, row);
        });
        formatTable.getColumns().add(aCol);

        // инициализация столбцов Y
        for (int iter = 1; iter <= props.getOperationsCount(); iter++) {

            TableColumn<Properties, Integer> yCol = getYFormatColumn(iter);

            formatTable.getColumns().add(yCol);
        }

        // инициализация столбца X
        TableColumn<Properties, Integer> xCol = new TableColumn<>("X");
        xCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        xCol.setCellValueFactory(new PropertyValueFactory<>("logicSize"));
        xCol.setOnEditCommit(event -> {
            Properties row = event.getRowValue();
            row.setLogicSize(event.getNewValue());
        });
        formatTable.getColumns().add(xCol);

        // инициализация столбца i
        TableColumn<Properties, String> iCol = new TableColumn<>("i");
        iCol.setCellValueFactory(cb -> new SimpleStringProperty("1"));
        formatTable.getColumns().add(iCol);

        // инициализация столбца A1
        TableColumn<Properties, Integer> a1Col = new TableColumn<>("A1");
        a1Col.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        a1Col.setCellValueFactory(new PropertyValueFactory<>("addressSize"));
        a1Col.setOnEditCommit(event -> {
            Properties row = event.getRowValue();
            row.setAddressSize(event.getNewValue());

            formatTable.getItems().set(0, row);
        });
        formatTable.getColumns().add(a1Col);

        for (TableColumn<Properties, ?> tableColumn : formatTable.getColumns()) {
            tableColumn.setPrefWidth(90);
            tableColumn.setResizable(false);
        }

        formatTable.getItems().add(props);
        formatTable.setPrefWidth(formatTable.getColumns().size() * 90 + 2);
        formatTable.setPrefHeight(73);
    }

    private static TableColumn<Properties, Integer> getYFormatColumn(int iter) {
        TableColumn<Properties, Integer> yCol = new TableColumn<>("Y" + iter);
        yCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        int finalIter = iter - 1;
        yCol.setCellValueFactory(data -> new ReadOnlyObjectWrapper<Integer>(data.getValue()
                .getOperationsSizes().get(finalIter)));

        yCol.setOnEditCommit(event -> {
            Properties row = event.getRowValue();
            row.getOperationsSizes().set(finalIter, event.getNewValue());
        });
        return yCol;
    }
}