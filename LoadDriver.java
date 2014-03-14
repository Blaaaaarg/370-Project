import java.sql.DriverManager;
import java.sql.SQLException;

public class LoadDriver {
	
	public void load() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
	{
		System.out.println("Attempting to load drivers...");
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("Drivers Loaded!");
	}
	
}
