package org.ypf.generic.orm.entityoper.mappergenerater;

import java.io.RandomAccessFile;

/**
 * @date: 2022/6/21 11:48
 */
public class Utils {
    //首字母大写
    public static String fisrtToUpper(String columnName) {
        StringBuilder fistLetter = new StringBuilder(columnName.substring(0, 1).toUpperCase());
        fistLetter.append(columnName.substring(1));

        return fistLetter.toString();
    }

    public static String columnName2FiledName(String columnName) {
        return dbName2FiledName(columnName, true);
    }

    public static String tableName2EntityName(String columnName) {
        return dbName2FiledName(columnName, false);
    }

    public static String fisrstUpper(String columnName) {

        return dbName2FiledName(columnName, false);
    }
    // get 方法

    /**
     * @param filedName
     * @param type
     * @return
     */
    public static String genGetMethod(String filedName, String type) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("public ");
        stringBuilder.append(type);
        stringBuilder.append(" ");
        stringBuilder.append("get");
        stringBuilder.append(fisrtToUpper(filedName));
        stringBuilder.append("(");
        stringBuilder.append(")");
        stringBuilder.append(" ");
        stringBuilder.append("{");
        stringBuilder.append("\n");
        stringBuilder.append("return ");
        stringBuilder.append(filedName);
        stringBuilder.append(";");
        stringBuilder.append("\n");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public static String genSetMethod(String filedName, String type) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("public void");
        stringBuilder.append(" ");
        stringBuilder.append("set");
        stringBuilder.append(fisrtToUpper(filedName));
        stringBuilder.append("(");
        stringBuilder.append(type);
        stringBuilder.append(" ");
        stringBuilder.append(filedName);
        stringBuilder.append(")");
        stringBuilder.append(" ");
        stringBuilder.append("{");
        stringBuilder.append("\n");
        stringBuilder.append("this.");
        stringBuilder.append(filedName);
        stringBuilder.append(" ");
        stringBuilder.append("=");
        stringBuilder.append(" ");
        stringBuilder.append(filedName);
        stringBuilder.append(";");
        stringBuilder.append("\n");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public static String dbName2FiledName(String dbName, boolean fisrt) {
        String[] sp = dbName.toLowerCase().split("_");
        StringBuilder letters = new StringBuilder();
        if (fisrt) {
            for (int i = 0; i < sp.length; i++) {

                if (i == 0) {
                    letters.append(sp[i]);
                } else {
                    letters.append(fisrtToUpper(sp[i]));
                }

            }

        } else {
            for (int i = 0; i < sp.length; i++) {
                letters.append(fisrtToUpper(sp[i]));
            }

        }

        return letters.toString();
    }

    //生成文件
    public static void genAndWrite2File(String file, String content) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");) {
            randomAccessFile.writeBytes(content);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.out.println(fisrtToUpper("hello"));
        System.out.println(columnName2FiledName("HELLO_USER_NP"));
        System.out.println(tableName2EntityName("HELLO_USER_NP"));
        genAndWrite2File("d:\\f\\jas.js", "heee");
    }
}
