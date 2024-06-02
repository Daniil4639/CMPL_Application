package app.cmpl_app.utilities;

import app.cmpl_app.datas.*;
import app.cmpl_app.exceptions.IncorrectFormatException;
import app.cmpl_app.exceptions.NoDataException;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class SlideUtils {

    public static void fillMachineTable(TablePackage tables,
                                        DataPackage data) {

        tables.machineTable.getItems().clear();
        tables.machineTable.getColumns().clear();

        // проверка и корректировка массива микроопераций
        int stagesCount = getStages(data.aCode);
        while (data.machineRows.size() > stagesCount) {
            data.machineRows.removeLast();
        }

        for (int rowIndex = 0; rowIndex < data.machineRows.size(); rowIndex++) {
            while (data.machineRows.get(rowIndex).getValues().size() > data.props.getOperationsCount()) {
                data.machineRows.get(rowIndex).getValues().removeLast();
            }

            while (data.machineRows.get(rowIndex).getValues().size() < data.props.getOperationsCount()) {
                data.machineRows.get(rowIndex).getValues().add("");
            }
        }

        for (int extraRows = data.machineRows.size(); extraRows < stagesCount; extraRows++) {
            data.machineRows.add(MachineTableRow.getDefaultRow(data.props.getOperationsCount()));
        }

        // инициализация столбца A
        TableColumn<MachineTableRow, String> aCol = ColumnCreator.getMachineStringColumn("A", "stage");
        tables.machineTable.getColumns().add(aCol);

        // инициализация столбцов Y
        for (int iter = 1; iter <= data.props.getOperationsCount(); iter++) {

            TableColumn<MachineTableRow, String> yCol = ColumnCreator.getListStringColumn(iter);
            tables.machineTable.getColumns().add(yCol);
        }

        // инициализация столбца X
        TableColumn<MachineTableRow, String> xCol = ColumnCreator.getMachineStringColumn("X", "logic");
        tables.machineTable.getColumns().add(xCol);

        // инициализация столбца i
        TableColumn<MachineTableRow, String> iCol = ColumnCreator.getMachineStringColumn("i", "i");
        tables.machineTable.getColumns().add(iCol);

        // инициализация столбца A1
        TableColumn<MachineTableRow, String> a1Col = ColumnCreator.getMachineStringColumn("A1", "address");
        tables.machineTable.getColumns().add(a1Col);

        for (TableColumn<MachineTableRow, ?> column : tables.machineTable.getColumns()) {
            column.setPrefWidth(90);
            column.setResizable(false);
        }

        tables.machineTable.getItems().addAll(data.machineRows);

        tables.machineTable.setFixedCellSize(25);
        tables.machineTable.setPrefWidth(90 * tables.machineTable.getColumns().size() + 2);
        tables.machineTable.prefHeightProperty().bind(Bindings.size(tables.machineTable.getItems())
                .multiply(tables.machineTable.getFixedCellSize()).add(35));
    }

    public static void fillFormatTable(TablePackage tables,
                                       DataPackage data) {

        tables.formatTable.getColumns().clear();
        tables.formatTable.getItems().clear();

        // инициализация столбца A
        TableColumn<Properties, Integer> aCol = ColumnCreator.getFormatIntegerColumn(tables,
                "A", "addressSize", tables.aTable, data.aCode);
        tables.formatTable.getColumns().add(aCol);

        // инициализация столбцов Y
        for (int iter = 1; iter <= data.props.getOperationsCount(); iter++) {

            TableColumn<Properties, Integer> yCol = ColumnCreator.getListIntegerColumn(tables, data, iter);
            tables.formatTable.getColumns().add(yCol);
        }

        // инициализация столбца X
        TableColumn<Properties, Integer> xCol = ColumnCreator.getFormatIntegerColumn(tables,
                "X", "logicSize", tables.xTable, data.xCode);
        tables.formatTable.getColumns().add(xCol);

        // инициализация столбца i
        TableColumn<Properties, String> iCol = new TableColumn<>("i");
        iCol.setCellValueFactory(cb -> new SimpleStringProperty("1"));
        tables.formatTable.getColumns().add(iCol);

        // инициализация столбца A1
        TableColumn<Properties, Integer> a1Col = ColumnCreator.getFormatIntegerColumn(tables,
                "A1", "addressSize", tables.aTable, data.aCode);
        tables.formatTable.getColumns().add(a1Col);

        for (TableColumn<Properties, ?> tableColumn : tables.formatTable.getColumns()) {
            tableColumn.setPrefWidth(90);
            tableColumn.setResizable(false);
        }

        tables.formatTable.getItems().add(data.props);
        tables.formatTable.setFixedCellSize(25);
        tables.formatTable.setPrefWidth(tables.formatTable.getColumns().size() * 90 + 2);
        tables.formatTable.prefHeightProperty().bind(Bindings.size(tables.formatTable.getItems())
                .multiply(tables.formatTable.getFixedCellSize()).add(35));
    }

    public static void fillCodeTable(TablePackage tablePackage, TableView<SignalEncoding> table,
                                     List<SignalEncoding> codes, int bit, CodeTableMode mode) {

        table.getItems().clear();
        table.getColumns().clear();

        SignalEncoding.getEncodingByBit(codes, bit);

        switch (mode) {
            case XMode -> {
                codes.getFirst().setValue("Const 1");
                codes.getLast().setValue("Const 0");
            }
            case YMode -> {
                codes.getFirst().setValue("-");
            }
        }

        // инициализация столбца code
        TableColumn<SignalEncoding, String> codeColumn = new TableColumn<>("Code");
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        table.getColumns().add(codeColumn);

        // инициализация столбца val
        TableColumn<SignalEncoding, String> valColumn = ColumnCreator.getSignalEncodingStringTableColumn();
        table.getColumns().add(valColumn);

        for (TableColumn<SignalEncoding, ?> column: table.getColumns()) {
            column.setPrefWidth(90);
            column.setResizable(false);
        }

        table.getItems().addAll(codes);
        table.setFixedCellSize(25);
        table.prefHeightProperty().bind(Bindings.size(table.getItems())
                .multiply(table.getFixedCellSize()).add(35));

        if (mode.equals(CodeTableMode.YMode)) {
        resizeYBox(tablePackage);
        }
    }

    public static void checkMachineTable(TableView<MachineTableRow> machineTable,
                                         List<MachineTableRow> machineRows, Properties props)
            throws NoDataException, IncorrectFormatException {

        List<Integer> listOfProps = makeListOfProps(props);

        int rowNum = 0;
        for (MachineTableRow row: machineRows) {
            rowNum++;

            List<String> rowProps = makeListOfFields(row);

            for (int i = 0; i < rowProps.size(); i++) {
                if (rowProps.get(i).isEmpty()) {
                    throw new NoDataException("No data in " + machineTable.getColumns().get(i).getText() + " column, "
                            + rowNum + " row!");
                }

                boolean formatOk = true;
                for (char symbol: rowProps.get(i).toCharArray()) {
                    if (symbol != '0' && symbol != '1') {
                        formatOk = false;
                        break;
                    }
                }

                if (!formatOk || rowProps.get(i).length() != listOfProps.get(i)) {
                    throw new IncorrectFormatException("Incorrect data format in "
                            + machineTable.getColumns().get(i).getText() + " column, " + rowNum + " row!");
                }
            }
        }
    }

    private static List<String> makeListOfFields(MachineTableRow row) {
        List<String> res = new ArrayList<>();

        res.add(row.getStage());
        res.addAll(row.getValues());
        res.addAll(List.of(row.getLogic(), row.getI(), row.getAddress()));

        return res;
    }

    private static List<Integer> makeListOfProps(Properties props) {
        List<Integer> res = new ArrayList<>();

        res.add(props.getAddressSize());
        res.addAll(props.getOperationsSizes());
        res.addAll(List.of(props.getLogicSize(), 1, props.getAddressSize()));

        return res;
    }

    private static void resizeYBox(TablePackage tablePackage) {
        double maxHeight = 0;
        for (int i = 0; i < tablePackage.yTables.size(); i++) {
            if (maxHeight < tablePackage.yTables.get(i).getPrefHeight()) {
                maxHeight = tablePackage.yTables.get(i).getPrefHeight() + 1;
            }
        }

        tablePackage.yBox.setPrefHeight(maxHeight);
    }

    public static int getStages(List<SignalEncoding> aCodes) {
        int res = 0;
        for (SignalEncoding aCode : aCodes) {
            if (!aCode.getValue().isEmpty()) {
                res++;
            }
        }

        return res;
    }
}