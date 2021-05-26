package helpers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import config.DriverConfig;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.selenide.LogType;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import tests.TestData;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static org.openqa.selenium.logging.LogType.BROWSER;

public class DriverHelper {

    private static final DriverConfig driverConfig = ConfigFactory.create(DriverConfig.class, System.getProperties());

    public static String getWebMobile() {
        return driverConfig.webBrowserMobileView();
    }

    public static boolean isWebMobile() {
        return !getWebMobile().equals("");
    }

    public static String getWebRemoteDriver() {
        return String.format(driverConfig.webRemoteDriverUrlFormat(),
                driverConfig.webRemoteDriverUser(),
                driverConfig.webRemoteDriverPassword(),
                driverConfig.webRemoteDriverUrl());
    }

    public static boolean isRemoteWebDriver() {
        return driverConfig.isRemote();
    }

    public static String getVideoUrl() {
        return String.format(driverConfig.videoStorageFormat(),
                driverConfig.webRemoteDriverUrl());
    }

    public static boolean isVideoOn() {
        if (isRemoteWebDriver()) {
            return driverConfig.videoEnabled();
        }
        return false;
    }

    public static String getSessionId(){
        return ((RemoteWebDriver) getWebDriver()).getSessionId().toString().replace("selenoid","");
    }

    public static String getConsoleLogs() {
        return String.join("\n", Selenide.getWebDriverLogs(BROWSER));
    }

    public static void configureDriver() {
        addListener("AllureSelenide", new AllureSelenide().enableLogs(LogType.BROWSER, Level.ALL));

        Configuration.browser = driverConfig.webBrowser();
        Configuration.browserVersion = driverConfig.webBrowserVersion();
        Configuration.browserSize = driverConfig.webBrowserSize();
        Configuration.baseUrl = TestData.getWebUrl();
        Configuration.timeout = 4000;

        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (isWebMobile()) { // for chrome only
            ChromeOptions chromeOptions = new ChromeOptions();
            Map<String, Object> mobileDevice = new HashMap<>();
            mobileDevice.put("deviceName", getWebMobile());
            chromeOptions.setExperimentalOption("mobileEmulation", mobileDevice);
            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        }

        if (isRemoteWebDriver()) {
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", isVideoOn());
            Configuration.remote = getWebRemoteDriver();
        }

        Configuration.browserCapabilities = capabilities;


    }
}
