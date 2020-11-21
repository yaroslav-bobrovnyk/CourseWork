package functionalTest;

import config.ServerConfig;
import factory.WebFactory;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.exparity.hamcrest.date.Moments;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.EventPage;
import pages.MainPage;


import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.exparity.hamcrest.date.DateMatchers.*;

public class EpamEventsTest {
    WebDriver driver;
    private final ServerConfig cfg = ConfigFactory.create(ServerConfig.class);
    private static final Logger logger = LogManager.getLogger(EpamEventsTest.class);
    private static MainPage mainPage;

    @SneakyThrows
    @BeforeEach
    public void setUp() {
        String browser = System.getProperty("browser");
        if (browser==null){
            browser="chrome";
        }
        driver=WebFactory.create(WebFactory.Browsers.valueOf(browser.toUpperCase()));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        mainPage=new MainPage(driver);
        driver.get(cfg.url());
        logger.info("Events EPAM application was opened");
    }

    @Test
    @Epic("Epam Events")
    @Feature("Mandatory test coverage")
    @Story("Viewing Upcoming Events")
    @Description("This test verifies that cards are displayed on Upcoming Events tab and number of cards are equal counter on Upcoming Events button")
        public void upcomingEventsReviewTest(){
        EventPage eventPage=new EventPage(driver);
        int actualNumberOfCards=mainPage.eventPageOpen().cardNumberOfUpcomingEvents();
        int numberOfCardsOnCounter=eventPage.counterNumberOfUpcomingEvents();
        assertThat(actualNumberOfCards, equalTo(numberOfCardsOnCounter));
        logger.info("Number of cards are equal number on counter");
    }

    @Test
    @Epic("Epam Events")
    @Feature("Mandatory test coverage")
    @Story("Viewing Event Cards")
    @Description("This test verifies that cards are displayed on Upcoming Events tab " +
            "and card contains information about the event: location, language, title , date, registration information, speaker list")
    public void upcomingEventsCardReviewTest() {
        EventPage eventPage=new EventPage(driver);
        mainPage.eventPageOpen().cardNumberOfUpcomingEvents();
        assertThat(eventPage.getCardLocation(), is(not(emptyString())));
        logger.info("Card contains information about the location");
        assertThat(eventPage.getCardLanguage(), is(not(emptyString())));
        logger.info("Card contains information about the language");
        assertThat(eventPage.getCardEventTitle(), is(not(emptyString())));
        logger.info("Card contains information about the event title");
        assertThat(eventPage.getCardEventDate(), is(not(emptyString())));
        logger.info("Card contains information about the event date");
        assertThat(eventPage.getEventStatus(), is(not(emptyString())));
        logger.info("Card contains information about the event status");
        assertThat(eventPage.getCardSpeaker(),greaterThan(0));
        logger.info("Card contains information about the speakers");
    }

    @Test
    @Epic("Epam Events")
    @Feature("The order of the displayed blocks with information in the event card")
    @Story("Date validation of the Upcoming Events")
    @Description("This test verifies that cards are displayed on Upcoming Events tab " +
            "and in the This week block, the dates of the events are greater than or equal to the current date and are within the current week")
    public void upcomingEventsDateValidationTest() throws ParseException {
        Date date=mainPage.eventPageOpen().getDateFromCard();
        assertThat(date, sameOrAfter(Moments.today()));
        logger.info("Dates of the events are greater than or equal to the current date and are within the current week");
    }

    @Test
    @Epic("Epam Events")
    @Feature("The order of the displayed blocks with information in the event card")
    @Story("Viewing Past Events in Canada")
    @Description("This test verifies that cards are displayed on Past Events tab " +
            "and the dates of the events are less than the current date")
    public void pastEventsReviewTest() throws ParseException {
        EventPage eventPage=new EventPage(driver);
        int actualNumberOfCards=mainPage.eventPageOpen().cardNumberOfPastEvents();
        int numberOfCardsOnCounter=eventPage.counterNumberOfPastEvents();
        Date eventCardDate=eventPage.getDateFromCard();
        assertThat(actualNumberOfCards ,greaterThan(0));
        logger.info("Cards are displayed on Past Events tab");
        assertThat(actualNumberOfCards, equalTo(numberOfCardsOnCounter));
        logger.info("Cards are equals number on counter");
        assertThat(eventCardDate, before(Moments.today()));
        logger.info("Dates of the events are less than the current date");
    }

    @Test
    @Epic("Epam Events")
    @Feature("The order of the displayed blocks with information in the event card")
    @Story("Viewing detailed information about the event")
    @Description("This test verifies that cards are displayed on Upcoming Events tab " +
            "and on the page with information about the event, a block with a button for registration, date and time, event program is displayed")
    public void cardDetailInformationReviewTest(){
        boolean informationDisplayBlock =mainPage.eventPageOpen().eventCardOpen().doesInformationBlockDisplay();
        assertThat(informationDisplayBlock,equalTo(true));
        logger.info("Information block is displayed");
    }

    @Test
    @Epic("Epam Events")
    @Feature("The order of the displayed blocks with information in the event card")
    @Story("Filtering reports by category")
    @Description("The page displays cards that match the rules of the selected filters")
    public void categoryFilterTest() {
        String category="Testing";
        String location="Belarus";
        String language="ENGLISH";
        Map <String,String> selectedFilterData=mainPage.videoPageOpen()
                .categoriesChoose(category,location,language).getSelectedFilterData();
        assertThat(selectedFilterData.get("category"),equalTo(category));
        logger.info("Cards match the rules of the category filter");
        assertThat(selectedFilterData.get("location"),containsString(location));
        logger.info("Cards match the rules of the location filter");
        assertThat(selectedFilterData.get("language"),equalTo(language));
        logger.info("Cards match the rules of the language filter");
    }

    @Test
    @Epic("Epam Events")
    @Feature("The order of the displayed blocks with information in the event card")
    @Story("Speech search by keyword")
    @Description("The page displays reports containing the search keyword in the title")
    public void reportSearchByKeywordTest() {
        String keyword="QA";
        String reportTitle=mainPage.videoPageOpen().keywordSearch(keyword);
        assertThat(reportTitle,containsString(keyword));
        logger.info("Cards contain the search keyword in the title");
    }

    @AfterEach
    public void setDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
