package extractor.trigger;

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
import java.util.List;

/**
 * 出发文件下载以及信息提取
 * Created by lisheng on 17-5-16.
 */
public class Main {

    public static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void start() throws IOException {
        logger.info("start executing building trinity and generate regex!");
        List<String> filePaths = FileUtils.getFileName(PropertiesUtils.getExtractorInputpath());
        for (String filePath : filePaths) {
            try {
                logger.info("start reading url from file!");
                String urlRaw = FileUtils.readUrlFromFile(filePath);
                String urls[] = urlRaw.split("\\n");
                int count = 0;
                for (String url : urls) {
                    String html = DownloadUtils.download(url);
                    logger.info("writing html string to file!");
                    FileUtils.savetoFile(html, PropertiesUtils.getExtractorOuputpath(), "" + (count++) + ".html");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Text> texts = null;
            try {
                texts = InputDocument.getDefaultReadHtml("./Html/");//每个文件形成一条Txt
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Node node = new Node();
            node.setTexts(texts);
            CreateTrinity trinity = new CreateTrinity(texts);
            trinity.createTrinity(node);
            TestTrinity.preScanTrinity(node);


            LearnTemplate learnTemplate = new LearnTemplate();
            String regex = learnTemplate.learnTemplate(node, "");//提取正则表达式
            logger.info("regex=" + regex);
            List<String> regexs = CutRegexUtils.getRegexs(regex, texts.get(0).getText());
            logger.info("cut rehgex is=" + regexs);
            logger.info("使用提取的正则表达式进行文本获取");
            // regex = ParseRegexUtils.cutRegex(regex);
            for (String regex1 : regexs) {
                int groupNumbers = ParseRegexUtils.analyseNumberGroup(regex1);
                regex1 = ParseRegexUtils.getNormalRegex(regex1, regexs.size());
                for (int i = 1; i <= groupNumbers; i++) {
                    System.out.println(ParseRegexUtils.extraText(regex1, i, texts.get(0).getText()));
                }
            }
        }
    }

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) throws IOException {
        start();
    }
}
