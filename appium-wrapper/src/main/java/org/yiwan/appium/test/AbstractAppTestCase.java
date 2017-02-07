package org.yiwan.appium.test;

import net.lightbody.bmp.client.ClientUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.yiwan.appium.model.AndroidTestCapabilities;
import org.yiwan.appium.model.FirefoxOSTestCapabilities;
import org.yiwan.appium.model.IOSTestCapabilities;
import org.yiwan.appium.wrapper.AppiumDriverWrapper;
import org.yiwan.appium.wrapper.AppiumDriverWrapperFactory;
import org.yiwan.appium.wrapper.IAppiumDriverWrapper;
import org.yiwan.easy.test.AbstractTestCase;
import org.yiwan.easy.test.TestCaseManager;
import org.yiwan.easy.util.Helper;
import org.yiwan.easy.util.PropertiesHelper;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by Kenny Wang on 3/14/2016.
 */
public abstract class AbstractAppTestCase extends AbstractTestCase {
    @Override
    public void embedScreenshot() throws IOException {
        String saveTo = Helper.randomize() + ".png";
        File screenshot = ((AppiumDriverWrapper) getDriverWrapper()).getScreenshotAs(OutputType.FILE);
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
        setDriverWrapper(new AppiumDriverWrapperFactory(getTestCapabilities(), realProxy).create());
    }

    @Override
    public void setUpTest(boolean isProxyEnabled) throws Exception {
        super.setUpTest(isProxyEnabled);
        createDriverWrapper();//create appiumDriverWrapper
    }

    @Override
    public void tearDownTest() throws Exception {
        if (getDriverWrapper() != null) {
            try {
                ((IAppiumDriverWrapper) getDriverWrapper()).quit();
            } catch (Exception ignored) {
            }
        }
        super.tearDownTest();
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

        String platform = platformName == null ? PropertiesHelper.getProperty("appium.platformName") : platformName;
        switch (platform) {
            case "ios":
                setTestCapabilities(new IOSTestCapabilities(automationName, platformName, platformVersion, deviceName, app, browserName, newCommandTimeout, language, locale, udid, orientation, autoWebview, noReset, fullReset, calendarFormat, bundleId, launchTimeout, locationServicesEnabled, locationServicesAuthorized, autoAcceptAlerts, autoDismissAlerts, nativeInstrumentsLib, nativeWebTap, safariInitialUrl, safariAllowPopups, safariIgnoreFraudWarning, safariOpenLinksInBackground, keepKeyChains, localizableStringsDir, processArguments, interKeyDelay, showIOSLog, sendKeyStrategy, screenshotWaitTimeout, waitForAppScript, webviewConnectRetries, appName));
                break;
            case "firefoxos":
                setTestCapabilities(new FirefoxOSTestCapabilities());
                break;
            default:
                setTestCapabilities(new AndroidTestCapabilities(automationName, platformName, platformVersion, deviceName, app, browserName, newCommandTimeout, language, locale, udid, orientation, autoWebview, noReset, fullReset, appActivity, appPackage, appWaitActivity, appWaitPackage, deviceReadyTimeout, androidCoverage, enablePerformanceLogging, androidDeviceReadyTimeout, adbPort, androidDeviceSocket, avd, avdLaunchTimeout, avdReadyTimeout, avdArgs, useKeystore, keystorePath, keystorePassword, keyAlias, keyPassword, chromedriverExecutable, autoWebviewTimeout, intentAction, intentCategory, intentFlags, optionalIntentArguments, dontStopAppOnReset, unicodeKeyboard, resetKeyboard, noSign, ignoreUnimportantViews, disableAndroidWatchers, chromeOptions, recreateChromeDriverSessions, nativeWebScreenshot));
        }

        report(String.format("test capability<br/>%s", getTestCapabilities()));
    }

    @AfterClass
    protected void afterClass(ITestContext testContext, ITestResult testResult) {
//        do something
    }
}
