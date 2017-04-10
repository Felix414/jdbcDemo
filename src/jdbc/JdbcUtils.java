package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class JdbcUtils {
	private final static String DRIVERCLASSNAME = "com.mysql.jdbc.Driver";
	private final static String DATABASEURL = "jdbc:mysql://192.168.0.222:3306/crebas?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
	private final static String USERNAME = "m5rage";
	private final static String PASSWORD = "eng5ne";
	/**
	 * �����ݿ�����
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception {
		Connection conn = null;
		Class.forName(DRIVERCLASSNAME);
		conn = DriverManager.getConnection(DATABASEURL,	USERNAME, PASSWORD);
		return conn;
	}
	
	/**
	 * ������ѯsql����
	 * @return
	 * @throws Exception
	 */
	public static Statement getStatement(Connection conn) throws Exception{
		Statement statement = null;
		if(conn!=null){
			statement = conn.createStatement();
			if(statement!=null){
				return statement;
			}
		}
		return null;
	}
	
	/**
	 * ��������sql����
	 * @return
	 * @throws Exception
	 */
	public static Statement getUpdateStatement(Connection conn) throws Exception{
		Statement statement = null;
		if(conn!=null){
			statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			if(statement!=null){
				return statement;
			}
		}
		return null;
	}
	/**
	 * �ر����ݿ�������Դ
	 * @param conn
	 * @param rs
	 * @param ps
	 * @throws Exception
	 */
	public static void closeConnection(Connection conn,ResultSet rs,Statement st) throws Exception{
		if(rs!=null) {rs.close();}
		if(st!=null) {st.close();}
		if(conn!=null) {conn.close();}
	}
}
