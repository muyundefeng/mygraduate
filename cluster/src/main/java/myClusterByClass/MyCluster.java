package myClusterByClass;

import DAO.dao.ReadFromDB;
import DAO.dao.SaveDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ClusterIdGenerateUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by lisheng on 17-5-18.
 */
public class MyCluster {

    public static Logger logger = LoggerFactory.getLogger(MyCluster.class);

    public static Map<String, String> map = new HashMap<>();//存放hostname，与clustrerId

    public static void cluster() {
        List<String> urls = ReadFromDB.getUrl();
        for (String url : urls) {
            URL myUrl = null;
            try {
                myUrl = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            if (map.containsKey(myUrl.getHost())) {
                String clusterId = map.get(myUrl.getHost());
                logger.info("clusterId=" + clusterId);
                logger.info("insert url:" + url);
                SaveDB.insertCluster(clusterId, url);
            } else {
                String clusterId = ClusterIdGenerateUtils.getClusterId();
                logger.info("insert url:" + url);
                SaveDB.insertCluster(clusterId, url);
                map.put(myUrl.getHost(), clusterId);
            }
        }
    }

    public static void main(String[] args) {
        cluster();
    }
}
