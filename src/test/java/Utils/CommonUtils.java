package Utils;

public class CommonUtils {

    /**
     * Replaces tokens like v1, v2, v3 in the rawXpath with actual runtime values
     */
    public static String customizeXpath(String rawXpath, String... replacements) {
        String modifiedXpath = rawXpath;
        for (int i = 0; i < replacements.length; i++) {
            modifiedXpath = modifiedXpath.replace("v" + (i + 1), replacements[i]);
        }
        return modifiedXpath;
    }
}
