package test;
import static org.junit.Assert.*;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import functionalities.RequestHandler;

public class RequestHandlerTest {
	
		
	@Test
	public void httpPost_CorrectData(){
		RequestHandler testedObject = RequestHandler.getInstance();
		String asanaApiKey = "5aix9w1X.93qkODiCZmG2EBSZ92EbteK:";
		String url = "/users/me";
		String httpMethod = "GET";
		String jsonData = null;
		List<SimpleEntry<String, String>> headers = new ArrayList<>();
		
		headers.add(new SimpleEntry<String, String>("Authorization","Basic " + DatatypeConverter.printBase64Binary(asanaApiKey.getBytes())));
		
		try {
			assertNotNull(testedObject.asanaApiJsonRequest(url, httpMethod, jsonData, headers));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
