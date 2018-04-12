package constant;


import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import util.Base64Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017-12-29.
 */
public class HbaseRows {

    List<HbaseRow> hbaseRowList = null;

    public HbaseRows(HbaseRow... hbaseRows) {
        hbaseRowList = new ArrayList<>();
        for (HbaseRow hbaseRow : hbaseRows) {
            hbaseRowList.add(hbaseRow);
        }
    }

    public HbaseRows(List<HbaseRow> hbaseRows) {
        hbaseRowList = new ArrayList<>();
        for (HbaseRow hbaseRow : hbaseRows) {
            hbaseRowList.add(hbaseRow);
        }
    }

    public void addHbaseRow(HbaseRow hbaseRow) {
        hbaseRowList.add(hbaseRow);
    }

    public JSONObject getHbaseRowsJson() {
        JSONObject hbaseRowsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (HbaseRow hbaseRow : hbaseRowList) {
            jsonArray.put(hbaseRow.getJsonOfRow());
        }
        try {
            hbaseRowsJson.put("Row", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hbaseRowsJson;
    }

    public static List<HbaseRow> parseHBaseRowsJson(String jsonStr) {
        org.codehaus.jettison.json.JSONObject jsonObject = null;
        org.codehaus.jettison.json.JSONArray jsonArray = null;
        List<HbaseRow> hbaseRowList = new ArrayList<>();
        try {
            jsonObject = new org.codehaus.jettison.json.JSONObject(jsonStr);
            jsonArray = jsonObject.getJSONArray("Row");
            for (int i = 0; i != jsonArray.length(); ++i) {
                org.codehaus.jettison.json.JSONObject hbaseRowJson = (org.codehaus.jettison.json.JSONObject) jsonArray.get(i);
                HbaseRow hbaseRow = new HbaseRow(Base64Util.decodeByBase64(hbaseRowJson.getString("key")));
                org.codehaus.jettison.json.JSONArray array = hbaseRowJson.getJSONArray("Cell");
                for (int j = 0; j != array.length(); ++j) {
                    org.codehaus.jettison.json.JSONObject object = (org.codehaus.jettison.json.JSONObject) array.get(j);
                    String str = Base64Util.decodeByBase64(object.getString("column"));
                    String value = Base64Util.decodeByBase64(object.getString("$"));
                    int index = str.indexOf(':');
                    String columnFamily = str.substring(0, index);
                    String column = str.substring(index + 1);
                    HbaseCell hbaseCell = new HbaseCell(columnFamily, column, value);
                    hbaseRow.addHbaseCell(hbaseCell);
                }
                hbaseRowList.add(hbaseRow);
            }
        } catch (org.codehaus.jettison.json.JSONException e) {
            e.printStackTrace();
        }
        return hbaseRowList;
    }
}

