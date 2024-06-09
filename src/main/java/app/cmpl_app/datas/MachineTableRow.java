package app.cmpl_app.datas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MachineTableRow {

    private String stage;
    private List<String> values;
    private String logic;
    private String i;
    private String address;

    public static MachineTableRow getDefaultRow(int valuesCount) {
        return new MachineTableRow("", new ArrayList<>(Collections.nCopies(valuesCount, "")), "", "", "");
    }
}
