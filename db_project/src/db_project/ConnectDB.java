package db_project;

import java.sql.*;

public class ConnectDB {

	Statement stmt;
	ResultSet rs;
	   
	String url = "jdbc:postgresql://localhost:5432/db_Proj";
	String user ="postgres";
	String password = "";

	
	/*
	 *
	 * 
	 *  password 
	 * 
	 * 
	 * 
	 * */
	
	
	static Connection conn = null;

	public ConnectDB() throws SQLException {
		try {
		//   Scanner scan = new Scanner(System.in);
		 
			System.out.println("Connecting PostgreSQL database");
		   
			conn = DriverManager.getConnection(url,user,password);
			stmt = conn.createStatement();
			
			String createR = "create table restroom(restName varchar(50), restAd varchar(50), "
					+ "disM_b int, disM_u int, disF int, open varchar(50), "
					+ "latitude float(20), longitude float(20), primary key(restName, restAd));";
			  
		    //stmt.executeUpdate(createR); 
		    
			stmt.close();
			conn.close();
		}catch(SQLException ex) {
			throw ex;
		} 
	  }

	public static Connection getConnection() {
		return conn;
	}
}