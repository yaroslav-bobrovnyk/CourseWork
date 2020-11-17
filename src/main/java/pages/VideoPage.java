package pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.io.ByteArrayInputStream;

public class VideoPage extends Page{
    @FindBy(css=".show-more")
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

    @Step("Selecting categories in the filter")
    public VideoCardDetailPage categoriesChoose(String category, String location, String language) {
        globalLoaderWait();
        moreFilterButton.click();
        categoryFilter.click();
        driver.findElement(By.xpath(String.format(checkbox,category))).click();
        waitForElement(globalLoader);
        locationFilter.click();
        driver.findElement(By.xpath(String.format(checkbox,location))).click();
        waitForElement(globalLoader);
        languageFilter.click();
        driver.findElement(By.xpath(String.format(checkbox,language))).click();
        waitForElement(globalLoader);
        Allure.addAttachment("Chosen categories on the Video page", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        eventCard.click();
        return new VideoCardDetailPage(driver);
    }

    @Step("Enters a keyword into the search box")
    public String keywordSearch(String keyword) {
        //globalLoaderWait();
        searchField.click();
        searchField.sendKeys(keyword);
        waitForElement(globalLoader);
        Allure.addAttachment("Speech search by keyword", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        return textTitle.getText();
    }
}
