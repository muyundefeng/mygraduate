package myschedule;


import donwloader.Downloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spider.pageprocessor.PageProcessor;
import spider.selector.Page;
import spider.selector.Request;
import spider.utils.RmDuplicateUtils;

import java.util.List;


/**
 * Created by lisheng on 17-4-20.
 */
public class Woker {

    public Downloader downloader;

    public PageProcessor pageProcessor;

    public volatile MyRequestCollection requestsPool;

    public Logger logger = LoggerFactory.getLogger(Woker.class);

    public Woker(Downloader downloader, PageProcessor pageProcessor, MyRequestCollection collection) {
        this.downloader = downloader;
        this.pageProcessor = pageProcessor;
        requestsPool = collection;
    }

    public void execute() {
        Page page = downloader.download(requestsPool.popRequest());
        if (page == null) {
            logger.info("page is null");
            return;
        }
        pageProcessor.processor(page);
        List<Request> requests = page.getRequestList();
        if (requests != null)
            for (Request request : requests) {
                if (!RmDuplicateUtils.containElement(request.getUrl())) {
                    requestsPool.addRequest(request);
                    RmDuplicateUtils.add(request.getUrl());
                }
            }
    }
}
