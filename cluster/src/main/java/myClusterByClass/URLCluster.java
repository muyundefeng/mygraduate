package myClusterByClass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;

/**
 * 处理url将url做一个大体的聚类分析
 * http://sports.sohu.com/20170505/n491847969.shtml
 * 将上面的url sohu.com移除掉，并根据类别存放到不同的文件中
 * Created by lisheng on 17-5-5.
 */
public class URLCluster {

    public static Logger logger = LoggerFactory.getLogger(URLCluster.class);

    /**
     * @param urlFile  hadoop提取出的url记录
     * @param hostName 该网站的主机名
     * @param svaePath 存放处理之后的url路径
     * @throws IOException
     */
    public static void readUrlFromFile(String urlFile, String hostName, String svaePath) throws IOException {
        FileInputStream is = new FileInputStream(urlFile);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader in = new BufferedReader(isr);
        String line = null;
        logger.info("start clustering url first!!");
        while ((line = in.readLine()) != null) {
            URL url = new URL(line);
            String fileName = url.getHost().replace(hostName, "").replace(".", "");
            writeUrlToFile(fileName, line, svaePath);
        }
        logger.info("end clustering url!");
    }

    public static void writeUrlToFile(String fileName, String line, String savePath) {
        try {
            String path = savePath + fileName;
            File file = new File(path);
            if (!file.exists())
                file.createNewFile();
            FileOutputStream out = new FileOutputStream(file, true); //如果追加方式用true
            StringBuffer sb = new StringBuffer();
            sb.append(line + "\n");
            out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
            out.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            readUrlFromFile("./url/part-r-00000", "qq.com", "./urls/");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
