package functionalTest;

import config.ServerConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.exparity.hamcrest.date.Moments;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.EventPage;
import pages.MainPage;
import pages.VideoCardDetailPage;


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

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        mainPage=new MainPage(driver);
        driver.get(cfg.url());
    }

    @Test
    public void upcomingEventsReviewTest(){
        EventPage eventPage=new EventPage(driver);
        int actualNumberOfCards=mainPage.eventPageOpen().cardNumberOfUpcomingEvents();
        int numberOfCardsOnCounter=eventPage.counterNumberOfUpcomingEvents();
        assertThat(actualNumberOfCards, equalTo(numberOfCardsOnCounter));
    }

    @Test
    public void upcomingEventsCardReviewTest() {
        EventPage eventPage=new EventPage(driver);
        mainPage.eventPageOpen();
        assertThat(eventPage.getCardLocation(), is(not(emptyString())));
        assertThat(eventPage.getCardLanguage(), is(not(emptyString())));
        assertThat(eventPage.getCardEventTitle(), is(not(emptyString())));
        assertThat(eventPage.getCardEventDate(), is(not(emptyString())));
        assertThat(eventPage.getEventStatus(), is(not(emptyString())));
        assertThat(eventPage.getCardSpeaker(),greaterThan(0));
    }

    @Test
    public void upcomingEventsDateValidationTest() throws ParseException {
        Date date=mainPage.eventPageOpen().getDateFromCard();
        assertThat(date, sameOrAfter(Moments.today()));
    }

    @Test
    public void pastEventsReviewTest() throws ParseException {
        EventPage eventPage=new EventPage(driver);
        int actualNumberOfCards=mainPage.eventPageOpen()
                .pastEventTabClick()
                .locationFilterClick()
                .canadaCheckboxChoose().cardNumberOfPastEvents();
        int numberOfCardsOnCounter=eventPage.counterNumberOfPastEvents();
        Date date=eventPage.getDateFromCard();
        assertThat(actualNumberOfCards ,greaterThan(0));
        assertThat(actualNumberOfCards, equalTo(numberOfCardsOnCounter));
        assertThat(date, before(Moments.today()));
    }

    @Test
    public void cardDetailInformationReviewTest(){
        boolean informationDisplayBlock =mainPage.eventPageOpen().eventCardOpen().doInformationBlockDisplay();
        assertThat(informationDisplayBlock,equalTo(true));
    }

    @Test
    public void categoryFilterTest() {
        String category="Testing";
        String location="Belarus";
        String language="ENGLISH";
        mainPage.videoPageOpen().categoriesChoose(category,location,language);
        VideoCardDetailPage videoCardDetailPage=new VideoCardDetailPage(driver);
        System.out.println(driver.getCurrentUrl());
        Map <String,String> data=videoCardDetailPage.getData();
        System.out.println(data.get("location"));
        assertThat(data.get("category"),equalTo(category));
        assertThat(data.get("location"),containsString(location));
        assertThat(data.get("language"),equalTo(language));
    }

    @Test
    public void reportSearchByKeywordTest() {
        String keyword="QA";
        String reportTitle=mainPage.videoPageOpen().keywordSearch(keyword);
        assertThat(reportTitle,containsString(keyword));

    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
