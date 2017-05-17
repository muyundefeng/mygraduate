package extractor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DownloadUtils;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisheng on 17-5-16.
 */
public class FileUtils {

    public static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static String hostname;


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
            out = new FileOutputStream(file); //如果追加方式用true
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

    public static String readUrlFromFile(String path) throws IOException {
        logger.info("read url from file=" + path);
        String content = "";
        FileInputStream is = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader in = new BufferedReader(isr);
        String line = "";
        int count = 0;//只需要从一个url文件中提取出两个url链接就可以1
        String myline = in.readLine();
        URL url = new URL(myline);
        hostname = url.getHost();
        while ((line = in.readLine()) != null) {
            if (line.length() > 0 && isDetail(line) && DownloadUtils.download(line) != null) {
                content += line + "\n";
                count++;
            }
            if (count == 2)
                break;
        }
        isr.close();
        is.close();
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

    /**
     * 得到文件夹下的文件列表
     *
     * @param path
     * @return
     */
    public static List<String> getFileName(String path) throws IOException {
        File file = new File(path);
        File[] files = file.listFiles();
        List<String> names = new ArrayList<>();
        for (File file1 : files) {
            names.add(file1.getCanonicalPath());
        }
        return names;
    }
}
