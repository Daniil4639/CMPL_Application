package app.cmpl_app.packages;

import app.cmpl_app.datas.ResultTableRow;
import app.cmpl_app.datas.encoding.LogicSignalEncoding;
import app.cmpl_app.datas.MachineTableRow;
import app.cmpl_app.datas.Properties;
import app.cmpl_app.datas.encoding.SignalEncoding;
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

    public TableView<ResultTableRow> modelingResultsTable;

    public TableView<LogicSignalEncoding> logicCyclesTable;
}
