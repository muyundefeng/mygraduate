package extractor.util;


import extractor.trinity.Text;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 自定义String工具类
 *
 * @author lisheng
 */
public class StringUtil {

    /**
     * 获取Text文本长度,将一个标签视为一个token，计算token的个数
     *
     * @param text
     * @return
     */
    public static int length(Text text) {
        return count(text.getText()); //+ rawText.length();
    }

    /**
     * 获取String中的标签个数
     *
     * @param str
     * @return
     */
    public static int count(String str) {
        int count = 0;
        Pattern pattern = Pattern.compile("<[^>]*>");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    /**
     * 截取子字符串，截取字符串的时候只关心字符串中所包含的标签
     *
     * @param text  　函数作用的目标
     * @param start 　子字符串开始的位置
     * @param end   　子字符串结束的位置
     * @return　返回子字符串
     */
    public static String subString(Text text, int start, int end) {
        int realStart = realStartIndex(text, start);
        int realEnd = realEndIndex(text, end);
        return text.getText().substring(realStart, realEnd);
    }

    /**
     * 查找字符串真正开始的位置
     *
     * @param text
     * @param start
     * @return
     */
    public static int realStartIndex(Text text, int start) {
        String raw = text.getText();
        int length = 0;
        if (raw != null) {
            if (raw.startsWith("<")) {

            } else {
                length = raw.indexOf("<");
                raw = raw.substring(length);
            }
        }
        Pattern pattern = Pattern.compile("<[^>]*>");
        Matcher matcher = pattern.matcher(raw);
        int realStartIndex = 0;
        int count = 0;
        String rawTemp = "";
        String temp = "";
        String match = "";
        String endMatch = "";
        while (matcher.find()) {
            if (count != (start + 1)) {
                temp = match;
                match = matcher.group();
                endMatch = match;
                count++;
                realStartIndex += match.length();
                String betweenTokenStr = StringUtils.substringBetween(rawTemp, temp, match);
                if (betweenTokenStr != null && betweenTokenStr.length() > 0) {

                    realStartIndex += betweenTokenStr.length();
                }
                rawTemp = raw;
                raw = raw.replaceFirst("<[^>]*>", "");
            } else
                break;
        }
        return realStartIndex + length - endMatch.length();
    }

    /**
     * 查找字符串真正结束的位置
     *
     * @param text
     * @return
     */
    @SuppressWarnings("Duplicates")
    public static int realEndIndex(Text text, int end) {
        String raw = text.getText();
        int length = 0;
        if (raw != null) {
            if (raw.startsWith("<")) {

            } else {
                length = raw.indexOf("<");
                raw = raw.substring(length);
            }
        }
        Pattern pattern = Pattern.compile("<[^>]*>");
        Matcher matcher = pattern.matcher(raw);
        int realEndIndex = length;
        int count = 0;
        String rawTemp = raw;
        String temp = "";
        String match = "";
        //String endMacth = "";
        while (matcher.find()) {
            if (count != end) {
                temp = match;
                match = matcher.group();
                count++;
                realEndIndex += match.length();
                String betweenTokenStr = StringUtils.substringBetween(rawTemp, temp, match);
                if (betweenTokenStr != null && betweenTokenStr.length() > 0) {
                    realEndIndex += betweenTokenStr.length();
                }
                rawTemp = raw;
                raw = raw.replaceFirst("<[^>]*>", "");
            } else
                break;
        }
        return realEndIndex;
    }

    public static boolean equals(String src, String target) {
        String srclabel = extaLabel(src);
        String targetlabel = extaLabel(target);
        return srclabel.equals(targetlabel) ? true : false;
    }

    public static String extaLabel(String str) {
        String labelRegex = "(<[^>]*>)";
        Pattern pattern = Pattern.compile(labelRegex);
        Matcher matcher = pattern.matcher(str);
        String re = "";
        while (matcher.find()) {
            re += matcher.group(1);
        }
        return re;
    }
//
//    public static void main(String[] args) {
//        String str = "<title>调查显示多数美国人仍然只用电视机看传统电视节目_科技_腾讯网</title>\n" +
//                "<em>0</em>\n" +
//                "<div>Recode中文站5月11日报道据美国互动广告局（IAB）的最新研究报告称，超过半数（56%）的美国人已经能够在电视机上观看互联网视频，但是他们仍然只用电视机看传统电视节目。报告指出，自2015年以来，能够在电视机上观看互联网视频的美国成年人比例增加了20个百分点，他们要么可以通过电视机本身观看互联网视频，要么可以通过各种机顶盒比如Chromecast观看互联网视频。但是美国成年人使用电视机的很大一部分时间（39%）都是用电视机来观看传统电视节目。他们用了大约24%的时间通过Netflix、YouTube或Hulu观看互联网视频。但是从另一个角度来看，随着<a>\n" +
//                "</a>和Netflix等流媒体服务公司提供的互联网电视服务越来越多，互联网电视服务在观众观看时间中所占的比例却在增长。那些使用电视机观看Netflix或Hulu的互联网视频的人观看互联网视频的数量和频率不断增加。拥有流媒体功能电视机的美国人中，大约一半（46%）的人每天都要观看流媒体视频，高于2015年的32%。他们观看的流媒体视频是哪些内容呢？79%的人观看的是互联网电视节目，70%的人观看的是各种原创剧比如Netflix的《怪奇物语》或Hulu的《使女的故事》。（编译/林靖东）</div>\n" +
//                "<a>摩拜回应高管贪腐传言：纯属捏造事实，已向警方报案</a>\n" +
//                "<a>小米与中国电信战略合作将推不限流量“米粉卡”</a>\n" +
//                "<a>谷歌居然自己搞了个广告拦截器到底意欲何为？</a>";
//        Text text = new Text(str);
//        System.out.println(subString(text, 1, 4));
//    }

}
