package Utilities;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class APIReadConfig {

	Properties pro;
	
	public APIReadConfig() {
    pro = new Properties();
    try (InputStream input = getClass().getClassLoader().getResourceAsStream("Config.properties")) {
        if (input == null) {
            throw new RuntimeException("Unable to find Config.properties");
        }
        pro.load(input);
    } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException("Failed to load Config.properties");
    }
}

public String getIVRBaseURL() {
    return pro.getProperty("IvrBaseUrl").trim();
}

public String getLYFTBaseURL() {
	return pro.getProperty("LyftBaseUrl").trim();
}
public String getProperty(String key) {
    return pro.getProperty(key);
}
	
}