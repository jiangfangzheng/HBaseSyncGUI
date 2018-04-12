package constant;


import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import util.Base64Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017-12-28.
 */
public class HbaseRow {

    private String rowKey = null;
    private List<HbaseCell> hbaseCellList = null;

    public String getRowKey() {
        return rowKey;
    }

    public List<HbaseCell> getHbaseCellList() {
        return hbaseCellList;
    }

    public HbaseRow(String rowKey, HbaseCell... hbaseCells) {
        this.rowKey = rowKey;
        hbaseCellList = new ArrayList<>();
        for (HbaseCell hbaseCell : hbaseCells) {
            hbaseCellList.add(hbaseCell);
        }
    }

    public HbaseRow(String rowKey, List<HbaseCell> hbaseCells) {
        this.rowKey = rowKey;
        this.hbaseCellList = hbaseCells;
    }

    public void addHbaseCell(HbaseCell hbaseCell) {
        hbaseCellList.add(hbaseCell);
    }

    public JSONObject getJsonOfRow() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (HbaseCell hbaseCell : hbaseCellList) {
            JSONObject object = getHCellJson(hbaseCell.columnFamily, hbaseCell.column, hbaseCell.value);
            jsonArray.put(object);
        }
        try {
            jsonObject.put("key", Base64Util.encodeByBase64(rowKey));
            jsonObject.put("Cell", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private static JSONObject getHCellJson(String columnFamily, String column, String value) {
        JSONObject object = new JSONObject();
        String asB64 = Base64Util.encodeByBase64(columnFamily.concat(":").concat(column));
        String asB64Value = Base64Util.encodeByBase64(value);
        try {
            object.put("column", asB64);
            object.put("$", asB64Value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}

