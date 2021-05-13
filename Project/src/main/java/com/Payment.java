package com;

import java.sql.*;

public class Payment {
	

	private Connection connect()
	 {
	 Connection con = null;
	 try
	 {
	 Class.forName("com.mysql.jdbc.Driver");

	 //Provide the correct details: DBServer/DBName, username, password
	 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gb", "root", "");
	 }
	 catch (Exception e)
	 {e.printStackTrace();}
	 return con;
	 }
	
	
	
	public String insertPayment(String nameoncard, String cardnumber, String expireddate, String cvv)
	{ 
	 String output = ""; 
	try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 { 
	 return "Error while connecting to the database"; 
	 } 
	 // create a prepared statement
	 String query = " insert into payment(`payId`,`nameOnCard`,`cardNumber`,`expiredDate`,`cvv`) values (?, ?, ?, ?, ?)";
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setInt(1, 0); 
	 preparedStmt.setString(2, nameoncard); 
	 preparedStmt.setInt(3,  Integer.parseInt(cardnumber)); 
	 preparedStmt.setString(4, expireddate);
	 preparedStmt.setString(5, cvv);

	//execute the statement
	 preparedStmt.execute(); //
	 con.close(); //
	 
	 String newPayments = readPayment();
	 output = "{\"status\":\"success\", \"data\": \"" +newPayments + "\"}"; 
	 } 
	catch (Exception e) 
	 { 
		output = "{\"status\":\"error\", \"data\":\"Error while adding the payment.\"}";
		 System.err.println(e.getMessage());
	 } 
	
	return output; 
	}
	
	
	
	public String readPayment()
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for reading."; }
	 
	 
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>nameOncard</th><th>cardNumber</th><th>expiredDate</th><th>cvv</th><th>Update</th><th>Remove</th></tr>"; 
	 
	 String query = "select * from payment"; 
	 Statement stmt = con.createStatement(); 
	 ResultSet rs = stmt.executeQuery(query); 
	 // iterate through the rows in the result set
	 while (rs.next()) 
	 { 
	 String payId = Integer.toString(rs.getInt("payId")); 
	 String nameOncard = rs.getString("nameOncard"); 
	 String cardNumber = Integer.toString(rs.getInt("cardNumber")); 
	 String expiredDate = rs.getString("expiredDate"); 
	 String cvv = rs.getString("cvv"); 
	
	 // Add into the html table
	 output += "<tr><td>" + nameOncard + "</td>"; 
	 output += "<td>" + cardNumber + "</td>"; 
	 output += "<td>" + expiredDate + "</td>"; 
	 output += "<td>" + cvv + "</td>"; 
	 // buttons	//
	 output +=  "<td><input name='btnUpdate' type='button' value='Update' "
	 			+ "class='btnUpdate btn btn-secondary' data-paymentid='" + payId + "'></td>"
	 			+ "<td><input name='btnRemove' type='button' value='Remove' "
	 			+ "class='btnRemove btn btn-danger' data-paymentid='" + payId + "'></td></tr>";
	 
	 } 
	 con.close(); 
	 // Complete the html table
	 output += "</table>"; 
	 } 
	 catch (Exception e) 
	 { 
	 output = "Error while reading the payments."; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 }
	
	 
	

	public String deletePayment(String ID)
	{
	 String output = "";
	try
	 {
	 Connection con = connect();
	 if (con == null)//.
	 {
	 return "Error while connecting to the database for deleting.";
	 }
	 // create a prepared statement
	 String query = "delete from payment where payId=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(ID));

	 // execute the statement
	 preparedStmt.execute();
	 con.close();//.
	 String newPayments = readPayment();
	 output = "{\"status\":\"success\", \"data\": \"" +newPayments + "\"}";
	 }
	catch (Exception e)
	 {
		 output = "{\"status\":\"error\", \"data\":\"Error while deleting the payment.\"}";
		 System.err.println(e.getMessage());
	 }
	return output;
	}
	
	
	
	
	
	public String updatePayment(String ID, String nameoncard, String cardnumber, String expireddate, String cvv)
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for updating."; } 
	 // create a prepared statement
	 String query = "UPDATE payment SET nameOnCard=?,cardNumber=?,expiredDate=?,cvv=?WHERE payId=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
	 preparedStmt.setString(1, nameoncard); 
	 preparedStmt.setInt(2, Integer.parseInt(cardnumber));
	 preparedStmt.setString(3, expireddate);
	 preparedStmt.setString(4, cvv); 
	 preparedStmt.setInt(5, Integer.parseInt(ID)); 


	 preparedStmt.execute(); 
	 con.close(); //.
	 String newPayments = readPayment();
	 output = "{\"status\":\"success\", \"data\": \"" +newPayments + "\"}";
	 } 
	 catch (Exception e) 
	 { 
		 output = "{\"status\":\"error\", \"data\": \"Error while editing the payment.\"}";
		 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 }
	
	 
}