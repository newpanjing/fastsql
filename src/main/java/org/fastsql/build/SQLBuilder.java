package org.fastsql.build;

import org.fastsql.annotation.*;
import org.fastsql.core.Entry;
import org.fastsql.core.SQLParameter;
import org.fastsql.entity.SQLExecuteType;
import org.fastsql.exception.SQLBuildRuntimeException;
import org.fastsql.utils.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLBuilder {

    public static SQLParameter builder(Class clazz, Method method, Object[] args) {
        //获取

        //如果是自动sql，按照自动sql方式处理
        Automatic automatic = method.getAnnotation(Automatic.class);
        if (automatic != null) {

            return null;
        }


        return handerAnnotation(clazz, method, args);

    }

    /**
     * 原始sql 通过参数 构建查询sql
     *
     * @param sql
     * @param kv
     * @return
     */
    public static SQLBase handlerSQL(String sql, Map<String, Object> kv) {
        //参数替换

        Pattern pattern = Pattern.compile("[\\$|#]{1}\\{(.*?)\\}");
        Matcher m = pattern.matcher(sql);

        List<Entry> parameters = new ArrayList<>();

        while (m.find()) {
            String sqlName = m.group(0);
            String fieldName = m.group(1).trim();
            if (!kv.keySet().contains(fieldName)) {
                throw new SQLBuildRuntimeException("not found field:\"" + fieldName + "\"");
            }
            int ch = sqlName.charAt(0);

            if (ch == '#') {
                sql = sql.replaceAll(StringUtils.escapeExprSpecialWord(sqlName), "?");
                //添加参数到查询
                parameters.add(new Entry(fieldName, kv.get(fieldName)));
            } else if (ch == '$') {
                Object val = kv.get(fieldName);

                sql = sql.replaceAll(StringUtils.escapeExprSpecialWord(sqlName), String.valueOf(val));
            }
//            System.out.println(sqlName + " - " + fieldName);
        }

        SQLBase sqlBase = new SQLBase();
        sqlBase.setParameters(parameters);
        sqlBase.setSql(sql);
        return sqlBase;
    }

    /**
     * 通过注解获取参数
     *
     * @param clazz
     * @param method
     * @param args
     * @return
     */
    public static SQLParameter handerAnnotation(Class clazz, Method method, Object[] args) {
        Annotation[][] paramterAnnotations = method.getParameterAnnotations();

        String[] fields = new String[paramterAnnotations.length];

        for (int i = 0; i < paramterAnnotations.length; i++) {
            if (paramterAnnotations[i].length == 0) {
                throw new SQLBuildRuntimeException((String.format("方法%s 第%s个参数，未加@Param注解", (method.getDeclaringClass() + "." + method.getName() + "()"), (i + 1))));
            }
            for (int k = 0; k < paramterAnnotations[i].length; k++) {

                Annotation ano = paramterAnnotations[i][k];
                if (ano instanceof Param) {
                    fields[i] = ((Param) ano).value();
                    break;
                }
            }

        }

        //凑参数
        Map<String, Object> kv = new HashMap<>();
        for (int i = 0; i < fields.length; i++) {
            //方法入参参数不允许重复
            String key = fields[i];
            if (kv.containsKey(key)) {
                throw new SQLBuildRuntimeException("field '" + key + "' is repeated");
            }
            kv.put(key, args[i]);
        }

        Select select = method.getAnnotation(Select.class);
        Update update;
        Delete delete;
        Insert insert;

        SQLExecuteType type = null;
        String sql = null;
        if (select != null) {
            sql = select.value();
            type = SQLExecuteType.SELECT;
        } else if ((update = method.getAnnotation(Update.class)) != null) {
            sql = update.value();
            type = SQLExecuteType.UPDATE;
        } else if ((delete = method.getAnnotation(Delete.class)) != null) {
            sql = delete.value();
            type = SQLExecuteType.DELETE;
        } else if ((insert = method.getAnnotation(Insert.class)) != null) {
            sql = insert.value();
            type = SQLExecuteType.INSERT;
        } else {
            throw new SQLBuildRuntimeException("not found CRUD action");
        }

//        System.out.println(sql);

        //参数替换
        SQLBase sqlBase = handlerSQL(sql, kv);

        //构建SQLExecute对象
        SQLParameter execute = new SQLParameter();
        execute.setSql(sqlBase.getSql());
        execute.setParameters(sqlBase.getParameters());
        execute.setResultType(method.getReturnType());


        //获取集合泛型

        Type returnType = method.getGenericReturnType();
        if (returnType != null && returnType instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) returnType;

            Object object=pt.getActualTypeArguments()[0];

            //如果class = sun.reflect.generics.reflectiveObjects.WildcardTypeImpl
            //泛型类型为？，默认为null，指定默认类型

            if (object !=sun.reflect.generics.reflectiveObjects.WildcardTypeImpl.class) {
                Class genericClazz = (Class)object ;
                execute.setActualType(genericClazz);
            }
        }

        execute.setType(type);
        return execute;
    }


}
