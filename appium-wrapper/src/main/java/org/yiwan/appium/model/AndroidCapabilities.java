package org.yiwan.appium.model;


import io.appium.java_client.remote.AndroidMobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.yiwan.appium.util.PropHelper;

/**
 * Created by Kenny Wang on 4/2/2016.
 */
public class AndroidCapabilities extends AppiumCapabilities {
    private String appActivity = PropHelper.getProperty("android.appActivity");
    private String appPackage = PropHelper.getProperty("android.appPackage");
    private String appWaitActivity;
    private String appWaitPackage;
    private Integer deviceReadyTimeout;
    private String androidCoverage;
    private Boolean enablePerformanceLogging;
    private Integer androidDeviceReadyTimeout;
    private Integer adbPort;
    private String androidDeviceSocket;
    private String avd;
    private Long avdLaunchTimeout;
    private Long avdReadyTimeout;
    private String avdArgs;
    private Boolean useKeystore;
    private String keystorePath;
    private String keystorePassword;
    private String keyAlias;
    private String keyPassword;
    private String chromedriverExecutable;
    private Long autoWebviewTimeout;
    private String intentAction;
    private String intentCategory;
    private String intentFlags;
    private String optionalIntentArguments;
    private Boolean dontStopAppOnReset;
    private Boolean unicodeKeyboard;
    private Boolean resetKeyboard;
    private Boolean noSign;
    private Boolean ignoreUnimportantViews;
    private Boolean disableAndroidWatchers;
    private String chromeOptions;
    private Boolean recreateChromeDriverSessions;
    private Boolean nativeWebScreenshot;

    public AndroidCapabilities() {
        super();
    }

    public AndroidCapabilities(String automationName, String platformName, String platformVersion, String deviceName, String app, String browserName, Integer newCommandTimeout, String language, String locale, String udid, String orientation, Boolean autoWebview, Boolean noReset, Boolean fullReset, String appActivity, String appPackage, String appWaitActivity, String appWaitPackage, Integer deviceReadyTimeout, String androidCoverage, Boolean enablePerformanceLogging, Integer androidDeviceReadyTimeout, Integer adbPort, String androidDeviceSocket, String avd, Long avdLaunchTimeout, Long avdReadyTimeout, String avdArgs, Boolean useKeystore, String keystorePath, String keystorePassword, String keyAlias, String keyPassword, String chromedriverExecutable, Long autoWebviewTimeout, String intentAction, String intentCategory, String intentFlags, String optionalIntentArguments, Boolean dontStopAppOnReset, Boolean unicodeKeyboard, Boolean resetKeyboard, Boolean noSign, Boolean ignoreUnimportantViews, Boolean disableAndroidWatchers, String chromeOptions, Boolean recreateChromeDriverSessions, Boolean nativeWebScreenshot) {
        super(automationName, platformName, platformVersion, deviceName, app, browserName, newCommandTimeout, language, locale, udid, orientation, autoWebview, noReset, fullReset);
        if (appActivity != null) {
            this.appActivity = appActivity;
        }
        if (appPackage != null) {
            this.appPackage = appPackage;
        }
        if (appWaitActivity != null) {
            this.appWaitActivity = appWaitActivity;
        }
        if (appWaitPackage != null) {
            this.appWaitPackage = appWaitPackage;
        }
        if (deviceReadyTimeout != null) {
            this.deviceReadyTimeout = deviceReadyTimeout;
        }
        if (androidCoverage != null) {
            this.androidCoverage = androidCoverage;
        }
        if (enablePerformanceLogging != null) {
            this.enablePerformanceLogging = enablePerformanceLogging;
        }
        if (androidDeviceReadyTimeout != null) {
            this.androidDeviceReadyTimeout = androidDeviceReadyTimeout;
        }
        if (adbPort != null) {
            this.adbPort = adbPort;
        }
        if (androidDeviceSocket != null) {
            this.androidDeviceSocket = androidDeviceSocket;
        }
        if (avd != null) {
            this.avd = avd;
        }
        if (avdLaunchTimeout != null) {
            this.avdLaunchTimeout = avdLaunchTimeout;
        }
        if (avdReadyTimeout != null) {
            this.avdReadyTimeout = avdReadyTimeout;
        }
        if (avdArgs != null) {
            this.avdArgs = avdArgs;
        }
        if (useKeystore != null) {
            this.useKeystore = useKeystore;
        }
        if (keystorePath != null) {
            this.keystorePath = keystorePath;
        }
        if (keystorePassword != null) {
            this.keystorePassword = keystorePassword;
        }
        if (keyAlias != null) {
            this.keyAlias = keyAlias;
        }
        if (keyPassword != null) {
            this.keyPassword = keyPassword;
        }
        if (chromedriverExecutable != null) {
            this.chromedriverExecutable = chromedriverExecutable;
        }
        if (autoWebviewTimeout != null) {
            this.autoWebviewTimeout = autoWebviewTimeout;
        }
        if (intentAction != null) {
            this.intentAction = intentAction;
        }
        if (intentCategory != null) {
            this.intentCategory = intentCategory;
        }
        if (intentFlags != null) {
            this.intentFlags = intentFlags;
        }
        if (optionalIntentArguments != null) {
            this.optionalIntentArguments = optionalIntentArguments;
        }
        if (dontStopAppOnReset != null) {
            this.dontStopAppOnReset = dontStopAppOnReset;
        }
        if (unicodeKeyboard != null) {
            this.unicodeKeyboard = unicodeKeyboard;
        }
        if (resetKeyboard != null) {
            this.resetKeyboard = resetKeyboard;
        }
        if (noSign != null) {
            this.noSign = noSign;
        }
        if (ignoreUnimportantViews != null) {
            this.ignoreUnimportantViews = ignoreUnimportantViews;
        }
        if (disableAndroidWatchers != null) {
            this.disableAndroidWatchers = disableAndroidWatchers;
        }
        if (chromeOptions != null) {
            this.chromeOptions = chromeOptions;
        }
        if (recreateChromeDriverSessions != null) {
            this.recreateChromeDriverSessions = recreateChromeDriverSessions;
        }
        if (nativeWebScreenshot != null) {
            this.nativeWebScreenshot = nativeWebScreenshot;
        }
    }

    public String getAppActivity() {
        return appActivity;
    }

    public void setAppActivity(String appActivity) {
        this.appActivity = appActivity;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public String getAppWaitActivity() {
        return appWaitActivity;
    }

    public void setAppWaitActivity(String appWaitActivity) {
        this.appWaitActivity = appWaitActivity;
    }

    public String getAppWaitPackage() {
        return appWaitPackage;
    }

    public void setAppWaitPackage(String appWaitPackage) {
        this.appWaitPackage = appWaitPackage;
    }

    public Integer getDeviceReadyTimeout() {
        return deviceReadyTimeout;
    }

    public void setDeviceReadyTimeout(Integer deviceReadyTimeout) {
        this.deviceReadyTimeout = deviceReadyTimeout;
    }

    public String getAndroidCoverage() {
        return androidCoverage;
    }

    public void setAndroidCoverage(String androidCoverage) {
        this.androidCoverage = androidCoverage;
    }

    public Boolean getEnablePerformanceLogging() {
        return enablePerformanceLogging;
    }

    public void setEnablePerformanceLogging(Boolean enablePerformanceLogging) {
        this.enablePerformanceLogging = enablePerformanceLogging;
    }

    public Integer getAndroidDeviceReadyTimeout() {
        return androidDeviceReadyTimeout;
    }

    public void setAndroidDeviceReadyTimeout(Integer androidDeviceReadyTimeout) {
        this.androidDeviceReadyTimeout = androidDeviceReadyTimeout;
    }

    public Integer getAdbPort() {
        return adbPort;
    }

    public void setAdbPort(Integer adbPort) {
        this.adbPort = adbPort;
    }

    public String getAndroidDeviceSocket() {
        return androidDeviceSocket;
    }

    public void setAndroidDeviceSocket(String androidDeviceSocket) {
        this.androidDeviceSocket = androidDeviceSocket;
    }

    public String getAvd() {
        return avd;
    }

    public void setAvd(String avd) {
        this.avd = avd;
    }

    public Long getAvdLaunchTimeout() {
        return avdLaunchTimeout;
    }

    public void setAvdLaunchTimeout(Long avdLaunchTimeout) {
        this.avdLaunchTimeout = avdLaunchTimeout;
    }

    public Long getAvdReadyTimeout() {
        return avdReadyTimeout;
    }

    public void setAvdReadyTimeout(Long avdReadyTimeout) {
        this.avdReadyTimeout = avdReadyTimeout;
    }

    public String getAvdArgs() {
        return avdArgs;
    }

    public void setAvdArgs(String avdArgs) {
        this.avdArgs = avdArgs;
    }

    public Boolean getUseKeystore() {
        return useKeystore;
    }

    public void setUseKeystore(Boolean useKeystore) {
        this.useKeystore = useKeystore;
    }

    public String getKeystorePath() {
        return keystorePath;
    }

    public void setKeystorePath(String keystorePath) {
        this.keystorePath = keystorePath;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }

    public void setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
    }

    public String getKeyAlias() {
        return keyAlias;
    }

    public void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }

    public String getKeyPassword() {
        return keyPassword;
    }

    public void setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
    }

    public String getChromedriverExecutable() {
        return chromedriverExecutable;
    }

    public void setChromedriverExecutable(String chromedriverExecutable) {
        this.chromedriverExecutable = chromedriverExecutable;
    }

    public Long getAutoWebviewTimeout() {
        return autoWebviewTimeout;
    }

    public void setAutoWebviewTimeout(Long autoWebviewTimeout) {
        this.autoWebviewTimeout = autoWebviewTimeout;
    }

    public String getIntentAction() {
        return intentAction;
    }

    public void setIntentAction(String intentAction) {
        this.intentAction = intentAction;
    }

    public String getIntentCategory() {
        return intentCategory;
    }

    public void setIntentCategory(String intentCategory) {
        this.intentCategory = intentCategory;
    }

    public String getIntentFlags() {
        return intentFlags;
    }

    public void setIntentFlags(String intentFlags) {
        this.intentFlags = intentFlags;
    }

    public String getOptionalIntentArguments() {
        return optionalIntentArguments;
    }

    public void setOptionalIntentArguments(String optionalIntentArguments) {
        this.optionalIntentArguments = optionalIntentArguments;
    }

    public Boolean getDontStopAppOnReset() {
        return dontStopAppOnReset;
    }

    public void setDontStopAppOnReset(Boolean dontStopAppOnReset) {
        this.dontStopAppOnReset = dontStopAppOnReset;
    }

    public Boolean getUnicodeKeyboard() {
        return unicodeKeyboard;
    }

    public void setUnicodeKeyboard(Boolean unicodeKeyboard) {
        this.unicodeKeyboard = unicodeKeyboard;
    }

    public Boolean getResetKeyboard() {
        return resetKeyboard;
    }

    public void setResetKeyboard(Boolean resetKeyboard) {
        this.resetKeyboard = resetKeyboard;
    }

    public Boolean getNoSign() {
        return noSign;
    }

    public void setNoSign(Boolean noSign) {
        this.noSign = noSign;
    }

    public Boolean getIgnoreUnimportantViews() {
        return ignoreUnimportantViews;
    }

    public void setIgnoreUnimportantViews(Boolean ignoreUnimportantViews) {
        this.ignoreUnimportantViews = ignoreUnimportantViews;
    }

    public Boolean getDisableAndroidWatchers() {
        return disableAndroidWatchers;
    }

    public void setDisableAndroidWatchers(Boolean disableAndroidWatchers) {
        this.disableAndroidWatchers = disableAndroidWatchers;
    }

    public String getChromeOptions() {
        return chromeOptions;
    }

    public void setChromeOptions(String chromeOptions) {
        this.chromeOptions = chromeOptions;
    }

    public Boolean getRecreateChromeDriverSessions() {
        return recreateChromeDriverSessions;
    }

    public void setRecreateChromeDriverSessions(Boolean recreateChromeDriverSessions) {
        this.recreateChromeDriverSessions = recreateChromeDriverSessions;
    }

    public Boolean getNativeWebScreenshot() {
        return nativeWebScreenshot;
    }

    public void setNativeWebScreenshot(Boolean nativeWebScreenshot) {
        this.nativeWebScreenshot = nativeWebScreenshot;
    }

    @Override
    public DesiredCapabilities toDesiredCapabilities() {
        DesiredCapabilities capabilities = super.toDesiredCapabilities();
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);
        return capabilities;
    }
}
