package RestAPITest;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;

import Utilities.IVRAPIResponse;

public class BreauthoringTest extends Base{
public String actualLabel;

	@Test
	public void testGetCancelreason() throws IOException {

		APIResponse response = breapiEndpoint.getcancelReason();
		Assert.assertEquals(response.status(),200);
		String responseBody=response.text();
		System.out.println("Response Body:"+ responseBody);
		String filePath="src/test/resources/BreauthoringResponse.json";
		IVRAPIResponse.saveResponseToFile(filePath, responseBody);
		ObjectMapper objectmapper= new ObjectMapper();
		JsonNode rootNode = objectmapper.readTree(response.body()); 
		JsonNode dataSources=rootNode.get("dataSources");
		if(dataSources.isArray()) {
			for(JsonNode dataSource:dataSources) {
				JsonNode idNode = dataSource.get("id");
				if(idNode.asInt()==15) {
					JsonNode labelNode = dataSource.get("label");
					if (labelNode != null) {
						String actualLabel = labelNode.asText();
						System.out.println("Label for id 15: " + actualLabel);
					} else {
						System.out.println("Label field is missing for id 15.");
					}
					break;	
				}
				Assert.assertEquals(actualLabel, "Addt info req and not received");
			}
			
		}
		
	}
	
	
	@Test
	public void testTransactionDetails() {
	APIResponse response = breapiEndpoint.getTransactiondetails();	
	Assert.assertEquals(response.statusText(),"OK");
	Assert.assertEquals(response.status(),200);
	String responseBody=response.text();
	String filePath="src/test/resources/BreauthoringResponse.json";
	IVRAPIResponse.saveResponseToFile(filePath, responseBody);
	}
}	



