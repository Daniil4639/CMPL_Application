package app.cmpl_app.utilities;

import app.cmpl_app.datas.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

public class ColumnCreator {

    public static TableColumn<MachineTableRow, String> getListStringColumn(int iter) {
        TableColumn<MachineTableRow, String> yCol = new TableColumn<>("Y" + iter);
        yCol.setCellFactory(TextFieldTableCell.forTableColumn());
        yCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValues().get(iter - 1)));

        yCol.setOnEditCommit(event -> {
            MachineTableRow row = event.getRowValue();
            row.getValues().set(iter - 1, event.getNewValue());
        });

        return yCol;
    }

    public static TableColumn<Properties, Integer> getListIntegerColumn(TablePackage tables,
                                                                        DataPackage dataPackage,
                                                                        int iter) {

        TableColumn<Properties, Integer> yCol = new TableColumn<>("Y" + iter);
        yCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        yCol.setCellValueFactory(data -> new ReadOnlyObjectWrapper<Integer>(data.getValue()
                .getOperationsSizes().get(iter - 1)));

        yCol.setOnEditCommit(event -> {
            Properties row = event.getRowValue();

            if (event.getNewValue() > 0) {
                if (!Objects.equals(row.getOperationsSizes().get(iter - 1), event.getNewValue())) {
                    SlideUtils.fillCodeTable(tables, tables.yTables.get(iter - 1), dataPackage.yCodes.get(iter - 1),
                            event.getNewValue(), CodeTableMode.YMode);
                }

                row.getOperationsSizes().set(iter - 1, event.getNewValue());
            }
            tables.formatTable.getItems().set(0, row);
        });
        return yCol;
    }

    public static TableColumn<MachineTableRow, String> getMachineStringColumn(String name, String value) {
        TableColumn<MachineTableRow, String> col = new TableColumn<>(name);
        col.setCellFactory(TextFieldTableCell.forTableColumn());
        col.setCellValueFactory(new PropertyValueFactory<>(value));
        col.setOnEditCommit(event -> {
            MachineTableRow row = event.getRowValue();

            try {
                Field field = row.getClass().getDeclaredField(value);
                field.setAccessible(true);

                field.set(row, event.getNewValue());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        return col;
    }

    public static TableColumn<Properties, Integer> getFormatIntegerColumn(TablePackage tablePackage,
                                                                          String name, String value,
                                                                          TableView<SignalEncoding> codeTable,
                                                                          List<SignalEncoding> codes) {

        TableColumn<Properties, Integer> col = new TableColumn<>(name);
        col.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col.setCellValueFactory(new PropertyValueFactory<>(value));
        col.setOnEditCommit(event -> {
            Properties row = event.getRowValue();

            try {
                Field field = row.getClass().getDeclaredField(value);
                field.setAccessible(true);

                int val = (int) field.get(row);

                if (event.getNewValue() > 0) {
                    if (val != event.getNewValue()) {
                        SlideUtils.fillCodeTable(tablePackage, codeTable, codes,
                                event.getNewValue(), convertStringToMode(value));
                    }

                    field.set(row, event.getNewValue());
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            tablePackage.formatTable.getItems().set(0, row);
        });

        return col;
    }

    public static TableColumn<SignalEncoding, String> getSignalEncodingStringTableColumn() {
        TableColumn<SignalEncoding, String> valColumn = new TableColumn<>("Val");
        valColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        valColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        valColumn.setOnEditCommit(event -> {
            SignalEncoding signal = event.getRowValue();
            signal.setValue(event.getNewValue());
        });
        return valColumn;
    }

    private static CodeTableMode convertStringToMode(String value) {
        switch (value) {
            case "logicSize" -> {
                return CodeTableMode.XMode;
            }
            case "addressSize" -> {
                return CodeTableMode.AMode;
            }
        }

        return CodeTableMode.Unknown;
    }
}
