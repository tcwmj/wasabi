package org.yiwan.appium.wrapper;

import com.google.gson.JsonObject;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import org.assertj.core.api.AbstractBooleanAssert;
import org.assertj.core.api.AbstractCharSequenceAssert;
import org.assertj.core.api.AbstractIntegerAssert;
import org.assertj.core.api.AbstractListAssert;
import org.openqa.selenium.*;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.remote.ExecuteMethod;
import org.yiwan.appium.locator.Locator;

import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Kenny Wang on 4/4/2016.
 */
public class DummyAppDriverWrapper implements IAppiumDriverWrapper {
    @Override
    public IBrowseNavigation navigate() {
        return new IBrowseNavigation() {
            @Override
            public IBrowseNavigation to(String url) {
                return this;
            }

            @Override
            public IBrowseNavigation forward() {
                return this;
            }

            @Override
            public IBrowseNavigation backward() {
                return this;
            }

            @Override
            public IBrowseNavigation refresh() {
                return this;
            }
        };
    }

    @Override
    public WebDriver.Options manage() {
        return null;
    }

    @Override
    public IAppiumDriverWrapper close() {
        return this;
    }

    @Override
    public IAppiumDriverWrapper closeAll() {
        return this;
    }

    @Override
    public IAppiumDriverWrapper quit() {
        return this;
    }

    @Override
    public IAppiumDriverWrapper deleteAllCookies() {
        return this;
    }

    @Override
    public String getPageSource() {
        return "";
    }

    @Override
    public String getCurrentUrl() {
        return "";
    }

    @Override
    public Set<Cookie> getCookies() {
        return new HashSet<>();
    }

    @Override
    public ITargetLocatorWrapper switchTo() {
        return new ITargetLocatorWrapper() {
            @Override
            public IWebElementWrapper activeElement() {
                return null;
            }

            @Override
            public IAlertWrapper alert() {
                return null;
            }

            @Override
            public IAppiumDriverWrapper defaultContent() {
                return DummyAppDriverWrapper.this;
            }

            @Override
            public IAppiumDriverWrapper frame(int index) {
                return DummyAppDriverWrapper.this;
            }

            @Override
            public IAppiumDriverWrapper frame(String nameOrId) {
                return DummyAppDriverWrapper.this;
            }

            @Override
            public IAppiumDriverWrapper frame(Locator locator) {
                return DummyAppDriverWrapper.this;
            }

            @Override
            public IAppiumDriverWrapper parentFrame() {
                return DummyAppDriverWrapper.this;
            }

            @Override
            public IAppiumDriverWrapper window(String nameOrHandle) {
                return DummyAppDriverWrapper.this;
            }
        };
    }

    @Override
    public String getPageTitle() {
        return "";
    }

    @Override
    public IAppiumDriverWrapper clickSmartly(Locator... locators) {
        return this;
    }

    @Override
    public IAppiumDriverWrapper inputSmartly(String value, Locator... locators) {
        return this;
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) {
        return target.convertFromBase64Png("");
    }

    @Override
    public IActionsWrapper actions() {
        return new IActionsWrapper() {
            @Override
            public IActionsWrapper click() {
                return this;
            }

            @Override
            public IActionsWrapper click(Locator locator) {
                return this;
            }

            @Override
            public IActionsWrapper clickAndHold() {
                return this;
            }

            @Override
            public IActionsWrapper clickAndHold(Locator locator) {
                return this;
            }

            @Override
            public IActionsWrapper contextClick() {
                return this;
            }

            @Override
            public IActionsWrapper contextClick(Locator locator) {
                return this;
            }

            @Override
            public IActionsWrapper release() {
                return this;
            }

            @Override
            public IActionsWrapper release(Locator locator) {
                return this;
            }

            @Override
            public IActionsWrapper doubleClick() {
                return this;
            }

            @Override
            public IActionsWrapper doubleClick(Locator locator) {
                return this;
            }

            @Override
            public IActionsWrapper dragAndDrop(Locator source, Locator target) {
                return this;
            }

            @Override
            public IActionsWrapper dragAndDrop(Locator source, int xOffset, int yOffset) {
                return this;
            }

            @Override
            public IActionsWrapper keyDown(Keys theKey) {
                return this;
            }

            @Override
            public IActionsWrapper keyDown(Locator locator, Keys theKey) {
                return this;
            }

            @Override
            public IActionsWrapper keyUp(Keys theKey) {
                return this;
            }

            @Override
            public IActionsWrapper keyUp(Locator locator, Keys theKey) {
                return this;
            }

            @Override
            public IActionsWrapper sendKeys(Locator locator, CharSequence... keysToSend) {
                return this;
            }

            @Override
            public IActionsWrapper sendKeys(CharSequence... keysToSend) {
                return this;
            }

            @Override
            public IActionsWrapper moveTo(Locator locator) {
                return this;
            }

            @Override
            public IActionsWrapper moveTo(Locator locator, int xOffset, int yOffset) {
                return this;
            }

            @Override
            public IActionsWrapper moveTo(int xOffset, int yOffset) {
                return this;
            }

            @Override
            public Action build() {
                return new Action() {

                    @Override
                    public void perform() {

                    }
                };
            }

            @Override
            public void perform() {

            }

            @Override
            public void performMultiTouchAction(MultiTouchAction multiAction) {

            }

            @Override
            public TouchAction performTouchAction(TouchAction touchAction) {
                return null;
            }

            @Override
            public void performPinch(int x, int y) {

            }

            @Override
            public void performPinch(Locator locator) {

            }

            @Override
            public void performSwipe(int startx, int starty, int endx, int endy, int duration) {

            }

            @Override
            public ISwipeDirection performSwipe() {
                return null;
            }

            @Override
            public void performTap(int fingers, int x, int y, int duration) {

            }

            @Override
            public void performTap(int fingers, Locator locator, int duration) {

            }

            @Override
            public void performZoom(int x, int y) {

            }

            @Override
            public void performZoom(Locator locator) {

            }
        };
    }

    @Override
    public Object executeScript(String script, Object... args) {
        return null;
    }

    @Override
    public Object executeAsyncScript(String script, Object... args) {
        return null;
    }

    @Override
    public IWebElementWrapper element(final Locator locator) {
        return new IWebElementWrapper() {

            @Override
            public IWebElementWrapper click() {
                return this;
            }

            @Override
            public boolean clickSmartly() {
                return true;
            }

            @Override
            public IWebElementWrapper clickByJavaScript() {
                return this;
            }

            @Override
            public IWebElementWrapper clickCircularly() throws InterruptedException {
                return this;
            }

            @Override
            public IWebElementWrapper doubleClick() {
                return this;
            }

            @Override
            public IWebElementWrapper contextClick() {
                return this;
            }

            @Override
            public IWebElementWrapper dragAndDrop(Locator target) {
                return this;
            }

            @Override
            public IWebElementWrapper dragAndDrop(int xOffset, int yOffset) {
                return this;
            }

            @Override
            public IWebElementWrapper type(CharSequence... value) {
                return this;
            }

            @Override
            public IWebElementWrapper clear() {
                return this;
            }

            @Override
            public IWebElementWrapper input(String value) {
                return this;
            }

            @Override
            public boolean inputSmartly(String value) {
                return true;
            }

            @Override
            public IWebElementWrapper check(boolean checked) {
                return this;
            }

            @Override
            public boolean isChecked() {
                return true;
            }

            @Override
            public boolean tick(boolean checked) {
                return true;
            }

            @Override
            public IWebElementWrapper selectAll() {
                return this;
            }

            @Override
            public IWebElementWrapper checkByJavaScript(boolean checked) {
                return this;
            }

            @Override
            public IWebElementWrapper selectByVisibleText(String text) {
                return this;
            }

            @Override
            public IWebElementWrapper deselectAll() {
                return this;
            }

            @Override
            public IWebElementWrapper deselectByVisibleText(String text) {
                return this;
            }

            @Override
            public IWebElementWrapper deselectByVisibleText(String... texts) {
                return this;
            }

            @Override
            public IWebElementWrapper deselectByIndex(int index) {
                return this;
            }

            @Override
            public IWebElementWrapper deselectByValue(String value) {
                return this;
            }

            @Override
            public IWebElementWrapper selectByVisibleText(String... texts) {
                return this;
            }

            @Override
            public IWebElementWrapper selectByIndex(int index) {
                return this;
            }

            @Override
            public IWebElementWrapper selectByValue(String value) {
                return this;
            }

            @Override
            public IWebElementWrapper moveTo() {
                return this;
            }

            @Override
            public boolean isPresent() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }

            @Override
            public boolean isDisplayed() {
                return true;
            }

            @Override
            public boolean isSelected() {
                return true;
            }

            @Override
            public String getAttribute(String attribute) {
                return "";
            }

            @Override
            public String getCssValue(String attribute) {
                return "";
            }

            @Override
            public String getInnerText() {
                return "";
            }

            @Override
            public List<String> getAllInnerTexts() {
                return new ArrayList<>();
            }

            @Override
            public IWebElementWrapper setInnerText(String text) {
                return this;
            }

            @Override
            public IWebElementWrapper setValue(String value) {
                return this;
            }

            @Override
            public List<WebElement> getAllSelectedOptions() {
                return new ArrayList<>();
            }

            @Override
            public List<WebElement> getAllOptions() {
                return new ArrayList<>();
            }

            @Override
            public List<String> getAllOptionTexts() {
                return new ArrayList<>();
            }

            @Override
            public String getSelectedText() {
                return "";
            }

            @Override
            public List<String> getAllSelectedTexts() {
                return new ArrayList<>();
            }

            @Override
            public IWebElementWrapper triggerEvent(String event) {
                return this;
            }

            @Override
            public IWebElementWrapper fireEvent(String event) {
                return this;
            }

            @Override
            public IWebElementWrapper scrollTo() {
                return this;
            }

            @Override
            public IWebElementWrapper scrollIntoView() {
                return this;
            }

            @Override
            public IWebElementWrapper scrollIntoView(boolean bAlignToTop) {
                return this;
            }

            @Override
            public IWebElementWrapper setAttribute(String attribute, String value) {
                return this;
            }

            @Override
            public IWebElementWrapper removeAttribute(String attribute) {
                return this;
            }

            @Override
            public long getCellRow() {
                return 0;
            }

            @Override
            public long getCellColumn() {
                return 0;
            }

            @Override
            public long getRow() {
                return 0;
            }

            @Override
            public long getRowCount() {
                return 0;
            }

            @Override
            public IAppiumDriverWrapper switchTo() {
                return null;
            }

            @Override
            public int getNumberOfMatches() {
                return 0;
            }

            @Override
            public List<IWebElementWrapper> getAllMatchedElements() {
                return new ArrayList<>();
            }
        };
    }

    @Override
    public IFluentLocatorWait waitThat(final Locator locator) {
        return new IFluentLocatorWait() {

            @Override
            public IFluentStringWait innerText() {
                return null;
            }

            @Override
            public Boolean toBeInvisible() {
                return true;
            }

            @Override
            public Boolean toBeSelected() {
                return true;
            }

            @Override
            public Boolean toBeDeselected() {
                return true;
            }

            @Override
            public WebElement toBePresent() {
                return null;
            }

            @Override
            public WebElement toBeEnabled() {
                return null;
            }

            @Override
            public List<WebElement> toBeAllPresent() {
                return new ArrayList<>();
            }

            @Override
            public Boolean toBeAbsent() {
                return true;
            }

            @Override
            public IFluentLocatorWait toBePresentIn(long milliseconds) {
                return null;
            }

            @Override
            public IFluentLocatorWait toBeAbsentIn(long milliseconds) {
                return null;
            }

            @Override
            public IFluentLocatorWait toBeAppearedIn(long milliseconds) {
                return this;
            }

            @Override
            public IFluentLocatorWait toBeDisappearedIn(long milliseconds) {
                return this;
            }

            @Override
            public WebElement toBeClickable() {
                return null;
            }

            @Override
            public WebElement toBeVisible() {
                return null;
            }

            @Override
            public List<WebElement> toBeAllVisible() {
                return new ArrayList<>();
            }

            @Override
            public IAppiumDriverWrapper frameToBeAvailableAndSwitchToIt() {
                return DummyAppDriverWrapper.this;
            }

            @Override
            public IFluentStringWait attributeValueOf(String attribute) {
                return new IFluentStringWait() {
                    @Override
                    public Boolean toBe(String text) {
                        return true;
                    }

                    @Override
                    public Boolean toBeEmpty() {
                        return true;
                    }

                    @Override
                    public Boolean notToBe(String text) {
                        return true;
                    }

                    @Override
                    public Boolean contains(String text) {
                        return true;
                    }

                    @Override
                    public Boolean notContains(String text) {
                        return true;
                    }

                    @Override
                    public Boolean startsWith(String text) {
                        return true;
                    }

                    @Override
                    public Boolean endsWith(String text) {
                        return true;
                    }

                    @Override
                    public Boolean matches(Pattern pattern) {
                        return true;
                    }
                };
            }

            @Override
            public IFluentStringWait cssValueOf(String cssAttribute) {
                return new IFluentStringWait() {
                    @Override
                    public Boolean toBe(String text) {
                        return true;
                    }

                    @Override
                    public Boolean toBeEmpty() {
                        return true;
                    }

                    @Override
                    public Boolean notToBe(String text) {
                        return true;
                    }

                    @Override
                    public Boolean contains(String text) {
                        return true;
                    }

                    @Override
                    public Boolean notContains(String text) {
                        return true;
                    }

                    @Override
                    public Boolean startsWith(String text) {
                        return true;
                    }

                    @Override
                    public Boolean endsWith(String text) {
                        return true;
                    }

                    @Override
                    public Boolean matches(Pattern pattern) {
                        return true;
                    }
                };
            }

            @Override
            public IFluentNumberWait numberOfElements() {
                return new IFluentNumberWait() {
                    @Override
                    public Boolean equalTo(int number) {
                        return true;
                    }

                    @Override
                    public Boolean notEqualTo(int number) {
                        return true;
                    }

                    @Override
                    public Boolean lessThan(int number) {
                        return true;
                    }

                    @Override
                    public Boolean greaterThan(int number) {
                        return true;
                    }

                    @Override
                    public Boolean equalToOrLessThan(int number) {
                        return true;
                    }

                    @Override
                    public Boolean equalToOrGreaterThan(int number) {
                        return true;
                    }
                };
            }

            @Override
            public IFluentLocatorWait nestedElements(Locator locator) {
                return this;
            }
        };
    }

    @Override
    public IFluentWait waitThat() {
        return new IFluentWait() {

            @Override
            public IFluentWait timeout(long milliseconds) throws InterruptedException {
                return this;
            }

            @Override
            public IFluentDocumentWait document() {
                return new IFluentDocumentWait() {
                    @Override
                    public void toBeReady() {
                    }
                };
            }

            @Override
            public IFluentJQueryWait jQuery() {
                return new IFluentJQueryWait() {
                    @Override
                    public boolean isJQuerySupported() {
                        return true;
                    }

                    @Override
                    public void toBeInactive() {
                    }
                };
            }

            @Override
            public IFluentAlertWait alert() {
                return new IFluentAlertWait() {
                    @Override
                    public IAlertWrapper toBePresent() {
                        return new AlertWrapper();
                    }
                };
            }

            @Override
            public IFluentPageWait page() {
                return new IFluentPageWait() {
                    @Override
                    public IFluentStringWait title() {
                        return new IFluentStringWait() {
                            @Override
                            public Boolean toBe(String text) {
                                return true;
                            }

                            @Override
                            public Boolean toBeEmpty() {
                                return true;
                            }

                            @Override
                            public Boolean notToBe(String text) {
                                return true;
                            }

                            @Override
                            public Boolean contains(String text) {
                                return true;
                            }

                            @Override
                            public Boolean notContains(String text) {
                                return true;
                            }

                            @Override
                            public Boolean startsWith(String text) {
                                return true;
                            }

                            @Override
                            public Boolean endsWith(String text) {
                                return true;
                            }

                            @Override
                            public Boolean matches(Pattern pattern) {
                                return true;
                            }
                        };
                    }

                    @Override
                    public IFluentStringWait source() {
                        return new IFluentStringWait() {
                            @Override
                            public Boolean toBe(String text) {
                                return true;
                            }

                            @Override
                            public Boolean toBeEmpty() {
                                return true;
                            }

                            @Override
                            public Boolean notToBe(String text) {
                                return true;
                            }

                            @Override
                            public Boolean contains(String text) {
                                return true;
                            }

                            @Override
                            public Boolean notContains(String text) {
                                return true;
                            }

                            @Override
                            public Boolean startsWith(String text) {
                                return true;
                            }

                            @Override
                            public Boolean endsWith(String text) {
                                return true;
                            }

                            @Override
                            public Boolean matches(Pattern pattern) {
                                return true;
                            }
                        };
                    }

                    @Override
                    public IFluentStringWait url() {
                        return new IFluentStringWait() {
                            @Override
                            public Boolean toBe(String text) {
                                return true;
                            }

                            @Override
                            public Boolean toBeEmpty() {
                                return true;
                            }

                            @Override
                            public Boolean notToBe(String text) {
                                return true;
                            }

                            @Override
                            public Boolean contains(String text) {
                                return true;
                            }

                            @Override
                            public Boolean notContains(String text) {
                                return true;
                            }

                            @Override
                            public Boolean startsWith(String text) {
                                return true;
                            }

                            @Override
                            public Boolean endsWith(String text) {
                                return true;
                            }

                            @Override
                            public Boolean matches(Pattern pattern) {
                                return true;
                            }
                        };
                    }
                };
            }


        };
    }

    @Override
    public IFluentLocatorAssertion assertThat(final Locator locator) {
        return new IFluentLocatorAssertion() {
            @Override
            public AbstractListAssert<? extends AbstractListAssert, ? extends List, String> allSelectedTexts() {
                return null;
            }

            @Override
            public AbstractCharSequenceAssert<?, String> selectedText() {
                return null;
            }

            @Override
            public AbstractListAssert<? extends AbstractListAssert, ? extends List, String> allOptionTexts() {
                return null;
            }

            @Override
            public AbstractBooleanAssert<?> present() {
                return null;
            }

            @Override
            public AbstractBooleanAssert<?> enabled() {
                return null;
            }

            @Override
            public AbstractBooleanAssert<?> displayed() {
                return null;
            }

            @Override
            public AbstractBooleanAssert<?> selected() {
                return null;
            }

            @Override
            public AbstractCharSequenceAssert<?, String> innerText() {
                return null;
            }

            @Override
            public AbstractListAssert<? extends AbstractListAssert, ? extends List, String> allInnerTexts() {
                return null;
            }

            @Override
            public AbstractCharSequenceAssert<?, String> attributeValueOf(String attribute) {
                return null;
            }

            @Override
            public AbstractCharSequenceAssert<?, String> cssValueOf(String cssAttribute) {
                return null;
            }

            @Override
            public AbstractIntegerAssert<? extends AbstractIntegerAssert<?>> numberOfElements() {
                return null;
            }

            @Override
            public IFluentLocatorAssertion nestedElements(Locator locator) {
                return new IFluentLocatorAssertion() {
                    @Override
                    public AbstractListAssert<? extends AbstractListAssert, ? extends List, String> allSelectedTexts() {
                        return null;
                    }

                    @Override
                    public AbstractCharSequenceAssert<?, String> selectedText() {
                        return null;
                    }

                    @Override
                    public AbstractListAssert<? extends AbstractListAssert, ? extends List, String> allOptionTexts() {
                        return null;
                    }

                    @Override
                    public AbstractBooleanAssert<?> present() {
                        return null;
                    }

                    @Override
                    public AbstractBooleanAssert<?> enabled() {
                        return null;
                    }

                    @Override
                    public AbstractBooleanAssert<?> displayed() {
                        return null;
                    }

                    @Override
                    public AbstractBooleanAssert<?> selected() {
                        return null;
                    }

                    @Override
                    public AbstractCharSequenceAssert<?, String> innerText() {
                        return null;
                    }

                    @Override
                    public AbstractListAssert<? extends AbstractListAssert, ? extends List, String> allInnerTexts() {
                        return null;
                    }

                    @Override
                    public AbstractCharSequenceAssert<?, String> attributeValueOf(String attribute) {
                        return null;
                    }

                    @Override
                    public AbstractCharSequenceAssert<?, String> cssValueOf(String cssAttribute) {
                        return null;
                    }

                    @Override
                    public AbstractIntegerAssert<? extends AbstractIntegerAssert<?>> numberOfElements() {
                        return null;
                    }

                    @Override
                    public IFluentLocatorAssertion nestedElements(Locator locator) {
                        return null;
                    }
                };
            }
        };
    }

    @Override
    public IFluentLocatorAssertion assertThat(IWebElementWrapper webElementWrapper) {
        return null;
    }

    @Override
    public IFluentAssertion assertThat() {
        return new IFluentAssertion() {

            @Override
            public IFluentAlertAssertion alert() {
                return new IFluentAlertAssertion() {
                    @Override
                    public AbstractBooleanAssert<?> present() {
                        return null;
                    }

                    @Override
                    public AbstractCharSequenceAssert<?, String> text() {
                        return null;
                    }
                };
            }

            @Override
            public IFluentPageAssertion page() {
                return new IFluentPageAssertion() {
                    @Override
                    public AbstractCharSequenceAssert<?, String> title() {
                        return null;
                    }

                    @Override
                    public AbstractCharSequenceAssert<?, String> source() {
                        return null;
                    }

                    @Override
                    public AbstractCharSequenceAssert<?, String> url() {
                        return null;
                    }
                };
            }
        };
    }

    @Override
    public IFluentLocatorAssertion validateThat(Locator locator) {
        return assertThat(locator);
    }

    @Override
    public IFluentAssertion validateThat() {
        return assertThat();
    }

    @Override
    public void validateAll() {

    }

    @Override
    public IAlertWrapper alert() {
        return new AlertWrapper();
    }

    @Override
    public IAppiumDriverWrapper closeApp() {
        return null;
    }

    @Override
    public Map<String, String> getAppStringMap() {
        return null;
    }

    @Override
    public Map<String, String> getAppStringMap(String language) {
        return null;
    }

    @Override
    public Map<String, String> getAppStringMap(String language, String stringFile) {
        return null;
    }

    @Override
    public String getContext() {
        return null;
    }

    @Override
    public Set<String> getContextHandles() {
        return null;
    }

    @Override
    public String getDeviceTime() {
        return null;
    }

    @Override
    public ExecuteMethod getExecuteMethod() {
        return null;
    }

    @Override
    public ScreenOrientation getOrientation() {
        return null;
    }

    @Override
    public URL getRemoteAddress() {
        return null;
    }

    @Override
    public JsonObject getSettings() {
        return null;
    }

    @Override
    public IAppiumDriverWrapper hideKeyboard() {
        return null;
    }

    @Override
    public IAppiumDriverWrapper installApp(String appPath) {
        return null;
    }

    @Override
    public boolean isAppInstalled(String bundleId) {
        return false;
    }

    @Override
    public IAppiumDriverWrapper launchApp() {
        return null;
    }

    @Override
    public Location location() {
        return null;
    }

    @Override
    public byte[] pullFile(String remotePath) {
        return new byte[0];
    }

    @Override
    public byte[] pullFolder(String remotePath) {
        return new byte[0];
    }

    @Override
    public void removeApp(String bundleId) {

    }

    @Override
    public void resetApp() {

    }

    @Override
    public void rotate(ScreenOrientation orientation) {

    }

    @Override
    public void runAppInBackground(int seconds) {

    }

    @Override
    public void setLocation(Location location) {

    }

    private class AlertWrapper implements IAlertWrapper {
        @Override
        public void dismiss() {

        }

        @Override
        public void accept() {

        }

        @Override
        public String getText() {
            return "";
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public void disable() {
        }

        @Override
        public void disable(boolean accept) {

        }

        @Override
        public void enable() {

        }
    }
}
