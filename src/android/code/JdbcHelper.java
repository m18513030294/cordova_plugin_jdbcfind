package com.lzsf.jdbc.jdbcfind.utils;

import android.util.Log;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by zyo_b on 2017/6/12.
 */

public class JdbcHelper{

    public static JdbcHelper instenince;

    private JdbcUtils utils;

    public static JdbcHelper getInstenince() {
        synchronized (JdbcHelper.class) {
            if (instenince == null)
                instenince = new JdbcHelper();
        }
        return instenince;
    }

    private JdbcHelper() {
        if (utils==null) {
            utils = new JdbcUtils();
        }
    }

    /**
     * 执行一:新建表
     */
    public void createTable(String sql) {
//        String sql0 ="create table newuser(id int primary key auto_increment, username varchar(64), password varchar(64))";
        String sql0 ="select * from CONMAT_MST;";
        try {
            if (utils!=null) {
                utils.update(sql, null);
            }
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    public void addMessage(String sql){
        addMessage(null,null,null,sql);
    }



    /**
     * 执行二：增加一条记录
     */
    public void addMessage(String str,String table,String value,String sql){
//        String str = "(";
//        String value = "(";
//        System.out.println("通过Map.entrySet遍历key和value");
//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            str +=entry.getKey()+",";
//            if (entry.getValue() instanceof String) {
//                value += "\'" + entry.getValue() + "\',";
//            }else{
//                value += entry.getValue()+",";
//            }
//        }
//        str = str.substring(0,str.length()-1)+")";
//        value = value.substring(0,value.length()-1)+")";
//        for (int i=0;i<key.clone().length;i++){
//            str +=key.clone()[i];
//            value += "\'"+map.get(key.clone()[i])+"\'";
//            if (i==key.length-1){
//                break;
//            }
//            str+=",";
//            value +=",";
//        }
        String sql1 = "INSERT INTO "+table+str+" VALUES "+value;
        Log.d("sql",sql1);
        try{
            if (sql!=null) {
                utils.update(sql, null);
            }else{
                utils.update(sql1, null);
            }
        }catch (SQLException e){
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    public void updateMessage(String sql){
        updateMessage(null,null,null,sql);
    }

    /**
     * 修改一条记录
     *
     * @param map
     * @param sql sql语句
     */
    public void updateMessage(String map,String table,String value ,String sql){
//        String str = "";
//        String value1 = "";
//        System.out.println("通过Map.entrySet遍历key和value");
//        for (Map.Entry<String, Object> entry : map.entrySet()){
//            str +=entry.getKey()+"="+"\'"+entry.getValue()+"\',";
//        }
//        for (Map.Entry<String, Object> entry : value.entrySet()){
//            value1 +=entry.getKey()+"="+"\'"+entry.getValue()+"\',";
//        }
//        str = str.substring(0,str.length()-1);
//        value1 = value1.substring(0,value1.length()-1);
        String sql2 = "UPDATE "+table+" SET "+ map +" WHERE "+value;
        try{
            if (sql!=null) {
                utils.update(sql, null);
            }else{
                utils.update(sql2, null);
            }
        }catch (SQLException e){
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    private void initService(Map<String, Object> map, String sql){
        try{
            utils.update(sql, null);
        }catch (SQLException e){
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    /**
     *  执行四：删除一条记录
     * @param table 表名
     * @param key 所查的数据
     */
    public void deleteData(String table,String key,String tab) {
         String sql2 = "delete from "+table +" where "+ tab+ " = "+"\'"+key+"\'";
        Log.d("sql",sql2);
         try {
         utils.update(sql2, null);
         } catch (SQLException e) {
         // TODO 自动生成的 catch 块
         e.printStackTrace();
         }
    }

    public String selectData(String table){
        return selectData("*",table);
    }


    /**
     *  执行五：查询所有记录
     *      不完善
     * @param key
     * @param table
     */
    public String selectData(String key,String table){
        String sql3 = "select " + key + " from " + table;
        String str = null;
        try{
            str = utils.query(sql3, null,table);
        }catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return str;
    }

    public void otherMessage(String sql){
        try {
            utils.update(sql, null);
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

}