package org.yiwan.appium.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yiwan.easy.model.TestCapabilities;
import org.yiwan.easy.util.PropHelper;

/**
 * Created by Kenny Wang on 4/2/2016.
 */
public abstract class AppiumCapabilities extends TestCapabilities {
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(AppiumCapabilities.class);

    private String automationName = PropHelper.getProperty("appium.automationName");
    private String platformName = PropHelper.getProperty("appium.platformName");
    private String platformVersion = PropHelper.getProperty("appium.platformVersion");
    private String deviceName = PropHelper.getProperty("appium.deviceName");
    private String app = PropHelper.getProperty("appium.app");
    private String browserName;
    private Integer newCommandTimeout = Integer.parseInt(PropHelper.getProperty("appium.newCommandTimeout"));
    private String language;
    private String locale;
    private String udid;
    private String orientation;
    private Boolean autoWebview;
    private Boolean noReset = Boolean.parseBoolean(PropHelper.getProperty("appium.noReset"));
    private Boolean fullReset = Boolean.parseBoolean(PropHelper.getProperty("appium.fullReset"));

    public AppiumCapabilities() {
        super();
    }

    public AppiumCapabilities(String automationName, String platformName, String platformVersion, String deviceName, String app, String browserName, Integer newCommandTimeout, String language, String locale, String udid, String orientation, Boolean autoWebview, Boolean noReset, Boolean fullReset) {
        if (automationName != null) {
            this.automationName = automationName;
        }
        if (platformName != null) {
            this.platformName = platformName;
        }
        if (platformVersion != null) {
            this.platformVersion = platformVersion;
        }
        if (deviceName != null) {
            this.deviceName = deviceName;
        }
        if (app != null) {
            this.app = app;
        }
        if (browserName != null) {
            this.browserName = browserName;
        }
        if (newCommandTimeout != null) {
            this.newCommandTimeout = newCommandTimeout;
        }
        if (language != null) {
            this.language = language;
        }
        if (locale != null) {
            this.locale = locale;
        }
        if (udid != null) {
            this.udid = udid;
        }
        if (orientation != null) {
            this.orientation = orientation;
        }
        if (autoWebview != null) {
            this.autoWebview = autoWebview;
        }
        if (noReset != null) {
            this.noReset = noReset;
        }
        if (fullReset != null) {
            this.fullReset = fullReset;
        }
    }

    public String getAutomationName() {
        return automationName;
    }

    public void setAutomationName(String automationName) {
        this.automationName = automationName;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public Integer getNewCommandTimeout() {
        return newCommandTimeout;
    }

    public void setNewCommandTimeout(Integer newCommandTimeout) {
        this.newCommandTimeout = newCommandTimeout;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public Boolean isAutoWebview() {
        return autoWebview;
    }

    public void setAutoWebview(Boolean autoWebview) {
        this.autoWebview = autoWebview;
    }

    public Boolean isNoReset() {
        return noReset;
    }

    public void setNoReset(Boolean noReset) {
        this.noReset = noReset;
    }

    public Boolean isFullReset() {
        return fullReset;
    }

    public void setFullReset(Boolean fullReset) {
        this.fullReset = fullReset;
    }

    @Override
    public String toString() {
        try {
            return (new ObjectMapper()).writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return "";
        }
    }

    public DesiredCapabilities toDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationName);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        capabilities.setCapability(MobileCapabilityType.APP, app);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, newCommandTimeout);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, noReset);
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, fullReset);
        return capabilities;
    }
}
