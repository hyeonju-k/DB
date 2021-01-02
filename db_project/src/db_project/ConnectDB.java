package db_project;

import java.sql.*;

public class ConnectDB {

	String stmt;
	ResultSet rs;
	static Statement st = null;
	PreparedStatement p1, p2, p3;
	
	String url = "jdbc:postgresql://localhost:5432/db_Proj";
	String user ="postgres";
	String password = "hjoo4878";
	
	static Connection conn = null;

	public ConnectDB() throws SQLException {
		try {
			System.out.println("Connecting PostgreSQL database");
			conn = DriverManager.getConnection(url, user, password);
			
			stmt = "drop table near;"
					+ "drop table bus_stop;"
					+ "drop table restroom;";
					
			p1 = conn.prepareStatement(stmt);
			p1.executeUpdate();
			
			stmt = "create table restroom(restName varchar(50), restAd varchar(50), "
					+ "disM_b int, disM_u int, disF int, open varchar(50), "
					+ "latitude float(20), longitude float(20), primary key(restName, restAd));";
			p1 = conn.prepareStatement(stmt);
			p1.executeUpdate();
			
			stmt = "create table bus_stop(stationId int, stationName varchar(100), "
					+ "lat float(20), long float(20), primary key(stationId, stationName));";
			p1 = conn.prepareStatement(stmt);
			p1.executeUpdate();
			
			stmt = "create table near(stationId int, stationName varchar(100), restName varchar(50), restAd varchar(50), "
					+ "dist NUMERIC(20,1), primary key(stationId, stationName, restName, restAd), "
					+ "foreign key(stationId, stationName) references bus_stop(stationId, stationName), "
					+ "foreign key(restName, restAd) references restroom(restName, restAd))";
			p1 = conn.prepareStatement(stmt);
			p1.executeUpdate();
			
			//stmt.close();
			//conn.close();
		}catch(SQLException ex) {
			throw ex;
		} 
	  }

	public static Connection getConnection() {
		return conn;
	}
	
	public static Statement getStatement() {
		return st;
	}

}