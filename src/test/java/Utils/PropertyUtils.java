package Utils;

import java.io.FileInputStream;
import java.util.Properties;
import java.io.IOException;

public class PropertyUtils {

    private static final Properties props = new Properties();

    static {
        String env = System.getProperty("env", "qa");
        String path = "src/test/resources/Config/" + env + ".properties";
        try (FileInputStream fis = new FileInputStream(path)) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Could not load property file for env: " + env + " at path: " + path + " | Error: " + e.getMessage(), e);
        }


    }

    public static String getValue(String key) {
        return props.getProperty(key);
    }

}
