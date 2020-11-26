package pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(EventPage.class);

    public EventPage(WebDriver webDriver) { super(webDriver); }

    @Step("Get the card number value of Upcoming Events")
    public int cardNumberOfUpcomingEvents(){
        int actualNumberOfCards=numberUpcomingEventsCard.size();
        logger.info("Get actual number of cards on the page");
        assertThat(actualNumberOfCards,greaterThan(0));
        logger.info("Assert that page displays cards for upcoming events");
        Allure.addAttachment("Upcoming Events cards on Events page", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        return actualNumberOfCards;
    }

    @Step("Get the number from the Upcoming Events counter")
    public int counterNumberOfUpcomingEvents(){
        logger.info("Return the number of card from the counter");
        return Integer.parseInt(counterUpcomingEvents.getText());
    }

    @Step("Get the number from the Past Events counter")
    public int counterNumberOfPastEvents(){
        logger.info("Return the number of card from the counter");
        return Integer.parseInt(counterPastEvents.getText());
    }

    @Step("Get the location of the event from the card")
    public String getCardLocation(){
        logger.info("Get card location");
        Allure.addAttachment("Card information about Event", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        return cardLocation.getText(); }

    @Step("Get the language of the event from the card")
    public String getCardLanguage(){
        logger.info("Get card language");
        return cardLanguage.getText();
    }

    @Step("Get the title of the event from the card")
    public String getCardEventTitle(){
        logger.info("Get card event title");
        return cardEventTitle.getText();
    }

    @Step("Get the date of the event from the card")
    public String getCardEventDate(){
        logger.info("Get card event date");
        return cardEventDate.getText();
    }

    @Step("Get the status of the event from the card")
    public String getEventStatus(){
        logger.info("Get card event status");
        return cardEventStatus.getText();
    }

    @Step("Get the speaker list of the event from the card")
    public int getCardSpeaker(){
        logger.info("Get card number of speakers");
        return cardSpeakerList.size();
    }

    @Step("Get the date from the event card")
    public Date getDateFromCard() throws ParseException {
        if (getCardEventDate().contains("-")){
            return new SimpleDateFormat(("dd MMM yyyy")).parse(getCardEventDate()
                    .substring(getCardEventDate().indexOf("-")+1).trim());
        }
        logger.info("Get the date from the event card");
        Allure.addAttachment("Date from the event card", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        return new SimpleDateFormat("dd MMM yyyy").parse(getCardEventDate());
    }

    @Step("Get the card number value of Past Events")
    public int cardNumberOfPastEvents(){
        pastEventsTab.click();
        waitForElement(globalLoader);
        logger.info("Click on the Past Event tab");
        filterLocation.click();
        waitForElement(globalLoader);
        logger.info("Click on the location filter");
        canadaCheckbox.click();
        waitForElement(globalLoader);
        logger.info("Click on the Canada checkbox");
        Allure.addAttachment("Past Events cards on Events page", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        logger.info("Return the card number value of Past Events");
        return numberPastEventsCard.size();
    }

    @Step("Open Event Card details page")
    public EventCardDetailsPage eventCardOpen(){
        globalLoaderWait();
        eventCard.click();
        logger.info("Click on the Event Card");
        return new EventCardDetailsPage(driver);
    }
}