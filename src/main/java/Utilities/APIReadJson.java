package Utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APIReadJson {
	private static final Map<String,JsonNode> jsonDataMap = new HashMap<>();
	private static JsonNode loadJson(String fileName) {
		if(!jsonDataMap.containsKey(fileName)) {
	 try {
	           
	            InputStream inputStream = APIReadJson.class.getClassLoader().getResourceAsStream(fileName);
	            if (inputStream == null) {
	                throw new RuntimeException("JSON file" +fileName +"not found in resources folder!");
	            }
	            ObjectMapper objectMapper = new ObjectMapper();
	           JsonNode jsondata = objectMapper.readTree(inputStream);
	           jsonDataMap.put(fileName, jsondata);
	        } catch (IOException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Failed to read JSON file");
	        }
	    }
	return jsonDataMap.get(fileName);
	}
	
// Method to get a jsonString by file and key	
	    public static String getJsonString(String fileName,String key) {
	      JsonNode fileJson=loadJson(fileName);
	      JsonNode valueNode = fileJson.get(key); 
	      if(valueNode == null) {
	    	  throw new RuntimeException("Key '"+key+"not found in file"+ fileName);      
	      }
	    return valueNode.toString();
}
	    
	    
	    public static String getJsonString(String fileName) {
		      JsonNode fileJson=loadJson(fileName);
		      
		      if(fileJson == null) {
		    	  throw new RuntimeException("Key '"+fileName+"not found in file"+ fileName);      
		      }
		    return fileJson.toString();
}
}