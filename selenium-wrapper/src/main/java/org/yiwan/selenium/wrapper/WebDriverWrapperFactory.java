package org.yiwan.selenium.wrapper;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yiwan.easy.model.TestCapabilities;
import org.yiwan.easy.util.IAction;
import org.yiwan.easy.util.PropertiesHelper;
import org.yiwan.selenium.model.WebTestCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

/**
 * @author Kenny Wang
 */
public class WebDriverWrapperFactory {
    private final static Logger logger = LoggerFactory.getLogger(WebDriverWrapperFactory.class);
    private URL url;
    private TestCapabilities testCapabilities;
    private final Proxy seleniumProxy;

    public WebDriverWrapperFactory(TestCapabilities testCapabilities) throws MalformedURLException {
        this(testCapabilities, null);
    }

    public WebDriverWrapperFactory(TestCapabilities testCapabilities, Proxy seleniumProxy) throws MalformedURLException {
        this(new URL(PropertiesHelper.REMOTE_ADDRESS), testCapabilities, seleniumProxy);
    }

    public WebDriverWrapperFactory(URL url, TestCapabilities testCapabilities, Proxy seleniumProxy) {
        this.url = url;
        this.testCapabilities = testCapabilities;
        this.seleniumProxy = seleniumProxy;
    }

    public IWebDriverWrapper create() throws MalformedURLException {
        IWebDriverWrapper webDriverWrapper = null;
        if (PropertiesHelper.DUMMY_TEST) {
            webDriverWrapper = new DummyWebDriverWrapper();
        } else {
            if (PropertiesHelper.REMOTE) {
                logger.info("choosing remote test mode");
                webDriverWrapper = createRemoteWebDriverWrapper();
            } else {
                logger.info("choosing local test mode");
                webDriverWrapper = createWebDriverWrapper();
            }
//        use explicit wait to replace implicitly wait
//        webDriver.manage().timeouts().implicitlyWait(PropertiesHelper.TIMEOUT_INTERVAL, TimeUnit.SECONDS);
        }
        logger.info("created wrapper driver with following information\nSession ID: {}\n{}", webDriverWrapper.getSessionId(), webDriverWrapper.getCapabilities());
        return webDriverWrapper;
    }

    private IWebDriverWrapper createWebDriverWrapper() {
        switch (((WebTestCapabilities) testCapabilities).getBrowser().toLowerCase()) {
            case "chrome":
                return createChromeDriverWrapper();
            case "ie":
                return createInternetExplorerDriverWrapper();
            case "htmlunit":
                return createHtmlUnitDriverWrapper();
            case "htmlunitjs":
                return createHtmlUnitJSDriverWrapper();
            case "phantomjs":
                return createPhantomJSDriverWrapper();
            default:
                return createFirefoxDriverWrapper();
        }
    }

    private IWebDriverWrapper createRemoteWebDriverWrapper() throws MalformedURLException {
        final DesiredCapabilities capabilities = ((WebTestCapabilities) testCapabilities).toDesiredCapabilities();
        logger.info("choosing platform " + ((WebTestCapabilities) testCapabilities).getOs());
        logger.info("choosing platform version " + ((WebTestCapabilities) testCapabilities).getOsVersion());
        setRemoteBrowserCapabilities(capabilities);

//        resolve easy grid issue of "org.openqa.easy.WebDriverException: Error forwarding the new session Error forwarding the request Read timed out"
        final URL addressOfRemoteServer = new URL(PropertiesHelper.REMOTE_ADDRESS);
        final RemoteWebDriver[] rwd = new RemoteWebDriver[1];
        new WebDriverActionExecutor().execute(new IAction() {
            @Override
            public void execute() {
                rwd[0] = new RemoteWebDriver(addressOfRemoteServer, capabilities);
            }
        });

//        HttpClient.Factory factory = new ApacheHttpClient.Factory(new HttpClientFactory(PropertiesHelper.REMOTE_CONNECTION_TIMEOUT, PropertiesHelper.REMOTE_SOCKET_TIMEOUT));
//        HttpCommandExecutor executor = new HttpCommandExecutor(Collections.<String, CommandInfo>emptyMap(), addressOfRemoteServer, factory);
//        RemoteWebDriver rwd = new RemoteWebDriver(executor, capabilities);

        rwd[0].setFileDetector(new LocalFileDetector());
        return wrapWebDriver(rwd[0]);
    }

    private IWebDriverWrapper wrapWebDriver(WebDriver webDriver) {
        switch (((WebTestCapabilities) testCapabilities).getBrowser().toLowerCase()) {
            case "chrome":
                return new ChromeDriverWrapper(webDriver);
            case "ie":
                return new InternetExplorerDriverWrapper(webDriver);
            case "htmlunit":
                return new HtmlUnitDriverWrapper(webDriver);
            case "htmlunitjs":
                return new HtmlUnitDriverWrapper(webDriver);
            case "phantomjs":
                return new PhantomJSDriverWrapper(webDriver);
            default:
                return new FirefoxDriverWrapper(webDriver);
        }
    }

    private void setRemoteBrowserCapabilities(DesiredCapabilities capabilities) {
        setBrowserCapabilities(capabilities);
        switch (((WebTestCapabilities) testCapabilities).getBrowser().toLowerCase()) {
            case "ie":
                setInternetExplorerCapabilities(capabilities);
                break;
            case "chrome":
                setChromeCapabilities(capabilities);
                break;
            case "htmlunit":
                setHtmlUnitCapabilities(capabilities);
                break;
            case "htmlunitjs":
                setHtmlUnitCapabilities(capabilities);
                break;
            case "phantomjs":
                setPhantomJSCapabilities(capabilities);
                break;
            default:
                setFirefoxCapabilities(capabilities);
        }
    }

    private IWebDriverWrapper createHtmlUnitDriverWrapper() {
        DesiredCapabilities capabilities = DesiredCapabilities.htmlUnit();
        setBrowserCapabilities(capabilities);
        setHtmlUnitCapabilities(capabilities);
        return new HtmlUnitDriverWrapper(new HtmlUnitDriver(capabilities));
    }

    private IWebDriverWrapper createHtmlUnitJSDriverWrapper() {
        DesiredCapabilities capabilities = DesiredCapabilities.htmlUnitWithJs();
        setBrowserCapabilities(capabilities);
        setHtmlUnitCapabilities(capabilities);
        return new HtmlUnitDriverWrapper(new HtmlUnitDriver(capabilities));
    }

    private IWebDriverWrapper createPhantomJSDriverWrapper() {
        System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, PropertiesHelper.PHANTOMJS_PATH);
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        setBrowserCapabilities(capabilities);
        setPhantomJSCapabilities(capabilities);
        return new PhantomJSDriverWrapper(new PhantomJSDriver(capabilities));
    }

    private IWebDriverWrapper createChromeDriverWrapper() {
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, PropertiesHelper.CHROME_WEBDRIVER);
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        setBrowserCapabilities(capabilities);
        setChromeCapabilities(capabilities);
        return new ChromeDriverWrapper(new ChromeDriver(capabilities));
    }

    private IWebDriverWrapper createInternetExplorerDriverWrapper() {
        System.setProperty(InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY, PropertiesHelper.IE_WEBDRIVER);
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        setBrowserCapabilities(capabilities);
        setInternetExplorerCapabilities(capabilities);
        return new InternetExplorerDriverWrapper(new InternetExplorerDriver(capabilities));
    }

    private IWebDriverWrapper createFirefoxDriverWrapper() {
//        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_BINARY, PropertiesHelper.FIREFOX_PATH);
        System.setProperty("webdriver.gecko.driver", PropertiesHelper.MARIONETTE_WEBDRIVER);
        FirefoxBinary firefoxBinary;
        if (PropertiesHelper.FIREFOX_PATH != null && !PropertiesHelper.FIREFOX_PATH.trim().isEmpty()) {
            firefoxBinary = new FirefoxBinary(new File(PropertiesHelper.FIREFOX_PATH));
        } else {
            firefoxBinary = new FirefoxBinary();
        }

        FirefoxProfile profile = new FirefoxProfile();
        setFirefoxProfile(profile);

        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        setBrowserCapabilities(capabilities);
        setFirefoxCapabilities(capabilities);

        if (capabilities.getCapability("marionette").equals(true)) {
            return new FirefoxDriverWrapper(new MarionetteDriver(capabilities));
        }
        return new FirefoxDriverWrapper(new FirefoxDriver(firefoxBinary, profile, capabilities));
    }

    private void setBrowserCapabilities(DesiredCapabilities capabilities) {
        logger.info("choosing browser " + ((WebTestCapabilities) testCapabilities).getBrowser());
        logger.info("choosing browser " + ((WebTestCapabilities) testCapabilities).getBrowserVersion());
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, PropertiesHelper.ACCEPT_SSL_CERTS);
        capabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS, PropertiesHelper.NATIVE_EVENTS);
        if (PropertiesHelper.UNEXPECTED_ALERT_BEHAVIOUR != null) {
            capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.fromString(PropertiesHelper.UNEXPECTED_ALERT_BEHAVIOUR));
        }
        if (seleniumProxy != null) {
            capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
        }
    }

    private void setHtmlUnitCapabilities(DesiredCapabilities capabilities) {
        capabilities.setBrowserName(BrowserType.HTMLUNIT);
    }

    private void setPhantomJSCapabilities(DesiredCapabilities capabilities) {
        capabilities.setBrowserName(BrowserType.PHANTOMJS);
        capabilities.setCapability("takesScreenshot", true);
        String[] phantomjsCliArgs = StringUtils.split(PropertiesHelper.PHANTOMJS_CLI_ARGS.trim());
        if (PropertiesHelper.PHANTOMJS_CLI_ARGS != null && phantomjsCliArgs.length > 0) {
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, Arrays.asList(phantomjsCliArgs));
        }
        String[] phantomjsGhostdriverCliArgs = StringUtils.split(PropertiesHelper.PHANTOMJS_GHOSTDRIVER_CLI_ARGS.trim());
        if (PropertiesHelper.PHANTOMJS_GHOSTDRIVER_CLI_ARGS != null && phantomjsGhostdriverCliArgs.length > 0) {
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS, Arrays.asList(phantomjsGhostdriverCliArgs));
        }
    }

    private void setInternetExplorerCapabilities(DesiredCapabilities capabilities) {
        capabilities.setBrowserName(BrowserType.IE);
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, PropertiesHelper.IGNORE_PROTECTED_MODE_SETTINGS);
        if (PropertiesHelper.INITIAL_BROWSER_URL != null) {
            capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, PropertiesHelper.INITIAL_BROWSER_URL);
        }
        capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, PropertiesHelper.IGNORE_ZOOM_SETTING);
        capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, PropertiesHelper.REQUIRE_WINDOW_FOCUS);
        capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, PropertiesHelper.ENABLE_PERSISTENT_HOVER);
//        capabilities.setCapability("disable-popup-blocking", true);
    }

    private void setFirefoxCapabilities(DesiredCapabilities capabilities) {
        capabilities.setBrowserName(BrowserType.FIREFOX);
        if (((WebTestCapabilities) testCapabilities).getBrowserVersion() != null && Integer.parseInt(((WebTestCapabilities) testCapabilities).getBrowserVersion()) > 47) {
            logger.info("choosing marionette mode");
            capabilities.setCapability("marionette", true);
        }
    }

    private void setChromeCapabilities(DesiredCapabilities capabilities) {
        capabilities.setBrowserName(BrowserType.CHROME);
    }

    private void setFirefoxProfile(FirefoxProfile profile) {
        profile.setAcceptUntrustedCertificates(true);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.helperApps.alwaysAsk.force", false);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/plain,text/xml,text/csv,image/jpeg,application/zip,application/vnd.ms-excel,application/pdf,application/xml");
//        profile.setEnableNativeEvents(PropertiesHelper.NATIVE_EVENTS);
//        profile.setPreference("browser.download.folderList", 2);
//        profile.setPreference("browser.download.dir", "D:\\mydownloads\\");
//        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream");
//        profile.setPreference("browser.helperApps.neverAsk.openFile", "text/csv,application/pdf,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,application/x-excel,application/x-msexcel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
//        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv,application/pdf,application/x-msexcel,application/excel,application/x-excel,application/excel,application/x-excel,application/excel,application/vnd.ms-excel,application/x-excel,application/x-msexcel,image/png,image/pjpeg,image/jpeg,text/html,text/plain,application/msword,application/xml,application/excel");
//        Then add the proxy setting to the Firefox profile we created
//        profile.setPreference("network.proxy.http", "localhost");
//        profile.setPreference("network.proxy.http_port", "8888");
    }
}