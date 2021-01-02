/* -----------------------------------------------------
 *  2020년 1학기 데이터베이스 중간시험
 *  소프트웨어학과 201721782 공현주
 * -------------------------------------------------- */

import java.sql.*;
import java.util.Scanner;

public class SqlTest {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub	
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	String url = "jdbc:postgresql://localhost:5432/";
	String user = "postgres";
	String password = "hjoo4878";
	
try {
	Scanner scan = new Scanner(System.in);
	System.out.println("SQL Programming Test");
	
	System.out.println("Connecting PostgreSQL database");
	// JDBC를 이용해 PostgreSQL 서버 및 데이터베이스 연결
	
	conn = DriverManager.getConnection(url,user,password);
	stmt = conn.createStatement();
	
	System.out.println("Creating College, Student, Apply relations");
	// 3개 테이블 생성: Create table문 이용
	
	
	String createC = "create table College(cName varchar(20), state char(2), enrollment int, primary key(cName))";
	String createS = "create table Student(sID int, sName varchar(20), GPA numeric(2,1), sizeHS int, primary key(sID))";
	String createA = "create table Apply(sID int, cName varchar(20), major varchar(20), decision char,primary key(sID, cName, major),"
			+ "foreign key(sID) references Student(sID),foreign key(cName) references College(cName))";
	
	stmt.executeUpdate(createC);
	stmt.executeUpdate(createS);
	stmt.executeUpdate(createA);
	

	System.out.println("Inserting tuples to College, Student, Apply relations");
	// 3개 테이블에 튜플 생성: Insert문 이용
	
	String insert;
	
	insert = "insert into College values ('Stanford', 'CA', 15000);"+
			"insert into College values ('Berkeley', 'CA', 36000);"+
 			"insert into College values ('MIT', 'MA', 10000);"+
 			"insert into College values ('Cornell', 'NY', 21000);";
	stmt.executeUpdate(insert);
	
	insert = "insert into Student values (123, 'Amy', 3.9, 1000);"+
 			"insert into Student values (234, 'Bob', 3.6, 1500);"+
			"insert into Student values (345, 'Craig', 3.5, 500);"+
			"insert into Student values (456, 'Doris', 3.9, 1000);"+
			"insert into Student values (567, 'Edward', 2.9, 2000);"+
			"insert into Student values (678, 'Fay', 3.8, 200);"+
			"insert into Student values (789, 'Gary', 3.4, 800);"+
			"insert into Student values (987, 'Helen', 3.7, 800);"+
			"insert into Student values (876, 'Irene', 3.9, 400);"+
			"insert into Student values (765, 'Jay', 2.9, 1500);"+
			"insert into Student values (654, 'Amy', 3.9, 1000);"+
			"insert into Student values (543, 'Craig', 3.4, 2000);";
	stmt.executeUpdate(insert);


	insert = "insert into Apply values (123, 'Stanford', 'CS', 'Y');"+
			"insert into Apply values (123, 'Stanford', 'EE', 'N');"+
			"insert into Apply values (123, 'Berkeley', 'CS', 'Y');"+
			"insert into Apply values (123, 'Cornell', 'EE', 'Y');"+
			"insert into Apply values (234, 'Berkeley', 'biology', 'N');"+
			"insert into Apply values (345, 'MIT', 'bioengineering', 'Y');"+
			"insert into Apply values (345, 'Cornell', 'bioengineering', 'N');"+
			"insert into Apply values (345, 'Cornell', 'CS', 'Y');"+
			"insert into Apply values (345, 'Cornell', 'EE', 'N');"+
			"insert into Apply values (678, 'Stanford', 'history', 'Y');"+
			"insert into Apply values (987, 'Stanford', 'CS', 'Y');"+
			"insert into Apply values (987, 'Berkeley', 'CS', 'Y');"+
			"insert into Apply values (876, 'Stanford', 'CS', 'N');"+
			"insert into Apply values (876, 'MIT', 'biology', 'Y');"+
			"insert into Apply values (876, 'MIT', 'marine biology', 'N');"+
			"insert into Apply values (765, 'Stanford', 'history', 'Y');"+
			"insert into Apply values (765, 'Cornell', 'history', 'N');"+
			"insert into Apply values (765, 'Cornell', 'psychology', 'Y');"+
			"insert into Apply values (543, 'MIT', 'CS', 'N');";
	stmt.executeUpdate(insert);
	
	int idx;
// ------------------------------------------------------------------
	System.out.println("Continue? (Enter 1 for continue)");
	scan.nextLine();
	idx=0;
	
	System.out.println("Query 1");
	String query = "select * from College;";
	
	rs = stmt.executeQuery(query);
	
	System.out.println("  |cName	|state	|enrollment");
	System.out.println("------------------------------------");
	while(rs.next()){
	String cName = rs.getString("cName");
	String state = rs.getString("state");
	int enrollment = rs.getInt("enrollment");
	System.out.printf("%2d|%-10s	|%2s	|%d\n",++idx, cName, state, enrollment);
	}
	
// --------------------------------------------------------------------
	System.out.println("Continue? (Enter 1 for continue)");
	scan.nextLine();
	idx=0;
	
	System.out.println("Query 2");
	query = "select * from Student;";
	
	rs = stmt.executeQuery(query);
	
	System.out.println("  |sID	|sName	|GPA	|sizeHS");
	System.out.println("--------------------------------");
	while(rs.next()){
	int sID = rs.getInt("sID");
	String sName = rs.getString("sName");
	java.math.BigDecimal GPA = rs.getBigDecimal("GPA");
	int sizeHS = rs.getInt("sizeHS");
	System.out.printf("%2d|%d	|%s	|%s	|%d\n", ++idx,sID,sName,GPA,sizeHS);
	}
	  
// ----------------------------------------------------------------
	System.out.println("Continue? (Enter 1 for continue)");
	scan.nextLine();
	idx=0;
	
	System.out.println("Query 3");
	query = "select * from Apply;";
	
	 rs = stmt.executeQuery(query);
	 
	 System.out.println("  |sID	|cName		|major		|decision");
	 System.out.println("-------------------------------------------------");
	 while(rs.next()){
	 int sID = rs.getInt("sID");
	 String cName = rs.getString("cName");
	 String major = rs.getString("major");
	 String decision = rs.getString("decision");
	 System.out.printf("%2d|%d	|%10s	|%-10s	|%s\n",++idx,sID,cName,major,decision);
	 
	 }
	
// --------------------------------------------------------------------
	System.out.println("Continue? (Enter 1 for continue)");
	scan.nextLine();
	idx=0;
	
	System.out.println("Query 4");
	query = "select * from Student S1 where (select count(*) from Student S2 "
			+ "where S2.sID <> S1.sID and S2.GPA = S1.GPA) = (select count(*) "
			+ "from Student S2 where S2.sID <> S1.sID and S2.sizeHS = S1.sizeHS);";
	
	rs = stmt.executeQuery(query);
	
	System.out.println("  |sID	|sName	|GPA	|sizeHS");
	System.out.println("--------------------------------");
	while(rs.next()){
		int sID = rs.getInt("sID");
		String sName = rs.getString("sName");
		java.math.BigDecimal GPA = rs.getBigDecimal("GPA");
		int sizeHS = rs.getInt("sizeHS");
		System.out.printf("%2d|%d	|%s	|%s	|%d\n",++idx,sID, sName, GPA, sizeHS);
	}
	
// --------------------------------------------------------------------------------------------------------------------
	System.out.println("Continue? (Enter 1 for continue)");
	scan.nextLine();
	idx=0;
	System.out.println("Query 5");
	query = "select Student.sID, sName, count(distinct cName) from Student, Apply where Student.sID = Apply.sID group by Student.sID;";
	
	rs = stmt.executeQuery(query);
	
	System.out.println("  |sID	|sName	|count");
	System.out.println("----------------------");
	while(rs.next()) {
		int sID = rs.getInt("sID");
		String sName = rs.getString("sName");
		int count = rs.getInt("count");
		System.out.printf("%2d|%d	|%s	|%d\n", ++idx,sID, sName, count);
	}
	
	
// -------------------------------------------------------------------------------------------------------------------
	System.out.println("Continue? (Enter 1 for continue)");
	scan.nextLine();
	idx=0;
	
	System.out.println("Query 6");
	query = "select major from Student, Apply where Student.sID = Apply.sID group by major having max(GPA) < (select avg(GPA) from Student);";	
	
	rs = stmt.executeQuery(query);

	System.out.println("  |major");
	System.out.println("------------------");
	while(rs.next()) {
		String major = rs.getString("major");
		System.out.printf("%2d|%s\n",++idx,major);
	}
// ------------------------------------------------------------------------------------------------------------------
	System.out.println("Continue? (Enter 1 for continue)");
	scan.nextLine();
	idx=0;
	
	System.out.println("Query 7");
	query = "select sName, GPA from Student join Apply on Student.sID = Apply.sID where sizeHS < ? and major = ? and cName = ?;";
	
	PreparedStatement p = conn.prepareStatement(query);
	
	System.out.print("Enter a sizeHS : ");
	int sizehs =  scan.nextInt();
	System.out.print("Enter a major : ");
	String major = scan.next();
	System.out.print("Enter a college name : ");
	String cname = scan.next();
	
	p.clearParameters();
	p.setInt(1, sizehs);
	p.setString(2, major);
	p.setString(3, cname);
	
	rs = p.executeQuery();
	System.out.println("  |sName	|GPA");
	System.out.println("---------------------");
	while(rs.next()) {
		String sName = rs.getString(1);
		java.math.BigDecimal GPA = rs.getBigDecimal(2);
		System.out.printf("%2d|%s	|%s\n", ++idx,sName, GPA);
	}
	
	
	scan.close();
	rs.close();
	stmt.close();
	conn.close();
	
}catch(SQLException ex) {
	throw ex;
}

}

}
