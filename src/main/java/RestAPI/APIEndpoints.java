package RestAPI;

import java.util.Map;

import com.microsoft.playwright.APIResponse;

import Utilities.APIReadJson;
import Utilities.IVRTestData;


public class APIEndpoints {


	private final APIClients apiClient;

    public APIEndpoints(APIClients apiClients) {
        this.apiClient = apiClients;
    }
//------------------------------------------IVR Endpoints-----------------------------------------------------------

// Get Next leg
    public APIResponse getNextLegJson(Map<String,String> headers) {
        String jsonData = APIReadJson.getJsonString("Ivr_NextLegTestData.json","getNextLeg");
        return apiClient.postJson("/ivrapi/api/GetNextLeg", jsonData,headers);
    }
    
// Get Active ride API
    public APIResponse getActiveRideJson(Map<String,String> headers) {
    	String jsonData="\""+IVRTestData.getNextActive().replace("\"", "\\\"") + "\"";
    	return apiClient.postJson("/ivrapi/api/GetActiveRide", jsonData,headers);
    }

 // Get Request ride API
    public APIResponse getRequestRideJson(Map<String,String> headers) {
    	String jsonData="\""+IVRTestData.getRequestRide().replace("\"", "\\\"") + "\"";
    	return apiClient.postJson("/ivrapi/api/RequestRide", jsonData,headers);
    }

//----------------------------------LYFT Endpoint-------------------------------------------------------------------------

//Create LYFT new rider request
    
public APIResponse getLYFTDispatch(Map<String,String> lyftheaders) {
	String jsonData = APIReadJson.getJsonString("LYFTDispatchTestData.json");
	return apiClient.postJson("/api/v1/dispatch", jsonData,lyftheaders);
}

//Get LYFT details

public APIResponse getDetails() {
	return apiClient.getJson("/api/v1/HelloWorld","caller","test");
}






}
