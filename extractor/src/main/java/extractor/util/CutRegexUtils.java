package extractor.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 分割正则表达式
 * Created by lisheng on 17-5-16.
 */
public class CutRegexUtils {
    /**
     * 是否有必要进行正则表达式的切割
     *
     * @param regex
     * @param html
     * @return
     */
    public static boolean isCut(String regex, String html) {
        return html.matches(regex);
    }

    public static List<String> getRegexs(String regex, String html) {
        List<String> regexs = new ArrayList<String>();
        if (isCut(regex, html)) {
            regexs.add(regex);
            return regexs;
        } else {
            //使用正则表达式的元字符进行切割
            int length = regex.length();
            int startIndex = 0;
            int endIdex = regex.indexOf(".*");
            while (true) {
                if (startIndex < length && endIdex < length) {
                    String label = "";
                    String subRegex = regex.substring(endIdex);
                    String patternStr = "(<[^>]*>)";
                    Pattern pattern = Pattern.compile(patternStr);
                    Matcher matcher = pattern.matcher(subRegex);
                    if (matcher.find()) {
                        label = matcher.group(1);
                    }
                    String regexA = regex.substring(startIndex, endIdex + 2) + label;
                    startIndex += regexA.length();
                    endIdex = regex.substring(startIndex).indexOf(".*")+startIndex;
                    regex = regex.trim();
                    if(!regexA.contains("(")&&!regexA.matches(">.*")&&!regexA.startsWith(".*")) {
                        regexs.add(regexA);
                    }
                } else {
                    break;
                }
            }
        }
        return regexs;
    }

    public static void main(String[] args) {
        String regex = "<title>.*</title><a>登录</a><a>.*</a><option>新闻</option><option>图片</option><option>博客</option><option>视频</option></form>.*<div><a>.*</a>.*<div></div>.*<div></div>(.*)<div><div>文章关键词：<div></div>(.*)</a>(.*)</div>聚焦<dl></div>应用中心新浪公益新浪游戏互动活动热点推荐<div><div>·<a>(.*)</a></s>(.*)</a>(.*)</a><div>(.*)</div>()?</a>(.*)</a><div>(.*)</div>()?</a>(.*)</a><div>(.*)</div>(.*)</a></div>Copyright1996-2017SINACorporation,AllRightsReserved新浪公司<a><a>(.*)</form>0</div>";
        System.out.println(getRegexs(regex, "<gdsfgsdgds>"));
    }

}
