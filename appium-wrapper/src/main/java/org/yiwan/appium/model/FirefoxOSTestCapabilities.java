package org.yiwan.appium.model;


import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by Kenny Wang on 4/2/2016.
 */
public class FirefoxOSTestCapabilities extends AppiumTestCapabilities {

    public FirefoxOSTestCapabilities() {
        super();
    }

    public FirefoxOSTestCapabilities(String automationName, String platformName, String platformVersion, String deviceName, String app, String browserName, Integer newCommandTimeout, String language, String locale, String udid, String orientation, Boolean autoWebview, Boolean noReset, Boolean fullReset) {
        super(automationName, platformName, platformVersion, deviceName, app, browserName, newCommandTimeout, language, locale, udid, orientation, autoWebview, noReset, fullReset);
    }

    @Override
    public DesiredCapabilities toDesiredCapabilities() {
        DesiredCapabilities capabilities = super.toDesiredCapabilities();
        return capabilities;
    }
}
