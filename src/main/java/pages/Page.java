package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Page {
    @FindBy(css=".evnt-cards-container .evnt-card-wrapper")
    @CacheLookup public WebElement eventCard;

    @FindBy(xpath="//div[@data-group=\"Canada\"]//div[@data-toggle=\"collapse\"]")
    @CacheLookup public WebElement collapseWaiter;

    protected WebDriver driver;

    public Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected boolean waitForElement(WebElement element) {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.invisibilityOf(element));
    }

}