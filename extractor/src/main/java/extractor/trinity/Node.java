package extractor.trinity;

import java.util.List;

/**
 * @author lisheng
 *         定义三叉树的节点
 */
public class Node {

    private List<Text> texts;//定义该节点所容纳文件的内容

    private Pattern pattern;

    private Node preffixNode;

    private Node separatorNode;

    private Node suffixNode;

    private static final String NO_PREFFIX = "nill";//当前缀为空时,默认变量

    private static final String NO_SEPERATOR = "noll";

    private static final String NO_SUFFIX = "nell";

    public Node() {
        // TODO Auto-generated constructor stub
    }

    public Node(List<Text> texts, Pattern pattern) {
        this.texts = texts;
        this.pattern = pattern;
    }

    public List<Text> getTexts() {
        return texts;
    }

    public void setTexts(List<Text> texts) {
        this.texts = texts;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Node getPreffixNode() {
        return preffixNode;
    }

    public void setPreffixNode(Node preffixNode) {
        this.preffixNode = preffixNode;
    }

    public Node getSeparatorNode() {
        return separatorNode;
    }

    public void setSeparatorNode(Node separatorNode) {
        this.separatorNode = separatorNode;
    }

    public Node getSuffixNode() {
        return suffixNode;
    }

    public void setSuffixNode(Node suffixNode) {
        this.suffixNode = suffixNode;
    }

    @Override
    public String toString() {
        return "Node{" +
                "texts=" + texts +
                ", pattern=" + pattern +
                ", preffixNode=" + preffixNode +
                ", separatorNode=" + separatorNode +
                ", suffixNode=" + suffixNode +
                '}';
    }
}
