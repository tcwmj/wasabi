package org.yiwan.appium.model;


import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by Kenny Wang on 4/2/2016.
 */
public class IOSTestCapabilities extends AppiumTestCapabilities {
    private String calendarFormat;
    private String bundleId;
    private Long launchTimeout;
    private Boolean locationServicesEnabled;
    private Boolean locationServicesAuthorized;
    private Boolean autoAcceptAlerts;
    private Boolean autoDismissAlerts;
    private Boolean nativeInstrumentsLib;
    private Boolean nativeWebTap;
    private String safariInitialUrl;
    private Boolean safariAllowPopups;
    private Boolean safariIgnoreFraudWarning;
    private Boolean safariOpenLinksInBackground;
    private Boolean keepKeyChains;
    private String localizableStringsDir;
    private String processArguments;
    private Long interKeyDelay;
    private String showIOSLog;
    private String sendKeyStrategy;
    private String screenshotWaitTimeout;
    private String waitForAppScript;
    private Integer webviewConnectRetries;
    private String appName;

    public IOSTestCapabilities() {
        super();
    }

    public IOSTestCapabilities(String automationName, String platformName, String platformVersion, String deviceName, String app, String browserName, Integer newCommandTimeout, String language, String locale, String udid, String orientation, Boolean autoWebview, Boolean noReset, Boolean fullReset, String calendarFormat, String bundleId, Long launchTimeout, Boolean locationServicesEnabled, Boolean locationServicesAuthorized, Boolean autoAcceptAlerts, Boolean autoDismissAlerts, Boolean nativeInstrumentsLib, Boolean nativeWebTap, String safariInitialUrl, Boolean safariAllowPopups, Boolean safariIgnoreFraudWarning, Boolean safariOpenLinksInBackground, Boolean keepKeyChains, String localizableStringsDir, String processArguments, Long interKeyDelay, String showIOSLog, String sendKeyStrategy, String screenshotWaitTimeout, String waitForAppScript, Integer webviewConnectRetries, String appName) {
        super(automationName, platformName, platformVersion, deviceName, app, browserName, newCommandTimeout, language, locale, udid, orientation, autoWebview, noReset, fullReset);
        if (calendarFormat != null) {
            this.calendarFormat = calendarFormat;
        }
        if (bundleId != null) {
            this.bundleId = bundleId;
        }
        if (launchTimeout != null) {
            this.launchTimeout = launchTimeout;
        }
        if (locationServicesEnabled != null) {
            this.locationServicesEnabled = locationServicesEnabled;
        }
        if (locationServicesAuthorized != null) {
            this.locationServicesAuthorized = locationServicesAuthorized;
        }
        if (autoAcceptAlerts != null) {
            this.autoAcceptAlerts = autoAcceptAlerts;
        }
        if (autoDismissAlerts != null) {
            this.autoDismissAlerts = autoDismissAlerts;
        }
        if (nativeInstrumentsLib != null) {
            this.nativeInstrumentsLib = nativeInstrumentsLib;
        }
        if (nativeWebTap != null) {
            this.nativeWebTap = nativeWebTap;
        }
        if (safariInitialUrl != null) {
            this.safariInitialUrl = safariInitialUrl;
        }
        if (safariAllowPopups != null) {
            this.safariAllowPopups = safariAllowPopups;
        }
        if (safariIgnoreFraudWarning != null) {
            this.safariIgnoreFraudWarning = safariIgnoreFraudWarning;
        }
        if (safariOpenLinksInBackground != null) {
            this.safariOpenLinksInBackground = safariOpenLinksInBackground;
        }
        if (keepKeyChains != null) {
            this.keepKeyChains = keepKeyChains;
        }
        if (localizableStringsDir != null) {
            this.localizableStringsDir = localizableStringsDir;
        }
        if (processArguments != null) {
            this.processArguments = processArguments;
        }
        if (interKeyDelay != null) {
            this.interKeyDelay = interKeyDelay;
        }
        if (showIOSLog != null) {
            this.showIOSLog = showIOSLog;
        }
        if (sendKeyStrategy != null) {
            this.sendKeyStrategy = sendKeyStrategy;
        }
        if (screenshotWaitTimeout != null) {
            this.screenshotWaitTimeout = screenshotWaitTimeout;
        }
        if (waitForAppScript != null) {
            this.waitForAppScript = waitForAppScript;
        }
        if (webviewConnectRetries != null) {
            this.webviewConnectRetries = webviewConnectRetries;
        }
        if (appName != null) {
            this.appName = appName;
        }
    }

    public String getCalendarFormat() {
        return calendarFormat;
    }

    public void setCalendarFormat(String calendarFormat) {
        this.calendarFormat = calendarFormat;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public Long getLaunchTimeout() {
        return launchTimeout;
    }

    public void setLaunchTimeout(Long launchTimeout) {
        this.launchTimeout = launchTimeout;
    }

    public Boolean getLocationServicesEnabled() {
        return locationServicesEnabled;
    }

    public void setLocationServicesEnabled(Boolean locationServicesEnabled) {
        this.locationServicesEnabled = locationServicesEnabled;
    }

    public Boolean getLocationServicesAuthorized() {
        return locationServicesAuthorized;
    }

    public void setLocationServicesAuthorized(Boolean locationServicesAuthorized) {
        this.locationServicesAuthorized = locationServicesAuthorized;
    }

    public Boolean getAutoAcceptAlerts() {
        return autoAcceptAlerts;
    }

    public void setAutoAcceptAlerts(Boolean autoAcceptAlerts) {
        this.autoAcceptAlerts = autoAcceptAlerts;
    }

    public Boolean getAutoDismissAlerts() {
        return autoDismissAlerts;
    }

    public void setAutoDismissAlerts(Boolean autoDismissAlerts) {
        this.autoDismissAlerts = autoDismissAlerts;
    }

    public Boolean getNativeInstrumentsLib() {
        return nativeInstrumentsLib;
    }

    public void setNativeInstrumentsLib(Boolean nativeInstrumentsLib) {
        this.nativeInstrumentsLib = nativeInstrumentsLib;
    }

    public Boolean getNativeWebTap() {
        return nativeWebTap;
    }

    public void setNativeWebTap(Boolean nativeWebTap) {
        this.nativeWebTap = nativeWebTap;
    }

    public String getSafariInitialUrl() {
        return safariInitialUrl;
    }

    public void setSafariInitialUrl(String safariInitialUrl) {
        this.safariInitialUrl = safariInitialUrl;
    }

    public Boolean getSafariAllowPopups() {
        return safariAllowPopups;
    }

    public void setSafariAllowPopups(Boolean safariAllowPopups) {
        this.safariAllowPopups = safariAllowPopups;
    }

    public Boolean getSafariIgnoreFraudWarning() {
        return safariIgnoreFraudWarning;
    }

    public void setSafariIgnoreFraudWarning(Boolean safariIgnoreFraudWarning) {
        this.safariIgnoreFraudWarning = safariIgnoreFraudWarning;
    }

    public Boolean getSafariOpenLinksInBackground() {
        return safariOpenLinksInBackground;
    }

    public void setSafariOpenLinksInBackground(Boolean safariOpenLinksInBackground) {
        this.safariOpenLinksInBackground = safariOpenLinksInBackground;
    }

    public Boolean getKeepKeyChains() {
        return keepKeyChains;
    }

    public void setKeepKeyChains(Boolean keepKeyChains) {
        this.keepKeyChains = keepKeyChains;
    }

    public String getLocalizableStringsDir() {
        return localizableStringsDir;
    }

    public void setLocalizableStringsDir(String localizableStringsDir) {
        this.localizableStringsDir = localizableStringsDir;
    }

    public String getProcessArguments() {
        return processArguments;
    }

    public void setProcessArguments(String processArguments) {
        this.processArguments = processArguments;
    }

    public Long getInterKeyDelay() {
        return interKeyDelay;
    }

    public void setInterKeyDelay(Long interKeyDelay) {
        this.interKeyDelay = interKeyDelay;
    }

    public String getShowIOSLog() {
        return showIOSLog;
    }

    public void setShowIOSLog(String showIOSLog) {
        this.showIOSLog = showIOSLog;
    }

    public String getSendKeyStrategy() {
        return sendKeyStrategy;
    }

    public void setSendKeyStrategy(String sendKeyStrategy) {
        this.sendKeyStrategy = sendKeyStrategy;
    }

    public String getScreenshotWaitTimeout() {
        return screenshotWaitTimeout;
    }

    public void setScreenshotWaitTimeout(String screenshotWaitTimeout) {
        this.screenshotWaitTimeout = screenshotWaitTimeout;
    }

    public String getWaitForAppScript() {
        return waitForAppScript;
    }

    public void setWaitForAppScript(String waitForAppScript) {
        this.waitForAppScript = waitForAppScript;
    }

    public Integer getWebviewConnectRetries() {
        return webviewConnectRetries;
    }

    public void setWebviewConnectRetries(Integer webviewConnectRetries) {
        this.webviewConnectRetries = webviewConnectRetries;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public DesiredCapabilities toDesiredCapabilities() {
        DesiredCapabilities capabilities = super.toDesiredCapabilities();
        return capabilities;
    }
}
