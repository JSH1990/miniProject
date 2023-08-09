package Member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Common {

    final static String ORACLE_URL = "jdbc:mysql://localhost:3306/mini?useSSL=false";
    final static String ORACLE_ID = "root";
    final static String ORACLE_PWD = "1234";
    final static String ORACLE_DRV = "com.mysql.jdbc.Driver";

    public static Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName(ORACLE_DRV); //오라클 디바이스 드라이버 로딩
            conn = DriverManager.getConnection(
            		"jdbc:mysql://localhost:3306/mini?useSSL=false",
            		"root",
            		"1234");
        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }
    public static void close(Connection conn){
        try {
            if (conn != null && !conn.isClosed()){
                conn.close();
          
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void close(Statement stmt){
        try {
            if (stmt != null && !stmt.isClosed()){
                stmt.close();
       
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void close(ResultSet rset){
        try {
            if (rset != null && !rset.isClosed()){
                rset.close();
         
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        }

    }