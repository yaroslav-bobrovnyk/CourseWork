package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.Map;

public class VideoCardDetailPage extends Page{
    @FindBy(xpath="//label[contains(text(),\"Testing\")]")
    @CacheLookup
    public WebElement categoryCheck;

    @FindBy(css=".location span")
    @CacheLookup
    public WebElement locationCheck;

    @FindBy(css=".evnt-card-table .language span")
    @CacheLookup
    public WebElement languageCheck;

    public VideoCardDetailPage(WebDriver webDriver) {super(webDriver);}

    public Map<String,String> getData(){
        Map<String,String> data=new HashMap<>();
        data.put("category",categoryCheck.getText());
        data.put("location",locationCheck.getText());
        data.put("language",languageCheck.getText());
        return data;
    }
}
