package functionalities;
import org.json.*;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.DatatypeConverter;

public final class LoginHandler {
	private static String TEST_CONNECTION_URL = "/users/me";
	private static String AUTHORIZATION_HTTP_HEADER_NAME = "Authorization";
	private static String BASE_64_AUTHORIZATION_HTTP_HEADER_VALUE_PREFIX = "Basic ";
	private static String ASANA_API_KEY_BASE_64_POSTFIX = ":";
	private final static LoginHandler ourInstance = new LoginHandler();
	private String asanaApiKey = "5aix9w1X.93qkODiCZmG2EBSZ92EbteK"; //temporary hard-coded
	private String asanaEmail;
	private boolean isAuthorized = false;
	private RequestHandler requestHandler = RequestHandler.getInstance();
    
	public static LoginHandler getInstance() {
        return ourInstance;
    }
 
    public LoginHandler(RequestHandler rq) {
    	this.requestHandler = rq;
    }
    
    public LoginHandler() {
    	
    }
    
    public String getAsanaUserName() {
		return asanaEmail;
	}
    
    public boolean login(String email, String apiKey) throws Exception{
    	this.asanaEmail = email;
    	this.asanaApiKey = apiKey;
    	
    	//porownaj wynik testConnection z username i jak sie zgodzi to hula
    	isAuthorized = testConnection(asanaApiKey, asanaEmail);
    	
    	return isAuthorized;
    }
    
    public SimpleEntry<String, String> getAuthorizationHeader(){
    	String encodedApiKey = toBase64(asanaApiKey + ASANA_API_KEY_BASE_64_POSTFIX);
    	return new SimpleEntry<String, String>(AUTHORIZATION_HTTP_HEADER_NAME, BASE_64_AUTHORIZATION_HTTP_HEADER_VALUE_PREFIX + encodedApiKey);
    }
    
    public boolean testConnection(String apiKey, String email){
    	List<SimpleEntry<String, String>> headers = new ArrayList<>();
    	headers.add(getAuthorizationHeader());
    	JSONObject json;

    	try{
    		json = new JSONObject(requestHandler.asanaApiJsonRequest(TEST_CONNECTION_URL, "GET", null, headers));
    		
    		if(json.getJSONObject("data").getString("email").equals(email)){
    			return true;
    		}
    	} catch (Exception e){
    		return false;
    	}

    	return false;
    }
    
    
    
    private String toBase64(String input){
    	return DatatypeConverter.printBase64Binary(input.getBytes());
    }
}
