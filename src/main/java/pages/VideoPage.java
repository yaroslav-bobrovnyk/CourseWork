package pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(VideoPage.class);

    public VideoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Selecting categories in the filter")
    public VideoCardDetailPage categoriesChoose(String category, String location, String language) {
        globalLoaderWait();
        oneTrustAcceptButton.click();
        moreFilterButton.click();
        logger.info("Click on more filter button");
        categoryFilter.click();
        globalLoaderWait();
        logger.info("Click on more category filter button");
        driver.findElement(By.xpath(String.format(checkbox,category))).click();
        globalLoaderWait();
        locationFilter.click();
        logger.info("Click on more location filter button");
        driver.findElement(By.xpath(String.format(checkbox,location))).click();
        globalLoaderWait();
        languageFilter.click();
        logger.info("Click on more language filter button");
        driver.findElement(By.xpath(String.format(checkbox,language))).click();
        globalLoaderWait();
        Allure.addAttachment("Chosen categories on the Video page", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        eventCard.click();
        logger.info("Open the event card");
        return new VideoCardDetailPage(driver);
    }

    @Step("Enters a keyword into the search box")
    public String keywordSearch(String keyword) {
        globalLoaderWait();
        searchField.click();
        searchField.sendKeys(keyword);
        logger.info("Write 'QA' value to the search field");
        globalLoaderWait();
        Allure.addAttachment("Speech search by keyword", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        return textTitle.getText();
    }
}
