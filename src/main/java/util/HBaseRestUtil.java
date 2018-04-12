package util;

import constant.HbaseCell;
import constant.HbaseRow;
import constant.HbaseRows;
import javafx.util.Pair;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static util.FileUtil.readFile;
import static util.FileUtil.saveAppend;

/**
 * @author Sandeepin
 * 2018/4/11 0011
 */
public class HBaseRestUtil {

    private static String uriBase = "http://59.69.101.206:6077/";

    public static void main(String[] args) {
        System.out.println(getHTableArray());
        String fileName = "D:\\01CD1702.csv";
        System.out.println(adds(fileName));
    }

    // 输入文件名，导入数据到HBase
    public static boolean adds(String fileName) {
        List<String> textList = new ArrayList<>();
        boolean b = readFile(fileName, textList);
        if (!b) {
            return false;
        }
        Map<String, String> rowkeyAndValue = new TreeMap<>();
        for (String line : textList) {
            String key = line.substring(0, 16);
            String value = line.substring(17, line.length());
            rowkeyAndValue.put(key, value);
        }
        // 导入HBase 武重表 wuzhong 分批导入 50000条一批
        boolean outBool = false;
        Map<String, String> tempMap = new TreeMap<>();
        int batchNum = 1;
        System.out.println("adds:rowkeyAndValue.size() " + rowkeyAndValue.size());
        saveAppend("HBaseSync.log", "    导入数据条数：" + rowkeyAndValue.size());
        int cnt = 0;
        for (String akey : rowkeyAndValue.keySet()) {
            tempMap.put(akey, rowkeyAndValue.get(akey));
            cnt++;
            if (cnt % 50000 == 0 || cnt == rowkeyAndValue.size()) {
                outBool = adds("wuzhong", tempMap, "d", "k");
                System.out.println("adds:分批处理，第 " + batchNum + " 批，大小：" + tempMap.size());
                saveAppend("HBaseSync.log", "    分批处理，第 " + batchNum + " 批，大小：" + tempMap.size());
                tempMap.clear();
                batchNum++;
            }
        }
        return outBool;
    }

    // 插入rowkey、Value合集，默认一个family
    public static Boolean adds(String tableName, final Map<String, String> rowkeyAndValue, final String family, final String qualifier) {
        System.out.println("批量插入:" + tableName + " " + family + " " + qualifier);
        HbaseRows hbaseRows = new HbaseRows();
        for (String rowkey : rowkeyAndValue.keySet()) {
            hbaseRows.addHbaseRow(new HbaseRow(rowkey, new HbaseCell(family, qualifier, rowkeyAndValue.get(rowkey))));
        }
        return insertRows(tableName, hbaseRows.getHbaseRowsJson()).getKey();
    }

    /**
     * 插入多行
     *
     * @param tableName  输入表名
     * @param jsonObject 插入的值
     * @return 插入结果
     */
    public static Pair<Boolean, String> insertRows(String tableName, JSONObject jsonObject) {
        String strUrl = uriBase.concat(tableName).concat("/").concat("rows");
        Pair<Integer, String> pair = HttpRequestUtil.httpPutByJson(strUrl, jsonObject);
        if (pair.getKey() == 200 || pair.getKey() == 201) {
            return new Pair(true, pair.getValue());
        }
        return new Pair(false, pair.getValue());
    }

    /**
     * 获取所有表名
     *
     * @return 表名
     */
    public static JSONArray getHTableArray() {
        Pair<Integer, String> pair = HttpRequestUtil.httpGet(uriBase);
        JSONArray jsonArray = null;
        if (pair.getKey() == 200) {
            try {
                jsonArray = new JSONObject(pair.getValue()).getJSONArray("table");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }
}
