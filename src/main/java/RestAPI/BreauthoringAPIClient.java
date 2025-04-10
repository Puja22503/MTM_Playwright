package RestAPI;

import java.util.Map;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

public class BreauthoringAPIClient {

private final Playwright p;
private final APIRequestContext requestcontext;

public BreauthoringAPIClient(String baseUrl,String username,String password) {
	p =Playwright.create();
	APIRequest.NewContextOptions contextOptions = new APIRequest.NewContextOptions()
			.setBaseURL(baseUrl)
			.setHttpCredentials(username, password)
			.setTimeout(60000);
	requestcontext = p.request().newContext(contextOptions);
    
}


// Generic GET method with query parameters

public APIResponse getJson(String endpoint, Map<String,String> queryParams) {
	
	RequestOptions options = RequestOptions.create();
	if(queryParams!= null) {
		queryParams.forEach(options::setQueryParam);
	}
	return requestcontext.get(endpoint, options);
}

public void dispose() {
    requestcontext.dispose();
    p.close();
}
}

