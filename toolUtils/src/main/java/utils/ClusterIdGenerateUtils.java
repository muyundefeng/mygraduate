package utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by lisheng on 17-5-18.
 */
public class ClusterIdGenerateUtils {

    private static Set<String> set = new HashSet<String>();

    private static int bound = 10000;

    @SuppressWarnings("Duplicates")
    public static String getClusterId() {
        String fileName = null;
        Random random = new Random();
        int ad = random.nextInt(bound);
        while (true) {
            if (set.add(ad + "")) {
                fileName = ad + "";
                break;
            }
        }
        return fileName;
    }
}
