package toystore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import toystore.dto.Vendor;
public class VendorDao 
{
	public void createVendorTable() throws Exception
	{
		try {
		Connection con=createConnection();
		Statement st=con.createStatement();
	
		boolean res= st.execute("create table if not exists vendortable(id int primary key auto_increment,name varchar(45),email varchar(45) unique,pwd varchar(45))");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public Connection createConnection() throws Exception
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost:3307/toystoredb?createDatabaseIfNotExist=true","root","root");
	}
	//Save vendor details in table
	public int saveVendor(Vendor vendor) throws Exception
	{
		try {
		Connection con=createConnection();
		PreparedStatement ps=con.prepareStatement("insert into vendortable(name,email,pwd) values(?,?,?)");
		ps.setString(1, vendor.getName());
		ps.setString(2, vendor.getEmail());
		ps.setString(3, vendor.getPwd());
		return ps.executeUpdate();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}
	//Login Vendor
	public boolean loginVendor(Vendor vendor) throws Exception
	{
		boolean isFound = false;
		Connection con=createConnection();
		PreparedStatement ps=con.prepareStatement("select * from vendortable where email=? and pwd=?");
		ps.setString(1, vendor.getEmail());
		ps.setString(2, vendor.getPwd());
		ResultSet rs=ps.executeQuery();
		while(rs.next())
		{
			//System.out.println(rs.getInt("id")+" | "+rs.getString("name")+" | "+rs.getString("email")+" | "+rs.getString("pwd"));
			isFound=true;
		}		
		return isFound;		
	}
	public int updateProfileBasedonId(String email,String pwd,String omail,String opwd) throws Exception 
	{
		Connection con=createConnection();
		PreparedStatement ps=con.prepareStatement("update vendortable set email=?,pwd=? where email=? and pwd=?");
		ps.setString(1, email);
		ps.setString(2, pwd);
		ps.setString(3,omail);
		ps.setString(4, opwd);
		return ps.executeUpdate();
	}
	public int deleteVendorProfile(String email,String pwd) throws Exception 
	{
		Connection con=createConnection();
		PreparedStatement ps=con.prepareStatement("delete from vendortable where email=? and pwd=?");
		ps.setString(1,email);
		ps.setString(2, pwd);
		return ps.executeUpdate();
	}
	public void fetchVendorProfile() throws Exception 
	{
		Connection con=createConnection();
		PreparedStatement ps=con.prepareStatement("select * from vendortable");
		ResultSet rs=ps.executeQuery();
		while(rs.next())
		{
			System.out.println(rs.getInt("id")+" | "+rs.getString("name")+" | "+rs.getString("email")+" | "+rs.getString("pwd"));
		}		
	}
}
