package stepsdefinition;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;


public class Calculator {

    WebDriver driver;
    WebElement iframe;
    WebElement canvas;

    @Given("^Open chrome browser and start application$")
    public void openChromeBrowserAndStartApplication() {
        System.setProperty("webdriver.chrome.driver", "/Users/bukalapak/Documents/qa-assessment-xendit/chromedrivertest");
        driver = new ChromeDriver();
        driver.get("https://www.online-calculator.com/full-screen-calculator/");

        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(webDriver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete"));
    }

    @When("^I enter following values and press enter button$")
    public void iEnterFollowingValuesAndPressEnterButton(DataTable args) throws InterruptedException {
        iframe = driver.findElement(By.id("fullframe"));
        driver.switchTo().frame(iframe);

        Actions builder = new Actions(driver);
        canvas = driver.findElement(By.id("canvas"));

        Map<String, String> v1 = args.asMap(String.class, String.class);
        Map<String, String> v2 = args.asMap(String.class, String.class);
        Map<String, String> op = args.asMap(String.class, String.class);

        Action enterValue1 = builder.sendKeys(canvas, v1.get("value1")).build();
        enterValue1.perform();
        Action operator = builder.sendKeys(canvas, op.get("operator")).build();
        operator.perform();
        Action enterValue2 = builder.sendKeys(canvas, v2.get("value2")).build();
        enterValue2.perform();
        Action pressEnter = builder.sendKeys(Keys.ENTER).build();
        pressEnter.perform();
    }

    @Then("^I should be able to see$")
    public void iShouldBeAbleToSee(DataTable result) {
        Map<String, String> expected = result.asMap(String.class, String.class);
        String res = expected.get("expected");
        canvas = driver.findElement(By.id("canvas"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String pngData = js.executeScript("return arguments[0].toDataURL('/image/png').substring(22);", canvas).toString();
        System.out.println(pngData);

        byte[] canvas_png = Base64.getDecoder().decode(pngData);
        FileOutputStream fos = null;
        try {
            File imgFile = new File("image");
            fos = new FileOutputStream(imgFile);
            fos.write(canvas_png);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
