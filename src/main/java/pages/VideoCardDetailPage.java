package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class VideoCardDetailPage extends Page{
    @FindBy(xpath="//label[contains(text(),\"Testing\")]")
    @CacheLookup
    private WebElement categoryCheck;

    @FindBy(css=".location span")
    @CacheLookup
    private WebElement locationCheck;

    @FindBy(css=".evnt-card-table .language span")
    @CacheLookup
    private WebElement languageCheck;

    public VideoCardDetailPage(WebDriver webDriver) {super(webDriver);}
}
