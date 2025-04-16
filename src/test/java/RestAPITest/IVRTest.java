package RestAPITest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;

import RestAPI.APIClients;
import RestAPI.APIEndpoints;
import Utilities.APIReadConfig;
import Utilities.APIReadHeaders;
import Utilities.IVRAPIResponse;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;



public class IVRTest{

	protected APIClients apiClient;
	protected APIEndpoints apiEndpoint;
	public Map<String,String> headers;
	public String baseURL;
    APIReadConfig readConfig;


	@BeforeClass
	public void setup() throws IOException {

		// For IVR 	
		readConfig = new APIReadConfig();
		baseURL=readConfig.getIVRBaseURL();
		APIReadHeaders readHeaders = new APIReadHeaders("Headers.json");
		headers = readHeaders.getHeaders("IvrHeaders");

		apiClient = new APIClients(baseURL,headers);
		apiEndpoint = new APIEndpoints(apiClient);
	}


	@Test
	@Severity(SeverityLevel.BLOCKER)
	public void testGetNextLeg() {
		APIResponse response = apiEndpoint.getNextLegJson(headers);
		Assert.assertEquals(response.status(), 200);
		String responseText=response.text();
		String filePath = readConfig.getIvrResponsefile();
		IVRAPIResponse.saveResponseToFile(filePath, response.text()); 
		System.out.println("ResponseText:"+responseText);
		Assert.assertEquals(response.statusText(),"OK");
		Assert.assertTrue(responseText.contains("No leg data found"));
	}

	@Test
	public void testGetActiveRide() {
		APIResponse response = apiEndpoint.getActiveRideJson(headers);
		String responseBody  = response.text();
		Assert.assertEquals(response.statusText(),"OK");
		Assert.assertTrue(responseBody.contains("false"));
	}

	@Test
	public void testGetRequestRide() throws IOException {
		APIResponse response =apiEndpoint.getRequestRideJson(headers);
		Assert.assertEquals(response.status(), 200);
		String responseBody=response.text();
		System.out.println("Response Body:"+responseBody);
		// Convert JSONString to JSONNode
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(response.body()); 
		//Navigate to "Data" object and extract the inside values
		JsonNode dataNode =rootNode.get("Data");
		String actualnetStopId=dataNode.get("netStopId").asText();  
		Assert.assertEquals(actualnetStopId, "DC9E910F-419A-4444-9330-139BA79308E3"); 
		Assert.assertEquals(dataNode.get("targetSystem").asText(), "LYFT");
	}

	@AfterClass
	public void tearDown() {
		apiClient.dispose();

	}

}
