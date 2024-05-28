package app.cmpl_app.datas;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Properties {

    private int operationsCount;
    private List<Integer> operationsSizes;
    private int logicSize;
    private int addressSize;

    public boolean isCorrect() {
        if (operationsCount == -1 || logicSize == -1 || addressSize == -1) {
            return false;
        }
        for (Integer operation: operationsSizes) {
            if (operation == -1) {
                return false;
            }
        }

        return true;
    }

    public static Properties getDefaultProps() {
        Properties props = new Properties();
        props.operationsCount = 1;
        props.operationsSizes = new ArrayList<>(List.of(1));
        props.logicSize = 1;
        props.addressSize = 1;

        return props;
    }
}