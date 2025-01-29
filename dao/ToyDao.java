package toystore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import toystore.dto.Cart;
import toystore.dto.Toy;
public class ToyDao 
{
	static VendorDao vendorDao=new VendorDao();
	public void createToyTable() throws Exception
	{
		try {
			Connection con=vendorDao.createConnection();
			Statement st=con.createStatement();
			st.execute("create table if not exists toy(id int primary key auto_increment,brand varchar(45),"
					+ "cost double,type varchar(45),color varchar(45),quantity int,vendor_mail varchar(45))");
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	public void registerToy(Toy toy) throws Exception
	{
		try {
			Connection con=vendorDao.createConnection();
			PreparedStatement ps= con.prepareStatement("insert into toy(brand,cost,type,color,quantity,vendor_mail) values(?,?,?,?,?,?)");
			ps.setString(1, toy.getBrand());
			ps.setDouble(2, toy.getCost());
			ps.setString(3, toy.getType());
			ps.setString(4, toy.getColor());
			ps.setInt(5, toy.getQuantity());
			ps.setString(6, toy.getVendorMail());
			int res=ps.executeUpdate();
//			System.out.println(res);
			if(res>0)	System.out.println("Toy Created Successfully");
			else System.out.println("Inavlid details");
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	public int toysUpdate(double price,String color,int quantity,int id) throws Exception
	{
		try {
			Connection con=vendorDao.createConnection();
			PreparedStatement ps= con.prepareStatement("update toy set cost=?,color=?,quantity=? where id = ?");
			ps.setDouble(1, price);
			ps.setString(2, color);
			ps.setInt(3, quantity);
			ps.setInt(4, id);
			return ps.executeUpdate();
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return 0;
	}
	public int toyDelete(int id) throws Exception {
		Connection con=vendorDao.createConnection();
		PreparedStatement ps= con.prepareStatement("delete from toy where id = ?");
		ps.setDouble(1, id);
		return ps.executeUpdate();
	}
	public void fetchingToys(String email) throws Exception 
	{
		Connection con=vendorDao.createConnection();
		PreparedStatement ps = con.prepareStatement("select * from toy where vendor_mail=?");
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			System.out.println(rs.getInt("id")+" || "+rs.getString("brand")+" || "+rs.getDouble("cost")+" || "
			+rs.getString("type")+" || "+rs.getString("color")+" || "+rs.getInt("quantity")+" || "+rs.getString("vendor_mail"));
		}
	}
	public void fetchingAllToys() throws Exception 
	{
		Connection con=vendorDao.createConnection();
		PreparedStatement ps = con.prepareStatement("select * from toy");
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			System.out.println(rs.getInt("id")+" || "+rs.getString("brand")+" || "+rs.getDouble("cost")+" || "
			+rs.getString("type")+" || "+rs.getString("color")+" || "+rs.getInt("quantity")+" || "+rs.getString("vendor_mail"));
		}
	}
	public Cart addToyToCart(int id,int custId) throws Exception
	{
		Connection con=vendorDao.createConnection();
		PreparedStatement ps = con.prepareStatement("select * from toy where id=?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		Cart cart=new Cart();
		while(rs.next())
		{
			int toyId=rs.getInt("id");
			String toyName=rs.getString("type");
			double toyCost=rs.getDouble("cost");
			cart=new Cart(toyId,toyName,toyCost,custId);
		}
		return cart;
			
	}

}
