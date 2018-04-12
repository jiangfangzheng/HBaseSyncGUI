package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 *
 * @author ouzhb
 */
public class StringUtil {

    private final static Pattern PATTERN = Pattern.compile("[+-]?[0-9]+[0-9]*(\\.[0-9]+)?");

    // 判断字符串是否为null、“ ”、“null”
    public static boolean isNull(String obj) {
        if (obj == null) {
            return true;
        } else if (obj.toString().trim().equals("")) {
            return true;
        } else if (obj.toString().trim().toLowerCase().equals("null")) {
            return true;
        }

        return false;
    }

    // 正则验证是否是数字
    public static boolean isNumber(String str) {
        Matcher match = PATTERN.matcher(str);
        return match.matches();
    }

    // 将一个长整数转换位字节数组(8个字节)，b[0]存储高位字符，大端
    public static byte[] longToBytes(long l) {
        byte[] b = new byte[8];
        b[0] = (byte) (l >>> 56);
        b[1] = (byte) (l >>> 48);
        b[2] = (byte) (l >>> 40);
        b[3] = (byte) (l >>> 32);
        b[4] = (byte) (l >>> 24);
        b[5] = (byte) (l >>> 16);
        b[6] = (byte) (l >>> 8);
        b[7] = (byte) (l);
        return b;
    }

    // 字符串中将 // /// 等 统一为/
    public static String stringSlashToOne(String input) {
        String out = input.replace("////", "/");
        out = out.replace("///", "/");
        out = out.replace("//", "/");
        out = out.replace("\\\\\\", "/");
        out = out.replace("\\\\", "/");
        out = out.replace("\\", "/");
        return out;
    }

    // 文件绝对路径中提取文件名
    public static String getFileNameByFullPath(String fileFullPath) {
        fileFullPath =  stringSlashToOne(fileFullPath);
        String out = fileFullPath;
        if (fileFullPath.contains("/")){
            String[] arr = fileFullPath.split("/");
            out = arr[arr.length - 1];
        }
        return out;
    }
}
