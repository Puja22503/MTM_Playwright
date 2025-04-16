package RestAPITest;

import java.io.IOException;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;

import RestAPI.BreauthoringAPIClient;
import RestAPI.BreauthoringAPIEnpoints;
import Utilities.APIReadConfig;
import Utilities.APIReadHeaders;
import Utilities.IVRAPIResponse;

public class BreauthoringTest {
	public String actualLabel;
	public String basebreUrl;
	public String breusername;
	public String brepassword;
	public Map<String,String> getCancelqueryParams;
	public Map<String,String> getTransactionqueryParams;
	public Map<String,String> getDenialqueryParams;
	protected BreauthoringAPIClient breapiClient;
	protected BreauthoringAPIEnpoints breapiEndpoint;
	APIReadConfig readConfig;



	@BeforeClass
	public void setup() throws IOException {
		readConfig = new APIReadConfig();
		basebreUrl=readConfig.getBreauthoringBaseURL();
		breusername=readConfig.getUsername();
		brepassword=readConfig.getPassword();
		APIReadHeaders readHeaders = new APIReadHeaders("QueryParams.json");
		getCancelqueryParams = readHeaders.getHeaders("BreauthoringGetCancel");
		getTransactionqueryParams=readHeaders.getHeaders("getTransactiondetails");
		getDenialqueryParams=readHeaders.getHeaders("getDenialCodes");
		breapiClient = new BreauthoringAPIClient(basebreUrl,breusername,brepassword);
		breapiEndpoint = new BreauthoringAPIEnpoints(breapiClient);

	}


	@Test
	public void testGetCancelreason() throws IOException {

		APIResponse response = breapiEndpoint.getcancelReason(getCancelqueryParams);
		Assert.assertEquals(response.status(),200);
		String responseBody=response.text();
		System.out.println("Response Body:"+ responseBody);
		String filePath=readConfig.getBreauthoringResponsefile();
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
		APIResponse response = breapiEndpoint.getTransactiondetails(getTransactionqueryParams);	
		Assert.assertEquals(response.statusText(),"OK");
		Assert.assertEquals(response.status(),200);
		String responseBody=response.text();
		String filePath=readConfig.getBreauthoringResponsefile();
		IVRAPIResponse.saveResponseToFile(filePath, responseBody);
	}

	@Test
	public void testDenialDetails() {
		APIResponse response=breapiEndpoint.getDenialcodes(getDenialqueryParams);
		Assert.assertEquals(response.statusText(),"OK");
		Assert.assertEquals(response.status(),200);
		String responseBody=response.text();
		String filePath=readConfig.getBreauthoringResponsefile();
		IVRAPIResponse.saveResponseToFile(filePath, responseBody);

	}

	@AfterClass
	public void tearDown() {
		breapiClient.dispose();

	}
}	



