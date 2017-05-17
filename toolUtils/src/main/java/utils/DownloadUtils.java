package utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by lisheng on 17-5-7.
 */
@SuppressWarnings("Duplicates")
public class DownloadUtils {

    public static HttpClient httpClient = HttpClientManager.getHttpClient();

    public static Logger logger = LoggerFactory.getLogger(DownloadUtils.class);

    public static String proxy = PropertiesUtils.getProxy();

    public static int port = PropertiesUtils.getPort();

    public static String download(String str) {
        logger.info("download url:" + str + "======");
        if (str == null || str.length() == 0)
            return null;
        HttpGet httpGet = new HttpGet(str);

//        HttpHost host = new HttpHost(proxy, port);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(3000)
                .setSocketTimeout(10000)
//                .setProxy(host)
                .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                .build();
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.79 Safari/537.1");
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.setConfig(requestConfig);
        HttpResponse response = null;
        String content = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                byte[] contentBytes = IOUtils.toByteArray(response.getEntity()
                        .getContent());
                String htmlCharset = getHtmlCharset(response, contentBytes);
                content = new String(contentBytes, htmlCharset);
                //content = IOUtils.toString(response.getEntity().getContent(), htmlCharset);
            } else {
                return null;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (response != null)
                    EntityUtils.consume(response.getEntity());
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return content;
    }

    protected static String getHtmlCharset(HttpResponse httpResponse,
                                    byte[] contentBytes) throws IOException {
        String charset;
        String value = httpResponse.getEntity().getContentType().getValue();
        charset = UrlUtils.getCharset(value);
        if (StringUtils.isNotBlank(charset)) {
            return charset;
        }
        Charset defaultCharset = Charset.defaultCharset();
        String content = new String(contentBytes, defaultCharset.name());
        if (StringUtils.isNotEmpty(content)) {
            Document document = Jsoup.parse(content);
            Elements links = document.select("meta");
            for (Element link : links) {
                String metaContent = link.attr("content");
                String metaCharset = link.attr("charset");
                if (metaContent.indexOf("charset") != -1) {
                    metaContent = metaContent.substring(
                            metaContent.indexOf("charset"),
                            metaContent.length());
                    charset = metaContent.split("=")[1];
                    break;
                }
                else if (StringUtils.isNotEmpty(metaCharset)) {
                    charset = metaCharset;
                    break;
                }
            }
        }
        return charset;
    }


    public static void main(String[] args) {
        System.out.println(download("http://news.xinhuanet.com/money/2017-05/16/c_1120981137.htm"));
    }
}
