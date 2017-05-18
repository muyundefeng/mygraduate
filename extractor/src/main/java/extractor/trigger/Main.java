package extractor.trigger;

import DAO.dao.ReadFromDB;
import DAO.dao.SaveDB;
import DAO.entity.Cluster;
import DAO.entity.Htmls;
import extractor.input.InputDocument;
import extractor.test.TestTrinity;
import extractor.trinity.CreateTrinity;
import extractor.trinity.LearnTemplate;
import extractor.trinity.Node;
import extractor.trinity.Text;
import extractor.util.CutRegexUtils;
import extractor.util.FileUtils;
import extractor.util.ParseRegexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DownloadUtils;
import utils.PropertiesUtils;

import java.io.IOException;
import java.util.*;

/**
 * 出发文件下载以及信息提取
 * Created by lisheng on 17-5-16.
 */
public class Main {

    public static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void start() throws IOException {
        List<String> clusterId = ReadFromDB.getClusterIds();
        Set<String> set = new HashSet<>();
        set.addAll(clusterId);
        Map<String, List<String>> map = ReadFromDB.getTwoUrl(set);
        System.out.println(map);
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            List<String> list = entry.getValue();
            List<Text> texts = new ArrayList<>();
            int channelId = 0;
            for (String url : list) {
                Htmls htmls = ReadFromDB.getHtmlsByUrl(url);
                texts.add(new Text(htmls.getProcesscontent()));
                channelId = htmls.getChannelId();
            }
            Node node = new Node();
            node.setTexts(texts);
            CreateTrinity trinity = new CreateTrinity(texts);
            trinity.createTrinity(node);
            TestTrinity.preScanTrinity(node);


            LearnTemplate learnTemplate = new LearnTemplate();
            String regex = learnTemplate.learnTemplate(node, "");//提取正则表达式
            logger.info("regex=" + regex);

            SaveDB.updateChannelId(channelId, regex);
        }
    }

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) throws IOException {
        start();
    }
}
