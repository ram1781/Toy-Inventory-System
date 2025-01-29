package toystore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import toystore.dto.Shop;
import toystore.dto.Vendor;
public class ShopDao 
{
	static VendorDao vendorDao = new VendorDao();
	static Vendor vendor=new Vendor();
	public void createShopTable() throws Exception
	{
		Connection con=vendorDao.createConnection();
		Statement st=con.createStatement();
		//		st.execute("create table if not exists shop(id int primary key auto_increment,name varchar(45),address varchar(45),email varchar(45),foreign key(email) references vendortable(email))");
		String str="CREATE TABLE IF NOT EXISTS shop(id INT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(45),address VARCHAR(45),email VARCHAR(45))";
		st.execute(str);
	}
	public int registerShop(Shop shop) throws Exception
	{
		Connection con=vendorDao.createConnection();
		try {
			PreparedStatement ps = con.prepareStatement("insert into shop(name,address,email) values(?,?,?)");
			ps.setString(1, shop.getName());
			ps.setString(2, shop.getAddress());
			ps.setString(3, shop.getEmail());
			return ps.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return 0;
	}
	public int shopNameUpdate(Shop shop) throws Exception
	{
		Connection con=vendorDao.createConnection();
		PreparedStatement ps=con.prepareStatement("update shop set name=? where id=?");
		ps.setString(1,shop.getName());
		ps.setInt(2, shop.getId());
		return ps.executeUpdate();
	}
	public int shopAddressUpdate(String address,int id) throws Exception
	{
		Connection con=vendorDao.createConnection();
		PreparedStatement ps=con.prepareStatement("update shop set address=? where id=?");
		ps.setString(1,address);
		ps.setInt(2, id);
		return ps.executeUpdate();
	}
	public int shopNameandAddressUpdate(Shop shop) throws Exception
	{
		Connection con=vendorDao.createConnection();
		PreparedStatement ps=con.prepareStatement("update shop set name=?,address=? where id=?");
		ps.setString(1, shop.getName());
		ps.setString(2,shop.getAddress());
		ps.setInt(3, shop.getId());
		return ps.executeUpdate();
	}
	public int removeShop(int id) throws Exception
	{
		Connection con=vendorDao.createConnection();
		PreparedStatement ps=con.prepareStatement("delete from shop where id=?");
		ps.setInt(1,id);
 		return ps.executeUpdate();
	}
	public boolean fetchingShops(Vendor vendor) throws Exception
	{
		boolean records=false;
		Connection con=vendorDao.createConnection();	
		PreparedStatement ps=con.prepareStatement("select * from shop where email = ?");
		ps.setString(1, vendor.getEmail());
		ResultSet rs=ps.executeQuery();
		while(rs.next())
		{
			System.out.println(rs.getInt("id")+" | "+rs.getString("name")+" | "+rs.getString("address")+" | "+rs.getString("email"));
			records=true;
		}
		return records;
	}
	public Map<Integer, Shop> getShopDetailsByVendor(Vendor vendorDetails) throws Exception{
		Connection con = vendorDao.createConnection();
		PreparedStatement ps = con.prepareStatement("select * from shop where email = ?");
		ps.setString(1, vendorDetails.getEmail());
		ResultSet rs = ps.executeQuery();
		Map<Integer, Shop> shopDetails = new HashMap<>();
		System.out.println("select shop id which want to update:: ");
		Shop shop = null;
		int i =0;
		while(rs.next()) {
			System.out.println(++i+". "+rs.getString("name"));
			shop = new Shop(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getString("email"));
			shopDetails.put(i, shop);
		}
		return shopDetails;
	}
}
