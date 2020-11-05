package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class VideoPage extends Page{
    @FindBy(xpath="(//div[@data-toggle=\"collapse\"]//span)[1]")
    @CacheLookup public WebElement moreFilterButton;

    @FindBy(id="filter_category")
    @CacheLookup public WebElement categoryFilter;

    @FindBy(id="filter_location")
    @CacheLookup public WebElement locationFilter;

    @FindBy(id="filter_language")
    @CacheLookup public WebElement languageFilter;

    @FindBy(css=".evnt-search-filter input")
    @CacheLookup public WebElement searchField;

    @FindBy(css=".evnt-talk-name span")
    @CacheLookup public WebElement textTitle;

    String checkbox = "//label[@data-value=\"%s\"]";

    public VideoPage(WebDriver webDriver) {
        super(webDriver);
    }

    public VideoCardDetailPage categoriesChoose(String category, String location, String language) {
        moreFilterButton.click();
        categoryFilter.click();
        driver.findElement(By.xpath(String.format(checkbox,category))).click();
        locationFilter.click();
        driver.findElement(By.xpath(String.format(checkbox,location))).click();
        waitForElement(globalLoader);
        languageFilter.click();
        driver.findElement(By.xpath(String.format(checkbox,language))).click();
        eventCard.click();
        return new VideoCardDetailPage(driver);
    }

    public String keywordSearch(String keyword) {
        searchField.click();
        searchField.sendKeys(keyword);
        waitForElement(globalLoader);
        return textTitle.getText();
    }
}
