package app.cmpl_app.packages;

import app.cmpl_app.datas.MachineTableRow;
import app.cmpl_app.datas.Properties;
import app.cmpl_app.datas.encoding.LogicSignalEncoding;
import app.cmpl_app.datas.ResultTableRow;
import app.cmpl_app.datas.encoding.SignalEncoding;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class DataPackage {

    public Properties props;
    public List<MachineTableRow> machineRows;

    public List<SignalEncoding> aCode;
    public List<SignalEncoding> xCode;
    public List<List<SignalEncoding>> yCodes;

    public List<LogicSignalEncoding> logicEncoding;
    public Pair<ResultTableRow, ResultTableRow> results;

    public DataPackage() {
        aCode = new ArrayList<>();
        xCode = new ArrayList<>();
        yCodes = new ArrayList<>();
        logicEncoding = new ArrayList<>();

        props = Properties.getDefaultProps();
        machineRows = new ArrayList<>();

        results = new Pair<>(new ResultTableRow("A"), new ResultTableRow("Y"));
    }
}
