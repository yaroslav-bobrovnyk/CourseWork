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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

public class EventPage extends Page{
    @FindBy(css=".evnt-upcoming-events .evnt-card-wrapper")
    @CacheLookup public List<WebElement> numberUpcomingEventsCard;

    @FindBy(css=".evnt-events-column")
    @CacheLookup public List<WebElement> numberPastEventsCard;

    @FindBy(xpath="(//li[@class=\"evnt-tab-item nav-item\"])[1]//span[last()]")
    @CacheLookup public WebElement counterUpcomingEvents;

    @FindBy(xpath="(//li[@class=\"evnt-tab-item nav-item\"])[2]//span[last()]")
    @CacheLookup public WebElement counterPastEvents;

    @FindBy(xpath="//div[@class=\"evnt-event-details-table\"]/div[1]/p")
    @CacheLookup public WebElement cardLocation;

    @FindBy(xpath="//div[@class=\"evnt-event-details-table\"]/div[2]/p")
    @CacheLookup public WebElement cardLanguage;

    @FindBy(css=".evnt-event-name span")
    @CacheLookup public WebElement cardEventTitle;

    @FindBy(css=".evnt-event-dates-table .date")
    @CacheLookup public WebElement cardEventDate;

    @FindBy(css=".evnt-event-dates-table .status")
    @CacheLookup public WebElement cardEventStatus;

    @FindBy(xpath="(//div[@class=\"evnt-events-row\"])[1]//div[@class=\"evnt-speaker\"]")
    @CacheLookup public List<WebElement> cardSpeakerList;

    @FindBy(xpath="(//div[@class=\"evnt-events-tabs-nav\"]//li[@class=\"evnt-tab-item nav-item\"])[2]")
    @CacheLookup public WebElement pastEventsTab;

    @FindBy(id="filter_location")
    @CacheLookup public WebElement filterLocation;

    @FindBy(xpath="//label[@data-value=\"Canada\"]")
    @CacheLookup public WebElement canadaCheckbox;

    public EventPage(WebDriver webDriver) { super(webDriver); }

    @Step("Get the card number value of Upcoming Events")
    public int cardNumberOfUpcomingEvents(){
        int actualNumberOfCards=numberUpcomingEventsCard.size();
        assertThat(actualNumberOfCards,greaterThan(0));
        Allure.addAttachment("Upcoming Events cards on Events page", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        return actualNumberOfCards;
    }

    @Step("Get the number from the Upcoming Events counter")
    public int counterNumberOfUpcomingEvents(){ return Integer.parseInt(counterUpcomingEvents.getText()); }

    @Step("Get the number from the Past Events counter")
    public int counterNumberOfPastEvents(){ return Integer.parseInt(counterPastEvents.getText()); }

    @Step("Get the location of the event from the card")
    public String getCardLocation(){
        Allure.addAttachment("Card information about Event", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        return cardLocation.getText(); }

    @Step("Get the language of the event from the card")
    public String getCardLanguage(){
        return cardLanguage.getText();
    }

    @Step("Get the title of the event from the card")
    public String getCardEventTitle(){
        return cardEventTitle.getText();
    }

    @Step("Get the date of the event from the card")
    public String getCardEventDate(){
        return cardEventDate.getText();
    }

    @Step("Get the status of the event from the card")
    public String getEventStatus(){
        return cardEventStatus.getText();
    }

    @Step("Get the speaker list of the event from the card")
    public int getCardSpeaker(){ return cardSpeakerList.size(); }

    @Step("Get the date from the event card")
    public Date getDateFromCard() throws ParseException {
        if (getCardEventDate().contains("-")){
            return new SimpleDateFormat(("dd MMM yyyy")).parse(getCardEventDate()
                    .substring(getCardEventDate().indexOf("-")+1).trim());
        }
        Allure.addAttachment("Date from the event card", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        return new SimpleDateFormat("dd MMM yyyy").parse(getCardEventDate());
    }

    @Step("Get the card number value of Past Events")
    public int cardNumberOfPastEvents(){
        pastEventsTab.click();
        waitForElement(globalLoader);
        filterLocation.click();
        waitForElement(globalLoader);
        canadaCheckbox.click();
        waitForElement(globalLoader);
        Allure.addAttachment("Past Events cards on Events page", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        return numberPastEventsCard.size();
    }

    @Step("Open Event Card details page")
    public EventCardDetailsPage eventCardOpen(){
        if (globalLoader.isEnabled()){
            waitForElement(globalLoader);
        }
        eventCard.click();
        return new EventCardDetailsPage(driver);
    }
}