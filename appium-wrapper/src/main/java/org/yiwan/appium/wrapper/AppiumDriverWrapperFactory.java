package org.yiwan.appium.wrapper;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yiwan.appium.model.AppiumTestCapabilities;
import org.yiwan.appium.model.FirefoxOSTestCapabilities;
import org.yiwan.appium.model.IOSTestCapabilities;
import org.yiwan.easy.model.TestCapabilities;
import org.yiwan.easy.util.PropertiesHelper;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Kenny Wang
 */
public class AppiumDriverWrapperFactory {
    private final static Logger logger = LoggerFactory.getLogger(AppiumDriverWrapperFactory.class);
    private URL url;
    private TestCapabilities testCapabilities;
    private final Proxy seleniumProxy;

    public AppiumDriverWrapperFactory(TestCapabilities testCapabilities) throws MalformedURLException {
        this(new URL(PropertiesHelper.REMOTE_ADDRESS), testCapabilities, null);
    }

    public AppiumDriverWrapperFactory(TestCapabilities testCapabilities, Proxy seleniumProxy) throws MalformedURLException {
        this(new URL(PropertiesHelper.REMOTE_ADDRESS), testCapabilities, seleniumProxy);
    }

    public AppiumDriverWrapperFactory(URL url, TestCapabilities testCapabilities, Proxy seleniumProxy) {
        this.url = url;
        this.testCapabilities = testCapabilities;
        this.seleniumProxy = seleniumProxy;
    }

    public IAppiumDriverWrapper create() {
        return PropertiesHelper.DUMMY_TEST ? new DummyAppDriverWrapper() : createAppiumDriverWrapper();
    }

    private IAppiumDriverWrapper createAppiumDriverWrapper() {
        if (testCapabilities instanceof IOSTestCapabilities) {
            return createIOSDriverWrapper();
        } else if (testCapabilities instanceof FirefoxOSTestCapabilities) {
            return createFirefoxOSDriverWrapper();
        } else {
            return createAndroidDriverWrapper();
        }
    }

    private IAppiumDriverWrapper createFirefoxOSDriverWrapper() {
        AppiumDriver driver = new IOSDriver(url, ((AppiumTestCapabilities) testCapabilities).toDesiredCapabilities());
        return new FirefoxOSDriverWrapper(driver);
    }

    private IAppiumDriverWrapper createIOSDriverWrapper() {
        AppiumDriver driver = new IOSDriver(url, ((AppiumTestCapabilities) testCapabilities).toDesiredCapabilities());
        return new IOSDriverWrapper(driver);
    }

    private IAppiumDriverWrapper createAndroidDriverWrapper() {
        AppiumDriver driver = new AndroidDriver(url, ((AppiumTestCapabilities) testCapabilities).toDesiredCapabilities());
        return new AndroidDriverWrapper(driver);
    }
}