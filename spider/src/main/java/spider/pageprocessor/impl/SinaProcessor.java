package spider.pageprocessor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spider.pageprocessor.PageProcessor;
import spider.selector.Html;
import spider.selector.Page;

import java.util.List;

/**
 * Created by lisheng on 17-4-20.
 */
public class SinaProcessor implements PageProcessor {

    public Logger logger = LoggerFactory.getLogger(getClass());

    public static final String HOST_NAME = "http://.*qq.com[^#]*";

    public void processor(Page page) {
        String content = page.getContent();
        Html html = Html.create(content);
        List<String> urls = html.links().all();
        for(String url:urls){
            if(url.matches(HOST_NAME)){
                page.addRequest(url);
            }
        }
    }
}
