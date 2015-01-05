package functionalities;

import com.sun.jersey.api.client.ClientResponse;

import net.joelinn.asana.ApiException;
import net.joelinn.asana.Asana;

public class AsanaTerminal extends Asana{
	private static String loginErrorMessage = "Login Error: Asana User credentials incorrect";
	
	public AsanaTerminal(String apiKey, String email) throws ApiException{
		super(apiKey);
		
		if(this.users().getMe() != null){
			if(!this.users().getMe().email.equals(email)){
				throw new ApiException(ClientResponse.Status.UNAUTHORIZED, loginErrorMessage);
			}
		}else{
			throw new ApiException(ClientResponse.Status.UNAUTHORIZED, loginErrorMessage);
		}
	}

}
