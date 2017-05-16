package extractor.input;


import extractor.trinity.Text;
import extractor.util.ProcessHtmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author lisheng
 *         处理输入的html文档,并将html文档转化为字符串
 */
public class InputDocument {

    private static final String FILE_DIR = "/home/lisheng/work/ExperData/htmls/people/";

    private static Logger logger = LoggerFactory.getLogger(InputDocument.class);

    /**
     * 读取指定目录之下的文件列表
     *
     * @throws IOException
     */
    @SuppressWarnings("Duplicates")
    public static List<Text> getDefaultReadHtml() throws IOException {
        List<Text> texts = new ArrayList<Text>();
        File file = new File(FILE_DIR);
        if (!file.exists())
            logger.error("this file is not exist!");
        String htmlName[] = file.list();//得到文件列表
        for (String str : htmlName) {
            File file2 = new File(FILE_DIR + str);
            String sourceText = "";
            if (file2.isFile()) {
                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file2));
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String Line = "";
                while ((Line = bufferedReader.readLine()) != null) {
                    sourceText += Line + "\n";
                }
                String afterProcessHtml = ProcessHtmlUtils.rmSomeScript(sourceText);
                Text text = new Text(afterProcessHtml);
                texts.add(text);
            }
        }
        return texts;
    }

    /**
     * 自定义输入html文件
     *
     * @param filesNames
     * @throws IOException
     */
    public static List<Text> getDefiniteReadHtml(String... filesNames) throws IOException {
        List<Text> texts = new ArrayList<Text>();
        for (String str : filesNames) {
            File file2 = new File(str);
            String sourceText = "";
            if (file2.isFile()) {
                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file2));
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String Line = "";
                while ((Line = bufferedReader.readLine()) != null) {
                    sourceText += Line;
                }
                Text text = new Text(sourceText);
                texts.add(text);
            }
        }
        return texts;
    }
}
