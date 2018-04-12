package constant;

/**
 * Created by YY on 2017-12-28.
 */
public class HbaseCell {

    public String columnFamily = null;
    public String column = null;
    public String value = null;

    public HbaseCell(String columnFamily, String column, String value) {
        this.columnFamily = columnFamily;
        this.column = column;
        this.value = value;
    }
}

