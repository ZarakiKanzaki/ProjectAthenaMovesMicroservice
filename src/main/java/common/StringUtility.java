package common;

public class StringUtility {
    public static boolean isNullOrEmpty(String theString) {
        return theString == null || theString.isEmpty();
    }

    public static boolean isNullOrWhitespace(String theString) {
        return  isNullOrEmpty(theString) || theString.isBlank();
    }
}
