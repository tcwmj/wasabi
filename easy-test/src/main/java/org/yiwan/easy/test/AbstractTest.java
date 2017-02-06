package org.yiwan.easy.test;

import org.assertj.core.api.SoftAssertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.ITestResult;
import org.testng.Reporter;
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
import org.yiwan.easy.util.PropertiesHelper;

import java.io.File;
import java.io.IOException;
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
    private Charset downloadFileCharset;
    private FileFormat downloadFileFormat;

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
    public void setSkipTest(boolean skipTest) {
        this.skipTest = skipTest;
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
    public Map<String, String> getTestMap() {
        return testMap;
    }

    @Override
    public IDriverWrapper getDriverWrapper() {
        return driverWrapper;
    }

    @Override
    public void setDriverWrapper(IDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;
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
    public IViewManager getViewManager() {
        return viewManager;
    }

    @Override
    public void setViewManager(IViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @Override
    public ProxyWrapper getProxyWrapper() {
        return proxyWrapper;
    }

    @Override
    public TestCapabilities getTestCapabilities() {
        return testCapabilities;
    }

    @Override
    public void setTestCapabilities(TestCapabilities testCapabilities) {
        this.testCapabilities = testCapabilities;
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
        if (PropertiesHelper.ENABLE_TRANSACTION_TIMESTAMP_RECORD || PropertiesHelper.ENABLE_TRANSACTION_SCREENSHOT_CAPTURE || PropertiesHelper.ENABLE_HTTP_ARCHIVE || PropertiesHelper.ENABLE_FILE_DOWNLOAD) {
            proxyWrapper = new ProxyWrapper();
            if (PropertiesHelper.ENABLE_WHITELIST) {
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
            if (PropertiesHelper.ENABLE_TRANSACTION_TIMESTAMP_RECORD) {
                subject.attach(new TimestampObserver(this));
            }
            if (PropertiesHelper.ENABLE_TRANSACTION_SCREENSHOT_CAPTURE) {
                subject.attach(new ScreenshotObserver(this));
            }
            if (PropertiesHelper.ENABLE_HTTP_ARCHIVE) {
                subject.attach(new HttpArchiveObserver(this));
            }
            if (PropertiesHelper.ENABLE_FILE_DOWNLOAD) {
                subject.attach(new FileDownloadObserver(this));
            }
        }
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
     * @param isProxyEnabled enable proxy by test scenario or not
     * @throws Exception
     */
    @Override
    public void setUpTest(boolean isProxyEnabled) throws Exception {
        MDC.put(PropertiesHelper.DISCRIMINATOR_KEY, getSuiteTestSeparator() + scenarioId + ".log");
        (new File(PropertiesHelper.TARGET_SCENARIO_DATA_FOLDER)).mkdirs();
        logger.info("setup test before starting feature id {}, scenario id {}", featureId, scenarioId);

        softAssertions = new SoftAssertions();
        testMap = new HashMap<>();
        skipTest = false;
        prepareToDownload = false;
        recycleTestEnvironment = false;
        proxyWrapper = null;
        downloadFileCharset = StandardCharsets.UTF_8;
        downloadFileFormat = FileFormat.DEFAULT;

        //if no available test environment, no need create webdriver and test data
        testEnvironment = TestCaseManager.pollTestEnvironment();
        if (testEnvironment != null) {
            recycleTestEnvironment = true;//must be after method setTestEnvironment
            if (isProxyEnabled && (PropertiesHelper.ENABLE_TRANSACTION_TIMESTAMP_RECORD ||
                    PropertiesHelper.ENABLE_TRANSACTION_SCREENSHOT_CAPTURE ||
                    PropertiesHelper.ENABLE_HTTP_ARCHIVE ||
                    PropertiesHelper.ENABLE_FILE_DOWNLOAD)) {
                createProxyWrapper();//create proxyWrapper must before creating driver Wrapper
            }

            if (PropertiesHelper.ENABLE_TRANSACTION_TIMESTAMP_RECORD) {
                timestampWriter = new TimestampWriter();
                timestampWriter.write(this);
            }

            report(String.format("taken test environment<br/>%s", testEnvironment));
            report(Helper.getTestReportStyle("../../" + PropertiesHelper.LOG_FOLDER + MDC.get(PropertiesHelper.DISCRIMINATOR_KEY), "open test execution log"));
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
        softAssertions.assertAll();
    }

    @Override
    public Charset getDownloadFileCharset() {
        return downloadFileCharset;
    }

    @Override
    public void setDownloadFileCharset(Charset downloadFileCharset) {
        this.downloadFileCharset = downloadFileCharset;
    }

    @Override
    public FileFormat getDownloadFileFormat() {
        return downloadFileFormat;
    }

    @Override
    public void setDownloadFileFormat(FileFormat downloadFileFormat) {
        this.downloadFileFormat = downloadFileFormat;
    }

    @Override
    public void prepareToDownloadFile() {
        prepareToDownload = true;
    }

    @Override
    public void prepareToDownloadTextFile() {
        prepareToDownload = true;
        downloadFileFormat = FileFormat.TEXT;
    }

    @Override
    public void prepareToDownloadBinaryFile() {
        prepareToDownload = true;
        downloadFileFormat = FileFormat.BINARY;
    }
}
