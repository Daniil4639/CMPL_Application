package app.cmpl_app.datas;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class ResultTableRow {

    private String name;
    private List<String> results;

    public ResultTableRow(String name) {
        this.name = name;
        results = new ArrayList<>();
    }

    public void resize(int length) {
        while (length > results.size()) {
            results.add("");
        }
        while (length < results.size()) {
            results.removeLast();
        }
    }

    public static void clear(ResultTableRow results) {
        results.getResults().replaceAll(ignored -> "");
    }
}
