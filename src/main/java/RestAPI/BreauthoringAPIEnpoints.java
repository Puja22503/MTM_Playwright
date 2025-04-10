package RestAPI;

import java.util.HashMap;
import java.util.Map;

import com.microsoft.playwright.APIResponse;

public class BreauthoringAPIEnpoints {

	// APIClient class is for managing API requests	

	private final BreauthoringAPIClient breapiClient;

	public BreauthoringAPIEnpoints(BreauthoringAPIClient breapiClients) {
		this.breapiClient = breapiClients;
	}	

	//Get cancel reason   

	public APIResponse getcancelReason() {
		Map<String,String> options = new HashMap<>();
		options.put("name", "Cancelreason");
		options.put("planCode", "FHC");
		options.put("effectiveDate", "10-04-2025");
		return breapiClient.getJson("/breauthoring/api/DataSources", options);
	}

	//Get transaction details
   
   public APIResponse getTransactiondetails() {
	   Map<String,String> options = new HashMap<>();
	   options.put("name", "Transactionclassification");
	   options.put("planCode","FHC");
	   options.put("effectiveDate", "11-04-2025");
	   return breapiClient.getJson("/breauthoring/api/DataSources", options);
   }	
   
   //

}
