package extractor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by lisheng on 17-5-16.
 */
public class FileUtils {

    public static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static final String hostname = "qq.com";

    /**
     * @param savepath  存放html文件路径
     * @param clusterId url聚类的标识符
     */
    public static void savetoFile(String content, String savepath, String clusterId) {
        FileOutputStream out = null;
        StringBuffer sb = null;
        try {
            String path = savepath + clusterId;
            logger.info("write html to file=" + path + "=========");
            File file = new File(path);
            if (!file.exists())
                file.createNewFile();
            out = new FileOutputStream(file, true); //如果追加方式用true
            sb = new StringBuffer();
            sb.append(content);
            out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
            out.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        }
        logger.info("end writing to file html");
    }

    @SuppressWarnings("Duplicates")
    public static String readUrlFromFile(String path) throws IOException {
        logger.info("read url from file=" + path);
        String content = "";
        FileInputStream is = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader in = new BufferedReader(isr);
        String line = "";
        int count = 0;
        while ((line = in.readLine()) != null) {
            if (isDetail(line)) {
                content += line + "\n";
                count++;
            }
            if (count == 2)
                break;
        }
        isr.close();
        is.close();
        logger.info("content=" + content);
        return content;
    }

    public static boolean isDetail(String url) {
        String patternStr = ".*" + hostname + "(/.*){2,}.+";
        if (url.matches(patternStr)) {
            return true;
        } else {
            return false;
        }
    }
}
