package app.cmpl_app.datas;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MachineTableRow {

    private String stage;
    private List<String> values;
    private String logic;
    private String i;
    private String address;
}
