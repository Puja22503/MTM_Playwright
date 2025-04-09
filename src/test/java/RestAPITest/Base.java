package RestAPITest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import RestAPI.APIClients;
import RestAPI.APIEndpoints;
import Utilities.APIReadConfig;

public class Base {
	 
	protected APIClients apiClient;
	protected APIEndpoints apiEndpoint;
	protected APIClients lyftapiClient;
	protected APIEndpoints lyftapiEnpoint;
	public static Logger logger;
	public Map<String,String> headers;
	public String baseURL;
	public String baseLYFTUrl;
	public Map<String,String> lyftheaders;
	
	
	
	@BeforeClass
	public void setup() throws IOException {
	// Read from config file	
	// For IVR 	
		APIReadConfig readConfig = new APIReadConfig();
		baseURL=readConfig.getIVRBaseURL();
		headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("X-Token-Key","5jW-BVent9tt-mtxkmx2*KQAYJf");
	
		apiClient = new APIClients(baseURL,headers);
		apiEndpoint = new APIEndpoints(apiClient);
		
   // For LYFT		
		APIReadConfig readConfigLYFT = new APIReadConfig();
		baseLYFTUrl=readConfigLYFT.getLYFTBaseURL();
		lyftheaders = new HashMap<>();
		lyftheaders.put("X-Token-Key","UQQ-A2C-L*FTEWFJVGwIIUlhaAgcG");
		lyftheaders.put("Content-Type", "application/json");
		lyftapiClient = new APIClients(baseLYFTUrl,lyftheaders);
		lyftapiEnpoint = new APIEndpoints(lyftapiClient);
		
		
	}


	@AfterClass
	public void tearDown() {
		apiClient.dispose();
		lyftapiClient.dispose();
	}
}
