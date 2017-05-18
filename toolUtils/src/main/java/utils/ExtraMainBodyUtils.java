package utils;


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
                    if (lines.get(ii).length() < 3) continue;
                    tmp.append(lines.get(ii) + "\n");
                }
                String str = tmp.toString();
                text.append(str);
                boolstart = boolend = false;
            }
        }
        return text.toString();
    }
}
