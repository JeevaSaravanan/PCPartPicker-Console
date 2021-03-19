import java.util.*;
import java.sql.*;

public class PartPicker {
	Connection connect;
	ShoppingCart shop=new ShoppingCart();
	User u;
	Scanner sc=new Scanner(System.in);
	
	public void selectProcessor() {
		try {
		PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT pc_part_picker.vendor_table.ID, pc_part_picker.vendor_table.VendorName FROM pc_part_picker.vendor_table WHERE pc_part_picker.vendor_table.ID IN (SELECT pc_part_picker.gen_type.CID FROM pc_part_picker.gen_type INNER JOIN pc_part_picker.cpu ON pc_part_picker.cpu.GID=pc_part_picker.gen_type.GID)");
		ResultSet rs=stmt.executeQuery();
		System.out.println("Choose the Processor Brand you would like to use");
		int i=1;
		int option;
		ArrayList<Brand> brandProcessor=new ArrayList<Brand>();
		while(rs.next()) {
			System.out.println("Press "+i+" for "+rs.getString("VendorName"));
			brandProcessor.add(new Brand(rs.getInt("ID"),rs.getString("VendorName")));
			i++;
		}
		option=sc.nextInt();
		stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.cpu WHERE pc_part_picker.cpu.GID IN (SELECT pc_part_picker.gen_type.GID FROM pc_part_picker.gen_type WHERE pc_part_picker.gen_type.CID=?)");
		stmt.setInt(1,brandProcessor.get(option-1).id);
		rs=stmt.executeQuery();
		ArrayList<String> pid=new ArrayList<String>();
		HashMap<String,Processor> processorMap=new HashMap<String,Processor>();
		System.out.println("Choose the Processor you would like to use");
		i=1;
		
		System.out.println("-------------------------------------------------------------------------------");
		System.out.println("----------------MODEL--------------------------DETAILS--------------------------------RATE---------");
		while(rs.next()) {
			System.out.println("Press "+i+" for \t"+rs.getString("MODEL")+"\t --- \t  "+rs.getString("DETAILS")+"\t --- \t  "+rs.getFloat("RATE"));
			pid.add(rs.getString("PID"));
			processorMap.put(rs.getString("PID"),new Processor(rs.getString("PID"),rs.getString("MODEL"),rs.getString("DETAILS"),rs.getFloat("RATE"),rs.getInt("QTY"),rs.getInt("GC"),rs.getInt("GID"),rs.getInt("schemaID")));
			i++;
		}
		System.out.println("-------------------------------------------------------------------------------");
		do {
         option=sc.nextInt();
         if(option>processorMap.size()) {
        	 System.out.println("Enter a correct value");
         }
         else if(!processorMap.get(pid.get(option-1)).checkAvailability()) {
			System.out.println("The product is Not Available,Choose again");
		}
		}while(option>processorMap.size() || processorMap.get(pid.get(option-1)).qty==0);
		shop.cpu=new Processor(processorMap.get(pid.get(option-1)).PID,processorMap.get(pid.get(option-1)).Model,processorMap.get(pid.get(option-1)).Details,processorMap.get(pid.get(option-1)).rate,1,processorMap.get(pid.get(option-1)).gc,processorMap.get(pid.get(option-1)).gid,processorMap.get(pid.get(option-1)).schemaID);
		shop.cpu.setActualQuantity(processorMap.get(pid.get(option-1)).qty);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void selectMotherBoard() {
		try {
			int d=shop.cpu.gid;
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table INNER JOIN pc_part_picker.mother_board ON pc_part_picker.mother_board.Brand=pc_part_picker.vendor_table.ID INNER JOIN pc_part_picker.motherboard_series ON pc_part_picker.motherboard_series.SeriesID=pc_part_picker.mother_board.SeriesID INNER JOIN pc_part_picker.compatible_board ON pc_part_picker.motherboard_series.SeriesID=pc_part_picker.compatible_board.SeriesID WHERE pc_part_picker.compatible_board.GID=?");
			stmt.setInt(1,d);
			ResultSet rs=stmt.executeQuery();
			System.out.println("Choose the MotherBoard you would like to use");
			int i=1;
			ArrayList<String> pid=new ArrayList<String>();
			HashMap<String,MotherBoard> boardMap=new HashMap<String,MotherBoard>();
			System.out.println("-------------------------------------------------------------------------------");
			System.out.println("----------------BRAND-------------------------MODEL--------------------------------RATE---------");
			while(rs.next()) {
				System.out.println("Press "+i+" for \t"+rs.getString("VendorName")+"\t --- \t  "+rs.getString("MODEL")+"\t --- \t  "+rs.getFloat("RATE"));
				pid.add(rs.getString("PID"));
				boardMap.put(rs.getString("PID"),new MotherBoard(rs.getString("PID"),rs.getString("MODEL"),rs.getString("VendorName"),rs.getFloat("RATE"),rs.getInt("QTY"),rs.getInt("SeriesID"),rs.getInt("schemaID")));
				i++;
			}
			System.out.println("-------------------------------------------------------------------------------");
			int option=0;
			do {
	         option=sc.nextInt();
	         if(option>boardMap.size()) {
	        	 System.out.println("Enter a correct value");
	         }
	         else if(!boardMap.get(pid.get(option-1)).checkAvailability()) {
				System.out.println("The product is Not Available,Choose again");
			}
			}while(option>boardMap.size()|| boardMap.get(pid.get(option-1)).qty==0);
			shop.board=new MotherBoard(boardMap.get(pid.get(option-1)).PID,boardMap.get(pid.get(option-1)).Model,boardMap.get(pid.get(option-1)).brand,boardMap.get(pid.get(option-1)).rate,1,boardMap.get(pid.get(option-1)).seriesid,boardMap.get(pid.get(option-1)).schemaID);
			shop.board.setActualQuantity(boardMap.get(pid.get(option-1)).qty);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void isCompatibleGraphicCard() {
		int d=shop.cpu.gc;
		String ot="Y";
		if(d==0) {
			System.out.println("Your Processor Already has Graphic Card. Do you want to Proceed Further?[Y/N]");
			ot=sc.next();
			String yes="Y";
			if(ot.equalsIgnoreCase(yes)){
				selectGraphicCard();
			}
			else {
				return;
			}
		}
		else {
			System.out.println("Your Processor doesn't have any in-built Graphic Card.");
			selectGraphicCard();
		}
		
	}
	public void selectGraphicCard() {
		try {
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table INNER JOIN pc_part_picker.graphic_card ON pc_part_picker.graphic_card.BRAND=pc_part_picker.vendor_table.ID");
			ResultSet rs=stmt.executeQuery();
			System.out.println("Choose the Graphic Card you would like to use");
			int i=1;
			ArrayList<String> pid=new ArrayList<String>();
			HashMap<String,GraphicCard> gpuMap=new HashMap<String,GraphicCard>();
			System.out.println("-------------------------------------------------------------------------------");
			System.out.println("----------------BRAND--------------GPU CHIP-------------------MODEL------------------------RATE---------");
			while(rs.next()) {
				System.out.println("Press "+i+" for \t"+rs.getString("VendorName")+"\t --- \t  "+rs.getString("GPU_CHIP")+"\t --- \t "+rs.getString("MODEL")+"\t --- \t "+rs.getFloat("RATE"));
				pid.add(rs.getString("PID"));
				gpuMap.put(rs.getString("PID"),new GraphicCard(rs.getString("PID"),rs.getString("MODEL"),rs.getString("GPU_CHIP"),rs.getString("VendorName"),rs.getFloat("RATE"),rs.getInt("QTY"),rs.getInt("schemaID")));
				i++;
			}
			System.out.println("-------------------------------------------------------------------------------");
			int qty=0,option=0;
			do {
	         option=sc.nextInt();
	         if(option>gpuMap.size()) {
	        	 System.out.println("Enter a Correct Value");
	         }
	         else if(!gpuMap.get(pid.get(option-1)).checkAvailability()) {
				System.out.println("The product is Not Available,Choose again");
			}
			}while(option>gpuMap.size()||gpuMap.get(pid.get(option-1)).qty==0);
			shop.gpu=new GraphicCard(gpuMap.get(pid.get(option-1)).PID,gpuMap.get(pid.get(option-1)).Model,gpuMap.get(pid.get(option-1)).gpuChip,gpuMap.get(pid.get(option-1)).brand,gpuMap.get(pid.get(option-1)).rate,1,gpuMap.get(pid.get(option-1)).schemaID);
			shop.gpu.setActualQuantity(gpuMap.get(pid.get(option-1)).qty);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void selectMemoryModule() {
		try {
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table INNER JOIN pc_part_picker.memory_module ON pc_part_picker.memory_module.BRAND=pc_part_picker.vendor_table.ID");
			ResultSet rs=stmt.executeQuery();
			int option=0,qty=0;
			ArrayList<String> pid=new ArrayList<String>();
			HashMap<String,MemoryModule> memoryMap=new HashMap<String,MemoryModule>();
			System.out.println("Choose the Memory Module you would like to use");
			int i=1;
			
			System.out.println("-------------------------------------------------------------------------------");
			System.out.println("----------------BRAND---------------------MODEL------------------------RATE---------");
			while(rs.next()) {
				System.out.println("Press "+i+" for \t"+rs.getString("VendorName")+"\t --- \t  "+rs.getString("MODEL")+"\t --- \t "+rs.getFloat("RATE"));
				pid.add(rs.getString("PID"));
				memoryMap.put(rs.getString("PID"),new MemoryModule(rs.getString("PID"),rs.getString("MODEL"),rs.getString("VendorName"),rs.getFloat("RATE"),rs.getInt("QTY"),rs.getInt("schemaID")));
				i++;
			}
			System.out.println("-------------------------------------------------------------------------------");
			do {
	         option=sc.nextInt();
	         if(option>memoryMap.size()) {
	        	 System.out.println("Enter a Correct Value");
	         }
	         else if(!memoryMap.get(pid.get(option-1)).checkAvailability()) {
				System.out.println("The product is Not Available,Choose again");
			}
			else {
			do {
			System.out.println("Enter the Quantity Required");
			qty=sc.nextInt();
			if(!memoryMap.get(pid.get(option-1)).qtyAvailability(qty)) {
				System.out.println("The Required Quantity is not available,Choose again");
			}
			}while(memoryMap.get(pid.get(option-1)).qty<qty);
			}
			}while(option>memoryMap.size()||memoryMap.get(pid.get(option-1)).qty==0);

			   shop.ram=new MemoryModule(memoryMap.get(pid.get(option-1)).PID,memoryMap.get(pid.get(option-1)).Model,memoryMap.get(pid.get(option-1)).brand,memoryMap.get(pid.get(option-1)).rate,qty,memoryMap.get(pid.get(option-1)).schemaID);
			   shop.ram.setActualQuantity(memoryMap.get(pid.get(option-1)).qty);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void selectPowerSupply() {
		try {
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table INNER JOIN pc_part_picker.power_supply ON pc_part_picker.power_supply.BRAND=pc_part_picker.vendor_table.ID");
			ResultSet rs=stmt.executeQuery();
			int option=0;
			ArrayList<String> pid=new ArrayList<String>();
			HashMap<String,PowerSupply> powerMap=new HashMap<String,PowerSupply>();
			System.out.println("Choose the Power Supply you would like to use");
			int i=1;
			
			System.out.println("-------------------------------------------------------------------------------");
			System.out.println("----------------BRAND---------------------MODEL------------------------RATE---------");
			while(rs.next()) {
				System.out.println("Press "+i+" for \t"+rs.getString("VendorName")+"\t --- \t  "+rs.getString("MODEL")+"\t --- \t  "+rs.getString("Details")+"\t --- \t "+rs.getFloat("RATE"));
				pid.add(rs.getString("PID"));
				powerMap.put(rs.getString("PID"),new PowerSupply(rs.getString("PID"),rs.getString("MODEL"),rs.getString("Details"),rs.getString("VendorName"),rs.getFloat("RATE"),rs.getInt("QTY"),rs.getInt("schemaID")));
				i++;
			}
			System.out.println("-------------------------------------------------------------------------------");
			do {
	         option=sc.nextInt();
	         if(option>powerMap.size()) {
	        	 System.out.println("Enter a Correct Value");
	         }
	         else if(!powerMap.get(pid.get(option-1)).checkAvailability()) {
				System.out.println("The product is Not Available,Choose again");
			}
			}while(option>powerMap.size()||powerMap.get(pid.get(option-1)).qty==0);

			   shop.power=new PowerSupply(powerMap.get(pid.get(option-1)).PID,powerMap.get(pid.get(option-1)).Model,powerMap.get(pid.get(option-1)).Details,powerMap.get(pid.get(option-1)).brand,powerMap.get(pid.get(option-1)).rate,1,powerMap.get(pid.get(option-1)).schemaID);
			   shop.power.setActualQuantity(powerMap.get(pid.get(option-1)).qty);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void selectCabinets() {
		try {
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table INNER JOIN pc_part_picker.cabinets ON pc_part_picker.cabinets.BRAND=pc_part_picker.vendor_table.ID");
			ResultSet rs=stmt.executeQuery();
			int option=0;
			ArrayList<String> pid=new ArrayList<String>();
			HashMap<String,Cabinets> cabinetsMap=new HashMap<String,Cabinets>();
			System.out.println("Choose the Cabinets you would like to use");
			int i=1;
			
			System.out.println("-------------------------------------------------------------------------------");
			System.out.println("----------------BRAND---------------------MODEL------------------------RATE---------");
			while(rs.next()) {
				System.out.println("Press "+i+" for \t"+rs.getString("VendorName")+"\t --- \t  "+rs.getString("MODEL")+"\t --- \t "+rs.getFloat("RATE"));
				pid.add(rs.getString("PID"));
				cabinetsMap.put(rs.getString("PID"),new Cabinets(rs.getString("PID"),rs.getString("MODEL"),rs.getString("VendorName"),rs.getFloat("RATE"),rs.getInt("QTY"),rs.getInt("schemaID")));
				i++;
			}
			System.out.println("-------------------------------------------------------------------------------");
			do {
	         option=sc.nextInt();
	         if(option>cabinetsMap.size()) {
	        	 System.out.println("Enter a Correct Value");
	         }
	         else if(!cabinetsMap.get(pid.get(option-1)).checkAvailability()) {
				System.out.println("The product is Not Available,Choose again");
			}
			}while(option>cabinetsMap.size()||cabinetsMap.get(pid.get(option-1)).qty==0);

			   shop.cabinets=new Cabinets(cabinetsMap.get(pid.get(option-1)).PID,cabinetsMap.get(pid.get(option-1)).Model,cabinetsMap.get(pid.get(option-1)).brand,cabinetsMap.get(pid.get(option-1)).rate,1,cabinetsMap.get(pid.get(option-1)).schemaID);
			   shop.cabinets.setActualQuantity(cabinetsMap.get(pid.get(option-1)).qty);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void selectUps() {
		try {
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table INNER JOIN pc_part_picker.ups ON pc_part_picker.ups.BRAND=pc_part_picker.vendor_table.ID");
			ResultSet rs=stmt.executeQuery();
			int option=0;
			ArrayList<String> pid=new ArrayList<String>();
			HashMap<String,Ups> upsMap=new HashMap<String,Ups>();
			System.out.println("Choose the UPS you would like to use");
			int i=1;
			
			System.out.println("-------------------------------------------------------------------------------");
			System.out.println("----------------BRAND---------------------MODEL------------------------RATE---------");
			while(rs.next()) {
				System.out.println("Press "+i+" for \t"+rs.getString("VendorName")+"\t --- \t  "+rs.getString("MODEL")+"\t --- \t "+rs.getFloat("RATE"));
				pid.add(rs.getString("PID"));
				upsMap.put(rs.getString("PID"),new Ups(rs.getString("PID"),rs.getString("MODEL"),rs.getString("VendorName"),rs.getFloat("RATE"),rs.getInt("QTY"),rs.getInt("schemaID")));
				i++;
			}
			System.out.println("-------------------------------------------------------------------------------");
			do {
	         option=sc.nextInt();
	         if(option>upsMap.size()) {
	        	 System.out.println("Enter a Correct Value");
	         }
	         else if(!upsMap.get(pid.get(option-1)).checkAvailability()) {
				System.out.println("The product is Not Available,Choose again");
			}
			}while(option>upsMap.size()||upsMap.get(pid.get(option-1)).qty==0);

			   shop.ups=new Ups(upsMap.get(pid.get(option-1)).PID,upsMap.get(pid.get(option-1)).Model,upsMap.get(pid.get(option-1)).brand,upsMap.get(pid.get(option-1)).rate,1,upsMap.get(pid.get(option-1)).schemaID);
			   shop.ups.setActualQuantity(upsMap.get(pid.get(option-1)).qty);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void selectStorage() {
		try {
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table INNER JOIN pc_part_picker.storage ON pc_part_picker.storage.BRAND=pc_part_picker.vendor_table.ID INNER JOIN pc_part_picker.storage_type ON pc_part_picker.storage.SID=pc_part_picker.storage_type.SID");
			ResultSet rs=stmt.executeQuery();
			int option=0,qty=0;
			ArrayList<String> pid=new ArrayList<String>();
			HashMap<String,Storage> storageMap=new HashMap<String,Storage>();
			System.out.println("Choose the Storage you would like to use");
			int i=1;
			
			System.out.println("-------------------------------------------------------------------------------");
			System.out.println("----------------BRAND---------------------MODEL------------------------RATE---------");
			while(rs.next()) {
				System.out.println("Press "+i+" for \t"+rs.getString("VendorName")+"\t --- \t  "+rs.getString("MODEL")+"\t --- \t "+rs.getFloat("RATE"));
				pid.add(rs.getString("PID"));
				storageMap.put(rs.getString("PID"),new Storage(rs.getString("PID"),rs.getString("MODEL"),rs.getString("VendorName"),rs.getString("TYPE"),rs.getString("CAPACITY"),rs.getFloat("RATE"),rs.getInt("QTY"),rs.getInt("SID"),rs.getInt("schemaID")));
				i++;
			}
			System.out.println("-------------------------------------------------------------------------------");
			do {
	         option=sc.nextInt();
	         if(option>storageMap.size()) {
	        	 System.out.println("Enter a Correct Value");
	         }
	         else if(!storageMap.get(pid.get(option-1)).checkAvailability()) {
				System.out.println("The product is Not Available,Choose again");
			}
	         else {
	 			do {
	 			System.out.println("Enter the Quantity Required");
	 			qty=sc.nextInt();
	 			if(!storageMap.get(pid.get(option-1)).qtyAvailability(qty)) {
	 				System.out.println("The Required Quantity is not available,Choose again");
	 			}
	 			}while(storageMap.get(pid.get(option-1)).qty<qty);
	 			}
			}while(option>storageMap.size()||storageMap.get(pid.get(option-1)).qty==0);

			   shop.storage=new Storage(storageMap.get(pid.get(option-1)).PID,storageMap.get(pid.get(option-1)).Model,storageMap.get(pid.get(option-1)).brand,storageMap.get(pid.get(option-1)).type,storageMap.get(pid.get(option-1)).Capacity,storageMap.get(pid.get(option-1)).rate,qty,storageMap.get(pid.get(option-1)).SID,storageMap.get(pid.get(option-1)).schemaID);
			   shop.storage.setActualQuantity(storageMap.get(pid.get(option-1)).qty);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void orderHistory() {
		try {
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.order_details INNER JOIN pc_part_picker.order ON pc_part_picker.order.OrderID=pc_part_picker.order_details.OrderID WHERE pc_part_picker.order.userID=?");
			stmt.setString(1,u.uid);
			ResultSet rs=stmt.executeQuery();
			System.out.println("------------------------------------------------------------------------");
			System.out.println("-Product----------------Model------------------Rate--------Qty-----Total");
			while(rs.next()) {
				System.out.println("-"+rs.getString("ProductName")+"\t \t"+rs.getString("ProductModel")+"\t \t"+rs.getFloat("RATE")+"\t \t"+rs.getInt("QTY")+"\t \t"+rs.getFloat("RATE")*rs.getInt("QTY")+"-");
				
			}
			System.out.println("--------------------------------------------------------------------");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void shopFunction() {
		selectProcessor();
		selectMotherBoard();
		isCompatibleGraphicCard();
		selectMemoryModule();
		selectPowerSupply();
		selectCabinets();
		selectUps();
		selectStorage();
		int option=0;
		do {
        	System.out.println("Enter 2 to See Order Summary");
        	System.out.println("Enter 4 to CheckOut");
        	System.out.println("Enter 5 to view Order History");
        	System.out.println("Enter 0 number to exit");
        	option=sc.nextInt();
        	sc.nextLine();
        	switch(option) {
        	case 2:
        		shop.getSummary();
        		break;
        	case 4:
        		checkOut();
        		break;
        	case 5:
        		orderHistory();
        		break;
        	default:
        		break;
        	}
		}while(option!=0);
	}
	public void update(String PID,int qty,int id) {
		try {
			String check="";
			PreparedStatement s=(PreparedStatement)connect.prepareStatement("SELECT pc_part_picker.schema_details.schemaName FROM pc_part_picker.schema_details WHERE ID=?");
			s.setInt(1,id);
			ResultSet rs=s.executeQuery();
			while(rs.next()) {
				check=rs.getString("schemaName");
			}
			s=(PreparedStatement)connect.prepareStatement("UPDATE pc_part_picker.? SET QTY=? WHERE PID=?");
			s.setString(1,check);
			s.setInt(2,shop.cpu.actualQuantity-shop.cpu.qty);
			s.setString(3,shop.cpu.PID);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void putinOrder(String id,String orderId,String pid,String productName,String productModel,float rate,int qty) {
		try {
			
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("INSERT INTO pc_part_picker.order_details(OrderDetailsID,orderID,PID,ProductName,ProductModel,RATE,QTY) VALUES (?,?,?,?,?,?,?)");
            stmt.setString(1,id);
            stmt.setString(2,orderId);
            stmt.setString(3,pid);
            stmt.setString(4,productName);
            stmt.setString(5, productModel);
            stmt.setFloat(4,rate);
            stmt.setInt(5,qty);
            int rs=stmt.executeUpdate();
            if(rs==1) {
            	System.out.println(id+" OrderNo is Confirmed");
            }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void checkOut() {
		order o=new order(u.uid,shop.total);
		o.generateOrderID();
		try {
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("INSERT INTO pc_part_picker.order(OrderID,userID,Total) VALUES (?,?,?)");
            stmt.setString(1,o.orderID);
            stmt.setString(2,o.userID);
            stmt.setFloat(3,o.total);
			int rs=stmt.executeUpdate();
			if(rs==1) {
				putinOrder(shop.cpu.PID+o.orderID,o.orderID,shop.cpu.PID,"CPU",shop.cpu.Model,shop.cpu.rate,shop.cpu.qty);
				update(shop.cpu.PID,shop.cpu.qty,shop.cpu.schemaID);
				
				putinOrder(shop.board.PID+o.orderID,o.orderID,shop.board.PID,"Mother Board",shop.board.Model,shop.board.rate,shop.board.qty);
				update(shop.board.PID,shop.board.qty,shop.board.schemaID);
				
				putinOrder(shop.gpu.PID+o.orderID,o.orderID,shop.gpu.PID,"Graphic Card",shop.gpu.Model,shop.gpu.rate,shop.gpu.qty);
				update(shop.gpu.PID,shop.gpu.qty,shop.gpu.schemaID);
				
				putinOrder(shop.power.PID+o.orderID,o.orderID,shop.power.PID,"Power Supply",shop.power.Model,shop.power.rate,shop.power.qty);
				update(shop.power.PID,shop.power.qty,shop.power.schemaID);
				
				putinOrder(shop.cabinets.PID+o.orderID,o.orderID,shop.cabinets.PID,"Cabinets",shop.cabinets.Model,shop.cabinets.rate,shop.cabinets.qty);
				update(shop.cabinets.PID,shop.cabinets.qty,shop.cabinets.schemaID);
				
				putinOrder(shop.ups.PID+o.orderID,o.orderID,shop.ups.PID,"UPS",shop.ups.Model,shop.ups.rate,shop.ups.qty);
				update(shop.ups.PID,shop.ups.qty,shop.ups.schemaID);
				
				putinOrder(shop.ram.PID+o.orderID,o.orderID,shop.ram.PID,"Memory Module",shop.ram.Model,shop.ram.rate,shop.ram.qty);
				update(shop.ram.PID,shop.ram.qty,shop.ram.schemaID);
				
				putinOrder(shop.storage.PID+o.orderID,o.orderID,shop.storage.PID,"Storage",shop.storage.Model,shop.storage.rate,shop.storage.qty);
				update(shop.storage.PID,shop.storage.qty,shop.storage.schemaID);
				
				System.out.println("The order is placed Successfully");
			}
            
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void logOut() {
		u.clearActive();
	}
	public void logIn() {

		String email;
		String psw;
		
		System.out.println("Enter the Email:");
		email=sc.nextLine();
		System.out.println("Enter the Password:");
		psw=sc.nextLine();
		try {
			PreparedStatement stmt=(PreparedStatement)connect.prepareStatement("SELECT * FROM pc_part_picker.user WHERE pc_part_picker.user.Email=? AND pc_part_picker.user.psw=?");
			stmt.setString(1,email);
			stmt.setString(2, psw);
			ResultSet rs=stmt.executeQuery();
			if(rs.next()) {
				System.out.println("Logged in Successfully");
				u=new User(rs.getString("FirstName"),rs.getString("LastName"),rs.getString("Email"),rs.getString("psw"),rs.getString("Phone"));
				u.uid=rs.getString("userID");
				u.setActive();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void signUp() {
		String fn;
		String ln;
		String email;
		String psw;
		String phone;
		try {
			System.out.println("Enter the First Name:");
			fn=sc.nextLine();
			System.out.println("Enter the Last Name:");
			ln=sc.nextLine();
			System.out.println("Enter the Email:");
			email=sc.nextLine();
			System.out.println("Enter the Password:");
			psw=sc.nextLine();
			System.out.println("Enter the Phone No:");
			phone=sc.nextLine();
			u=new User(fn,ln,email,psw,phone);
			u.generateUID();
			u.setActive();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("INSERT INTO pc_part_picker.user(userId,FirstName,LastName,Email,psw,phone,Address,City,State,Zip,Country,EmailVerification,PhoneVerification) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1,u.uid);
            stmt.setString(2,u.firstName);
            stmt.setString(3,u.lastName);
            stmt.setString(4,u.email);
            stmt.setString(5,u.psw);
            stmt.setString(6,u.phone);
            stmt.setString(7,u.address);
            stmt.setString(8,u.city);
            stmt.setString(9,u.state);
            stmt.setString(10,u.zip);
            stmt.setString(11,u.country);
            stmt.setInt(12,u.emailVerfication);
            stmt.setInt(13,u.phoneVerfication);
			int rs=stmt.executeUpdate();
			if(rs==1)
            System.out.println("The User is added Successfully");
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		SetConnection set=new SetConnection();
		PartPicker pp=new PartPicker();
		try {
			pp.connect=set.connectDb();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		Scanner sc=new Scanner(System.in);
        int flag=0,option=0;
        System.out.println("Welcome to PCPartPicker");
        do{
           
            if(pp.u==null || !pp.u.active) {
            	System.out.println("Enter 1 to Login");
            	System.out.println("Enter 2 to Signup");
            	
            	System.out.println("Enter any number to exit");
            	option=sc.nextInt();
            	sc.nextLine();
            	switch(option) {
            	case 1:
            		pp.logIn();
            		break;
            	case 2:
            		pp.signUp();
            		break;
            	default:
            		break;
            	}
            }
            else {
            	System.out.println("Enter 1 to See User Details");
            	System.out.println("Enter 3 to Shop");
            	System.out.println("Enter 5 to Logout");
            	System.out.println("Enter 6 to view Order History");
            	System.out.println("Enter any number to exit");
            	option=sc.nextInt();
            	sc.nextLine();
            	switch(option) {
            	case 1:
            		pp.u.userDetails();
            		break;
            	case 3:
            		pp.shopFunction();
            		break;
            	case 5:
            		pp.logOut();
            		break;
            	case 6:
            		pp.orderHistory();
            		break;
            	default:
            		break;
            	}
            }
            System.out.println("Enter 1 to go back to menu otherwise 0");
            flag=sc.nextInt();

        }while(flag==1);
		
	}
	

}
