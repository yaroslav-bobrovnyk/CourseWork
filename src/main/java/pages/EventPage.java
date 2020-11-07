package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

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

    public int cardNumberOfUpcomingEvents(){
        int actualNumberOfCards=numberUpcomingEventsCard.size();
        assertThat(actualNumberOfCards,greaterThan(0));
        return actualNumberOfCards;
    }

    public int counterNumberOfUpcomingEvents(){ return Integer.parseInt(counterUpcomingEvents.getText()); }

    public int counterNumberOfPastEvents(){ return Integer.parseInt(counterPastEvents.getText()); }

    public String getCardLocation(){ return cardLocation.getText(); }

    public String getCardLanguage(){
        return cardLanguage.getText();
    }

    public String getCardEventTitle(){
        return cardEventTitle.getText();
    }

    public String getCardEventDate(){
        return cardEventDate.getText();
    }

    public String getEventStatus(){
        return cardEventStatus.getText();
    }
    
    public int getCardSpeaker(){ return cardSpeakerList.size(); }

    public Date getDateFromCard() throws ParseException {
        if (getCardEventDate().contains("-")){
            return new SimpleDateFormat(("dd MMM yyyy")).parse(getCardEventDate()
                    .substring(getCardEventDate().indexOf("-")+1).trim());
        }
        return new SimpleDateFormat("dd MMM yyyy").parse(getCardEventDate());
    }

    public int cardNumberOfPastEvents(){
        pastEventsTab.click();
        filterLocation.click();
        canadaCheckbox.click();
        waitForElement(globalLoader);
        return numberPastEventsCard.size();
    }


    public EventCardDetailsPage eventCardOpen(){
        eventCard.click();
        return new EventCardDetailsPage(driver);
    }
}