package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

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

    public Map<String,String> getSelectedFilterData(){
        Map<String,String> selectedFilterData=new HashMap<>();
        selectedFilterData.put("category",categoryCheck.getText());
        selectedFilterData.put("location",locationCheck.getText());
        selectedFilterData.put("language",languageCheck.getText());
        return selectedFilterData;
    }
}
