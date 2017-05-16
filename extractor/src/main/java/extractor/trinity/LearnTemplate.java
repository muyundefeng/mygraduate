package extractor.trinity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 遍历三叉树,生成模板算法
 *
 * @author lisheng
 */
public class LearnTemplate {

    private static final String NO_PREFFIX = "nill";//当前缀为空时,默认变量

    private static final String NO_SEPERATOR = "noll";

    private static final String NO_SUFFIX = "nell";

    public static final String OPTION_CHAR = "?";

    public static final String A_CHAR = ".";

    public static final String CAN_HAVE_CHARS = "*";

    public static final String HAVE_CHAES = "+";

    private static Logger logger = LoggerFactory.getLogger(LearnTemplate.class);

    /**
     * 自动生成正则表达式主要方法
     *
     * @param node
     * @return
     */
    public String learnTemplate(Node node, String result) {
        List<Text> texts1 = node.getTexts();
        for (Text text : texts1)
            logger.info("访问三叉树节点=" + text.getText());
        if (isOptional(node)) {
            result += "(";
            List<Text> texts = node.getTexts();
            for (Text text : texts)
                logger.info("访问可选三叉树节点=" + text.getText());
        }
        if (isLeaf(node)) {
            if (isVariable(node)) {
                result += ".*";
                List<Text> texts = node.getTexts();
                for (Text text : texts)
                    logger.info("访问捕获成功三叉树节点=" + text.getText());
                logger.info("捕获信息成功之后的result结果＝" + result);
            }
        } else {
            result += learnTemplate(node.getPreffixNode(), "");
            logger.info("捕获的模板为＝" + node.getPattern().getString());
            logger.info("result=" + result);
            result += node.getPattern().getString();
            logger.info("result=" + result);
            if (isRepeatable(node)) {
                logger.info("Repeat");
                result += "(" + learnTemplate(node.getSeparatorNode(), "")
                        + node.getPattern().getString();
                if (contain(node))
                    result += ")" + CAN_HAVE_CHARS;
                else
                    result += ")" + HAVE_CHAES;
            }
            result += learnTemplate(node.getSuffixNode(), "");
        }
        if (isOptional(node))
            result += ")" + OPTION_CHAR;
        return result;

    }

    /**
     * 判断一个节点是否重复
     *
     * @param node
     * @return
     */
    public boolean isOptional(Node node) {
        List<Text> list = node.getTexts();
        boolean isOptional = false;
        int empty = 0;
        for (Text text : list) {
            if (text.getText().equals(NO_PREFFIX) || text.getText().equals(NO_SEPERATOR)
                    || text.getText().equals(NO_SUFFIX)) {
                empty++;
            }
        }
        if (empty > 0 && empty < list.size())
            isOptional = true;
        return isOptional;
    }

    /**
     * 判断一个节点是否为重复.主要是检查共享模板在node节点中出现的次数,如果Text中的pattern至少
     * 出现的次数多于一次
     *
     * @param node
     * @return
     */
    public boolean isRepeatable(Node node) {
        boolean isRepeatable = false;
        String pattern = node.getPattern().getString();
        List<Text> texts = node.getTexts();
        for (Text text : texts) {
            if (text.getText().equals(NO_PREFFIX) &&
                    text.getText().equals(NO_SEPERATOR) &&
                    text.getText().equals(NO_SUFFIX) &&
                    count(text.getText(), pattern) > 1) {
                isRepeatable |= true;
                break;
            } else {
                isRepeatable |= false;
            }
        }
        logger.info("isRepeatable=" + isRepeatable);
        return isRepeatable;
    }

    /**
     * 计算Text中出现共享模板的次数.
     *
     * @param text
     * @param pattern
     * @return
     */
    public int count(String text, String pattern) {
        if (text.equals(NO_SEPERATOR))
            return -1;
        int count = 0;
        Pattern pattern2 = Pattern.compile(pattern);
        Matcher m = pattern2.matcher(text);
        while (m.find()) {
            count++;
        }
        return count;
    }

    /**
     * 判断节点中是否存在nill
     *
     * @param node
     * @return
     */
    public boolean contain(Node node) {
        boolean isContain = true;
        List<Text> list = node.getTexts();
        for (Text text : list) {
            if (text.getText().contains(NO_SEPERATOR)) {
                isContain &= true;
            } else {
                isContain &= false;
            }
        }
        return isContain;
    }

    /**
     * 判断一个节点是否为叶子节点
     *
     * @param node
     * @return
     */
    public boolean isLeaf(Node node) {
        boolean isLeaf = false;
        if (node != null) {
            if (node.getPreffixNode() == null && node.getSeparatorNode() == null
                    && node.getSuffixNode() == null) {
                isLeaf = true;
            }
        }
        return isLeaf;
    }

    /**
     * 判断一个节点是具有值的属性
     *
     * @param node
     * @return
     */
    public boolean isVariable(Node node) {
        boolean isVariable = true;
        List<Text> texts = node.getTexts();
        for (Text text : texts) {
            if (!text.getText().matches("<[^>]*>") && !text.getText().equals(NO_PREFFIX)
                    && !text.getText().equals(NO_SEPERATOR)
                    && !text.getText().equals(NO_SUFFIX))
                isVariable &= true;
            else {
                isVariable = false;
                break;
            }
        }
        return isVariable;
    }

    /**
     * 获取的捕获分组可能存在多个,且数目不确定,捕获分组之间代表字符不能出现重复,获取字母之间的组合
     *
     * @return得到一个捕获分组
     */
    public String freshCaptureGroup() {
        return A_CHAR + CAN_HAVE_CHARS;
    }
}
