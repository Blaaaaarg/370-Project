
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
//import java.sql.Driver;
//import java.util.Properties;


// Connect to the database
public class DBConnect 
{
	String userName = "********";
	String password = "*********";
	String dbms = "jdbc:mysql://";
	String portNumber = "3306";
	String serverName = "*****************";
	
	
	public Connection Connect() throws SQLException 
	{
		
	    Connection conn = null;
	    
	    System.out.println("Attempting database connection...");
	     conn = DriverManager.getConnection(
	               dbms + serverName + ":"+ portNumber,
	                userName, password);
	 	
	    System.out.println("Connected to database!");
	    return conn;
	}
}