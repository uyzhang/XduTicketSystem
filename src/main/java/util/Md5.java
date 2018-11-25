package util;

import org.junit.Test;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Md5 {
    /**
     * 利用MD5进行加密
     *  若字符串为空，会抛出异常
     */
    public static String EncoderByMd5(String str) throws Exception {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        return base64en.encode(md5.digest(str.getBytes(StandardCharsets.UTF_8)));
    }
}
