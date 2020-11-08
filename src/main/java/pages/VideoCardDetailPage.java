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
import java.util.HashMap;
import java.util.Map;

public class VideoCardDetailPage extends Page{
    @FindBy(xpath="//label[contains(text(),\"Testing\")]")
    @CacheLookup public WebElement categoryCheck;

    @FindBy(css=".location span")
    @CacheLookup public WebElement locationCheck;

    @FindBy(css=".evnt-card-table .language span")
    @CacheLookup public WebElement languageCheck;

    public VideoCardDetailPage(WebDriver webDriver) {super(webDriver);}

    @Step("Get the data from the filter")
    public Map<String,String> getSelectedFilterData(){
        Map<String,String> selectedFilterData=new HashMap<>();
        selectedFilterData.put("category",categoryCheck.getText());
        selectedFilterData.put("location",locationCheck.getText());
        selectedFilterData.put("language",languageCheck.getText());
        Allure.addAttachment("Video card details page", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        return selectedFilterData;
    }
}
