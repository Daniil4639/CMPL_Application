package app.cmpl_app.datas.encoding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class SignalEncoding{

    private String code;
    private String value;

    public static void getEncodingByBit(List<SignalEncoding> list, int bit) {
        int size = (int) Math.pow(2, bit);
        list.clear();

        for (int ind = 0; ind < size; ind++) {
            list.add(new SignalEncoding(String.format("%" + bit + "s", Integer.toBinaryString(ind))
                    .replace(' ', '0'), ""));
        }
    }
}