package app.cmpl_app.datas;

import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TablePackage {

    public TableView<MachineTableRow> machineTable;

    public TableView<Properties> formatTable;

    public TableView<SignalEncoding> aTable;

    public TableView<SignalEncoding> xTable;

    public HBox yBox;

    public List<TableView<SignalEncoding>> yTables;
}
