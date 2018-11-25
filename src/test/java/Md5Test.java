import org.junit.Test;
import util.Md5;

import static util.Md5.EncoderByMd5;

/**
 * 用于进行 Md5.java的边界值测试
 */
public class Md5Test {
    @Test
    public void testMd5() {
        String s = "Hello World";
        String res = null;
        try {
            res = EncoderByMd5(s);
            System.out.println(res);
            s = "";
            res = EncoderByMd5(s);
            System.out.println(res);
            s = "12345678901234567890";
            res = EncoderByMd5(s);
            System.out.println(res);
            s = "123456789012345678900";
            res = EncoderByMd5(s);
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(expected = NullPointerException.class)
    public void TestMd5Exception() throws Exception {
        String s = null;
        try {
            String res = Md5.EncoderByMd5(s);
            System.out.println(s);
        } catch (Exception e) {
            throw e;
        }
    }
}
