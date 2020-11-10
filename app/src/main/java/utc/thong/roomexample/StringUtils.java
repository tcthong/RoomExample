package utc.thong.roomexample;

public class StringUtils {
    public static boolean isSameStrings(String s1, String s2) {
        if (s1 != null) {
            return s1.equals(s2);
        } else {
            return s2 == null;
        }
    }
}
