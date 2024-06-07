package app.cmpl_app.datas.encoding;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
public class LogicSignalEncoding {

    private List<Integer> logicValues;

    private SignalEncoding encoding;

    public LogicSignalEncoding(SignalEncoding encoding, int cycles) {
        this.encoding = encoding;
        logicValues = new ArrayList<>(Collections.nCopies(cycles, 0));
    }
}
