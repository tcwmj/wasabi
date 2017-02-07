package org.yiwan.appium.wrapper;

import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yiwan.easy.util.ActionExecutor;
import org.yiwan.easy.util.IAction;

/**
 * Created by Kenny Wang on 8/22/2016.
 * for resolving UnreachableBrowserException by retry last operation
 */
public class AppiumDriverActionExecutor extends ActionExecutor {
    private final Logger logger = LoggerFactory.getLogger(AppiumDriverActionExecutor.class);

    public AppiumDriverActionExecutor() {
        super(2);
    }

    public AppiumDriverActionExecutor(int max_retry_times) {
        super(max_retry_times);
    }

    public void execute(IAction action) {
        execute(action, WebDriverException.class);
    }
}
