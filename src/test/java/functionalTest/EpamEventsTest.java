package functionalTest;

import config.ServerConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.exparity.hamcrest.date.Moments;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
    static WebDriver driver;
    private static final Logger logger = LogManager.getLogger(EpamEventsTest.class);
    private final ServerConfig cfg = ConfigFactory.create(ServerConfig.class);
    private static MainPage mainPage;

    @BeforeEach
    public void setUp() {
//        String browser = System.getProperty("browser");
//        logger.info(browser);
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        mainPage=new MainPage(driver);
        driver.get(cfg.url());
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
        assertThat(eventPage.getCardLanguage(), is(not(emptyString())));
        assertThat(eventPage.getCardEventTitle(), is(not(emptyString())));
        assertThat(eventPage.getCardEventDate(), is(not(emptyString())));
        assertThat(eventPage.getEventStatus(), is(not(emptyString())));
        assertThat(eventPage.getCardSpeaker(),greaterThan(0));
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
        assertThat(actualNumberOfCards, equalTo(numberOfCardsOnCounter));
        assertThat(eventCardDate, before(Moments.today()));
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
        assertThat(selectedFilterData.get("location"),containsString(location));
        assertThat(selectedFilterData.get("language"),equalTo(language));
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

    }

    @AfterEach
    public void setDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
