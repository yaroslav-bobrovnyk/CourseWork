package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class VideoPage extends Page{
    @FindBy(xpath="(//div[@data-toggle=\"collapse\"]//span)[1]")
    @CacheLookup
    private WebElement moreFilterButton;

    @FindBy(id="filter_category")
    @CacheLookup
    private WebElement categoryFilter;

    @FindBy(xpath="//div[@class=\"evnt-filter-item\" and @data-group=\"Testing\"]")
    @CacheLookup
    private WebElement testingCheckbox;

    @FindBy(id="filter_location")
    @CacheLookup
    private WebElement locationFilter;

    @FindBy(xpath="//div[@class=\"evnt-filter-item\" and @data-group=\"Belarus\"]")
    @CacheLookup
    private WebElement belarusCheckbox;

    @FindBy(id="filter_language")
    @CacheLookup
    private WebElement languageFilter;

    @FindBy(xpath="//label[@class=\"form-check-label\" and @data-value=\"ENGLISH\"]")
    @CacheLookup
    private WebElement englishLanguageCheckbox;

    @FindBy(css=".evnt-search-filter input")
    @CacheLookup
    private WebElement searchField;

    @FindBy(css=".evnt-talk-name span")
    @CacheLookup
    private WebElement textTitle;

    public VideoPage(WebDriver webDriver) {
        super(webDriver);
    }

}
