package extractor.util;


import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据文本的标签<p></p>提取新闻类主体
 * Created by lisheng on 17-5-10.
 */
public class ExtraMainBodyUtils {

    @SuppressWarnings("Duplicates")
    public static String extraMainBody(String html, String label) {
        List<String> lines;
        int blocksWidth = 1;//设置连续的文件块(这里每行代表一个文件块)
        int threshold = 1;//在连续文件块中出现label标签的最小数目
        int start;
        int end;
        StringBuilder text = new StringBuilder();
        ArrayList<Integer> indexDistribution = new ArrayList<Integer>();

        lines = new ArrayList<String>();
        String segs[] = html.split("\\n");
        for (String seg : segs) {
            if (!seg.matches("\\s*")) {
                lines.add(seg);
            }
        }
        for (int i = 0; i < lines.size() - blocksWidth; i++) {
            int lablesNum = 0;
            for (int j = i; j < i + blocksWidth; j++) {
                int count = StringUtils.countMatches(lines.get(j), label);
                lablesNum += count;
            }
            indexDistribution.add(lablesNum);
        }
        start = -1;
        end = -1;
        boolean boolstart = false, boolend = false;
        text.setLength(0);
        for (int i = 0; i < indexDistribution.size() - blocksWidth; i++) {
            if (indexDistribution.get(i) >= threshold && !boolstart) {
                if (indexDistribution.get(i + 1).intValue() != 0) {
                    boolstart = true;
                    start = i;
                    continue;
                }
            }
            if (boolstart) {
                if (indexDistribution.get(i).intValue() == 0
                        || indexDistribution.get(i + 1).intValue() == 0) {
                    end = i;
                    boolend = true;
                }
            }
            StringBuilder tmp = new StringBuilder();
            if (boolend) {
                for (int ii = start; ii <= end; ii++) {
                    if (lines.get(ii).length() < 5) continue;
                    tmp.append(lines.get(ii) + "\n");
                }
                String str = tmp.toString();
                if (str.contains("Copyright")) continue;
                text.append(str);
                boolstart = boolend = false;
            }
        }
        return text.toString();
    }

    public static void main(String[] args) {
        String str = "<a>水电外送消纳矛盾暗涌西南\"弃水\"形势严峻</a>\n" +
                "<a>发改委：今年煤炭去产能将超1.5亿吨地条钢退出</a>\n" +
                "<a>专家谈勒索病毒：获利渠道不切断可能还会遭攻击</a>\n" +
                "<a>微整形市场“潜规则”：兜售针剂推荐工作室注射</a>\n" +
                "<a>贫困女大学生3年捐2万自己每个月花不到两百</a>\n" +
                "<a>药品零加成政策落地“有喜有忧”医院“倒贴”补缺</a>\n" +
                "<a>破解以药养医须坚持“三医联动”</a>\n" +
                "<a>银监会：房地产贷款风险在可控范围内</a>\n" +
                "<a>“市场调整+监管风暴”下险资何去何从</a>\n" +
                "<a>1883只个股“跌破”熔断时价格暴风跌7成</a>\n" +
                "<li>客户端</li>\n" +
                "<li>搜索</li>\n" +
                "<li>频道</li>";
        System.out.println(extraMainBody(str, "<a>"));
    }
}
