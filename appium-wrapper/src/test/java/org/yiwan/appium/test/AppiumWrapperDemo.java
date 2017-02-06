package org.yiwan.appium.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import org.yiwan.appium.wrapper.AppiumDriverWrapperFactory;
import org.yiwan.appium.wrapper.IAppiumDriverWrapper;
import org.yiwan.appium.model.AndroidTestCapabilities;

public class AppiumWrapperDemo {
    private final static Logger logger = LoggerFactory.getLogger(AppiumWrapperDemo.class);

    @Test
    public void DemoTest() throws Exception {
        IAppiumDriverWrapper driver = new AppiumDriverWrapperFactory(new AndroidTestCapabilities()).create();

        try {
            driver.waitThat().timeout(2000);
            driver.actions().performSwipe().forward();
            driver.waitThat().timeout(2000);

//            driver.findElement(By.id("com.youdao.note:id/try_now")).click();
//            Thread.sleep(2000);
//            Assert.assertEquals(driver.findElementById("com.youdao.note:id/notebook").getText(), "全部笔记");

        } catch (Exception e) {
            // TODO: handle exception
            logger.error(e.getMessage(), e);
        } finally {
            driver.quit();
        }
    }
}
