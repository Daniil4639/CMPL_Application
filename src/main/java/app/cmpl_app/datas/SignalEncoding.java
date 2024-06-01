package app.cmpl_app.datas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class SignalEncoding {

    private String code;
    private String value;

    public static List<SignalEncoding> getEncodingByBit(int bit) {
        int size = (int) Math.pow(2, bit);
        List<SignalEncoding> result = new ArrayList<>();

        for (int ind = 0; ind < size; ind++) {
            result.add(new SignalEncoding(String.format("%" + bit + "s", Integer.toBinaryString(ind))
                    .replace(' ', '0'), ""));
        }

        return result;
    }
}
