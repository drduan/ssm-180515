package utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public List<HashMap<String, Object>> convertResultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        while (rs.next()) {
            HashMap<String, Object> row = new HashMap<String, Object>(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(row);
        }

        return list;
    }

    //    private Map<String, List<Object>> resultSetToArrayList(ResultSet rs) throws SQLException {
    //        ResultSetMetaData md = rs.getMetaData();
    //        int columns = md.getColumnCount();
    //        Map<String, List<Object>> map = new HashMap<>(columns);
    //        for (int i = 1; i <= columns; ++i) {
    //            map.put(md.getColumnName(i), new ArrayList<>());
    //        }
    //        while (rs.next()) {
    //            for (int i = 1; i <= columns; ++i) {
    //                map.get(md.getColumnName(i)).add(rs.getObject(i));
    //            }
    //        }
    //
    //        return map;
    //    }
}
