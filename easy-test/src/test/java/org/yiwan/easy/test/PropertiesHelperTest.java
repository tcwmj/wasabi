package org.yiwan.easy.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import org.yiwan.easy.util.PropertiesHelper;

/**
 * Created by Kenny Wang on 5/18/2016.
 */
public class PropertiesHelperTest {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesHelperTest.class);

    @Test
    public void testServerInfoProperty() throws Exception {
        logger.info("\n" + PropertiesHelper.SERVER_INFO);
    }

    @Test
    public void testGetServerInfo() {
//        System.setProperty("server.url", "http://192.168.1.1/,http://192.168.1.2/");
        logger.info(PropertiesHelper.SERVER_URL);
        logger.info(PropertiesHelper.SERVER_INFO);
    }
}
