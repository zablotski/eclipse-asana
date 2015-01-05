package acceptanceTests;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import functionalities.ApplicationContext;
import static org.junit.Assert.*;


public class LoginStepDefs {
	private ApplicationContext application;
	private String email, key;
	private boolean isLoggedIn;
	
	@Given("^email is \"(.*?)\"$")
	public void email_is(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		email = arg1;
	}

	@Given("^APIkey is \"(.*?)\"$")
	public void apikey_is(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    key = arg1;
	}

	@When("^user clicks submit login form button$")
	public void user_clicks_submit_login_form_button() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		try{
			application = new ApplicationContext();
			application.initAsana(key, email);
			isLoggedIn = true;
		} catch (Exception ex){
			isLoggedIn = false;
		}
	}

	@Then("^user is \"(.*?)\"$")
	public void user_is(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    if(arg1.equals("Logged in")){
	    	assertTrue(isLoggedIn);
	    } else {
	    	assertTrue(!isLoggedIn);
	    }
	}

}
