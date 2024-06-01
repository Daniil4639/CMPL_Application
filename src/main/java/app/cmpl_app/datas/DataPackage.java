package app.cmpl_app.datas;

import javafx.scene.control.TableColumn;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class DataPackage {

    public Properties props;
    public List<MachineTableRow> machineRows;

    public List<SignalEncoding> aCode;
    public List<SignalEncoding> xCode;
    public List<List<SignalEncoding>> yCodes;
}
