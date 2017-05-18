package utils;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {
    public static  String generateHash(String text) {
        if(text != null && !text.equals("")) {
            MessageDigest digest = null;

            try {
                digest = MessageDigest.getInstance("SHA-256");
                byte[] e = digest.digest(text.getBytes("UTF-8"));
                return Hex.encodeHexString(e);
            } catch (NoSuchAlgorithmException var3) {
            } catch (UnsupportedEncodingException var4) {
            }

            return null;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
//        String url = "http://mm.10086.cn/\n" +
//                "http://shouji.baidu.com/\n" +
//                "http://www.mumayi.com/android/game\n" +
//                "http://sj.qq.com\n" +
//                "http://app.mi.com/\n" +
//                "http://www.25pp.com/android/\n" +
//                "http://www.anzhi.com/\n" ;
//        String urls[] = url.split("\\n");
//        for(String str:urls){
//            System.out.println(str+","+HashUtils.generateHash(str));
//        }
        System.out.println(HashUtils.generateHash("http://www.play.cn/game/searchgame?type_code=12&class_code=-1&order_type=0"));
    }
}