package newproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;


public class MysqlConnection {
	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://new-db.crnbwzhpnodx.ap-northeast-2.rds.amazonaws.com/newdb";
	private static final String DB_USERNAME = "admin";
	private static final String DB_PASSWORD = "OOPproject_10";
			
	public static Connection getConnection(){
		Connection conn = null;
		try{
			//Register the JDBC driver
			Class.forName(DB_DRIVER);
 
			//Open the connection
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
 
			if(conn != null){
			   System.out.println("Successfully connected.");
			}else{
			   System.out.println("Failed to connect.");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}
	
	//테이블 보기
	public void viewTable(String table) {// table 변수 ->all(전체보기),user,note_list,friend,mynote,basicvoca,bookmark
		PreparedStatement preparedStatement = null;
		String sql = null;
		ResultSet rs = null;
		
		try {
			Connection conn = MysqlConnection.getConnection();
			
			if(table.equals("user") || table.equals("all")) {
				System.out.println("");
				sql = "SELECT * FROM user;";
				preparedStatement = conn.prepareStatement(sql);
				rs = preparedStatement.executeQuery();
				
				System.out.println("user_id\tnickname");
				
				while(rs.next()) {
					String user_id = rs.getString("user_id");
					String nickname = rs.getString("nickname");
					System.out.println(user_id + " \t" + nickname);
				}
			}
				
			if(table.equals("friend") || table.equals("all")) {
				System.out.println("");
				sql = "SELECT * FROM friend;";
				preparedStatement = conn.prepareStatement(sql);
				rs = preparedStatement.executeQuery();
				
				System.out.println("my_id\tfriend_nickname");
				while(rs.next()) {
					String my_id = rs.getString("my_id");
					String friend_nickname= rs.getString("friend_nickname");
					System.out.println(my_id + "\t" + friend_nickname);
				}
			}
			
			if(table.equals("note_list") || table.equals("all")) {
				System.out.println("");
				sql = "SELECT * FROM note_list;";
				preparedStatement = conn.prepareStatement(sql);
				rs = preparedStatement.executeQuery();
				System.out.println("note_name\tuser_id");
				while(rs.next()) {
					String note_name = rs.getString("note_name");
					String user_id = rs.getString("user_id");
					System.out.println(note_name + "\t" + user_id+ "\t" );
				}
			}
			
			
			if(table.equals("basic_voca") || table.equals("all")) {
				System.out.println("");
				sql = "SELECT * FROM basic_voca;";
				preparedStatement = conn.prepareStatement(sql);
				rs = preparedStatement.executeQuery();
				System.out.println("num\tword\tmeaning");
				while(rs.next()) {
					int num = rs.getInt("num");
					String word = rs.getString("word");
					String meaning = rs.getString("meaning");
					System.out.println(num + "\t" + word +  "\t" + meaning + "\t");
				}
			}
			
			if(table.equals("mynote") || table.equals("all")) {
				System.out.println("");
				sql = "SELECT * FROM mynote;";
				preparedStatement = conn.prepareStatement(sql);
				rs = preparedStatement.executeQuery();
				
				System.out.println("num\tuser_id\tnote_name\tword\tmeaning");
				while(rs.next()) {
					int num = rs.getInt("num");
					String user_id = rs.getString("user_id");
					String note_name= rs.getString("note_name");
					String word = rs.getString("word");
					String meaning = rs.getString("meaning");
					System.out.println(num + "\t" + user_id + "\t" + note_name + " \t" + word + "\t" + meaning + "\t");
				}
			}
			if(table.equals("bookmark") || table.equals("all")) {
				System.out.println("");
				sql = "SELECT * FROM bookmark;";
				preparedStatement = conn.prepareStatement(sql);
				rs = preparedStatement.executeQuery();
				
				System.out.println("user_id\tword\tmeaning");
				while(rs.next()) {
					
					String user_id = rs.getString("user_id");
					
					String word = rs.getString("word");
					String meaning = rs.getString("meaning");
					System.out.println( user_id + "\t" + word + "\t" + meaning + "\t");
				}
			}
			
			
			
			
			preparedStatement.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		
		
		}
	}
	
	//DB 테이블 생성 (웬만하면 건들지 말것)
	public void creatTable(String sql){//  ex) "CREATE table newtable(ID INT)"

			PreparedStatement preparedStatement = null;
			
			
			try {
				Connection conn = getConnection();
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.execute();
				preparedStatement.close();
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			
	}
	
	//sql문으로 작동
	public void query(String sql){// ex) "Insert into Voca values(3,"+"\'grape\',"+"\'포도\')"

			PreparedStatement preparedStatement = null;
			
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
	
	//user 테이블에 id추가
	public void userInsert(String id, String nickname) {
		PreparedStatement preparedStatement = null;
		
		String sql = "INSERT INTO user VALUES("+ "\'"+ id +"\'" +","+ "\'" + nickname +"\'"+");";
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
	
	//mynote 테이블에 (나만의 단어장) 단어 추가
	public void noteInsert(String user_id, String note_name, String word, String meaning) {
		PreparedStatement preparedStatement = null;
		
		String sql = "SELECT MAX(num) FROM mynote;";
		try {
			
			
			Connection conn = MysqlConnection.getConnection();
			
			preparedStatement = conn.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			int num = rs.getInt("MAX(num)")+1;
			
			sql = "INSERT INTO mynote VALUES("+ "\'"+ num +"\'" +","+ "\'" + user_id +"\'"+","+"\'"+ note_name+"\'" +","+"\'"+ word +"\'" +","+"\'"+ meaning +"\'"+");";
			System.out.println(sql);
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.execute();
			preparedStatement.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	//단어장에서 단어 삭제
	public void noteDelete(String user_id,String note_name ,String word) {
		PreparedStatement preparedStatement = null;
		
		String sql = "DELETE FROM mynote WHERE(user_id ="+"\'"+user_id+"\'"+")and(note_name="+"\'"+note_name+"\'"+")and(word="+"\'"+word+"\'"+");";
		System.out.println(sql);
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
	
	//friend 테이블에 친구 추가
	public void friendInsert(String my_id, String friend_nickname) {
		PreparedStatement preparedStatement = null;
		
		String sql = "INSERT INTO friend VALUES("+ "\'"+ my_id +"\'" +","+ "\'" + friend_nickname +"\'"+");";
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
	
	//단어장 추가
	public void noteListInsert(String note_name, String user_id) {
		PreparedStatement preparedStatement = null;
		
		String sql = "INSERT INTO note_list VALUES("+ "\'"+ note_name +"\'" +","+ "\'" + user_id +"\'"+");";
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
	
	//단어장 자체 삭제(내부 단어 포함)
	public void noteListDelete(String user_id,String note_name ) {
		PreparedStatement preparedStatement = null;
		
		String sql = "DELETE FROM note_list WHERE(user_id ="+"\'"+user_id+"\'"+")and(note_name="+"\'"+note_name+"\'"+");";
		System.out.println(sql);
		try {
			Connection conn = MysqlConnection.getConnection();

			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.execute();
			sql = "DELETE FROM mynote WHERE(user_id ="+"\'"+user_id+"\'"+")and(note_name="+"\'"+note_name+"\'"+");";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.execute();
			preparedStatement.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	//번호를 이용해 mynote 테이블의 내용 수정
	public void noteUpdate(String column, String new_data, int num) {
		PreparedStatement preparedStatement = null;
		String sql = null;
		if(column.equals("word"))
			sql = "UPDATE mynote SET word =" + "\'"+new_data+"\'"+"WHERE(num = "+num +");";
		else if(column.equals("meaning"))
			sql = "UPDATE mynote SET meaning =" + "\'"+new_data+"\'"+"WHERE(num = "+num+");";
		
		try {
			Connection conn = MysqlConnection.getConnection();
			System.out.println(sql);
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.execute();
			preparedStatement.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	//id의 모든 노트 목록을 가져온다
	public String[] noteList(String id) {
		Vector<String> note_list = new Vector<String>();
		PreparedStatement preparedStatement = null;
		String sql = null;
		ResultSet rs = null;
		String[] notelist=null;

		try {
			Connection conn = MysqlConnection.getConnection();
			sql = "SELECT note_name FROM note_list WHERE user_id = '" + id+"\'"+";";
			System.out.println(sql);
			preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				String note_name = rs.getString("note_name");
				note_list.add(note_name);
			
			}
			notelist = note_list.toArray(new String[note_list.size()]);
			return notelist;
		}catch(Exception e){
			e.printStackTrace();
		}
		return notelist;
		
	}
	
	//지정된 노트의 단어 목록을 모두 불러온다
	public HashMap<String,String> wordList(String note_name) {
		HashMap<String, String> wordlist = new HashMap<String,String>();
		PreparedStatement preparedStatement = null;
		String sql = null;
		ResultSet rs = null;

		try {
			Connection conn = MysqlConnection.getConnection();
			sql = "SELECT word,meaning FROM mynote WHERE note_name = '" + note_name+"\'"+";";
			System.out.println(sql);
			preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				String word = rs.getString("word");
				String meaning = rs.getString("meaning");
				wordlist.put(word, meaning);
			}
			
			return wordlist;
		}catch(Exception e){
			e.printStackTrace();
		}
		return wordlist;
		
	}
	
	//id의 전체 친구 닉네임 목록을 불러온다
	public String[] friendList(String id) {
		Vector<String> friend_list = new Vector<String>();
		String[] friendlist = null;
		PreparedStatement preparedStatement = null;
		String sql = null;
		ResultSet rs = null;

		try {
			Connection conn = MysqlConnection.getConnection();
			sql = "SELECT friend_nickname FROM friend WHERE my_id = '" + id+"\'"+";";
			System.out.println(sql);
			preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				String nickname = rs.getString("friend_nickname");
				
				friend_list.add(nickname);
			}
			friendlist = friend_list.toArray(new String[friend_list.size()]);
			return friendlist;
		}catch(Exception e){
			e.printStackTrace();
		}
		return friendlist;
		
	}
	
	//id가 존재하는지 확인 true ->있음 false ->없음
	public boolean hasId(String id) {
		PreparedStatement preparedStatement = null;
		String sql = null;
		ResultSet rs = null;
		String nickname = null;

		try {
			Connection conn = MysqlConnection.getConnection();
			sql = "SELECT user_id FROM user WHERE user_id = '" + id+"\'"+";";
			System.out.println(sql);
			preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				nickname = rs.getString("user_id");
			}
			
			if(nickname == null) return false;
			else return true;
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
		
	}
	
	//basic_voca 테이블에서 search
	public String[] search(String lang, String search_word) {
		String[] a = new String[2]; // 출력 결과를 넣을 String배열
		PreparedStatement preparedStatement = null;
		String sql = null;
		ResultSet rs = null;
		
		try {
			Connection conn = MysqlConnection.getConnection();
			
			if(lang.equals("Eng")) {// 언어가 영어일때
				sql = "SELECT meaning FROM basic_voca WHERE word = '" + search_word+"\'"+";";
				System.out.println(sql);
				preparedStatement = conn.prepareStatement(sql);
				rs = preparedStatement.executeQuery();
				
				while(rs.next()) {
					String meaning = rs.getString("meaning");
					a[0] = search_word;
					a[1] = meaning;
					
				}
			}
			
			if(lang.equals("Kor")) {// 언어가 한국어 일때
				
				sql = "SELECT word FROM basic_voca WHERE meaning = '" + search_word+"\'"+";";
				System.out.println(sql);
				preparedStatement = conn.prepareStatement(sql);
				rs = preparedStatement.executeQuery();
				
				
				while(rs.next()) {
					String word = rs.getString("word");
					a[0] = search_word;
					a[1] = word;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		
		
		}
				
		
		return a;
	}
	
	//즐겨찾기 추가
	public void bookMarkInsert(String user_id, String word, String meaning) {
		PreparedStatement preparedStatement = null;
		
		String sql = "INSERT INTO bookmark VALUES("+ "\'"+ user_id +"\'" +","+ "\'" + word +"\'"+","+"\'"+meaning+"\'"+");";
		System.out.println(sql);
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
	//즐겨찾기 모두 가져오기
	public HashMap<String,String> bookMarkList(String user_id) {
		HashMap<String, String> wordlist = new HashMap<String,String>();
		PreparedStatement preparedStatement = null;
		String sql = null;
		ResultSet rs = null;

		try {
			Connection conn = MysqlConnection.getConnection();
			sql = "SELECT word,meaning FROM bookmark WHERE user_id = '" + user_id+"\'"+";";
			System.out.println(sql);
			preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				String word = rs.getString("word");
				String meaning = rs.getString("meaning");
				wordlist.put(word, meaning);
			}
			
			return wordlist;
		}catch(Exception e){
			e.printStackTrace();
		}
		return wordlist;
		
	}
	//즐겨찾기 내부 단어 삭제
	public void bookMarkDelete(String user_id, String word) {
		PreparedStatement preparedStatement = null;
		
		String sql = "DELETE FROM bookmark WHERE(user_id ="+"\'"+user_id+"\'"+")and(word="+"\'"+word+"\'"+");";
		System.out.println(sql);
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

	
