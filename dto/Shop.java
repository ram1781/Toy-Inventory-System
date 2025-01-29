package toystore.dto;

public class Shop 
{
	private int id;
	private String name;
	private String address;
	private String email;
	public Shop(String name, String address, String email) 
	{
		this.name = name;
		this.address = address;
		this.email = email;
	}
	public Shop(int id, String name, String address, String email) {
		this(name, address, email);
		this.id=id;
	}
	public Shop(String name,String email)
	{
		this.name=name;
		this.email=email;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Shop() {
		super();
	}
	@Override
	public String toString() {
		return "Shop [id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + "]";
	}
	
	

}
