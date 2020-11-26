package factory;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class WebFactory {

    private static final Logger logger = LogManager.getLogger(WebFactory.class);

    public enum Browsers{
        CHROME,
        FIREFOX,
        OPERA
    }

    @SneakyThrows
    public static WebDriver create(Browsers browsers){
        String slenoidURL = "http://localhost:4444/wd/hub";
        DesiredCapabilities caps = new DesiredCapabilities();
        switch (browsers){
            case CHROME: {
                caps.setBrowserName("chrome");
                caps.setVersion("85.0");
                logger.info("Chrome docker image was successfully up");
                break;
            }
            case OPERA: {
                caps.setBrowserName("opera");
                caps.setVersion("70.0");
                logger.info("Opera docker image was successfully up");
                break;
            }
            case FIREFOX: {
                caps.setBrowserName("firefox");
                caps.setVersion("82.0");
                logger.info("Firefox docker image was successfully up");
                break;
            }
            default:{
                caps.setBrowserName("chrome");
                caps.setVersion("85.0");
                logger.info("Chrome docker image was successfully up");
            }
        }
        caps.setCapability("enableVNC", true);
        caps.setCapability("screenResolution", "1440x989");
        caps.setCapability("enableVideo", true);
        caps.setCapability("enableLog", true);
        return new RemoteWebDriver(new URL(slenoidURL), caps);
    }
}
