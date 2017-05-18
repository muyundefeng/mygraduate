package extractor.trigger;

import DAO.dao.ReadFromDB;
import DAO.entity.Channel;
import extractor.util.CutRegexUtils;
import extractor.util.ParseRegexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by lisheng on 17-5-18.
 */
public class InfoGet {

    public static Logger logger = LoggerFactory.getLogger(InfoGet.class);

    public static void getInfo(String regex, String text) {
        List<String> regexs = CutRegexUtils.getRegexs(regex, text);
        logger.info("cut rehgex is=" + regexs);
        logger.info("使用提取的正则表达式进行文本获取");
        // regex = ParseRegexUtils.cutRegex(regex);
        for (String regex1 : regexs) {
            int groupNumbers = ParseRegexUtils.analyseNumberGroup(regex1);
            regex1 = ParseRegexUtils.getNormalRegex(regex1, regexs.size());
            for (int i = 1; i <= groupNumbers; i++) {
                System.out.println(ParseRegexUtils.extraText(regex1, i, text));
            }
        }
    }

    public static void getHtml() {
        List<Channel> channels = ReadFromDB.getChannels();
        for (Channel channel : channels) {
            String regex = channel.getRegex();
            List<String> texts = ReadFromDB.getText(channel.getChannleId());
            for (String text : texts) {
                getInfo(regex, text);
            }
        }
    }

    public static void main(String[] args) {
        getHtml();
    }
}
