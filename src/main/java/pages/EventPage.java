package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EventPage extends Page{
    @FindBy(css=".evnt-upcoming-events .evnt-card-wrapper")
    @CacheLookup
    private List<WebElement> numberUpcomingEventsCard;

    @FindBy(xpath="(//li[@class=\"evnt-tab-item nav-item\"])[1]//span[last()]")
    @CacheLookup
    private WebElement counterUpcomingEvents;

    @FindBy(xpath="(//li[@class=\"evnt-tab-item nav-item\"])[2]//span[last()]")
    @CacheLookup
    private WebElement counterPastEvents;

    @FindBy(xpath="//div[@class=\"evnt-event-details-table\"]/div[1]/p")
    @CacheLookup
    private WebElement cardLocation;

    @FindBy(xpath="//div[@class=\"evnt-event-details-table\"]/div[2]/p")
    @CacheLookup
    private WebElement cardLanguage;

    @FindBy(css=".evnt-event-name span")
    @CacheLookup
    private WebElement cardEventTitle;

    @FindBy(css=".evnt-event-dates-table .date")
    @CacheLookup
    private WebElement cardEventDate;

    @FindBy(css=".evnt-event-dates-table .status")
    @CacheLookup
    private WebElement cardEventStatus;

    @FindBy(css=".speakers-wrapper")
    @CacheLookup
    private WebElement cardSpeakerList;

    @FindBy(xpath="(//div[@class=\"evnt-events-tabs-nav\"]//li[@class=\"evnt-tab-item nav-item\"])[2]")
    @CacheLookup
    private WebElement pastEventsTab;

    @FindBy(id="filter_location")
    @CacheLookup
    private WebElement filterLocation;

    @FindBy(xpath="//label[@data-value=\"Canada\"]")
    @CacheLookup
    private WebElement canadaCheckbox;

    @FindBy(css=".evnt-events-row a")
    @CacheLookup
    private WebElement upcomingEventCard;






    public EventPage(WebDriver webDriver) {
        super(webDriver);
    }

}