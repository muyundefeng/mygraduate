package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by lisheng on 17-5-17.
 */
public class PropertiesUtils {
    private static Properties pro = null;

    static {
        pro = new Properties();
        InputStream in = PropertiesUtils.class.getResourceAsStream("/baseInfo.properties");
        try {
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getAnalyseInputpath() {
        return pro.get("analyselog.inputpath").toString();
    }

    public static String getAnalyseOutputpath() {
        return pro.get("analyselog.outputpath").toString();
    }

    public static String getGeneralClusterInputpath() {
        return pro.get("cluster.general.inputpath").toString();
    }

    public static String getGeneralClusterOuputpath() {
        return pro.get("cluster.general.outputpath").toString();
    }

    public static String getFineClusterInputpath() {
        return getGeneralClusterOuputpath();
    }

    public static String getFineClusterOuputpath() {
        return pro.get("cluster.fine.outputpath").toString();
    }

    public static String getExtractorInputpath() {
        return pro.get("extractor.url.path").toString();
    }

    public static String getExtractorOuputpath() {
        return pro.get("extractor.html.outputpath").toString();
    }

    public static String getCommonProxy() {
        return pro.get("common.download.proxy").toString();
    }

    public static String getProxy() {
        return getCommonProxy().split(":")[1];
    }

    public static int getPort() {
        return Integer.parseInt(getCommonProxy().split(":")[1]);
    }
}
