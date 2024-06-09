package app.cmpl_app.packages;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaveProjectPackage {
    private int cycles;
    private String entryStage;
    private DataPackage data;
}
