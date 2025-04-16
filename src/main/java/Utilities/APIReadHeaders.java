package Utilities;

import java.io.InputStream;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APIReadHeaders {
	private final Map<String, Map<String, String>> headers;

    public APIReadHeaders(String jsonFileName) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(jsonFileName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + jsonFileName);
            }
            ObjectMapper mapper = new ObjectMapper();
            headers = mapper.readValue(inputStream, new TypeReference<Map<String, Map<String, String>>>(){});
        } catch (Exception e) {
            throw new RuntimeException("Failed to load headers from JSON file: " + jsonFileName, e);
        }
    }

    public Map<String, String> getHeaders(String headerKey) {
        return headers.get(headerKey);
    }
}
