package extractor.util;

import org.w3c.tidy.Tidy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lisheng on 17-5-9.
 */
public class HtmlCleanerUtils {
    public static void main(String[] args) throws IOException {
        Tidy tidy = new Tidy(); //使用Jtidy几乎只需要用的这一个类
        FileInputStream in = new FileInputStream("/home/lisheng/work/ExperData/d.html");
        FileOutputStream out = new FileOutputStream("/home/lisheng/work/ExperData/e.html");    //输出的文件
        tidy.parse(in, out);    //开始转换了~~~Jtidy把所有东西都封装好了，哈哈~~
        out.close();    //转换完成关闭输入输出流
        in.close();
    }
}
