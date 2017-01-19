package org.yiwan.easy.test;

import org.assertj.core.api.SoftAssertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yiwan.easy.locator.Locator;
import org.yiwan.easy.locator.LocatorBean;
import org.yiwan.easy.util.JaxbHelper;
import org.yiwan.easy.util.PropHelper;

import java.util.Set;

/**
 * Created by Kenny Wang on 4/2/2016.
 */
public class AbstractView {
    private final static LocatorBean LOCATOR_BEAN = JaxbHelper.unmarshal(ClassLoader.getSystemResourceAsStream(PropHelper.LOCATORS_FILE), ClassLoader.getSystemResourceAsStream(PropHelper.LOCATOR_SCHEMA), LocatorBean.class);
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private IWebDriverWrapper webDriverWrapper;

    public AbstractView(IWebDriverWrapper webDriverWrapper) {
        this.webDriverWrapper = webDriverWrapper;
    }

    public IWebDriverWrapper getWebDriverWrapper() {
        return webDriverWrapper;
    }

    public IWebDriverWrapper.IBrowseNavigation navigate() {
        return webDriverWrapper.navigate();
    }

    /**
     * get page source of current page
     *
     * @return page source string
     */
    protected String getPageSource() {
        return webDriverWrapper.getPageSource();
    }

    /**
     * get current url address
     *
     * @return string value of current url
     */
    protected String getCurrentUrl() {
        return webDriverWrapper.getCurrentUrl();
    }

    /**
     * get current window handle
     *
     * @return string
     */
    protected String getWindowHandle() {
        return webDriverWrapper.getWindowHandle();
    }

    /**
     * get all window handles
     *
     * @return Set<string>
     */
    protected Set<String> getWindowHandles() {
        return webDriverWrapper.getWindowHandles();
    }

    /**
     * Switch to a window or frame
     */
    protected IWebDriverWrapper.ITargetLocatorWrapper switchTo() {
        return webDriverWrapper.switchTo();
    }

    /**
     * get current page title
     *
     * @return string value of title
     */
    protected String getPageTitle() {
        return webDriverWrapper.getPageTitle();
    }

    /**
     * click element if it's displayed, otherwise click the next one
     */
    protected IWebDriverWrapper clickSmartly(Locator... locators) {
        return webDriverWrapper.clickSmartly(locators);
    }

    /**
     * input value in the first locator if it exists, otherwise input the next one
     *
     * @param value
     * @param locators
     */
    public IWebDriverWrapper inputSmartly(String value, Locator... locators) {
        return webDriverWrapper.inputSmartly(value, locators);
    }

    protected Object executeScript(String script, Object... args) {
        return webDriverWrapper.executeScript(script, args);
    }

    protected Object executeAsyncScript(String script, Object... args) {
        return webDriverWrapper.executeAsyncScript(script, args);
    }

    protected IActionsWrapper actions() {
        return webDriverWrapper.actions();
    }

    protected IAlertWrapper alert() {
        return webDriverWrapper.alert();
    }

    protected Locator locator(String id, String... replacements) throws Exception {
        return LOCATOR_BEAN.locator(id, replacements);
    }

    protected IWebElementWrapper element(Locator locator) {
        return webDriverWrapper.element(locator);
    }

    protected IWebElementWrapper element(String id, String... replacements) throws Exception {
        return element(locator(id, replacements));
    }

    protected IFluentWait waitThat() {
        return webDriverWrapper.waitThat();
    }

    protected IFluentLocatorWait waitThat(Locator locator) {
        return webDriverWrapper.waitThat(locator);
    }

    protected IFluentLocatorWait waitThat(String id, String... replacements) throws Exception {
        return waitThat(locator(id, replacements));
    }

    protected IFluentAssertion assertThat() {
        return webDriverWrapper.assertThat();
    }

    protected IFluentLocatorAssertion assertThat(Locator locator) {
        return webDriverWrapper.assertThat(locator);
    }

    protected IFluentLocatorAssertion assertThat(IWebElementWrapper webElementWrapper) {
        return webDriverWrapper.assertThat(webElementWrapper);
    }

    protected IFluentLocatorAssertion assertThat(String id, String... replacements) throws Exception {
        return assertThat(locator(id, replacements));
    }

    protected IFluentAssertion validateThat() {
        return webDriverWrapper.validateThat();
    }

    protected IFluentLocatorAssertion validateThat(Locator locator) {
        return webDriverWrapper.validateThat(locator);
    }

    protected IFluentLocatorAssertion validateThat(String id, String... replacements) throws Exception {
        return validateThat(locator(id, replacements));
    }

    protected SoftAssertions getSoftAssertions() {
        return TestCaseManager.getTestCase().getSoftAssertions();
    }
}
