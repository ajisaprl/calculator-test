package stepsDefinition;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.*;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    public void iEnterFollowingValuesAndPressEnterButton(DataTable args) {
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
    public void iShouldBeAbleToSee(DataTable result) throws IOException {
        Map<String, String> expect = result.asMap(String.class, String.class);
        String expected = expect.get("expected");

        File screenshot = canvas.getScreenshotAs(OutputType.FILE);
        BufferedImage fullImg = ImageIO.read(screenshot);

        BufferedImage eleScreenshot= fullImg.getSubimage(25, 26, 354, 48);
        ImageIO.write(eleScreenshot, "png", screenshot);

        File screenshotLocation = new File("/Users/bukalapak/Documents/qa-assessment-xendit/result.png");
        FileHandler.copy(screenshot, screenshotLocation);

        //OCR
        Tesseract tesseract = new Tesseract();
        tesseract.setTessVariable("user_defined_dpi", "270");
        String actual = null;
        try {
            tesseract.setDatapath("/Users/bukalapak/Documents/qa-assessment-xendit");
            String scanned = tesseract.doOCR(new File("/Users/bukalapak/Documents/qa-assessment-xendit/result.png"));
            actual = scanned.substring(0, scanned.length() - 1);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        assertEquals(actual,expected);
    }

    @And("^user close the browser$")
    public void userCloseTheBrowser() {
        driver.close();
    }
}
