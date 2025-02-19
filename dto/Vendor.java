package toystore.dto;

public class Vendor 
{
	private int id;
	private String name;
	private String email;
	private String pwd;
	
	//getteres and Settors
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
	public Vendor(String name, String email, String pwd) {
		super();
		this.name=name;
		this.email = email;
		this.pwd = pwd;
	}
	public Vendor(String email,String pwd)
	{
		this.email=email;
		this.pwd=pwd;
	}
	
	public Vendor(String email) {
		super();
		this.email = email;
	}
	public Vendor() {
		super();
	}
	@Override
	public String toString() {
		return "Vendor [id=" + id + ", name=" + name + ", email=" + email + ", pwd=" + pwd + "]";
	}
	
	
	
	
	
	
	

}
