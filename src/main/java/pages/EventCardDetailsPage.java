package pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.io.ByteArrayInputStream;


public class EventCardDetailsPage extends Page {
    @FindBy(css=".evnt-content-wrapper")
    @CacheLookup
    public WebElement informationBlock;

    public EventCardDetailsPage(WebDriver webDriver) { super(webDriver); }

    @Step("Does the information block display on the event card detail page")
    public boolean doesInformationBlockDisplay(){
        waitForElement(globalLoader);
        Allure.addAttachment("Event card details page", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        return informationBlock.isDisplayed();
    }
}
