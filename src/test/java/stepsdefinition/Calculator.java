package stepsdefinition;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Map;


public class Calculator {

    WebDriver driver;
    Robot bot;

    @Given("^Open chrome browser and start application$")
    public void openChromeBrowserAndStartApplication() {
        System.setProperty("webdriver.chrome.driver", "/Users/bukalapak/Documents/qa-assessment-xendit/chromedrivertest");
        driver = new ChromeDriver();
        driver.get("https://www.online-calculator.com/full-screen-calculator/");
        driver.switchTo().frame("fullframe");

        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(webDriver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete"));
    }

    @When("^I enter following values and press CE button$")
    public void iEnterFollowingValuesAndPressCEButton(DataTable value) {
        driver.getWindowHandle();
        Map<String, String> v = value.asMap(String.class, String.class);
        String value1 = v.get("value1");
        try
        {
            bot = new Robot();
        }
        catch (AWTException e)
        {
            e.printStackTrace();
        }

        bot.delay(250);
        switch (value1) {
            case "1":
                bot.keyPress(KeyEvent.VK_NUMPAD1);
                bot.keyRelease(KeyEvent.VK_NUMPAD1);
                break;
            case "2":
                bot.keyPress(KeyEvent.VK_NUMPAD2);
                bot.keyRelease(KeyEvent.VK_NUMPAD2);
                break;
            default:
                break;
        }

    }

}
