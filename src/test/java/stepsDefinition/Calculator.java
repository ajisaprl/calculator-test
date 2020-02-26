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
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(webDriver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete"));
    }

    @When("^I enter following values for \"([^\"]*)\" operation$")
    public void iEnterFollowingValues(String operation, DataTable args) {
        iframe = driver.findElement(By.id("fullframe"));
        driver.switchTo().frame(iframe);

        Actions builder = new Actions(driver);
        canvas = driver.findElement(By.id("canvas"));

        Map<String, String> v1 = args.asMap(String.class, String.class);
        Map<String, String> v2 = args.asMap(String.class, String.class);
        Map<String, String> op = args.asMap(String.class, String.class);

        Action enterValue1 = builder.sendKeys(canvas, v1.get("value1")).build();
        enterValue1.perform();

        if (operation.equals("basic")) {
            Action operator = builder.sendKeys(canvas, op.get("operator")).build();
            operator.perform();
            Action enterValue2 = builder.sendKeys(canvas, v2.get("value2")).build();
            enterValue2.perform();
        } else {
            String button = op.get("button");
            if (button.equals("DELETE")) {
                Action del = builder.sendKeys(canvas, Keys.BACK_SPACE).build();
                del.perform();
            } else {
                Action btn = builder.sendKeys(canvas, button).build();
                btn.perform();
            }
        }
    }

    @Then("^I should be able to see the right result$")
    public void iShouldBeAbleToSee(DataTable result) throws IOException {
        Map<String, String> expect = result.asMap(String.class, String.class);
        String expected = expect.get("expected");
        takeScreenShot("result");
        assertEquals(expected,recognizeImage("result"));
    }

    @And("^I press \"([^\"]*)\" button$")
    public void iPressButton(String button) {
        Actions builder = new Actions(driver);
        switch (button) {
            case "ENTER":
                builder.sendKeys(canvas, Keys.ENTER).build().perform();
                break;
            case "C":
                builder.sendKeys(canvas, "C").build().perform();
                break;
            case "DELETE":
                builder.sendKeys(canvas, Keys.BACK_SPACE).build().perform();
                break;
            default:
                break;
        }
    }

    @And("^user close the browser$")
    public void userCloseTheBrowser() {
        driver.close();
    }

    @Then("^The result should reset$")
    public void theResultShouldReset() throws IOException {
        takeScreenShot("result");
        assertEquals("0",recognizeImage("result"));
        takeScreenShot("button");
        assertEquals("CE",recognizeImage("button"));
    }

    public String recognizeImage(String position) {
        Tesseract tesseract = new Tesseract();
        tesseract.setTessVariable("user_defined_dpi", "270");
        String actual = "";
        try {
            String scanned;
            if (position.equals("result")) {
                scanned = tesseract.doOCR(new File("result.png"));
            } else {
                scanned = tesseract.doOCR(new File("button.png"));
            }
            actual = scanned.substring(0, scanned.length() - 1);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return actual;
    }

    public void takeScreenShot(String position) throws IOException {
        File screenshot = canvas.getScreenshotAs(OutputType.FILE);
        BufferedImage fullImg = ImageIO.read(screenshot);
        BufferedImage croppedScreenShot;
        File screenshotLocation;

        if (position.equals("result")) {
            //this varies based on window resolution
            croppedScreenShot = fullImg.getSubimage(32, 32, 374, 50);
            screenshotLocation = new File("result.png");
        } else {
            croppedScreenShot = fullImg.getSubimage(351, 139, 56, 50);
            screenshotLocation = new File("button.png");
        }

        ImageIO.write(croppedScreenShot, "png", screenshot);
        FileHandler.copy(screenshot, screenshotLocation);
    }

}
