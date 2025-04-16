package Utilities;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;


	public class IVRAPIResponse {
	    private static final ObjectMapper objectMapper = new ObjectMapper();

	    public static void saveResponseToFile(String filePath, Object response) {
	        try {
	            File file = new File(filePath);
	            file.getParentFile().mkdirs(); 

	            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, response);
	            System.out.println("Response saved successfully to: " + filePath);
	        } catch (IOException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Failed to save response to JSON file");
	        }
	    }
	}


