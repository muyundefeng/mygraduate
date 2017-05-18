package donwloader;


import spider.selector.Page;
import spider.selector.Request;

/**
 * Created by lisheng on 17-4-20.
 */
public interface Downloader {
    public Page download(Request request);

    public void setChannelId(String channelId);
}
