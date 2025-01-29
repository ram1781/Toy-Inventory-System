package toystore.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;

import toystore.dao.CustomerDao;
import toystore.dao.ShopDao;
import toystore.dao.ToyDao;
import toystore.dao.VendorDao;
import toystore.dto.Cart;
import toystore.dto.Customer;
import toystore.dto.Shop;
import toystore.dto.Toy;
import toystore.dto.Vendor;

public class toyStoreMain 
{
	static Scanner scanner = new Scanner(System.in);
	static VendorDao vendorDao = new VendorDao();
	static ShopDao shopDao = new ShopDao();
	static Vendor vendor = new Vendor();
	static ToyDao toyDao = new ToyDao();
	static CustomerDao customerDao = new CustomerDao();
	public static void main(String[] args) throws Exception
	{
		vendorDao.createVendorTable();
		shopDao.createShopTable();
		toyDao.createToyTable();
		customerDao.createCustomerTable();
		System.out.println("---------- WELCOME ----------");
		boolean check=true; 
		while(check)
		{
			System.out.println("Enter Your Choice \n 1.Customer \n 2.Vendor \n 3.Exit");
			int choice = scanner.nextInt();
			switch(choice)
			{
			case 1:
				System.out.println("Customer Operation");
				customerOperations();
				break;					
			case 2:
				vendorOperations();
				break;
			case 3:
				check = false;
				break;
			default :
				System.out.println("Invalid option enter valid option");
			}
		}
		System.out.println("THANK YOU VISIT AGAIN 🙏🙏🙏");
	}
	//Customer Operations
	private static void customerOperations() throws Exception 
	{
		boolean customerOpr=true;
		while(customerOpr)
		{
			System.out.println("Enter you Choice :\n 	1.Register\n 	2.Login\n 	3.Exit");
			int customerChoice=scanner.nextInt();
			switch(customerChoice)
			{
				case 1:
					registerCustomer();
					break;
				case 2:
					Customer customer=loginCustomer();
					if(customer != null)
					{
						System.out.println("Login Successfully");
						boolean customerCheck=true;
						while(customerCheck)
						{
							System.out.println("Enter your Choice Customer:\n   1.Shopping\n   2.Profile\n   3.Exit");
							int choice=scanner.nextInt();
							switch(choice)
							{
							case 1:
								System.out.println("Shopping");
								customerShopping(customer);
								break;
							case 2:
								System.out.println("Profile");
								customerProfile(customer);
								break;
							case 3:
								customerCheck=false;
								break;
							default:
								System.err.println("Invalid Input");
							}
							
						}
						
					}
					else
					{
						System.err.println("Details not Found Enter valid details");
//						loginCustomer();
					}
					break;
				case 3:
					customerOpr=false;
					break;
				default:
					System.err.println("Invalid Input");
			}
		}
	}
	//Customer Registration
		private static void registerCustomer()
		{
			System.out.println("Enter customer name");
			String name=scanner.next();
			System.out.println("Enter customer Email");
			String email=scanner.next();
			System.out.println("Enter customer Password");
			String pwd=scanner.next();
			System.out.println("Enter customer Wallet Money");
			double wallet=scanner.nextDouble();
			Customer customer = new Customer(name,email,pwd,wallet);
			try
			{
				int res=customerDao.registerCustomer(customer);
				if(res>0) System.out.println(name+" Customer Registered Successfully");
			}catch(Exception e)
			{
				System.out.println(e.getMessage());
				System.err.println("Customer record alredy Existed");
				registerCustomer();
			}
		}

		//Customer login validation
		private static Customer loginCustomer() 
		{
			System.out.println("Enter customer Email");
			String email=scanner.next();
			System.out.println("Enter customer Password");
			String pwd=scanner.next();
			try {
				Customer res=customerDao.customerLogin(email,pwd);
				return res;
			}catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			return null;
		}
		
	//Customer Shopping Operations
		private static void customerShopping(Customer customer) throws Exception {
			boolean isShop=true;
			while(isShop)
			{
				System.out.println("Enter Your Choice : \n 1.Browse Toys \n 2.Bucket/Cart \n 3.Payment\n 4.Exit");
				int shopChoie=scanner.nextInt();
				switch(shopChoie)
				{
					case 1:
						browseToys(customer);
						break;
					case 2:
						bucketCart(customer);
						break;
					case 3:
						System.out.println("Payment Operation");
						paymentProcess(customer);
						break;
					case 4:
						isShop=false;
						break;					
				}
			}
		}
		//Payment Operations
		private static void paymentProcess(Customer customer) throws Exception {
			Double totalAmount = customer.getCart().stream().map(x->x.getCost()).reduce((x,y)->x+y).get();
			System.out.println("money in your wallet:: "+customer.getWallet());
			System.out.println("Total amount to pay :: "+totalAmount);
			System.out.println("If you want to pay the cash from your Wallet Press 8");
			int opr=scanner.nextInt();
 			if(totalAmount <= customer.getWallet() && opr==8) {
 				customer.setWallet(customer.getWallet()-totalAmount);
				System.out.println("Payment Done\n    Happy shopping");
				System.out.println("If you want to see your current wallet amount press 6");
				int wp=scanner.nextInt();
				if(wp==6)
				{
					System.out.println("The balance in your wallet is : Rs "+customer.getWallet());
				}
				
			}else {
				System.out.println("insufficient funds in your wallet\ntop up your wallet");
			}
		}
		//Browse Toys
		private static void browseToys(Customer customer) throws Exception 
		{
			List<Cart> list=customer.getCart();
			boolean isBrowse=true;
			toyDao.fetchingAllToys();
			while(isBrowse)
			{
				System.out.println("1.Add toy to cart\n2.Exit");
				int ch=scanner.nextInt();
				switch(ch)
				{
				case 1:
					try {
						System.out.println("Enter id you want add to cart");
						int id=scanner.nextInt();
						Cart cart=toyDao.addToyToCart(id,customer.getId());
						list.add(cart);
						System.out.println("Item added to cart succesfully");
						for(Cart c:list)
						{
							System.out.println("toy id :"+c.getToyId());
							System.out.println("Toy name :"+c.getToyType());
							System.out.println("Toy cost :"+c.getCost());
						}
//						ListIterator<Cart> l=list.listIterator();
//						while(l.hasNext())
//						{
//							System.out.println(l.next());
//						}
//						browseToys(customer);
 					}catch(Exception e)
					{
						System.err.println("Details not found");
						browseToys(customer);
					}
					break;
				case 2:
					isBrowse=false;
					break;
				}
			}
		}
		//bucket/cart Opeartions
		private static void bucketCart(Customer customer) throws Exception 
		{
			boolean isBucket=true;
			while(isBucket)
			{
				System.out.println("Enter :\n  1.Update Cart\n  2.Delete Cart\n  3.Exit");
				int choice=scanner.nextInt();
				switch(choice)
				{
					case 1:
						updateCart(customer);
						break;
					case 2:
						customer.setCart(new ArrayList<Cart>());
						break;
					case 3:
						isBucket=false;
						break;
				}
			}
		}
		//update cart
		private static void updateCart(Customer customer) throws Exception 
		{
			boolean cartUpdate=true;
			while(cartUpdate)
			{
				System.out.println("Enter 1 for Add Item to cart\n Enter 2 For to Remove item from cart");
				int choice=scanner.nextInt();
				switch(choice)
				{
				case 1:browseToys(customer);
				break;
				case 2:
					System.out.println(customer.getCart());
					System.out.println("Enter toy id you want to remove");
					int tid=scanner.nextInt();
					customer.getCart().removeIf(x->x.getToyId()==tid);
					System.out.println("Item Deleted to cart succesfully");
					for(Cart c:customer.getCart())
					{
						System.out.println("toy id :"+c.getToyId());
						System.out.println("Toy name :"+c.getToyType());
						System.out.println("Toy cost :"+c.getCost());
					}
					break;
				default:
					cartUpdate=false;
					break;
				}
			}
			
		}
		//delete cart
		private static void deleteCart(Customer customer) 
		{
//			cartDao.fetchCart(customer.getId());
			System.out.println("Enter toy Id You want to Delete");
			int toyId=scanner.nextInt();
//			cartDao.deleteItem(toyId);
		}
	
	//customer Profile Operations
	private static void customerProfile(Customer customer) throws Exception 
	{
		boolean customerProfile=true;
		while(customerProfile)
		{
			System.out.println("Enter Your choice :\n1.Profile Details\n2.Update Wallet Money\n3.Delete profile\n4.Exit");
			int profileChoice=scanner.nextInt();
			switch(profileChoice)
			{
				case 1:
					fetchProfile(customer);
					break;
				case 2:
					updateWalletMoney(customer);
					break;
				case 3:
					deleteCustomerProfile(customer);
					break;
				case 4:
					customerProfile=false;
					break;
				default:
					System.err.println("Invalid Input");
			}
		}
		
	}
	//fetch customer profile
	private static void fetchProfile(Customer customer) throws Exception {
		customerDao.fetchCustomer(customer.getId());
	}
	//updateWalletMoney
	private static void updateWalletMoney(Customer customer) throws Exception {
		customerDao.fetchCustomer(customer.getId());
		try {	
			System.out.println("Enter new Wallet Money");
			double wallet=scanner.nextDouble();
			int res=customerDao.updateCustomerWallet(wallet,customer.getId());
			if(res>0) System.out.println("Wallet updated Successfully");
		}catch(Exception e)
		{
			System.err.println("Invalid Details");
			updateWalletMoney(customer);
		}
		
	}
	//deleteCustomerProfile
	private static void deleteCustomerProfile(Customer customer) throws Exception 
	{
		customerDao.deleteProfile(customer.getId());
	}
	
	
	//Vendor Operation
	private static void vendorOperations() throws Exception 
	{
		boolean isVendor=true;
		while(isVendor)
		{
			System.out.println("Vendor Operations\n  1.Register \n  2.Login \n  3.Exit");
			int vendorChoice=scanner.nextInt();
			switch(vendorChoice)
			{
			case 1:
				System.out.println("Vendor Register Operation");
				vendorRegister();
				break;
			case 2:
			{
				vendorLoginSuccess();
			}
			break;
			case 3:
				isVendor=false;
				break;
			default :
				System.out.println("Enter valid input");
			}
		}

	}
	//Vendor Login success for Shops
	private static void vendorLoginSuccess() throws Exception {
		System.out.println("Vendor Login Operation");
		Vendor vendorDetails=vendorLogin();
		if(vendorDetails != null)
		{
			System.out.println("Login SuccessFully");
			boolean loginOp=true;
			while(loginOp)
			{
				System.out.println("Enter your Choice : \n   1.Shop  \n   2.Profile \n   3.Exit");
				int loginChoice=scanner.nextInt();
				switch(loginChoice)
				{
				case 1:
					shopOpeartion(vendorDetails);
					break;
				case 2:
					System.out.println("Profile Opeartions");
					vendorProfile(vendorDetails);
					break;
				case 3:
					loginOp=false;
					break;
				default:
					System.out.println("Enter valid Data");	
				}
			}
		}
		
	}
	//Vendor Login Success for Profile
	private static void vendorProfile(Vendor vendorDetails) throws Exception 
	{
		boolean vendorOpr=true;
		while(vendorOpr)
		{
			System.out.println("Enter your Option:\n	1.Update Profile\n	2.Delete Profile\n	3.Fetch Profiles\n	4.Exit");
			int vendorOption = scanner.nextInt();
			switch(vendorOption)
			{
			case 1:
				System.out.println("Update Vendor Profile");
				updateProfile(vendorDetails);
				break;
			case 2:
				deleteProfile(vendorDetails);
				break;
			case 3:
				fetchProfile();
				break;
			case 4:
				vendorOpr=false;
				break;
			default:
				System.out.println("Enter valid input ");
			}
		}
	}

	//Vendor Register
	private static void vendorRegister() 
	{
		System.out.println("Enter vendor Name ");
		String name=scanner.next();
		System.out.println("Enter vendor Email ");
		String email=scanner.next();
		System.out.println("Enter vendor Password ");
		String pwd=scanner.next();
		Vendor vendor = new Vendor(name,email,pwd);
		try {
			int res = vendorDao.saveVendor(vendor);
			if(res>0)
			{
				System.out.println(name+" vendor Registered Successfully in database");
			}
		} catch (Exception e) {
			System.err.println("Already data Existed Enter the new Data");
			vendorRegister();
		}
	}
	//Vendor Login
	private static Vendor vendorLogin() throws Exception 
	{
		System.out.println("Enter vendor Email");
		String email=scanner.next();
		System.out.println("Enter password");
		String pwd=scanner.next();
		Vendor vendor=new Vendor(email,pwd);
		try
		{
			boolean vendorFound=vendorDao.loginVendor(vendor);
			if(vendorFound)
			{
				return vendor;
			}
			else
			{
				System.err.println("Incorrect Details Enter Valid Data");
			}
		}
		catch(Exception e)
		{
			vendorLogin();
		}
		return null;				

	}
	//Shop Operations
	private static void shopOpeartion(Vendor vendorDetails) throws Exception 
	{
		boolean checkShop=true;
		while(checkShop)
		{
			System.out.println("Enter your choice:\n 1.Create Shop \n 2.UpdateShop \n 3.Delete Shop\n 4.Fetch Shops"
					+ "\n 5.Create Toys \n 6.Update Toys \n 7.Delete Toys \n 8.Fetch Toys\n 9.Exit");
			int lopginOption=scanner.nextInt();
			switch(lopginOption)
			{
			case 1:
				createShop(vendorDetails);
				break;
			case 2:
				updateShopforVendor(vendorDetails);
				break;
			case 3:
				deleteShop(vendorDetails);
				break;
			case 4:
				fetchShops(vendorDetails);
				break;
			case 5:
				createToy(vendorDetails);
				break;
			case 6:
				updateToys(vendorDetails);
				break;
			case 7:
				deleteToys(vendorDetails);
				break;
			case 8:
				fetchToys(vendorDetails);
				break;
			case 9:checkShop=false;
			break;

			}
		}
	}
	//Create Shop
	private static void createShop(Vendor vendorDetials) throws Exception 
	{
		System.out.println("Enter Shop Name ");
		String name=scanner.next();
		System.out.println("Enter shop address ");
		String address=scanner.next();
		String email=vendorDetials.getEmail();
		Shop shop = new Shop(name,address,email);
		try
		{
			int shopRes=shopDao.registerShop(shop);
			if(shopRes>0)
			{
				System.out.println("Shop Registerd SuccessFully");
			}
		}
		catch(Exception e)
		{
			System.err.println("Shop already Registerd enter valid Details");
			System.out.println(e.getMessage());
			createShop(vendorDetials);
		}
	}

	//Update Shop
	private static void updateShop(Shop shop) throws Exception 
	{
		boolean update=true;
		while(update)
		{
			System.out.println("What you want to Update :\n1.Shop name \n2.shop Address\n3.Both Shop And Address\n4.Exit");
			int updateChoice=scanner.nextInt();
			switch(updateChoice)
			{
			case 1:
				updateShopNameBasedOnEmail(shop);
				break;
			case 2:
				updateShopAddressBasedOnEmail(shop.getId());
				break;
			case 3:
				updateShopName_AddressBasedOnEmail(shop.getId());
				break;
			case 4:
				update=false;
				break;
			default :
				System.out.println("Enter valid Input");
			}
		}
	}
	//updateShopNameBasedOnEmail
	private static void updateShopNameBasedOnEmail(Shop shopObj) throws Exception 
	{
		System.out.println("Enter the New Shop Name");
		String name=scanner.next();
		//		System.out.println("Enter the vendor email");
		//		String email=vendorDetails.getEmail();
		//		Shop shop=new Shop(name,shopObj.getEmail());
		shopObj.setName(name);
		try {
			int res=shopDao.shopNameUpdate(shopObj);
			if(res>0)
			{
				System.out.println("Shop name Updated Successfully");
			}
		}catch(Exception e)
		{
			System.out.println("Details Miss Matched");
			updateShopNameBasedOnEmail(shopObj);
		}
	}
	private static void updateShopforVendor(Vendor vendorDetails) throws Exception{
		Map<Integer, Shop> map = shopDao.getShopDetailsByVendor(vendorDetails);
		Integer id = scanner.nextInt();
		if(map.get(id) != null) {
			System.out.println("Name:: "+map.get(id).getName()+"\n"
					+ "Address:: "+map.get(id).getAddress());
			updateShop(map.get(id));
		}else {
			System.out.println("details not found");
		}

	}
	//updateShopAddressBasedOnEmail
	private static void updateShopAddressBasedOnEmail(int id) 
	{
		System.out.println("Enter the New Address for Shop");
		String address=scanner.next();
		try {
			int res=shopDao.shopAddressUpdate(address,id);
			if(res>0)
			{
				System.out.println("Shop Address Updated Successfully");
			}
		}catch(Exception e)
		{
			System.out.println("Details Miss Matched");
			updateShopAddressBasedOnEmail(id);
		}
	}
	//updateShopName_AddressBasedOnEmail
	private static void updateShopName_AddressBasedOnEmail(int id) 
	{
		System.out.println("Enter the New Shop Name");
		String name=scanner.next();
		System.out.println("Enter the New Address for Shop");
		String address=scanner.next();
		Shop shop=new Shop();
		shop.setName(name);
		shop.setAddress(address);
		shop.setId(id);

		try {
			int res=shopDao.shopNameandAddressUpdate(shop);
			if(res>0)
			{
				System.out.println("Shop Address & name Updated Successfully");
			}
		}catch(Exception e)
		{
			System.out.println("Details Miss Matched");
			updateShopName_AddressBasedOnEmail(id);			
		}

	}
	//Delete Shop
	private static void deleteShop(Vendor vendor) throws Exception 
	{
		Map<Integer, Shop> map =shopDao.getShopDetailsByVendor(vendor);

		Integer id=scanner.nextInt();
		try
		{
			if(map.get(id) != null) {
				System.out.println("Name:: "+map.get(id).getName()+"\n"
						+ "Address:: "+map.get(id).getAddress());
				int res =shopDao.removeShop(map.get(id).getId());
				if(res>0)
				{
					System.out.println("Shop Deleted Succesfully");
				}
				else
				{
					System.out.println("Shop not found for this details");
				}
			}else {
				System.out.println("details not found");
			}

		}catch(Exception e)
		{
			System.err.println("Shop not found");
			deleteShop(vendor);
			//System.out.println(e.getMessage());
		}				
	}
	//Fetch Shops
	private static void fetchShops(Vendor vendor) throws Exception 
	{
		boolean res=shopDao.fetchingShops(vendor);		
		if(!res)
		{
			System.out.println("No Records in the Shop table :-(");
		}
	}
	//create Toy
	private static void createToy(Vendor vendordetails) 
	{		
		System.out.println("Enter Toy Brand :");
		String brand=scanner.next();
		System.out.println("Enter Cost :");
		double cost=scanner.nextDouble();
		System.out.println("Enter toy Type");
		String type=scanner.next();
		System.out.println("Toy Color ");
		String color=scanner.next();
		System.out.println("Enter Quantity");
		int quantity=scanner.nextInt();
		String vendorEmail=vendordetails.getEmail();
		Toy toy = new Toy(brand,cost,type,color,quantity,vendorEmail);
		try {
			toyDao.registerToy(toy);
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			createToy(vendordetails);
		}
	}
	//updateToys
	private static void updateToys(Vendor vendor) throws Exception 
	{
		fetchToys(vendor);
		System.out.println("Enter id you want to update");
		int id=scanner.nextInt();
		System.out.println("Enter new  Cost :");
		double cost=scanner.nextDouble();
		System.out.println("Toy new Color ");
		String color=scanner.next();
		System.out.println("Enter new Quantity");
		int quantity=scanner.nextInt();
		try {
			int res=toyDao.toysUpdate(cost,color,quantity,id);
			if(res>0)
			{
				System.out.println("Toys Details Updated Successfully");
			}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	//deleteToys
	private static void deleteToys(Vendor vendor) throws Exception
	{	
		fetchToys(vendor);
		System.out.println("Enter toy id you want to delete");
		int id=scanner.nextInt();
		try {
			int res=toyDao.toyDelete(id);
			if(res>0) System.out.println(" Successfully toy deleted from table");
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	//fetchToys
	private static void fetchToys(Vendor vendor) throws Exception {
		String email=vendor.getEmail();
		toyDao.fetchingToys(email);
	}
	//updateProfile
	private static void updateProfile(Vendor vendorDetails) throws Exception 
	{
		String omail=vendorDetails.getEmail();
		String opwd=vendorDetails.getPwd();
		System.out.println("Enter vendor New Email ");
		String email=scanner.next();
		System.out.println("Enter vendor New Password ");
		String pwd=scanner.next();
		try {
			int res=vendorDao.updateProfileBasedonId(email,pwd,omail,opwd);
			if(res>0) System.out.println("Vendor Details Updated Successfully");
			else System.out.println("Details not updated");
		}catch(Exception e)
		{
			System.out.println("Details Not Found");
			updateProfile(vendorDetails);
		}
	}
	//deleteProfile
	private static void deleteProfile(Vendor vendorDetails) throws Exception 
	{
		System.out.println("Enter your mail id");
		String mail=scanner.next();
		System.out.println("Enter Password");
		String pwd=scanner.next();		
		try {
			if(vendorDetails.getEmail().equals(mail)&&vendorDetails.getPwd().equals(pwd))
			{
				int res=vendorDao.deleteVendorProfile(mail,pwd);
				if(res>0) System.out.println("Vendor Details Deleted Successfully");
				vendorOperations();
			}
			else
			{
				System.out.println("Invalid Details");
			}
		}catch(Exception e)
		{
			System.out.println("Details Not Found");
			deleteProfile(vendorDetails);
		}
	}
	//fetchProfile
	private static void fetchProfile() throws Exception 
	{
		vendorDao.fetchVendorProfile();				
	}
}