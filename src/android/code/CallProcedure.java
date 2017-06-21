package com.lzsf.jdbc.jdbcfind.utils;


import android.util.Log;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import oracle.jdbc.OracleTypes;

/**
 * @author PerfectPlans
 * @date 2012-7-7
 */
@SuppressWarnings("unused")
public class CallProcedure {
  public static Connection conn = null; // 数据库连接对象
  public static CallableStatement cs = null;// 存储过程执行对象
  public static ResultSet rs = null;// 结果集对象

  //静态处理块
  static {
    try {
      // 利用java反射技术获取对应驱动类
      Class.forName("oracle.jdbc.driver.OracleDriver");
      // 获取连接对象
      conn = DriverManager.getConnection(
        "jdbc:oracle:thin:@121.22.80.134:1081/orcl", "smztest", "123");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * @param conn
   * @return void
   * @throws Exception
   * @Discription 执行有参数，无返回值的存储过程
   */
    /* 对应的存储过程语句
    --有参数无返回值
    create or replace procedure updateName(byNo in number,useName in varchar2)
    as
    begin
           update emp e set e.ename = useName where e.empno = byNo;
    end;
    */
  public void callProcedureY(Connection conn) throws Exception {
    //指定调用的存储过程
    cs = conn.prepareCall("{call updateName(?,?)}");
    cs.setInt(1, 7499);//设置存储过程对应的输入参数
    cs.setString(2, "www");//对应下标从1 开始
    //执行存储过程调用
    cs.execute();
  }

  /**
   * @param conn
   * @return void
   * @throws Exception
   * @Discription 执行无参数，无返回值的存储过程
   */
    /*对应的存储过程语句
    --无参数
    create or replace procedure insertLine
    as
    begin
          insert into emp values(7333,'ALLEN','SAL',7698,to_date('2011/11/11','yyyy-MM-dd'),1600,300,30);
    end;
    */
  public void callProcedure(Connection conn) throws Exception {
    //指定调用的存储过程
    cs = conn.prepareCall("{call insertLine}");
    //执行存储过程的调用
    cs.execute();
  }

  /**
   * @param conn
   * @return void
   * @throws Exception
   * @Discription 执行有参数，有返回值的存储过程
   */
    /*对应的存储过程语句
     --有参数，有返回值
    create or replace procedure deleteLine(byNo in number,getCount out number)
    as
    begin
           delete from emp e where e.empno = byNo;
           select count(*) into getCount from emp e;
    end;
     */
  public void callProcedureYY(Connection conn) throws Exception {
    //指定调用的存储过程
    cs = conn.prepareCall("{call deleteLine(?,?)}");
    //设置参数
    cs.setInt(1, 7839);
    //这里需要配置OUT的参数新型
    cs.registerOutParameter(2, OracleTypes.NUMBER);
    //执行调用
    cs.execute();
    //输入返回值
    System.out.println(cs.getString(2));
  }

  public static int countStr(String str1, String str2) {
    String[] str = str1.split("\\?");
    return str.length - 1;
  }

  /**
   * @param conn
   * @return void
   * @throws Exception
   * @Discription 执行有参数，返回集合的存储过程
   */
    /*对应的存储过程语句
     --有参数返回一个列表，使用package
        create or replace package someUtils
        as
               type cur_ref is ref cursor;
               procedure selectRows(cur_ref out someUtils.cur_ref);
          end someUtils;

        create or replace package body someUtils
        as
               procedure selectRows(cur_ref out someUtils.cur_ref)
                 as
                   begin
                         open cur_ref for select * from emp e;
                   end selectRows;
        end someUtils;
     */
  public void callProcedureYYL(Connection conn, String call, String[] value, int position) throws Exception {
    //执行调用的存储过程
    cs = conn.prepareCall(call);
//        cs = conn.prepareCall("{call someUtils.selectRows(?)}");
    //设置返回参数
//        20170611","cd_store":"a001","cd_billno":"0001"}
//
    int index = countStr(call, "?");
    for (int i = 0; i < index; i++) {
      if (position != 0 & position - 1 == i) {
        cs.registerOutParameter(position, java.sql.Types.VARCHAR);
        continue;
      }
      Log.d("sql", "cs.setString(" + (i + 1) + value[i] + ")");
      cs.setString(i + 1, value[i]);
    }
    cs.execute();

//        cs.setString(3,"");

    //执行调用

    // 获取结果集 结果集是一个Object类型，需要进行强制转换 rs = (ResultSet)
    String rs = cs.getString(3);
    Log.d("sql", rs.toString());
    //输出返回值
//        while(rs.next()){
//            Log.d("sql",rs.getInt(1)+"\t"+rs.getString(2));
//            System.out.println(rs.getInt(1)+"\t"+rs.getString(2));
//        }
  }

  /**
   * @param conn
   * @return void
   * @throws Exception
   * @Discription 执行有参数的函数
   */
    /*对应的存储过程语句
     --创建函数，有参数
    create or replace function useOther(byNo in number)
    return String
    as
        returnValue char(10);
           begin
              select count(*) into returnValue from emp e where e.empno > byNo;
              return returnValue;
           end;
     */
  public void callProcedureFY(Connection conn) throws Exception {
    //指定调用的函数
    cs = conn.prepareCall("{? = call FUN_GET_STORESTOCK(?,?,?)}");
    //配置OUT参数信息
    cs.registerOutParameter(1, java.sql.Types.VARCHAR);
    //配置输入参数
    cs.setString(2, "S001");
    cs.setString(3, "YY001");
//        String    strDate    =    "20170620";
//
//        StringTokenizer    st    =    new StringTokenizer(strDate,    "-");
//
//        java.sql.Date    date    =    new    java.sql.Date(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
    cs.setString(4, "20170620");
    //执行过程调用
    cs.execute();
    //输入返回值
    System.out.println(cs.toString());
  }

  /**
   * @param conn
   * @param rs
   * @return void
   * @Discription 执行相应关闭操作
   */
  public void closeConn(Connection conn, ResultSet rs) {
    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }


  public static void initData(String call,String[] str,String index) {
    CallProcedure cp = new CallProcedure();
    try {
      //开启事务管理
      cp.conn.setAutoCommit(true);
//            String str[] = {"a004","20170617",""};
//            "{call PRO_CONMATASK_BILLNO_DML(?,?,?)}"
      cp.callProcedureYYL(cp.conn,call,str,Integer.valueOf(index));
      cp.conn.commit();
    } catch (Exception e) {
      e.printStackTrace();
      try {
        cp.conn.rollback();
      } catch (Exception e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
      // TODO: handle exception
    }finally
    {
      cp.closeConn(cp.conn, cp.rs);
    }
  }
}
