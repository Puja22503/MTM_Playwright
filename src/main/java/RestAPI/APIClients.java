package RestAPI;

import java.io.IOException;
import java.util.Map;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;



public class APIClients {

	private final Playwright p;
	private final APIRequestContext requestContext;


	public APIClients(String baseURL, Map<String,String> headers) throws IOException {
		p = Playwright.create();

// Create context options and set baseUrl
		
		APIRequest.NewContextOptions contextOptions= new APIRequest.NewContextOptions()
				.setBaseURL(baseURL)
				.setExtraHTTPHeaders(headers)
				.setTimeout(60000);

//Initialize requestContext using those options		
		requestContext = p.request().newContext(contextOptions);

	}



	// Generic POST method--- allow dynamic headers	
	public APIResponse postJson(String endpoint, String jsonData,Map<String,String> headers) {
		RequestOptions options = RequestOptions.create().setData(jsonData);
		headers.forEach(options::setHeader);

		return requestContext.post(endpoint,options);


	}		
// Get Method for LYFT controller request
	
	public APIResponse getJson(String endpoint,String paramName,String paramValue) {
	RequestOptions options=RequestOptions.create().setQueryParam(paramName, paramValue);
	return requestContext.get(endpoint, options);
	}
	
	
	
	public void dispose() {
		p.close();
	}

}
