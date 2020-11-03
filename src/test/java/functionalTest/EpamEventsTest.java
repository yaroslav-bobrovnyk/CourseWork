package functionalTest;

import config.ServerConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.exparity.hamcrest.date.DateMatchers;
import org.exparity.hamcrest.date.Moments;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.EventPage;
import pages.MainPage;


import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.exparity.hamcrest.date.DateMatchers.after;




public class EpamEventsTest {
    static WebDriver driver;
    private static Logger logger = LogManager.getLogger(EpamEventsTest.class);
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);
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
        assertThat(actualNumberOfCards ,greaterThan(0));
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

    public void upcomingEventsDateValidationTest() throws ParseException {
        EventPage eventPage=new EventPage(driver);
        mainPage.eventPageOpen();
        assertThat(eventPage.getDateFromCard(), after(Moments.today()));
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
