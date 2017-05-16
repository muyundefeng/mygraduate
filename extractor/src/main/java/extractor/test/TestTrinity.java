package extractor.test;

import extractor.input.InputDocument;
import extractor.trinity.CreateTrinity;
import extractor.trinity.LearnTemplate;
import extractor.trinity.Node;
import extractor.trinity.Text;
import extractor.util.CutRegexUtils;
import extractor.util.ParseRegexUtils;
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

    }
}
