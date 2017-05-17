package exe;

import analyse.Trigger;
import extractor.trigger.Main;
import myClusterByClass.URLCluster;
import myClusterByClass.core;
import myschedule.Spider;

import java.io.IOException;

/**
 * Created by lisheng on 17-5-17.
 */
public class exeSeriesAction {
    public static void main(String[] args) throws IOException {
//        String channelId = "1";
//        Spider spider = new Spider();
//        spider.initChannelProcessor(channelId).initWoker().start();
//        while (spider.endFlag.equals("0")){
//        }
//        Trigger.start();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        URLCluster.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        core.start();
        Main.start();
    }
}
