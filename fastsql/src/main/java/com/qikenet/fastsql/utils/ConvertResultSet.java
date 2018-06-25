package com.qikenet.fastsql.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConvertResultSet {

    /**
     * 结果集转换到对象
     * @param rs
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> getResults(ResultSet rs) throws Exception {

        //用arraylist 防止乱序
        List<Map<String, Object>> results = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        while (rs.next()) {

            //用linkedmap 防止乱序
            Map<String, Object> linkMap = new LinkedHashMap<>();

            for (int i = 0; i < count; i++) {
                String colName = rsmd.getColumnLabel(i + 1);
                Object value = rs.getObject(colName);
                linkMap.put(colName, value);
            }

            results.add(linkMap);
        }
        return results;
    }
}
