package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Account;
import model.Login;

public class AccountDao {

	public Account findByLogin(Login login){
		Connection conn = null;
		Account account = null;
		String url = "jdbc:mysql://localhost/mydb";
		String user = "root";
		String password = "password";
		try{
			// JDBCドライバ読み込み
			Class.forName("com.mysql.jdbc.Driver");
			// データベース接続
			conn = DriverManager.getConnection(url, user, password);
			String sql = "SELECT USER_ID, PASS, MAIL, NAME, AGE"
					+ " FROM ACCOUNT WHERE USER_ID = ? AND PASS = ?";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, login.getUserId());
			psmt.setString(2, login.getPassword());
			
			ResultSet rs = psmt.executeQuery();
			
			if(rs.next()){
				String userId = rs.getString("USER_ID");
				String pass = rs.getString("PASS");
				String mail = rs.getString("MAIL");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				
				account = new Account(userId, pass, mail, name, age);
			}
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			return null;
		}finally{
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
					return null;
				}
			}
		}
		return account;
	}
	
	public int registerUser(Account account){
		Connection conn = null;
		String url = "jdbc:mysql://localhost/mydb";
		String user = "root";
		String password = "password";
		int count = 0;
		try{
			// JDBCドライバ読み込み
			Class.forName("com.mysql.jdbc.Driver");
			// データベース接続
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false); 
			String sql = " INSERT INTO ACCOUNT VALUES (?,?,?,?,?,now())";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, account.getUserId());
			psmt.setString(2, account.getPass());
			psmt.setString(3, account.getMail());
			psmt.setString(4, account.getName());
			psmt.setInt(5, account.getAge());
			
			count = psmt.executeUpdate();
			
			conn.commit();
			
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}finally{
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		return count;
	}
	
}
