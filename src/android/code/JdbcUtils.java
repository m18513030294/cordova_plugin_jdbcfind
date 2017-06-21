package com.lzsf.jdbc.jdbcfind.utils;

import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcUtils {
    // 数据库的登陆账号密码  
    private final String USERNAME = "root";
    private final String PASSWORD = "710123";
    // JDBC驱动程序  
    private final String DRIVER = "oracle.jdbc.driver";
    // 数据库地址  "
    private final String URL = "jdbc:mysql://localhost:3306/sqldata";

    // 三个重要类的对象  
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet resultSet;



    public JdbcUtils() {
        try {
            // 步骤1：加载驱动程序
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // 步骤2：建立连接，这里的处理是当实例化这个工具类对象时就完成这两个步骤
            connection = (Connection) DriverManager.getConnection(
                    "jdbc:oracle:thin:@121.22.80.134:1081/orcl","smztest","123");
        } catch (ClassNotFoundException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    // 封装的update函数  
    public int update(String sql, List<Object> params) throws SQLException {
        int result = 0;
        Log.d("sql",sql);

        // 步骤3：创建一个Statement，添加相关参数
        if (connection==null){
            Log.e("log","error");
        }
        ps = connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++)
                // 注意数据库的下标都是从1开始的，第i个占位符填充params的第i个值  
                ps.setObject(i + 1, params.get(i));
        }
        // 步骤4：执行SQL语句，步骤5和6则留给客户端处理  
        result = ps.executeUpdate();
        return result;
    }

    // 封装的query函数，返回的是List套个Map，数据库是以键值对的形式存储的  
    public String query(String sql, List<Object> params,String table)
            throws SQLException {
        // 步骤3：创建一个Statement，添加相关参数  

        ps = connection.prepareStatement("select count(*) as rowCount from " + table);
        ResultSet resultSet = ps.executeQuery();
        resultSet.next();
        if (resultSet == null){
            return "{\"data\":\"null\"}";
        }
        int rowCount = resultSet.getInt("rowCount");
            ps = connection.prepareStatement(sql);
            if (params != null && !params.isEmpty()) {
                for (int i = 0; i < params.size(); i++)
                    ps.setObject(i + 1, params.get(i));
            }
            resultSet = ps.executeQuery();
            // 步骤5：处理执行结果
            // 获取此ResultSet对象的列的属性
            ResultSetMetaData metaData = resultSet.getMetaData();
            // 列的长度
            resultSet.getFetchSize();
            int col_len = metaData.getColumnCount();
            String str = null;
            if (rowCount==0){
                return "{\"data\":\"null\"}";
            }
            if (rowCount>1) {
                str = "{ \"data\":[";
            }else{
                str = "{ \"data\":";
            }
            Log.d("sql", rowCount + "");

            // 若有下一条记录
            while (resultSet.next()) {
                // 将该条记录以map形式存储
                for (int i = 0; i < col_len; i++) {
                    if (i == 0) {
                        str += "{";
                    }
                    // 根据列名获得键值并存放在map中
                    String col_name = metaData.getColumnName(i + 1);
                    Object col_value = resultSet.getObject(col_name);

                    if (i == col_len - 1) {
                        str += "\"" + col_name.toLowerCase() + "\"" + ":" + "\"" + col_value + "\"},";
                    } else {
                        str += "\"" + col_name.toLowerCase() + "\"" + ":" + "\"" + col_value + "\",";
                    }

                }
                // 将该记录添加到list中
            }
            str = str.substring(0, str.length() - 1);
        if (rowCount>1) {
            str += "]}";
        }else{
            str += "}";
        }
        return str;
    }

    // 封装步骤6关闭JDBC对象  
    public void release() {
        if (resultSet != null)
            try {
                resultSet.close();
            } catch (SQLException e) {
                // TODO 自动生成的 catch 块  
                e.printStackTrace();
            }
        if (ps != null)
            try {
                ps.close();
            } catch (SQLException e) {
                // TODO 自动生成的 catch 块  
                e.printStackTrace();
            }
        if (connection != null)
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO 自动生成的 catch 块  
                e.printStackTrace();
            }
    }
}  