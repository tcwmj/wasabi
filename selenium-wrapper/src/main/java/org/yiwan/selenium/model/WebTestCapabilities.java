package org.yiwan.selenium.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yiwan.easy.model.TestCapabilities;

/**
 * Created by Kenny Wang on 12/29/2016.
 */
public class WebTestCapabilities extends TestCapabilities {
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(WebTestCapabilities.class);

    private String os;
    private String osVersion;
    private String browser;
    private String browserVersion;
    private String resolution;

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public DesiredCapabilities toDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setPlatform(Platform.fromString(os));
        capabilities.setCapability("os_version", osVersion);
        capabilities.setBrowserName(browser);
        capabilities.setVersion(browserVersion);
        capabilities.setCapability("resolution", resolution);
        return capabilities;
    }
}
