// Project: SCRUMptious
// Class: DBQuery
// Author: Scott Walker
// Description: This class uses the Connector J driver and jdbc to pass SQL statements to the team
// RDS server, and capture any returned results.
// Note: Any functions marked with ### WIP ### are not complete.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import javax.swing.JOptionPane;

// This class contains functions that access our RDS server.
public class DBQuery {
	
	// ############################
	// ##### HELPER FUNCTIONS #####
	// ############################
	
	public static void infoBox(String infoMessage, String location)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "Error! " + location, JOptionPane.INFORMATION_MESSAGE);
    }
	
	public static boolean isdigit(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	
	// ##### END HELPER FUNCTIONS #####
	
	
	// ###############################
	// ##### GENERAL USE QUERIES #####
	// ###############################
	
	// Select a column of information from the database
	// ## WIP ## -- mostly working, add multi-table selection
	public String DBSelectColumn(Connection con, String dbName, String table, String column[], String match_string[], String match_column[])
	    throws SQLException {
		String query = "select ";
		// Add the columns to be selected to the query
		for (int i = 0; i < column.length; i++) {
			if (i != column.length - 1) {
				query = query + column[i] + ", ";
			}
			else {
				query = query + column[i];
			}
		}
		// Check to see if specific columns are being selected
		if (match_string.length == 0 || match_column.length == 0)
		{
			query = query + " from " + dbName + "." + table + ";";
		}
		else if (match_string.length == match_column.length)
		{
			query = query + " from " + dbName + "." + table + " where ";
			// Add the search terms and columns to the query
			for (int i = 0; i < match_string.length; i++)
			{
				query = query + match_column[i] + "=" + match_string[i];
				if (i < match_string.length - 1)
				{
					query = query + " and ";
				}
			}
			query = query + ";";
		}
		else
		{
			// fail
		}
		System.out.println("Attempting selection from " + dbName + "." + table);
		System.out.println("Attempting query: " + query);
		
		Statement stmt = null;
		String result = "";
		try {
			stmt = con.createStatement();
			
	        ResultSet rs = stmt.executeQuery(query);
	        for (int i = 0; i < column.length; i++) {
	        		while(rs.next()) {
	        			result = result + " | " + rs.getString(column[i]);
	        		}
	        		result = result + "~\n";
	        		rs.first();
	        		if (i < column.length - 1)
	        		{
	        			result = result + rs.getString(column[i + 1]);
	        		}
	        }
	    } catch (SQLException e ) {
          	System.out.println(e + "\n");
          	//this.infoBox("ERROR: ", "");
	    } finally {
	    	if (stmt != null) { stmt.close(); }
	    }
		return result;
	}
	
	// Select a row of information of a specific entry from the database
	// ### WIP ### --  mostly working, add multitable selection
	public String DBSelectRow(Connection con, String dbName, String table, String column[], String match_string[], String match_column[])
		throws SQLException {
		String query = "select ";
		for (int i = 0; i < column.length; i++) {
			if (i != column.length - 1) {
				query = query + column[i] + ", ";
			}
			else {
				query = query + column[i];
			}
		}
		// Check to see if specific columns are being selected
		if (match_string.length == 0 || match_column.length == 0)
		{
			query = query + " from " + dbName + "." + table + ";";
		}
		else if (match_string.length == match_column.length)
		{
			query = query + " from " + dbName + "." + table + " where ";
			// Add the search terms and columns to the query
			for (int i = 0; i < match_string.length; i++)
			{
				query = query + match_column[i] + "=" + match_string[i];
				if (i < match_string.length - 1)
				{
					query = query + " and ";
				}
			}
			query = query + ";";
		}
		else
		{
			// fail
		}
		//query = query + " from " + dbName + "." + table + ";";
		//query = query + " from " + dbName + "." + table + " where " + dbName + "." + table + "." + match_column[0] + "=" + "'" + match_string[0] + "'" + ";";
		System.out.println("Attempting selection from " + dbName + "." + table);
		System.out.println("Attempting query: " + query);
		
		//String query = 
		//		"select " + column + " from " + dbName + "." + table + " where " + dbName + "." + table + "." + match_column[0] + "=" + match_string[0] + ";";
		//System.out.println("Attempting selection of " + column + "with value(s)" + match_string[0] + " from " + dbName + "." + table);
		//System.out.println("Attempting query: " + query);
		
		Statement stmt = null;
		String result = "";
		try {
			stmt = con.createStatement();
			
		       ResultSet rs = stmt.executeQuery(query);
		       for (int i = 0; i < column.length; i++) {
		    	   System.out.println("Printing " + column[i]);
	        		while(rs.next()) {
	        			result = rs.getString(column[i]);
	        		}
	        		result = result + " | ";
	        		rs.first();
	        		if (i < column.length - 1)
	        		{
	        			result = result + rs.getString(column[i + 1]);
	        		}
		       }
		   } catch (SQLException e ) {
	         	System.out.println(e + "\n");
		   } finally {
		   	if (stmt != null) { stmt.close(); }
		   }
		return result;
		}
	
	// Search for a row of information from the database
	// ### WIP ### -- mostly working, add multitable
	public String DBRowSearch(Connection con, String dbName, String table, String column[], String match_string[], String match_column[])
		    throws SQLException {
		String query = "select ";
		for (int i = 0; i < column.length; i++) {
			if (i != column.length - 1) {
				query = query + column[i] + ", ";
			}
			else {
				query = query + column[i];
			}
		}
		// Check to see if search terms will work
		if (match_string.length == 0 || match_column.length == 0)
		{
			query = query + " from " + dbName + "." + table + ";";
		}
		else if (match_string.length == match_column.length)
		{
			query = query + " from " + dbName + "." + table + " where ";
			// Add the search terms and columns to the query
			for (int i = 0; i < match_string.length; i++)
			{
				query = query + match_column[i] + " like " + "'%" + match_string[i] + "%'";
				if (i < match_string.length - 1)
				{
					query = query + " and ";
				}
			}
			query = query + ";";
		}
		else
		{
			// fail
		}
			//String query = 
			//		"select " + column + " from " + dbName + "." + table + " where " + match_column[0] + " like " + "'%" + match_string[0] + "%'" + ";";
			System.out.println("Attempting search for " + column + "with value(s)" + match_string[0] + " from " + dbName + "." + table);
			System.out.println("Attempting query: " + query);
			
			Statement stmt = null;
			String result = "";
			try {
				stmt = con.createStatement();
				
		        ResultSet rs = stmt.executeQuery(query);
		        for (int i = 0; i < column.length; i++) {
			    	   System.out.println("Printing " + column[i]);
		        		while(rs.next()) {
		        			result = rs.getString(column[i]);
		        		}
		        		result = result + " | ";
		        		rs.first();
		        		if (i < column.length - 1)
		        		{
		        			result = result + rs.getString(column[i + 1]);
		        		}
			       }
		    } catch (SQLException e ) {
	          	System.out.println(e + "\n");
		    } finally {
		    	if (stmt != null) { stmt.close(); }
		    }
			return result;
		}
	
	// Insert a new item into a table
	// ## WIP ### --syntax is right but seems to have key constraint problems
	public boolean DBInsert(Connection con, String dbName, String table, String values[])
		throws SQLException {
		String query = "insert into " + dbName + "." + table + " values(";
		String arguments = "";
		for (int i = 0; i < values.length; i++) {
			if (isdigit(values[i])) {
				arguments = arguments + values[i];
			}
			else if (values[i] == "default") {
				arguments = arguments + values[i];
			}
			else {
				arguments = arguments + "\"" +  values[i] + "\"";
			}
			
			if (i != values.length - 1) {
				arguments = arguments + ", ";
			}
		}
		query = query + arguments + ");";
		//System.out.println("Attempting insertion of " + values + " into " + dbName + "." + table);
		System.out.println("Attempting query: " + query);
		
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(query);
	    } catch (SQLException e ) {
          	System.out.println(e + "\n");
          	return false;
	    } finally {
	    	if (stmt != null) { stmt.close(); }
	    }
		return true;
	}
	
	// Update a specific item in the database
	// ### WIP ### -- not tested
	public boolean DBUpdate(Connection con, String dbName, String table, String value, String insert_column, String check_column, String test_case)
		throws SQLException {
		String query = "update " + dbName + "." + table + "x" + " set " + insert_column + "=" + value + " where " + "x." + check_column + "=" + test_case; 
		
		System.out.println("Attempting update of " + value + " into " + dbName + "." + table + "." + insert_column);
		
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(query);
	    } catch (SQLException e ) {
          	System.out.println(e + "\n");
          	return false;
	    } finally {
	    	if (stmt != null) { stmt.close(); }
	    }
		return true;
	}
	
	// Delete an item from the database
	// ### WIP ### -- works so far
	public boolean DBDelete(Connection con, String dbName, String table, String match_string[], String match_column[])
		throws SQLException {
		String query = "delete from " + dbName + "." + table;
		// Check to see if specific columns can be deleted
		if (match_string.length == 0 || match_column.length == 0)
		{
			
		}
		else if (match_string.length == match_column.length)
		{
			query = query + " where ";
			// Add the search terms and columns to the query
			for (int i = 0; i < match_string.length; i++)
			{
				query = query + match_column[i] + "=" + "'" + match_string[i] + "'";
				if (i < match_string.length - 1)
				{
					query = query + " and ";
				}
			}
			query = query + ";";
		}
		else
		{
			// fail
		}
		System.out.println("Attempting query: " + query);
		
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			
	        stmt.executeUpdate(query);
	        
	    } catch (SQLException e ) {
          	System.out.println(e + "\n");
	    } finally {
	    	if (stmt != null) { stmt.close(); }
	    }
		return true;
	}


	// This function initializes a simple console-based database interaction interface.
	// ### WIP ### -- works, but pretty crude	public void DBInteract(Connection con, String dbName)
	    throws SQLException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("### Database Interaction Interface 0.1 ###\n");
		String query = "";
		while (query != "qq")
		{
			System.out.println("Type a query to interact or qq to quit");
			try {
				query = br.readLine();
			}
			catch (IOException e2) {
				e2.printStackTrace();
			}
			if (query == "qq\0") {
				System.out.println("User Quit\n");
				System.exit(1);
			}
			System.out.println("Attempting sql query...\n");
				       
		    Statement stmt = null;
		    try {
		    	
		        stmt = con.createStatement();
		        ResultSet rs = stmt.executeQuery(query);
		        System.out.println("What to view: ");
		        String view = br.readLine();
		        while (rs.next()) {
		        	// experimental code
		        	    
		        	// end experimental code
		        	
		        	// old code
		            String ProjEntry = rs.getString(view);          
		            System.out.println(ProjEntry);
		        }
		    } catch (SQLException e ) {
		          	System.out.println(e + "\n");
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
		        if (stmt != null) { stmt.close(); }
		    }
		}
	}
	
	
	public static void main(String args[])
	{
		Connection con = null;
		String dbName = "SCRUMptious";
		DBConnect c = new DBConnect();
		LoadDriver d = new LoadDriver();
				
		try {
			d.load();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			con = c.Connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBQuery q = new DBQuery();
		//String values[] = { "test1", "0", "2", "default" };//"default", "1", "3", "if jimmy cracked corn and nobody cares...", "default", "1" };
		String match_string[] = {"test1"};
		String match_column[] = {"Name"};
		String column[] = { "Name", "creatorID"};
		String table = "Project";
		try {
			//System.out.println(q.DBSelect(con, dbName, "Item", "Entry"));
			//if (!q.DBInsert(con, dbName, "Project", values))
			//{
			//	System.out.println("Error!");
			//}
			if(q.DBDelete(con, dbName, table, match_string, match_column))
			{
				System.out.println("Deleted...");
			}
			//System.out.println(q.DBSelectColumn(con, dbName, table, column, match_string, match_column));
			//System.out.println(q.DBSelectRow(con, dbName, table, column, match_string, match_column));   //(con, dbName, "Project", "CreationTime", match_string, match_column));
			//System.out.println(q.DBRowSearch(con, dbName, table, column, match_string, match_column));
			//System.out.println(q.DBSelectColumn(con, dbName, table, column, match_string, match_column));//, match_string, match_column));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}


