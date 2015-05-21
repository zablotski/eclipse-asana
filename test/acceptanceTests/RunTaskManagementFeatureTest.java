package acceptanceTests;
import cucumber.api.junit.Cucumber;
import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;
// Associates Cucumber-JVM with the JUnit runner

@RunWith(Cucumber.class)
@CucumberOptions(features={"test/acceptanceTests/resources/task_management/task_management.feature"}, 
					format = {"html:target/cucumber-html-report","json:target/cucumber-json-report.json"},
					name={"Delete task"})

public class RunTaskManagementFeatureTest{
	
}
