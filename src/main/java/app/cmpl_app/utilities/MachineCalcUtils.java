package app.cmpl_app.utilities;

import app.cmpl_app.datas.MachineTableRow;
import app.cmpl_app.datas.ResultTableRow;
import app.cmpl_app.datas.encoding.LogicSignalEncoding;
import app.cmpl_app.datas.encoding.SignalEncoding;
import app.cmpl_app.exceptions.UnknownCodeException;
import app.cmpl_app.packages.DataPackage;
import app.cmpl_app.packages.TablePackage;
import javafx.scene.control.TableView;

import java.util.List;

public class MachineCalcUtils {

    public static boolean makeStep(TablePackage tables, DataPackage data, String startStage, int cycles) throws UnknownCodeException {
        int placeIndex = getResultPlaceIndex(tables.modelingResultsTable);

        if (placeIndex != -1) {
            String stage = getCurrentStage(tables, data, startStage, placeIndex);
            MachineTableRow row = data.machineRows.get(findMachineRowStage(data.machineRows, stage));
            StringBuilder resultOperation = new StringBuilder();

            for (int operationIndex = 0; operationIndex < row.getValues().size(); operationIndex++) {
                resultOperation.append(getOperation(data.yCodes.get(operationIndex),
                        row.getValues().get(operationIndex)));
            }

            tables.modelingResultsTable.getItems().getFirst().getResults().set(placeIndex,
                    findStageWithCode(data.aCode, stage).getValue());

            if (resultOperation.isEmpty()) {
                tables.modelingResultsTable.getItems().getLast().getResults().set(placeIndex,
                        resultOperation.toString());
            } else {
                tables.modelingResultsTable.getItems().getLast().getResults().set(placeIndex,
                        resultOperation.substring(2));
            }

            SlideUtils.fillResultsTable(tables.modelingResultsTable, data.results, cycles);

            return true;
        }
        return false;
    }

    private static String getCurrentStage(TablePackage tables, DataPackage data, String startStage, int placeIndex) throws UnknownCodeException {
        if (placeIndex == 0) {
            if (startStage.isEmpty()) {
                throw new UnknownCodeException("Unknown start stage with code \"" + startStage + "\"!");
            }
            return startStage;
        } else {
            String pastStage = tables.modelingResultsTable.getItems().getFirst().getResults().get(placeIndex - 1);
            for (SignalEncoding signal: data.aCode) {
                if (signal.getValue().equals(pastStage)) {
                    pastStage = signal.getCode();
                    break;
                }
            }

            MachineTableRow currRow = null;
            int currRowNum = -1;
            for (MachineTableRow row: data.machineRows) {
                currRowNum++;
                if (row.getStage().equals(pastStage)) {
                    currRow = row;
                    break;
                }
            }
            assert currRow != null;

            for (int operationIndex = 0; operationIndex < currRow.getValues().size(); operationIndex++) {
                String operation = getOperation(data.yCodes.get(operationIndex),
                        currRow.getValues().get(operationIndex));

                if (operation.equals(", yk")) {
                    throw new UnknownCodeException("With the receipt of the micro command \"yk\" at the last stage, the program completed its execution!");
                }
            }

            String logicConditionName = getLogicCondition(data.xCode, currRow.getLogic());
            int expectedLogicVal = Integer.parseInt(currRow.getI());

            int logicVal = getLogicVal(data, placeIndex, logicConditionName);

            if (logicVal == expectedLogicVal) {
                if (currRowNum + 1 >= data.machineRows.size()) {
                    throw new UnknownCodeException("it is not possible to get the next stage number! Current stage number is \"" + pastStage + "\"!");
                }
                return data.machineRows.get(currRowNum + 1).getStage();
            } else {
                return currRow.getAddress();
            }
        }
    }

    private static int getLogicVal(DataPackage data, int placeIndex, String logicConditionName) {
        int logicVal = -1;
        if (logicConditionName.equals("Const 0")) {
            logicVal = 0;
        } else if (logicConditionName.equals("Const 1")) {
            logicVal = 1;
        } else {
            for (LogicSignalEncoding signal: data.logicEncoding) {
                if (signal.getEncoding().getValue().equals(logicConditionName)) {
                    logicVal = signal.getLogicValues().get(placeIndex);
                }
            }
        }
        return logicVal;
    }

    private static String getLogicCondition(List<SignalEncoding> codes, String code) throws UnknownCodeException {
        for (SignalEncoding signal: codes) {
            if (signal.getCode().equals(code)) {
                if (signal.getValue().isEmpty()) {
                    throw new UnknownCodeException("Unknown logic condition with code \"" + code + "\"!");
                }
                else {
                    return signal.getValue();
                }
            }
        }

        throw new UnknownCodeException("Unknown operation with code \"" + code + "\"!");
    }

    private static String getOperation(List<SignalEncoding> codes, String code) throws UnknownCodeException {
        for (SignalEncoding signal: codes) {
            if (signal.getCode().equals(code)) {
                if (signal.getValue().isEmpty()) {
                    throw new UnknownCodeException("Unknown operation with code \"" + code + "\"!");
                }
                else if (signal.getValue().equals("-")) {
                    return "";
                }

                return ", " + signal.getValue();
            }
        }

        throw new UnknownCodeException("Unknown operation with code \"" + code + "\"!");
    }

    private static int getResultPlaceIndex(TableView<ResultTableRow> resultTable) {
        int index = -1;
        for (String res: resultTable.getItems().getFirst().getResults()) {
            index++;

            if (res.isEmpty()) {
                return index;
            }
        }

        return -1;
    }

    private static int findMachineRowStage(List<MachineTableRow> tableRows, String code) throws UnknownCodeException {
        int ind = -1;
        for (MachineTableRow row: tableRows) {
            ind++;

            if (row.getStage().equals(code)) {
                return ind;
            }
        }

        throw new UnknownCodeException("Unknown stage with code \"" + code + "\"!");
    }

    private static SignalEncoding findStageWithCode(List<SignalEncoding> codes, String code) throws UnknownCodeException {
        for (SignalEncoding signal: codes) {
            if (signal.getCode().equals(code)) {
                return signal;
            }
        }

        throw new UnknownCodeException("Unknown stage with code \"" + code + "\"!");
    }
}
