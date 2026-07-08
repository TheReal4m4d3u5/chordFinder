package chordfinder;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@SuppressWarnings("deprecation")
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/feature",
    glue = "chordfinder",
    plugin = {"pretty"}
)
public class RunCucumberTest {
}