package org.yiwan.appium.wrapper;

import org.assertj.core.api.SoftAssertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yiwan.appium.locator.Locator;
import org.yiwan.appium.locator.LocatorBean;
import org.yiwan.appium.wrapper.IAppiumDriverWrapper.*;
import org.yiwan.easy.test.IView;
import org.yiwan.easy.test.TestCaseManager;
import org.yiwan.easy.util.JaxbHelper;
import org.yiwan.easy.util.PropertiesHelper;

/**
 * Created by Kenny Wang on 4/2/2016.
 */
public abstract class AppView implements IView {
    private final static LocatorBean LOCATOR_BEAN = JaxbHelper.unmarshal(ClassLoader.getSystemResourceAsStream(PropertiesHelper.LOCATORS_FILE), ClassLoader.getSystemResourceAsStream(PropertiesHelper.LOCATOR_SCHEMA), LocatorBean.class);
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private IAppiumDriverWrapper appiumDriverWrapper;

    public AppView(IAppiumDriverWrapper appiumDriverWrapper) {
        this.appiumDriverWrapper = appiumDriverWrapper;
    }

    public IAppiumDriverWrapper getAppiumDriverWrapper() {
        return appiumDriverWrapper;
    }

    public IBrowseNavigation navigate() {
        return appiumDriverWrapper.navigate();
    }

    /**
     * get page source of current page
     *
     * @return page source string
     */
    protected String getPageSource() {
        return appiumDriverWrapper.getPageSource();
    }

    /**
     * get current url address
     *
     * @return string value of current url
     */
    protected String getCurrentUrl() {
        return appiumDriverWrapper.getCurrentUrl();
    }

    /**
     * Switch to a window or frame
     */
    protected IAppiumDriverWrapper.ITargetLocatorWrapper switchTo() {
        return appiumDriverWrapper.switchTo();
    }

    /**
     * get current page title
     *
     * @return string value of title
     */
    protected String getPageTitle() {
        return appiumDriverWrapper.getPageTitle();
    }

    /**
     * click element if it's displayed, otherwise click the next one
     */
    protected IAppiumDriverWrapper clickSmartly(Locator... locators) {
        return appiumDriverWrapper.clickSmartly(locators);
    }

    /**
     * input value in the first locator if it exists, otherwise input the next one
     *
     * @param value
     * @param locators
     */
    public IAppiumDriverWrapper inputSmartly(String value, Locator... locators) {
        return appiumDriverWrapper.inputSmartly(value, locators);
    }

    protected Object executeScript(String script, Object... args) {
        return appiumDriverWrapper.executeScript(script, args);
    }

    protected Object executeAsyncScript(String script, Object... args) {
        return appiumDriverWrapper.executeAsyncScript(script, args);
    }

    protected IActionsWrapper actions() {
        return appiumDriverWrapper.actions();
    }

    protected IAlertWrapper alert() {
        return appiumDriverWrapper.alert();
    }

    protected Locator locator(String id, String... replacements) throws Exception {
        return LOCATOR_BEAN.locator(id, replacements);
    }

    protected IWebElementWrapper element(Locator locator) {
        return appiumDriverWrapper.element(locator);
    }

    protected IWebElementWrapper element(String id, String... replacements) throws Exception {
        return element(locator(id, replacements));
    }

    protected IFluentWait waitThat() {
        return appiumDriverWrapper.waitThat();
    }

    protected IFluentLocatorWait waitThat(Locator locator) {
        return appiumDriverWrapper.waitThat(locator);
    }

    protected IFluentLocatorWait waitThat(String id, String... replacements) throws Exception {
        return waitThat(locator(id, replacements));
    }

    protected IFluentAssertion assertThat() {
        return appiumDriverWrapper.assertThat();
    }

    protected IFluentLocatorAssertion assertThat(Locator locator) {
        return appiumDriverWrapper.assertThat(locator);
    }

    protected IFluentLocatorAssertion assertThat(IWebElementWrapper webElementWrapper) {
        return appiumDriverWrapper.assertThat(webElementWrapper);
    }

    protected IFluentLocatorAssertion assertThat(String id, String... replacements) throws Exception {
        return assertThat(locator(id, replacements));
    }

    protected IFluentAssertion validateThat() {
        return appiumDriverWrapper.validateThat();
    }

    protected IFluentLocatorAssertion validateThat(Locator locator) {
        return appiumDriverWrapper.validateThat(locator);
    }

    protected IFluentLocatorAssertion validateThat(String id, String... replacements) throws Exception {
        return validateThat(locator(id, replacements));
    }

    protected SoftAssertions getSoftAssertions() {
        return TestCaseManager.getTestCase().getSoftAssertions();
    }
}
