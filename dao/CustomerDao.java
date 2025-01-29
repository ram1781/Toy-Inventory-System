package toystore.dao;

import java.sql.*;

import toystore.dto.Customer;
public class CustomerDao 
{
	VendorDao vendarDao = new VendorDao();
	public void createCustomerTable() throws Exception
	{
		try {
			Connection con=vendarDao.createConnection();
			Statement st=con.createStatement();
			st.execute("create table if not exists customer(id int primary key auto_increment,name varchar(45),email varchar(45),pwd varchar(45),wallet double)");
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}	
	}
	public int registerCustomer(Customer customer) throws Exception
	{
		try {
			Connection con=vendarDao.createConnection();
			PreparedStatement ps = con.prepareStatement("insert into customer(name,email,pwd,wallet) values(?,?,?,?)");
			ps.setString(1, customer.getName());
			ps.setString(2, customer.getEmail());
			ps.setString(3, customer.getPwd());
			ps.setDouble(4, customer.getWallet());
			return ps.executeUpdate();
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return 0;
	}
	public Customer customerLogin(String email, String pwd) throws Exception
	{
		boolean isFound = false;
		Connection con=vendarDao.createConnection();
		PreparedStatement ps=con.prepareStatement("select * from customer where email=? and pwd=?");
		ps.setString(1, email);
		ps.setString(2, pwd);
		Customer customer = new  Customer();
		ResultSet rs=ps.executeQuery();
		while(rs.next())
		{
			customer = new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("pwd"), rs.getDouble("wallet"));
			//System.out.println(rs.getInt("id")+" | "+rs.getString("name")+" | "+rs.getString("email")+" | "+rs.getString("pwd"));
			isFound=true;
		}		
		if(isFound)
		{
			return customer;
		}
		else return null;
	}
	public void fetchCustomer(int id) throws Exception 
	{
		Connection con=vendarDao.createConnection();
		PreparedStatement ps=con.prepareStatement("select * from customer where id=?");
		ps.setInt(1, id);
		ResultSet rs=ps.executeQuery();
		while(rs.next())
		{
			System.out.println(rs.getInt("id")+"||"+ rs.getString("name")+"||"+ rs.getString("email")+"||"+ rs.getString("pwd")+"||" +rs.getDouble("wallet"));
		}
		
	}
	public int updateCustomerWallet(double wallet,int id) throws Exception
	{
		Connection con=vendarDao.createConnection();
		PreparedStatement ps=con.prepareStatement("update customer set wallet=? where id=?");
		ps.setDouble(1, wallet);
		ps.setInt(2, id);
		return ps.executeUpdate();
	}
	public void deleteProfile(int id) throws Exception
	{
		Connection con=vendarDao.createConnection();
		PreparedStatement ps=con.prepareStatement("delete from customer where id=?");
		ps.setInt(1, id);
		int res=ps.executeUpdate();
		if(res>0)System.out.println("Customer Deleted Successfully");
		else System.err.println("customer not Found");
	}
	

}
