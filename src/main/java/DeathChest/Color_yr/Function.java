package DeathChest.Color_yr;

import java.util.regex.Pattern;

public class Function {
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
