package toystore.dto;

import java.util.ArrayList;
import java.util.List;

public class Customer 
{
	private int id;
	private String name;
	private String email;
	private String pwd;
	private double wallet;
	List<Cart> cart;
	
	public List<Cart> getCart() {
		return cart;
	}
	public void setCart(List<Cart> cart) {
		this.cart = cart;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public double getWallet() {
		return wallet;
	}
	public void setWallet(double wallet) {
		this.wallet = wallet;
	}
	public Customer()
	{
		super();
	}
	public Customer(String name, String email, String pwd, double wallet) {
		super();
		this.name = name;
		this.email = email;
		this.pwd = pwd;
		this.wallet = wallet;
		this.cart= new ArrayList<>();
	}
	
	
	public Customer(int id, String name, String email, String pwd, double wallet) {
//		super();
		this(name, email, pwd, wallet);
		this.id = id;
//		this.name = name;
//		this.email = email;
//		this.pwd = pwd;
//		this.wallet = wallet;
 	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", pwd=" + pwd + ", wallet=" + wallet
				+ "]";
	}
	
	
	
}
