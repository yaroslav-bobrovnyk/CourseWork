package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
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

    @FindBy(css=".evnt-global-loader")
    @CacheLookup public WebElement globalLoader;

    protected WebDriver driver;
    private static final Logger logger = LogManager.getLogger(Page.class);


    public Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected boolean waitForElement(WebElement element) {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.invisibilityOf(element));
    }

    protected void globalLoaderWait(){
        try {
            if (globalLoader.isEnabled()) {
                waitForElement(globalLoader);
            }
        }catch (StaleElementReferenceException | NoSuchElementException exception){
            logger.error("There is no global loader");
        }
    }

}