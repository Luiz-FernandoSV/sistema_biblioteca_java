package Utils;

import java.time.LocalDate;

public class Utilidades {
    public static LocalDate toLocalDate(String data) {
        return LocalDate.parse(data);
    }

}