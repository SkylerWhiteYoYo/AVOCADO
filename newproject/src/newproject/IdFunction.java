package newproject;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class IdFunction {


	public String passWord(String id) {
		PreparedStatement preparedStatement = null;
		String sql = null;
		ResultSet rs = null;
		String password = null;
		
		try {
			Connection conn = MysqlConnection.getConnection();
			sql = "SELECT password FROM user WHERE user_id = '" + id+"\'"+";";
			System.out.println(sql);
			preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				password = rs.getString("password");
			}
			System.out.println(password);
			return password;
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return password;
	}
	
	//id가 존재하는지 확인 true ->있음 false ->없음
	public boolean hasId(String id) {
		PreparedStatement preparedStatement = null;
		String sql = null;
		ResultSet rs = null;
		String user_id = null;
		
		try {
			Connection conn = MysqlConnection.getConnection();
			sql = "SELECT user_id FROM user WHERE user_id = '" + id+"\'"+";";
			System.out.println(sql);
			preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				user_id = rs.getString("user_id");
				
			}
		
			
			if(user_id == null) return false;
			else return true;
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
		
	}
	
	//user 테이블에 id추가
	public void userInsert(String id,String password) {
		PreparedStatement preparedStatement = null;
		
		String sql = "INSERT INTO user VALUES("+ "\'"+ id +"\'" +"," + "\'"+ password +"\'" +");";
		try {
			Connection conn = MysqlConnection.getConnection();

			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.execute();
			preparedStatement.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	

}
