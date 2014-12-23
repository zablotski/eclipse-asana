package functionalities;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

public class RequestHandler {
	private static String ASANA_API_BASIC_URL = "https://app.asana.com/api/1.0";
	private final static RequestHandler ourInstance = new RequestHandler();
    
	public static RequestHandler getInstance() {
        return ourInstance;
    }
    
    public RequestHandler() {
    }
	public String asanaApiJsonRequest(String url, String httpMethod, String jsonData, List<SimpleEntry<String, String>> headers) throws Exception{	
		URL urlObject;
		HttpURLConnection connection = null;  
		try {
			urlObject = new URL(ASANA_API_BASIC_URL + url);
			connection = (HttpURLConnection)urlObject.openConnection();
			connection.setRequestMethod(httpMethod);
			
			if(jsonData != null){
				connection.setRequestProperty("Content-Type", "application/json");

				connection.setRequestProperty("Content-Length", Integer.toString(jsonData.getBytes().length));
			}
			
			for(SimpleEntry<String, String> header : headers){
				connection.setRequestProperty(header.getKey(), header.getValue());
			}

			connection.setUseCaches (false);
			connection.setDoInput(true);
			

			//Send request
			connection.setDoOutput(true);
			if(jsonData != null){
				DataOutputStream httpOutputStream = new DataOutputStream (
						connection.getOutputStream ());
				
				httpOutputStream.writeBytes (jsonData);
				httpOutputStream.flush ();
				httpOutputStream.close ();
			}
			

			//Get Response    
			BufferedReader httpInputReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuffer response = new StringBuffer(); 
			
			while((line = httpInputReader.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			
			httpInputReader.close();
			return response.toString();

		} 
		
		finally {

			if(connection != null) {
				connection.disconnect(); 
			}
		}
		
	}
}
