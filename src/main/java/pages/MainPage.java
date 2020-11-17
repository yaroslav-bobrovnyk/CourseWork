package pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.io.ByteArrayInputStream;


public class MainPage extends Page{
    @FindBy(css=".nav-item.events-icon")
    @CacheLookup
    public WebElement eventNavButton;

    @FindBy(css=".talks-library-icon .nav-link")
    @CacheLookup
    public WebElement videoBavButton;

    public MainPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("The Events Page is open")
    public EventPage eventPageOpen(){
        globalLoaderWait();
        eventNavButton.click();
        waitForElement(globalLoader);
        Allure.addAttachment("Epam Events page", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        return new EventPage(driver);
    }

    @Step("The Video Page is open")
    public VideoPage videoPageOpen(){
        globalLoaderWait();
        videoBavButton.click();
        waitForElement(globalLoader);
        Allure.addAttachment("Epam Video page", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        return new VideoPage(driver);
    }
}
