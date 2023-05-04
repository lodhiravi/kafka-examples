package myapps.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class KafkaUtil {

    public static String getBootstrapServer() {
        try (InputStream input = new FileInputStream("config/kafka.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty("bootstrap.server");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
