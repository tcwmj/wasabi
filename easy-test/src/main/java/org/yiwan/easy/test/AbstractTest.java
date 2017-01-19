package org.yiwan.easy.test;

import net.lightbody.bmp.client.ClientUtil;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.yiwan.easy.bmproxy.ProxyWrapper;
import org.yiwan.easy.bmproxy.TimestampWriter;
import org.yiwan.easy.bmproxy.observer.FileDownloadObserver;
import org.yiwan.easy.bmproxy.observer.HttpArchiveObserver;
import org.yiwan.easy.bmproxy.observer.ScreenshotObserver;
import org.yiwan.easy.bmproxy.observer.TimestampObserver;
import org.yiwan.easy.bmproxy.subject.Subject;
import org.yiwan.easy.bmproxy.subject.TransactionSubject;
import org.yiwan.easy.model.ApplicationServer;
import org.yiwan.easy.model.TestCapabilities;
import org.yiwan.easy.model.TestEnvironment;
import org.yiwan.easy.util.Helper;
import org.yiwan.easy.util.PropHelper;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Kenny Wang on 3/14/2016.
 */
public abstract class AbstractTest implements ITestBase {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String downloadFile;//last download file name by relative path
    private String defaultDownloadFileName;//default name of download file
    private String transactionName;//unique http archive file name
    private String suiteName;//testng test suite name
    private String testName;//testng test name
    private String scenarioId;
    private String featureId;

    private boolean skipTest;//whether to skip next execution of left test methods
    private boolean prepareToDownload;
    private boolean recycleTestEnvironment;

    private Map<String, String> testMap;
    private Subject subject;
    private IDriverWrapper driverWrapper;
    private ITestDataManager testDataManager;
    private IViewManager viewManager;
    private ProxyWrapper proxyWrapper;
    private TestCapabilities testCapabilities;
    private TestEnvironment testEnvironment;
    private TimestampWriter timestampWriter;
    private SoftAssertions softAssertions;
    private Charset defaultDownloadFileCharset;

    @Override
    public String getDownloadFile() {
        return downloadFile;
    }

    @Override
    public String getDefaultDownloadFileName() {
        return defaultDownloadFileName;
    }

    @Override
    public String getTransactionName() {
        return transactionName;
    }

    @Override
    public String getSuiteName() {
        return suiteName;
    }

    @Override
    public String getTestName() {
        return testName;
    }

    @Override
    public String getScenarioId() {
        return scenarioId;
    }

    @Override
    public String getFeatureId() {
        return featureId;
    }

    @Override
    public String getSuiteTestSeparator() {
        if (suiteName != null && testName != null) {
            return suiteName + "/" + testName + "/";
        }
        return "";
    }

    @Override
    public boolean isSkipTest() {
        return skipTest;
    }

    @Override
    public boolean isPrepareToDownload() {
        return prepareToDownload;
    }

    @Override
    public Map<String, String> getTestMap() {
        return testMap;
    }

    public IDriverWrapper getDriverWrapper() {
        return driverWrapper;
    }

    @Override
    public ITestDataManager getTestDataManager() {
        return testDataManager;
    }

    public IViewManager getViewManager() {
        return viewManager;
    }

    @Override
    public ProxyWrapper getProxyWrapper() {
        return proxyWrapper;
    }

    public TestCapabilities getTestCapabilities() {
        return testCapabilities;
    }

    @Override
    public TestEnvironment getTestEnvironment() {
        return testEnvironment;
    }

    @Override
    public TimestampWriter getTimestampWriter() {
        return timestampWriter;
    }

    @Override
    public SoftAssertions getSoftAssertions() {
        return softAssertions;
    }

    @Override
    public Charset getDefaultDownloadFileCharset() {
        return defaultDownloadFileCharset;
    }

    @Override
    public void setDownloadFile(String downloadFile) {
        this.downloadFile = downloadFile;
    }

    @Override
    public void setDefaultDownloadFileName(String defaultDownloadFileName) {
        this.defaultDownloadFileName = defaultDownloadFileName;
    }

    @Override
    public void setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
    }

    @Override
    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }

    @Override
    public void setSkipTest(boolean skipTest) {
        this.skipTest = skipTest;
    }

    @Override
    public void setPrepareToDownload(boolean prepareToDownload) {
        this.prepareToDownload = prepareToDownload;
    }

    @Override
    public void setTestDataManager(ITestDataManager testDataManager) {
        this.testDataManager = testDataManager;
    }

    @Override
    public void setViewManager(IViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @Override
    public void setDefaultDownloadFileCharset(Charset defaultDownloadFileCharset) {
        this.defaultDownloadFileCharset = defaultDownloadFileCharset;
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
//        if (driverWrapper.alert().isPresent()) {
//            logger.warn("dismiss unexpected alert {} before taking screenshot", driverWrapper.alert().getText());
//            driverWrapper.alert().dismiss();
//        }
        String saveTo = Helper.randomize() + ".png";
        File screenshot = driverWrapper.getScreenshotAs(OutputType.FILE);
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
    public void startTransaction(String transactionName) {
        this.transactionName = transactionName;
        if (subject != null) {
            subject.nodifyObserversStart();
        }
    }

    @Override
    public void stopTransaction() {
        if (subject != null) {
            subject.nodifyObserversStop();
        }
    }

    @Override
    public void createProxyWrapper() {
        if (PropHelper.ENABLE_TRANSACTION_TIMESTAMP_RECORD || PropHelper.ENABLE_TRANSACTION_SCREENSHOT_CAPTURE || PropHelper.ENABLE_HTTP_ARCHIVE || PropHelper.ENABLE_FILE_DOWNLOAD) {
            proxyWrapper = new ProxyWrapper();
            if (PropHelper.ENABLE_WHITELIST) {
                // This are the patterns of our sites, in real life there are more...
                List<String> allowUrlPatterns = new ArrayList<>();
                for (ApplicationServer applicationServer : testEnvironment.getApplicationServers()) {
                    if (applicationServer.getUrl() != null) {
                        allowUrlPatterns.add(Pattern.quote(applicationServer.getUrl()) + ".*");
                    }
                }
                // All the URLs that are not from our sites are blocked and a status code of 404 is returned
                proxyWrapper.whitelistRequests(allowUrlPatterns, 404);
            }
            proxyWrapper.start();
            subject = new TransactionSubject();
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
    public void createWebDriverWrapper() throws MalformedURLException {
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
        driverWrapper = new WebDriverWrapperFactory(testCapabilities, realProxy).create();
    }

    /**
     * feature id and scenario id should be set before invoking setUpTest
     *
     * @throws Exception
     */
    @Override
    public void setUpTest() throws Exception {
        setUpTest(true);
    }

    /**
     * feature id and scenario id should be set before invoking setUpTest
     *
     * @param proxied enable proxy by test scenario or not
     * @throws Exception
     */
    @Override
    public void setUpTest(boolean proxied) throws Exception {
        MDC.put(PropHelper.DISCRIMINATOR_KEY, getSuiteTestSeparator() + scenarioId + ".log");
        (new File(PropHelper.TARGET_SCENARIO_DATA_FOLDER)).mkdirs();
        logger.info("setup test before starting feature id {}, scenario id {}", featureId, scenarioId);

        softAssertions = new SoftAssertions();
        testMap = new HashMap<>();
        skipTest = false;
        prepareToDownload = false;
        recycleTestEnvironment = false;
        proxyWrapper = null;
        defaultDownloadFileCharset = StandardCharsets.UTF_8;

        //if no available test environment, no need create webdriver and test data
        testEnvironment = TestCaseManager.pollTestEnvironment();
        if (testEnvironment != null) {
            recycleTestEnvironment = true;//must be after method setTestEnvironment
            if (proxied && (PropHelper.ENABLE_TRANSACTION_TIMESTAMP_RECORD ||
                    PropHelper.ENABLE_TRANSACTION_SCREENSHOT_CAPTURE ||
                    PropHelper.ENABLE_HTTP_ARCHIVE ||
                    PropHelper.ENABLE_FILE_DOWNLOAD)) {
                createProxyWrapper();//create proxyWrapper must before creating webdriverWrapper
            }
            createWebDriverWrapper();//create webdriverWrapper
            driverWrapper.deleteAllCookies();
            if (PropHelper.MAXIMIZE_BROWSER) {
                driverWrapper.maximize();
            }
            if (PropHelper.ENABLE_TRANSACTION_TIMESTAMP_RECORD) {
                timestampWriter = new TimestampWriter();
                timestampWriter.write(this);
            }

            report(String.format("taken test environment<br/>%s", testEnvironment));
            report(Helper.getTestReportStyle("../../" + PropHelper.LOG_FOLDER + MDC.get(PropHelper.DISCRIMINATOR_KEY), "open test execution log"));
        } else {
            throw new RuntimeException("couldn't get a valid test environment");
        }
    }

    @Override
    public void tearDownTest() throws Exception {
        logger.info("teardown test after finishing feature id {}, scenario id {}", featureId, scenarioId);
        if (recycleTestEnvironment) {
            TestCaseManager.offerTestEnvironment(testEnvironment);
            recycleTestEnvironment = false;
        }
        if (proxyWrapper != null) {
            proxyWrapper.stop();
        }
        if (driverWrapper != null) {
            try {
                closeAlerts();
                driverWrapper.quit();
            } catch (Exception ignored) {
                logger.error(ignored.getMessage(), ignored);
            }
        }
        softAssertions.assertAll();
    }

    private void closeAlerts() {
        int acceptAlerts = 0;
        while (driverWrapper.alert().isPresent() && acceptAlerts++ < 10) {
            driverWrapper.alert().accept();
        }
    }

    @BeforeClass
    @Parameters({"os", "os_version", "browser", "browser_version", "resolution"})
    protected void beforeClass(ITestContext testContext, @Optional String os, @Optional String osVersion, @Optional String browser, @Optional String browserVersion, @Optional String resolution) {
        TestCaseManager.setTestCase(this);

        suiteName = testContext.getCurrentXmlTest().getSuite().getName();
        testName = testContext.getCurrentXmlTest().getName();

        testCapabilities = new TestCapabilities();
        testCapabilities.setOs(os);
        testCapabilities.setOsVersion(osVersion);
        testCapabilities.setBrowser(browser);
        testCapabilities.setBrowserVersion(browserVersion);
        testCapabilities.setResolution(resolution);

        report(String.format("test capability<br/>%s", testCapabilities));
    }

    @AfterClass
    protected void afterClass(ITestContext testContext, ITestResult testResult) {
//        do something
    }
}
