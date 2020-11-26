package pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.io.ByteArrayInputStream;


public class MainPage extends Page{
    @FindBy(css=".events-icon")
    @CacheLookup
    public WebElement eventNavButton;

    @FindBy(css=".talks-library-icon")
    @CacheLookup
    public WebElement videoBavButton;

    private static final Logger logger = LogManager.getLogger(MainPage.class);

    public MainPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("The Events Page is open")
    public EventPage eventPageOpen(){
        globalLoaderWait();
        eventNavButton.click();
        logger.info("Event page is opened");
        globalLoaderWait();
        Allure.addAttachment("Epam Events page", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        return new EventPage(driver);
    }

    @Step("The Video Page is open")
    public VideoPage videoPageOpen(){
        globalLoaderWait();
        videoBavButton.click();
        logger.info("Video page is opened");
        globalLoaderWait();
        Allure.addAttachment("Epam Video page", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        return new VideoPage(driver);
    }
}