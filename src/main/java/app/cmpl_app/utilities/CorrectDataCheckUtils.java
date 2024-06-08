package app.cmpl_app.utilities;

import app.cmpl_app.datas.MachineTableRow;
import app.cmpl_app.datas.Properties;
import app.cmpl_app.datas.encoding.SignalEncoding;
import app.cmpl_app.exceptions.IncorrectDataContent;
import app.cmpl_app.exceptions.IncorrectFormatException;
import app.cmpl_app.exceptions.NoDataException;
import app.cmpl_app.packages.DataPackage;
import app.cmpl_app.packages.TablePackage;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class CorrectDataCheckUtils {

    public static void checkMachineTable(TableView<MachineTableRow> machineTable,
                                         List<MachineTableRow> machineRows, Properties props)
            throws NoDataException, IncorrectFormatException, IncorrectDataContent{

        if (machineRows.isEmpty()) {
            throw new IncorrectDataContent("There are no stages in the \"A\" codes table!");
        }

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

                if (machineTable.getColumns().get(i).getText().equals("A1") && rowProps.get(i).equals("xxxx")) {
                    continue;
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

    public static void checkCodeTables(DataPackage data) throws IncorrectDataContent {
        boolean const0Contains = false;
        boolean const1Contains = false;

        for (SignalEncoding signal: data.xCode) {
            if (signal.getValue().equals("Const 0")) {
                const0Contains = true;
            }
            if (signal.getValue().equals("Const 1")) {
                const1Contains = true;
            }
        }

        if (!const0Contains) {
            throw new IncorrectDataContent("There is no logical condition \"Const 0\" in the \"X\" codes table!");
        }
        if (!const1Contains) {
            throw new IncorrectDataContent("There is no logical condition \"Const 1\" in the \"X\" codes table!");
        }

        boolean endPointContains = isEndPointContains(data);

        if (!endPointContains) {
            throw new IncorrectDataContent("There is no end micro-operation \"yk\" in the \"Y\" codes table!");
        }
    }

    private static boolean isEndPointContains(DataPackage data) throws IncorrectDataContent {
        boolean endPointContains = false;
        int tableNum = -1;

        for (List<SignalEncoding> yCodes: data.yCodes) {
            tableNum++;
            boolean zeroCommandContains = false;

            for (SignalEncoding code: yCodes) {
                if (code.getValue().equals("yk")) {
                    endPointContains = true;
                }
                if (code.getValue().equals("-")) {
                    zeroCommandContains = true;
                }
            }

            if (!zeroCommandContains) {
                throw new IncorrectDataContent("There is no empty micro-operation \"-\" in " + tableNum
                        + " column of the \"Y\" codes table!");
            }
        }
        return endPointContains;
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
}
