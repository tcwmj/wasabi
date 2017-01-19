package org.yiwan.appium.wrapper;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yiwan.appium.model.AppiumCapabilities;
import org.yiwan.appium.model.FirefoxOSCapabilities;
import org.yiwan.appium.model.IOSCapabilities;
import org.yiwan.appium.util.PropHelper;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Kenny Wang
 */
public class AppiumDriverWrapperFactory {
    private final static Logger logger = LoggerFactory.getLogger(AppiumDriverWrapperFactory.class);
    private URL url;
    private AppiumCapabilities appiumCapabilities;
    private final Proxy seleniumProxy;

    public AppiumDriverWrapperFactory(AppiumCapabilities appiumCapabilities) throws MalformedURLException {
        this(new URL(PropHelper.REMOTE_ADDRESS), appiumCapabilities, null);
    }

    public AppiumDriverWrapperFactory(AppiumCapabilities appiumCapabilities, Proxy seleniumProxy) throws MalformedURLException {
        this(new URL(PropHelper.REMOTE_ADDRESS), appiumCapabilities, seleniumProxy);
    }

    public AppiumDriverWrapperFactory(URL url, AppiumCapabilities appiumCapabilities, Proxy seleniumProxy) {
        this.url = url;
        this.appiumCapabilities = appiumCapabilities;
        this.seleniumProxy = seleniumProxy;
    }

    public IAppiumDriverWrapper create() {
        return PropHelper.DUMMY_TEST ? new DummyAppDriverWrapper() : createAppiumDriverWrapper();
    }

    private IAppiumDriverWrapper createAppiumDriverWrapper() {
        if (appiumCapabilities instanceof IOSCapabilities) {
            return createIOSDriverWrapper();
        } else if (appiumCapabilities instanceof FirefoxOSCapabilities) {
            return createFirefoxOSDriverWrapper();
        } else {
            return createAndroidDriverWrapper();
        }
    }


    private IAppiumDriverWrapper createFirefoxOSDriverWrapper() {
        AppiumDriver driver = new IOSDriver(url, appiumCapabilities.toDesiredCapabilities());
        return new FirefoxOSDriverWrapper(driver);
    }

    private IAppiumDriverWrapper createIOSDriverWrapper() {
        AppiumDriver driver = new IOSDriver(url, appiumCapabilities.toDesiredCapabilities());
        return new IOSDriverWrapper(driver);
    }

    private IAppiumDriverWrapper createAndroidDriverWrapper() {
        AppiumDriver driver = new AndroidDriver(url, appiumCapabilities.toDesiredCapabilities());
        return new AndroidDriverWrapper(driver);
    }
}