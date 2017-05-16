package extractor.test;

import extractor.trinity.CreateTrinity;
import extractor.trinity.Node;
import extractor.trinity.Pattern;
import extractor.trinity.Text;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lisheng on 17-5-12.
 */
public class testMethod {

    public CreateTrinity createTrinity = new CreateTrinity();

    @Test
    public void testShortestText() {
        List<Text> list = new ArrayList<Text>();
        String str = "<title>两名中国女大学生在德国被强奸案：伊拉克难民认罪_新闻_腾讯网</title>";
        String str1 = "<title>两名中国女大学生在德国被强奸案：伊拉克<a></a>难民认罪_新闻_腾讯网sfsa</title>";
        String str2 = "nill";
        list.add(new Text(str));
        list.add(new Text(str1));
        list.add(new Text(str2));
        Node node = new Node();
        node.setTexts(list);
        Text text = createTrinity.findShortestText(node);
        System.out.println(text);
    }

    @Test
    public void testFindMatches() {
        String base = "<title>调查显示多数美国人仍然只用电视机看传统电视节目_科技_腾讯网</title>";
        Text baseText = new Text(base);
        String text = "<title>国防部：中国海军抓获<a>sadfasdfasdfsad</a>3名海盗移交给索马里当局_新闻_腾讯网</title>";
        Text texta = new Text(text);
        List<Integer> list = createTrinity.findMatches(texta, baseText, 0, 1);
        System.out.println(list);
    }


    @Test
    public void testFindPattern() {
        List<Text> list = new ArrayList<Text>();
        String str = "<title>两名中国女大学生在德国被强奸案：伊拉克难民认罪_新闻_腾讯网</title>";
        String str1 = "<title>两名中国女大学生在德国被强奸案：伊拉克<a></a>难民认罪_新闻_腾讯网sfsa</title>";
        String str2 = "nill";
        list.add(new Text(str));
        list.add(new Text(str1));
        list.add(new Text(str2));
        Node node = new Node();
        node.setTexts(list);
        Map<Pattern, List<Map<Text, List<Integer>>>> map = createTrinity.findPattern(node, 1);
        for (Map.Entry<Pattern, List<Map<Text, List<Integer>>>> entry : map.entrySet()) {
            System.out.println("pattern =" + entry.getKey());
            List<Map<Text, List<Integer>>> list1 = entry.getValue();
            for (Map<Text, List<Integer>> map1 : list1) {
                for (Map.Entry<Text, List<Integer>> entry1 : map1.entrySet()) {
                    System.out.println("节点中的文本:" + entry1.getKey());
                    System.out.println("节点中的闻之信息:" + entry1.getValue());
                }
            }
        }
    }

    @Test
    public void testIsVaribaleLeave() {
        String str = "nell";
        String str1 = "<div>safdasdf</div>";
        String str2 = "<div>asfda</div>";
        List<Text> list = new ArrayList<Text>();
        list.add(new Text(str));
        list.add(new Text(str1));
        list.add(new Text(str2));
        Node node = new Node();
        node.setTexts(list);
        System.out.println(CreateTrinity.isNotVaribaleLeave(node));

    }
}
