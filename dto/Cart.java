package toystore.dto;

public class Cart 
{
	private int toyId;
	private String toyType;
	private double cost;
	private int custID;
	public Cart(int toyId, String toyType, double cost,int custID) {
		super();
		this.toyId = toyId;
		this.toyType = toyType;
		this.cost = cost;
		this.custID=custID;
		
	}
	public int getToyId() {
		return toyId;
	}
	public void setToyId(int toyId) {
		this.toyId = toyId;
	}
	public String getToyType() {
		return toyType;
	}
	public void setToyType(String toyType) {
		this.toyType = toyType;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public int getCustID() {
		return custID;
	}
	public void setCustID(int custID) {
		this.custID = custID;
	}
//	public Cart(int toyId, String toyType, double cost) {
//		super();
//		this.toyId = toyId;
//		this.toyType = toyType;
//		this.cost = cost;
//	}
	public Cart() {
		super();
	}
	@Override
	public String toString() {
		return "Cart [toyId=" + toyId + ", toyType=" + toyType + ", cost=" + cost + ", custID=" + custID + "]";
	}
	
	
	
	
}
