package com.qikenet.fastsql.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlTest {

    public static void main(String[] args) {
        String sql = "select * from user where user_id=#{  userId    } and name='${name}'";

        Pattern pattern = Pattern.compile("[\\$|#]{1}\\{(.*?)\\}");
        Matcher m = pattern.matcher(sql);

        while (m.find()) {
            String sqlName = m.group(0);
            String fieldName = m.group(1).trim();

            System.out.println(sqlName + " - " + fieldName);


        }
    }
}
