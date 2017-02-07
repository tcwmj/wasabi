package org.yiwan.selenium.test;

import net.lightbody.bmp.client.ClientUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.yiwan.easy.test.AbstractTestCase;
import org.yiwan.easy.test.TestCaseManager;
import org.yiwan.easy.util.Helper;
import org.yiwan.easy.util.PropertiesHelper;
import org.yiwan.selenium.model.WebTestCapabilities;
import org.yiwan.selenium.wrapper.IWebDriverWrapper;
import org.yiwan.selenium.wrapper.WebDriverWrapperFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by Kenny Wang on 3/14/2016.
 */
public abstract class AbstractWebTestCase extends AbstractTestCase {
    @Override
    public void embedScreenshot() throws IOException {
//        if (webDriverWrapper.alert().isPresent()) {
//            logger.warn("dismiss unexpected alert {} before taking screenshot", webDriverWrapper.alert().getText());
//            webDriverWrapper.alert().dismiss();
//        }
        String saveTo = Helper.randomize() + ".png";
        File screenshot = ((IWebDriverWrapper) getDriverWrapper()).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File(PropertiesHelper.RESULT_FOLDER + PropertiesHelper.SCREENSHOT_FOLDER + saveTo));
        String refPath = "../../" + PropertiesHelper.SCREENSHOT_FOLDER + saveTo;
        report(Helper.getTestReportStyle(refPath, "<img src=\"" + refPath + "\" width=\"400\" height=\"300\"/>"));
    }

    @Override
    public void createDriverWrapper() throws MalformedURLException {
        Proxy realProxy;
        if (getProxyWrapper() != null) {
            if (PropertiesHelper.ENABLE_PENETRATION_TEST) {
                getProxyWrapper().setChainedProxy(PropertiesHelper.ZAP_SERVER_HOST, PropertiesHelper.ZAP_SERVER_PORT);
            }
            realProxy = ClientUtil.createSeleniumProxy(getProxyWrapper().getProxy());
        } else {
            if (PropertiesHelper.ENABLE_PENETRATION_TEST) {
                String proxyStr = String.format("%s:%d", PropertiesHelper.ZAP_SERVER_HOST, PropertiesHelper.ZAP_SERVER_PORT);
                realProxy = new Proxy().setProxyType(Proxy.ProxyType.MANUAL)
                        .setHttpProxy(proxyStr)
                        .setSslProxy(proxyStr);
            } else {
                realProxy = new Proxy().setProxyType(Proxy.ProxyType.DIRECT);
            }
        }
        setDriverWrapper(new WebDriverWrapperFactory(getTestCapabilities(), realProxy).create());
    }

    /**
     * feature id and scenario id should be set before invoking setUpTest
     *
     * @throws Exception
     */
    @Override
    public void setUpTest(boolean isProxyEnabled) throws Exception {
        super.setUpTest(isProxyEnabled);
        createDriverWrapper();//create webdriverWrapper
        ((IWebDriverWrapper) getDriverWrapper()).deleteAllCookies();
        if (PropertiesHelper.MAXIMIZE_BROWSER) {
            ((IWebDriverWrapper) getDriverWrapper()).maximize();
        }
    }

    @Override
    public void tearDownTest() throws Exception {
        if (getDriverWrapper() != null) {
            try {
                closeAlerts();
                ((IWebDriverWrapper) getDriverWrapper()).quit();
            } catch (Exception ignored) {
            }
        }
        super.tearDownTest();
    }

    private void closeAlerts() {
        int acceptAlerts = 0;
        while (((IWebDriverWrapper) getDriverWrapper()).alert().isPresent() && acceptAlerts++ < 10) {
            ((IWebDriverWrapper) getDriverWrapper()).alert().accept();
        }
    }

    @BeforeClass
    @Parameters({"os", "os_version", "browser", "browser_version", "resolution"})
    protected void beforeClass(ITestContext testContext, @Optional String os, @Optional String osVersion, @Optional String browser, @Optional String browserVersion, @Optional String resolution) {
        TestCaseManager.setTestCase(this);

        setSuiteName(testContext.getCurrentXmlTest().getSuite().getName());
        setTestName(testContext.getCurrentXmlTest().getName());

        setTestCapabilities(new WebTestCapabilities());
        ((WebTestCapabilities) getTestCapabilities()).setOs(os == null ? System.getProperty("os") : os);
        ((WebTestCapabilities) getTestCapabilities()).setOsVersion(osVersion == null ? System.getProperty("os.version") : osVersion);
        ((WebTestCapabilities) getTestCapabilities()).setBrowser(browser == null ? System.getProperty("browser", PropertiesHelper.DEFAULT_BROWSER) : browser);
        ((WebTestCapabilities) getTestCapabilities()).setBrowserVersion(browserVersion == null ? System.getProperty("browser.version") : browserVersion);
        ((WebTestCapabilities) getTestCapabilities()).setResolution(resolution == null ? System.getProperty("resolution") : resolution);

        report(String.format("test capability<br/>%s", getTestCapabilities()));
    }

    @AfterClass
    protected void afterClass() {
//        do something
    }
}
