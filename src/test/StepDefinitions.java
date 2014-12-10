package test;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import eclipseasana.actions.Belly;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StepDefinitions {

    @Given("^login to app$")
    public void login_to_app() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^login with e-mail \"(.*?)\" and api-key \"(.*?)\"$")
    public void login_with_e_mail_and_api_key(String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^A user loginned$")
    public void a_user_loginned() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


}