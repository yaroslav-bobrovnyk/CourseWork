package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;


public class EventCardDetailsPage extends Page {
    @FindBy(css=".evnt-content-wrapper")
    @CacheLookup
    public WebElement informationBlock;

    public EventCardDetailsPage(WebDriver webDriver) {super(webDriver);}

    public boolean doInformationBlockDisplay(){
        return informationBlock.isDisplayed();
    }
}
