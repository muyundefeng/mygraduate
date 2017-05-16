package extractor.util;


import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对html文档进行预处理
 * Created by lisheng on 17-5-9.
 */
public class ProcessHtmlUtils {

    public static final String regMetaCha[] = {"?", "^", "[", "]", "{", "}", "(", ")", "$", "+", ".", "*"};

    public static String rmSomeScript(String html) {
        html = html.replaceAll("(?is)<!DOCTYPE.*?>", "");
        html = html.replaceAll("(?is)<!--.*?-->", "");
        html = html.replaceAll("(?is)<script.*?>.*?</script>", "");
        html = html.replaceAll("(?is)<iframe.*?>.*?</iframe>", "");
        html = html.replaceAll("(?is)<noscript.*?>.*?</noscript>", "");
        html = html.replaceAll("(?is)<style.*?>.*?</style>", "");
        html = html.replaceAll("&.{2,5};|&#.{2,5};", " ");
        String patternStr = "<(\\w+)?(\\s[^>]+)>";
        Pattern pattern = Pattern.compile(patternStr);

        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            String str = matcher.group(2);
            String label = matcher.group(1);
            for (String ch : regMetaCha) {
                str = str.replace(ch, "\\" + ch);
            }
            html = html.replaceFirst(str, "");
        }
        //得到网页中导航信息
        String aLabel = ExtraMainBodyUtils.extraMainBody(html, "<a>");//根据文本块进行判断
        String rmInfos[] = aLabel.split("\\n");
        for (String str : rmInfos) {
            html = html.replace(str, "");
        }
//        html = html.replaceAll("\\s", "");//移除空格
        html = html.replaceAll("<meta>", "");//移除meta元素
        html = html.replaceAll("<head>", "");//移除head元素
        html = html.replaceAll("<p>", "");//移除p标签
        html = html.replaceAll("</p>", "");
        html = html.replaceAll("<span>", "");//移除span
        html = html.replaceAll("</span>", "");
        html = html.replaceAll("<strong>", "");//移除strong
        html = html.replaceAll("</strong>", "");
        html = html.replaceAll("<img>", "");//移除图片
        html = html.replaceAll("<br>", "");
        html = html.replaceAll("<link>", "");
        html = html.replaceAll("<font>", "");
        html = html.replaceAll("</font>", "");
        html = html.replaceAll("<li>", "");
        html = html.replaceAll("</li>", "");
        html = html.replaceAll("\\n+", "\n");
        html = html.replaceAll("<em>", "");
        html = html.replaceAll("</em>", "");
        html = html.replaceAll("<(/)?h[^>]*>", "");
        html = html.replaceAll("<(/)?[^>]*>", "");
//        html = html.replaceAll("(?is)<iframe[^>]*>", "");
        String aLabel1 = ExtraMainBodyUtils.extraMainBody(html, "<a>");//根据文本块进行判断
        String labels[] = aLabel1.split("\\n");
        for (String label : labels) {
            html = html.replace(label, "");
        }
        String segs[] = html.split("\\n");
        html = "";
        for (String seg : segs) {
            html += rmSomeNoisy(seg) + "\n";
            if (html.contains("footer"))
                break;
        }
        html = html.replaceAll("\\n", "");
        html = html.replaceAll("\\s", "");

        //提取出有元素的数据
        String extraEleData = "(?i)<[^>]+>[^<]+<[^>]+>";
        Pattern patternData = Pattern.compile(extraEleData);
        Matcher matcher1 = patternData.matcher(html);
        String afterProcessHtml = "";
        while (matcher1.find()) {
            String str = matcher1.group(0);
            afterProcessHtml += str;
        }
        return afterProcessHtml;
    }

    public static String rmSomeNoisy(String str) {
        int count = StringUtils.countMatches(str, "<a>");
        if (count > 3)
            return "";
        else
            return str;
    }

    public static void main(String[] args) {
        String str = "<iframe src=\"http://v.qq.com/video/playview.html?vid=o0500gf4ps0\" width=\"0\" height=\"0\" style=\"display:none;\"></iframe>";
        System.out.println(rmSomeScript(str));
    }
}
