package RestAPITest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;

import RestAPI.APIClients;
import RestAPI.APIEndpoints;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Utilities.APIReadConfig;
import Utilities.APIReadHeaders;
import Utilities.IVRAPIResponse;

public class LYFTTest {

	protected APIClients lyftapiClient;
	protected APIEndpoints lyftapiEnpoint;
	public String baseLYFTUrl;
	public Map<String,String> lyftheaders;
	APIReadConfig readConfig;


	@BeforeClass
	public void setup() throws IOException {
		// For LYFT		
		 readConfig = new APIReadConfig();
		baseLYFTUrl=readConfig.getLYFTBaseURL();
		APIReadHeaders readHeaders = new APIReadHeaders("Headers.json");
		lyftheaders = readHeaders.getHeaders("LYFTHeaders");
		lyftapiClient = new APIClients(baseLYFTUrl,lyftheaders);
		lyftapiEnpoint = new APIEndpoints(lyftapiClient);
	}
	@Test(enabled = false)
	public void testLyftDispatch() throws JsonMappingException, JsonProcessingException {
		APIResponse response = lyftapiEnpoint.getLYFTDispatch(lyftheaders);
		Assert.assertEquals(response.status(), 200);

		// Save response body
		String responseBody=response.text();
		System.out.println("Response body:"+responseBody);
		String filePath=readConfig.getLyftResponsefile();
		IVRAPIResponse.saveResponseToFile(filePath, responseBody);

		//Parse JSON and Validate

		ObjectMapper objectmapper = new ObjectMapper();
		JsonNode rootNode = objectmapper.readTree(responseBody);

		JsonNode originAddress = rootNode.path("ride_details").path("origin").path("address");
		JsonNode destinationAddress = rootNode.path("ride_details").path("destination").path("address");
		JsonNode phoneNumber=rootNode.path("ride_details").path("passenger").path("phone_number");
		String expectedOriginAddress="8627 Langdon Ave, North Hills, CA 91343, USA";
		String expectedDestinationAddress="1 World Way, Los Angeles, CA 90045";
		String expectedPhoneNumber="+13035551234";
		Assert.assertEquals(originAddress.asText(), expectedOriginAddress,"Origin Address is not matching");
		Assert.assertEquals(destinationAddress.asText(),expectedDestinationAddress,"Destination is not matching");
		Assert.assertEquals(phoneNumber.asText(),expectedPhoneNumber ,"Phone No is not matching");

	}

	@Test
	public void testGetDetails() {

		APIResponse response = lyftapiEnpoint.getDetails();
		Assert.assertEquals(response.status(),200);
		String responseBody=response.text();
		System.out.println("Response Body:"+ responseBody);
		String filePath=readConfig.getLyftResponsefile();
		IVRAPIResponse.saveResponseToFile(filePath, responseBody);
		Assert.assertTrue(responseBody.contains("test"));
	}

	@AfterClass
	public void tearDown() {
		lyftapiClient.dispose();
	}
}
