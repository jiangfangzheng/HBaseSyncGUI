package util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xy
 * 获取已上传的文件属性，如文件名、大小、可读性等。
 */
public class FileUtil {

    public static void main(String[] args) {
    }

    // 保存 String 到 文件
    public static boolean save(String fileName, String text) {
        if (text == null) {
            text = "";
        }
        try {
            File f = new File(fileName);
            FileOutputStream fop = new FileOutputStream(f, false);
            OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
            writer.append(text);
            writer.close();
            fop.close();
        } catch (IOException e) {
            System.out.println("save(String fileName, String text)异常：" + e.toString());
            return false;
        }
        return true;
    }

    // 保存 String 到 文件 【追加、\n换行】
    public static boolean saveAppend(String fileName, String text) {
        if (text == null) {
            text = "";
        }
        try {
            File f = new File(fileName);
            FileOutputStream fop = new FileOutputStream(f, true);
            OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
            writer.append(text).append("\n");
            writer.close();
            fop.close();
        } catch (IOException e) {
            System.out.println("saveAppend(String fileName, String text)异常：" + e.toString());
            return false;
        }
        return true;
    }

    // 保存 List<String> 到 文件
    public static boolean save(String fileName, List<String> textList) {
        try {
            File f = new File(fileName);
            FileOutputStream fop = new FileOutputStream(f, false);
            OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
            for (int i = 0; i < textList.size(); i++) {
                writer.append(textList.get(i));
                if (i != textList.size() - 1) {
                    writer.append("\n");
                }
            }
            writer.close();
            fop.close();
        } catch (IOException e) {
            System.out.println("save(String fileName, List<String> textList)异常：" + e.toString());
            return false;
        }
        return true;
    }

    // 保存 Map<String,String> 到 文件
    public static boolean save(String fileName, Map<String, String> map) {
        try {
            File f = new File(fileName);
            FileOutputStream fop = new FileOutputStream(f, false);
            OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
            StringBuilder stringBuilder = new StringBuilder();
            for (String akey : map.keySet()) {
                stringBuilder.append(akey).append(",").append(map.get(akey)).append("\n");
                writer.append(stringBuilder);
                stringBuilder.delete(0, stringBuilder.length());
            }
            writer.close();
            fop.close();
        } catch (IOException e) {
            System.out.println("save(String fileName, Map<String,String> map)异常：" + e.toString());
            return false;
        }
        return true;
    }

    // 保存 String[][] 到 文件
    public static boolean save(String fileName, String[][] mat) {
        if (mat == null) {
            return false;
        }
        try {
            File f = new File(fileName);
            FileOutputStream fop = new FileOutputStream(f, false);
            OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
            // StringBuilder单行缓冲, 速度内存均衡
            for (int i = 0; i < mat.length; i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < mat[0].length; j++) {
                    line.append(mat[i][j]);
                    if (j != mat[0].length - 1) {
                        line.append(",");
                    } else {
                        line.append("\n");
                    }
                }
                writer.append(line);
            }
            writer.close();
            fop.close();
        } catch (IOException e) {
            System.out.println("save(String fileName, String[][] mat)异常：" + e.toString());
            return false;
        }
        return true;
    }

    // 保存 title + String[][] 到 csv文件
    public static boolean saveCsv(String fileName, String title, String[][] mat) {
        if (mat == null) {
            return false;
        }
        try {
            File f = new File(fileName);
            FileOutputStream fop = new FileOutputStream(f, false);
            OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
            writer.append(title);
            for (int i = 0; i < mat.length; i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < mat[0].length; j++) {
                    line.append(mat[i][j]);
                    if (j != mat[0].length - 1) {
                        line.append(",");
                    } else {
                        line.append("\n");
                    }
                }
                writer.append(line);
            }
            writer.close();
            fop.close();
        } catch (IOException e) {
            System.out.println("saveCsv(String fileName, String title, String[][] mat)异常：" + e.toString());
            return false;
        }
        return true;
    }

    // 保存 double[][] 到 csv文件
    public static boolean save(String fileName, double[][] mat) {
        if (mat == null) {
            return false;
        }
        String[][] mats = new String[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                mats[i][j] = String.valueOf(mat[i][j]);
            }
        }
        return save(fileName, mats);
    }

    // 保存 Map 到 logs(D:\logs\和/logs/)  msg:附带的信息 【追加、文件后缀默认log、保存存时间】
    public static void saveMapToLogs(String fileName, Map<String, String> inputMap, String msg) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");
        String dataStr = dateFormat.format(new Date());
        String filePath;
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            filePath = "D:\\logs\\";
        } else {
            filePath = "/logs/";
        }
        File f = new File(filePath + fileName + dataStr.substring(0, 10) + ".log");
        // 追加写入
        FileOutputStream fop = new FileOutputStream(f, true);
        OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
        // 写入到缓冲区
        StringBuilder mapMsg = new StringBuilder();
        mapMsg.append("MapSize: ").append(inputMap.size()).append("\t").append(dataStr).append("\t").append(msg).append("\n");
        writer.append(mapMsg);
        mapMsg.delete(0, mapMsg.length());
        TreeMap<String, String> inputTreeMap = new TreeMap<>();
        inputTreeMap.putAll(inputMap);
        for (String akey : inputTreeMap.keySet()) {
            mapMsg.append(akey).append("\t").append(inputTreeMap.get(akey)).append("\n");
            writer.append(mapMsg);
            mapMsg.delete(0, mapMsg.length());
        }
        writer.append("\n");
        writer.close();
        fop.close();
    }

    // 保存 String 到 logs(D:\logs\和/logs/)  msg:附带的信息 【追加、文件后缀默认log、保存存时间】
    public static void saveStringToLogs(String fileName, String inputStr, String msg) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");
        String dataStr = dateFormat.format(new Date());
        String filePath;
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            filePath = "D:\\logs\\";
        } else {
            filePath = "/logs/";
        }
        File f = new File(filePath + fileName + dataStr.substring(0, 10) + ".log");
        // 追加写入
        FileOutputStream fop = new FileOutputStream(f, true);
        OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
        StringBuilder mapMsg = new StringBuilder();
        mapMsg.append("StrSize: ").append(inputStr.length()).append("\t").append(dataStr).append("\t").append(msg).append("\n");
        writer.append(mapMsg);
        writer.append(inputStr);
        writer.append("\n\n");
        writer.close();
        fop.close();
    }

    // 读取 文件 到 String
    public static String readFile(String fileName) throws IOException {
        File f = new File(fileName);
        FileInputStream fip = new FileInputStream(f);
        InputStreamReader reader = new InputStreamReader(fip, "UTF-8");
        StringBuilder sb = new StringBuilder();
        while (reader.ready()) {
            // 转成char加到StringBuffer对象中
            sb.append((char) reader.read());
        }
        reader.close();
        fip.close();
        return sb.toString();
    }

    // 读取 文件 到 List<String>
    public static boolean readFile(String fileName, List<String> textList) {
        try {
            // 此时获取到的bre就是[整个文件]的缓存流
            BufferedReader bre = new BufferedReader(new FileReader(fileName));
            String str;
            // 判断最后一行不存在，为空结束循环
            while ((str = bre.readLine()) != null) {
                textList.add(str);
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    // 读取 csv文件 到 String[][] StringTokenizer、空数据跳过
    public static String[][] readCsv(String fileName) {
        List<String> textList = new ArrayList<>();
        readFile(fileName, textList);
        // StringTokenizer方法，切片性能极高
        if (textList.size() > 0) {
            String[] line = textList.get(0).split(",");
            int row = textList.size();
            int col = line.length;
            String[][] mat = new String[row][col];
            for (int i = 0; i < row; i++) {
                StringTokenizer st = new StringTokenizer(textList.get(i), ",");
                int j = 0;
                while (st.hasMoreTokens()) {
                    mat[i][j++] = st.nextToken();
                }
            }
            return mat;
        }
        return null;
    }

    // 读取 csv文件 到 String[][] split方法、保留空数据,且设为0
    public static String[][] readCsvSplit(String fileName) {
        List<String> textList = new ArrayList<>();
        readFile(fileName, textList);
        // StringTokenizer方法，切片性能极高
        if (textList.size() > 0) {
            String[] line = textList.get(0).split(",", -1);
            int row = textList.size();
            int col = line.length;
            String[][] mat = new String[row][col];
//            for (int i = 0; i < row; i++) {
//                for (int j = 0; j < col; j++) {
//                    mat[i][j] = "0";
//                }
//            }
            for (int i = 0; i < row; i++) {
                String[] oneLine = textList.get(i).split(",", -1);
                for (int j = 0; j < col; j++) {
                    if ("".equals(oneLine[j]) && j != col - 1) {
                        mat[i][j] = "0";
                    } else {
                        mat[i][j] = oneLine[j];
                    }
                }
            }
            return mat;
        }
        return null;
    }

    // 读取目录下的所有文件
    public static List<String> readFilesByPath(String pathName) {
        File file = new File(pathName);
        List<String> fileList = new ArrayList<>();
        if (file.exists()) {
            for (File temp : file.listFiles()) {
                if (temp.isFile()) {
                    fileList.add(temp.toString());
                }
            }
        }
        return fileList;
    }

    // 读取 mat文件 到 double[][]
    public static double[][] readMat(String fileName) {
        List<String> textList = new ArrayList<>();
        readFile(fileName, textList);
        // StringTokenizer方法，切片性能极高
        if (textList.size() > 0) {
            String[] line = textList.get(0).split(",");
            int row = textList.size();
            int col = line.length;
            double[][] mat = new double[row][col];
            for (int i = 0; i < row; i++) {
                StringTokenizer st = new StringTokenizer(textList.get(i), ",");
                int j = 0;
                while (st.hasMoreTokens()) {
                    String value = st.nextToken();
                    value = value.trim();
                    double valued = Double.valueOf(value);
                    mat[i][j++] = valued;
                }
            }
            return mat;
        }
        return null;
    }

    // 显示 String[][] - 逐行显示 Tab分割列数据
    public static void show(String[][] mat) {
        int row = mat.length;
        int col = 0;
        if (row > 0) {
            col = mat[0].length;
        }
        for (int i = 0; i < row; i++) {
            StringBuilder temp = new StringBuilder();
            for (int j = 0; j < col; j++) {
                temp.append(mat[i][j]).append("\t");
            }
            System.out.println(temp);
        }
        System.out.println("");
    }

    // 显示 double[][]  - 逐行显示 Tab分割列数据
    public static void show(double[][] mat) {
        int row = mat.length;
        int col = 0;
        if (row > 0) {
            col = mat[0].length;
        }
        for (int i = 0; i < row; i++) {
            StringBuilder temp = new StringBuilder();
            for (int j = 0; j < col; j++) {
                temp.append(mat[i][j]).append("\t");
            }
            System.out.println(temp);
        }
        System.out.println("");
    }

    // 显示 Map<String, String> - 逐行显示 Tab分割键值对
    public static void show(Map<String, String> map) {
        for (String akey : map.keySet()) {
            System.out.println(akey + "\t" + map.get(akey));
        }
        System.out.println("");
    }

    // 显示 List<String> - 逐行显示
    public static void show(List<String> list) {
        for (String akey : list) {
            System.out.println(akey);
        }
        System.out.println("");
    }

    // 显示 String[] - 逐行显示
    public static void show(String[] strArray) {
        for (String e : strArray) {
            System.out.println(e);
        }
        System.out.println("");
    }


//    public static void main(String[] args) throws IOException {
//        long start = System.currentTimeMillis();
//
//        save("1.txt", "123abc456xyz");
//
//        List<String> textList = new LinkedList<String>();
//        textList.add("123");
//        textList.add("abc");
//        textList.add("456");
//        textList.add("xyz");
//        save("2.txt", textList);
//
//        String[][] mats = {{"1", "2", "3"}, {"4", "5", "6"}, {"7", "8", "9"}, {"10", "11", "12"}};
//        double[][] matd = {{1, 2, 3}, {4.4444, 5.555, 6.66}, {7.7, 8, 9}};
//        save("3.txt", mats);
//
//        save("4.txt", matd);
//
//        String a = readFile("3.txt");
//        System.out.println(a);
//
//        List<String> textList2 = new LinkedList<String>();
//        readFile("3.txt",textList2);
//        System.out.println(textList2);
//
//        long start4 = System.currentTimeMillis();
//        String[][] matsTr = readCsv("D:\\mat143708x219.csv");
////        show(matsTr);
//        System.out.println("readCsvstr　" + (System.currentTimeMillis() - start4)/1000.0 + "s");
//
//        long start3 = System.currentTimeMillis();
//        double[][] matdd = readMat("D:\\mat143708x219.csv");
////        show(matdd);
//        System.out.println("readCsvdou　" + (System.currentTimeMillis() - start3)/1000.0 + "s");
//
//        long start2 = System.currentTimeMillis();
//        double[][] test143708x219 = new double[143708][219];
//        save("mat143708x219.csv",test143708x219);
//        System.out.println("save mat143708x219 " + (System.currentTimeMillis() - start2)/1000.0 + "s");
//
//
//        System.out.println("ok!　" + (System.currentTimeMillis() - start) + "ms");
//        Map<String, String> map = new TreeMap<>();
//        map.put("123", "456");
//        map.put("789", "102");
//        save("D:\\jjjjj.txt", map);
//    }

}

