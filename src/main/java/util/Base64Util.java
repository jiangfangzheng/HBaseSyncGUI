package util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Created by YY on 2017-12-28.
 */
public class Base64Util {
    // 编码
    public static String encodeByBase64(String input) {
        String asB64 = "";
        try {
            asB64 = Base64.getEncoder().encodeToString(input.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return asB64;
    }

    // 解码
    public static String decodeByBase64(String input) {
        String str = "";
        try {
            str = new String(Base64.getDecoder().decode(input), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
}

