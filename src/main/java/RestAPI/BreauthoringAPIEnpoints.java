package RestAPI;


import java.util.Map;

import com.microsoft.playwright.APIResponse;

public class BreauthoringAPIEnpoints {

	// APIClient class is for managing API requests	

	private final BreauthoringAPIClient breapiClient;

	public BreauthoringAPIEnpoints(BreauthoringAPIClient breapiClients) {
		this.breapiClient = breapiClients;
	}	

	//Get cancel reason   

	public APIResponse getcancelReason(Map<String,String> queryParams) {
	return breapiClient.getJson("/breauthoring/api/DataSources", queryParams);
	}

	//Get transaction details
   
   public APIResponse getTransactiondetails(Map<String,String> queryParams) {
   return breapiClient.getJson("/breauthoring/api/DataSources", queryParams);
   }	
   
   //Get denial codes
   
   public APIResponse getDenialcodes(Map<String,String> queryParams) {
	   return breapiClient.getJson("/breauthoring/api/DataSources", queryParams);
   }

}
