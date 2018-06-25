package com.qikenet.fastsql.utils;

public class StringUtils {

    public static String escapeExprSpecialWord(String keyword) {
        if (keyword!=null) {
            String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }

    public static String getFirstUpperCase(String string) {

        if (string == null||string.trim().equals("")) {
            return string;
        }

        if (string.length() == 1) {
            return string.toUpperCase();
        }

        return string.substring(0, 1).toUpperCase() + string.substring(1);

    }

    public static void main(String[] args) {
        System.out.println("set"+getFirstUpperCase("name"));
    }
}
