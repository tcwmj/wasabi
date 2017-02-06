package org.yiwan.selenium.wrapper;

import java.lang.reflect.Constructor;

public class PageFactory {
    private IWebDriverWrapper webDriverWrapper;

    public PageFactory(IWebDriverWrapper webDriverWrapper) {
        this.webDriverWrapper = webDriverWrapper;
    }

    public <T extends WebPage> T create(Class<T> clazz) throws Exception {
        Constructor<T> c = clazz.getDeclaredConstructor(IWebDriverWrapper.class);
        c.setAccessible(true);
        return c.newInstance(webDriverWrapper);
    }
}
