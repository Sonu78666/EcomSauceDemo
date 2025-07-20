package Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Properties;
import java.io.IOException;

public class PropertyUtils {

    private static final Properties props = new Properties();
    private static final Logger logger = LoggerFactory.getLogger(PropertyUtils.class);

    static {
        String env = System.getProperty("env", "qa");
        String path = "src/test/resources/Config/" + env + ".properties";
        try (FileInputStream fis = new FileInputStream(path)) {
            props.load(fis);
            logger.info("Loaded configuration for environment: {}", env);
        } catch (IOException e) {
            logger.warn("Could not load property file for env: {} at path: {}. Attempting fallback to 'qa'", env, path);
            if (!"qa".equals(env)) {
                try (FileInputStream fis = new FileInputStream("src/test/resources/Config/qa.properties")) {
                    props.load(fis);
                    logger.info("Loaded fallback configuration for environment: qa");
                } catch (IOException ex) {
                    throw new RuntimeException("Failed to load fallback configuration", ex);
                }
            } else {
                throw new RuntimeException("Could not load configuration for env: " + env, e);
            }
        }


    }

    public static String getValue(String key) {
        return props.getProperty(key);
    }

    public static String getValue(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

}
