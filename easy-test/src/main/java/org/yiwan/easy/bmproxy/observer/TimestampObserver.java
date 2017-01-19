package org.yiwan.easy.bmproxy.observer;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.filters.ResponseFilter;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yiwan.easy.bmproxy.ProxyWrapper;
import org.yiwan.easy.bmproxy.TimestampWriter;
import org.yiwan.easy.bmproxy.model.HttpRequestDetail;
import org.yiwan.easy.bmproxy.model.HttpResponseDetail;
import org.yiwan.easy.bmproxy.model.UserTransactionDetail;
import org.yiwan.easy.test.ITestBase;

/**
 * Created by Kenny Wang on 3/14/2016.
 */
public class TimestampObserver extends SampleObserver {
    private static final Logger logger = LoggerFactory.getLogger(TimestampObserver.class);
    private ITestBase testcase;
    private ProxyWrapper proxyWrapper;
    private UserTransactionDetail userTransactionDetail;
    private TimestampWriter timestampWriter;

    public TimestampObserver(ITestBase testCase) {
        this.testcase = testCase;
        this.proxyWrapper = testCase.getProxyWrapper();
        this.timestampWriter = testCase.getTimestampWriter();
//        supprotToRecordHttpTransactionTimestamp();
    }

    @Override
    public void start() {
        super.start();
        userTransactionDetail = new UserTransactionDetail(testcase.getTransactionName());
        userTransactionDetail.setUserActionTimestamp(System.currentTimeMillis());
    }

    @Override
    public void stop() {
        super.stop();
        userTransactionDetail.setDocumentReadyTimestamp(System.currentTimeMillis());
        timestampWriter.write(userTransactionDetail);
    }

    private void supprotToRecordHttpTransactionTimestamp() {
        proxyWrapper.addReqeustFilter(new RequestFilter() {
            @Override
            public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
                //bug here, asynchronized thread may cause the timestamp is incorrect
                timestampWriter.write(new HttpRequestDetail(System.currentTimeMillis(), request, contents, messageInfo));
                return null;
            }
        });
        proxyWrapper.addResponseFilter(new ResponseFilter() {
            @Override
            public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
                //bug here, asynchronized thread may cause the timestamp is incorrect
                timestampWriter.write(new HttpResponseDetail(System.currentTimeMillis(), response, contents, messageInfo));
            }
        });
    }
}
