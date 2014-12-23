package unitTests;
import static org.junit.Assert.*;

import org.junit.Test;

import functionalities.LoginHandler;

public class LoginHandlerTest {
	
	@Test
	public void httpPost_CorrectData(){
		LoginHandler testedObject = LoginHandler.getInstance();
		String asanaApiKey = "5aix9w1X.93qkODiCZmG2EBSZ92EbteK";
		String email = "j-kwasnicki@wp.pl";

		assertTrue(testedObject.testConnection(asanaApiKey, email));
	}
}
