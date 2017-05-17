package analyse;

import org.apache.hadoop.util.ToolRunner;
import utils.PropertiesUtils;

/**
 * Created by lisheng on 17-5-17.
 */
public class Trigger {
    public static void start() {
        String str[] = {PropertiesUtils.getAnalyseInputpath(), PropertiesUtils.getAnalyseOutputpath()};
        try {
            ToolRunner.run(new AnalyseLogDriver(), str);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.exit(exitcode);
    }

    public static void main(String[] args) {
        start();
    }
}
