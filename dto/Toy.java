package toystore.dto;

public class Toy 
{
	private int id;
	private String brand;
	private double cost;
	private String type;
	private String color;
	private int quantity;
	private String vendorMail;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getVendorMail() {
		return vendorMail;
	}
	public void setVendorMail(String vendorMail) {
		this.vendorMail = vendorMail;
	}
	public Toy() {
		super();
	}
	public Toy(String brand, double cost, String type, String color, int quantity, String vendorMail) {
		super();
		this.brand = brand;
		this.cost = cost;
		this.type = type;
		this.color = color;
		this.quantity = quantity;
		this.vendorMail = vendorMail;
	}
	@Override
	public String toString() {
		return "Toy [id=" + id + ", brand=" + brand + ", cost=" + cost + ", type=" + type + ", color=" + color
				+ ", quantity=" + quantity + ", shopName=" + vendorMail + "]";
	}

	
	
}
