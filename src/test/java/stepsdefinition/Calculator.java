package stepsdefinition;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class Calculator {
    @Given("^Open chrome browser and start application$")
    public void openChromeBrowserAndStartApplication() {
        System.setProperty("webdriver.chrome.driver", "/Users/bukalapak/Documents/qa-assessment-xendit/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.online-calculator.com/full-screen-calculator/");
    }

    @When("^I enter following values and press CE button$")
    public void iEnterFollowingValuesAndPressCEButton(String value) {

    }

}
