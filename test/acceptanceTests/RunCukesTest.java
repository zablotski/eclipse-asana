package acceptanceTests;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
// Associates Cucumber-JVM with the JUnit runner

@RunWith(Cucumber.class)
@CucumberOptions(format = {"html:target/cucumber-html-report","json:target/cucumber-json-report.json"})

public class RunCukesTest{
	
}
