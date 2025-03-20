package utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static utility.ExtentReportListener.test;

public class ConfigurationManager {

    private static Properties properties;

    private ConfigurationManager() {

    }
    public static String getProperty(String key) {
        if (properties == null) {
            properties = new Properties();
            setupConfigReader("config.properties");
        }
        return properties.getProperty(key);
    }

    public static void setupConfigReader(String fileName) {
        try (InputStream input = ConfigurationManager.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                test.warning(String.format("File not found: %s", fileName));
                throw new IOException("File not found: " + fileName);
            }
            properties.load(input);
        } catch (IOException e) {
            test.warning("Cannot read properties file from resources folder");
        }
    }


}
