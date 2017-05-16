package extractor.trinity;

import extractor.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;


/**
 * @author lisheng
 *         创建三叉树的核心类
 */
public class CreateTrinity {

    public static int Max;

    public static int Min = 1;

    public static int S;

    private static final String NO_PREFFIX = "nill";//当前缀为空时,默认变量

    private static final String NO_SEPERATOR = "noll";

    private static final String NO_SUFFIX = "nell";

    List<Node> leaves = new ArrayList<Node>();

    private static int flag = 0;

    private static Logger logger = LoggerFactory.getLogger(CreateTrinity.class);

    public CreateTrinity() {

    }

    @SuppressWarnings("Since15")
    public CreateTrinity(List<Text> texts) throws IOException {
        // TODO Auto-generated constructor stub
        texts.sort(new Comparator<Text>() {
            public int compare(Text t1, Text t2) {
                // TODO Auto-generated method stub
                if (StringUtil.length(t1) > StringUtil.length(t2)) {//这里的长度比较是每个文本所拥有的标签个数
                    return -1;
                } else if (StringUtil.length(t1) < StringUtil.length(t2)) {
                    return 1;
                } else
                    return 0;
            }
        });
        Max = StringUtil.length(texts.get(0));
        logger.info("max length html is " + Max);
        S = Max;
    }

    /**
     * 创建三叉树
     * 对主要函数进行封装
     *
     * @param node
     */
    public void createTrinity(Node node) {
        logger.info("starting build trinity");
        createTrinity(node, Max, Min);
    }

    /**
     * 创建三叉树
     * 其中max与min所代表的是一个文本字符串中所包含的标签的数目
     *
     * @param node
     */
    public void createTrinity(Node node, int Max, int Min) {
        boolean expanded = false;
        int size = Max;
        while (size >= Min && !expanded) {
            expanded = expand(node, size);//将根节点进行扩展，其中size表示寻找模板的长度的最大值，node表示本节点
            size = size - 1;
        }
        if (expanded) {
            leaves.clear();
            flag = 0;
            if (node.getPreffixNode() != null) {
                Node node2 = node.getPreffixNode();
                for (Text text : node2.getTexts()) {
                    logger.info("前缀节点元素为:" + text.getText() + " ");
                }
                System.out.println();
                if (isNotVaribaleLeave(node.getPreffixNode())) {
                    logger.info("从前缀开始分裂节点");
                    createTrinity(rebuildNode(node2), size + 1, Min);
                }
            }
            if (node.getSeparatorNode() != null) {
                Node node2 = node.getSeparatorNode();
                for (Text text : node2.getTexts()) {
                    logger.info("分隔符节点元素为:" + text.getText() + " ");
                }
                System.out.println();
                if (isNotVaribaleLeave(node.getSeparatorNode())) {
                    logger.info("从分隔符开始分裂节点");
                    logger.info("Min=" + Min);
                    logger.info("size=" + (size + 1));
                    createTrinity(rebuildNode(node2), size + 1, Min);
                }
            }
            if (node.getSuffixNode() != null) {
                Node node2 = node.getSuffixNode();
                for (Text text : node2.getTexts()) {
                    logger.info("后缀节点元素为:" + text.getText() + " ");
                }
                System.out.println();
                if (isNotVaribaleLeave(node.getSuffixNode())) {
                    logger.info("从后缀开始分裂节点");
                    createTrinity(rebuildNode(node2), size + 1, Min);
                }
            }
        }
    }

    /**
     * 判断叶子节点是否为提取的文本信息，对于非文本信息进行返回true，再进行相关的分裂
     * 对于文本信息进行保留，不做分裂处理
     *
     * @param node
     * @return
     */
    public static boolean isNotVaribaleLeave(Node node) {
        List<Text> list = node.getTexts();
        boolean flag = false;
        for (Text text : list) {
            if (text.getText().contains("<"))
                flag |= true;
        }
        return flag;
    }

    /**
     * 扩展本节点
     *
     * @param node 本节点
     * @param size 共享模板的长度
     * @return
     */
    public boolean expand(Node node, int size) {
        boolean result = false;
        int nodeSize = node.getTexts().size();//确定node节点中所包含文本条目的数量
        logger.info("size1=" + size);
        if (nodeSize > 0) {
            Map<Pattern, List<Map<Text, List<Integer>>>> map = findPattern(node, size);//size寻求共享模板的长度
            if (map != null) {
                for (Entry<Pattern, List<Map<Text, List<Integer>>>> entry : map.entrySet()) {
                    Pattern pattern = entry.getKey();
                    List<Map<Text, List<Integer>>> list = entry.getValue();
                    if (list != null) {
                        result = true;
                        createChilder(node, list, pattern);
                    }
                }
            }
        }
        return result;
    }

    /**
     * @param node 产生共享模板的节点
     * @param s    寻找长度为s的模板
     * @return/Map:key:模板样式，通文本来表示；value:表示该节点，该模板之下的该节点的信息，包括文本以及文本对应的位置
     */
    public Map<Pattern, List<Map<Text, List<Integer>>>> findPattern(Node node, int s) {
        boolean found = false;
        Text base = findShortestText(node);//寻找包含标签数目最少的文本
        logger.info("shortest text is " + base.getText());
        if (base != null) {
            Pattern pattern = null;
            //Map:key:模板样式，通文本来表示；value:表示该节点，该模板之下的list数据。
            Map<Pattern, List<Map<Text, List<Integer>>>> targetMap = new HashMap<Pattern, List<Map<Text, List<Integer>>>>();
            List<Map<Text, List<Integer>>> patternList = null;
            for (int i = 0; i <= StringUtil.length(base) - s; i++) {
                if (!found) {
                    patternList = new ArrayList<Map<Text, List<Integer>>>();
//                    found = true;
                    for (Text text : node.getTexts()) {
                        List<Integer> matches = findMatches(text, base, i, s);
                        if (!text.getText().equals(base.getText())) {
                            found = isFound(matches);
                            if (found) {
                                if (flag == 0) {//只添加一次base
                                    List<Integer> baseList = new ArrayList<Integer>();//存放开始的标签位置，以及所包含的标签数目
                                    baseList.add(i);//标签开始的位置
                                    baseList.add(s);//从开始位置所容纳标签的个数
                                    logger.info("baseList=" + baseList);
                                    Map<Text, List<Integer>> map = new HashMap<Text, List<Integer>>();//map中的key是指html文本
                                    map.put(base, baseList);
                                    patternList.add(map);
                                    flag++;
                                }
                                Map<Text, List<Integer>> map = new HashMap<Text, List<Integer>>();
                                map.put(text, matches);
                                patternList.add(map);
                            }
                        }
                    }
                    if (found) {
                        pattern = new Pattern();
                        pattern.setString(subSqence(base, i, s));
                        logger.info("pattern is:" + pattern.getString() + "============");
                        targetMap.put(pattern, patternList);
                        break;
                    }
                }
            }
            return targetMap;
        } else {
            return null;
        }

    }

    /**
     * 根据传递过来的参数,对节点node进行分裂:前缀,分隔符与后缀
     *
     * @param node    该节点信息
     * @param list    表示该节点的信息，
     * @param pattern 模板字符串
     */
    public void createChilder(Node node, List<Map<Text, List<Integer>>> list, Pattern pattern) {
        Node prefix = new Node();
        Node separator = new Node();
        Node suffix = new Node();
        logger.info("some info is:" + list.toString());
        logger.info("pattern is" + pattern.getString());
        node.setPattern(pattern);
        int len = list.size();
        List<Text> preffixTexts = new ArrayList<Text>();//创建前缀节点所包含文本的链表
        for (Text text : node.getTexts()) {
            for (int j = 0; j < len; j++) {
                Map<Text, List<Integer>> map = list.get(j);
                boolean flag = false;
                for (Entry<Text, List<Integer>> entry : map.entrySet()) {
                    Text tempText = entry.getKey();
                    if (tempText.getText().equals(text.getText())) {
                        List<Integer> matches = entry.getValue();//获得匹配的位置以及匹配的长度
                        Text text2 = new Text(computePreffix(text, matches, pattern));
                        preffixTexts.add(text2);
                        flag = true;
                        break;
                    }
                }
                if (flag == true) {
                    break;
                }
            }
            continue;
        }
        logger.info("total preffix is=" + preffixTexts);
        logger.info("first preffix is=" + preffixTexts.get(0));
        prefix.setTexts(preffixTexts);
        List<Text> separatorTexts = new ArrayList<Text>();
        for (Text text : node.getTexts()) {
            for (int j = 0; j < len; j++) {
                Map<Text, List<Integer>> map = list.get(j);
                boolean flag = false;
                for (Entry<Text, List<Integer>> entry : map.entrySet()) {
                    Text tempText = entry.getKey();
                    if (tempText.getText().equals(text.getText())) {
                        List<Integer> matches = entry.getValue();
                        Text text2 = new Text(computeSeperator(text, matches, pattern));
                        separatorTexts.add(text2);
                        flag = true;
                        break;
                    }
                }
                if (flag == true) {
                    break;
                }
            }
            continue;
        }
        logger.info("total separatorTexts=" + separatorTexts);
        separator.setTexts(separatorTexts);

        //计算后缀节点
        List<Text> suffixTexts = new ArrayList<Text>();
        for (Text text : node.getTexts()) {
            for (int j = 0; j < len; j++) {
                Map<Text, List<Integer>> map = list.get(j);
                boolean flag = false;
                for (Entry<Text, List<Integer>> entry : map.entrySet()) {
                    Text tempText = entry.getKey();
                    if (tempText.getText().equals(text.getText())) {
                        List<Integer> matches = entry.getValue();
                        Text text2 = new Text(computeSuffix(text.getText(), matches, pattern));
                        suffixTexts.add(text2);
                        flag = true;
                        break;
                    }
                }
                if (flag == true) {
                    break;
                }
            }
            continue;

        }
        suffix.setTexts(suffixTexts);
        logger.info("total suffix =" + suffixTexts);
        node.setPreffixNode(prefix);

        node.setSeparatorNode(separator);
        node.setSuffixNode(suffix);
        for (Text text : prefix.getTexts()) {
            logger.info("前缀＝" + text.getText());
        }
        for (Text text : separator.getTexts()) {
            logger.info("分隔符＝" + text.getText());
        }
        for (Text text : suffix.getTexts()) {
            logger.info("后缀＝" + text.getText());
        }
    }

    public Text findShortestText(Node node) {
        List<Text> texts = node.getTexts();
        boolean flag = false;
        Text tempText = null;
        //判断一个节点中是否全部为nill,noll等字符串，如果全部都是，则不进行相关的处理
        for (Text text : texts) {
            flag |= nullText(text);
        }
        if (!flag) {
            return null;
        }
        //如果不全部为空，需要对节点进行处理
        if (flag) {
            int temp = StringUtil.length(texts.get(0));
            tempText = texts.get(0);
            for (int i = 1; i < texts.size(); i++) {
                if (StringUtil.length(texts.get(i)) < temp && !texts.get(i).getText().equals(NO_PREFFIX)
                        && !texts.get(i).getText().equals(NO_SEPERATOR)
                        && !texts.get(i).getText().equals(NO_SUFFIX)) {
                    temp = StringUtil.length(texts.get(i));
                    tempText = texts.get(i);
                }
            }
        }
        logger.info("tempText=" + tempText);
        return tempText;
    }

    /**
     * 判断node节点中是否存在无效的字符串
     *
     * @param text
     * @return
     */
    public static boolean nullText(Text text) {
        String str = text.getText();
        if (str.equals(NO_PREFFIX) || str.equals(NO_SEPERATOR) || str.equals(NO_SUFFIX))
            return false;
        else
            return true;

    }

    /**
     * 这里的匹配是指对于标签的匹配
     *
     * @param text  node节点中一条Text
     * @param base  node节点中最短的Text
     * @param index 开始索引的位置,是base的索引位置.
     * @param size  查找匹配模板的长度，base的长度
     * @return 返回的text开始的位置以及长度
     */
    public List<Integer> findMatches(Text text, Text base, int index, int size) {
        logger.info("index=" + index);
        logger.info("size=" + size);
        String subString = StringUtil.subString(base, index, index + size);

        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i <= StringUtil.length(text) - size; i++) {
            if (!text.getText().equals(base.getText())) {//排除自己比较的问题
                String subStr = StringUtil.subString(text, i, i + size);
                if (subStr.equals(subString)) {//这里的相等是指纯标签字符串的相等
                    logger.info("subStr=" + subStr);
                    logger.info("subString=" + subString);
                    list.add(i);
                    list.add(size);
                    break;
                }
            }
        }
        return list;
    }

    public static String subSqence(Text base, int index, int size) {
        String subString = StringUtil.subString(base, index, index + size);
        return subString;
    }

    public static boolean isFound(List<Integer> list) {
        return list.size() > 0 ? true : false;
    }

    /**
     * 在进行节点分裂之前先要进行节点预处理，
     * nell
     * <></>
     * <></>
     * 要去除nell元素
     *
     * @param node
     * @return
     */
    public static Node rebuildNode(Node node) {
        List<Text> texts = node.getTexts();
        List<Text> newTexts = new ArrayList<Text>();
        for (Text text : texts) {
            if (text.getText().equals(NO_SEPERATOR) || text.getText().equals(NO_SEPERATOR)
                    || text.getText().equals(NO_SUFFIX)) {
                continue;
            } else {
                newTexts.add(text);
            }
        }
        node.setTexts(newTexts);
        return node;
    }

    /**
     * 该函数是对node节点进行相关的处理,首先需要对节点中的每一个Text进行相关的处理,然后处理完成之后
     * 在进行相关的合并,组成新的Node节点.
     *
     * @param text 该节点中的Text
     * @param list 匹配生成的模板相关位置，位置以及匹配长度
     * @return
     */
    public String computePreffix(Text text, List<Integer> list, Pattern pattern) {
        String raw = text.getText();
        int index = list.get(0);//
        String preffix = "";

        if (index == 0) {
            //如果匹配的模式开始的位置是0,则不存在前缀
            if (!exitStrBefore(raw)) {
                preffix = NO_PREFFIX;
                return preffix;
            }
        }
        //一个html可能存在多个相同的pattern
        preffix = text.getText().split(pattern.getString())[0];

        logger.info(preffix);
        return preffix;
    }

    /**
     * 进行前缀之前查看是否存在字符串
     *
     * @param str
     * @return
     */
    public static boolean exitStrBefore(String str) {
        int index = str.indexOf("<");
        String str1 = str.substring(0, index);
        return str1.length() > 0 ? true : false;
    }

    public String computeSeperator(Text text, List<Integer> list, Pattern pattern) {
        String patternStr = pattern.getString() + "(.+?)" + pattern.getString();
        java.util.regex.Pattern pattern1 = java.util.regex.Pattern.compile(patternStr);
        int index = list.get(0);
        Matcher matcher = pattern1.matcher(text.getText().substring(index));
        String str = null;
        while (matcher.find()) {
            str = matcher.group(1).toString();
        }
        if (str == null)
            return NO_SEPERATOR;
        else
            return str;
    }

    /**
     * 计算后缀节点
     *
     * @param text    要处理的文本
     * @param pattern 后缀节点数据
     * @return
     */
    public static String computeSuffix(String text, List<Integer> list, Pattern pattern) {
        int index = list.get(0);
        int length = list.get(1) - 1;
        int start = index + length;
        text = text.substring(StringUtil.realStartIndex(new Text(text), start));
        text = text.replaceFirst("<[^>]*>", "");
        if (text == null || text.length() == 0 || text.matches("\\s*"))
            text = NO_SUFFIX;
        return text;
    }


    public static void main(String[] args) {
        String str = "<title>调查显示多数美国人仍然只用电视机看传统电视节目_科技_腾讯网</title>\n" +
                "<div>Recode中文站5月11日报道据美国互动广告局（IAB）的最新研究报告称，超过半数（56%）的美国人已经能够在电视机上观看互联网视频，但是他们仍然只用电视机看传统电视节目。报告指出，自2015年以来，能够在电视机上观看互联网视频的美国成年人比例增加了20个百分点，他们要么可以通过电视机本身观看互联网视频，要么可以通过各种机顶盒比如Chromecast观看互联网视频。但是美国成年人使用电视机的很大一部分时间（39%）都是用电视机来观看传统电视节目。他们用了大约24%的时间通过Netflix、YouTube或Hulu观看互联网视频。但是从另一个角度来看，随着</div><a>\n" +
                "</a>和Netflix等流媒体服务公司提供的互联网电视服务越来越多，互联网电视服务在观众观看时间中所占的比例却在增长。那些使用电视机观看Netflix或Hulu的互联网视频的人观看互联网视频的数量和频率不断增加。拥有流媒体功能电视机的美国人中，大约一半（46%）的人每天都要观看流媒体视频，高于2015年的32%。他们观看的流媒体视频是哪些内容呢？79%的人观看的是互联网电视节目，70%的人观看的是各种原创剧比如Netflix的《怪奇物语》或Hulu的《使女的故事》。（编译/林靖东）</div>\n" +
                "<a>摩拜回应高管贪腐传言：纯属捏造事实，已向警方报案</a>\n" +
                "<a>小米与中国电信战略合作将推不限流量“米粉卡”</a>\n" +
                "<a>谷歌居然自己搞了个广告拦截器到底意欲何为？</a>";
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        Pattern pattern = new Pattern();
        pattern.setString("</title>\n" +
                "<div>");
        System.out.println(computeSuffix(str, list, pattern));
    }
}
