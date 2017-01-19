package org.yiwan.appium.test;

import net.lightbody.bmp.client.ClientUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.yiwan.appium.bmproxy.ProxyWrapper;
import org.yiwan.appium.bmproxy.TimestampWriter;
import org.yiwan.appium.bmproxy.observer.FileDownloadObserver;
import org.yiwan.appium.bmproxy.observer.HttpArchiveObserver;
import org.yiwan.appium.bmproxy.observer.ScreenshotObserver;
import org.yiwan.appium.bmproxy.observer.TimestampObserver;
import org.yiwan.appium.bmproxy.subject.Subject;
import org.yiwan.appium.bmproxy.subject.TransactionSubject;
import org.yiwan.appium.model.AndroidCapabilities;
import org.yiwan.appium.model.AppiumCapabilities;
import org.yiwan.appium.model.FirefoxOSCapabilities;
import org.yiwan.appium.model.IOSCapabilities;
import org.yiwan.appium.util.Helper;
import org.yiwan.appium.util.PropHelper;
import org.yiwan.appium.wrapper.AppiumDriverWrapperFactory;
import org.yiwan.appium.wrapper.IAppiumDriverWrapper;
import org.yiwan.easy.test.AbstractTest;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Kenny Wang on 3/14/2016.
 */
public abstract class AbstractAppTest extends AbstractTest {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Map<String, String> testMap = new HashMap<>();
    private final Subject subject = new TransactionSubject();
    private final TimestampWriter timestampWriter = new TimestampWriter();

    private boolean recycleTestEnvironment = false;
    private boolean skipTest = false;//whether to skip next execution of left test methods
    private boolean prepareToDownload = false;

    private String scenarioId;
    private String featureId;
    private String downloadFile;//last download file name by relative path
    private String defaultDownloadFileName;//default name of download file
    private String transactionName;//unique http archive file name
    private String suiteName;//testng test suite name
    private String testName;//testng test name

    private AppiumCapabilities appiumCapabilities;
    private IAppiumDriverWrapper appiumDriverWrapper;
    private ITestDataManager testDataManager;
    private IViewManager viewManager;
    private ProxyWrapper proxyWrapper;
    private TestEnvironment testEnvironment;

    @Override
    public TimestampWriter getTimestampWriter() {
        return timestampWriter;
    }

    @Override
    public AppiumCapabilities getAppiumCapabilities() {
        return appiumCapabilities;
    }

    @Override
    public IAppiumDriverWrapper getDriverWrapper() {
        return appiumDriverWrapper;
    }

    @Override
    public IViewManager getViewManager() {
        return viewManager;
    }

    @Override
    public void setViewManager(IViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @Override
    public String getSuiteName() {
        return suiteName;
    }

    @Override
    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }

    @Override
    public String getTestName() {
        return testName;
    }

    @Override
    public void setTestName(String testName) {
        this.testName = testName;
    }

    @Override
    public Map<String, String> getTestMap() {
        return testMap;
    }

    @Override
    public TestEnvironment getTestEnvironment() {
        return testEnvironment;
    }

    @Override
    public void setTestEnvironment(TestEnvironment testEnvironment) {
        this.testEnvironment = testEnvironment;
    }

    @Override
    public boolean getSkipTest() {
        return skipTest;
    }

    @Override
    public void setSkipTest(boolean skipTest) {
        this.skipTest = skipTest;
    }

    @Override
    public void createProxyWrapper() {
        if (PropHelper.ENABLE_TRANSACTION_TIMESTAMP_RECORD || PropHelper.ENABLE_TRANSACTION_SCREENSHOT_CAPTURE || PropHelper.ENABLE_HTTP_ARCHIVE || PropHelper.ENABLE_FILE_DOWNLOAD) {
            proxyWrapper = new ProxyWrapper();
            if (PropHelper.ENABLE_WHITELIST) {
                // This are the patterns of our sites, in real life there are more...
                List<String> allowUrlPatterns = new ArrayList<>();
                for (ApplicationServer applicationServer : getTestEnvironment().getApplicationServers()) {
                    if (applicationServer.getUrl() != null) {
                        allowUrlPatterns.add(Pattern.quote(applicationServer.getUrl()) + ".*");
                    }
                }
                // All the URLs that are not from our sites are blocked and a status code of 404 is returned
                proxyWrapper.whitelistRequests(allowUrlPatterns, 404);
            }
            proxyWrapper.start();
            if (PropHelper.ENABLE_TRANSACTION_TIMESTAMP_RECORD) {
                subject.attach(new TimestampObserver(this));
            }
            if (PropHelper.ENABLE_TRANSACTION_SCREENSHOT_CAPTURE) {
                subject.attach(new ScreenshotObserver(this));
            }
            if (PropHelper.ENABLE_HTTP_ARCHIVE) {
                subject.attach(new HttpArchiveObserver(this));
            }
            if (PropHelper.ENABLE_FILE_DOWNLOAD) {
                subject.attach(new FileDownloadObserver(this));
            }
        }
    }

    @Override
    public void createAppiumDriverWrapper() throws MalformedURLException {
        Proxy realProxy;
        if (proxyWrapper != null) {
            if (PropHelper.ENABLE_PENETRATION_TEST) {
                proxyWrapper.setChainedProxy(PropHelper.ZAP_SERVER_HOST, PropHelper.ZAP_SERVER_PORT);
            }
            realProxy = ClientUtil.createSeleniumProxy(proxyWrapper.getProxy());
        } else {
            if (PropHelper.ENABLE_PENETRATION_TEST) {
                String proxyStr = String.format("%s:%d", PropHelper.ZAP_SERVER_HOST, PropHelper.ZAP_SERVER_PORT);
                realProxy = new Proxy().setProxyType(Proxy.ProxyType.MANUAL)
                        .setHttpProxy(proxyStr)
                        .setSslProxy(proxyStr);
            } else {
                realProxy = new Proxy().setProxyType(Proxy.ProxyType.DIRECT);
            }
        }
        appiumDriverWrapper = new AppiumDriverWrapperFactory(appiumCapabilities, realProxy).create();
//        use explicit wait to replace implicitly wait
//        appiumDriverWrapper.manage().timeouts().implicitlyWait(PropHelper.TIMEOUT_INTERVAL, TimeUnit.SECONDS);
    }

    @Override
    public ProxyWrapper getProxyWrapper() {
        return proxyWrapper;
    }

    @Override
    public boolean isPrepareToDownload() {
        return prepareToDownload;
    }

    @Override
    public void setPrepareToDownload(boolean prepareToDownload) {
        this.prepareToDownload = prepareToDownload;
    }

    @Override
    public String getDownloadFile() {
        return downloadFile;
    }

    @Override
    public void setDownloadFile(String downloadFile) {
        this.downloadFile = downloadFile;
    }

    @Override
    public String getDefaultDownloadFileName() {
        return defaultDownloadFileName;
    }

    @Override
    public void setDefaultDownloadFileName(String defaultDownloadFileName) {
        this.defaultDownloadFileName = defaultDownloadFileName;
    }

    @Override
    public String getTransactionName() {
        return transactionName;
    }

    @Override
    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    @Override
    public ITestDataManager getTestDataManager() {
        return testDataManager;
    }

    @Override
    public void setTestDataManager(ITestDataManager testDataManager) {
        this.testDataManager = testDataManager;
    }

    @Override
    public String getScenarioId() {
        return scenarioId;
    }

    @Override
    public void setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
    }

    @Override
    public String getFeatureId() {
        return featureId;
    }

    @Override
    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }

    @Override
    public void report(String s) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String now = df.format(new Date());
        Reporter.log(now + " " + this.getClass().getName() + " " + s + "<br>");
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info(result.getTestClass().getName() + "." + result.getName() + " started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info(result.getTestClass().getName() + "." + result.getName() + " passed");
    }

    @Override
    public void onTestFailure(ITestResult result) throws IOException {
        logger.info(result.getTestClass().getName() + "." + result.getName() + " failed");
        embedScreenshot();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.info(result.getTestClass().getName() + "." + result.getName() + " skipped");
    }

    @Override
    public void embedScreenshot() throws IOException {
        String saveTo = Helper.randomize() + ".png";
        File screenshot = getDriverWrapper().getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File(PropHelper.RESULT_FOLDER + PropHelper.SCREENSHOT_FOLDER + saveTo));
        String refPath = "../../" + PropHelper.SCREENSHOT_FOLDER + saveTo;
        report(Helper.getTestReportStyle(refPath, "<img src=\"" + refPath + "\" width=\"400\" height=\"300\"/>"));
    }

    @Override
    public void embedTestLog() throws IOException {
    }

    @Override
    public void embedTestData(Object o) throws Exception {
    }

    @Override
    public boolean isRecycleTestEnvironment() {
        return recycleTestEnvironment;
    }

    @Override
    public void setRecycleTestEnvironment(boolean recycleTestEnvironment) {
        this.recycleTestEnvironment = recycleTestEnvironment;
    }

    @Override
    public void startTransaction(String transactionName) {
        this.transactionName = transactionName;
        subject.nodifyObserversStart();
    }

    @Override
    public void stopTransaction() {
        subject.nodifyObserversStop();
    }

    @Override
    public String getSuiteTestSeparator() {
        if (getSuiteName() != null && getTestName() != null) {
            return getSuiteName() + "/" + getTestName() + "/";
        }
        return "";
    }

    @Override
    public void setUpTest() throws Exception {
        MDC.put(PropHelper.DISCRIMINATOR_KEY, getSuiteTestSeparator() + getScenarioId() + ".log");
        (new File(PropHelper.TARGET_SCENARIO_DATA_FOLDER)).mkdirs();
        setTestEnvironment(TestCaseManager.takeTestEnvironment());//if no available test environment, no need create appiumdriver and test data
        setRecycleTestEnvironment(true);//must be after method setTestEnvironment
        report(String.format("test environment<br/>%s", getTestEnvironment()));
        createProxyWrapper();//create proxyWrapper must before creating appiumDriverWrapper
        createAppiumDriverWrapper();//create appiumDriverWrapper
        report(Helper.getTestReportStyle("../../" + PropHelper.LOG_FOLDER + MDC.get(PropHelper.DISCRIMINATOR_KEY), "open test execution log"));
        if (PropHelper.ENABLE_TRANSACTION_TIMESTAMP_RECORD) {
            timestampWriter.write(this);
        }
    }

    @Override
    public void tearDownTest() throws Exception {
        if (isRecycleTestEnvironment()) {
            TestCaseManager.putTestEnvironment(getTestEnvironment());
            setRecycleTestEnvironment(false);
        }
        if (getProxyWrapper() != null) {
            proxyWrapper.stop();
        }
        try {
            appiumDriverWrapper.quit();
        } catch (Exception ignored) {
        }
        appiumDriverWrapper.validateAll();
    }

    @BeforeClass
    @Parameters({
            //appium
            "automationName", "platformName", "platformVersion", "deviceName", "wrapper", "browserName", "newCommandTimeout", "language", "locale", "udid", "orientation", "autoWebview", "noReset", "fullReset",
            //android
            "appActivity", "appPackage", "appWaitActivity", "appWaitPackage", "deviceReadyTimeout", "androidCoverage", "enablePerformanceLogging", "androidDeviceReadyTimeout", "adbPort", "androidDeviceSocket", "avd", "avdLaunchTimeout", "avdReadyTimeout", "avdArgs", "useKeystore", "keystorePath", "keystorePassword", "keyAlias", "keyPassword", "chromedriverExecutable", "autoWebviewTimeout", "intentAction", "intentCategory", "intentFlags", "optionalIntentArguments", "dontStopAppOnReset", "unicodeKeyboard", "resetKeyboard", "noSign", "ignoreUnimportantViews", "disableAndroidWatchers", "chromeOptions", "recreateChromeDriverSessions", "nativeWebScreenshot",
            //ios
            "calendarFormat", "bundleId", "launchTimeout", "locationServicesEnabled", "locationServicesAuthorized", "autoAcceptAlerts", "autoDismissAlerts", "nativeInstrumentsLib", "nativeWebTap", "safariInitialUrl", "safariAllowPopups", "safariIgnoreFraudWarning", "safariOpenLinksInBackground", "keepKeyChains", "localizableStringsDir", "processArguments", "interKeyDelay", "showIOSLog", "sendKeyStrategy", "screenshotWaitTimeout", "waitForAppScript", "webviewConnectRetries", "appName"})
    protected void beforeClass(ITestContext testContext,
                               //appium
                               @Optional String automationName, @Optional String platformName, @Optional String platformVersion, @Optional String deviceName, @Optional String app, @Optional String browserName, @Optional Integer newCommandTimeout, @Optional String language, @Optional String locale, @Optional String udid, @Optional String orientation, @Optional Boolean autoWebview, @Optional Boolean noReset, @Optional Boolean fullReset,
                               //android
                               @Optional String appActivity, @Optional String appPackage, @Optional String appWaitActivity, @Optional String appWaitPackage, @Optional Integer deviceReadyTimeout, @Optional String androidCoverage, @Optional Boolean enablePerformanceLogging, @Optional Integer androidDeviceReadyTimeout, @Optional Integer adbPort, @Optional String androidDeviceSocket, @Optional String avd, @Optional Long avdLaunchTimeout, @Optional Long avdReadyTimeout, @Optional String avdArgs, @Optional Boolean useKeystore, @Optional String keystorePath, @Optional String keystorePassword, @Optional String keyAlias, @Optional String keyPassword, @Optional String chromedriverExecutable, @Optional Long autoWebviewTimeout, @Optional String intentAction, @Optional String intentCategory, @Optional String intentFlags, @Optional String optionalIntentArguments, @Optional Boolean dontStopAppOnReset, @Optional Boolean unicodeKeyboard, @Optional Boolean resetKeyboard, @Optional Boolean noSign, @Optional Boolean ignoreUnimportantViews, @Optional Boolean disableAndroidWatchers, @Optional String chromeOptions, @Optional Boolean recreateChromeDriverSessions, @Optional Boolean nativeWebScreenshot,
                               //ios
                               @Optional String calendarFormat, @Optional String bundleId, @Optional Long launchTimeout, @Optional Boolean locationServicesEnabled, @Optional Boolean locationServicesAuthorized, @Optional Boolean autoAcceptAlerts, @Optional Boolean autoDismissAlerts, @Optional Boolean nativeInstrumentsLib, @Optional Boolean nativeWebTap, @Optional String safariInitialUrl, @Optional Boolean safariAllowPopups, @Optional Boolean safariIgnoreFraudWarning, @Optional Boolean safariOpenLinksInBackground, @Optional Boolean keepKeyChains, @Optional String localizableStringsDir, @Optional String processArguments, @Optional Long interKeyDelay, @Optional String showIOSLog, @Optional String sendKeyStrategy, @Optional String screenshotWaitTimeout, @Optional String waitForAppScript, @Optional Integer webviewConnectRetries, @Optional String appName) {
        TestCaseManager.setTestCase(this);
        setSuiteName(testContext.getCurrentXmlTest().getSuite().getName());
        setTestName(testContext.getCurrentXmlTest().getName());

        String platform = platformName == null ? PropHelper.getProperty("appium.platformName") : platformName;
        switch (platform) {
            case "ios":
                appiumCapabilities = new IOSCapabilities(automationName, platformName, platformVersion, deviceName, app, browserName, newCommandTimeout, language, locale, udid, orientation, autoWebview, noReset, fullReset, calendarFormat, bundleId, launchTimeout, locationServicesEnabled, locationServicesAuthorized, autoAcceptAlerts, autoDismissAlerts, nativeInstrumentsLib, nativeWebTap, safariInitialUrl, safariAllowPopups, safariIgnoreFraudWarning, safariOpenLinksInBackground, keepKeyChains, localizableStringsDir, processArguments, interKeyDelay, showIOSLog, sendKeyStrategy, screenshotWaitTimeout, waitForAppScript, webviewConnectRetries, appName);
                break;
            case "firefoxos":
                appiumCapabilities = new FirefoxOSCapabilities();
                break;
            default:
                appiumCapabilities = new AndroidCapabilities(automationName, platformName, platformVersion, deviceName, app, browserName, newCommandTimeout, language, locale, udid, orientation, autoWebview, noReset, fullReset, appActivity, appPackage, appWaitActivity, appWaitPackage, deviceReadyTimeout, androidCoverage, enablePerformanceLogging, androidDeviceReadyTimeout, adbPort, androidDeviceSocket, avd, avdLaunchTimeout, avdReadyTimeout, avdArgs, useKeystore, keystorePath, keystorePassword, keyAlias, keyPassword, chromedriverExecutable, autoWebviewTimeout, intentAction, intentCategory, intentFlags, optionalIntentArguments, dontStopAppOnReset, unicodeKeyboard, resetKeyboard, noSign, ignoreUnimportantViews, disableAndroidWatchers, chromeOptions, recreateChromeDriverSessions, nativeWebScreenshot);
        }

        report(String.format("test capability<br/>%s", appiumCapabilities));
    }
}
