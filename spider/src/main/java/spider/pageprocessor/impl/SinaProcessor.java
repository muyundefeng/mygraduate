package spider.pageprocessor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spider.pageprocessor.PageProcessor;
import spider.selector.Html;
import spider.selector.Page;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by lisheng on 17-4-20.
 */
public class SinaProcessor implements PageProcessor {

    public Logger logger = LoggerFactory.getLogger(getClass());

//    public static final String HOST_NAME = "http://news.qq.com[^#]*";

    public void processor(Page page) {
        String myurl = page.getRequest().getUrl();
        URL url1 = null;
        try {
            url1 = new URL(myurl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String hostName = url1.getHost();
        String content = page.getContent();
        Html html = Html.create(content);
        List<String> urls = html.links().all();
        for (String url : urls) {
            if (url.matches("http://" + hostName + ".*")) {
                page.addRequest(url);
            }
        }
    }
}
