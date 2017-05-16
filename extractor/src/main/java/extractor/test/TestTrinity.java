package extractor.test;

import com.muyundefeng.extractor.input.InputDocument;
import com.muyundefeng.extractor.trinity.CreateTrinity;
import com.muyundefeng.extractor.trinity.LearnTemplate;
import com.muyundefeng.extractor.trinity.Node;
import com.muyundefeng.extractor.trinity.Text;
import com.muyundefeng.extractor.util.CutRegexUtils;
import com.muyundefeng.extractor.util.ParseRegexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * 三叉树测试程序
 *
 * @author lisheng
 */
public class TestTrinity {

    private static Logger logger = LoggerFactory.getLogger(TestTrinity.class);

    public static boolean isVariable(Node node) {
        boolean isVariable = true;
        List<Text> texts = node.getTexts();
        for (Text text : texts) {
            if (text.getText().contains("<"))
                isVariable &= true;
            else {
                isVariable = false;
                break;
            }
        }
        return isVariable;
    }

    public static boolean isLeaf(Node node) {
        boolean isLeaf = false;
        if (node != null) {
            if (node.getPreffixNode() == null && node.getSeparatorNode() == null
                    && node.getSuffixNode() == null) {
                isLeaf = true;
            }
        }
        return isLeaf;
    }

    //遍历构建三叉树
    public static void preScanTrinity(Node node) {
        List<Text> texts = node.getTexts();
        System.out.println("-----------------");
        if (node.getPattern() != null)
            System.out.println(node.getPattern().getString());
        for (Text text : texts) {
            System.out.println(text.getText());
        }
        if (node.getPreffixNode() != null) {
            preScanTrinity(node.getPreffixNode());
        }
        if (node.getSeparatorNode() != null) {
            preScanTrinity(node.getSeparatorNode());
        }
        if (node.getSuffixNode() != null) {
            preScanTrinity(node.getSuffixNode());
        }
    }

    public static void main(String[] args) throws IOException {
        List<Text> texts = null;
        try {
            texts = InputDocument.getDefaultReadHtml();//每个文件形成一条Txt
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Node node = new Node();

        node.setTexts(texts);
        CreateTrinity trinity = new CreateTrinity(texts);
        trinity.createTrinity(node);
        preScanTrinity(node);


        LearnTemplate learnTemplate = new LearnTemplate();
        String regex = learnTemplate.learnTemplate(node, "");//提取正则表达式
        System.out.println("regex=" + regex);
        List<String> regexs = CutRegexUtils.getRegexs(regex, texts.get(0).getText());
        System.out.println(regexs);
        logger.info("使用提取的正则表达式进行文本获取");
        // regex = ParseRegexUtils.cutRegex(regex);
        for (String regex1 : regexs) {
            int groupNumbers = ParseRegexUtils.analyseNumberGroup(regex1);
            regex1 = ParseRegexUtils.getNormalRegex(regex1,regexs.size());
            System.out.println("regex=" + regex1);
            System.out.println(groupNumbers);
            for (int i = 1; i <= groupNumbers; i++) {
                System.out.println(ParseRegexUtils.extraText(regex1, i, texts.get(0).getText()));
            }
        }
    }
}
