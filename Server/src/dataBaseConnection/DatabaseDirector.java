package dataBaseConnection;

import java.sql.*;

public class DatabaseDirector {
	private Connection connection;

	public DatabaseDirector(String dataBasePath){
		try {
			Class.forName("org.sqlite.JDBC");
			this.connection = DriverManager.getConnection("jdbc:sqlite:" + dataBasePath);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet execute(String sqlStatement){
		Statement statement;
		ResultSet result = null;
		try {
			statement = this.connection.createStatement();
			statement.setQueryTimeout(30);

			result = statement.executeQuery("select * from food");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void close(){
		try {
			if(connection != null)
				connection.close();
		} catch(SQLException e) {
			System.err.println(e);
		}
	}

	public static void main(String[] args){
		DatabaseDirector director =  new DatabaseDirector("/Users/YutakaOmido/Desktop/example.sql");
		ResultSet rs = director.execute("select name from food");
		try {
			while(rs.next()){
				System.out.println(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		director.close();
	}
}
