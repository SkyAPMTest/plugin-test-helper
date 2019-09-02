package test.apache.skywalking.apm.helper.util;

public class StringUtils {

    public static boolean isBlank(String str) {
        return str == null || str.length() == 0;
    }
}
