package data;

import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import jdbc.JdbcUtils;


public class DealData {
	
	public static void main(String[] args) {
		//updateItvZhztListData();
		updateVideoData();
	}
	
	/**
	 * 处理itv_zhzt_list表中的picture_sd和picture_hd无效图片
	 */
	public static void updateItvZhztListData() {
		try{
			String pic_path = "http://pics2016.3jidi.com/";
			String query_sql = "SELECT id,title,picture_sd,picture_hd FROM itv_zhzt_list";
			Connection conn = JdbcUtils.getConnection(); 
	        conn.setAutoCommit(false); 
	        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	        		ResultSet.CONCUR_UPDATABLE, ResultSet.CLOSE_CURSORS_AT_COMMIT); 
	        ResultSet rs = stmt.executeQuery(query_sql); 
	        int count_sd = 0, count_hd = 0;
	        //循环逐条更新查询结果集数据 
	        while (rs.next()) { 
	        	String picture_sd = rs.getString("picture_sd");
	        	if(!existHttpPath(pic_path + picture_sd)){
	        		count_sd ++;
	        		rs.updateString("picture_sd", "upload/pics/itv/video_sd.jpg");
	        	}
	        	String picture_hd = rs.getString("picture_hd");
	        	if(!existHttpPath(pic_path + picture_hd)){
	        		count_hd ++;
	        		rs.updateString("picture_hd", "upload/pics/itv/video_hd.jpg");
	        	}
	        	rs.updateRow();
                //更新数据的name列 
                //rs.updateString("name", "lavasoft25"); 
                //rs.updateDate("updatetime", new Date()); 
	        } 
	        System.out.println("count_sd=" + count_sd);
	        System.out.println("count_hd=" + count_hd);
//	        System.out.println(conn.isReadOnly()); 
//	        System.out.println(conn.nativeSQL(query_sql)); 
	        conn.commit(); 
	        JdbcUtils.closeConnection(conn, rs, stmt); 
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	
	public static void updateVideoData() {
		try{
			String pic_path = "http://192.168.0.176:8081/";
			String query_sql = "SELECT ID,Name,Pictrue from video";
			Connection conn = JdbcUtils.getConnection(); 
	        conn.setAutoCommit(false); 
	        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	        		ResultSet.CONCUR_UPDATABLE, ResultSet.CLOSE_CURSORS_AT_COMMIT); 
	        ResultSet rs = stmt.executeQuery(query_sql); 
	        int count = 0;
	        //循环逐条更新查询结果集数据 
	        while (rs.next()) {
	        	String Pictrue = rs.getString("Pictrue");
	        	if(!existHttpPath(pic_path + Pictrue)){
	        		count ++;
	        		rs.updateString("Pictrue", "upload/pics/itv/video_hd.jpg");
	        	}
	        
	        	rs.updateRow();
	        } 
	        System.out.println("count=" + count);
	        conn.commit(); 
	        JdbcUtils.closeConnection(conn, rs, stmt); 
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public static Boolean existHttpPath(String httpPath){
		URL httpurl = null;
		try {
			httpurl = new URL(httpPath);
			URLConnection rulConnection = httpurl.openConnection();
			rulConnection.getInputStream();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
