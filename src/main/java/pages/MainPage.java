package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;


public class MainPage extends Page{
    @FindBy(css=".events-icon .nav-link")
    @CacheLookup
    private WebElement eventNavButton;
    @FindBy(css=".talks-library-icon .nav-link")
    @CacheLookup
    private WebElement videoBavButton;

    public MainPage(WebDriver webDriver) {
        super(webDriver);
    }

    public EventPage eventPageOpen(){
        eventNavButton.click();
        return new EventPage(driver);
    }

    public VideoPage videoPageOpen(){
        eventNavButton.click();
        return new VideoPage(driver);
    }

}
