package org.yiwan.selenium.wrapper;

import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yiwan.easy.util.ActionExecutor;
import org.yiwan.easy.util.IAction;
import org.yiwan.easy.util.PropertiesHelper;

/**
 * Created by Kenny Wang on 8/22/2016.
 * for resolving UnreachableBrowserException by retry last operation
 */
public class WebDriverActionExecutor extends ActionExecutor {
    private final Logger logger = LoggerFactory.getLogger(WebDriverActionExecutor.class);

    public WebDriverActionExecutor() {
        super(Integer.parseInt(PropertiesHelper.getProperty("action.max.retry.times")));
    }

    public WebDriverActionExecutor(int max_retry_times) {
        super(max_retry_times);
    }

    public void execute(IAction action) {
        execute(action, WebDriverException.class);
    }
}
