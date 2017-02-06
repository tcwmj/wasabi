package org.yiwan.easy.test;

import org.assertj.core.api.SoftAssertions;
import org.testng.ITestResult;
import org.yiwan.easy.bmproxy.ProxyWrapper;
import org.yiwan.easy.bmproxy.TimestampWriter;
import org.yiwan.easy.model.TestCapabilities;
import org.yiwan.easy.model.TestEnvironment;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.Map;

public interface ITestBase {

    String getDownloadFile();

    void setDownloadFile(String downloadFile);

    String getDefaultDownloadFileName();

    void setDefaultDownloadFileName(String defaultDownloadFileName);

    String getTransactionName();

    String getSuiteName();

    void setSuiteName(String suiteName);

    String getTestName();

    void setTestName(String testName);

    String getScenarioId();

    void setScenarioId(String scenarioId);

    String getFeatureId();

    void setFeatureId(String featureId);

    String getSuiteTestSeparator();

    boolean isSkipTest();

    void setSkipTest(boolean skipTest);

    boolean isPrepareToDownload();

    void setPrepareToDownload(boolean prepareToDownload);

    Map<String, String> getTestMap();

    IDriverWrapper getDriverWrapper();

    void setDriverWrapper(IDriverWrapper driverWrapper);

    ITestDataManager getTestDataManager();

    void setTestDataManager(ITestDataManager testDataManager);

    IViewManager getViewManager();

    void setViewManager(IViewManager viewManager);

    ProxyWrapper getProxyWrapper();

    TestCapabilities getTestCapabilities();

    void setTestCapabilities(TestCapabilities testCapabilities);

    TestEnvironment getTestEnvironment();

    TimestampWriter getTimestampWriter();

    SoftAssertions getSoftAssertions();

    /**
     * log the content into the report
     *
     * @param s
     */
    void report(String s);

    /**
     * Invoked each time before a test will be invoked. The
     * <code>ITestResult</code> is only partially filled with the references to
     * class, method, start millis and status.
     *
     * @param result the partially filled <code>ITestResult</code>
     * @see ITestResult#STARTED
     */
    void onTestStart(ITestResult result);

    /**
     * Invoked each time a test succeeds.
     *
     * @param result <code>ITestResult</code> containing information about the run
     *               test
     * @see ITestResult#SUCCESS
     */
    void onTestSuccess(ITestResult result);

    /**
     * Invoked each time a test fails.
     *
     * @param result <code>ITestResult</code> containing information about the run
     *               test
     * @see ITestResult#FAILURE
     */
    void onTestFailure(ITestResult result) throws IOException;

    /**
     * Invoked each time a test is skipped.
     *
     * @param result <code>ITestResult</code> containing information about the run
     *               test
     * @see ITestResult#SKIP
     */
    void onTestSkipped(ITestResult result);

    void embedScreenshot() throws IOException;

    void embedTestLog() throws IOException;

    void embedTestData(Object o) throws Exception;

    void startTransaction(String transactionName);

    void stopTransaction();

    void createProxyWrapper();

    void createDriverWrapper() throws MalformedURLException;

    void setUpTest() throws Exception;

    void setUpTest(boolean isProxyEnabled) throws Exception;

    void tearDownTest() throws Exception;

    Charset getDownloadFileCharset();

    void setDownloadFileCharset(Charset downloadFileCharset);

    FileFormat getDownloadFileFormat();

    void setDownloadFileFormat(FileFormat downloadFileFormat);

    void prepareToDownloadFile();

    void prepareToDownloadTextFile();

    void prepareToDownloadBinaryFile();
}