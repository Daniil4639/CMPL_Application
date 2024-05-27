package datas;

import java.util.List;

public class Properties {

    public int operationsCount;
    public List<Integer> operationsSizes;
    public int logicSize;
    public int addressSize;

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
        props.operationsSizes.set(0, 1);
        props.logicSize = 1;
        props.addressSize = 1;

        return props;
    }
}